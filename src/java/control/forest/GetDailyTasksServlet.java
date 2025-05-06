package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
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
import model.UserService.UserService;
import model.Users;
import model.Usertasklist;
import model.UserTaskListService.UserTaskListService;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetDailyTasksServlet extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        // Check if user is logged in
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            JSONObject result = new JSONObject();
            result.put("status", "error");
            result.put("message", "User not logged in");
            result.put("redirect", "/MizukiForest/view/general/login.jsp");
            out.print(result.toString());
            return;
        }

        UserService us = new UserService(mgr);
        user = us.findUserById(user.getUserid());

        try {
            TaskService taskService = new TaskService(mgr);
            UserTaskListService userTaskListService = new UserTaskListService(mgr);

            List<Task> tasks = taskService.findAll();
            List<Usertasklist> userTasks = userTaskListService.findTasksByUserId(user.getUserid());

            // Group tasks by customisable flag
            JSONArray defaultTasks = new JSONArray();
            JSONArray customisableTasks = new JSONArray();

            // Add placeholder task if needed
            if (!customisableTasks.isEmpty()) {
// Always ensure there's one placeholder task
                boolean hasPlaceholder = false;
                for (int i = 0; i < customisableTasks.length(); i++) {
                    JSONObject task = customisableTasks.getJSONObject(i);
                    if (!task.getBoolean("isCompletedToday")
                            && task.getString("taskName").equals("Customised Task")
                            && task.getString("taskDescription").equals("Customised Description")) {
                        hasPlaceholder = true;
                        break;
                    }
                }

// If no placeholder, add one using the first customisable task as template
                if (!hasPlaceholder && customisableTasks.length() > 0) {
                    JSONObject templateTask = customisableTasks.getJSONObject(0);
                    JSONObject placeholderTask = new JSONObject();
                    placeholderTask.put("taskId", templateTask.getString("taskId"));
                    placeholderTask.put("taskName", "Customised Task");
                    placeholderTask.put("taskDescription", "Customised Description");
                    placeholderTask.put("isCustomisable", true);
                    placeholderTask.put("isCompletedToday", false);

                    customisableTasks.put(placeholderTask);
                }
            }

            // Set up date ranges for today
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startOfDay = calendar.getTime();

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date startOfTomorrow = calendar.getTime();

            for (Task task : tasks) {
                // Skip deleted or archived tasks
                if (task.getIsdeleted() || task.getIsarchived()) {
                    continue;
                }

                JSONObject taskJson = new JSONObject();
                taskJson.put("taskId", task.getTaskid());
                taskJson.put("taskName", task.getTaskname());
                taskJson.put("taskDescription", task.getTaskdescription());
                taskJson.put("isCustomisable", task.getIscustomisable());

                // Check if task has been completed today
                boolean isCompletedToday = false;
                String userTaskListId = null; // Add this line to track userTaskListId for custom tasks

                for (Usertasklist userTask : userTasks) {
                    if (userTask.getTaskid().getTaskid().equals(task.getTaskid())) {
                        // For customisable tasks, include the userTaskListId and custom name/description
                        if (task.getIscustomisable() && userTask.getCustomisedtaskname() != null) {
                            userTaskListId = userTask.getUsertasklistid();
                            // Override default task name and description with custom values
                            taskJson.put("taskName", userTask.getCustomisedtaskname());
                            taskJson.put("taskDescription", userTask.getCustomisedtaskdescription());
                            taskJson.put("userTaskListId", userTaskListId);
                        }

                        // Check if completed today
                        if (userTask.getDatecompleted() != null
                                && !userTask.getIsdeleted()
                                && userTask.getDatecompleted().compareTo(startOfDay) >= 0
                                && userTask.getDatecompleted().compareTo(startOfTomorrow) < 0) {
                            isCompletedToday = true;
                            break;
                        }
                    }
                }

                taskJson.put("isCompletedToday", isCompletedToday);

                // Include all tasks based on type without any limit
                if (task.getIscustomisable()) {
                    customisableTasks.put(taskJson);
                } else {
                    defaultTasks.put(taskJson);
                }
            }

            int completedCustomTasksToday = 0;
            for (Usertasklist userTask : userTasks) {
                if (userTask.getTaskid().getIscustomisable()
                        && userTask.getDatecompleted() != null
                        && !userTask.getIsdeleted()
                        && userTask.getDatecompleted().compareTo(startOfDay) >= 0
                        && userTask.getDatecompleted().compareTo(startOfTomorrow) < 0) {
                    completedCustomTasksToday++;
                }
            }

// Add to result JSON
            JSONObject result = new JSONObject();
            result.put("status", "success");
            result.put("defaultTasks", defaultTasks);
            result.put("customisableTasks", customisableTasks);
            result.put("userExp", user.getExp());  // Add user's current exp
            result.put("userCoins", user.getCoins());  // Add user's current coin amount
            result.put("completedCustomTasksToday", completedCustomTasksToday);

            out.print(result.toString());

        } catch (Exception e) {
            JSONObject result = new JSONObject();
            result.put("status", "error");
            result.put("message", "Error retrieving tasks: " + e.getMessage());
            out.print(result.toString());
        }
    }
}
