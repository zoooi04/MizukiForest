/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import model.Reportcontent;
import model.forumService.ReportContentService;
import model.forumService.ThreadCommentService;
import model.forumService.ThreadService;

/**
 *
 * @author johno
 */
@Transactional
public class UpdateForumReportedContentServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateForumReportedContentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateForumReportedContentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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

        String contentType = request.getParameter("contentType");
        String reportedContentId = request.getParameter("reportedContentId");

        String action = request.getParameter("action");

        if (contentType.equals("comment")) {
            String commentUser = request.getParameter("commentUser");
            String commentContent = request.getParameter("commentContent");
        }

        ReportContentService reportContentService = new ReportContentService(entityManager);
        Reportcontent reportedContent = reportContentService.findReportById(reportedContentId);
        ThreadService threadService = new ThreadService(entityManager);
        ThreadCommentService threadCommentService = new ThreadCommentService(entityManager);

        if (action.equals("normal")) {
            reportContentService.markReportAsSolved(reportedContentId);
        } else if (action.equals("delete")) {
            if (contentType.equals("thread")) {
                threadService.deleteThread(reportedContent.getThreadid().getThreadid());
            } else if (contentType.equals("comment")) {
                threadCommentService.deleteComment(reportedContent.getThreadcommentid().getThreadcommentid());
            }
            reportContentService.markReportAsSolved(reportedContentId);
        }

        String redirectURL = request.getContextPath() + "/ViewForumReportedContentServlet";
        response.sendRedirect(redirectURL);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
