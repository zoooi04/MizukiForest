package control.music;

import model.PlaylistMusicService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;

@WebServlet(name = "RemoveMusicServlet", urlPatterns = {"/RemoveMusicServlet"})
public class RemoveMusicServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String playlistId = request.getParameter("playlistId");
        String musicId = request.getParameter("musicId");

        PlaylistMusicService musicService = new PlaylistMusicService(em);

        try {
            utx.begin();  // 🟢 Start transaction
            musicService.removeMusicFromPlaylist(playlistId, musicId);
//            musicService.softRemoveMusicFromPlaylist(playlistId, musicId);

            HttpSession session = request.getSession();
            session.setAttribute("RemoveFromPlaylist", musicId);

            utx.commit(); // ✅ Commit changes
        } catch (Exception e) {
//            utx.rollback(); // ❌ Rollback if something fails
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
    }
}
