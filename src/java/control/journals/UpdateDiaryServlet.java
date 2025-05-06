package control.journals;

import model.Diaryentry;
import model.Users;
import model.journalService.DiaryentryService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import java.io.IOException;

/**
 *
 * @author JiaQuann
 */
@WebServlet("/UpdateDiaryServlet")
public class UpdateDiaryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("isSearch") != null) {
            session.removeAttribute("isSearch");
        }
        
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // Get request parameters
        String diaryId = request.getParameter("id");
        String diaryTitle = request.getParameter("diarytitle");
        String description = request.getParameter("description");

        try {
            utx.begin();

            // Find diary entry by ID
            DiaryentryService diaryentryService = new DiaryentryService(em);
            Diaryentry diary = diaryentryService.findDiaryentryById(diaryId);

            if (diary != null) {
                // Update diary entry fields
                diary.setDiarytitle(diaryTitle);
                diary.setDescription(description);

                em.merge(diary);
                utx.commit();

                // Success message and redirect
                session.setAttribute("message", "Diary entry updated successfully.");
                response.sendRedirect("ViewDiaryEntryDetailsServlet?diaryid=" + diaryId + "&mode=view");
            } else {
                utx.rollback();
                session.setAttribute("errorMessage", "Diary entry not found.");
                response.sendRedirect("error.jsp");
            }

        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }

            ex.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred while updating the diary entry.");
            response.sendRedirect("error.jsp");
        }
    }
}
