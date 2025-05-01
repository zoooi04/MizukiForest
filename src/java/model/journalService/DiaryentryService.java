package model.journalService;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Diaryentry;
import model.Users;

/**
 *
 * @author JiaQuann
 */
public class DiaryentryService {

    @PersistenceContext
    private EntityManager mgr;

    // Constructor to initialize the EntityManager
    public DiaryentryService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new Diaryentry
    public void addDiaryentry(Diaryentry diaryentry) {
    try {
        mgr.persist(diaryentry);
    } catch (ConstraintViolationException e) {
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
        }
        throw e;
    }
}

    // Find a Diaryentry by its ID
    public Diaryentry findDiaryentryById(String diaryid) {
        return mgr.find(Diaryentry.class, diaryid);
    }

    // Find Diaryentry by title
    public List<Diaryentry> findDiaryentryByTitle(String title) {
        TypedQuery<Diaryentry> query = mgr.createQuery("SELECT d FROM Diaryentry d WHERE d.diarytitle = :title", Diaryentry.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    // Find Diaryentry by mood
    public List<Diaryentry> findDiaryentryByMood(String mood) {
        TypedQuery<Diaryentry> query = mgr.createQuery("SELECT d FROM Diaryentry d WHERE d.mood = :mood", Diaryentry.class);
        query.setParameter("mood", mood);
        return query.getResultList();
    }

    // Find Diaryentry by user
    public List<Diaryentry> findDiaryentriesByUser(Users user) {
        TypedQuery<Diaryentry> query = mgr.createQuery("SELECT d FROM Diaryentry d WHERE d.userid = :user", Diaryentry.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    // Delete a Diaryentry by its ID
    public boolean deleteDiaryentry(String diaryid) {
        Diaryentry diaryentry = findDiaryentryById(diaryid);
        if (diaryentry != null) {
            mgr.remove(diaryentry);
            return true;
        }
        return false;
    }

    // Find all Diaryentries
    public List<Diaryentry> findAllDiaryentries() {
        TypedQuery<Diaryentry> query = mgr.createQuery("SELECT d FROM Diaryentry d", Diaryentry.class);
        return query.getResultList();
    }

    // Update a Diaryentry
    public boolean updateDiaryentry(Diaryentry updatedDiaryentry) {
        Diaryentry existingDiaryentry = findDiaryentryById(updatedDiaryentry.getDiaryid());
        if (existingDiaryentry != null) {
            existingDiaryentry.setDiarytitle(updatedDiaryentry.getDiarytitle());
            existingDiaryentry.setDescription(updatedDiaryentry.getDescription());
            existingDiaryentry.setMood(updatedDiaryentry.getMood());
            existingDiaryentry.setDatewritten(updatedDiaryentry.getDatewritten());
            existingDiaryentry.setIsarchived(updatedDiaryentry.getIsarchived());
            existingDiaryentry.setIsdeleted(updatedDiaryentry.getIsdeleted());
            existingDiaryentry.setUserid(updatedDiaryentry.getUserid());
            existingDiaryentry.setDiarytagList(updatedDiaryentry.getDiarytagList());
            return true;
        }
        return false;
    }

    // Get the last diary entry ID (assuming it's a string with a certain pattern)
    public String getLastDiaryEntryId() {
        String jpql = "SELECT d.diaryid FROM Diaryentry d ORDER BY d.diaryid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> diaryIdList = query.getResultList();
        return diaryIdList.isEmpty() ? "No diary entry found" : diaryIdList.get(0);
    }

    // Find Diaryentry by exact written date
    public List<Diaryentry> findDiaryentriesByDate(Date date) {
        TypedQuery<Diaryentry> query = mgr.createQuery(
                "SELECT d FROM Diaryentry d WHERE d.datewritten = :date AND d.isdeleted = false",
                Diaryentry.class
        );
        query.setParameter("date", date);
        return query.getResultList();
    }

}
