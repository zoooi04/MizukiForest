package model.AchievementCategoryService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Achievementcategory;

public class AchievementCategoryService {

    @PersistenceContext
    private EntityManager mgr;

    @Resource
    private Query query;

    public AchievementCategoryService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addAchievementCategory(Achievementcategory category) {
        mgr.persist(category);
        return true;
    }

    public Achievementcategory findById(String id) {
        return mgr.find(Achievementcategory.class, id);
    }

    public Achievementcategory findByName(String name) {
        TypedQuery<Achievementcategory> query = mgr.createQuery(
            "SELECT a FROM Achievementcategory a WHERE a.achievementcategoryname = :name", Achievementcategory.class);
        query.setParameter("name", name);
        List<Achievementcategory> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean deleteAchievementCategory(String id) {
        Achievementcategory category = findById(id);
        if (category != null) {
            mgr.remove(category);
            return true;
        }
        return false;
    }

    public boolean updateAchievementCategory(Achievementcategory category) {
        Achievementcategory existing = findById(category.getAchievementcategoryid());
        if (existing != null) {
            existing.setAchievementcategoryname(category.getAchievementcategoryname());
            existing.setIsdeleted(category.getIsdeleted());
            existing.setBadgeid(category.getBadgeid());
            return true;
        }
        return false;
    }

    public List<Achievementcategory> findAll() {
        query = mgr.createNamedQuery("Achievementcategory.findAll");
        return query.getResultList();
    }

    public String getLastAchievementCategoryId() {
        String jpql = "SELECT a.achievementcategoryid FROM Achievementcategory a ORDER BY a.achievementcategoryid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> result = query.getResultList();
        return result.isEmpty() ? "No category found" : result.get(0);
    }
}
