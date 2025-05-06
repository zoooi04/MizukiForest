package control.music;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.PlaylistMusicService;
import model.Playlistmusic;
import model.UserQueueService;
import model.Userqueuelist;
import model.Users;

@WebServlet(name = "AddPlaylistToQueueServlet", urlPatterns = {"/AddPlaylistToQueueServlet"})
public class AddPlaylistToQueueServlet extends HttpServlet {

    @Inject
    private UserQueueService userQueueService; // inject managed service

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //String userId = "U2500001"; // hardcoded
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
            
        String playlistId = request.getParameter("playlistId");

        if (playlistId != null && !playlistId.isEmpty()) {
            PlaylistMusicService playlistMusicService = new PlaylistMusicService(em);
            List<Playlistmusic> musicList = playlistMusicService.findMusicInPlaylist(playlistId);

            for (Playlistmusic pm : musicList) {
//                String musicId = pm.getMusic().getMusicid();
                String musicId = pm.getPlaylistmusicPK().getMusicid();
                userQueueService.addMusicToQueue(userId, musicId); // use transactional method
                
                //HttpSession session = request.getSession();
                session.setAttribute("AddPlaylistToQueue", musicId);
            }
        }

        response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
    }
}
