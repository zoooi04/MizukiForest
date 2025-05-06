package model;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class LandContentService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public LandContentService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addLandContent(Landcontent content) {
        mgr.persist(content);
        return true;
    }

    public Landcontent findById(String id) {
        return mgr.find(Landcontent.class, id);
    }

    public List<Landcontent> findByLandId(String landId) {
        TypedQuery<Landcontent> query = mgr.createQuery(
                "SELECT l FROM Landcontent l WHERE l.landid.landid = :landId", Landcontent.class);
        query.setParameter("landId", landId);
        return query.getResultList();
    }

    public boolean deleteLandContent(String id) {
        Landcontent content = findById(id);
        if (content != null) {
            mgr.remove(content);
            return true;
        }
        return false;
    }

    public List<Landcontent> findAll() {
        query = mgr.createNamedQuery("Landcontent.findAll");
        return query.getResultList();
    }

    public boolean updateLandContent(Landcontent content) {
        Landcontent existing = findById(content.getLandcontentid());
        if (existing != null) {
            existing.setXcoord(content.getXcoord());
            existing.setYcoord(content.getYcoord());
            existing.setIsdeleted(content.getIsdeleted());
            existing.setItemid(content.getItemid());
            existing.setLandid(content.getLandid());
            existing.setTreeid(content.getTreeid());
            return true;
        }
        return false;
    }

    public String getLastLandContentId() {
        Query query = mgr.createQuery(
                "SELECT l.landcontentid FROM Landcontent l WHERE l.landcontentid LIKE 'LCAA%' ORDER BY l.landcontentid DESC");
        query.setMaxResults(1);

        List<?> results = query.getResultList();
        if (results.isEmpty()) {
            // No existing IDs found, return the first ID in sequence
            return "LCAA0001";
        } else {
            // Return the largest existing ID
            return (String) results.get(0);
        }
    }
    
    public Landcontent findByLandCoord(String landId, int xCoord, int yCoord) {
        TypedQuery<Landcontent> query = mgr.createQuery(
                "SELECT l FROM Landcontent l WHERE l.landid.landid = :landId " +
                "AND l.xcoord = :xCoord AND l.ycoord = :yCoord", 
                Landcontent.class);
        query.setParameter("landId", landId);
        query.setParameter("xCoord", xCoord);
        query.setParameter("yCoord", yCoord);
        List<Landcontent> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
