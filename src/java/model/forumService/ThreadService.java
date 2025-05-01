package model.forumService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Threadcategory;
import model.Users;

public class ThreadService {

    @PersistenceContext
    private EntityManager mgr;

    public ThreadService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addThread(model.Thread thread) {
        try {
            mgr.persist(thread);
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

    public List<model.Thread> findThreadsByUser(Users user) {
        TypedQuery<model.Thread> query = mgr.createQuery("SELECT t FROM Thread t WHERE t.userid = :user", model.Thread.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<model.Thread> findThreadsByCategory(Threadcategory category) {
        TypedQuery<model.Thread> query = mgr.createQuery("SELECT t FROM Thread t WHERE t.threadcategoryid = :category", model.Thread.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public boolean deleteThread(String threadId) {
        model.Thread thread = findThreadById(threadId);
        if (thread != null) {
            mgr.remove(thread);
            return true;
        }
        return false;
    }

    public List<model.Thread> findAllThreads() {
        TypedQuery<model.Thread> query = mgr.createQuery("SELECT t FROM Thread t", model.Thread.class);
        return query.getResultList();
    }
    
    public List<model.Thread> findAllThreads(String category, String vote) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Thread t");
    
        // Adjust the category filter to match the THREADCATEGORYID directly
        if (category != null && !category.equals("all")) {
            queryBuilder.append(" WHERE t.threadcategoryid.threadcategoryid = :category");
        } else {
            queryBuilder.append(" WHERE 1=1"); // Ensures the query can still run when no category filter is provided
        }
    
        // Add sorting based on the vote parameter
        if (vote != null) {
            if (vote.equals("upvote")) {
                queryBuilder.append(" ORDER BY t.upvote DESC");
            } else if (vote.equals("downvote")) {
                queryBuilder.append(" ORDER BY t.downvote DESC");
            }
        }
    
        TypedQuery<model.Thread> query = mgr.createQuery(queryBuilder.toString(), model.Thread.class);
    
        // Set the category parameter if necessary
        if (category != null && !category.equals("all")) {
            query.setParameter("category", category);
        }
    
        // Execute the query to get the filtered or unfiltered list of threads
        return query.getResultList();
    }
    
    public List<model.Thread> findThreadsByUserAndFilters(String userId, String category, String vote) {
        // Retrieve the Users object for the given userId
        Users user = mgr.find(Users.class, userId);

        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Thread t WHERE t.userid = :userId");

        // Adjust the category filter to match the THREADCATEGORYID directly
        if (category != null && !category.equals("all")) {
            queryBuilder.append(" AND t.threadcategoryid.threadcategoryid = :category");
        }

        // Add sorting based on the vote parameter
        if (vote != null) {
            if (vote.equals("upvote")) {
                queryBuilder.append(" ORDER BY t.upvote DESC");
            } else if (vote.equals("downvote")) {
                queryBuilder.append(" ORDER BY t.downvote DESC");
            }
        }

        TypedQuery<model.Thread> query = mgr.createQuery(queryBuilder.toString(), model.Thread.class);
        query.setParameter("userId", user);

        // Set the category parameter if necessary
        if (category != null && !category.equals("all")) {
            query.setParameter("category", category);
        }

        // Execute the query to get the filtered list of threads
        return query.getResultList();
    }

    public boolean updateThread(model.Thread updatedThread) {
        model.Thread existingThread = findThreadById(updatedThread.getThreadid());
        if (existingThread != null) {
            existingThread.setThreadtitle(updatedThread.getThreadtitle());
            existingThread.setThreaddescription(updatedThread.getThreaddescription());
            existingThread.setUpvote(updatedThread.getUpvote());
            existingThread.setDownvote(updatedThread.getDownvote());
            existingThread.setSharecount(updatedThread.getSharecount());
            existingThread.setIsdeleted(updatedThread.getIsdeleted());
            existingThread.setUserid(updatedThread.getUserid());
            existingThread.setThreadcategoryid(updatedThread.getThreadcategoryid());
            return true;
        }
        return false;
    }

    public String getLastThreadId() {
        String jpql = "SELECT t.threadid FROM Thread t ORDER BY t.threadid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> threadIdList = query.getResultList();
        return threadIdList.isEmpty() ? "No thread found" : threadIdList.get(0);
    }
}
