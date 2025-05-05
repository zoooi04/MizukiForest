package control.forum;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import model.Threadcomment;
import model.forumService.ThreadCommentService;
import model.forumService.ThreadService;

/**
 *
 * @author johno
 */
@Transactional
public class DeleteForumThreadCommentServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract parameters from the request
        String commentId = request.getParameter("commentId");
        String threadId = request.getParameter("threadId");

        if ((commentId == null || commentId.isEmpty()) && (threadId == null || threadId.isEmpty())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        try {
            // Log variables for debugging
            System.out.println("Comment ID: " + commentId);
            System.out.println("Thread ID: " + threadId);

            // Initialize service classes
            ThreadCommentService commentService = new ThreadCommentService(entityManager);
            ThreadService threadService = new ThreadService(entityManager);

            if (commentId != null && !commentId.isEmpty()) {
                // Soft delete thread comment
                Threadcomment comment = entityManager.find(Threadcomment.class, commentId);
                System.out.println("Comment Object: " + comment);
                if (comment == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comment not found.");
                    return;
                }

                boolean isDeleted = commentService.deleteComment(commentId);
                if (!isDeleted) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete comment.");
                    return;
                }
            } else {
                // Soft delete thread
                model.Thread thread = entityManager.find(model.Thread.class, threadId);
                if (thread == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Thread not found.");
                    return;
                }

                boolean isDeleted = threadService.deleteThread(thread.getThreadid());
                if (!isDeleted) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete thread.");
                    return;
                }
            }

            // Redirect to forum entry page after successful deletion
            String redirectURL = "";
            if (commentId != null && !commentId.isEmpty()) {
                redirectURL = request.getContextPath() + "/ViewForumDetailServlet?thread_id=" + commentService.findThreadIdByCommentId(commentId);
            } else {
                redirectURL = request.getContextPath() + "/ViewForumEntryServlet";
            }

            response.sendRedirect(redirectURL);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting.");
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles soft deletion of forum threads and comments.";
    }
}
