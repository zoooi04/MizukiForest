package model.forumService;

import model.Threadvote;

import javax.persistence.*;
import model.ThreadvotePK;

public class ThreadVoteService {
    @PersistenceContext
    private EntityManager mgr;

    public ThreadVoteService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void castVote(Threadvote vote) {
        mgr.merge(vote); // If vote exists, update; else insert
    }

    public Threadvote findVote(String threadId, String userId) {
        return mgr.find(Threadvote.class, new ThreadvotePK(threadId, userId));
    }

    public boolean removeVote(String threadId, String userId) {
        Threadvote vote = findVote(threadId, userId);
        if (vote != null) {
            mgr.remove(vote);
            return true;
        }
        return false;
    }
}
