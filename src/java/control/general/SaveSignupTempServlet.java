package control.general;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Users;
import model.UserService.UserService;

/**
 *
 * @author JiaQuann
 */
@WebServlet(name = "SaveSignupTempServlet", urlPatterns = {"/saveSignupTemp"})
public class SaveSignupTempServlet extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuilder errorMessage = new StringBuilder();
        UserService us = new UserService(mgr);

        String email = request.getParameter("userEmail");
        String password = request.getParameter("userPw");
        String confirmPw = request.getParameter("confirmPw");

        Users validEmail = us.findUserByEmail(email);

        if (!password.equals(confirmPw)) {
            response.sendRedirect("signup.jsp?error=PasswordMismatch");
            return;
        }

        if (validEmail == null) {
            try {

                // Save into session temporarily
                HttpSession session = request.getSession();
                session.setAttribute("tempEmail", email);
                session.setAttribute("tempPassword", confirmPw);
                session.setAttribute("signedUp", true);

                response.sendRedirect(request.getContextPath() + "/view/general/welcome.jsp");
            } catch (Exception ex) {
                errorMessage.append(ex.getMessage() + "\n");

            }
        } else {
            errorMessage.append("Email is already being used by another user\n");
            response.sendRedirect(request.getContextPath() + "/view/general/signup.jsp?error=EmailExists");
        }
    }
}
