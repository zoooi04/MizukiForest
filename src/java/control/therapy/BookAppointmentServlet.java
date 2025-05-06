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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Appointment;
import model.Therapist;
import model.Timeslot;
import model.Users;
import model.therapyService.AppointmentService;
import model.therapyService.TimeSlotService;

/**
 *
 * @author huaiern
 */
public class BookAppointmentServlet extends HttpServlet {
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

        
        
        TimeSlotService tss = new TimeSlotService(em,utx);
        Timeslot ts = tss.getTimeSlotById(slotid);
        LocalDate tsDate = convertDate(ts.getTsdate());
        LocalDate today = LocalDate.now();
        
        
        //check if user already have one existing appointment
        boolean found = false;
        AppointmentService as = new AppointmentService(em, utx);
        List<Appointment> appList = as.getAllAppointmentByUser(user);
        for(Appointment app: appList){
            if(!app.getIsdeleted()){
                found = true;
            } 
        }
        
        if(found){
            session.setAttribute("error","You already have existing booking");
        }else{
        
            if (!tsDate.isAfter(today)) {
                session.setAttribute("error", "Cannot have same day booking");
            } else {
                boolean success = as.bookAppointment(user, slotid);
                session.setAttribute("success", success);
            }
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
