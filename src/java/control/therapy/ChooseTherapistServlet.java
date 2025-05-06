/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.therapy;

import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.therapyService.UserTherapistService;
import model.Users;

/**
 *
 * @author huaiern
 */
public class ChooseTherapistServlet extends HttpServlet {
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        
        String therapistId = request.getParameter("therapistid");
        
        UserTherapistService uts = new UserTherapistService(em, utx);
           System.out.println("HERE USERR:" +user);
        boolean valid = uts.assignTherapist(therapistId, user);
        if(valid){
            response.sendRedirect(request.getContextPath() + "/LoadTherapistServlet");

        }
    }




}
