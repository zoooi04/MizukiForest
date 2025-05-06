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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/viewEntriesByDate")
public class ViewDiaryEntriesByDateServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("isSearch") != null) {
            session.removeAttribute("isSearch");
        }
        
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String dateParam = request.getParameter("date");

        if (dateParam != null && !dateParam.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                Date date = dateFormat.parse(dateParam);
                String formattedDate = dateFormat.format(date);

                DiaryentryService diaryService = new DiaryentryService(mgr);
                List<Diaryentry> entries = diaryService.findDiaryEntriesByDate(date);

                request.setAttribute("entryList", entries);
                request.setAttribute("selectedDate", formattedDate);

            } catch (ParseException e) {
                request.setAttribute("errorMessage", "Invalid date format. Please use yyyy-MM-dd.");
                request.setAttribute("selectedDate", dateParam);
            }
        } else {
            request.setAttribute("errorMessage", "Date parameter is missing.");
            request.setAttribute("selectedDate", null);
        }

        request.getRequestDispatcher("view/journals/journal-diary-content.jsp").forward(request, response);
    }
}
