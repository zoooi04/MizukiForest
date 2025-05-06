/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

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
import model.Message;
import model.therapyService.MessageService;
import model.Therapist;
import model.UserService.UserService;
import model.therapyService.TherapistService;
import model.therapyService.UserTherapistService;
import model.Users;
import model.Usertherapist;

/**
 *
 * @author huaiern
 */
public class LoadTherapistServlet extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("role", "user");

        //mock user
        UserService us = new UserService(em);
//        Users user = us.findUserById("U2500002");
//        Users user = new Users("U2500001", "myson", "jakarta123", "banana1", java.sql.Date.valueOf("2001-03-06"), 722, 293, 1, 5, true, false, false);
        Users user = (Users) session.getAttribute("user");

//        session.setAttribute("user", user);
        //check if user already assigned to a therapist
        UserTherapistService uts = new UserTherapistService(em, utx);
        Usertherapist ut = uts.getUserTherapist(user);

        TherapistService ts = new TherapistService(em, utx);
        List<Therapist> therapistList = ts.getAllTherapist();
        session.setAttribute("therapistList", therapistList);

        if (ut == null) {
            response.sendRedirect(request.getContextPath() + "/view/therapy/therapistCatalogue.jsp");
        } else {
            Therapist therapist = ts.getCurrentTherapistByUser(user);
            session.setAttribute("therapist", therapist);

            List<Usertherapist> userTherapistList = (List<Usertherapist>) uts.getAllUserTherapistByUser(user);
            session.setAttribute("userTherapistList", userTherapistList);

            MessageService ms = new MessageService(em, utx);
            List<Message> messageList = ms.getAllMessageByUser(user);
            session.setAttribute("messageList", messageList);

            response.sendRedirect(request.getContextPath() + "/view/therapy/therapistRoom.jsp");
        }

    }

}
