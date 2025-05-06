package control.music;

import model.UserPlaylistService;
import model.Userplaylist;
import model.Users;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CreatePlaylistServlet", urlPatterns = {"/CreatePlaylistServlet"})
public class CreatePlaylistServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String playlistName = request.getParameter("playlistName").toUpperCase();
        //String userId = request.getParameter("userId");
        
        HttpSession session = request.getSession();
            Users userr = (Users)session.getAttribute("user");
            
            if(userr == null){
//                response.sendRedirect("login.jsp");
                request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
                return;
            }           
            
            String userId = userr.getUserid();
            
            //UserService userService = new UserService(mgr);
            //String userId = userService.findUserById(user.getUserid());

        try {
            utx.begin();

            // Generate Playlist ID like PL000001
            UserPlaylistService playlistService = new UserPlaylistService(em);

            StringBuilder errorMessage = new StringBuilder();

            // Check for duplicate
            boolean isDuplicate = playlistService.isDuplicatePlaylistName(userId, playlistName.toUpperCase());

            if (isDuplicate) {

                errorMessage.append("A playlist name already exists. Please enter a different name\n");
                //HttpSession session = request.getSession();
                session.setAttribute("CreatePlaylistErrorMsg", errorMessage.toString());

                // Redirect with error message
                response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet?message="
                        + java.net.URLEncoder.encode("Playlist name already exists.", "UTF-8"));
                return;
            }

            String lastPlaylistId = playlistService.getLastPlaylistId();
            String newPlaylistId;

            if (lastPlaylistId != null && lastPlaylistId.length() == 8) {
                String numericPart = lastPlaylistId.substring(2);
                int num = Integer.parseInt(numericPart);
                newPlaylistId = String.format("PL%06d", num + 1);
            } else {
                newPlaylistId = "PL000001";
            }

            // Create playlist
            Userplaylist playlist = new Userplaylist();
            playlist.setPlaylistid(newPlaylistId);
            playlist.setPlaylistname(playlistName.toUpperCase());
            playlist.setDatecreated(new Date());
            playlist.setIsdeleted(false);

            // Link to user
            Users user = em.find(Users.class, userId);
            playlist.setUserid(user);

            // ✅ Persist directly here, not in service
            em.persist(playlist);

            //HttpSession session = request.getSession();
            session.setAttribute("CreatePlaylist", playlist);
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

        response.sendRedirect(request.getContextPath() + "/UserPlaylistServlet");
    }
}
