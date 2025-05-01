package model.journalService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Users;
import model.Usertag;

public class UsertagService {

    @PersistenceContext
    private EntityManager mgr;

    public UsertagService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new Usertag
    public void addUsertag(Usertag usertag) {
        try {
            mgr.persist(usertag);
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
            throw e;
        }
    }

    // Find Usertag by usertag ID
    public Usertag findUsertagById(String usertagid) {
        return mgr.find(Usertag.class, usertagid);
    }

    // Find Usertags by new tag name
    public List<Usertag> findUsertagsByNewtagName(String newtagname) {
        TypedQuery<Usertag> query = mgr.createQuery(
                "SELECT u FROM Usertag u WHERE u.newtagname = :newtagname", Usertag.class);
        query.setParameter("newtagname", newtagname);
        return query.getResultList();
    }

    // Find Usertags by isDeleted status and User ID
    public List<Usertag> findUsertagsByIsDeletedAndUserId(Boolean isdeleted, Users userid) {
        TypedQuery<Usertag> query = mgr.createQuery(
                "SELECT u FROM Usertag u WHERE u.isdeleted = :isdeleted AND u.userid = :userid", Usertag.class);
        query.setParameter("isdeleted", isdeleted);
        query.setParameter("userid", userid);
        return query.getResultList();
    }

    // Find Usertags by isDeleted status
    public List<Usertag> findUsertagsByIsDeleted(Boolean isdeleted) {
        TypedQuery<Usertag> query = mgr.createQuery(
                "SELECT u FROM Usertag u WHERE u.isdeleted = :isdeleted", Usertag.class);
        query.setParameter("isdeleted", isdeleted);
        return query.getResultList();
    }

    // Delete a Usertag by ID
    public boolean deleteUsertag(String usertagid) {
        Usertag usertag = findUsertagById(usertagid);
        if (usertag != null) {
            mgr.remove(usertag);
            return true;
        }
        return false;
    }

    // Find all Usertags
    public List<Usertag> findAllUsertags() {
        TypedQuery<Usertag> query = mgr.createQuery("SELECT u FROM Usertag u", Usertag.class);
        return query.getResultList();
    }

    // Update a Usertag
    public boolean updateUsertag(Usertag updatedUsertag) {
        Usertag existingUsertag = findUsertagById(updatedUsertag.getUsertagid());
        if (existingUsertag != null) {
            existingUsertag.setNewtagname(updatedUsertag.getNewtagname());
            existingUsertag.setIsdeleted(updatedUsertag.getIsdeleted());
            existingUsertag.setTagid(updatedUsertag.getTagid());
            existingUsertag.setUserid(updatedUsertag.getUserid());
            existingUsertag.setDiarytagList(updatedUsertag.getDiarytagList());
            return true;
        }
        return false;
    }
}
