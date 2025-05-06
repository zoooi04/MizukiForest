package model.AchievementRewardService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Achievementreward;

public class AchievementRewardService {

    @PersistenceContext
    EntityManager mgr;

    public AchievementRewardService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addAchievementReward(Achievementreward reward) {
        mgr.persist(reward);
        return true;
    }

    public Achievementreward findAchievementRewardById(String id) {
        return mgr.find(Achievementreward.class, id);
    }

    public List<Achievementreward> findAllAchievementRewards() {
        TypedQuery<Achievementreward> query = mgr.createNamedQuery("Achievementreward.findAll", Achievementreward.class);
        return query.getResultList();
    }

    public boolean deleteAchievementReward(String id) {
        Achievementreward reward = findAchievementRewardById(id);
        if (reward != null) {
            mgr.remove(reward);
            return true;
        }
        return false;
    }

    public boolean updateAchievementReward(Achievementreward reward) {
        Achievementreward existing = findAchievementRewardById(reward.getAchievementrewardid());
        if (existing != null) {
            existing.setQuantity(reward.getQuantity());
            existing.setIsdeleted(reward.getIsdeleted());
            existing.setAchievementid(reward.getAchievementid());
            existing.setItemid(reward.getItemid());
            existing.setTreeboxid(reward.getTreeboxid());
            return true;
        }
        return false;
    }

    public List<Achievementreward> findByAchievementId(String achievementId) {
        TypedQuery<Achievementreward> query = mgr.createQuery(
            "SELECT a FROM Achievementreward a WHERE a.achievementid.achievementid = :achievementId", 
            Achievementreward.class
        );
        query.setParameter("achievementId", achievementId);
        return query.getResultList();
    }

    public List<Achievementreward> findNonDeletedRewards() {
        TypedQuery<Achievementreward> query = mgr.createQuery(
            "SELECT a FROM Achievementreward a WHERE a.isdeleted = false", 
            Achievementreward.class
        );
        return query.getResultList();
    }
}
