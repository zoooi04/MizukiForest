package model.TreeRarityService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Treerarity;

public class TreeRarityService {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    Query query;

    public TreeRarityService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addTreeRarity(Treerarity treerarity) {
        mgr.persist(treerarity);
        return true;
    }

    public Treerarity findById(String rarityId) {
        return mgr.find(Treerarity.class, rarityId);
    }

    public Treerarity findByName(String name) {
        TypedQuery<Treerarity> query = mgr.createQuery("SELECT t FROM Treerarity t WHERE t.rarityname = :name", Treerarity.class);
        query.setParameter("name", name);
        List<Treerarity> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean deleteTreeRarity(String rarityId) {
        Treerarity treerarity = findById(rarityId);
        if (treerarity != null) {
            mgr.remove(treerarity);
            return true;
        }
        return false;
    }

    public boolean updateTreeRarity(Treerarity treerarity) {
        Treerarity existing = findById(treerarity.getRarityid());
        if (existing != null) {
            existing.setRarityname(treerarity.getRarityname());
            existing.setRaritycolour(treerarity.getRaritycolour());
            existing.setIsarchived(treerarity.getIsarchived());
            existing.setIsdeleted(treerarity.getIsdeleted());
            return true;
        }
        return false;
    }

    public List<Treerarity> findAll() {
        query = mgr.createNamedQuery("Treerarity.findAll");
        return query.getResultList();
    }

    public String getLastRarityId() {
        String jpql = "SELECT t.rarityid FROM Treerarity t ORDER BY t.rarityid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> result = query.getResultList();
        return result.isEmpty() ? "No rarity found" : result.get(0);
    }
}
