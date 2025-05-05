package model.forumService;

import model.Threadcomment;
import model.Users;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import model.Thread;

public class ThreadCommentService {
    @PersistenceContext
    private EntityManager mgr;

    public ThreadCommentService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public String generateNextCommentId() {
        try {
            String lastId = mgr.createQuery(
                    "SELECT t.threadcommentid FROM Threadcomment t ORDER BY t.threadcommentid DESC", 
                    String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(1));
            return String.format("C%07d", number + 1);
        } catch (Exception e) {
            return "C0000001";
        }
    }

    public void addComment(Threadcomment comment) {
        mgr.persist(comment);
    }

    public List<Threadcomment> findCommentsByThreadId(String threadId) {
        TypedQuery<Threadcomment> query = mgr.createQuery(
            "SELECT c FROM Threadcomment c WHERE c.threadid.threadid = :threadId AND c.commentidreplyingto IS NULL AND c.isdeleted = false ORDER BY c.upvote DESC", 
            Threadcomment.class
        );
        query.setParameter("threadId", threadId);
        return query.getResultList();
    }
    
    public List<Threadcomment> findReplies(String commentId) {
        TypedQuery<Threadcomment> query = mgr.createQuery(
            "SELECT c FROM Threadcomment c WHERE c.commentidreplyingto.threadcommentid = :commentId AND c.isdeleted = false ORDER BY c.postdatetime ASC", 
            Threadcomment.class
        );
        query.setParameter("commentId", commentId);
        return query.getResultList();
    }

    public boolean deleteComment(String commentId) {
        Threadcomment comment = mgr.find(Threadcomment.class, commentId);
        if (comment != null) {
            comment.setIsdeleted(true);
            mgr.merge(comment);
            return true;
        }
        return false;
    }

    public Threadcomment updateComment(String commentId, String content) {
        Threadcomment existingComment = mgr.find(Threadcomment.class, commentId);
        if (existingComment != null && !existingComment.getIsdeleted()) {
            existingComment.setContent(content);
            mgr.merge(existingComment);
            return existingComment;
        }
        return null;
    }

    public void updateCommentVotes(String commentId, int upvotes, int downvotes) {
        Threadcomment comment = mgr.find(Threadcomment.class, commentId);
        if (comment != null && !comment.getIsdeleted()) {
            comment.setUpvote(upvotes);
            comment.setDownvote(downvotes);
            mgr.merge(comment);
        }
    }

    public String findThreadIdByCommentId(String commentId) {
        try {
            Threadcomment comment = mgr.find(Threadcomment.class, commentId);
            if (comment != null && comment.getThreadid() != null) {
                return comment.getThreadid().getThreadid();
            }
        } catch (Exception e) {
            System.out.println("Error finding thread ID by comment ID: " + e.getMessage());
        }
        return null;
    }

    public Threadcomment findCommentById(String commentId) {
        return mgr.find(Threadcomment.class, commentId);
    }
}
