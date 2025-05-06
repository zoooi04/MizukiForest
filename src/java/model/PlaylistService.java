package model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Userplaylist;

public class PlaylistService {

    private EntityManager em;

    public PlaylistService(EntityManager em) {
        this.em = em;
    }

    // Get all playlists for a user
    public List<Userplaylist> getUserPlaylists(String userId) {
        TypedQuery<Userplaylist> query = em.createQuery(
                "SELECT p FROM Userplaylist p WHERE p.userid = :userId", Userplaylist.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // Optional: Create a new playlist
    public void createPlaylist(String playlistName, Users userId) {
        Userplaylist newPlaylist = new Userplaylist();
        // Assume playlistId is generated somehow
        newPlaylist.setPlaylistid(generatePlaylistId());
        newPlaylist.setPlaylistname(playlistName);
        newPlaylist.setUserid(userId);

        em.getTransaction().begin();
        em.persist(newPlaylist);
        em.getTransaction().commit();
    }

    private String generatePlaylistId() {
        // Get the latest playlist ID from the database
        String latestId = em.createQuery(
                "SELECT MAX(p.playlistid) FROM Userplaylist p", String.class
        ).getSingleResult();

        int nextNumber = 1; // default if no playlist exists yet

        if (latestId != null) {
            // Extract numeric part after 'PL'
            String numberPart = latestId.substring(2);
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        // Format as PL + 6-digit number
        return String.format("PL%06d", nextNumber);
    }

}
