package control.music;

import model.PlaylistMusicService;
import model.Playlistmusic;
import model.PlaylistmusicPK;
import model.UserPlaylistService;
import model.Userplaylist;
import model.Users;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.util.Date;
import model.Userfavourite;
import model.UserfavouritePK;

@WebServlet(name = "SetFavouriteServlet", urlPatterns = {"/SetFavouriteServlet"})
public class SetFavouriteServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String musicId = request.getParameter("musicId");
        //String userId = "U2500001"; // Hardcoded for now, replace with session user
        
        HttpSession session = request.getSession();
            Users user = (Users)session.getAttribute("user");
            
            if(user == null){
//                response.sendRedirect("login.jsp");
                request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
                return;
            }           
            
            String userId = user.getUserid();
            
            //UserService userService = new UserService(mgr);
            //String userId = userService.findUserById(user.getUserid());

        UserfavouritePK fpk = new UserfavouritePK(userId, musicId);

        try {
            utx.begin();

            // Check if Favourite playlist exists
            UserPlaylistService playlistService = new UserPlaylistService(em);
            Userplaylist favouritePlaylist = playlistService.findFavouritePlaylist(userId);

            Userfavourite existingFavourite = em.find(Userfavourite.class, fpk);
            
            // Check if music already exists in the Favourite playlist
            boolean musicInPlaylist = false;
            if (favouritePlaylist != null) {
                PlaylistmusicPK pmPK = new PlaylistmusicPK(favouritePlaylist.getPlaylistid(), musicId);
                Playlistmusic playlistMusic = em.find(Playlistmusic.class, pmPK);
                musicInPlaylist = (playlistMusic != null && !playlistMusic.getIsdeleted());
            }

            StringBuilder errorMessage = new StringBuilder();

            // If Favourite playlist doesn't exist, create it
            if (favouritePlaylist == null) {
                favouritePlaylist = new Userplaylist();
                favouritePlaylist.setPlaylistid("PL000000"); // Special ID for Favourites
                favouritePlaylist.setPlaylistname("Favourite");
                favouritePlaylist.setDatecreated(new Date());
                favouritePlaylist.setIsdeleted(false);
                favouritePlaylist.setUserid(em.find(Users.class, userId));
                em.persist(favouritePlaylist);

                //HttpSession session = request.getSession();
                session.setAttribute("AddToFavourite", favouritePlaylist);
                System.out.println(favouritePlaylist);
            }

            // if already marked as favourite or already in playlist
            if (existingFavourite != null || musicInPlaylist) {
                errorMessage.append("Music already set as favourite\n");
                //HttpSession session = request.getSession();
                session.setAttribute("AddToFavouriteErrorMsg", errorMessage.toString());
            } else {
                // Add to Userfavourite table
                Userfavourite newFavourite = new Userfavourite(fpk, false);
                em.persist(newFavourite);

                // Add music to Favourite playlist
                PlaylistmusicPK pk = new PlaylistmusicPK(favouritePlaylist.getPlaylistid(), musicId);
                Playlistmusic playlistMusic = new Playlistmusic(pk, false);
                em.persist(playlistMusic);

                //HttpSession session = request.getSession();
                session.setAttribute("AddToFavourite", favouritePlaylist);
                System.out.println(favouritePlaylist);
            }

            utx.commit();

            // Set success message if no errors
            if (errorMessage.length() == 0) {
                request.getSession().setAttribute("message", "Added to Favourites successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
                request.getSession().setAttribute("error", "Failed to add to Favourites");
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/MusicServlet");
    }
}

//@WebServlet(name = "SetFavouriteServlet", urlPatterns = {"/SetFavouriteServlet"})
//public class SetFavouriteServlet extends HttpServlet {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Resource
//    private UserTransaction utx;
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String musicId = request.getParameter("musicId");
//        String userId = "U2500001"; // Hardcoded for now, replace with session user
//        
//        UserfavouritePK fpk = new UserfavouritePK(userId, musicId);
//
//        try {
//            utx.begin();
//
//            // Check if Favourite playlist exists
//            UserPlaylistService playlistService = new UserPlaylistService(em);
//            Userplaylist favouritePlaylist = playlistService.findFavouritePlaylist(userId);
//            
//            Userfavourite existing = em.find(Userfavourite.class, fpk);
//            
//            StringBuilder errorMessage = new StringBuilder();
//            
//            
//
//            // If doesn't exist, create it
//            if (favouritePlaylist == null) {
//                favouritePlaylist = new Userplaylist();
//                favouritePlaylist.setPlaylistid("PL000000"); // Special ID for Favourites
//                favouritePlaylist.setPlaylistname("Favourite");
//                favouritePlaylist.setDatecreated(new Date());
//                favouritePlaylist.setIsdeleted(false);
//                favouritePlaylist.setUserid(em.find(Users.class, userId));
//                em.persist(favouritePlaylist);
//
//                HttpSession session = request.getSession();
//                session.setAttribute("AddToFavourite", favouritePlaylist);
//                System.out.println(favouritePlaylist);
//                
//            }
//            
//            // if exist
//            if (existing != null){
//                errorMessage.append("Music is already set as favourite\n");
//                HttpSession session = request.getSession();
//                session.setAttribute("AddToFavouriteErrorMsg", errorMessage.toString());
//            }else{
//                
//                // Add music to Favourite playlist
//            PlaylistmusicPK pk = new PlaylistmusicPK(favouritePlaylist.getPlaylistid(), musicId);
//            Playlistmusic playlistMusic = new Playlistmusic(pk, false);
//            em.persist(playlistMusic);
//            
//            HttpSession session = request.getSession();
//                session.setAttribute("AddToFavourite", favouritePlaylist);
//                System.out.println(favouritePlaylist);
//                
//            }
//
//            
//
//            utx.commit();
//
//            // Set success message
//            request.getSession().setAttribute("message", "Added to Favourites successfully!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                utx.rollback();
//                request.getSession().setAttribute("error", "Failed to add to Favourites");
//            } catch (Exception rollbackEx) {
//                rollbackEx.printStackTrace();
//            }
//        }
//
//        response.sendRedirect(request.getContextPath() + "/MusicServlet");
//    }
//}