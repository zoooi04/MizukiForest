package control.general;

import model.Users;
import model.UserService.UserService;

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
import java.util.Collection;
import java.util.List;
import model.Userbadge;

@WebServlet("/getUserProfileDetails")
public class getUserProfileDetails extends HttpServlet {

    @PersistenceContext
    private EntityManager entityManager;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(entityManager);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user != null) {
            String userId = user.getUserid();
            Users userDetails = userService.findUserById(userId);

            if (userDetails != null) {
                request.setAttribute("userDetails", userDetails);

                List<Userbadge> userBadges = new ArrayList<>(userDetails.getUserbadgeCollection());
                request.setAttribute("userBadges", userBadges);

                request.getRequestDispatcher("/view/general/profile.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/view/general/login.jsp");
        }
    }
}
