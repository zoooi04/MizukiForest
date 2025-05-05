/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.forum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Commentvote;
import model.Threadcomment;
import model.Thread;
import model.Threadvote;
import model.forumService.CommentVoteService;
import model.forumService.ThreadCommentService;
import model.forumService.ThreadService;

/**
 *
 * @author johno
 */
@WebServlet(name = "UpdateForumVoteTypeServlet")
public class UpdateForumVoteTypeServlet extends HttpServlet {

    private EntityManager entityManager;

    private EntityManager getEntityManager(HttpServletRequest request) {
        EntityManager em = (EntityManager) request.getServletContext().getAttribute("em");
        if (em == null) {
            throw new IllegalStateException("EntityManager is not initialized in the servlet context.");
        }
        return em;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        EntityManager em = getEntityManager(request);
        if (em == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"EntityManager not initialized\"}");
            return;
        }

        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonString = jsonBuilder.toString();

        boolean voteType;
        String id;
        boolean isThreadVote;
        try {
            voteType = jsonString.contains("\"voteType\":true");
            isThreadVote = jsonString.contains("\"isThreadVote\":true");

            int idStartIndex = jsonString.indexOf("\"id\":\"") + 6;
            int idEndIndex = jsonString.indexOf("\"", idStartIndex);
            id = jsonString.substring(idStartIndex, idEndIndex);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Invalid JSON format\"}");
            return;
        }

        String userId = ((model.Users) request.getSession().getAttribute("currentUser")).getUserid();

        CommentVoteService commentVoteService = new CommentVoteService(em);
        ThreadCommentService threadCommentService = new ThreadCommentService(em);
        ThreadService threadService = new ThreadService(em);

        if (id == null || id.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Invalid ID\"}");
            return;
        }

        if (isThreadVote) {
            model.Thread thread = threadService.findThreadById(id);

            if (thread == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\": \"Thread not found\"}");
                return;
            }

            Threadvote existingVote = threadService.findThreadVote(userId, id);

            if (existingVote == null) {
                // Insert new vote
                threadService.castThreadVote(new Threadvote(new model.ThreadvotePK(id, userId), voteType));
                if (voteType) {
                    thread.setUpvote(thread.getUpvote() + 1);
                } else {
                    thread.setDownvote(thread.getDownvote() + 1);
                }
            } else if (existingVote.getVotetype() == voteType) {
                // Unvote: remove the vote
                threadService.removeThreadVote(id, userId);
                if (voteType) {
                    thread.setUpvote(thread.getUpvote() - 1);
                } else {
                    thread.setDownvote(thread.getDownvote() - 1);
                }
            } else {
                // Change vote type
                threadService.updateThreadVoteType(id, userId, voteType);
                if (voteType) {
                    thread.setUpvote(thread.getUpvote() + 1);
                    thread.setDownvote(thread.getDownvote() - 1);
                } else {
                    thread.setDownvote(thread.getDownvote() + 1);
                    thread.setUpvote(thread.getUpvote() - 1);
                }
            }

            threadService.saveThread(thread);

            request.getSession().setAttribute("threadVoteType", voteType);

            out.write("{" +
                    "\"upvotes\": " + thread.getUpvote() + "," +
                    "\"downvotes\": " + thread.getDownvote() + "," +
                    "\"userVoteType\": " + voteType +
                    "}");
        } else {
            Commentvote existingVote = commentVoteService.findVote(userId, id);

            if (existingVote == null) {
                // Insert new vote
                commentVoteService.castVote(new Commentvote(new model.CommentvotePK(id, userId), voteType));
                if (voteType) {
                    threadCommentService.incrementUpvote(id);
                } else {
                    threadCommentService.incrementDownvote(id);
                }
            } else if (existingVote.getVotetype() == voteType) {
                // Unvote: remove the vote
                commentVoteService.removeVote(id, userId);
                if (voteType) {
                    threadCommentService.decrementUpvote(id);
                } else {
                    threadCommentService.decrementDownvote(id);
                }
            } else {
                // Change vote type
                commentVoteService.updateVoteType(id, userId, voteType);
                if (voteType) {
                    threadCommentService.incrementUpvote(id);
                    threadCommentService.decrementDownvote(id);
                } else {
                    threadCommentService.incrementDownvote(id);
                    threadCommentService.decrementUpvote(id);
                }
            }

            Threadcomment comment = threadCommentService.findCommentById(id);
            if (comment == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\": \"Comment not found\"}");
                return;
            }

            Map<String, Boolean> commentVotes = (Map<String, Boolean>) request.getSession().getAttribute("commentVotes");
            if (commentVotes == null) {
                commentVotes = new HashMap<>();
            }
            commentVotes.put(id, voteType);
            request.getSession().setAttribute("commentVotes", commentVotes);

            out.write("{" +
                    "\"upvotes\": " + comment.getUpvote() + "," +
                    "\"downvotes\": " + comment.getDownvote() + "," +
                    "\"userVoteType\": " + voteType +
                    "}");
        }

        out.close();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
