package control.journals;

import java.io.IOException;
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
import model.Users;
/**
 *
 * @author JiaQuann
 */
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

            // Get the logged-in user from session
            Users user = (Users) session.getAttribute("user");

            // If user is not logged in, redirect to homepage
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            // Set the current user in session (optional, only if needed)
            session.setAttribute("currentUser", user);

            // Forward to diary entry page
            request.getRequestDispatcher("/view/journals/journal-diary-add.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/view/journals/journal-diary-add.jsp").forward(request, response);
        }
    }
}
