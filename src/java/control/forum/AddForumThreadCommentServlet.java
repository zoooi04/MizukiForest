/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import model.Users;
import model.forumService.ThreadCommentService;
import model.Thread;

/**
 *
 * @author johno
 */
@Transactional
public class AddForumThreadCommentServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("This servlet handles adding comments to forum threads.");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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

        // Print all request parameters
        System.out.println("\nReceived comment request parameters:");
        System.out.println("userId: " + request.getParameter("userId"));
        System.out.println("threadId: " + request.getParameter("threadId"));
        System.out.println("commentDescription: " + request.getParameter("commentDescription"));
        System.out.println("commentIdReplyTo: " + request.getParameter("commentIdReplyTo"));

        // Extract parameters from the request
        String userId = request.getParameter("userId");
        String threadId = request.getParameter("threadId");
        String commentDescription = request.getParameter("commentDescription");
        String commentIdReplyTo = request.getParameter("commentIdReplyTo");

        if (userId == null || threadId == null || commentDescription == null || 
            userId.isEmpty() || threadId.isEmpty() || commentDescription.isEmpty()) {
            System.out.println("Error: Missing required parameters");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        try {
            // Fetch the Thread entity from the database
            Thread thread = entityManager.find(Thread.class, threadId);
            if (thread == null) {
                System.out.println("Error: Thread not found with ID: " + threadId);
                throw new IllegalArgumentException("Thread not found: " + threadId);
            }

            // Create and save the comment
            ThreadCommentService commentService = new ThreadCommentService(entityManager);
            Threadcomment comment = new Threadcomment();
            comment.setThreadcommentid(commentService.generateNextCommentId());
            comment.setThreadid(thread);
            comment.setUserid(entityManager.find(Users.class, userId));
            comment.setContent(commentDescription);
            comment.setCommentidreplyingto(commentIdReplyTo != null && !commentIdReplyTo.isEmpty() ? new Threadcomment(commentIdReplyTo) : null);
            comment.setPostdatetime(new java.util.Date());
            comment.setUpvote(0);
            comment.setDownvote(0);
            comment.setIsdeleted(false);

            commentService.addComment(comment);
            System.out.println("Successfully added comment with ID: " + comment.getThreadcommentid());

            // Redirect to the thread detail page
            String redirectURL = request.getContextPath() + "/ViewForumDetailServlet?thread_id=" + thread.getThreadid();
            response.sendRedirect(redirectURL);

        } catch (Exception e) {
            System.out.println("Error adding comment: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while adding the comment.");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This servlet handles adding comments to forum threads.";
    }// </editor-fold>

}
