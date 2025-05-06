package control.forest;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import model.Item;
import model.Landcontent;
import model.LandContentService;
import model.Tree;
import model.UserInventoryItemService;
import model.Userinventoryitem;
import model.Users;

/**
 * Servlet to handle the removal of items/trees from land plots
 */

@Transactional
public class RemoveItemServlet extends HttpServlet {
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Handles the POST request to remove an item from a land plot
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get session and check if user is logged in
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        
        if (user == null) {
            // Redirect to login page if not logged in
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            // Get parameters from the form
            String landId = request.getParameter("landId");
            int xCoord = Integer.parseInt(request.getParameter("xCoord"));
            int yCoord = Integer.parseInt(request.getParameter("yCoord"));
            
            // Create services
            LandContentService landContentService = new LandContentService(em);
            UserInventoryItemService inventoryService = new UserInventoryItemService(em);
            
            // Find land content that matches the coordinates
            List<Landcontent> landContents = landContentService.findByLandId(landId);
            Landcontent contentToRemove = null;
            
            for (Landcontent content : landContents) {
                if (content.getXcoord() == xCoord && content.getYcoord() == yCoord) {
                    contentToRemove = content;
                    break;
                }
            }
            
            if (contentToRemove != null) {
                // Before removing, save the associated item or tree
                Item item = contentToRemove.getItemid();
                Tree tree = contentToRemove.getTreeid();
                
                // Handle inventory update
                if (item != null) {
                    // Check if user already has this item in inventory
                    Userinventoryitem existingItem = inventoryService.findItemInventoryItem(user.getUserid(), item.getItemid());
                    
                    if (existingItem != null) {
                        // Update quantity
                        existingItem.setQuantity(existingItem.getQuantity() + 1);
                        inventoryService.updateUserInventoryItem(existingItem);
                    } else {
                        // Create new inventory item
                        Userinventoryitem newItem = createNewInventoryItem(user, item, null);
                        inventoryService.addUserInventoryItem(newItem);
                    }
                } else if (tree != null) {
                    // Check if user already has this tree in inventory
                    Userinventoryitem existingTree = inventoryService.findTreeInventoryItem(user.getUserid(), tree.getTreeid());
                    
                    if (existingTree != null) {
                        // Update quantity
                        existingTree.setQuantity(existingTree.getQuantity() + 1);
                        inventoryService.updateUserInventoryItem(existingTree);
                    } else {
                        // Create new inventory item for tree
                        Userinventoryitem newItem = createNewInventoryItem(user, null, tree);
                        inventoryService.addUserInventoryItem(newItem);
                    }
                }
                
                // Remove the content from land
                landContentService.deleteLandContent(contentToRemove.getLandcontentid());
            }
            
            // Redirect back to the forest page
            
            session.setAttribute("RemoveFromForestSuccess", true);
            response.sendRedirect(request.getContextPath() + "/GetUserForestData");
            
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to an error page or back to the forest page with an error message
            response.sendRedirect(request.getContextPath() + "/GetUserForestData?error=true");
        }
    }
    
    /**
     * Creates a new UserInventoryItem record
     * 
     * @param user the user who owns the item
     * @param item the item to add (can be null if it's a tree)
     * @param tree the tree to add (can be null if it's an item)
     * @return the new UserInventoryItem record
     */
    private Userinventoryitem createNewInventoryItem(Users user, Item item, Tree tree) {
        // Generate new inventory item ID
        String newId = generateNewInventoryItemId();
        
        // Create and set up new inventory item
        Userinventoryitem newItem = new Userinventoryitem();
        newItem.setInventoryitemid(newId);
        newItem.setQuantity(1);
        newItem.setIsdeleted(false);
        newItem.setUserid(user);
        
        if (item != null) {
            newItem.setItemid(item);
        } else if (tree != null) {
            newItem.setTreeid(tree);
        }
        
        return newItem;
    }
    
    /**
     * Generates a new inventory item ID in the format IVAA0001, IVAA0002, etc.
     * 
     * @return the new inventory item ID
     */
    private String generateNewInventoryItemId() {
        // Query to find the highest inventory item ID
        String query = "SELECT u FROM Userinventoryitem u WHERE u.inventoryitemid LIKE 'IVAA%' ORDER BY u.inventoryitemid DESC";
        List<Userinventoryitem> items = em.createQuery(query, Userinventoryitem.class)
                                         .setMaxResults(1)
                                         .getResultList();
        
        String prefix = "IVAA";
        int number = 1;
        
        if (!items.isEmpty()) {
            String lastId = items.get(0).getInventoryitemid();
            if (lastId.length() >= 8) {
                try {
                    number = Integer.parseInt(lastId.substring(4)) + 1;
                } catch (NumberFormatException e) {
                    // In case of parsing error, start from 1
                    number = 1;
                }
            }
        }
        
        // Format to ensure 4 digits
        return prefix + String.format("%04d", number);
    }
}