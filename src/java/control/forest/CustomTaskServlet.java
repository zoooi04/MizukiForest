package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.json.Json;
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
import model.Users;
import model.Usertasklist;
import model.UserTaskListService.UserTaskListService;

@WebServlet(name = "CustomTaskServlet", urlPatterns = {"/CustomTaskServlet"})
public class CustomTaskServlet extends HttpServlet {

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
            String userTaskListId = request.getParameter("userTaskListId"); // Parameter to identify if we're updating
            String taskName = request.getParameter("taskName");
            String taskDesc = request.getParameter("taskDesc");
            String isPlaceholder = request.getParameter("isPlaceholder"); // Parameter to identify placeholder tasks

            // Validate parameters
            if (taskId == null || taskName == null || taskDesc == null) {
                jsonBuilder.add("status", "error");
                jsonBuilder.add("message", "Missing parameters");
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

            utx.begin();

            // Check if we're updating an existing task
            if (userTaskListId != null && !userTaskListId.isEmpty()) {
                // Find the existing user task
                Usertasklist existingTask = mgr.find(Usertasklist.class, userTaskListId);

                if (existingTask != null && existingTask.getUserid().getUserid().equals(user.getUserid())) {
                    // Update the existing task
                    existingTask.setCustomisedtaskname(taskName);
                    existingTask.setCustomisedtaskdescription(taskDesc);

                    // Save the updated task
                    mgr.merge(existingTask);

                    // Return success response for update
                    jsonBuilder.add("status", "success");
                    jsonBuilder.add("message", "Custom task updated successfully");
                    jsonBuilder.add("taskId", userTaskListId);
                    jsonBuilder.add("operation", "update");
                } else {
                    jsonBuilder.add("status", "error");
                    jsonBuilder.add("message", "Task not found or does not belong to user");
                }
            } else {
                // Create new user task list record
                String newUserTaskId = generateNextUserTaskListId(utls);

                Usertasklist userTask = new Usertasklist();
                userTask.setUsertasklistid(newUserTaskId);
                userTask.setCustomisedtaskname(taskName);
                userTask.setCustomisedtaskdescription(taskDesc);
                userTask.setDatetimeaccepted(new Date());
                userTask.setIsdeleted(false);
                userTask.setTaskid(task);
                userTask.setUserid(user);

                // Save to database
                mgr.persist(userTask);

                // Return success response for creation
                jsonBuilder.add("status", "success");
                jsonBuilder.add("message", "Custom task created successfully");
                jsonBuilder.add("taskId", newUserTaskId);
                jsonBuilder.add("operation", "create");
            }

            utx.commit();

        } catch (Exception e) {
            try {
                if (utx.getStatus() != javax.transaction.Status.STATUS_NO_TRANSACTION) {
                    utx.rollback();
                }
            } catch (Exception ex) {
                // Ignore rollback error
            }
            
            jsonBuilder.add("status", "error");
            jsonBuilder.add("message", "Error saving custom task: " + e.getMessage());
        }

        out.print(jsonBuilder.build().toString());
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