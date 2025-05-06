package model.RarityDropRateService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import model.Raritydroprate;
import model.RaritydropratePK;

public class RarityDropRateService {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    Query query;

    public RarityDropRateService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addRarityDropRate(Raritydroprate dropRate) {
        mgr.persist(dropRate);
        return true;
    }

    public Raritydroprate findById(String treeboxId, String rarityId) {
        RaritydropratePK pk = new RaritydropratePK(treeboxId, rarityId);
        return mgr.find(Raritydroprate.class, pk);
    }

    public boolean deleteRarityDropRate(String treeboxId, String rarityId) {
        Raritydroprate dropRate = findById(treeboxId, rarityId);
        if (dropRate != null) {
            mgr.remove(dropRate);
            return true;
        }
        return false;
    }

    public boolean updateRarityDropRate(Raritydroprate updatedDropRate) {
        Raritydroprate existing = findById(
            updatedDropRate.getRaritydropratePK().getTreeboxid(),
            updatedDropRate.getRaritydropratePK().getRarityid()
        );
        if (existing != null) {
            existing.setPercentage(updatedDropRate.getPercentage());
            existing.setIsdeleted(updatedDropRate.getIsdeleted());
            existing.setTreebox(updatedDropRate.getTreebox());
            existing.setTreerarity(updatedDropRate.getTreerarity());
            return true;
        }
        return false;
    }

    public List<Raritydroprate> findAll() {
        query = mgr.createNamedQuery("Raritydroprate.findAll");
        return query.getResultList();
    }

    public List<Raritydroprate> findByTreeboxId(String treeboxId) {
        TypedQuery<Raritydroprate> query = mgr.createNamedQuery("Raritydroprate.findByTreeboxid", Raritydroprate.class);
        query.setParameter("treeboxid", treeboxId);
        return query.getResultList();
    }

    public List<Raritydroprate> findByRarityId(String rarityId) {
        TypedQuery<Raritydroprate> query = mgr.createNamedQuery("Raritydroprate.findByRarityid", Raritydroprate.class);
        query.setParameter("rarityid", rarityId);
        return query.getResultList();
    }

    public List<Raritydroprate> findByPercentage(String percentage) {
        TypedQuery<Raritydroprate> query = mgr.createNamedQuery("Raritydroprate.findByPercentage", Raritydroprate.class);
        query.setParameter("percentage", percentage);
        return query.getResultList();
    }

    public List<Raritydroprate> findByIsDeleted(boolean isDeleted) {
        TypedQuery<Raritydroprate> query = mgr.createNamedQuery("Raritydroprate.findByIsdeleted", Raritydroprate.class);
        query.setParameter("isdeleted", isDeleted);
        return query.getResultList();
    }
}
