package model;

import java.util.HashMap;
import java.util.LinkedList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserQueueService {

    @PersistenceContext
    private EntityManager em;
    
    public void setEntityManager(EntityManager em) {
    this.em = em;
}


    public List<Userqueuelist> getUserQueue(String userId) {
        return em.createNamedQuery("Userqueuelist.findByUserid", Userqueuelist.class)
                .setParameter("userid", userId)
                .getResultList();
    }

    @Transactional
    public void removeMusicFromQueue(String userId, String musicId) {
        Userqueuelist userQueue = em.createQuery("SELECT u FROM Userqueuelist u WHERE u.userid = :userid AND u.musicid = :musicid", Userqueuelist.class)
                .setParameter("userid", userId)
                .setParameter("musicid", musicId)
                .getSingleResult();
        if (userQueue != null) {
            em.remove(userQueue);
        }
    }

    @Transactional
    public void clearUserQueue(String userId) {
        List<Userqueuelist> userQueue = getUserQueue(userId);
        for (Userqueuelist item : userQueue) {
            em.remove(em.merge(item)); // Safe removal in managed state
        }
    }

    @Transactional
    public void addMusicToQueue(String userId, String musicId) {
        // First check if it already exists to avoid duplicates
        List<Userqueuelist> existing = em.createQuery(
                "SELECT u FROM Userqueuelist u WHERE u.userid = :userid AND u.musicid = :musicid", Userqueuelist.class)
                .setParameter("userid", userId)
                .setParameter("musicid", musicId)
                .getResultList();

        if (existing.isEmpty()) {
            Userqueuelist newEntry = new Userqueuelist();
            newEntry.setUserid(userId);
            newEntry.setMusicid(musicId);
            em.persist(newEntry);
            em.flush();
        }

    }

    @Transactional
    public void addPlaylistToQueue(String userId, List<String> musicIds) {
        for (String musicId : musicIds) {
            addMusicToQueue(userId, musicId); // Reuse existing method to avoid duplicates
        }
    }
    
    // Add this method to track played songs
    private static final Map<String, LinkedList<String>> tempPlayedQueues = new HashMap<>();

    public List<String> getPlayedSongs(String userId) {
        return tempPlayedQueues.getOrDefault(userId, new LinkedList<>());
    }

    public void addToPlayedSongs(String userId, String musicId) {
        LinkedList<String> playedQueue = tempPlayedQueues.computeIfAbsent(userId, k -> new LinkedList<>());
        playedQueue.add(musicId);
        
        // Remove from main queue
        removeMusicFromQueue(userId, musicId);
    }

    public void clearPlayedSongs(String userId) {
        tempPlayedQueues.remove(userId);
    }


}
