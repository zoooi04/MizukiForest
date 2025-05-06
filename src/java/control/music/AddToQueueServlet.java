package control.music;

import model.UserQueueService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Userqueuelist;
import model.UserqueuelistPK;
import model.Users;

@WebServlet(name = "AddToQueueServlet", urlPatterns = {"/AddToQueueServlet"})
public class AddToQueueServlet extends HttpServlet {

    @Inject
    private UserQueueService queueService;
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Example: normally you would get from session: 
        //String userId = "U2500001"; // Hardcoded for now as you did
        
        HttpSession session = request.getSession();
            Users user = (Users)session.getAttribute("user");
            
            if(user == null){
//                response.sendRedirect("login.jsp");
                request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
                return;
            }           
            
            String userId = user.getUserid();
            
            //UserService userService = new UserService(mgr);
            //String userId = userService.findUserById(user.getUserid());
        
        String musicId = request.getParameter("musicId");
        
        UserqueuelistPK pk = new UserqueuelistPK(userId, musicId);

        if (musicId != null && !musicId.isEmpty()) {
            
            Userqueuelist existing = em.find(Userqueuelist.class, pk);

            String message;
            StringBuilder errorMessage = new StringBuilder();
            
            if (existing != null){
                errorMessage.append("Music is already in the queue\n");
                //HttpSession session = request.getSession();
                session.setAttribute("AddToQueueErrorMsg", errorMessage.toString());
            }else{
                queueService.addMusicToQueue(userId, musicId);
                
                //HttpSession session = request.getSession();
                session.setAttribute("AddNewToQueue", musicId);
                
                // Check for hidden achievement - this will be processed by AJAX separately
                request.setAttribute("checkForAchievement", true);
                request.setAttribute("musicIdForAchievement", musicId);
            }
            
            
           
        }

        // After adding, redirect back to MusicServlet (popular music page)
        response.sendRedirect(request.getContextPath() + "/MusicServlet");
    }
}
