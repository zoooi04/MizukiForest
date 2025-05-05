package model.forumService;

import model.Commentvote;

import javax.persistence.*;
import model.CommentvotePK;

public class CommentVoteService {
    @PersistenceContext
    private EntityManager mgr;

    public CommentVoteService(EntityManager mgr) {
        this.mgr = mgr;
        System.out.println("EntityManager initialized: " + (mgr != null));
    }

    public void castVote(Commentvote vote) {
        System.out.println("Casting vote: " + vote);
        mgr.merge(vote); // update or insert
        System.out.println("Vote cast successfully.");
    }

    public Commentvote findVote(String commentId, String userId) {
        Commentvote vote = mgr.find(Commentvote.class, new CommentvotePK(commentId, userId));
        return vote;
    }

    public Boolean findVoteByUserAndComment(String userId, String commentId) {
        Commentvote vote = findVote(commentId, userId);
        return vote != null ? vote.getVotetype() : null;
    }

    public boolean removeVote(String commentId, String userId) {
        System.out.println("Removing vote for commentId: " + commentId + ", userId: " + userId);
        Commentvote vote = findVote(commentId, userId);
        if (vote != null) {
            mgr.remove(vote);
            System.out.println("Vote removed successfully.");
            return true;
        }
        System.out.println("No vote found to remove.");
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
