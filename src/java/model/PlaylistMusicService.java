package model;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class PlaylistMusicService {

    @PersistenceContext
    private EntityManager em;

    public PlaylistMusicService(EntityManager em) {
        this.em = em;
    }

    // Add a song to a playlist
    public boolean addMusicToPlaylist(Playlistmusic playlistMusic) {
        try {
            Playlistmusic existing = em.find(Playlistmusic.class, playlistMusic.getPlaylistmusicPK());
            if (existing == null) {
                em.persist(playlistMusic);
                return true;
            } else if (existing.getIsdeleted()) {
                existing.setIsdeleted(false);
                em.merge(existing);
                return true;
            }
            return false; // already exists and not deleted
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove music from playlist (hard delete)
    public boolean removeMusicFromPlaylist(String playlistId, String musicId) {
        PlaylistmusicPK pk = new PlaylistmusicPK(playlistId, musicId);
        Playlistmusic playlistMusic = em.find(Playlistmusic.class, pk);
        if (playlistMusic != null) {
            em.remove(playlistMusic);
            return true;
        }
        return false;
    }

    // Soft delete music from playlist
//    public boolean softRemoveMusicFromPlaylist(String playlistId, String musicId) {
//        PlaylistmusicPK pk = new PlaylistmusicPK(playlistId, musicId);
//        Playlistmusic playlistMusic = em.find(Playlistmusic.class, pk);
//        if (playlistMusic != null) {
//            playlistMusic.setIsdeleted(true);
//            em.merge(playlistMusic);
//            return true;
//        }
//        return false;
//    }
    public boolean softRemoveMusicFromPlaylist(String playlistId, String musicId) {
        PlaylistmusicPK pk = new PlaylistmusicPK(playlistId, musicId);
        Playlistmusic playlistMusic = em.find(Playlistmusic.class, pk);

        if (playlistMusic != null) {
            System.out.println("Marking music as deleted: " + pk);
            playlistMusic.setIsdeleted(true);
            em.merge(playlistMusic);
            return true;
        }

        System.out.println("No matching playlist music found for: " + pk);
        return false;
    }

    // Get all music in a playlist
    public List<Playlistmusic> findMusicInPlaylist(String playlistId) {
        TypedQuery<Playlistmusic> query = em.createQuery(
                "SELECT p FROM Playlistmusic p WHERE p.playlistmusicPK.playlistid = :playlistId AND p.isdeleted = false", Playlistmusic.class);
        query.setParameter("playlistId", playlistId);
        return query.getResultList();
    }

    // Get all playlists that contain a given music ID
    public List<Playlistmusic> findPlaylistsByMusicId(String musicId) {
        TypedQuery<Playlistmusic> query = em.createQuery(
                "SELECT p FROM Playlistmusic p WHERE p.playlistmusicPK.musicid = :musicId AND p.isdeleted = false", Playlistmusic.class);
        query.setParameter("musicId", musicId);
        return query.getResultList();
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

    // Add a song to an existing playlist (by selecting the playlist)
    public boolean addMusicToPlaylist(String playlistId, String musicId) {
        try {
            // Find the playlist by ID
            Userplaylist playlist = em.find(Userplaylist.class, playlistId);
            if (playlist == null) {
                return false;  // Playlist doesn't exist
            }

            // Check if the music is already in the playlist
            PlaylistmusicPK pk = new PlaylistmusicPK(playlistId, musicId);
            Playlistmusic existing = em.find(Playlistmusic.class, pk);
            if (existing == null) {
                // Music not found in playlist, so we add it
                Playlistmusic playlistMusic = new Playlistmusic(playlistId, musicId);
                em.persist(playlistMusic);
                return true;
            }
            return false; // Music already in the playlist
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addMusic(Playlistmusic playlistMusic) {
        em.persist(playlistMusic);
    }

}
