package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Task;
import model.TaskService.TaskService;
import model.Users;
import model.UserService.UserService;
import model.Usertasklist;
import model.UserTaskListService.UserTaskListService;
import model.Userlevel;
import model.UserLevelService.UserLevelService;
import model.Levelreward;
import model.LevelRewardService.LevelRewardService;
import model.Userinventoryitem;
import model.UserInventoryItemService;

@WebServlet(name = "CompleteTaskServlet", urlPatterns = {"/CompleteTaskServlet"})
public class CompleteTaskServlet extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        
        UserTaskListService utls = new UserTaskListService(mgr);

        // Check if user is logged in
        if (user == null) {
            jsonBuilder.add("status", "error");
            jsonBuilder.add("message", "User not logged in");
            jsonBuilder.add("redirect", "/MizukiForest/view/general/login.jsp");
            out.print(jsonBuilder.build().toString());
            return;
        }

        try {
            // Get user with full details
            user = mgr.find(Users.class, user.getUserid());

            // Get parameters
            String taskId = request.getParameter("taskId");
            String isPlaceholder = request.getParameter("isPlaceholder");

            // Validate parameters
            if (taskId == null) {
                jsonBuilder.add("status", "error");
                jsonBuilder.add("message", "Missing task ID");
                out.print(jsonBuilder.build().toString());
                return;
            }

            // Find the task
            Task task = mgr.find(Task.class, taskId);
            if (task == null) {
                jsonBuilder.add("status", "error");
                jsonBuilder.add("message", "Task not found");
                out.print(jsonBuilder.build().toString());
                return;
            }

            // Check if the task is the placeholder task
            boolean isPlaceholderTask = "true".equals(isPlaceholder);
            if (isPlaceholderTask) {
                jsonBuilder.add("status", "error");
                jsonBuilder.add("message", "Cannot complete a placeholder task");
                out.print(jsonBuilder.build().toString());
                return;
            }

            // Check if task has already been completed today
            if (isTaskCompletedToday(utls, user.getUserid(), taskId)) {
                jsonBuilder.add("status", "alreadyCompleted");
                jsonBuilder.add("message", "Task already completed today");
                out.print(jsonBuilder.build().toString());
                return;
            }

            // Start transaction to complete task
            utx.begin();
            
            // Create completion record
            Usertasklist completedTask = new Usertasklist();
            
            // For customizable tasks, we need to check if a record already exists
            String customTaskId = null;
            String customTaskName = null;
            String customTaskDesc = null;
            
            if (task.getIscustomisable()) {
                // Find existing customized task for this user
                List<Usertasklist> userTasks = utls.findTasksByUserId(user.getUserid());
                for (Usertasklist userTask : userTasks) {
                    if (userTask.getTaskid().getTaskid().equals(taskId) && 
                        userTask.getCustomisedtaskname() != null && 
                        !userTask.getIsdeleted()) {
                        customTaskId = userTask.getUsertasklistid();
                        customTaskName = userTask.getCustomisedtaskname();
                        customTaskDesc = userTask.getCustomisedtaskdescription();
                        break;
                    }
                }
                
                // If custom task record exists, update it
                if (customTaskId != null) {
                    completedTask = mgr.find(Usertasklist.class, customTaskId);
                    completedTask.setDatecompleted(new Date());
                } else {
                    // If no custom task exists, create a new record with default task details
                    completedTask.setUsertasklistid(generateNextUserTaskListId(utls));
                    completedTask.setDatetimeaccepted(new Date());
                    completedTask.setDatecompleted(new Date());
                    completedTask.setTaskid(task);
                    completedTask.setUserid(user);
                    completedTask.setIsdeleted(false);
                    mgr.persist(completedTask);
                }
            } else {
                // For default tasks, simply create a new completion record
                completedTask.setUsertasklistid(generateNextUserTaskListId(utls));
                completedTask.setDatetimeaccepted(new Date());
                completedTask.setDatecompleted(new Date());
                completedTask.setTaskid(task);
                completedTask.setUserid(user);
                completedTask.setIsdeleted(false);
                mgr.persist(completedTask);
            }
            
            // Add rewards to user
            // Default rewards: 100 XP and 100 coins per task
            int xpReward = 100;
            int coinReward = 100;
            
            int oldExp = user.getExp();
            int newExp = oldExp + xpReward;
            user.setExp(newExp);
            
            int oldCoins = user.getCoins();
            int newCoins = oldCoins + coinReward;
            user.setCoins(newCoins);
            
            // Check if user leveled up
            boolean leveledUp = false;
            int oldLevel = user.getUserlevel();
            int newLevel = oldLevel;
            
            // Simple level calculation: level = exp / 1000 + 1
            newLevel = (newExp / 1000) + 1;
            leveledUp = (newLevel > oldLevel);
            
            // If leveled up, update user level
            if (leveledUp) {
                user.setUserlevel(newLevel);
                
                // Here you would add any level-up rewards
                // For simplicity, we'll just create a placeholder for the rewards
                JsonArrayBuilder rewardsArray = Json.createArrayBuilder();
                // Add example reward
                JsonObjectBuilder rewardObject = Json.createObjectBuilder();
                rewardObject.add("name", "Level Up Bonus");
                rewardObject.add("quantity", 1);
                rewardsArray.add(rewardObject);
                
                // Add level up info to response
                jsonBuilder.add("leveledUp", true);
                jsonBuilder.add("oldLevel", oldLevel);
                jsonBuilder.add("newLevel", newLevel);
                jsonBuilder.add("rewards", rewardsArray);
            } else {
                jsonBuilder.add("leveledUp", false);
            }
            
            // Save updated user
            mgr.merge(user);
            
            // Commit transaction
            utx.commit();
            
            // Build success response
            jsonBuilder.add("status", "success");
            jsonBuilder.add("message", "Task completed successfully");
            jsonBuilder.add("newExp", newExp);
            jsonBuilder.add("newCoins", newCoins);
            
        } catch (Exception e) {
            try {
                if (utx.getStatus() != javax.transaction.Status.STATUS_NO_TRANSACTION) {
                    utx.rollback();
                }
            } catch (Exception ex) {
                // Ignore rollback error
            }
            
            jsonBuilder.add("status", "error");
            jsonBuilder.add("message", "Error completing task: " + e.getMessage());
        }

        out.print(jsonBuilder.build().toString());
    }

    /**
     * Check if a task has been completed by the user today
     *
     * @param service UserTaskListService instance
     * @param userId User ID
     * @param taskId Task ID
     * @return true if task has already been completed today, false otherwise
     */
    private boolean isTaskCompletedToday(UserTaskListService service, String userId, String taskId) {
        List<Usertasklist> userTasks = service.findTasksByUserId(userId);

        // Get today's date with time set to beginning of day
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        Date startOfDay = today.getTime();

        // Get tomorrow's date (beginning of day)
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        Date startOfTomorrow = tomorrow.getTime();

        // Check if the task has been completed today
        for (Usertasklist task : userTasks) {
            if (task.getTaskid().getTaskid().equals(taskId)
                    && task.getDatecompleted() != null
                    && !task.getIsdeleted()
                    && task.getDatecompleted().compareTo(startOfDay) >= 0
                    && task.getDatecompleted().compareTo(startOfTomorrow) < 0) {
                return true;
            }
        }

        return false;
    }

    private String generateNextUserTaskListId(UserTaskListService service) {
        // Find all user task lists
        List<Usertasklist> allTasks = service.findAllTasks();

        if (allTasks.isEmpty()) {
            return "UTL00001";
        }

        // Find the highest ID
        String highestId = "UTL00000";
        for (Usertasklist task : allTasks) {
            String id = task.getUsertasklistid();
            if (id != null && id.startsWith("UTL") && id.compareTo(highestId) > 0) {
                highestId = id;
            }
        }

        // Extract the numeric part
        String numericPart = highestId.substring(3);
        int nextNum = Integer.parseInt(numericPart) + 1;

        // Format the new ID
        return String.format("UTL%05d", nextNum);
    }
}