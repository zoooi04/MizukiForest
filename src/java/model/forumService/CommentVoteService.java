package model.forumService;

import model.Commentvote;

import javax.persistence.*;
import model.CommentvotePK;

public class CommentVoteService {
    @PersistenceContext
    private EntityManager mgr;

    public CommentVoteService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void castVote(Commentvote vote) {
        mgr.merge(vote); // update or insert
    }

    public Commentvote findVote(String commentId, String userId) {
        return mgr.find(Commentvote.class, new CommentvotePK(commentId, userId));
    }

    public boolean removeVote(String commentId, String userId) {
        Commentvote vote = findVote(commentId, userId);
        if (vote != null) {
            mgr.remove(vote);
            return true;
        }
        return false;
    }
}
