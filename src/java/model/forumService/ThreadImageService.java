package model.forumService;

import model.Threadimage;
import javax.persistence.*;
import java.util.List;
import model.Thread;
import model.ThreadimagePK;

public class ThreadImageService {
    @PersistenceContext
    private EntityManager mgr;

    public ThreadImageService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public String generateNextImageId() {
        try {
            String lastId = mgr.createQuery(
                    "SELECT t.threadimagePK.imageid FROM Threadimage t ORDER BY t.threadimagePK.imageid DESC", 
                    String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(2));
            return String.format("IA%06d", number + 1);
        } catch (Exception e) {
            return "IA000001";
        }
    }

    public void addThreadImage(Threadimage threadImage) {
        mgr.persist(threadImage);
    }

    public List<Threadimage> findImagesByThreadId(String threadId) {
        TypedQuery<Threadimage> query = mgr.createQuery(
            "SELECT i FROM Threadimage i WHERE i.threadimagePK.threadid = :threadId AND i.isdeleted = false", 
            Threadimage.class
        );
        query.setParameter("threadId", threadId);
        return query.getResultList();
    }

    public Threadimage findMainImage(String threadId) {
        TypedQuery<Threadimage> query = mgr.createQuery(
            "SELECT i FROM Threadimage i WHERE i.threadimagePK.threadid = :threadId AND i.ismainimage = true AND i.isdeleted = false", 
            Threadimage.class
        );
        query.setParameter("threadId", threadId);
        List<Threadimage> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean deleteImage(String imageId, String threadId) {
        Threadimage image = mgr.find(Threadimage.class, new ThreadimagePK(imageId, threadId));
        if (image != null) {
            image.setIsdeleted(true);
            mgr.merge(image);
            return true;
        }
        return false;
    }

    public boolean updateImage(Threadimage updatedImage) {
        Threadimage existingImage = mgr.find(Threadimage.class, updatedImage.getThreadimagePK());
        if (existingImage != null && !existingImage.getIsdeleted()) {
            existingImage.setIsmainimage(updatedImage.getIsmainimage());
            existingImage.setImage(updatedImage.getImage());
            mgr.merge(existingImage);
            return true;
        }
        return false;
    }
}
