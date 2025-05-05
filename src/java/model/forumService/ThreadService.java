package model.forumService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Thread;
import model.Threadcategory;
import model.Users;
import model.Threadvote;

public class ThreadService {
    @PersistenceContext
    private EntityManager mgr;

    public ThreadService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public String generateNextThreadId() {
        try {
            String lastId = mgr.createQuery(
                    "SELECT t.threadid FROM Thread t ORDER BY t.threadid DESC", 
                    String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(2));
            return String.format("TH%06d", number + 1);
        } catch (Exception e) {
            return "TH000001";
        }
    }

    // Log the persistence process
    public void addThread(model.Thread thread) {
        try {
            System.out.println("Persisting thread: " + thread);
            mgr.persist(thread);
            System.out.println("Thread persisted successfully.");
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
            throw e;
        }
    }

    public model.Thread findThreadById(String threadId) {
        return mgr.find(model.Thread.class, threadId);
    }

    public void saveThread(Thread thread) {
        mgr.getTransaction().begin();
        mgr.merge(thread);
        mgr.getTransaction().commit();
    }

    public List<model.Thread> findThreadsByUser(Users user) {
        TypedQuery<model.Thread> query = mgr.createQuery(
            "SELECT t FROM Thread t WHERE t.userid = :user AND t.isdeleted = false", 
            model.Thread.class
        );
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<model.Thread> findThreadsByCategory(Threadcategory category) {
        TypedQuery<model.Thread> query = mgr.createQuery(
            "SELECT t FROM Thread t WHERE t.threadcategoryid = :category AND t.isdeleted = false", 
            model.Thread.class
        );
        query.setParameter("category", category);
        return query.getResultList();
    }

    public boolean deleteThread(String threadId) {
        model.Thread thread = mgr.find(model.Thread.class, threadId);
        if (thread != null) {
            thread.setIsdeleted(true);
            mgr.merge(thread);
            return true;
        }
        return false;
    }

    public List<model.Thread> findAllThreads() {
        TypedQuery<model.Thread> query = mgr.createQuery(
            "SELECT t FROM Thread t WHERE t.isdeleted = false", 
            model.Thread.class
        );
        return query.getResultList();
    }

    public List<model.Thread> findAllThreads(String category, String vote) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Thread t WHERE t.isdeleted = false");
    
        if (category != null && !category.equals("all")) {
            queryBuilder.append(" AND t.threadcategoryid.threadcategoryid = :category");
        }
    
        if (vote != null) {
            if (vote.equals("upvote")) {
                queryBuilder.append(" ORDER BY t.upvote DESC");
            } else if (vote.equals("downvote")) {
                queryBuilder.append(" ORDER BY t.downvote DESC");
            }
        }
    
        TypedQuery<model.Thread> query = mgr.createQuery(queryBuilder.toString(), model.Thread.class);
    
        if (category != null && !category.equals("all")) {
            query.setParameter("category", category);
        }
    
        return query.getResultList();
    }
    
    public List<model.Thread> findThreadsByUserAndFilters(String userId, String category, String vote) {
        Users user = mgr.find(Users.class, userId);
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Thread t WHERE t.userid = :userId AND t.isdeleted = false");

        if (category != null && !category.equals("all")) {
            queryBuilder.append(" AND t.threadcategoryid.threadcategoryid = :category");
        }

        if (vote != null) {
            if (vote.equals("upvote")) {
                queryBuilder.append(" ORDER BY t.upvote DESC");
            } else if (vote.equals("downvote")) {
                queryBuilder.append(" ORDER BY t.downvote DESC");
            }
        }

        TypedQuery<model.Thread> query = mgr.createQuery(queryBuilder.toString(), model.Thread.class);
        query.setParameter("userId", user);

        if (category != null && !category.equals("all")) {
            query.setParameter("category", category);
        }

        return query.getResultList();
    }

    public boolean updateThread(model.Thread updatedThread) {
        model.Thread existingThread = mgr.find(model.Thread.class, updatedThread.getThreadid());
        if (existingThread != null && !existingThread.getIsdeleted()) {
            existingThread.setThreadtitle(updatedThread.getThreadtitle());
            existingThread.setThreaddescription(updatedThread.getThreaddescription());
            existingThread.setThreadcategoryid(updatedThread.getThreadcategoryid());
            mgr.merge(existingThread); // Ensure changes are merged
            mgr.flush(); // Force the changes to be written to the database
            return true;
        }
        return false;
    }

    public Threadvote findThreadVote(String userId, String threadId) {
        try {
            return mgr.createQuery("SELECT v FROM Threadvote v WHERE v.threadvotePK.userid = :userId AND v.threadvotePK.threadid = :threadId", Threadvote.class)
                    .setParameter("userId", userId)
                    .setParameter("threadId", threadId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void castThreadVote(Threadvote threadVote) {
        mgr.getTransaction().begin();
        mgr.persist(threadVote);
        mgr.getTransaction().commit();
    }

    public void removeThreadVote(String threadId, String userId) {
        Threadvote vote = findThreadVote(userId, threadId);
        if (vote != null) {
            mgr.getTransaction().begin();
            mgr.remove(vote);
            mgr.getTransaction().commit();
        }
    }

    public void updateThreadVoteType(String threadId, String userId, boolean voteType) {
        Threadvote vote = findThreadVote(userId, threadId);
        if (vote != null) {
            mgr.getTransaction().begin();
            vote.setVotetype(voteType);
            mgr.merge(vote);
            mgr.getTransaction().commit();
        }
    }
}
