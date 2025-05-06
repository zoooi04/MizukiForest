/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import model.Therapist;
import model.therapyService.TimeSlotService;

/**
 *
 * @author huaiern
 */
public class AddTimeSlotServlet extends HttpServlet {
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Therapist therapist = (Therapist) session.getAttribute("therapist");
            
            // Get raw values from form
            String dateStr = request.getParameter("date");              // e.g., "2025-05-04"
            String startHourStr = request.getParameter("starttime");   // e.g., "9" or "13"
            String durationStr = request.getParameter("hour");         // e.g., "2"

            System.out.println("HIHIHI" + dateStr);
            System.out.println(startHourStr);
            System.out.println(durationStr);
            int duration = Integer.parseInt(durationStr);

// Parse the date and time
            LocalDate localDate = LocalDate.parse(dateStr);
            LocalTime startTimeParsed = LocalTime.parse(startHourStr); // Parse "HH:mm" format

// Combine to get LocalDateTime
            LocalDateTime startDateTime = LocalDateTime.of(localDate, startTimeParsed);
            LocalDateTime endDateTime = startDateTime.plusHours(duration);

// Convert to java.util.Date
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date startTime = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Date endTime = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());
            
            TimeSlotService tss = new TimeSlotService(em,utx);
            
            boolean isExist = tss.checkExistingTimeSlot(therapist, date, startTime, endTime);
            if(isExist){
                session.setAttribute("isExist", isExist);
                response.sendRedirect(request.getContextPath() + "/LoadScheduleServlet");
                return;
            }

            
            boolean success = tss.addAvailableSlot(therapist, date, startTime, endTime);
            session.setAttribute("success", success);
            response.sendRedirect(request.getContextPath() + "/LoadScheduleServlet");
               
            
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
