package model.UserAchievementService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import model.Userachievement;
import model.UserachievementPK;

public class UserAchievementService {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    Query query;

    public UserAchievementService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addUserAchievement(Userachievement ua) {
        mgr.persist(ua);
        return true;
    }

    public Userachievement findByPK(UserachievementPK pk) {
        return mgr.find(Userachievement.class, pk);
    }

    public List<Userachievement> findByUserId(String userId) {
        TypedQuery<Userachievement> query = mgr.createNamedQuery("Userachievement.findByUserid", Userachievement.class);
        query.setParameter("userid", userId);
        return query.getResultList();
    }

    public List<Userachievement> findByAchievementId(String achievementId) {
        TypedQuery<Userachievement> query = mgr.createNamedQuery("Userachievement.findByAchievementid", Userachievement.class);
        query.setParameter("achievementid", achievementId);
        return query.getResultList();
    }

    public boolean deleteUserAchievement(UserachievementPK pk) {
        Userachievement ua = findByPK(pk);
        if (ua != null) {
            mgr.remove(ua);
            return true;
        }
        return false;
    }

    public boolean softDeleteUserAchievement(UserachievementPK pk) {
        Userachievement ua = findByPK(pk);
        if (ua != null) {
            ua.setIsdeleted(true);
            return true;
        }
        return false;
    }

    public List<Userachievement> findAll() {
        query = mgr.createNamedQuery("Userachievement.findAll");
        return query.getResultList();
    }

    public boolean updateUserAchievement(Userachievement updatedUA) {
        Userachievement existingUA = findByPK(updatedUA.getUserachievementPK());
        if (existingUA != null) {
            existingUA.setDatecompleted(updatedUA.getDatecompleted());
            existingUA.setIsdeleted(updatedUA.getIsdeleted());
            existingUA.setAchievement(updatedUA.getAchievement());
            existingUA.setUsers(updatedUA.getUsers());
            return true;
        }
        return false;
    }
}
