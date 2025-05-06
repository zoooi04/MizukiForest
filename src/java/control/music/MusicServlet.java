package control.music;

import model.Music;
import model.MusicService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.PlaylistMusicService;
import model.Playlistmusic;
import model.UserPlaylistService;
import model.UserService.UserService;
import model.Userplaylist;
import model.Users;

@WebServlet(name = "MusicServlet", urlPatterns = {"/MusicServlet"})
public class MusicServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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
            
            //String userId = "U2500001";
            UserPlaylistService playlistService = new UserPlaylistService(mgr);

            List<Userplaylist> userPlaylists = playlistService.findByUserId(userId);

            session.setAttribute("userPlaylists", userPlaylists);

            MusicService musicService = new MusicService(mgr);

            List<Music> musicList = musicService.findAllMusic();
            if (musicList == null) {
                System.out.println("No music records found.");
            } else {
                System.out.println("Found " + musicList.size() + " music records.");
            }

            session.setAttribute("musicList", musicList);

            String message = request.getParameter("message");
            if (message != null) {
                request.setAttribute("message", message);
            }

            response.sendRedirect(request.getContextPath() + "/view/music/MusicPopular.jsp");
//response.sendRedirect(request.getContextPath() + "/view/MusicPopular.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
