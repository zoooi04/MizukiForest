package control.forest;

import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import model.Land;
import model.LandContentService;
import model.Landcontent;
import model.Item;
import model.Tree;
import model.Userinventoryitem;
import model.UserInventoryItemService;
import model.Users;

/**
 * Servlet that handles planting items or trees on user's land
 */

public class PlantItemServlet extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    
    @Resource
    UserTransaction ut;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Log initial entry to servlet
        System.out.println("[PlantItemServlet] Starting processing request");
        
        try {
            // Start transaction
            System.out.println("[PlantItemServlet] Beginning transaction");
            ut.begin();
            
            // Get parameters from form submission
            String landId = request.getParameter("landId");
            int xCoord = Integer.parseInt(request.getParameter("xCoord"));
            int yCoord = Integer.parseInt(request.getParameter("yCoord"));
            String itemType = request.getParameter("itemType"); // "tree" or "item"
            String itemId = request.getParameter("itemId");
            
            System.out.println("[PlantItemServlet] Received parameters: landId=" + landId + 
                               ", xCoord=" + xCoord + ", yCoord=" + yCoord + 
                               ", itemType=" + itemType + ", itemId=" + itemId);
            
            // Get user from session
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            
            if (user == null) {
                // User not logged in
                System.out.println("[PlantItemServlet] ERROR: User not logged in");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            
            String userId = user.getUserid();
            System.out.println("[PlantItemServlet] Processing for user: " + userId);
            
            // Initialize services
            LandContentService landContentService = new LandContentService(em);
            UserInventoryItemService inventoryService = new UserInventoryItemService(em);
            
            // Check if user has the item in inventory
            System.out.println("[PlantItemServlet] Checking if user has the item in inventory");
            Userinventoryitem inventoryItem = null;
            
            if ("tree".equals(itemType)) {
                System.out.println("[PlantItemServlet] Looking for tree item: " + itemId);
                inventoryItem = inventoryService.findTreeInventoryItem(userId, itemId);
            } else {
                System.out.println("[PlantItemServlet] Looking for regular item: " + itemId);
                inventoryItem = inventoryService.findItemInventoryItem(userId, itemId);
            }
            
            if (inventoryItem == null) {
                System.out.println("[PlantItemServlet] ERROR: Item not found in inventory");
                response.sendRedirect(request.getContextPath() + "/GetUserForestData?error=noitem");
                return;
            }
            
            if (inventoryItem.getQuantity() < 1) {
                System.out.println("[PlantItemServlet] ERROR: Item quantity is zero");
                response.sendRedirect(request.getContextPath() + "/GetUserForestData?error=noitem");
                return;
            }
            
            System.out.println("[PlantItemServlet] Found item in inventory with quantity: " + inventoryItem.getQuantity());
            
            // Create new land content
            Landcontent newContent = new Landcontent();
            
            // Generate a new sequential ID in format LCAA0001
            String newContentId = generateSequentialId();
            newContent.setLandcontentid(newContentId);
            System.out.println("[PlantItemServlet] Generated new land content ID: " + newContentId);
            
            // Set coordinates
            newContent.setXcoord(xCoord);
            newContent.setYcoord(yCoord);
            newContent.setIsdeleted(false);
            
            // Set land reference
            System.out.println("[PlantItemServlet] Finding land with ID: " + landId);
            Land land = em.find(Land.class, landId);
            
            if (land == null) {
                System.out.println("[PlantItemServlet] ERROR: Land not found");
                response.sendRedirect(request.getContextPath() + "/GetUserForestData?error=invalidland");
                return;
            }
            
            newContent.setLandid(land);
            
            // Set item or tree reference
            if ("tree".equals(itemType)) {
                System.out.println("[PlantItemServlet] Finding tree with ID: " + itemId);
                Tree tree = em.find(Tree.class, itemId);
                
                if (tree == null) {
                    System.out.println("[PlantItemServlet] ERROR: Tree not found");
                    response.sendRedirect(request.getContextPath() + "/GetUserForestData?error=invaliditem");
                    return;
                }
                
                newContent.setTreeid(tree);
                System.out.println("[PlantItemServlet] Set tree reference: " + tree.getTreeid());
            } else {
                System.out.println("[PlantItemServlet] Finding item with ID: " + itemId);
                Item item = em.find(Item.class, itemId);
                
                if (item == null) {
                    System.out.println("[PlantItemServlet] ERROR: Item not found");
                    response.sendRedirect(request.getContextPath() + "/GetUserForestData?error=invaliditem");
                    return;
                }
                
                newContent.setItemid(item);
                System.out.println("[PlantItemServlet] Set item reference: " + item.getItemid());
            }
            
            // Save the new land content
            System.out.println("[PlantItemServlet] Adding new land content to database");
            landContentService.addLandContent(newContent);
            
            // Update inventory (reduce quantity by 1)
            int newQuantity = inventoryItem.getQuantity() - 1;
            System.out.println("[PlantItemServlet] Updating inventory quantity from " + 
                               inventoryItem.getQuantity() + " to " + newQuantity);
            
            if (newQuantity <= 0) {
                // Delete the inventory item record if quantity becomes zero
                System.out.println("[PlantItemServlet] Quantity is zero, removing inventory item record");
                em.remove(inventoryItem);
            } else {
                // Update the quantity
                inventoryItem.setQuantity(newQuantity);
                em.merge(inventoryItem);
            }
            
            // Commit transaction
            System.out.println("[PlantItemServlet] Committing transaction");
            ut.commit();
            
            System.out.println("[PlantItemServlet] Planting completed successfully!");
            
            // Redirect back to forest page
            response.sendRedirect(request.getContextPath() + "/GetUserForestData?planted=true");
            
        } catch (Exception e) {
            System.out.println("[PlantItemServlet] ERROR: Exception during planting: " + e.getMessage());
            e.printStackTrace();
            
            try {
                System.out.println("[PlantItemServlet] Rolling back transaction");
                ut.rollback();
            } catch (Exception ex) {
                System.out.println("[PlantItemServlet] ERROR: Exception during rollback: " + ex.getMessage());
                ex.printStackTrace();
            }
            
            // Redirect with error
            response.sendRedirect(request.getContextPath() + "/GetUserForestData?error=planting");
        }
    }
    
    /**
     * Generates a sequential ID for land content in the format LCAA0001
     * Finds the highest existing ID and increments by 1
     * @return The new sequential ID
     */
    private String generateSequentialId() {
        try {
            // Query to find the highest ID
            Query query = em.createQuery(
                "SELECT lc.landcontentid FROM Landcontent lc " +
                "WHERE lc.landcontentid LIKE 'LCAA%' " +
                "ORDER BY lc.landcontentid DESC"
            );
            query.setMaxResults(1);
            
            String highestId = null;
            try {
                highestId = (String) query.getSingleResult();
            } catch (Exception e) {
                // No results found or other error
                System.out.println("[PlantItemServlet] No existing land content records found or error: " + e.getMessage());
            }
            
            int nextNumber = 1;
            
            if (highestId != null && highestId.length() >= 8) {
                // Extract the numeric part and increment
                String numericPart = highestId.substring(4);
                try {
                    nextNumber = Integer.parseInt(numericPart) + 1;
                } catch (NumberFormatException e) {
                    System.out.println("[PlantItemServlet] Error parsing ID number, using default: " + e.getMessage());
                    // Default to 1 if parsing fails
                }
            }
            
            // Format the new ID with leading zeros
            return String.format("LCAA%04d", nextNumber);
        } catch (Exception e) {
            System.out.println("[PlantItemServlet] Error generating sequential ID: " + e.getMessage());
            e.printStackTrace();
            // Fallback to a safe default if there's an error
            return "LCAA0001";
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[PlantItemServlet] Received POST request");
        processRequest(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[PlantItemServlet] Received GET request - redirecting");
        // Redirect GET requests to the appropriate handler
        response.sendRedirect(request.getContextPath() + "/GetUserForestData");
    }
}