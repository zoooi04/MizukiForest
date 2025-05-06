package control.Achievement;

import java.io.IOException;
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

import model.Achievement;
import model.AchievementService.AchievementService;
import model.Achievementcategory;
import model.AchievementCategoryService.AchievementCategoryService;
import model.Badge;
import model.BadgeService.BadgeService;
import model.Achievementreward;
import model.AchievementRewardService.AchievementRewardService;
import model.Treebox;
import model.TreeBoxService;
import model.Item;
import model.ItemService;
import model.UserAchievementService.UserAchievementService;
import model.Userachievement;
import model.Users;

@WebServlet(name = "AchievementServlet", urlPatterns = {"/AchievementServlet"})
public class AchievementServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();           
            Users user = (Users)session.getAttribute("user");
            
            if(user == null){
//                response.sendRedirect("login.jsp");
                request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
                return;
            }           
            
            String userId = user.getUserid();
            
            // Initialize all services
            AchievementService achievementService = new AchievementService(em);
            AchievementCategoryService categoryService = new AchievementCategoryService(em);
            BadgeService badgeService = new BadgeService(em);
            AchievementRewardService rewardService = new AchievementRewardService(em);
            TreeBoxService treeboxService = new TreeBoxService(em);
            ItemService itemService = new ItemService(em);
            UserAchievementService userAchievementService = new UserAchievementService(em);
            
            // Get all data from services
            List<Achievement> achievementList = achievementService.findAllAchievements();
            List<Achievementcategory> categoryList = categoryService.findAll();
            List<Badge> badgeList = badgeService.findAll();
            List<Achievementreward> rewardList = rewardService.findAllAchievementRewards();
            List<Treebox> treeboxList = treeboxService.findAllTreeboxes();
            List<Item> itemList = itemService.findAllItems(); 
            List<Userachievement> userAchievements = userAchievementService.findByUserId(userId);
            
            // Set data as attributes in the session
            session.setAttribute("achievementList", achievementList);
            session.setAttribute("categoryList", categoryList);
            session.setAttribute("badgeList", badgeList);
            session.setAttribute("rewardList", rewardList);
            session.setAttribute("treeboxList", treeboxList);
            session.setAttribute("itemList", itemList);
            session.setAttribute("userAchievements", userAchievements);
            
            // Redirect to achievement page
            response.sendRedirect(request.getContextPath() + "/view/achievement/AchievementPage.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}