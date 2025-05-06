package control.journals;

import model.Diaryentry;
import model.journalService.DiaryentryService;
import model.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ViewDiaryEntryDetailsServlet")
public class ViewDiaryEntryDetailsServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("isSearch") != null) {
            session.removeAttribute("isSearch");
        }

        Users user = (Users) session.getAttribute("user");
        // if not enter back to login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String diaryId = request.getParameter("diaryid");

        if (diaryId != null && !diaryId.isEmpty()) {
            try {
                DiaryentryService diaryService = new DiaryentryService(mgr);
                Diaryentry diaryEntry = diaryService.findDiaryentryById(diaryId);

                if (diaryEntry != null) {
                    if (diaryEntry.getUserid().equals(user)) {
                        request.setAttribute("diaryEntry", diaryEntry);
                    } else {
                        request.setAttribute("errorMessage", "You do not have permission to view this diary entry.");
                    }
                } else {
                    request.setAttribute("errorMessage", "Diary entry not found.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid diary ID format.");
            }
        } else {
            request.setAttribute("errorMessage", "Diary ID parameter is missing.");
        }

        request.getRequestDispatcher("view/journals/journal-diary-view.jsp").forward(request, response);
    }

}
