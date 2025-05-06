package control.forest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
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
import model.Userinventoryitem;
import model.UserInventoryItemService;
import model.TreeService;
import model.Users;
import model.LandContentService;
import model.Landcontent;
import model.UserService.UserService;

public class SearchForestData extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    UserTransaction utx;

    Query query;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users test = (Users)session.getAttribute("user");
        if (test== null) {
            response.sendRedirect("/MizukiForest/index.jsp");
            return;
        }
        
        UserService us = new UserService(mgr);
        String userId = request.getParameter("friendId");
        System.out.println("the user id of friend is: "+ userId);
        Users user = us.findUserById(userId);
        if(user==null){
            session.setAttribute("error", "UserID not found!");
            response.sendRedirect("/MizukiForest/GetUserForestData");
            return;
        }
        
        if(user.getUserid().equals(test.getUserid())){
            session.setAttribute("error2", "Cannot search yourself!");
            response.sendRedirect("/MizukiForest/GetUserForestData");
            return;
        }

        // Step 2: Use services with injected EntityManager
        LandService landService = new LandService(mgr);
        UserInventoryItemService userInventoryService = new UserInventoryItemService(mgr);
        BiomeService biomeService = new BiomeService(mgr);

        // Step 3: Get LAND records by user ID
        List<Land> landList = landService.findLandByUserId(userId);

        // Step 4: Get USERINVENTORYITEM records by user ID
        List<Userinventoryitem> inventoryList = userInventoryService.findByUserId(userId);

        // Step 5: Sort USERINVENTORYITEM records into 3 categories
        List<Userinventoryitem> treeOrItemList = new ArrayList<>();
        List<Userinventoryitem> biomeList = new ArrayList<>();
        List<Userinventoryitem> treeboxList = new ArrayList<>();

        for (Userinventoryitem item : inventoryList) {
            if (item.getTreeid() != null || item.getItemid() != null) {
                treeOrItemList.add(item);
            }
            if (item.getTreeboxid() != null) {
                treeboxList.add(item);
            }
        }

        // Step 6: Get all Biomes
        List<Biome> biomeAllList = biomeService.findAll();
        
        
        //step 7
        TreeService ts = new TreeService(mgr);
        List<Tree> AllTrees = ts.findAllTrees();
        
        //step 8
        ItemService is = new ItemService(mgr);
        List<Item> AllItems = is.findAllItems();
        
        LandContentService ls = new LandContentService(mgr);
        List<Landcontent> AllLandContents = ls.findAll();

        // Step 7: Store in session
        session.setAttribute("landList", landList);
        session.setAttribute("userInventoryList", inventoryList);
        session.setAttribute("treeOrItemList", treeOrItemList);
        session.setAttribute("biomeInventoryList", biomeList);
        session.setAttribute("treeboxInventoryList", treeboxList);
        session.setAttribute("biomeAllList", biomeAllList);
        session.setAttribute("allTreeList", AllTrees);
        session.setAttribute("allItemList", AllItems);
        session.setAttribute("AllLandContents", AllLandContents);
        
        session.setAttribute("friend", user);

        System.out.println("User land list size: " + landList.size());
        System.out.println("total inventory item : " + inventoryList.size());
        System.out.println("item and tree: " + treeOrItemList.size());
        System.out.println("total tree box : " + treeboxList.size());
        System.out.println("total biome: " + biomeAllList.size());

        // Step 8: Redirect to JSP
        response.sendRedirect(request.getContextPath() + "/view/forest/VisitFriend.jsp");
    }
}
