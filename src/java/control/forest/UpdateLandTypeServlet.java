/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.forest;

import model.*;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
public class UpdateLandTypeServlet extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    UserTransaction utx;

    Query query;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonRequest = jsonReader.readObject();

        String landId = jsonRequest.getString("landId");
        boolean isMainLand = jsonRequest.getBoolean("isMainLand");

        LandService ls = new LandService(mgr);
        Land land = ls.findLandById(landId);
        land.setIsmainland(isMainLand);
        ls.updateLand(land);

        HttpSession session = request.getSession();
        // Step 1: Hardcoded User ID
        Users user = (Users)session.getAttribute("user");
        String userId = user.getUserid();

        List<Land> landList = ls.findLandByUserId(userId);

        for (Land l : landList) {
            if (l.getLandid() != land.getLandid()) {
                l.setIsmainland(false);
                ls.updateLand(l);
            }
        }

        // Create JSON response using Jakarta JSON
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("success", true)
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();

    }

}
