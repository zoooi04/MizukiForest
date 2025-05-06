package control.journals;

import model.Diaryentry;
import model.Users;
import model.journalService.DiaryentryService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.transaction.UserTransaction;
import java.io.IOException;

/**
 *
 * @author JiaQuann
 */
@WebServlet("/ToggleArchiveServlet")
public class ToggleArchiveServlet extends HttpServlet {

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
            // Redirect to login or index if no user is logged in
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String diaryId = request.getParameter("diaryid");

        try {
            utx.begin();

            // Find the diary entry by ID
            DiaryentryService diaryentryService = new DiaryentryService(em);
            Diaryentry diary = diaryentryService.findDiaryentryById(diaryId);

            if (diary != null) {
                // Toggle archive status
                Boolean currentStatus = diary.getIsarchived();
                diary.setIsarchived(!currentStatus);

                em.merge(diary);
                utx.commit();

                // Set success message and redirect to detail view
                session.setAttribute("message", "Archive status updated.");
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
            session.setAttribute("errorMessage", "An error occurred while updating archive status.");
            response.sendRedirect("error.jsp");
        }
    }
}
