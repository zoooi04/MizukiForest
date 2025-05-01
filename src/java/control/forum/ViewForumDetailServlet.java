package control.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Threadcomment;
import model.Users;
import model.forumService.ThreadCommentService;
import model.forumService.ThreadService;

//@WebServlet("/ViewForumDetailServlet")
public class ViewForumDetailServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Users user = mgr.find(Users.class, "U2500001");
        Users currentUser = (Users) session.getAttribute("currentUser");

        String ThreadIdParam = request.getParameter("thread_id");

        if (ThreadIdParam != null && !ThreadIdParam.isEmpty()) {
            try {
                ThreadService threadService = new ThreadService(mgr);
                model.Thread thread = threadService.findThreadById(ThreadIdParam);

                ThreadCommentService threadCommentService = new ThreadCommentService(mgr);
                List<Threadcomment> threadCommentList = threadCommentService.findCommentsByThreadId(ThreadIdParam);

                request.setAttribute("selectedThread", thread);
                request.setAttribute("selectedThreadCommentList", threadCommentList);

            } catch (Exception e) {
                request.setAttribute("errorMessage", "Invalid thread id. Please try again.");
                request.setAttribute("selectedThreadID", ThreadIdParam);
            }
        } else {
            request.setAttribute("errorMessage", "Thread id parameter is missing.");
            request.setAttribute("selectedThreadID", null);
        }

        String redirectURL = request.getContextPath() + "/view/forum/forum-thread-detail.jsp?thread_id=" + ThreadIdParam;
        response.sendRedirect(redirectURL);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
