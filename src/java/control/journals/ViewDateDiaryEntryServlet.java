package control.journals;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Diaryentry;
import model.journalService.DiaryentryService;
import model.Users;

/**
 *
 * @author JiaQuann
 */
public class ViewDateDiaryEntryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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

            DiaryentryService diaryService = new DiaryentryService(mgr);
            List<Diaryentry> allEntries = diaryService.findAllDiaryentries();

            Map<Date, Diaryentry> latestDiaryEntries = new TreeMap<>(Collections.reverseOrder());

            for (Diaryentry entry : allEntries) {
                if (!entry.getIsdeleted() && entry.getUserid().getUserid().equals(user.getUserid())) {
                    Date dateKey = entry.getDatewritten(); // Use Date as key
                    // Get the latest entry for the same date
                    if (!latestDiaryEntries.containsKey(dateKey)
                            || latestDiaryEntries.get(dateKey).getDatewritten().before(entry.getDatewritten())) {
                        latestDiaryEntries.put(dateKey, entry);
                    }
                }
            }
            session.setAttribute("diaryEntries", latestDiaryEntries.values());
            response.sendRedirect(request.getContextPath() + "/view/journals/journal-diaries.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
