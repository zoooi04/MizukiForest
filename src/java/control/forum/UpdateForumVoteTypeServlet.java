package control.forum;

import java.io.BufferedReader;
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
import javax.transaction.Transactional;
import model.Commentvote;
import model.Threadcomment;
import model.Thread;
import model.Threadvote;
import model.forumService.CommentVoteService;
import model.forumService.ThreadCommentService;
import model.forumService.ThreadService;
import org.json.JSONObject;

@Transactional
public class UpdateForumVoteTypeServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Log raw JSON data
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonString = jsonBuilder.toString();
        System.out.println("Received vote request data: " + jsonString);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Parse JSON using org.json
            JSONObject jsonData = new JSONObject(jsonString);
            String voteType = jsonData.getString("voteType");
            String type = jsonData.getString("type");
            boolean isUpvote = voteType.equals("upvote");

            String userId = ((model.Users) request.getSession().getAttribute("currentUser")).getUserid();

            System.out.println("Processing vote - Type: " + type + ", VoteType: " + voteType + ", UserId: " + userId);

            CommentVoteService commentVoteService = new CommentVoteService(entityManager);
            ThreadCommentService threadCommentService = new ThreadCommentService(entityManager);
            ThreadService threadService = new ThreadService(entityManager);

            JSONObject responseJson = new JSONObject();
            
            try {
                switch (type) {
                    case "thread":
                        String threadId = jsonData.getString("threadId");
                        System.out.println("Thread vote - ThreadId: " + threadId);
                        handleThreadVote(threadId, userId, isUpvote, threadService, responseJson);
                        break;
                        
                    case "comment":
                        String commentId = jsonData.getString("commentId");
                        System.out.println("Comment vote - CommentId: " + commentId);
                        handleCommentVote(commentId, userId, isUpvote, commentVoteService, threadCommentService, responseJson);
                        break;
                        
                    case "reply":
                        String replyId = jsonData.getString("replyId");
                        System.out.println("Reply vote - ReplyId: " + replyId);
                        handleCommentVote(replyId, userId, isUpvote, commentVoteService, threadCommentService, responseJson);
                        break;
                        
                    default:
                        throw new IllegalArgumentException("Invalid vote type");
                }
                
                System.out.println("Vote processed successfully. Response: " + responseJson.toString());
                out.write(responseJson.toString());
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error processing vote: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", e.getMessage());
            out.write(errorJson.toString());
        }
    }

    private void handleThreadVote(String threadId, String userId, boolean isUpvote, 
            ThreadService threadService, JSONObject response) {
        model.Thread thread = threadService.findThreadById(threadId);
        if (thread == null) {
            throw new IllegalArgumentException("Thread not found");
        }

        Threadvote existingVote = threadService.findThreadVote(userId, threadId);

        if (existingVote == null) {
            // Insert new vote
            threadService.castThreadVote(new Threadvote(new model.ThreadvotePK(threadId, userId), isUpvote));
            if (isUpvote) {
                thread.setUpvote(thread.getUpvote() + 1);
            } else {
                thread.setDownvote(thread.getDownvote() + 1);
            }
            response.put("userVoteType", isUpvote ? "upvote" : "downvote");
        } else if (existingVote.getVotetype() == isUpvote) {
            // Unvote - remove the vote and don't set userVoteType
            threadService.removeThreadVote(threadId, userId);
            if (isUpvote) {
                thread.setUpvote(thread.getUpvote() - 1);
            } else {
                thread.setDownvote(thread.getDownvote() - 1);
            }
            response.put("userVoteType", JSONObject.NULL);
        } else {
            // Change vote type
            threadService.updateThreadVoteType(threadId, userId, isUpvote);
            if (isUpvote) {
                thread.setUpvote(thread.getUpvote() + 1);
                thread.setDownvote(thread.getDownvote() - 1);
            } else {
                thread.setDownvote(thread.getDownvote() + 1);
                thread.setUpvote(thread.getUpvote() - 1);
            }
            response.put("userVoteType", isUpvote ? "upvote" : "downvote");
        }
        
        threadService.saveThread(thread);
        
        response.put("upvotes", thread.getUpvote());
        response.put("downvotes", thread.getDownvote());
    }
    
    private void handleCommentVote(String commentId, String userId, boolean isUpvote,
            CommentVoteService commentVoteService, ThreadCommentService threadCommentService, 
            JSONObject response) {
        
        Threadcomment comment = threadCommentService.findCommentById(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("Comment not found");
        }

        Commentvote existingVote = commentVoteService.findVote(commentId, userId);

        if (existingVote == null) {
            // Insert new vote
            commentVoteService.castVote(new Commentvote(new model.CommentvotePK(commentId, userId), isUpvote));
            if (isUpvote) {
                comment.setUpvote(comment.getUpvote() + 1);
            } else {
                comment.setDownvote(comment.getDownvote() + 1);
            }
            response.put("userVoteType", isUpvote ? "upvote" : "downvote");
        } else if (existingVote.getVotetype() == isUpvote) {
            // Unvote - remove the vote and don't set userVoteType
            commentVoteService.removeVote(commentId, userId);
            if (isUpvote) {
                comment.setUpvote(comment.getUpvote() - 1);
            } else {
                comment.setDownvote(comment.getDownvote() - 1);
            }
            response.put("userVoteType", JSONObject.NULL);
        } else {
            // Change vote type
            commentVoteService.updateVoteType(commentId, userId, isUpvote);
            if (isUpvote) {
                comment.setUpvote(comment.getUpvote() + 1);
                comment.setDownvote(comment.getDownvote() - 1);
            } else {
                comment.setDownvote(comment.getDownvote() + 1);
                comment.setUpvote(comment.getUpvote() - 1);
            }
            response.put("userVoteType", isUpvote ? "upvote" : "downvote");
        }

        // Save the updated comment
        entityManager.merge(comment);
        
        response.put("upvotes", comment.getUpvote());
        response.put("downvotes", comment.getDownvote());
    }
}
