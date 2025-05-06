package control.forest;

import model.*;
import model.Land;
import model.Landcontent;
import model.Focussession;
import model.Userinventoryitem;
import model.FocusSessionService.FocusSessionService;
import model.LandService;
import model.LandContentService;
import model.UserInventoryItemService;

// Import missing persistence classes
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.MultipartConfig;
import model.UserService.UserService;
import org.json.JSONObject;

/**
 * Servlet for handling focus session data submissions
 */
@MultipartConfig
public class FocusSessionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String WITHERED_TREE_ID = "T9999999";
    private static final BigDecimal MIN_FOCUS_DURATION = BigDecimal.valueOf(30 * 60); // 30 minutes in seconds

    private FocusSessionService focusSessionService;
    private LandService landService;
    private LandContentService landContentService;
    private UserInventoryItemService userInventoryItemService;
    private TreeService treeService;
    private Random random;
    private TreeBoxService tbs;

    // Add UserTransaction for transaction management
    @Resource
    private UserTransaction ut;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Handle POST requests from the focus session JavaScript
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n\nDEBUG: ========== NEW FOCUS SESSION REQUEST ==========");
        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("DEBUG: Response content type set to JSON");

        System.out.println(
                "DEBUG: Initializing services...");
        focusSessionService = new FocusSessionService(entityManager);

        System.out.println(
                "DEBUG: FocusSessionService initialized");

        landService = new LandService(entityManager);

        System.out.println(
                "DEBUG: LandService initialized");

        landContentService = new LandContentService(entityManager);

        System.out.println(
                "DEBUG: LandContentService initialized");

        userInventoryItemService = new UserInventoryItemService(entityManager);

        System.out.println(
                "DEBUG: UserInventoryItemService initialized");

        treeService = new TreeService(entityManager);

        System.out.println(
                "DEBUG: TreeService initialized");

        tbs = new TreeBoxService(entityManager);

        System.out.println(
                "DEBUG: TreeBoxService initialized");

        random = new Random();

        System.out.println(
                "DEBUG: All services initialized successfully");

        try {
            // Log all parameters for debugging
            System.out.println("DEBUG: Received parameters:");
            request.getParameterMap().forEach((key, value)
                    -> System.out.println("DEBUG: Parameter [" + key + "]: " + String.join(", ", value)));

            // Get the current user from the session
            Users user = (Users) request.getSession().getAttribute("user");
            System.out.println("DEBUG: Retrieved user from session: " + (user != null ? "User ID: " + user.getUserid() : "NULL"));

            if (user == null) {
                System.err.println("DEBUG: User not logged in, sending error response");
                sendErrorResponse(response, "User not logged in");
                return;
            }

            UserService userService = new UserService(entityManager);
            System.out.println("DEBUG: UserService created");

            // Extract form data with null checks
            String sessionType = request.getParameter("sessionType");
            System.out.println("DEBUG: Session type parameter: " + sessionType);

            if (sessionType == null || sessionType.isEmpty()) {
                System.err.println("DEBUG: Session type is required but missing or empty");
                sendErrorResponse(response, "Session type is required");
                return;
            }

            // Fix for the duration parameter
            BigDecimal duration;
            String durationParam = request.getParameter("duration");
            System.out.println("DEBUG: Duration parameter: " + durationParam);

            if (durationParam != null && !durationParam.isEmpty()) {
                try {
                    duration = new BigDecimal(durationParam);
                    System.out.println("DEBUG: Parsed duration: " + duration);
                } catch (NumberFormatException e) {
                    // Handle case where duration isn't a valid number
                    System.err.println("DEBUG: Invalid duration format: " + durationParam);
                    sendErrorResponse(response, "Invalid duration format: " + durationParam);
                    return;
                }
            } else {
                // Handle case where duration is missing
                System.err.println("DEBUG: Duration is required but missing");
                sendErrorResponse(response, "Duration is required");
                return;
            }

            // Safe parsing of integer parameters
            int treeBoxesObtained;
            String treeBoxesParam = request.getParameter("treeBoxesObtained");
            System.out.println("DEBUG: Tree boxes obtained parameter: " + treeBoxesParam);

            if (treeBoxesParam != null && !treeBoxesParam.isEmpty()) {
                try {
                    treeBoxesObtained = Integer.parseInt(treeBoxesParam);
                    System.out.println("DEBUG: Parsed treeBoxesObtained: " + treeBoxesObtained);
                } catch (NumberFormatException e) {
                    System.err.println("DEBUG: Invalid treeBoxesObtained format: " + treeBoxesParam);
                    sendErrorResponse(response, "Invalid treeBoxesObtained format: " + treeBoxesParam);
                    return;
                }
            } else {
                System.err.println("DEBUG: treeBoxesObtained is required but missing");
                sendErrorResponse(response, "treeBoxesObtained is required");
                return;
            }

            String treeBoxId = request.getParameter("treeBoxId");
            System.out.println("DEBUG: Tree box ID parameter: " + treeBoxId);

            if (treeBoxId == null || treeBoxId.isEmpty()) {
                System.err.println("DEBUG: treeBoxId is required but missing");
                sendErrorResponse(response, "treeBoxId is required");
                return;
            }

            int treeBoxQuantity;
            String quantityParam = request.getParameter("treeBoxQuantity");
            System.out.println("DEBUG: Tree box quantity parameter: " + quantityParam);

            if (quantityParam != null && !quantityParam.isEmpty()) {
                try {
                    treeBoxQuantity = Integer.parseInt(quantityParam);
                    System.out.println("DEBUG: Parsed treeBoxQuantity: " + treeBoxQuantity);
                } catch (NumberFormatException e) {
                    System.err.println("DEBUG: Invalid treeBoxQuantity format: " + quantityParam);
                    sendErrorResponse(response, "Invalid treeBoxQuantity format: " + quantityParam);
                    return;
                }
            } else {
                System.err.println("DEBUG: treeBoxQuantity is required but missing");
                sendErrorResponse(response, "treeBoxQuantity is required");
                return;
            }

            // Create a new focus session record
            System.out.println("DEBUG: Creating new focus session record");
            Focussession focusSession = new Focussession();

            String sessionId = generateSessionId();
            System.out.println("DEBUG: Generated session ID: " + sessionId);
            focusSession.setSessionid(sessionId);

            focusSession.setUserid(user);
            System.out.println("DEBUG: Set user ID: " + user.getUserid());

            focusSession.setSessiontype(sessionType);
            System.out.println("DEBUG: Set session type: " + sessionType);

            focusSession.setDuration(duration);
            System.out.println("DEBUG: Set duration: " + duration);

            // Handle Pomodoro-specific parameters if applicable
            if ("Pomodoro".equals(sessionType)) {
                System.out.println("DEBUG: Processing Pomodoro-specific parameters");
                String minorBreak = request.getParameter("pomodoroMinorBreak");
                String majorBreak = request.getParameter("pomodoroMajorBreak");
                System.out.println("DEBUG: Pomodoro minor break: " + minorBreak);
                System.out.println("DEBUG: Pomodoro major break: " + majorBreak);

                if (minorBreak != null && !minorBreak.isEmpty()) {
                    try {
                        int minorBreakInt = Integer.parseInt(minorBreak);
                        focusSession.setPomodorominorbreak(minorBreakInt);
                        System.out.println("DEBUG: Set pomodoro minor break: " + minorBreakInt);
                    } catch (NumberFormatException e) {
                        // Just log instead of failing the whole request
                        System.err.println("DEBUG: Invalid minor break format: " + minorBreak);
                    }
                }

                if (majorBreak != null && !majorBreak.isEmpty()) {
                    try {
                        int majorBreakInt = Integer.parseInt(majorBreak);
                        focusSession.setPomodoromajorbreak(majorBreakInt);
                        System.out.println("DEBUG: Set pomodoro major break: " + majorBreakInt);
                    } catch (NumberFormatException e) {
                        // Just log instead of failing the whole request
                        System.err.println("DEBUG: Invalid major break format: " + majorBreak);
                    }
                }
            } else {
                focusSession.setPomodorominorbreak(null);
                focusSession.setPomodoromajorbreak(null);
            }

            focusSession.setTreeboxesobtained(treeBoxesObtained);
            System.out.println("DEBUG: Set tree boxes obtained: " + treeBoxesObtained);

            focusSession.setIsdeleted(false);
            System.out.println("DEBUG: Set isDeleted: false");

            // Start a transaction for all database operations
            try {
                // Begin the transaction
                ut.begin();
                System.out.println("DEBUG: Transaction started");

                // Determine if session was completed or terminated early
                System.out.println("DEBUG: Checking if duration is less than minimum required (" + MIN_FOCUS_DURATION + ")");
                System.out.println("DEBUG: Comparison result: " + duration.compareTo(MIN_FOCUS_DURATION));

                if (duration.compareTo(MIN_FOCUS_DURATION) < 0) {
                    focusSession.setSessionstatus("Terminated");
                    System.out.println("DEBUG: Session Terminated as duration < minimum required");

                    // Plant a withered tree in user's land
                    System.out.println("DEBUG: Planting withered tree as penalty");
                    plantWitheredTree(user);
                } else {
                    focusSession.setSessionstatus("COMPLETED");
                    System.out.println("DEBUG: Session COMPLETED successfully");

                    // Add tree boxes to user's inventory
                    System.out.println("DEBUG: Adding tree boxes to inventory - ID: " + treeBoxId + ", Quantity: " + treeBoxQuantity);
                    addTreeBoxesToInventory(user, treeBoxId, treeBoxQuantity);
                }

                // Save the focus session
                System.out.println("DEBUG: Saving focus session to database");
                System.out.println("The saving focus session is " + focusSession);
                focusSessionService.addFocusSession(focusSession);
                System.out.println("DEBUG: Focus session saved successfully with ID: " + focusSession.getSessionid());

                // Commit the transaction
                ut.commit();
                System.out.println("DEBUG: Transaction committed successfully");

            } catch (Exception e) {
                // Try to rollback the transaction in case of any exception
                System.err.println("DEBUG: Exception in transaction, attempting rollback");
                try {
                    ut.rollback();
                    System.out.println("DEBUG: Transaction rolled back successfully");
                } catch (Exception rollbackEx) {
                    System.err.println("DEBUG: Rollback failed: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                }
                // Re-throw to be caught by outer handler
                throw e;
            }

            // Prepare success response
            System.out.println("DEBUG: Preparing success response");
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            jsonResponse.put("sessionId", focusSession.getSessionid());
            jsonResponse.put("status", focusSession.getSessionstatus());

            if ("COMPLETED".equals(focusSession.getSessionstatus())) {
                jsonResponse.put("reward", treeBoxId);
                jsonResponse.put("quantity", treeBoxQuantity);
                System.out.println("DEBUG: Added reward details to response");
            } else {
                jsonResponse.put("witheredTree", true);
                System.out.println("DEBUG: Added withered tree flag to response");
            }

            // Send response
            System.out.println("DEBUG: Sending JSON response: " + jsonResponse.toString());
            response.getWriter().write(jsonResponse.toString());
            System.out.println("DEBUG: Response sent successfully");

        } catch (Exception e) {
            System.err.println("DEBUG: Exception occurred during processing:");
            e.printStackTrace();
            System.err.println("DEBUG: Sending error response");
            sendErrorResponse(response, "Error processing focus session: " + e.getMessage());
        }

        System.out.println("DEBUG: ========== END OF FOCUS SESSION REQUEST ==========\n");
    }

    /**
     * Generate a new session ID (format: SAA00001)
     */
    private String generateSessionId() {
        System.out.println("DEBUG: Generating new session ID");
        // Get the largest existing ID
        String largestId = focusSessionService.getLastSessionId();
        System.out.println("DEBUG: Last session ID from database: " + largestId);

        if (largestId == null || largestId.isEmpty() || "No session found".equals(largestId)) {
            System.out.println("DEBUG: No existing session IDs found, using default: SAA00001");
            return "SAA00001";
        }

        // Extract the numerical part
        String numericPart = largestId.substring(3);
        System.out.println("DEBUG: Extracted numeric part: " + numericPart);

        int nextNum = Integer.parseInt(numericPart) + 1;
        System.out.println("DEBUG: Next number: " + nextNum);

        // Format the new ID
        String newId = "SAA" + String.format("%05d", nextNum);
        System.out.println("DEBUG: Generated new session ID: " + newId);
        return newId;
    }

    /**
     * Plant a withered tree in the user's land as punishment for early
     * termination
     */
    private void plantWitheredTree(Users user) throws Exception {
        System.out.println("DEBUG: Starting plantWitheredTree() for user: " + user.getUserid());

        // Get all lands owned by the user
        List<Land> userLands = landService.findLandByUserId(user.getUserid());
        System.out.println("DEBUG: Found " + userLands.size() + " lands for user");

        if (userLands.isEmpty()) {
            System.out.println("DEBUG: User has no lands to plant withered tree in. Exiting method.");
            // No lands to plant a tree in
            return;
        }

        // Select a random land if user has multiple
        Land selectedLand = userLands.size() > 1
                ? userLands.get(random.nextInt(userLands.size()))
                : userLands.get(0);
        System.out.println("DEBUG: Selected land ID: " + selectedLand.getLandid());

        // Get all land contents for the selected land
        System.out.println("DEBUG: Retrieving all land contents");
        List<Landcontent> allContents = landContentService.findAll();
        System.out.println("DEBUG: Total land contents in database: " + allContents.size());

        List<Landcontent> existingContents = allContents.stream()
                .filter(content -> content.getLandid().getLandid().equals(selectedLand.getLandid()) && !content.getIsdeleted())
                .collect(Collectors.toList());
        System.out.println("DEBUG: Found " + existingContents.size() + " existing contents in selected land");

        // Find available coordinates
        System.out.println("DEBUG: Finding available coordinates");
        List<Coordinate> occupiedCoordinates = existingContents.stream()
                .map(content -> new Coordinate(content.getXcoord(), content.getYcoord()))
                .collect(Collectors.toList());
        System.out.println("DEBUG: Occupied coordinates: " + occupiedCoordinates.size());

        List<Coordinate> availableCoordinates = new ArrayList<>();

        System.out.println("DEBUG: Generating all possible coordinates on 8x8 grid");
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                Coordinate coord = new Coordinate(x, y);
                if (!occupiedCoordinates.contains(coord)) {
                    availableCoordinates.add(coord);
                }
            }
        }
        System.out.println("DEBUG: Available coordinates: " + availableCoordinates.size());

        if (availableCoordinates.isEmpty()) {
            System.out.println("DEBUG: No space available for withered tree. Exiting method.");
            // No space available for a withered tree
            return;
        }

        // Select a random available coordinate
        int randomIndex = random.nextInt(availableCoordinates.size());
        Coordinate selectedCoord = availableCoordinates.get(randomIndex);
        System.out.println("DEBUG: Selected coordinate for withered tree: (" + selectedCoord.x + ", " + selectedCoord.y + ")");

        // Create a new LandContent record for the withered tree
        System.out.println("DEBUG: Creating new land content for withered tree");
        Landcontent witheredTree = new Landcontent();

        String landContentId = generateLandContentId();
        System.out.println("DEBUG: Generated land content ID: " + landContentId);
        witheredTree.setLandcontentid(landContentId);

        witheredTree.setLandid(selectedLand);
        System.out.println("DEBUG: Looking up tree with ID: " + WITHERED_TREE_ID);
        witheredTree.setTreeid(treeService.findTreeById(WITHERED_TREE_ID));
        System.out.println("the found tree is " + witheredTree);
        witheredTree.setItemid(null);
        witheredTree.setXcoord(selectedCoord.x);
        witheredTree.setYcoord(selectedCoord.y);
        witheredTree.setIsdeleted(false);
        System.out.println("DEBUG: Land content object created with withered tree");

        // Save the new land content
        System.out.println("DEBUG: Saving withered tree to database");
        System.out.println("the witheredtree data is: " + witheredTree);
        landContentService.addLandContent(witheredTree);
        System.out.println("DEBUG: Withered tree saved successfully with ID: " + witheredTree.getLandcontentid());
    }

    /**
     * Generate a new land content ID (format: LCAA0001)
     */
    private String generateLandContentId() {
        System.out.println("DEBUG: Generating new land content ID");
        // Get the largest existing ID
        String largestId = landContentService.getLastLandContentId();
        System.out.println("DEBUG: Last land content ID from database: " + largestId);

        if (largestId == null || largestId.isEmpty() || "No land content found".equals(largestId)) {
            System.out.println("DEBUG: No existing land content IDs found, using default: LCAA0001");
            return "LCAA0001";
        }

        // Extract the numerical part
        String numericPart = largestId.substring(4);
        System.out.println("DEBUG: Extracted numeric part: " + numericPart);

        int nextNum = Integer.parseInt(numericPart) + 1;
        System.out.println("DEBUG: Next number: " + nextNum);

        // Format the new ID
        String newId = "LCAA" + String.format("%04d", nextNum);
        System.out.println("DEBUG: Generated new land content ID: " + newId);
        return newId;
    }

    /**
     * Add tree boxes to the user's inventory
     */
    private void addTreeBoxesToInventory(Users user, String treeBoxId, int quantity) throws Exception {
        System.out.println("DEBUG: Starting addTreeBoxesToInventory() for user: " + user.getUserid() + ", tree box: " + treeBoxId + ", quantity: " + quantity);

        // Check if user already has this tree box type in inventory
        System.out.println("DEBUG: Checking if user already has this tree box in inventory");
        Userinventoryitem existingItem = userInventoryItemService.findTreeBoxInventoryItem(user.getUserid(), treeBoxId);
        System.out.println("DEBUG: Existing inventory item: " + (existingItem != null ? existingItem.getInventoryitemid() : "null"));

        if (existingItem != null) {
            // Update quantity of existing inventory item
            int currentQuantity = existingItem.getQuantity();
            int newQuantity = currentQuantity + quantity;
            System.out.println("DEBUG: Updating existing inventory item. Current quantity: " + currentQuantity + ", New quantity: " + newQuantity);

            existingItem.setQuantity(newQuantity);
            userInventoryItemService.updateUserInventoryItem(existingItem);
            System.out.println("DEBUG: Inventory item updated successfully with ID: " + existingItem.getInventoryitemid());
        } else {
            // Create new inventory item
            System.out.println("DEBUG: Creating new inventory item");
            Userinventoryitem newItem = new Userinventoryitem();

            String inventoryItemId = generateInventoryItemId();
            System.out.println("DEBUG: Generated inventory item ID: " + inventoryItemId);
            newItem.setInventoryitemid(inventoryItemId);

            newItem.setUserid(user);
            System.out.println("DEBUG: Looking up tree box with ID: " + treeBoxId);
            newItem.setTreeboxid(tbs.findTreeboxById(treeBoxId));
            newItem.setQuantity(quantity);
            newItem.setIsdeleted(false);
            System.out.println("DEBUG: New inventory item object created");

            userInventoryItemService.addUserInventoryItem(newItem);
            System.out.println("DEBUG: New inventory item saved successfully with ID: " + newItem.getInventoryitemid());
        }
    }

    /**
     * Generate a new inventory item ID (format: IVAA0001)
     */
    private String generateInventoryItemId() {
        System.out.println("DEBUG: Generating new inventory item ID");
        // This assumes there's a method to get the last inventory item ID
        // You may need to implement this in UserInventoryItemService
        String largestId = getLastInventoryItemId();
        System.out.println("DEBUG: Last inventory item ID from database: " + largestId);

        if (largestId == null || largestId.isEmpty() || "No inventory item found".equals(largestId)) {
            System.out.println("DEBUG: No existing inventory item IDs found, using default: IVAA0001");
            return "IVAA0001";
        }

        // Extract the numerical part
        String numericPart = largestId.substring(4);
        System.out.println("DEBUG: Extracted numeric part: " + numericPart);

        int nextNum = Integer.parseInt(numericPart) + 1;
        System.out.println("DEBUG: Next number: " + nextNum);

        // Format the new ID
        String newId = "IVAA" + String.format("%04d", nextNum);
        System.out.println("DEBUG: Generated new inventory item ID: " + newId);
        return newId;
    }

    /**
     * Helper method to get the last inventory item ID
     */
    private String getLastInventoryItemId() {
        System.out.println("DEBUG: Getting last inventory item ID from database");
        try {
            // This would need to be implemented in UserInventoryItemService
            // For demonstration purposes, we're implementing it here
            String jpql = "SELECT u.inventoryitemid FROM Userinventoryitem u ORDER BY u.inventoryitemid DESC";
            System.out.println("DEBUG: Executing JPQL query: " + jpql);

            javax.persistence.Query query = entityManager.createQuery(jpql);
            query.setMaxResults(1);

            List<String> results = query.getResultList();
            System.out.println("DEBUG: Query returned " + results.size() + " results");

            String result = results.isEmpty() ? "No inventory item found" : results.get(0);
            System.out.println("DEBUG: Last inventory item ID: " + result);
            return result;
        } catch (Exception e) {
            System.err.println("DEBUG: Exception occurred while getting last inventory item ID");
            e.printStackTrace();
            return "No inventory item found";
        }
    }

    /**
     * Send an error response to the client
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        System.out.println("DEBUG: Sending error response: " + message);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", false);
        jsonResponse.put("error", message);

        response.getWriter().write(jsonResponse.toString());
        System.out.println("DEBUG: Error response sent: " + jsonResponse.toString());
    }

    /**
     * Helper class to represent x,y coordinates
     */
    private static class Coordinate {

        private final int x;
        private final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Coordinate)) {
                return false;
            }
            Coordinate other = (Coordinate) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
