/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Message;
import model.Therapist;
import model.Usertherapist;
import model.therapyService.MessageService;
import model.therapyService.UserTherapistService;

/**
 *
 * @author huaiern
 */
public class LoadPatientServlet extends HttpServlet {
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("role", "therapist");
        

        Therapist therapist = new Therapist("TP000001", "Dr. Aiman Zulkifli", "P@ssw0rd123", "aiman.zulkifli@therapymsia.com", 
                "Depression and anxiety, Emotion regulation, Self-exploration, Self-worth and esteem, Work and study stress",
                "English, Malay", 6, "Muslim", true, false);
        session.setAttribute("therapist", therapist);

        
        UserTherapistService uts = new UserTherapistService(em, utx);
        List<Usertherapist> userTherapistList = (List<Usertherapist>) uts.getAllUserTherapistByTherapist(therapist);

        session.setAttribute("userTherapistList", userTherapistList);
        
        
        MessageService ms = new MessageService(em, utx);
        List<Message> messageList = ms.getAllMessageByTherapist(therapist);
        session.setAttribute("messageList", messageList);
        
        response.sendRedirect(request.getContextPath() + "/view/therapy/trTherapistRoom.jsp");
        
    }


}
