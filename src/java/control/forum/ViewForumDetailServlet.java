package control.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Threadcomment;
import model.Threadimage;
import model.Threadcategory;
import model.Users;
import model.forumService.ThreadCommentService;
import model.forumService.ThreadImageService;
import model.forumService.ThreadService;
import model.forumService.ThreadVoteService;
import model.forumService.CommentVoteService;

//@WebServlet("/ViewForumDetailServlet")
public class ViewForumDetailServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Users user = mgr.find(Users.class, "U2500001");
        session.setAttribute("currentUser", user);
        Users currentUser = (Users) session.getAttribute("currentUser");

        String ThreadIdParam = request.getParameter("thread_id");
        session.setAttribute("selectedThreadID", ThreadIdParam);

        if (ThreadIdParam != null && !ThreadIdParam.isEmpty()) {
            try {
                ThreadService threadService = new ThreadService(mgr);
                model.Thread thread = threadService.findThreadById(ThreadIdParam);

                if (thread == null) {
                    throw new Exception("Thread not found for ID: " + ThreadIdParam);
                }

                ThreadCommentService threadCommentService = new ThreadCommentService(mgr);
                List<Threadcomment> threadCommentList = threadCommentService.findCommentsByThreadId(ThreadIdParam);

                ThreadImageService threadImageService = new ThreadImageService(mgr);
                List<Threadimage> threadImages = threadImageService.findImagesByThreadId(ThreadIdParam);

                // Ensure thread category is fetched
                Threadcategory threadCategory = thread.getThreadcategoryid();
                if (threadCategory == null) {
                    throw new Exception("Thread category not found for thread ID: " + ThreadIdParam);
                }

                // Ensure comments and images are fetched
                if (threadCommentList == null) {
                    threadCommentList = new ArrayList<>();
                }
                if (threadImages == null) {
                    threadImages = new ArrayList<>();
                }

                // Create a map to store replies grouped by comment ID
                Map<String, List<Threadcomment>> repliesMap = new HashMap<>();
                for (Threadcomment comment : threadCommentList) {
                    List<Threadcomment> replies = threadCommentService.findReplies(comment.getThreadcommentid());
                    repliesMap.put(comment.getThreadcommentid(), replies);
                }

                // Fetch votes for the thread
                ThreadVoteService threadVoteService = new ThreadVoteService(mgr);
                Boolean threadVoteType = threadVoteService.findVoteByUserAndThread(currentUser.getUserid(), ThreadIdParam);
                session.setAttribute("threadVoteType", threadVoteType);

                // Fetch votes for comments
                CommentVoteService commentVoteService = new CommentVoteService(mgr);
                Map<String, Boolean> commentVotes = new HashMap<>();
                for (Threadcomment comment : threadCommentList) {
                    Boolean voteType = commentVoteService.findVoteByUserAndComment(currentUser.getUserid(), comment.getThreadcommentid());
                    commentVotes.put(comment.getThreadcommentid(), voteType);
                }
                session.setAttribute("commentVotes", commentVotes);

                // Fetch votes for replies
                Map<String, Map<String, Boolean>> replyVotes = new HashMap<>();
                for (Map.Entry<String, List<Threadcomment>> entry : repliesMap.entrySet()) {
                    String commentId = entry.getKey();
                    List<Threadcomment> replies = entry.getValue();
                    Map<String, Boolean> replyVoteMap = new HashMap<>();
                    for (Threadcomment reply : replies) {
                        Boolean voteType = commentVoteService.findVoteByUserAndComment(currentUser.getUserid(), reply.getThreadcommentid());
                        replyVoteMap.put(reply.getThreadcommentid(), voteType);
                    }
                    replyVotes.put(commentId, replyVoteMap);
                }
                session.setAttribute("replyVotes", replyVotes);

                session.setAttribute("selectedThread", thread);
                session.setAttribute("selectedThreadCommentList", threadCommentList);
                session.setAttribute("selectedThreadImages", threadImages);
                session.setAttribute("selectedThreadCategory", threadCategory);
                session.setAttribute("repliesMap", repliesMap);

            } catch (Exception e) {
                request.setAttribute("errorMessage", "Invalid thread id. Please try again.");
                request.setAttribute("selectedThreadID", ThreadIdParam);
            }
        } else {
            request.setAttribute("errorMessage", "Thread id parameter is missing.");
            request.setAttribute("selectedThreadID", null);
        }

        String redirectURL = request.getContextPath() + "/view/forum/forum-thread-detail.jsp?thread_id=" + ThreadIdParam;
        response.sendRedirect(redirectURL);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
