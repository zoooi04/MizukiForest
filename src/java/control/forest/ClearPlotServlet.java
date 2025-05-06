package control.forest;

import java.io.IOException;
import java.util.List;
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
import model.LandContentService;
import model.Landcontent;
import model.Users;
import model.UserService.UserService;
import model.UserInventoryItemService;
import model.Userinventoryitem;
import model.ItemService;
import model.TreeService;
import model.Tree;
import model.Item;

public class ClearPlotServlet extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;
    
    @Resource
    UserTransaction utx;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        
        // Check if user is logged in
        if (user == null) {
            response.sendRedirect("/MizukiForest/view/general/login.jsp");
            return;
        }
        
        // Initialize services
        UserService userService = new UserService(mgr);
        LandContentService landContentService = new LandContentService(mgr);
        UserInventoryItemService inventoryService = new UserInventoryItemService(mgr);
        ItemService itemService = new ItemService(mgr);
        TreeService treeService = new TreeService(mgr);
        
        // Refresh user data
        user = userService.findUserById(user.getUserid());
        
        // Get landId from request
        String landId = request.getParameter("landId");
        if (landId == null || landId.isEmpty()) {
            response.sendRedirect("/MizukiForest/GetUserForestData");
            return;
        }
        
        try {
            // Begin transaction
            utx.begin();
            
            // Get all land content records for this land
            List<Landcontent> landContents = landContentService.findByLandId(landId);
            
            for (Landcontent content : landContents) {
                // Skip special trees (T9999999)
                if (content.getTreeid() != null && "T9999999".equals(content.getTreeid().getTreeid())) {
                    continue;
                }
                
                // Process each item or tree
                if (content.getItemid() != null) {
                    // Handle item
                    Item item = content.getItemid();
                    
                    // Check if user already has this item in inventory
                    Userinventoryitem existingItem = inventoryService.findItemInventoryItem(user.getUserid(), item.getItemid());
                    
                    if (existingItem != null) {
                        // Update quantity
                        existingItem.setQuantity(existingItem.getQuantity() + 1);
                        inventoryService.updateUserInventoryItem(existingItem);
                    } else {
                        // Create new inventory item
                        Userinventoryitem newItem = new Userinventoryitem();
                        newItem.setInventoryitemid(inventoryService.generateNextInventoryItemId());
                        newItem.setItemid(item);
                        newItem.setUserid(user);
                        newItem.setQuantity(1);
                        newItem.setIsdeleted(false);
                        inventoryService.addUserInventoryItem(newItem);
                    }
                } else if (content.getTreeid() != null) {
                    // Handle tree
                    Tree tree = content.getTreeid();
                    
                    // Check if user already has this tree in inventory
                    Userinventoryitem existingTree = inventoryService.findTreeInventoryItem(user.getUserid(), tree.getTreeid());
                    
                    if (existingTree != null) {
                        // Update quantity
                        existingTree.setQuantity(existingTree.getQuantity() + 1);
                        inventoryService.updateUserInventoryItem(existingTree);
                    } else {
                        // Create new inventory item for tree
                        Userinventoryitem newTree = new Userinventoryitem();
                        newTree.setInventoryitemid(inventoryService.generateNextInventoryItemId());
                        newTree.setTreeid(tree);
                        newTree.setUserid(user);
                        newTree.setQuantity(1);
                        newTree.setIsdeleted(false);
                        inventoryService.addUserInventoryItem(newTree);
                    }
                }
                
                // Delete the land content record
                landContentService.deleteLandContent(content.getLandcontentid());
            }
            
            // Commit transaction
            utx.commit();
            
        } catch (Exception e) {
            // Log the error
            System.err.println("Error clearing plot: " + e.getMessage());
            e.printStackTrace();
            
            try {
                // Rollback transaction on error
                utx.rollback();
            } catch (Exception ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        // Redirect back to forest data
        response.sendRedirect("/MizukiForest/GetUserForestData");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to POST
        doPost(request, response);
    }
}