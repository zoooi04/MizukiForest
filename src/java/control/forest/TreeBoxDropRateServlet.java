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
import model.RarityDropRateService.RarityDropRateService;
import model.TreeRarityService.TreeRarityService;
import model.Raritydroprate;
import model.Treerarity;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "TreeBoxDropRateServlet", urlPatterns = {"/treeBoxDropRates"})
public class TreeBoxDropRateServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    /**
     * Handles the HTTP GET request for tree box drop rates
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the tree box ID from the request parameter
        String treeboxId = request.getParameter("treeboxId");
        
        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Get a PrintWriter for writing the response
        PrintWriter out = response.getWriter();

        // Initialize services
        RarityDropRateService dropRateService = new RarityDropRateService(em);
        TreeRarityService rarityService = new TreeRarityService(em);
        
        try {
            // Get all rarities
            List<Treerarity> allRarities = rarityService.findAll();
            
            // Get drop rates for the specified tree box
            List<Raritydroprate> dropRates = dropRateService.findByTreeboxId(treeboxId);
            
            // Map to store rarity ID -> rarity name mapping
            Map<String, String> rarityMap = new HashMap<>();
            for (Treerarity rarity : allRarities) {
                rarityMap.put(rarity.getRarityid(), rarity.getRarityname());
            }
            
            // Create JSON array for response
            JSONArray jsonArray = new JSONArray();
            
            // Fill with drop rate data
            for (Raritydroprate rate : dropRates) {
                // Skip if deleted
                if (rate.getIsdeleted()) continue;
                
                JSONObject rateObj = new JSONObject();
                String rarityId = rate.getRaritydropratePK().getRarityid();
                String rarityName = rarityMap.getOrDefault(rarityId, "Unknown");
                
                rateObj.put("rarityId", rarityId);
                rateObj.put("rarityName", rarityName);
                rateObj.put("percentage", rate.getPercentage());
                
                jsonArray.put(rateObj);
            }
            
            // Write JSON array to response
            out.print(jsonArray.toString());
            
        } catch (Exception e) {
            // Log error
            getServletContext().log("Error retrieving drop rates", e);
            
            // Return error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorObj = new JSONObject();
            errorObj.put("error", "Failed to retrieve drop rates: " + e.getMessage());
            out.print(errorObj.toString());
        } finally {
            out.flush();
        }
    }
}