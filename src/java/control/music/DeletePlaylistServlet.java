package control.music;

import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.transaction.UserTransaction;
import model.PlaylistMusicService;
import model.UserPlaylistService;
import model.Userplaylist;

@WebServlet(name = "DeletePlaylistServlet", urlPatterns = {"/DeletePlaylistServlet"})
public class DeletePlaylistServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String playlistId = request.getParameter("playlistId");

        // Prevent deletion of Favourites playlist
        if ("PL000000".equals(playlistId)) {
            request.getSession().setAttribute("error", "Cannot delete Favourites playlist");
            response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
            return;
        }

        UserPlaylistService playlistService = new UserPlaylistService(em);
        PlaylistMusicService musicService = new PlaylistMusicService(em);

        try {
            utx.begin(); // 🔁 Start transaction

            // Soft delete music entries in the playlist
            musicService.softRemoveAllMusicFromPlaylist(playlistId); // ⬅️ update this too

            HttpSession session = request.getSession();
            session.setAttribute("DeletePlaylist", playlistId);

            // Soft delete the playlist itself
            Userplaylist playlist = playlistService.findById(playlistId);
            if (playlist != null) {
                playlist.setIsdeleted(true);
                em.merge(playlist);  // save the updated flag
            }

            utx.commit(); // ✅ Commit

        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback(); // ❌ Rollback on error
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
    }
}
