package model.journalService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Tag;

public class TagService {

    @PersistenceContext
    private EntityManager mgr;

    public TagService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new Tag
    public void addTag(Tag tag) {
        try {
            mgr.persist(tag);
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
            throw e;
        }
    }

    // Find Tag by tagid
    public Tag findTagById(String tagid) {
        return mgr.find(Tag.class, tagid);
    }

    // Find Tags by tagname
    public List<Tag> findTagsByTagname(String tagname) {
        TypedQuery<Tag> query = mgr.createQuery(
                "SELECT t FROM Tag t WHERE t.tagname = :tagname", Tag.class);
        query.setParameter("tagname", tagname);
        return query.getResultList();
    }

    // Find Tags by iscustomisable status
    public List<Tag> findTagsByIsCustomisable(Boolean iscustomisable) {
        TypedQuery<Tag> query = mgr.createQuery(
                "SELECT t FROM Tag t WHERE t.iscustomisable = :iscustomisable", Tag.class);
        query.setParameter("iscustomisable", iscustomisable);
        return query.getResultList();
    }

    // Find Tags by isdeleted status
    public List<Tag> findTagsByIsDeleted(Boolean isdeleted) {
        TypedQuery<Tag> query = mgr.createQuery(
                "SELECT t FROM Tag t WHERE t.isdeleted = :isdeleted", Tag.class);
        query.setParameter("isdeleted", isdeleted);
        return query.getResultList();
    }

    // Delete Tag by tagid
    public boolean deleteTag(String tagid) {
        Tag tag = findTagById(tagid);
        if (tag != null) {
            mgr.remove(tag);
            return true;
        }
        return false;
    }

    // Find all Tags
    public List<Tag> findAllTags() {
        TypedQuery<Tag> query = mgr.createQuery("SELECT t FROM Tag t", Tag.class);
        return query.getResultList();
    }

    // Update Tag
    public boolean updateTag(Tag updatedTag) {
        Tag existingTag = findTagById(updatedTag.getTagid());
        if (existingTag != null) {
            existingTag.setTagname(updatedTag.getTagname());
            existingTag.setIscustomisable(updatedTag.getIscustomisable());
            existingTag.setIsdeleted(updatedTag.getIsdeleted());
            return true;
        }
        return false;
    }
}
