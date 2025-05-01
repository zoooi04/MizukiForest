package model.forumService;

import model.Threadcategory;

import javax.persistence.*;
import java.util.List;

public class ThreadCategoryService {
    @PersistenceContext
    private EntityManager mgr;

    public ThreadCategoryService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addCategory(Threadcategory category) {
        mgr.persist(category);
    }

    public List<Threadcategory> getAllCategories() {
        TypedQuery<Threadcategory> query = mgr.createQuery("SELECT c FROM Threadcategory c WHERE c.isdeleted = false", Threadcategory.class);
        return query.getResultList();
    }

    public Threadcategory findCategoryById(String id) {
        return mgr.find(Threadcategory.class, id);
    }

    public boolean deleteCategory(String id) {
        Threadcategory category = findCategoryById(id);
        if (category != null) {
            mgr.remove(category);
            return true;
        }
        return false;
    }
}
