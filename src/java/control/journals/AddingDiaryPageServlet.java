package control.journals;

import java.io.IOException;
import java.util.List;
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
import model.Tag;
import model.journalService.TagService;
import model.Users;
import model.Usertag;
import model.journalService.UsertagService;

@WebServlet(name = "AddingDiaryPageServlet", urlPatterns = {"/AddingDiaryPageServlet"})
public class AddingDiaryPageServlet extends HttpServlet {
    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            
            
            Users user = em.find(Users.class, "U2500005");
            session.setAttribute("currentUser", user);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            TagService tagService = new TagService(em);
            List<Tag> allTags = tagService.findTagsByIsDeleted(false);
            request.setAttribute("allTags", allTags);

            UsertagService usertagService = new UsertagService(em);
            List<Usertag> userTags = usertagService.findUsertagsByIsDeletedAndUserId(false, user);
            request.setAttribute("userTags", userTags);

            request.getRequestDispatcher("/view/journals/journal-diary-add.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/journals/journal-diary-add.jsp").forward(request, response);
        }
    }
}
