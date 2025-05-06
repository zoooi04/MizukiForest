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
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Message;
import model.therapyService.MessageService;
import model.Therapist;
import model.Users;

/**
 *
 * @author huaiern
 */
public class SendMessageServlet extends HttpServlet {
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String content = (String) request.getParameter("message");
        System.out.println(content);
        
        String role = (String) session.getAttribute("role");
        
        
        
        Users user = (Users) session.getAttribute("user");
        Therapist therapist = (Therapist) session.getAttribute("therapist");
        

        MessageService ms = new MessageService(em, utx);

        //user is null means that it is logged in as therapist
        if (role.equalsIgnoreCase("therapist")) {
            //if sender is therapist
            String userid = (String) request.getParameter("userid");
            try{
                user = em.createQuery("SELECT u FROM Users u WHERE u.userid = :userid", Users.class)
                        .setParameter("userid", userid)
                        .getSingleResult();
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        boolean success = ms.addMessage(content, role.toUpperCase(), user, therapist);

        if(success){

            
            if(role.equalsIgnoreCase("user")){
                List<Message> messageList = ms.getAllMessageByUser(user);
                session.setAttribute("messageList", messageList);
                
                ChatWebSocket.broadcast("new_message_for_therapist:" + therapist.getTherapistid() + "|" + therapist.getTherapistname() + "|" + content + "|" + user.getUserid());
            }else{
                List<Message> messageList = ms.getAllMessageByTherapist(therapist);
                session.setAttribute("messageList", messageList);

                
                ChatWebSocket.broadcast("new_message_for_user:" + user.getUserid() + "|" + user.getUsername() + "|" + content + "|" + therapist.getTherapistid());
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(""+success);
        

        
    }



}
