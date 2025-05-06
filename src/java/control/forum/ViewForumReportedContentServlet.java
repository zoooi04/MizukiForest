package control.forum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import model.Reportcontent;
import model.Thread;
import model.Threadcomment;
import model.forumService.ReportContentService;

@Transactional
public class ViewForumReportedContentServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ReportContentService reportService = new ReportContentService(em);
            List<Reportcontent> unsolvedReports = reportService.findUnsolvedReports();

            // Separate reports into threads and comments
            List<Reportcontent> threadReports = new ArrayList<>();
            List<Reportcontent> commentReports = new ArrayList<>();

            for (Reportcontent report : unsolvedReports) {
                if (report.getThreadid() != null && report.getThreadcommentid() == null) {
                    threadReports.add(report);
                } else if (report.getThreadcommentid() != null) {
                    commentReports.add(report);
                }
            }

            // Set attributes for the JSP
            HttpSession session = request.getSession();
            session.setAttribute("reportedThreads", threadReports);
            session.setAttribute("reportedComments", commentReports);

            // Forward to the JSP page
            String redirectURL = request.getContextPath() + "/view/forum/forum-reported-content.jsp";
            response.sendRedirect(redirectURL);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error retrieving reported content: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
