package model.AchievementService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import model.Achievement;

public class AchievementService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public AchievementService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addAchievement(Achievement achievement) {
        mgr.persist(achievement);
        return true;
    }

    public Achievement findAchievementById(String id) {
        return mgr.find(Achievement.class, id);
    }

    public List<Achievement> findAllAchievements() {
        query = mgr.createNamedQuery("Achievement.findAll");
        return query.getResultList();
    }

    public boolean deleteAchievement(String id) {
        Achievement achievement = findAchievementById(id);
        if (achievement != null) {
            mgr.remove(achievement);
            return true;
        }
        return false;
    }

    public boolean updateAchievement(Achievement achievement) {
        Achievement existing = findAchievementById(achievement.getAchievementid());
        if (existing != null) {
            existing.setAchievementname(achievement.getAchievementname());
            existing.setAchievementdescription(achievement.getAchievementdescription());
            existing.setHidden(achievement.getHidden());
            existing.setIsdeleted(achievement.getIsdeleted());
            existing.setAchievementcategoryid(achievement.getAchievementcategoryid());
            existing.setAchievementrewardList(achievement.getAchievementrewardList());
            existing.setUserachievementList(achievement.getUserachievementList());
            return true;
        }
        return false;
    }

    public String getLastAchievementId() {
        String jpql = "SELECT a.achievementid FROM Achievement a ORDER BY a.achievementid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> idList = query.getResultList();
        return idList.isEmpty() ? "No achievement found" : idList.get(0);
    }

    public List<Achievement> findByCategoryId(String categoryId) {
        TypedQuery<Achievement> query = mgr.createQuery(
            "SELECT a FROM Achievement a WHERE a.achievementcategoryid.achievementcategoryid = :categoryId", Achievement.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }
}
