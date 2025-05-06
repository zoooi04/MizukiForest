package control.forest;

import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import model.Landcontent;
import model.LandContentService;
import model.UserInventoryItemService;
import model.UserService.UserService;
import model.Userinventoryitem;
import model.Users;

/**
 * Servlet that removes a withered tree from land and uses up a shovel from inventory
 */
public class RemoveWitheredTreeServlet extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    @Resource
    UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get parameters
        String landId = request.getParameter("landId");
        String xCoordStr = request.getParameter("xCoord");
        String yCoordStr = request.getParameter("yCoord");
        String itemId = request.getParameter("itemId");  // Should be IT000021 for shovel
        
        // Validate parameters
        if (landId == null || xCoordStr == null || yCoordStr == null || itemId == null) {
            log("Missing parameters for withered tree removal");
            response.sendRedirect("/MizukiForest/view/forest/myForest.jsp?error=missingParameters");
            return;
        }
        
        try {
            int xCoord = Integer.parseInt(xCoordStr);
            int yCoord = Integer.parseInt(yCoordStr);
            
            UserService us = new UserService(em);
            UserInventoryItemService inventoryService = new UserInventoryItemService(em);
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            
            if (user == null) {
                response.sendRedirect("/MizukiForest/view/general/login.jsp");
                return;
            }
            
            user = us.findUserById(user.getUserid());
            String userId = user.getUserid();
            
            // Initialize services
            LandContentService landContentService = new LandContentService(em);
            
            // Begin transaction
            try {
                utx.begin();
                
                // 1. Find the shovel in user's inventory
                Userinventoryitem shovelItem = inventoryService.findItemInventoryItem(userId, itemId);
                
                if (shovelItem == null || shovelItem.getQuantity() <= 0) {
                    // User doesn't have shovels
                    utx.rollback();
                    response.sendRedirect("/MizukiForest/view/forest/myForest.jsp?error=noShovels");
                    return;
                }
                
                // 2. Find the withered tree in land content
                Landcontent landContent = landContentService.findByLandCoord(landId, xCoord, yCoord);
                
                if (landContent == null) {
                    // No content at this location
                    utx.rollback();
                    response.sendRedirect("/MizukiForest/view/forest/myForest.jsp?error=noContentFound");
                    return;
                }
                
                // 3. Remove the withered tree (delete or update the land content)
                boolean removed = landContentService.deleteLandContent(landContent.getLandcontentid());
                
                if (!removed) {
                    utx.rollback();
                    response.sendRedirect("/MizukiForest/view/forest/myForest.jsp?error=removalFailed");
                    return;
                }
                
                // 4. Deduct one shovel from inventory
                shovelItem.setQuantity(shovelItem.getQuantity() - 1);
                
                // If quantity becomes 0, you can either remove the item or keep it with 0 quantity
                if (shovelItem.getQuantity() <= 0) {
                    // Option 1: Mark as deleted
                    shovelItem.setIsdeleted(true);
                    // Option 2: Remove from database
                    // inventoryService.deleteUserInventoryItem(shovelItem.getInventoryitemid());
                } else {
                    // Update the shovel quantity
                    inventoryService.updateUserInventoryItem(shovelItem);
                }
                
                // Commit the transaction
                utx.commit();
                
                // Redirect back to the forest page with success parameter
                response.sendRedirect("/MizukiForest/GetUserForestData");
                
            } catch (NotSupportedException | SystemException | RollbackException |
                    HeuristicMixedException | HeuristicRollbackException | SecurityException |
                    IllegalStateException e) {
                
                // Rollback in case of any errors
                try {
                    if (utx != null) {
                        utx.rollback();
                    }
                } catch (SystemException se) {
                    log("Error during rollback: " + se.getMessage(), se);
                }
                
                log("Transaction error in RemoveWitheredTreeServlet: " + e.getMessage(), e);
                response.sendRedirect("/MizukiForest/view/forest/myForest.jsp?error=transactionFailed");
            }
            
        } catch (NumberFormatException e) {
            log("Invalid coordinates format: " + e.getMessage());
            response.sendRedirect("/MizukiForest/view/forest/myForest.jsp?error=invalidCoordinates");
        } catch (Exception e) {
            // Attempt rollback for any other exception
            try {
                if (utx != null) {
                    utx.rollback();
                }
            } catch (SystemException se) {
                log("Error during rollback: " + se.getMessage(), se);
            }
            
            log("General error in RemoveWitheredTreeServlet: " + e.getMessage(), e);
            response.sendRedirect("/MizukiForest/view/forest/myForest.jsp?error=generalError");
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
        return "Removes a withered tree from land and uses up a shovel from inventory";
    }
}