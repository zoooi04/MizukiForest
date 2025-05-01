package model.forumService;

import model.Threadcomment;
import model.Users;

import javax.persistence.*;
import java.util.List;

public class ThreadCommentService {
    @PersistenceContext
    private EntityManager mgr;

    public ThreadCommentService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addComment(Threadcomment comment) {
        mgr.persist(comment);
    }

    public List<Threadcomment> findCommentsByThreadId(String threadId) {
        TypedQuery<Threadcomment> query = mgr.createQuery("SELECT c FROM Threadcomment c WHERE c.threadid = :threadId AND c.isdeleted = false", Threadcomment.class);
        query.setParameter("threadId", threadId);
        return query.getResultList();
    }

    public List<Threadcomment> findReplies(String commentId) {
        TypedQuery<Threadcomment> query = mgr.createQuery("SELECT c FROM Threadcomment c WHERE c.commentidreplyingto = :commentId", Threadcomment.class);
        query.setParameter("commentId", commentId);
        return query.getResultList();
    }

    public boolean deleteComment(String commentId) {
        Threadcomment comment = mgr.find(Threadcomment.class, commentId);
        if (comment != null) {
            mgr.remove(comment);
            return true;
        }
        return false;
    }
}
