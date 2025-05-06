package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
import model.Biome;
import model.BiomeService;
import model.Item;
import model.ItemService;
import model.Land;
import model.LandService;
import model.Tree;
import model.TreeService;
import model.Users;
import model.UserService.UserService;
import model.LandContentService;
import model.Landcontent;
import model.UserService.UserService;

public class GetFriendForestData extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set response content type to JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
        
        // Get the friendUserId parameter from the request
        String friendUserId = request.getParameter("profileId");
        
        // Check if friendUserId is provided
        if (friendUserId == null || friendUserId.trim().isEmpty()) {
            jsonResponse.add("error", "User ID cannot be empty");
            out.print(jsonResponse.build());
            return;
        }
        
        // Get the current user's session
        HttpSession session = request.getSession();
        Users currentUser = (Users)session.getAttribute("user");
        if (currentUser == null) {
            jsonResponse.add("error", "You must be logged in to view forests");
            out.print(jsonResponse.build());
            return;
        }

        // Step 1: Check if friend exists
        UserService userService = new UserService(mgr);
        Users friend = userService.findUserById(friendUserId);
        
        if (friend == null) {
            jsonResponse.add("error", "User not found");
            out.print(jsonResponse.build());
            return;
        }
        
        // Step 2: Check forest visibility
        if (!friend.getForestvisibility()) {
            jsonResponse.add("error", "This user's forest is hidden");
            out.print(jsonResponse.build());
            return;
        }

        // Step 3: Initialize services with EntityManager
        LandService landService = new LandService(mgr);
        BiomeService biomeService = new BiomeService(mgr);
        TreeService treeService = new TreeService(mgr);
        ItemService itemService = new ItemService(mgr);
        LandContentService landContentService = new LandContentService(mgr);

        // Step 4: Get LAND records by friend's user ID
        List<Land> landList = landService.findLandByUserId(friendUserId);

        // Step 5: Get all Biomes, Trees, Items, and Land Contents
        List<Biome> biomeAllList = biomeService.findAll();
        List<Tree> allTrees = treeService.findAllTrees();
        List<Item> allItems = itemService.findAllItems();
        List<Landcontent> allLandContents = landContentService.findAll();

        // Step 6: Build JSON response
        jsonResponse.add("friendUsername", friend.getUsername());
        
        // Add Land List
        JsonArrayBuilder landArrayBuilder = Json.createArrayBuilder();
        for (Land land : landList) {
            JsonObjectBuilder landObject = Json.createObjectBuilder()
                .add("landid", land.getLandid())
                .add("landname", land.getLandname())
                .add("ismainland", land.getIsmainland())
                .add("biomeid", land.getBiomeid().getBiomeid());
            
            landArrayBuilder.add(landObject);
        }
        jsonResponse.add("landList", landArrayBuilder);
        
        // Add Biome List
        JsonArrayBuilder biomeArrayBuilder = Json.createArrayBuilder();
        for (Biome biome : biomeAllList) {
            JsonObjectBuilder biomeObject = Json.createObjectBuilder()
                .add("biomeid", biome.getBiomeid())
                .add("biomename", biome.getBiomename())
                .add("biomeimage", request.getContextPath() + "/" + biome.getBiomeimage());
            
            biomeArrayBuilder.add(biomeObject);
        }
        jsonResponse.add("biomeAllList", biomeArrayBuilder);
        
        // Add Land Contents List (filtered for this friend's lands)
        JsonArrayBuilder contentArrayBuilder = Json.createArrayBuilder();
        for (Landcontent content : allLandContents) {
            // Only include content for this friend's lands
            if (landList.stream().anyMatch(land -> land.getLandid().equals(content.getLandid().getLandid()))) {
                JsonObjectBuilder contentObject = Json.createObjectBuilder()
                    .add("landid", content.getLandid().getLandid())
                    .add("xcoord", content.getXcoord())
                    .add("ycoord", content.getYcoord())
                    .add("isdeleted", content.getIsdeleted());
                
                // Add tree info if available
                if (content.getTreeid() != null) {
                    JsonObjectBuilder treeObject = Json.createObjectBuilder()
                        .add("treeid", content.getTreeid().getTreeid())
                        .add("treename", content.getTreeid().getTreename())
                        .add("treeimage", request.getContextPath() + "/" + content.getTreeid().getTreeimage());
                    
                    contentObject.add("treeid", treeObject);
                }
                
                // Add item info if available
                if (content.getItemid() != null) {
                    JsonObjectBuilder itemObject = Json.createObjectBuilder()
                        .add("itemid", content.getItemid().getItemid())
                        .add("itemname", content.getItemid().getItemname())
                        .add("itemimage", request.getContextPath() + "/" + content.getItemid().getItemimage());
                    
                    contentObject.add("itemid", itemObject);
                }
                
                contentArrayBuilder.add(contentObject);
            }
        }
        jsonResponse.add("AllLandContents", contentArrayBuilder);
        
        // Send the JSON response
        out.print(jsonResponse.build());
    }
}