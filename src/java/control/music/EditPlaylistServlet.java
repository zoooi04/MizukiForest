/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.music;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.UserPlaylistService;
import model.Userplaylist;

/**
 *
 * @author HP
 */
@WebServlet(name = "EditPlaylistServlet", urlPatterns = {"/EditPlaylistServlet"})
public class EditPlaylistServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String playlistId = request.getParameter("playlistId");
        String newName = request.getParameter("newName").toUpperCase();

        // Prevent renaming Favourites playlist
        if ("PL000000".equals(playlistId)) {
            request.getSession().setAttribute("error", "Cannot rename Favourites playlist");
            response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
            return;
        }

        UserPlaylistService playlistService = new UserPlaylistService(em);
        Userplaylist playlist = playlistService.findById(playlistId);

        if (playlist != null) {
            try {
                // Check for duplicate playlist name under the same user
                boolean isDuplicate = playlistService.isDuplicatePlaylistName(
                        playlist.getUserid().getUserid(), newName.toUpperCase());

                StringBuilder errorMessage = new StringBuilder();

                // Also allow rename if SAME as current (user edits but doesn't actually change name)
                if (isDuplicate && !playlist.getPlaylistname().equals(newName.toUpperCase())) {

                    errorMessage.append("A playlist name already exists. Please enter a different name\n");
                    HttpSession session = request.getSession();
                    session.setAttribute("EditPlaylistErrorMsg", errorMessage.toString());

                    request.getSession().setAttribute("error", "Playlist name already exists.");
                    response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
                    return;
                }

                utx.begin(); 
                playlist.setPlaylistname(newName.toUpperCase());
                em.merge(playlist);  // persist changes

                HttpSession session = request.getSession();
                session.setAttribute("EditPlaylist", playlist);
                System.out.println(playlist);

                utx.commit();        
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    utx.rollback();  
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
    }
}
