package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpSession;
import model.BiomeService;
import model.ItemService;
import model.LevelRewardService.LevelRewardService;
import model.TreeBoxService;
import model.UserLevelService.UserLevelService;
import model.Users;
import model.Item;
import model.Treebox;
import model.Userlevel;
import model.Levelreward;
import model.UserService.UserService;

public class UserLevelServlet extends HttpServlet {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Initialize services
        UserService userService = new UserService(em);
        UserLevelService userLevelService = new UserLevelService(em);
        LevelRewardService levelRewardService = new LevelRewardService(em);
        ItemService itemService = new ItemService(em);
        TreeBoxService treeBoxService = new TreeBoxService(em);
        
        HttpSession session = request.getSession();
        // Step 1: Hardcoded User ID
        Users user = (Users)session.getAttribute("user");
        String userId = user.getUserid();
        user = userService.findUserById(userId);
        int userLevel = 0;
        int userExp = 0;
        
        if (user != null) {
            userLevel = user.getUserlevel();
            userExp = user.getExp();
        }
        
        // Get all levels
        List<Userlevel> allLevels = userLevelService.findAll();
        
        // Find the next level's required exp
        int nextLevelRequiredExp = 100; // Default value
        for (Userlevel level : allLevels) {
            if (level.getLevelid() == userLevel + 1) {
                nextLevelRequiredExp = level.getRequiredxp();
                break;
            }
        }
        
        // Get all level rewards
        List<Levelreward> allRewards = levelRewardService.findAll();
        
        // Create JSON response
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
        
        // Add user level info to the response
        responseBuilder.add("userLevel", userLevel);
        responseBuilder.add("userExp", userExp);
        responseBuilder.add("nextLevelRequiredExp", nextLevelRequiredExp);
        
        // Add levels
        JsonArrayBuilder levelsArray = Json.createArrayBuilder();
        for (Userlevel level : allLevels) {
            if (level.getIsdeleted() != null && level.getIsdeleted()) {
                continue; // Skip deleted levels
            }
            
            JsonObjectBuilder levelBuilder = Json.createObjectBuilder()
                .add("levelid", level.getLevelid())
                .add("requiredxp", level.getRequiredxp());
            
            levelsArray.add(levelBuilder);
        }
        responseBuilder.add("levels", levelsArray);
        
        // Process rewards with item/treebox details
        JsonArrayBuilder rewardsArray = Json.createArrayBuilder();
        for (Levelreward reward : allRewards) {
            if (reward.getIsdeleted() != null && reward.getIsdeleted()) {
                continue; // Skip deleted rewards
            }
            
            JsonObjectBuilder rewardBuilder = Json.createObjectBuilder()
                .add("levelrewardid", reward.getLevelrewardid())
                .add("levelId", reward.getLevelid().getLevelid())
                .add("quantity", reward.getQuantity());
            
            // Add item info if available
            if (reward.getItemid() != null) {
                Item item = itemService.findItemById(reward.getItemid().getItemid());
                if (item != null) {
                    JsonObjectBuilder itemBuilder = Json.createObjectBuilder()
                        .add("id", item.getItemid())
                        .add("name", item.getItemname())
                        .add("image", item.getItemimage() != null ? item.getItemimage() : "");
                    
                    rewardBuilder.add("itemInfo", itemBuilder);
                }
            }
            
            // Add treebox info if available
            if (reward.getTreeboxid() != null) {
                Treebox treebox = treeBoxService.findTreeboxById(reward.getTreeboxid().getTreeboxid());
                if (treebox != null) {
                    JsonObjectBuilder treeboxBuilder = Json.createObjectBuilder()
                        .add("id", treebox.getTreeboxid())
                        .add("name", treebox.getTreeboxname())
                        .add("image", treebox.getTreeboximage() != null ? treebox.getTreeboximage() : "");
                    
                    rewardBuilder.add("treeboxInfo", treeboxBuilder);
                }
            }
            
            rewardsArray.add(rewardBuilder);
        }
        responseBuilder.add("rewards", rewardsArray);
        
        // Set response as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Write response
        try (PrintWriter out = response.getWriter()) {
            out.print(responseBuilder.build());
        }
    }
}