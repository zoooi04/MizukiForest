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

    public Boolean findVoteByUserAndComment(String userId, String commentId) {
        Commentvote vote = findVote(commentId, userId);
        return vote != null ? vote.getVotetype() : null;
    }

    public boolean removeVote(String commentId, String userId) {
        Commentvote vote = findVote(commentId, userId);
        if (vote != null) {
            mgr.remove(vote);
            return true;
        }
        return false;
    }

    public void updateVoteType(String commentId, String userId, boolean voteType) {
        Commentvote vote = findVote(commentId, userId);
        if (vote != null) {
            vote.setVotetype(voteType);
            mgr.merge(vote);
        }
    }
}
