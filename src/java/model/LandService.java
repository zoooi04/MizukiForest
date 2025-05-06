package model;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class LandService {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    Query query;

    public LandService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addLand(Land land) {
        mgr.persist(land);
        return true;
    }

    public Land findLandById(String id) {
        return mgr.find(Land.class, id);
    }

    public boolean deleteLand(String id) {
        Land land = findLandById(id);
        if (land != null) {
            mgr.remove(land);
            return true;
        }
        return false;
    }

    public List<Land> findAll() {
        query = mgr.createNamedQuery("Land.findAll");
        return query.getResultList();
    }

    public boolean updateLand(Land land) {
        Land tempLand = findLandById(land.getLandid());
        if (tempLand != null) {
            tempLand.setLandid(land.getLandid());
            tempLand.setLandname(land.getLandname());
            tempLand.setIsmainland(land.getIsmainland());
            tempLand.setIsdeleted(land.getIsdeleted());
            tempLand.setBiomeid(land.getBiomeid());
            tempLand.setUserid(land.getUserid());
            tempLand.setLandcontentCollection(land.getLandcontentCollection());
            return true;
        }
        return false;
    }

    public String getLastLandIdByPrefix(String prefix) {
        String jpql = "SELECT l.landid FROM Land l WHERE l.landid LIKE :prefix ORDER BY l.landid DESC";
        Query query = mgr.createQuery(jpql);
        query.setParameter("prefix", prefix + "%");
        List<String> landIdList = query.getResultList();
        return landIdList.isEmpty() ? "No land found" : landIdList.get(0);
    }

    public List<Land> findLandByUserId(String userId) {
        TypedQuery<Land> query = mgr.createQuery(
                "SELECT l FROM Land l WHERE l.userid.userid = :userId", Land.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    // LandService    

    public String generateNextLandId(String prefix) {
        String lastId = getLastLandIdByPrefix(prefix);

        if (lastId.equals("No land found")) {
            return prefix + "0000001";
        }

        String numberPart = lastId.substring(prefix.length());
        int number = Integer.parseInt(numberPart);
        number++;

        return String.format("%s%07d", prefix, number);
    }

}
