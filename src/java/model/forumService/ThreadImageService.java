package model.forumService;

import model.Threadimage;
import javax.persistence.*;
import java.util.List;
import model.ThreadimagePK;

public class ThreadImageService {
    @PersistenceContext
    private EntityManager mgr;

    public ThreadImageService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addThreadImage(Threadimage image) {
        mgr.persist(image);
    }

    public List<Threadimage> findImagesByThreadId(String threadId) {
        TypedQuery<Threadimage> query = mgr.createQuery("SELECT i FROM Threadimage i WHERE i.threadid = :threadId AND i.isdeleted = false", Threadimage.class);
        query.setParameter("threadId", threadId);
        return query.getResultList();
    }

    public boolean deleteImage(String imageId, String threadId) {
        Threadimage image = mgr.find(Threadimage.class, new ThreadimagePK(imageId, threadId));
        if (image != null) {
            mgr.remove(image);
            return true;
        }
        return false;
    }
}
