/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Appointment;
import model.Message;
import model.Therapist;
import model.Users;
import model.Usertherapist;
import model.therapyService.AppointmentService;
import model.therapyService.MessageService;
import model.therapyService.TherapistService;
import model.therapyService.UserTherapistService;

/**
 *
 * @author huaiern
 */
public class ChangeTherapistServlet extends HttpServlet {
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String therapistId = (String) request.getParameter("therapistid");
        Users user = (Users) session.getAttribute("user");
        
        
        UserTherapistService uts = new UserTherapistService(em, utx);
        Usertherapist ut = uts.getUserTherapist(user);
        Date date = ut.getDateestablished();
        LocalDate startDate = convertDate(date);
        LocalDate today = LocalDate.now();
        if (startDate.plusDays(14).isAfter(today)) {
            // Not enough days passed
            session.setAttribute("error", "You must wait at least 14 days before changing therapist.");
            response.sendRedirect(request.getContextPath() + "/view/therapy/changeTherapist.jsp");
            return;
        }
        
        
        AppointmentService as = new AppointmentService(em, utx);
        List<Appointment> appList = as.getAllAppointmentByUser(user);
        
        boolean found = false;
        for(Appointment app: appList){
            //if he got one that is not deleted/active appointment
            if(!app.getIsdeleted()){
                found=true;
                break;
            }
        }
        
        if(found){
            session.setAttribute("hasApp", true);
            System.out.println("HERE: FOUND APPOINTMENT CANNOT CHANGE");
            response.sendRedirect(request.getContextPath() + "/view/therapy/changeTherapist.jsp");
            return;
        }
        
        
        
        

        
        
        boolean success = uts.changeTherapist(therapistId, user);
        

        session.setAttribute("success", success);


        TherapistService ts = new TherapistService(em,utx);
        Therapist therapist = ts.getTherapistById(therapistId);
        session.setAttribute("therapist", therapist);

        List<Usertherapist> userTherapistList = (List<Usertherapist>) uts.getAllUserTherapistByUser(user);
        session.setAttribute("userTherapistList", userTherapistList);

        MessageService ms = new MessageService(em, utx);
        List<Message> messageList = ms.getAllMessageByUser(user);
        session.setAttribute("messageList", messageList);

        response.sendRedirect(request.getContextPath() + "/view/therapy/changeTherapist.jsp");

    }
    
    public LocalDate convertDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


}
