package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
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
import model.Biome;
import model.BiomeService;
import model.Item;
import model.ItemService;
import model.Land;
import model.LandService;
import model.Treebox;
import model.TreeBoxService;
import model.UserInventoryItemService;
import model.Userinventoryitem;
import model.UserService.UserService;
import model.Users;
import org.json.JSONObject;


public class purchaseItem extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    @Resource
    UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This handles GET requests to load the shop items
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        
        if (user == null) {
            // User not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String userId = user.getUserid();
        
        // Create services
        UserService userService = new UserService(em);
        ItemService itemService = new ItemService(em);
        TreeBoxService treeBoxService = new TreeBoxService(em);
        BiomeService biomeService = new BiomeService(em);
        
        // Get user to retrieve coins
        user = userService.findUserById(userId);
        int userCoins = user.getCoins();
        
        // Create JSON response
        JSONObject responseJson = new JSONObject();
        responseJson.put("userCoins", userCoins);
        responseJson.put("items", itemService.findAllItems());
        responseJson.put("treeBoxes", treeBoxService.findAllTreeboxes());
        responseJson.put("biomes", biomeService.findAll());
        
        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(responseJson.toString());
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This handles POST requests for purchases
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        
        JSONObject responseJson = new JSONObject();
        
        if (user == null) {
            // User not logged in
            responseJson.put("success", false);
            responseJson.put("message", "User not logged in");
            sendJsonResponse(response, responseJson);
            return;
        }
        
        String userId = user.getUserid();
        
        // Get purchase parameters
        String itemId = request.getParameter("itemId");
        String itemType = request.getParameter("itemType");
        int itemCost = Integer.parseInt(request.getParameter("itemCost"));
        
        // Create services
        UserService userService = new UserService(em);
        ItemService itemService = new ItemService(em, utx);
        TreeBoxService treeBoxService = new TreeBoxService(em);
        BiomeService biomeService = new BiomeService(em);
        LandService landService = new LandService(em);
        UserInventoryItemService inventoryService = new UserInventoryItemService(em);
        
        try {
            // Start transaction
            utx.begin();
            
            // Get user and check if they have enough coins
            user = userService.findUserById(userId);
            int userCoins = user.getCoins();
            
            if (userCoins < itemCost) {
                // Not enough coins
                utx.rollback();
                responseJson.put("success", false);
                responseJson.put("message", "Not enough coins");
                sendJsonResponse(response, responseJson);
                return;
            }
            
            // Process purchase based on item type
            switch (itemType) {
                case "biome":
                    // Purchase a biome
                    Biome biome = biomeService.findById(itemId);
                    if (biome == null || biome.getIsdeleted() || biome.getIsarchived()) {
                        utx.rollback();
                        responseJson.put("success", false);
                        responseJson.put("message", "Biome not available");
                        break;
                    }
                    
                    // Check if user already has this biome in inventory
                    Userinventoryitem existingBiome = inventoryService.findBiomeInventoryItem(userId, itemId);
                    
                    if (existingBiome != null) {
                        // Update existing inventory item
                        existingBiome.setQuantity(existingBiome.getQuantity() + 1);
                        inventoryService.updateUserInventoryItem(existingBiome);
                        
                        responseJson.put("success", true);
                        responseJson.put("message", "You've added another " + biome.getBiomename() + " biome to your inventory!");
                    } else {
                        // Generate new land ID
                        String lastLandId = landService.getLastLandIdByPrefix("L");
                        String newLandId;
                        if ("No land found".equals(lastLandId)) {
                            newLandId = "L0000001";
                        } else {
                            int lastId = Integer.parseInt(lastLandId.substring(1));
                            newLandId = "L" + String.format("%07d", lastId + 1);
                        }
                        
                        // Create new land
                        Land newLand = new Land(newLandId, biome.getBiomename(), false, false);
                        newLand.setBiomeid(biome);
                        newLand.setUserid(user);
                        landService.addLand(newLand);
                        
                        // Also add to user inventory
                        String newInventoryId = generateInventoryItemId(inventoryService);
                        Userinventoryitem newBiomeItem = new Userinventoryitem();
                        newBiomeItem.setInventoryitemid(newInventoryId);
                        newBiomeItem.setQuantity(1);
                        newBiomeItem.setIsdeleted(false);
                        newBiomeItem.setUserid(user);
                        newBiomeItem.setBiomeid(biome);
                        inventoryService.addUserInventoryItem(newBiomeItem);
                        
                        responseJson.put("success", true);
                        responseJson.put("message", "You've purchased a new land with the " + biome.getBiomename() + " biome!");
                        responseJson.put("newLandId", newLandId);
                    }
                    break;
                
                case "treebox":
                    // Purchase a treebox
                    Treebox treebox = treeBoxService.findTreeboxById(itemId);
                    if (treebox == null || treebox.getIsdeleted() || treebox.getIsarchived()) {
                        utx.rollback();
                        responseJson.put("success", false);
                        responseJson.put("message", "Tree box not available");
                        break;
                    }
                    
                    // Check if user already has this treebox in inventory
                    System.out.println("Before finding existing tree box the id is :" + itemId + " by: "+ userId);
                    Userinventoryitem existingTreebox = inventoryService.findTreeBoxInventoryItem(userId, itemId);
                    System.out.println("I might have found it:  "+ existingTreebox);
                    if (existingTreebox != null) {
                        // Update existing inventory item
                        existingTreebox.setQuantity(existingTreebox.getQuantity() + 1);
                        System.out.println("I might have found it:  "+ existingTreebox);
                        inventoryService.updateUserInventoryItem(existingTreebox);
                    } else {
                        // Create new inventory item
                        String newInventoryId = generateInventoryItemId(inventoryService);
                        Userinventoryitem newTreeboxItem = new Userinventoryitem();
                        newTreeboxItem.setInventoryitemid(newInventoryId);
                        newTreeboxItem.setQuantity(1);
                        newTreeboxItem.setIsdeleted(false);
                        newTreeboxItem.setUserid(user);
                        newTreeboxItem.setTreeboxid(treebox);
                        inventoryService.addUserInventoryItem(newTreeboxItem);
                    }
                    
                    responseJson.put("success", true);
                    responseJson.put("message", "You've purchased a " + treebox.getTreeboxname() + " tree box!");
                    break;
                    
                case "item":
                    // Purchase an item
                    Item item = itemService.findItemById(itemId);
                    if (item == null || item.getIsdeleted() || item.getIsarchived()) {
                        utx.rollback();
                        responseJson.put("success", false);
                        responseJson.put("message", "Item not available");
                        break;
                    }
                    
                    // Check if user already has this item in inventory
                    Userinventoryitem existingItem = inventoryService.findItemInventoryItem(userId, itemId);
                    
                    if (existingItem != null) {
                        // Update existing inventory item
                        existingItem.setQuantity(existingItem.getQuantity() + 1);
                        inventoryService.updateUserInventoryItem(existingItem);
                    } else {
                        // Create new inventory item
                        String newInventoryId = generateInventoryItemId(inventoryService);
                        Userinventoryitem newItem = new Userinventoryitem();
                        newItem.setInventoryitemid(newInventoryId);
                        newItem.setQuantity(1);
                        newItem.setIsdeleted(false);
                        newItem.setUserid(user);
                        newItem.setItemid(item);
                        inventoryService.addUserInventoryItem(newItem);
                    }
                    
                    responseJson.put("success", true);
                    responseJson.put("message", "You've purchased a " + item.getItemname() + "!");
                    break;
                    
                default:
                    utx.rollback();
                    responseJson.put("success", false);
                    responseJson.put("message", "Invalid item type");
                    sendJsonResponse(response, responseJson);
                    return;
            }
            
            // Deduct coins from user
            user.setCoins(userCoins - itemCost);
            userService.updateUser(user);
            
            // Update session with new coin amount
            session.setAttribute("userCoins", user.getCoins());
            responseJson.put("userCoins", user.getCoins());
            
            // Commit transaction
            utx.commit();
            
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            responseJson.put("success", false);
            responseJson.put("message", "An error occurred: " + e.getMessage());
        }
        
        sendJsonResponse(response, responseJson);
    }
    
    private void sendJsonResponse(HttpServletResponse response, JSONObject json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
    
    private String generateInventoryItemId(UserInventoryItemService service) {
        // Get all inventory items
        List<Userinventoryitem> allItems = service.findAll();
        
        // Find highest ID number
        int maxId = 0;
        String prefix = "IVAA";
        
        for (Userinventoryitem item : allItems) {
            String idStr = item.getInventoryitemid();
            if (idStr.startsWith(prefix)) {
                try {
                    int idNum = Integer.parseInt(idStr.substring(prefix.length()));
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Skip malformed IDs
                }
            }
        }
        
        // Generate new ID with incremented number
        return prefix + String.format("%04d", maxId + 1);
    }
}