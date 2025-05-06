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

    public String generateNextCategoryId() {
        try {
            String lastId = mgr.createQuery(
                    "SELECT t.threadcategoryid FROM Threadcategory t ORDER BY t.threadcategoryid DESC", 
                    String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(3));
            return String.format("THC%05d", number + 1);
        } catch (Exception e) {
            return "THC00001";
        }
    }

    public void addCategory(Threadcategory category) {
        mgr.persist(category);
    }

    public List<Threadcategory> getAllCategories() {
        TypedQuery<Threadcategory> query = mgr.createQuery(
            "SELECT c FROM Threadcategory c WHERE c.isdeleted = false ORDER BY c.threadcategoryname", 
            Threadcategory.class
        );
        return query.getResultList();
    }

    public Threadcategory findCategoryById(String id) {
        Threadcategory category = mgr.find(Threadcategory.class, id);
        return category != null && !category.getIsdeleted() ? category : null;
    }

    public boolean deleteCategory(String id) {
        Threadcategory category = mgr.find(Threadcategory.class, id);
        if (category != null) {
            category.setIsdeleted(true);
            mgr.merge(category);
            return true;
        }
        return false;
    }

    public boolean updateCategory(Threadcategory updatedCategory) {
        Threadcategory existingCategory = mgr.find(Threadcategory.class, updatedCategory.getThreadcategoryid());
        if (existingCategory != null && !existingCategory.getIsdeleted()) {
            existingCategory.setThreadcategoryname(updatedCategory.getThreadcategoryname());
            mgr.merge(existingCategory);
            return true;
        }
        return false;
    }
}
