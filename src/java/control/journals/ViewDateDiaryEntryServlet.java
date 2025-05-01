package control.journals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
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

            Users user = mgr.find(Users.class, "U2500005");
            session.setAttribute("currentUser", user);
            // if not enter back to login
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "../view/login.jsp");
                return;
            }

            DiaryentryService diaryService = new DiaryentryService(mgr);
            List<Diaryentry> allEntries = diaryService.findAllDiaryentries();

            Map<String, Diaryentry> latestDiaryEntries = new TreeMap<>(Collections.reverseOrder());

            for (Diaryentry entry : allEntries) {
                if (!entry.getIsdeleted() && entry.getUserid().equals(user)) {
                    String dateKey = new SimpleDateFormat("dd/MM/yyyy").format(entry.getDatewritten());

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
