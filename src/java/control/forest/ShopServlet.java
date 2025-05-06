package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import model.TreeBoxService;
import model.Users;
import model.Item;
import model.Treebox;
import model.Biome;
import model.UserService.UserService;

@WebServlet(name = "ShopServlet", urlPatterns = {"/shop"})
public class ShopServlet extends HttpServlet {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Initialize services
        ItemService itemService = new ItemService(em);
        TreeBoxService treeBoxService = new TreeBoxService(em);
        BiomeService biomeService = new BiomeService(em);
        UserService userService = new UserService(em);
        
        HttpSession session = request.getSession();
        Users user = (Users)session.getAttribute("user");
        String userId = user.getUserid();
        user = userService.findUserById(userId);
        int userCoins = 0;
        
        if (user != null) {
            userCoins = user.getCoins();
        }
        
        // Get all items, tree boxes, and biomes
        List<Item> items = itemService.findAllItems();
        List<Treebox> treeBoxes = treeBoxService.findAllTreeboxes();
        List<Biome> biomes = biomeService.findAll();
        
        // Create JSON response
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
        
        // Add user coins to the response
        responseBuilder.add("userCoins", userCoins);
        
        // Add items
        JsonArrayBuilder itemsArray = Json.createArrayBuilder();
        for (Item item : items) {
            JsonObjectBuilder itemBuilder = Json.createObjectBuilder()
                .add("id", item.getItemid())
                .add("name", item.getItemname())
                .add("cost", item.getItemcost())
                .add("image", item.getItemimage())
                .add("description", item.getItemtype() != null ? item.getItemtype() : "")
                .add("isArchived", item.getIsarchived());
            
            itemsArray.add(itemBuilder);
        }
        responseBuilder.add("items", itemsArray);
        
        // Add tree boxes
        JsonArrayBuilder treeBoxesArray = Json.createArrayBuilder();
        for (Treebox treeBox : treeBoxes) {
            JsonObjectBuilder treeBoxBuilder = Json.createObjectBuilder()
                .add("id", treeBox.getTreeboxid())
                .add("name", treeBox.getTreeboxname())
                .add("cost", treeBox.getTreeboxcost())
                .add("image", treeBox.getTreeboximage())
                .add("isArchived", treeBox.getIsarchived());
            
            treeBoxesArray.add(treeBoxBuilder);
        }
        responseBuilder.add("treeBoxes", treeBoxesArray);
        
        // Add biomes
        JsonArrayBuilder biomesArray = Json.createArrayBuilder();
        for (Biome biome : biomes) {
            JsonObjectBuilder biomeBuilder = Json.createObjectBuilder()
                .add("id", biome.getBiomeid())
                .add("name", biome.getBiomename())
                .add("cost", biome.getBiomecost())
                .add("image", biome.getBiomeimage())
                .add("description", biome.getBiomedescription() != null ? biome.getBiomedescription() : "")
                .add("isArchived", biome.getIsarchived());
            
            biomesArray.add(biomeBuilder);
        }
        responseBuilder.add("biomes", biomesArray);
        
        // Set response as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Write response
        try (PrintWriter out = response.getWriter()) {
            out.print(responseBuilder.build());
        }
    }
}