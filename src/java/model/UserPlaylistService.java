package model;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UserPlaylistService {

    private EntityManager em;

    public UserPlaylistService(EntityManager em) {
        this.em = em;
    }

    // Add a new playlist
    public boolean addPlaylist(Userplaylist playlist) {
        try {
            playlist.setDatecreated(new Date());
            em.persist(playlist);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find all playlists
    public List<Userplaylist> findAll() {
        TypedQuery<Userplaylist> query = em.createNamedQuery("Userplaylist.findAll", Userplaylist.class);
        return query.getResultList();
    }

    // Find playlist by ID
    public Userplaylist findById(String playlistId) {
        return em.find(Userplaylist.class, playlistId);
    }

    // Find playlists by user
    public List<Userplaylist> findByUserId(String userId) {
        TypedQuery<Userplaylist> query = em.createQuery(
                "SELECT p FROM Userplaylist p WHERE p.userid.userid = :userId AND p.isdeleted = false", Userplaylist.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // Delete playlist
    public boolean deletePlaylist(String playlistId) {
        Userplaylist playlist = findById(playlistId);
        if (playlist != null) {
            em.remove(playlist);
            return true;
        }
        return false;
    }

    // Soft delete playlist
    public boolean softDeletePlaylist(String playlistId) {
        Userplaylist playlist = findById(playlistId);
        if (playlist != null) {
            playlist.setIsdeleted(true);
            em.merge(playlist);
            return true;
        }
        return false;
    }

    // Update playlist name
    public boolean updatePlaylistName(String playlistId, String newName) {
        Userplaylist playlist = findById(playlistId);
        if (playlist != null) {
            playlist.setPlaylistname(newName);
            em.merge(playlist);
            return true;
        }
        return false;
    }

    // PlaylistMusicService.java
    public void removeAllMusicFromPlaylist(String playlistId) {
        List<Playlistmusic> list = em.createQuery("SELECT pm FROM Playlistmusic pm WHERE pm.userplaylist.playlistid = :pid")
                .setParameter("pid", playlistId)
                .getResultList();

        for (Playlistmusic pm : list) {
            em.remove(pm);
        }
    }
    
    public void softRemoveAllMusicFromPlaylist(String playlistId) {
    List<Playlistmusic> list = em.createQuery(
        "SELECT pm FROM Playlistmusic pm WHERE pm.userplaylist.playlistid = :pid", Playlistmusic.class)
        .setParameter("pid", playlistId)
        .getResultList();

    for (Playlistmusic pm : list) {
        pm.setIsdeleted(true);
        em.merge(pm);
    }
}
    
    public String getLastPlaylistId() {
    Query query = em.createQuery("SELECT p.playlistid FROM Userplaylist p ORDER BY p.playlistid DESC");
    query.setMaxResults(1);
    List<String> result = query.getResultList();
    return result.isEmpty() ? null : result.get(0);
}
    
    public Userplaylist findFavouritePlaylist(String userId) {
    try {
        return em.createQuery(
            "SELECT p FROM Userplaylist p WHERE p.userid.userid = :userId AND p.playlistid = 'PL000000'", 
            Userplaylist.class)
            .setParameter("userId", userId)
            .getSingleResult();
    } catch (NoResultException e) {
        return null;
    }
}
    
    public boolean isDuplicatePlaylistName(String userId, String playlistName) {
    List<Userplaylist> list = em.createQuery(
        "SELECT u FROM Userplaylist u WHERE u.userid.userid = :userId AND u.playlistname = :playlistName AND u.isdeleted = false", 
        Userplaylist.class)
        .setParameter("userId", userId)
        .setParameter("playlistName", playlistName)
        .getResultList();

    return !list.isEmpty(); // true if duplicate exists
}




}
