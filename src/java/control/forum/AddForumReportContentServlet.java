/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.forum;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Reportcontent;
import model.forumService.ReportContentService;
import model.Users;
import model.Thread;
import model.Threadcomment;
import javax.transaction.Transactional;

/**
 *
 * @author johno
 */
@Transactional
public class AddForumReportContentServlet extends HttpServlet {

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
            out.println("<title>Servlet AddForumReportContentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddForumReportContentServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");

        // Extract parameters from the request
        String userId = request.getParameter("userId");
        String threadId = request.getParameter("threadId");
        String threadCommentId = request.getParameter("threadCommentId");
        String reportReason = request.getParameter("reportReason");

        // Log all parameters for debugging
        request.getParameterMap().forEach((key, value) -> {
            System.out.println("Parameter: " + key + " = " + String.join(", ", value));
        });

        if (userId == null || reportReason == null || userId.isEmpty() || reportReason.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        if (threadId == null || threadId.isEmpty()) {
            System.out.println("Error: Thread ID is missing in the request.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thread ID is required.");
            return;
        }

        try {
            // Initialize the service
            ReportContentService reportService = new ReportContentService(entityManager);

            // Create a new Reportcontent object
            Reportcontent report = new Reportcontent();
            report.setReportcontentid(reportService.generateNextReportId());
            report.setUserid(new Users(userId));
            report.setReportreason(reportReason);
            report.setIssolved(false);

            Thread thread = entityManager.find(Thread.class, threadId);
            if (thread == null) {
                System.out.println("Thread with ID " + threadId + " not found in the database.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid thread ID.");
                return;
            }
            report.setThreadid(thread);

            if (threadCommentId != null && !threadCommentId.isEmpty()) {
                Threadcomment threadComment = entityManager.find(Threadcomment.class, threadCommentId);
                if (threadComment == null) {
                    System.out.println("Thread comment with ID " + threadCommentId + " not found in the database.");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid thread comment ID.");
                    return;
                }
                report.setThreadcommentid(threadComment);
            } else {
                report.setThreadcommentid(null);
            }

            // Add the report to the database
            reportService.addReport(report);

            // Set a success message in the session
            request.getSession().setAttribute("reportSuccessMessage", "Report submitted successfully.");

            // Redirect to the thread detail page
            String redirectURL = request.getContextPath() + "/ViewForumDetailServlet?thread_id=" + threadId;
            response.sendRedirect(redirectURL);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while submitting the report.");
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
