package control.forum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import model.forumService.ThreadService;

@Transactional
public class ViewForumReportedContentServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        ReportContentService reportContentService = new ReportContentService(entityManager);
        List<Reportcontent> reportedThreadList = reportContentService.findAllUnsolveReportedThread();
        List<Reportcontent> reportedCommentList = reportContentService.findAllUnsolveReportedComment();

        session.setAttribute("reportedThreadList", reportedThreadList);
        session.setAttribute("reportedCommentList", reportedCommentList);

        String redirectURL = request.getContextPath() + "/view/forum/forum-reported-content.jsp";
        response.sendRedirect(redirectURL);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
