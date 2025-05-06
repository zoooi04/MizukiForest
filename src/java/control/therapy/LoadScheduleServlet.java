/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class LoadScheduleServlet extends HttpServlet {
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        
        TimeSlotService tss = new TimeSlotService(em, utx);
        
        // Get logged in therapist or user therapist
        Therapist therapist = (Therapist) session.getAttribute("therapist");
        List<Timeslot> timeSlotList = tss.getAllTimeSlotByTherapist(therapist);


        Map<LocalDate, List<Timeslot>> fullWeekMap = getAllTimeslotsGroupedByWeek(timeSlotList);

        // Limit to current week + next 3 weeks (total 4)
        Map<LocalDate, List<Timeslot>> limitedWeekMap = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate thisMonday = today.with(DayOfWeek.MONDAY);

        System.out.println("limitedWeekMap here" + fullWeekMap);

        for (int i = 0; i < 4; i++) {
            LocalDate weekStart = thisMonday.plusWeeks(i);
            List<Timeslot> slots = fullWeekMap.getOrDefault(weekStart, new ArrayList<>());
            limitedWeekMap.put(weekStart, slots);
        }
        
        session.setAttribute("weekMap", limitedWeekMap);
        request.setAttribute("currentWeekIndex", 0); // Default to first week
        
        if(role.equalsIgnoreCase("therapist")){
            AppointmentService as = new AppointmentService(em, utx);
            List<Appointment> appList = as.getAllAppointment();
            Map<String, Users> appMap = new HashMap<>();
            
            for (Appointment app : appList) {
                if (!app.getIsdeleted()) {
                    Users user = app.getUserid();
                    System.out.println(appMap);

                    appMap.put(app.getTimeslotid().getTimeslotid(), user);
                }
            }
            System.out.println("APP MAP here" + appMap);

            
            session.setAttribute("appMap", appMap);
            response.sendRedirect(request.getContextPath() + "/view/therapy/therapistModifySchedule.jsp");

            
        }else if(role.equalsIgnoreCase("user")){
            Users user = (Users) session.getAttribute("user");
            
            AppointmentService as = new AppointmentService(em, utx);
            List<Appointment> appList = as.getAllAppointmentByUser(user);
            Map<String, Appointment> appMap = new HashMap<>(); 

            for(Appointment app: appList){
                if(!app.getIsdeleted()){
                    appMap.put(app.getTimeslotid().getTimeslotid(), app);
                }
            }
            
            
            session.setAttribute("appMap",appMap);
            response.sendRedirect(request.getContextPath() + "/view/therapy/bookSession.jsp");
        }

        
        

    }
    
    private Map<LocalDate, List<Timeslot>> getAllTimeslotsGroupedByWeek(List<Timeslot> timeSlotList) {
        Map<LocalDate, List<Timeslot>> weekMap = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();

        for (Timeslot slot : timeSlotList) {
            Instant instant = slot.getTsdate().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDate date = instant.atZone(zone).toLocalDate();

            if (!date.isBefore(today)) {
                LocalDate monday = date.with(DayOfWeek.MONDAY);
                weekMap.computeIfAbsent(monday, k -> new ArrayList<>()).add(slot);
            }
        }
        return weekMap;
    }

    public LocalDate convertDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
