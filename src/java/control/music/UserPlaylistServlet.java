package control.music;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "UserPlaylistServlet", urlPatterns = {"/UserPlaylistServlet"})
public class UserPlaylistServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //String userId = "U2500001"; // hardcoded for now
        
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

        UserPlaylistService playlistService = new UserPlaylistService(em);
        PlaylistMusicService playlistMusicService = new PlaylistMusicService(em);

        List<Userplaylist> userPlaylists = playlistService.findByUserId(userId);
        Map<String, List<Playlistmusic>> playlistMusicMap = new HashMap<>();

        for (Userplaylist playlist : userPlaylists) {
            List<Playlistmusic> musicList = playlistMusicService.findMusicInPlaylist(playlist.getPlaylistid());
            playlistMusicMap.put(playlist.getPlaylistid(), musicList);
            System.out.println("At user playlist servlet, i found records: "+ musicList.size());
        }
        
        //HttpSession session = request.getSession();
        session.setAttribute("userPlaylists", userPlaylists);
        session.setAttribute("playlistMusicMap", playlistMusicMap);

        response.sendRedirect(request.getContextPath() + "/view/music/MusicPlaylist.jsp");
//response.sendRedirect(request.getContextPath() + "/view/MusicPlaylist.jsp");
    }
}
