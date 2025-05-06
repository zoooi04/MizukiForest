package model.BadgeService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import model.Badge;

public class BadgeService {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    Query query;

    public BadgeService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addBadge(Badge badge) {
        mgr.persist(badge);
        return true;
    }

    public Badge findBadgeById(String id) {
        return mgr.find(Badge.class, id);
    }

    public Badge findBadgeByName(String name) {
        TypedQuery<Badge> query = mgr.createQuery("SELECT b FROM Badge b WHERE b.badgename = :name", Badge.class);
        query.setParameter("name", name);
        List<Badge> badgeList = query.getResultList();
        if (!badgeList.isEmpty()) {
            return badgeList.get(0); // Assuming badgename is unique
        }
        return null;
    }

    public boolean deleteBadge(String id) {
        Badge badge = findBadgeById(id);
        if (badge != null) {
            mgr.remove(badge);
            return true;
        }
        return false;
    }

    public List<Badge> findAll() {
        query = mgr.createNamedQuery("Badge.findAll");
        return query.getResultList();
    }

    public boolean updateBadge(Badge badge) {
        Badge tempBadge = findBadgeById(badge.getBadgeid());
        if (tempBadge != null) {
            tempBadge.setBadgename(badge.getBadgename());
            tempBadge.setIsdeleted(badge.getIsdeleted());
            tempBadge.setBadgeimage(badge.getBadgeimage());
            return true;
        }
        return false;
    }

    public String getLastBadgeId() {
        String jpql = "SELECT b.badgeid FROM Badge b ORDER BY b.badgeid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> badgeIdList = query.getResultList();

        return badgeIdList.isEmpty() ? "No badge found" : badgeIdList.get(0);
    }
}
