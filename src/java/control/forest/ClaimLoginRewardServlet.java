package control.forest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import model.Users;
import model.UserService.UserService;
import org.json.JSONObject;

/**
 * Servlet to handle claiming daily login rewards
 */
@WebServlet(name = "ClaimLoginRewardServlet", urlPatterns = {"/ClaimLoginRewardServlet"})
public class ClaimLoginRewardServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;
    
    @Resource
    private UserTransaction utx;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        UserService userService = new UserService(em);
        try {
            // Parse JSON request
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            JSONObject jsonRequest = new JSONObject(sb.toString());
            int rewardAmount = jsonRequest.getInt("amount");
            
            // Get user from session
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            
            if (user == null) {
                JSONObject errorJson = new JSONObject();
                errorJson.put("success", false);
                errorJson.put("error", "User not logged in");
                out.print(errorJson.toString());
                return;
            }
            
            user = userService.findUserById(user.getUserid());
            
            // Get the current date and user's last login date
            Date currentDate = new Date();
            Date lastLoginDate = user.getLastlogindate();
            
            // Calculate days difference
            int daysDifference = 0;
            if (lastLoginDate != null) {
                long diffTime = currentDate.getTime() - lastLoginDate.getTime();
                long diffDays = diffTime / (1000 * 60 * 60 * 24);
                daysDifference = (int) diffDays;
            }
            
            // Start transaction
            utx.begin();
            
           
            
            // Update user data
            int currentStreak = user.getLoginstreak();
            
            // Update streak based on days difference
            if (lastLoginDate == null || daysDifference >= 2) {
                // First login or streak reset
                user.setLoginstreak(1);
            } else if (daysDifference == 1) {
                // Continue streak
                user.setLoginstreak(currentStreak + 1);
            }
            // If daysDifference is 0, streak stays the same (already claimed today)
            
            // Add coins to user
            user.setCoins(user.getCoins() + rewardAmount);
            
            // Update last login date
            user.setLastlogindate(currentDate);
            
            // Save changes
            userService.updateUser(user);
            
            // Update session
            session.setAttribute("user", user);
            
            // Commit transaction
            utx.commit();
            
            // Prepare success response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Reward claimed successfully");
            jsonResponse.put("updatedCoins", user.getCoins());
            jsonResponse.put("updatedStreak", user.getLoginstreak());
            
            out.print(jsonResponse.toString());
            
        } catch (Exception e) {
            // Rollback transaction if an error occurs
            try {
                utx.rollback();
            } catch (Exception ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            
            JSONObject errorJson = new JSONObject();
            errorJson.put("success", false);
            errorJson.put("error", "Error claiming reward: " + e.getMessage());
            out.print(errorJson.toString());
            e.printStackTrace();
        }
    }
}