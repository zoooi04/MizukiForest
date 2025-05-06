package model.LevelRewardService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Levelreward;

public class LevelRewardService {

    @PersistenceContext
    private EntityManager mgr;

    @Resource
    private Query query;

    public LevelRewardService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new level reward
    public boolean addLevelReward(Levelreward reward) {
        mgr.persist(reward);
        return true;
    }

    // Find a level reward by ID
    public Levelreward findLevelRewardById(String rewardId) {
        return mgr.find(Levelreward.class, rewardId);
    }

    // Delete a level reward
    public boolean deleteLevelReward(String rewardId) {
        Levelreward reward = findLevelRewardById(rewardId);
        if (reward != null) {
            mgr.remove(reward);
            return true;
        }
        return false;
    }

    // Update a level reward
    public boolean updateLevelReward(Levelreward reward) {
        Levelreward existing = findLevelRewardById(reward.getLevelrewardid());
        if (existing != null) {
            existing.setQuantity(reward.getQuantity());
            existing.setIsdeleted(reward.getIsdeleted());
            existing.setItemid(reward.getItemid());
            existing.setTreeboxid(reward.getTreeboxid());
            existing.setLevelid(reward.getLevelid());
            return true;
        }
        return false;
    }

    // Find all level rewards
    public List<Levelreward> findAll() {
        query = mgr.createNamedQuery("Levelreward.findAll");
        return query.getResultList();
    }

    // Find rewards by level ID
    public List<Levelreward> findRewardsByLevelId(Integer levelId) {
        TypedQuery<Levelreward> query = mgr.createQuery(
            "SELECT l FROM Levelreward l WHERE l.levelid.levelid = :levelId", Levelreward.class
        );
        query.setParameter("levelId", levelId);
        return query.getResultList();
    }
}
