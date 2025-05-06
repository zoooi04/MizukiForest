package model;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class UserInventoryItemService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public UserInventoryItemService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addUserInventoryItem(Userinventoryitem item) {
        mgr.persist(item);
        return true;
    }

    public Userinventoryitem findById(String id) {
        return mgr.find(Userinventoryitem.class, id);
    }

    public List<Userinventoryitem> findByUserId(String userId) {
        TypedQuery<Userinventoryitem> query = mgr.createQuery(
                "SELECT u FROM Userinventoryitem u WHERE u.userid.userid = :userId", Userinventoryitem.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public boolean deleteUserInventoryItem(String id) {
        Userinventoryitem item = findById(id);
        if (item != null) {
            mgr.remove(item);
            return true;
        }
        return false;
    }

    public List<Userinventoryitem> findAll() {
        query = mgr.createNamedQuery("Userinventoryitem.findAll");
        return query.getResultList();
    }

    public boolean updateUserInventoryItem(Userinventoryitem item) {
        Userinventoryitem existing = findById(item.getInventoryitemid());
        if (existing != null) {
            existing.setQuantity(item.getQuantity());
            existing.setIsdeleted(item.getIsdeleted());
            existing.setBiomeid(item.getBiomeid());
            existing.setItemid(item.getItemid());
            existing.setTreeid(item.getTreeid());
            existing.setTreeboxid(item.getTreeboxid());
            existing.setUserid(item.getUserid());
            return true;
        }
        return false;
    }

    public Userinventoryitem findTreeInventoryItem(String userId, String treeId) {
        TypedQuery<Userinventoryitem> query = mgr.createQuery(
                "SELECT u FROM Userinventoryitem u WHERE u.userid.userid = :userId AND u.treeid.treeid = :treeId",
                Userinventoryitem.class);
        query.setParameter("userId", userId);
        query.setParameter("treeId", treeId);
        List<Userinventoryitem> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public Userinventoryitem findTreeBoxInventoryItem(String userId, String treeboxId) {
        TypedQuery<Userinventoryitem> query = mgr.createQuery(
                "SELECT u FROM Userinventoryitem u WHERE u.userid.userid = :userId AND u.treeboxid.treeboxid = :treeboxId",
                Userinventoryitem.class);
        query.setParameter("userId", userId);
        query.setParameter("treeboxId", treeboxId);
        List<Userinventoryitem> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public Userinventoryitem findItemInventoryItem(String userId, String itemId) {
        TypedQuery<Userinventoryitem> query = mgr.createQuery(
                "SELECT u FROM Userinventoryitem u WHERE u.userid.userid = :userId AND u.itemid.itemid = :itemId",
                Userinventoryitem.class);
        query.setParameter("userId", userId);
        query.setParameter("itemId", itemId);
        List<Userinventoryitem> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public Userinventoryitem findBiomeInventoryItem(String userId, String biomeId) {
        TypedQuery<Userinventoryitem> query = mgr.createQuery(
                "SELECT u FROM Userinventoryitem u WHERE u.userid.userid = :userId AND u.biomeid.biomeid = :biomeId",
                Userinventoryitem.class);
        query.setParameter("userId", userId);
        query.setParameter("biomeId", biomeId);
        List<Userinventoryitem> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    // UserInventoryItemService    
public String getLastId() {
        TypedQuery<Userinventoryitem> query = mgr.createQuery(
                "SELECT u FROM Userinventoryitem u ORDER BY u.inventoryitemid DESC", Userinventoryitem.class);
        query.setMaxResults(1);
        List<Userinventoryitem> result = query.getResultList();

        if (result.isEmpty()) {
            return "IVAA0001";
        } else {
            return result.get(0).getInventoryitemid();
        }
    }

    public String generateNextInventoryItemId() {
        String lastId = getLastId();
        try {
            int number = Integer.parseInt(lastId.substring(4));
            number++;
            return String.format("IVAA%04d", number);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "IVAA0001";
        }
    }

}
