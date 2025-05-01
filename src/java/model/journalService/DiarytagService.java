package model.journalService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Diarytag;

/**
 *
 * @author JiaQuann
 */
public class DiarytagService {

    @PersistenceContext
    private EntityManager mgr;

    // Constructor to initialize the EntityManager
    public DiarytagService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new Diarytag
    public void addDiarytag(Diarytag diarytag) {
        try {
            mgr.persist(diarytag);
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
            throw e;
        }
    }


    // Find Diarytags by Diary ID
    public List<Diarytag> findDiarytagsByDiaryId(String diaryid) {
        TypedQuery<Diarytag> query = mgr.createQuery(
            "SELECT d FROM Diarytag d WHERE d.diarytagPK.diaryid = :diaryid",
            Diarytag.class
        );
        query.setParameter("diaryid", diaryid);
        return query.getResultList();
    }

    // Find Diarytags by UserTag ID
    public List<Diarytag> findDiarytagsByUsertagId(String usertagid) {
        TypedQuery<Diarytag> query = mgr.createQuery(
            "SELECT d FROM Diarytag d WHERE d.diarytagPK.usertagid = :usertagid",
            Diarytag.class
        );
        query.setParameter("usertagid", usertagid);
        return query.getResultList();
    }

    // Find Diarytags by Tag ID
    public List<Diarytag> findDiarytagsByTagId(String tagid) {
        TypedQuery<Diarytag> query = mgr.createQuery(
            "SELECT d FROM Diarytag d WHERE d.diarytagPK.tagid = :tagid",
            Diarytag.class
        );
        query.setParameter("tagid", tagid);
        return query.getResultList();
    }


    // Find all Diarytags
    public List<Diarytag> findAllDiarytags() {
        TypedQuery<Diarytag> query = mgr.createQuery(
            "SELECT d FROM Diarytag d",
            Diarytag.class
        );
        return query.getResultList();
    }


    // Find Diarytags by isDeleted flag
    public List<Diarytag> findDiarytagsByIsDeleted(Boolean isdeleted) {
        TypedQuery<Diarytag> query = mgr.createQuery(
            "SELECT d FROM Diarytag d WHERE d.isdeleted = :isdeleted",
            Diarytag.class
        );
        query.setParameter("isdeleted", isdeleted);
        return query.getResultList();
    }
}
