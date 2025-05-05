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
import model.forumService.ThreadCommentService;

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

        if (commentId == null || commentId.isEmpty() || newDescription == null || newDescription.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        try {
            // Log variables for debugging
            System.out.println("Comment ID: " + commentId);
            System.out.println("New Description: " + newDescription);

            // Initialize service class
            ThreadCommentService commentService = new ThreadCommentService(entityManager);

            // Check if the comment exists
            model.Threadcomment comment = entityManager.find(model.Threadcomment.class, commentId);
            System.out.println("Comment Object: " + comment);
            if (comment == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comment not found.");
                return;
            }

            // Update the description for the comment
            commentService.updateComment(commentId, newDescription);

            // Send success response
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write("Comment updated successfully.");

            // redirect
            String redirectURL = request.getContextPath() + "/ViewForumDetailServlet?thread_id=" + commentService.findThreadIdByCommentId(commentId);
            response.sendRedirect(redirectURL);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the comment.");
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
