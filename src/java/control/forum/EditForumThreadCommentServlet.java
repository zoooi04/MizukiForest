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
import model.Threadcategory;
import model.Threadcomment;
import model.forumService.ThreadCommentService;
import model.forumService.ThreadService;

/**
 *
 * @author johno
 */
@Transactional
public class EditForumThreadCommentServlet extends HttpServlet {

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
        try (java.io.PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditForumThreadCommentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditForumThreadCommentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        // Extract parameters from the request
        String commentId = request.getParameter("commentId");
        String newDescription = request.getParameter("description");
        String threadTitle = request.getParameter("title");
        String threadCategory = request.getParameter("category");
        String threadId = request.getParameter("threadId");

        if ((commentId == null || commentId.isEmpty()) && (threadId == null || threadId.isEmpty())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        try {
            // Log variables for debugging
            System.out.println("Comment ID: " + commentId);
            System.out.println("New Description: " + newDescription);
            System.out.println("Thread Title: " + threadTitle);
            System.out.println("Thread Category: " + threadCategory);
            System.out.println("Thread ID: " + threadId);

            // Initialize service classes
            ThreadCommentService commentService = new ThreadCommentService(entityManager);
            ThreadService threadService = new ThreadService(entityManager);

            if (commentId != null && !commentId.isEmpty()) {
                // Update thread comment
                Threadcomment comment = entityManager.find(Threadcomment.class, commentId);
                System.out.println("Comment Object: " + comment);
                if (comment == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comment not found.");
                    return;
                }

                commentService.updateComment(commentId, newDescription);
            } else {
                // Update thread details
                model.Thread thread = entityManager.find(model.Thread.class, threadId);
                if (thread == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Thread not found.");
                    return;
                }

                if (threadTitle != null && !threadTitle.isEmpty()) {
                    thread.setThreadtitle(threadTitle);
                }

                if (threadCategory != null && !threadCategory.isEmpty()) {
                    Threadcategory category = entityManager.find(Threadcategory.class, threadCategory);
                    if (category != null) {
                        thread.setThreadcategoryid(category);
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid thread category.");
                        return;
                    }
                }

                if (newDescription != null && !newDescription.isEmpty()) {
                    thread.setThreaddescription(newDescription);
                }

                // Log the updated thread for debugging
                System.out.println("Updated Thread: " + thread);

                // Use the service to update the thread
                boolean isUpdated = threadService.updateThread(thread);
                if (!isUpdated) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update thread.");
                    return;
                }
            }

            // Send success response
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write("Update successful.");
            
            // redirect
            String redirectURL = request.getContextPath() + "/ViewForumDetailServlet?thread_id=" + threadId;
            response.sendRedirect(redirectURL);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating.");
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
        return "Short description";
    }// </editor-fold>

}
