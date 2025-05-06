package control.general;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */

@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class logout extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Do not create a new session if none exists

        if (session != null) {
            session.invalidate(); // Invalidate the current session
        }
        
        //remove all cookie when logout
        //new guest have to start on fresh cart
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            cookie.setMaxAge(0);
            cookie.setValue(null);
            response.addCookie(cookie);
        }
        
        // Redirect the user to a logout confirmation page or the login page
        session = request.getSession();
        session.setAttribute("logoutMessage", "You have successfully logged out.");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

}
