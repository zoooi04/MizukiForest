package control.music;

import model.Userqueuelist;
import model.UserQueueService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import model.Users;

@WebServlet("/UserQueueServlet")
public class UserQueueServlet extends HttpServlet {

    @Inject
    private UserQueueService queueService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String userId = "U2500001"; // Can be dynamic
        
        HttpSession session = request.getSession();
            Users user = (Users)session.getAttribute("user");
            
            if(user == null){
//                response.sendRedirect("login.jsp");
                request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
            }           
            
            String userId = user.getUserid();
            
            //UserService userService = new UserService(mgr);
            //String userId = userService.findUserById(user.getUserid());
        
        List<Userqueuelist> queue = queueService.getUserQueue(userId);
        System.out.println("Queue count is " + queue.size());

        // Get played songs to display if needed
        List<String> playedSongs = queueService.getPlayedSongs(userId);

        //HttpSession session = request.getSession();
        session.setAttribute("queue", queue);

        session.setAttribute("playedSongs", playedSongs);

        //request.getRequestDispatcher("/view/UserQueue.jsp").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/view/music/UserQueue.jsp");
//        response.sendRedirect(request.getContextPath() + "/view/UserQueue.jsp");
    }
    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Users user = (Users)session.getAttribute("user");
    
    if(user == null){
        request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
        return;
    }           
    
    String userId = user.getUserid();
    List<Userqueuelist> queue = queueService.getUserQueue(userId);
    
    // Handle Clear Queue
    if (request.getParameter("clearQueue") != null) {
        // Get the currently playing music ID before clearing
        String currentlyPlayingId = (String) session.getAttribute("currentlyPlayingId");
        
        queueService.clearUserQueue(userId);
        session.setAttribute("ClearQueue", userId);
        
        // If we're clearing the queue while a song is playing
        if (currentlyPlayingId != null) {
            // Set flag to stop playback
            session.setAttribute("stopPlayback", true);
        }
    }

    // Handle Remove Music
    if (request.getParameter("remove") != null) {
        String musicId = request.getParameter("musicId");
        if (musicId != null && !musicId.isEmpty()) {
            // Check if this is the currently playing song
            String currentlyPlayingId = (String) session.getAttribute("currentlyPlayingId");
            boolean isCurrentlyPlaying = musicId.equals(currentlyPlayingId);
            
            queueService.removeMusicFromQueue(userId, musicId);
            session.setAttribute("RemoveFromQueue", musicId);
            
            if (isCurrentlyPlaying) {
                // Set flag to play next song
                session.setAttribute("playNextAfterRemove", true);
            }
        }
    }

    // Refresh the queue
    queue = queueService.getUserQueue(userId);
    session.setAttribute("queue", queue);
    response.sendRedirect(request.getContextPath() + "/view/music/UserQueue.jsp");
}
    

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //String userId = "U2500001";
//        
//        HttpSession session = request.getSession();
//            Users user = (Users)session.getAttribute("user");
//            
//            if(user == null){
////                response.sendRedirect("login.jsp");
//                request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
//            }           
//            
//            String userId = user.getUserid();
//            
//            //UserService userService = new UserService(mgr);
//            //String userId = userService.findUserById(user.getUserid());
//        List<Userqueuelist> queue = queueService.getUserQueue(userId);
//        System.out.println("size: " + queue.size());
//        String message;
//        StringBuilder errorMessage = new StringBuilder();
//
//        // Handle Clear Queue
//        if (request.getParameter("clearQueue") != null) {
//            queueService.clearUserQueue(userId);
//
//            //HttpSession session = request.getSession();
//            session.setAttribute("ClearQueue", userId);
//        }
//
//        if (request.getParameter("remove") != null) {
//            String musicId = request.getParameter("musicId");
//            if (musicId != null && !musicId.isEmpty()) {
//                queueService.removeMusicFromQueue(userId, musicId);
//
//                //HttpSession session = request.getSession();
//                session.setAttribute("RemoveFromQueue", musicId);
//            }
//        }
//
////        response.sendRedirect("UserQueueServlet");
//        queue = queueService.getUserQueue(userId);
//        System.out.println("size after: " + queue.size());
//
//        //HttpSession session = request.getSession();
//        session.setAttribute("queue", queue);
//        response.sendRedirect(request.getContextPath() + "/view/music/UserQueue.jsp");
////        response.sendRedirect(request.getContextPath() + "/view/UserQueue.jsp");
//
//        //request.setAttribute("queue", queue);
//        // request.getRequestDispatcher("/view/UserQueue.jsp").forward(request, response);
//    }

}
