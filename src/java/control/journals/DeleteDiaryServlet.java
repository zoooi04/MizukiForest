package control.journals;

import model.Diaryentry;
import model.journalService.DiaryentryService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
/**
 *
 * @author JiaQuann
 */
@WebServlet("/DeleteDiaryServlet")
public class DeleteDiaryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String diaryId = request.getParameter("diaryid");
        String date = request.getParameter("date");  // Get the date from the request

        try {
            utx.begin();

            DiaryentryService diaryentryService = new DiaryentryService(em);
            Diaryentry diary = diaryentryService.findDiaryentryById(diaryId);

            if (diary != null) {
                diary.setIsdeleted(true);
                em.merge(diary);
                utx.commit();
                request.getSession().setAttribute("message", "Diary entry deleted successfully.");
            } else {
                request.getSession().setAttribute("errorMessage", "Diary entry not found.");
            }

        } catch (Exception e) {
            try {
                if (utx != null) utx.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            request.getSession().setAttribute("errorMessage", e.getMessage());
            request.getSession().setAttribute("errorName", e.getClass().getName());
            e.printStackTrace();
        }

        // Redirect to the viewEntriesByDate page with the date parameter
        if (date != null && !date.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ViewDiaryEntriesByDateServlet?date=" + date);
        } else {
            response.sendRedirect(request.getContextPath() + "/ViewDiaryEntryServlet");
        }
    }
}
