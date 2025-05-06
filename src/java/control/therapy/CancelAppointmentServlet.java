/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Timeslot;
import model.Users;
import model.therapyService.AppointmentService;
import model.therapyService.TimeSlotService;

/**
 *
 * @author huaiern
 */
public class CancelAppointmentServlet extends HttpServlet {
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Users user = (Users) session.getAttribute("user");
        String slotid = (String) request.getParameter("slotid");
        TimeSlotService tss = new TimeSlotService(em, utx);
        Timeslot ts = tss.getTimeSlotById(slotid);
        Date date = ts.getTsdate();
        
        LocalDateTime appointmentTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime currentTime = LocalDateTime.now();
        
        // Calculate the duration between the appointment time and the current time
        Duration duration = Duration.between(currentTime, appointmentTime);
        
        if (duration.toHours() >= 24) {
        
        
            AppointmentService as = new AppointmentService(em,utx);
            boolean isCancel = as.cancelAppointment(user, slotid);

            if (isCancel) {
                System.out.println("IT IS CANCELLED");
            }
            session.setAttribute("isCancel", isCancel);
        }else{
            session.setAttribute("error", "You can only cancel an appointment 24 hours in advance.");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String redirectUrl = request.getContextPath() + "/LoadScheduleServlet";
        String jsonResponse = "{\"redirectUrl\": \"" + redirectUrl + "\"}";

        response.getWriter().write(jsonResponse);

    }
    
    
    public LocalDate convertDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    


}
