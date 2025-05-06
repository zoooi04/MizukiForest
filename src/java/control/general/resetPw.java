package control.general;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.UserService.UserService;
import model.Users;

@WebServlet(name = "resetPw", urlPatterns = {"/resetPw"})
public class resetPw extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;
    
    @Resource
    UserTransaction utx;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserService userService = new UserService(mgr);
        
       Users existingUser = (Users) session.getAttribute("user");
        String currentPassword = request.getParameter("password");
        String newPassword = request.getParameter("NewPw");
        String confirmNewPassword = request.getParameter("ConfirmPw");
        
      System.out.println(existingUser.getUserid());
        Users user = userService.findUserById(existingUser.getUserid());
        System.out.println(user);
        
        if (user != null && currentPassword.equals(user.getUserpw())) {
            if (newPassword.equals(confirmNewPassword)) {
                user.setUserpw(newPassword);
                //userService.updateUser(user);
                try {
                    utx.begin();
                    mgr.merge(user);
                    utx.commit();
                    response.sendRedirect(request.getContextPath()+"/view/adminDashboard.jsp"); 
                } catch (Exception ex) {
                    response.sendRedirect(request.getContextPath() + "/view/error.jsp");
                }
            } else {
              
                session.setAttribute("errorPwMessage", "New password and confirm password do not match!");
                response.sendRedirect(request.getContextPath()+ "/view/changePw.jsp");
            }
        } else {
           
            session.setAttribute("errorPwMessage", "Incorrect current password!");
//            request.getRequestDispatcher("view/changePw.jsp").forward(request, response);
            response.sendRedirect(request.getContextPath()+ "/view/changePw.jsp");
        }
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
}
