package control.music;

import model.Playlistmusic;
import model.PlaylistmusicPK;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

@WebServlet("/AddToPlaylistServlet")
public class AddToPlaylistServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction userTransaction;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String musicId = request.getParameter("musicId");
        String playlistId = request.getParameter("playlistId");

        System.out.println("Music ID: " + musicId);
        System.out.println("Playlist ID: " + playlistId);

        PlaylistmusicPK pk = new PlaylistmusicPK(playlistId, musicId);
        
        try {
            userTransaction.begin();

            Playlistmusic existing = em.find(Playlistmusic.class, pk);

            String message;
            StringBuilder errorMessage = new StringBuilder();

            if (existing != null && !existing.getIsdeleted()) {
                // Music already exists in the playlist and is active
                message = "Music is already in this playlist";
                errorMessage.append("Music is already in this playlist\n");
                System.out.println(message);
                HttpSession session = request.getSession();
            session.setAttribute("AddToPlaylistErrorMsg", errorMessage.toString());
            } else if (existing != null && existing.getIsdeleted()) {
                // Music existed but was soft-deleted before -> Reactivate it
                existing.setIsdeleted(false);
                em.merge(existing);
                message = "Music added to the playlist";
                errorMessage.append("Music added to the playlist\n");
                System.out.println(message);
            } else {
                // New entry
                Playlistmusic newPlaylistMusic = new Playlistmusic(pk, false);
                em.persist(newPlaylistMusic);
                
                HttpSession session = request.getSession();
                session.setAttribute("AddNewToPlaylist", newPlaylistMusic);
                System.out.println(newPlaylistMusic);
                
                message = "Music added to the playlist";
                //errorMessage.append("Music added to the playlist\n");
                System.out.println(message);
            }

            userTransaction.commit();

            // Pass the message back to the page
            response.sendRedirect(request.getContextPath() + "/MusicServlet?message=" + java.net.URLEncoder.encode(message, "UTF-8"));
            
            

        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}



//@WebServlet("/AddToPlaylistServlet")
//public class AddToPlaylistServlet extends HttpServlet {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Inject
//    private UserTransaction userTransaction;
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String musicId = request.getParameter("musicId");
//        String playlistId = request.getParameter("playlistId");
//
//        System.out.println("Music ID: " + musicId);
//        System.out.println("Playlist ID: " + playlistId);
//
//        Playlistmusic playlistmusic = new Playlistmusic(new PlaylistmusicPK(playlistId, musicId), false); // isdeleted=false means active
//        System.out.println("playlist music: " + playlistmusic);
//
//        try {
//            // Begin the transaction
//            userTransaction.begin();
//
//            // Persist the playlist music
//            em.persist(playlistmusic);
//            System.out.println("Added");
//
//            // Commit the transaction
//            userTransaction.commit();
//        } catch (Exception e) {
//            try {
//                // Rollback if there is an exception
//                userTransaction.rollback();
//            } catch (Exception rollbackEx) {
//                rollbackEx.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//
//        response.sendRedirect(request.getContextPath() + "/MusicServlet?message=Music added to playlist successfully");
//    }
//}



/*
private static final long serialVersionUID = 1L;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("MizukiForestPU");
    }

    @Transactional
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String musicId = request.getParameter("musicId");
        String playlistId = request.getParameter("playlistId");

        // Debugging the parameters
        System.out.println("Music ID: " + musicId);
        System.out.println("Playlist ID: " + playlistId);

        if (musicId != null && playlistId != null) {
            // Get the EntityManager
            EntityManager em = emf.createEntityManager();

            try {
                
                /*
                // Retrieve Music and Userplaylist from database
                Music music = em.find(Music.class, musicId);
                Userplaylist playlist = em.find(Userplaylist.class, playlistId);

                // Check if the entities are found
                if (music == null) {
                    System.out.println("Music not found with ID: " + musicId);
                    response.sendRedirect(request.getContextPath() + "/MusicServlet?error=Music not found");
                    return;
                }
                if (playlist == null) {
                    System.out.println("Playlist not found with ID: " + playlistId);
                    response.sendRedirect(request.getContextPath() + "/MusicServlet?error=Playlist not found");
                    return;
                }
               // 
                // Create a new Playlistmusic entity to add to the playlist
                Playlistmusic playlistmusic = new Playlistmusic(new PlaylistmusicPK(playlistId, musicId), false); // isdeleted=false means active
                //playlistmusic.setMusic(music);
                //playlistmusic.setUserplaylist(playlist);

                System.out.println("Music ID: " + musicId);
                System.out.println("Playlist ID: " + playlistId);

                // Persist the new Playlistmusic object
                em.persist(playlistmusic);
                System.out.println("Music added to playlist successfully.");

                // Redirect the user to the same page with a success message
                response.sendRedirect(request.getContextPath() + "/MusicServlet?message=Music added to playlist successfully");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/MusicServlet?error=Failed to add music to playlist");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/MusicServlet?error=Invalid request");
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
*/
