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
import model.Therapist;
import model.Usertherapist;
import model.therapyService.UserTherapistService;

/**
 *
 * @author huaiern
 */
public class LoadAssignedPatients extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Therapist therapist = (Therapist) session.getAttribute("therapist");
        
        UserTherapistService uts = new UserTherapistService(em,utx);
        List<Usertherapist> utList = uts.getAllUserTherapistByTherapist(therapist);
        
        session.setAttribute("utList",utList);
        response.sendRedirect(request.getContextPath() + "/view/therapy/assignedPatients.jsp");
    }



}
