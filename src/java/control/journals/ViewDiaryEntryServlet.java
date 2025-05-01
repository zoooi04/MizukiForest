package control.journals;

import model.Diaryentry;
import model.journalService.DiaryentryService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewDiaryEntryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("isSearch") != null) {
                session.removeAttribute("isSearch");
            }
            
            DiaryentryService diaryService = new DiaryentryService(mgr);
            List<Diaryentry> allEntries = diaryService.findAllDiaryentries();
            
            List<Diaryentry> activeDiaryEntries = new ArrayList<>();
            for (Diaryentry entry : allEntries) {
                if (!entry.getIsdeleted()) { // Only add entries that are not deleted
                    activeDiaryEntries.add(entry);
                }
            }
            
            session.setAttribute("diaryEntries", activeDiaryEntries);
            response.sendRedirect(request.getContextPath() + "/view/journals/journal-qotd.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
