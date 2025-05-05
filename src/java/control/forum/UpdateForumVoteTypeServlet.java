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
import model.forumService.CommentVoteService;
import model.forumService.ThreadCommentService;

/**
 *
 * @author johno
 */
@WebServlet(name = "UpdateForumVoteTypeServlet")
public class UpdateForumVoteTypeServlet extends HttpServlet {

    private EntityManager entityManager;

    private EntityManager getEntityManager(HttpServletRequest request) {
        EntityManager em = (EntityManager) getServletContext().getAttribute("em");
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
            System.out.println("Error: EntityManager is null");
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

        System.out.println("Raw JSON payload: " + jsonString);

        // Improved JSON parsing logic
        boolean voteType = false;
        String id = null;
        try {
            voteType = jsonString.contains("\"voteType\":true");

            int idStartIndex = jsonString.indexOf("\"id\":\"") + 6;
            int idEndIndex = jsonString.indexOf("\"", idStartIndex);
            id = jsonString.substring(idStartIndex, idEndIndex);
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Invalid JSON format\"}");
            return;
        }

        String userId = ((model.Users) request.getSession().getAttribute("currentUser")).getUserid();

        CommentVoteService commentVoteService = new CommentVoteService(entityManager);

        if (id == null || id.isEmpty()) {
            System.out.println("Error: ID is null or empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Invalid ID\"}");
            return;
        }

        System.out.println("Processing comment vote");
        Commentvote existingVoteType = commentVoteService.findVote(userId, id);
        System.out.println("Existing comment vote type: " + existingVoteType);

        if (existingVoteType == null) {
            commentVoteService.castVote(new model.Commentvote(new model.CommentvotePK(id, userId), voteType));
            System.out.println("New comment vote cast: " + voteType);
        } else if (existingVoteType != null && existingVoteType.getVotetype() == voteType) {
            commentVoteService.removeVote(id, userId);
            System.out.println("Comment vote removed.");
        } else if (existingVoteType != null) {
            commentVoteService.updateVoteType(id, userId, voteType);
            System.out.println("Comment vote updated to: " + voteType);
        } else {

        }

        ThreadCommentService threadCommentService = new ThreadCommentService(entityManager);
        Threadcomment comment = threadCommentService.findCommentById(id);
        if (comment == null) {
            System.out.println("Error: Threadcomment not found for ID: " + id);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Comment not found\"}");
            return;
        }

        out.write("{"
                + "\"upvotes\": " + comment.getUpvote() + ","
                + "\"downvotes\": " + comment.getDownvote() + ","
                + "\"userVoteType\": " + voteType
                + "}");

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
