package model.UserLevelService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Userlevel;

public class UserLevelService {

    @PersistenceContext
    private EntityManager mgr;

    @Resource
    private Query query;

    public UserLevelService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new UserLevel
    public boolean addUserLevel(Userlevel level) {
        mgr.persist(level);
        return true;
    }

    // Find a UserLevel by ID
    public Userlevel findUserLevelById(Integer levelid) {
        return mgr.find(Userlevel.class, levelid);
    }

    // Delete a UserLevel
    public boolean deleteUserLevel(Integer levelid) {
        Userlevel level = findUserLevelById(levelid);
        if (level != null) {
            mgr.remove(level);
            return true;
        }
        return false;
    }

    // Update a UserLevel
    public boolean updateUserLevel(Userlevel userLevel) {
        Userlevel existing = findUserLevelById(userLevel.getLevelid());
        if (existing != null) {
            existing.setRequiredxp(userLevel.getRequiredxp());
            existing.setIsdeleted(userLevel.getIsdeleted());
            // Note: updating levelrewardList is optional be careful with cascading
            return true;
        }
        return false;
    }

    // Find all user levels
    public List<Userlevel> findAll() {
        query = mgr.createNamedQuery("Userlevel.findAll");
        return query.getResultList();
    }

    // Find levels by XP threshold
    public List<Userlevel> findLevelsByXp(int xp) {
        TypedQuery<Userlevel> query = mgr.createQuery("SELECT u FROM Userlevel u WHERE u.requiredxp <= :xp ORDER BY u.requiredxp DESC", Userlevel.class);
        query.setParameter("xp", xp);
        return query.getResultList();
    }
}
