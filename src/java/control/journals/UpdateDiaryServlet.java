package control.journals;

import model.Diaryentry;
import model.journalService.DiaryentryService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.io.IOException;

@WebServlet("/UpdateDiaryServlet")
public class UpdateDiaryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String diaryId = request.getParameter("id");
        String diaryTitle = request.getParameter("diarytitle");
        String description = request.getParameter("description");

        try {
            utx.begin();

            DiaryentryService diaryentryService = new DiaryentryService(em);
            Diaryentry diary = diaryentryService.findDiaryentryById(diaryId);

            if (diary != null) {
                diary.setDiarytitle(diaryTitle);
                diary.setDescription(description);
                em.merge(diary);
                utx.commit();

                request.getSession().setAttribute("message", "Diary entry updated successfully.");
                response.sendRedirect("ViewDiaryEntryDetailsServlet?diaryid=" + diaryId + "&mode=view");
            } else {
                utx.rollback();
                request.getSession().setAttribute("errorMessage", "Diary entry not found.");
                response.sendRedirect("error.jsp");
            }

        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An error occurred while updating the diary entry.");
            response.sendRedirect("error.jsp");
        }
    }
}
