package control.forum;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Threadcategory;
import model.Users;
import model.forumService.ThreadCategoryService;
import model.forumService.ThreadService;

public class ViewForumEntryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();

            // Fetch current user (adjust as necessary for your login/auth system)
            Users user = mgr.find(Users.class, "U2500001");
            session.setAttribute("currentUser", user);

            // If user is not found, redirect to login
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "../view/login.jsp");
                return;
            }

            // Fetch all categories
            ThreadCategoryService threadCategoryService = new ThreadCategoryService(mgr);
            List<Threadcategory> threadCategoryList = threadCategoryService.getAllCategories();

            // Handle missing parameters by setting defaults
            String category = request.getParameter("category");
            if (category == null || category.isEmpty()) {
                category = "all"; // Default category
            }

            String vote = request.getParameter("vote");
            if (vote == null || vote.isEmpty()) {
                vote = "all"; // Default vote filter
            }

            // Get threadType parameter
            String threadType = request.getParameter("threadType");
            if (threadType == null || threadType.isEmpty()) {
                if(session.getAttribute("threadType") == null){
                    threadType = "all"; // Default to all threads
                    session.setAttribute("threadType", threadType);
                }else{
                    threadType = (String) session.getAttribute("threadType");
                }
            }

            // Use ThreadService to fetch filtered threads
            ThreadService threadService = new ThreadService(mgr);
            List<model.Thread> threadList;
            if ("my".equals(threadType)) {
                threadList = threadService.findThreadsByUserAndFilters(user.getUserid(), category, vote);
            } else {
                threadList = threadService.findAllThreads(category, vote);
            }

            // Set filtered thread list and category list in session
            session.setAttribute("forumThreadList", threadList);
            session.setAttribute("forumThreadCategoryList", threadCategoryList);

            // Store the selected category, vote, and threadType in the session
            session.setAttribute("selectedCategory", category);
            session.setAttribute("selectedVote", vote);
            session.setAttribute("threadType", threadType);

            // Pass the selected category and vote back to the JSP
            request.setAttribute("selectedCategory", category);
            request.setAttribute("selectedVote", vote);

            String redirectURL = request.getContextPath() + "/view/forum/forum-thread-list.jsp?threadType=" + threadType + "&category=" + category + "&vote=" + vote;
            response.sendRedirect(redirectURL);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
