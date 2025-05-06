package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.UserInventoryItemService;
import model.UserService.UserService;
import model.Userinventoryitem;
import model.Users;
import org.json.JSONObject;

/**
 * Servlet that checks if the user has shovels in their inventory
 */

public class CheckShovelInventoryServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();
        
        try {
            // Get user ID from session
            UserService us = new UserService (em);
            UserInventoryItemService inventoryService = new UserInventoryItemService(em);
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            
            
            if (user == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "User not logged in");
                out.print(jsonResponse.toString());
                return;
            }
            user = us.findUserById(user.getUserid());
            String userId = user.getUserid();
            
            // Get itemId parameter (should be IT000021 for shovels)
            String itemId = request.getParameter("itemId");
            
            if (itemId == null || itemId.isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Item ID is required");
                out.print(jsonResponse.toString());
                return;
            }
            
            // Find shovel inventory item
            Userinventoryitem shovelItem = inventoryService.findItemInventoryItem(userId, itemId);
            
            if (shovelItem != null && shovelItem.getQuantity() > 0) {
                // User has shovels
                jsonResponse.put("success", true);
                jsonResponse.put("shovelCount", shovelItem.getQuantity());
            } else {
                // User has no shovels
                jsonResponse.put("success", true);
                jsonResponse.put("shovelCount", 0);
            }
            
            out.print(jsonResponse.toString());
            
        } catch (Exception e) {
            // Log the error
            log("Error in CheckShovelInventoryServlet: " + e.getMessage(), e);
            
            // Send error response
            jsonResponse.put("success", false);
            jsonResponse.put("message", "An error occurred: " + e.getMessage());
            out.print(jsonResponse.toString());
        } finally {
            out.close();
        }
    }

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
        processRequest(request, response);
    }

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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Checks if the user has shovels in their inventory";
    }
}