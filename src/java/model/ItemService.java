package model;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import model.Item;

public class ItemService {

    @PersistenceContext
    EntityManager em;
    @Resource
    Query query;

    private UserTransaction utx;

    public ItemService(EntityManager em) {
        this.em = em;
    }

    public ItemService(EntityManager em, UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    // Find all Items
    public List<Item> findAllItems() {
        Query query = em.createNamedQuery("Item.findAll");
        return query.getResultList();
    }

    // Find Item by ID
    public Item findItemById(String itemId) {
        return em.find(Item.class, itemId);
    }

    // Create new Item
    public void createItem(Item item) {
        try {
            utx.begin();
            em.persist(item);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Update existing Item
    public void updateItem(Item item) {
        try {
            utx.begin();
            em.merge(item);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Delete Item
    public void deleteItem(String itemId) {
        try {
            utx.begin();
            Item item = em.find(Item.class, itemId);
            if (item != null) {
                em.remove(item);
            }
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
