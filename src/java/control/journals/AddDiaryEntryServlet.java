package control.journals;

import model.Diaryentry;
import model.journalService.DiaryentryService;
import model.Users;
import model.Diarytag;
import model.journalService.DiarytagService;
import model.Tag;
import model.journalService.TagService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddDiaryEntryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            // Get form parameters
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String mood = request.getParameter("mood");
            String dateStr = request.getParameter("datewritten");
            String entryTags = request.getParameter("entryTags");

            // Convert date string to Date object
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateWritten = sdf.parse(dateStr);

            // Find user (replace with actual session user in production)
            Users user = em.find(Users.class, "U2500005");

            // Begin transaction
            utx.begin();

            // Create diary entry
            DiaryentryService diaryentryService = new DiaryentryService(em);
            String nextDiaryId = generateNextDiaryId();
            Diaryentry diary = new Diaryentry(nextDiaryId, dateWritten, false, false);
            diary.setDiarytitle(title);
            diary.setDescription(description);
            diary.setMood(mood);
            diary.setUserid(user);
            diaryentryService.addDiaryentry(diary);

            // Handle tags
            if (entryTags != null && !entryTags.trim().isEmpty()) {
                String[] tagNames = entryTags.split(",");
                TagService tagService = new TagService(em);
                DiarytagService diarytagService = new DiarytagService(em);

                for (String tagName : tagNames) {
                    tagName = tagName.trim();
                    if (!tagName.isEmpty()) {
                        List<Tag> foundTags = tagService.findTagsByTagname(tagName);
                        if (!foundTags.isEmpty()) {
                            Tag tag = foundTags.get(0);

                            // Create DiarytagPK using the nextDiaryId and tag information
                            String nextUserTagId = generateNextUserTagId(); // Generate next user tag ID (implement this if needed)

                        }
                    }
                }
            }

            // Commit transaction
            utx.commit();
            session.setAttribute("message", "Diary entry added successfully!");

        } catch (Exception e) {
            try {
                if (utx != null) utx.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }

            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorName", e.getClass().getName());
            e.printStackTrace();
        }

        // Forward back to diary add page
        request.getRequestDispatcher("/AddingDiaryPageServlet").forward(request, response);
    }

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

    private String generateNextUserTagId() {
        // Implement logic to generate the next unique user tag ID (if needed)
        return "U0000001"; // Placeholder
    }

    private String generateNextDiaryTagId() {
        try {
            String lastId = em.createQuery(
                    "SELECT d.diarytagid FROM Diarytag d ORDER BY d.diarytagid DESC", String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(2));
            return String.format("DT%07d", number + 1);
        } catch (Exception e) {
            return "DT0000001";
        }
    }
}
