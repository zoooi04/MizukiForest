package control.forum;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import model.Thread;
import model.Threadcategory;
import model.Users;
import model.forumService.ThreadCategoryService;
import model.forumService.ThreadService;

// Removed the @WebServlet annotation to avoid URL pattern conflict.
// The servlet can now be mapped in the web.xml file if needed.
@Transactional
public class AddForumThreadServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Retrieve parameters
            String threadTitle = request.getParameter("threadTitle");
            String threadDescription = request.getParameter("threadDescription");
            String categoryId = request.getParameter("categoryId");

            // String userId = "U2500001"; // Replace with session user ID later
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
//            String userId = currentUser.getUserid();

            // Validate input
            if (threadTitle == null || threadTitle.isEmpty()) {
                throw new IllegalArgumentException("Thread title is required.");
            }
            if (threadDescription == null || threadDescription.isEmpty()) {
                throw new IllegalArgumentException("Thread description is required.");
            }
            if (categoryId == null || categoryId.isEmpty()) {
                throw new IllegalArgumentException("Category ID is required.");
            }

//            // Find user and category entities
//            Users user = entityManager.find(Users.class, userId);
//            if (user == null) {
//                throw new IllegalArgumentException("User not found: " + userId);
//            }
//
//            Threadcategory category = entityManager.find(Threadcategory.class, categoryId);
//            if (category == null) {
//                throw new IllegalArgumentException("Category not found: " + categoryId);
//            }

            ThreadCategoryService threadCategoryService = new ThreadCategoryService(entityManager);
            Threadcategory category = threadCategoryService.findCategoryById(categoryId);

            // Create and save the thread
            ThreadService threadService = new ThreadService(entityManager);
            Thread newThread = new Thread();
            newThread.setThreadid(threadService.generateNextThreadId());
            newThread.setUserid(user);
            newThread.setThreadcategoryid(category);
            newThread.setThreadtitle(threadTitle);
            newThread.setThreaddescription(threadDescription);
            newThread.setUpvote(0);
            newThread.setDownvote(0);
            newThread.setSharecount(0);
            newThread.setIsdeleted(false);

            threadService.addThread(newThread);

            // Redirect to the thread list page
            response.sendRedirect(request.getContextPath() + "/ViewForumEntryServlet?threadType=my");

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Unable to create thread: " + e.getClass().getName()
                    + (e.getMessage() != null ? " - " + e.getMessage() : "");

            response.sendRedirect(request.getContextPath() + "/view/forum/forum-add-thread-form.jsp?error=" + errorMessage);
        }
    }
}
