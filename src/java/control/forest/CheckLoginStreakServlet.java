package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.UserService.UserService;
import model.Users;
import org.json.JSONObject;

/**
 * Servlet to check user's login streak and determine if rewards can be claimed
 */
@WebServlet(name = "CheckLoginStreakServlet", urlPatterns = {"/CheckLoginStreakServlet"})
public class CheckLoginStreakServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        UserService us = new UserService(em);
        try {
            // Get user from session
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");

            if (user == null) {
                JSONObject errorJson = new JSONObject();
                errorJson.put("error", "User not logged in");
                out.print(errorJson.toString());
                return;
            }
            
            user = us.findUserById(user.getUserid());
            
            // Get current date and user's last login date
            Date currentDate = new Date();
            Date lastLoginDate = user.getLastlogindate();

            // Calculate days difference
            int daysDifference = 0;
            boolean canClaim = false;

            if (lastLoginDate != null) {
                daysDifference = getDaysDifference(lastLoginDate, currentDate);

                // Determine if user can claim today's reward
                if (daysDifference == 0) {
                    // Same day, already claimed
                    canClaim = false;
                } else if (daysDifference == 1) {
                    // Next day, can claim and continue streak
                    canClaim = true;
                } else {
                    // Two or more days, reset streak but can still claim
                    canClaim = true;
                    // Streak will be set to 1 when they claim
                }
            } else {
                // No last login date, first time user
                canClaim = true;
            }

            // Prepare the JSON response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("userId", user.getUserid());

            int streakToReturn;
            if (daysDifference == 1) {
                streakToReturn = user.getLoginstreak() + 1;  // Increment streak for consecutive day login
            } else if (daysDifference >= 2) {
                streakToReturn = 0;  // Reset streak for gap in logins
            } else {
                streakToReturn = user.getLoginstreak();  // Same day, keep current streak
            }
            jsonResponse.put("loginStreak", streakToReturn);

            jsonResponse.put("coins", user.getCoins());
            jsonResponse.put("canClaim", canClaim);
            jsonResponse.put("lastLoginDate", lastLoginDate != null ? formatDate(lastLoginDate) : null);
            jsonResponse.put("currentDate", formatDate(currentDate));
            jsonResponse.put("daysDifference", daysDifference);

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "Error checking login streak: " + e.getMessage());
            out.print(errorJson.toString());
            e.printStackTrace();
        }
    }

    /**
     * Calculates the difference in days between two dates
     *
     * @param date1 First date
     * @param date2 Second date
     * @return Number of days difference (ignoring time)
     */
    private int getDaysDifference(Date date1, Date date2) {
        // Create calendar instances to remove time part
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        // Calculate the difference in days
        long diffMillis = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        return (int) (diffMillis / (24 * 60 * 60 * 1000));
    }

    /**
     * Format date to string
     *
     * @param date Date to format
     * @return Formatted date string
     */
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
