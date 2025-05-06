package control.journals;

import model.Diaryentry;
import model.Users;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddDiaryEntryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    private String generateNextDiaryId() {
        try {
            String lastId = em.createQuery(
                    "SELECT d.diaryid FROM Diaryentry d ORDER BY d.diaryid DESC", String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(1));
            return String.format("D%07d", number + 1);
        } catch (Exception e) {
            return "D0000001";
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String diaryId = generateNextDiaryId();
        String title = request.getParameter("title");
        String mood = request.getParameter("mood");
        String description = request.getParameter("description");
        String dateStr = request.getParameter("datewritten");

        try {
            utx.begin();

            DiaryentryService diaryentryService = new DiaryentryService(em);
            Diaryentry diary = new Diaryentry();

            diary.setDiaryid(diaryId);
            diary.setDiarytitle(title);
            diary.setMood(mood);
            diary.setDescription(description);

            // Parse date string to java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateWritten = sdf.parse(dateStr);
            diary.setDatewritten(dateWritten);
            diary.setIsarchived(false);
            diary.setIsdeleted(false);

            // Assume Users object is stored in session after login
            Users currentUser = (Users) request.getSession().getAttribute("user");
            diary.setUserid(currentUser);

            diaryentryService.addDiaryentry(diary);

            utx.commit();

            request.getSession().setAttribute("message", "Diary entry added successfully.");

        } catch (Exception e) {
            try {
                if (utx != null) {
                    utx.rollback();
                }
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            request.getSession().setAttribute("errorMessage", e.getMessage());
            request.getSession().setAttribute("errorName", e.getClass().getName());
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/AddingDiaryPageServlet");
    }
}

