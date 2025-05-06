/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

import java.io.IOException;
import java.io.PrintWriter;
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
public class DeleteTimeSlotServlet extends HttpServlet {
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Therapist therapist = (Therapist) session.getAttribute("therapist");
        String slotid = (String) request.getParameter("slotid");
        
        TimeSlotService tss = new TimeSlotService(em, utx);
        boolean isDeleted = tss.deleteTimeSlot(slotid, therapist);
        
        if(isDeleted){
            System.out.println("IT IS DELETED");

        }
        session.setAttribute("isDeleted", isDeleted);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String redirectUrl = request.getContextPath() + "/LoadScheduleServlet";
        String jsonResponse = "{\"redirectUrl\": \"" + redirectUrl + "\"}";

        response.getWriter().write(jsonResponse);
        
        
    }

}
