package control.general;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Therapist;
import model.UserService.UserService;
import model.Users;
import model.therapyService.TherapistService;

@WebServlet(name = "LoginGetUserInfo", urlPatterns = {"/login"})
public class LoginGetUserInfo extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    UserTransaction utx;
    Query query;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserService us = new UserService(mgr);
        TherapistService ts = new TherapistService(mgr); // Therapist service

        String userEmail = request.getParameter("userEmail");
        String userPw = request.getParameter("userPw");

        try {
            Users user = us.findUserByEmail(userEmail);

            if (user != null && userPw.equals(user.getUserpw())) {
                session.setAttribute("user", user);

                if ("U2500011".equals(user.getUserid()) || "admin@example.com".equalsIgnoreCase(user.getUseremail())) {
                    session.setAttribute("role", "admin");
                } else {
                    session.setAttribute("role", "user");
                }

                // Birthday check
                LocalDate newBirthday = dateToLD(user.getUserbirthday());
                LocalDate today = LocalDate.now();

                if (newBirthday.getDayOfMonth() == today.getDayOfMonth()
                        && newBirthday.getMonthValue() == today.getMonthValue()) {
                    session.setAttribute("birthday", true);
                } else {
                    session.setAttribute("birthday", false);
                }

                response.sendRedirect(request.getContextPath() + "/GetUserForestData");
                return;
            }

            // If not user, try therapist
            Therapist therapist = ts.findTherapistByEmail(userEmail);

            if (therapist != null && userPw.equals(therapist.getTherapistpw())) {
                session.setAttribute("therapist", therapist);
                session.setAttribute("role", "therapist");

                response.sendRedirect(request.getContextPath() + "/LoadPatientServlet"); 
                return;
            }

            // If both fail
            session.setAttribute("errorMessage", "The email or password might be incorrect");
            request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
            session.removeAttribute("errorMessage");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorName", e.getClass().getName());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/general/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public LocalDate dateToLD(java.util.Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public java.util.Date ldToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
