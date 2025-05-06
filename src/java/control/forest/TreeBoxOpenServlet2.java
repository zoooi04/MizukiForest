package control.forest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import model.Raritydroprate;
import model.Tree;
import model.Treerarity;
import model.Userinventoryitem;
import model.RarityDropRateService.RarityDropRateService;
import model.UserInventoryItemService;
import model.TreeService;
import model.UserService.UserService;
import model.Users;

@WebServlet(name = "TreeBoxOpenServlet2", urlPatterns = {"/TreeBoxOpenServlet2"})
public class TreeBoxOpenServlet2 extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Starting TreeBoxOpenServlet processRequest method");
        response.setContentType("application/json;charset=UTF-8");

        RarityDropRateService rarityDropRateService = new RarityDropRateService(em);
        TreeService treeService = new TreeService(em);
        
        System.out.println("Services initialized");

        String treeboxId = request.getParameter("treeboxId");
        HttpSession session = request.getSession();
        
        Users user = (Users)session.getAttribute("user");
        String userId = user.getUserid();
        System.out.println("Request parameters: treeboxId=" + treeboxId + ", userId=" + userId);

        try (PrintWriter out = response.getWriter()) {
            if (treeboxId == null || treeboxId.isEmpty()) {
                System.out.println("ERROR: Missing treeboxId parameter");
                sendErrorResponse(out, "Missing treeboxId parameter");
                return;
            }

            // Get rarity drop rates for this tree box
            System.out.println("Fetching drop rates for treeboxId: " + treeboxId);
            List<Raritydroprate> dropRates = rarityDropRateService.findByTreeboxId(treeboxId);
            System.out.println("Found " + (dropRates != null ? dropRates.size() : 0) + " drop rates");

            if (dropRates == null || dropRates.isEmpty()) {
                System.out.println("ERROR: No drop rates found for this tree box");
                sendErrorResponse(out, "No drop rates found for this tree box");
                return;
            }

            // Perform the gacha to select a rarity based on percentages
            System.out.println("Selecting rarity by gacha mechanism");
            Raritydroprate selectedDropRate = selectRarityByGacha(dropRates);

            if (selectedDropRate == null) {
                System.out.println("ERROR: Failed to select a rarity");
                sendErrorResponse(out, "Failed to select a rarity");
                return;
            }

            // Get the selected rarity ID
            String selectedRarityId = selectedDropRate.getRaritydropratePK().getRarityid();
            Treerarity selectedRarity = selectedDropRate.getTreerarity();
            System.out.println("Selected rarity: ID=" + selectedRarityId
                    + ", Name=" + (selectedRarity != null ? selectedRarity.getRarityname() : "Unknown"));

            // Get all trees of the selected rarity
            System.out.println("Fetching all trees");
            List<Tree> allTrees = treeService.findAllTrees();
            System.out.println("Found " + allTrees.size() + " total trees");

            List<Tree> treesOfSelectedRarity = allTrees.stream()
                    .filter(tree -> tree.getRarityid() != null
                    && tree.getRarityid().getRarityid().equals(selectedRarityId))
                    .collect(Collectors.toList());
            System.out.println("Found " + treesOfSelectedRarity.size() + " trees with selected rarity");

            if (treesOfSelectedRarity.isEmpty()) {
                System.out.println("ERROR: No trees found for the selected rarity");
                sendErrorResponse(out, "No trees found for the selected rarity");
                return;
            }

            // Select a random tree from the filtered list
            System.out.println("Selecting random tree from available trees");
            Tree selectedTree = selectRandomTree(treesOfSelectedRarity);

            if (selectedTree == null) {
                System.out.println("ERROR: Failed to select a tree");
                sendErrorResponse(out, "Failed to select a tree");
                return;
            }
            System.out.println("Selected tree: ID=" + selectedTree.getTreeid() + ", Name=" + selectedTree.getTreename());

           
            // Build the JSON response with all drop rates and selected tree/rarity
            System.out.println("Building JSON response");
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

            // Add all rarities with their drop rates
            JsonArrayBuilder raritiesArray = Json.createArrayBuilder();
            for (Raritydroprate dropRate : dropRates) {
                Treerarity rarity = dropRate.getTreerarity();
                JsonObjectBuilder rarityObj = Json.createObjectBuilder()
                        .add("rarityId", dropRate.getRaritydropratePK().getRarityid())
                        .add("rarityName", rarity != null ? rarity.getRarityname() : "Unknown")
                        .add("percentage", dropRate.getPercentage().doubleValue());
                raritiesArray.add(rarityObj);
            }

            // Add selected tree information
            JsonObjectBuilder selectedTreeObj = Json.createObjectBuilder()
                    .add("treeId", selectedTree.getTreeid())
                    .add("treeName", selectedTree.getTreename())
                    .add("treeDescription", selectedTree.getTreedescription() != null ? selectedTree.getTreedescription() : "")
                    .add("imagePath", "/media/images/tree/" + selectedTree.getTreename() + ".png");

            // Add selected rarity information
            JsonObjectBuilder selectedRarityObj = Json.createObjectBuilder()
                    .add("rarityId", selectedRarityId)
                    .add("rarityName", selectedRarity != null ? selectedRarity.getRarityname() : "Unknown");

            // Build the complete response
            JsonObject finalResponse = responseBuilder
                    .add("success", true)
                    .add("allRarities", raritiesArray)
                    .add("selectedRarity", selectedRarityObj)
                    .add("selectedTree", selectedTreeObj)
                    .build();

            System.out.println("Sending successful JSON response");
            out.print(finalResponse.toString());
            System.out.println("Response sent successfully");
        } catch (Exception e) {
            System.out.println("ERROR in processRequest: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print(Json.createObjectBuilder()
                        .add("success", false)
                        .add("error", "Server error: " + e.getMessage())
                        .build().toString());
            }
        }
        System.out.println("TreeBoxOpenServlet processRequest completed");
    }

    private Raritydroprate selectRarityByGacha(List<Raritydroprate> dropRates) {
        System.out.println("Starting gacha selection process");

        // Sum up all percentages to ensure they total 100%
        double totalPercentage = 0;
        for (Raritydroprate dropRate : dropRates) {
            totalPercentage += dropRate.getPercentage().doubleValue();
        }
        System.out.println("Total percentage of all rarities: " + totalPercentage);

        // Generate a random number between 0 and the total percentage
        Random random = new Random();
        double randomValue = random.nextDouble() * totalPercentage;
        System.out.println("Generated random value for gacha: " + randomValue);

        // Use the random value to select a rarity based on its drop rate
        double cumulativePercentage = 0;
        for (Raritydroprate dropRate : dropRates) {
            double percentage = dropRate.getPercentage().doubleValue();
            cumulativePercentage += percentage;
            System.out.println("Checking rarity: " + dropRate.getRaritydropratePK().getRarityid()
                    + ", percentage: " + percentage
                    + ", cumulative: " + cumulativePercentage);

            if (randomValue <= cumulativePercentage) {
                System.out.println("Selected rarity: " + dropRate.getRaritydropratePK().getRarityid()
                        + " (random value " + randomValue + " <= cumulative " + cumulativePercentage + ")");
                return dropRate;
            }
        }

        // Fallback to the last rarity if something went wrong
        System.out.println("No rarity selected through normal process, using fallback to last rarity");
        return dropRates.isEmpty() ? null : dropRates.get(dropRates.size() - 1);
    }

    private Tree selectRandomTree(List<Tree> trees) {
        if (trees == null || trees.isEmpty()) {
            System.out.println("Cannot select random tree: tree list is null or empty");
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(trees.size());
        Tree selected = trees.get(randomIndex);
        System.out.println("Selected random tree: index=" + randomIndex
                + " out of " + trees.size() + " trees, tree ID=" + selected.getTreeid());
        return selected;
    }

    private void sendErrorResponse(PrintWriter out, String errorMessage) {
        System.out.println("Sending error response: " + errorMessage);
        out.print(Json.createObjectBuilder()
                .add("success", false)
                .add("error", errorMessage)
                .build().toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("TreeBoxOpenServlet - doGet called");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("TreeBoxOpenServlet - doPost called");
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "TreeBoxOpenServlet - Handles tree box opening with gacha mechanics";
    }

    /**
     * Generates the next sequential inventory item ID in the format IVAA0001,
     * IVAA0002, etc.
     *
     * @param service The UserInventoryItemService to query existing IDs
     * @return The next available ID
     */
    private String generateNextInventoryItemId(UserInventoryItemService service) {
        System.out.println("Generating next inventory item ID");
        // Get all inventory items
        List<Userinventoryitem> allItems = service.findAll();
        System.out.println("Found " + allItems.size() + " total inventory items");

        int maxNumber = 0;
        String prefix = "IVAA";

        // Find the highest number
        for (Userinventoryitem item : allItems) {
            String id = item.getInventoryitemid();
            if (id != null && id.startsWith(prefix) && id.length() >= 8) {
                try {
                    // Extract the numeric part (last 4 digits)
                    String numericPart = id.substring(4);
                    int num = Integer.parseInt(numericPart);
                    if (num > maxNumber) {
                        maxNumber = num;
                    }
                } catch (NumberFormatException e) {
                    // Skip if not a valid number format
                    System.out.println("Skipping invalid inventory ID format: " + id);
                    continue;
                }
            }
        }

        System.out.println("Current maximum inventory number: " + maxNumber);

        // Generate the next number
        int nextNumber = maxNumber + 1;

        // Format with leading zeros to ensure 4 digits
        String newId = prefix + String.format("%04d", nextNumber);
        System.out.println("Generated new inventory ID: " + newId);
        return newId;
    }
}