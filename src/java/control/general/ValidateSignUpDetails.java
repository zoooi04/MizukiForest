package control.general;

import java.time.Year;
import model.Users;
import model.UserService.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import model.Biome;
import model.Land;
import model.LandService;
import model.Tree;
import model.UserInventoryItemService;
import model.Userinventoryitem;

@WebServlet(name = "ValidateSignUpDetails", urlPatterns = {"/signup"})
public class ValidateSignUpDetails extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService(mgr);
        String name = request.getParameter("userName");
        String birthday = request.getParameter("userBirthday");
        String email = request.getParameter("userEmail");
        String password = request.getParameter("userPw");

        System.out.println("Received Sign-Up Data:");
        System.out.println("Name: " + name);
        System.out.println("Birthday: " + birthday);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        try {
            String lastRecentID = us.getLastUserId();
            int number = Integer.parseInt(lastRecentID.substring(3)) + 1;
            String newID = String.format("U%02d%05d", java.time.Year.now().getValue() % 100, number);

            Date parsedBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            Users user = new Users(newID, name, password, email, parsedBirthday, 500, 0, 0, 1, 0, new Date(), true, true, false);

            utx.begin();
            mgr.persist(user);

            UserInventoryItemService inventoryService = new UserInventoryItemService(mgr);

            // Free one Biome
            Userinventoryitem biomeItem = new Userinventoryitem();
            biomeItem.setInventoryitemid(inventoryService.generateNextInventoryItemId());
            biomeItem.setUserid(user);
            biomeItem.setBiomeid(new Biome("B0000001"));
            biomeItem.setQuantity(1);
            biomeItem.setIsdeleted(false);
            inventoryService.addUserInventoryItem(biomeItem);

            // Free 4 Trees
            for (String treeId : new String[]{"T0000001", "T0000002", "T0000003", "T0000004"}) {
                Userinventoryitem treeItem = new Userinventoryitem();
                treeItem.setInventoryitemid(inventoryService.generateNextInventoryItemId());
                treeItem.setUserid(user);
                treeItem.setTreeid(new Tree(treeId));
                treeItem.setQuantity(1);
                treeItem.setIsdeleted(false);
                inventoryService.addUserInventoryItem(treeItem);

                System.out.println("Generated Tree Item ID: " + treeItem.getInventoryitemid());

            }

            // Free one empty land
            LandService landService = new LandService(mgr);
            Land land = new Land();
            land.setLandid(landService.generateNextLandId("L"));
            land.setUserid(user);
            land.setBiomeid(new Biome("B0000001"));
            land.setLandname("Andrew Forest");
            land.setIsmainland(true);
            land.setIsdeleted(false);
            landService.addLand(land);

            utx.commit();

            System.out.println("Generated User ID: " + newID);
            System.out.println("Generated Biome Item ID: " + biomeItem.getInventoryitemid());
            System.out.println("Generated Land ID: " + land.getLandid());

            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/GetUserForestData");

        } catch (Exception ex) {
            System.out.println("Error during sign-up: " + ex.getMessage());
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/view/general/signup.jsp");
        }
    }

}
