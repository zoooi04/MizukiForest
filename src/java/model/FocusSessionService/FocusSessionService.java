package model.FocusSessionService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import model.Focussession;

public class FocusSessionService {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    Query query;

    public FocusSessionService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addFocusSession(Focussession session) {
        mgr.persist(session);
        return true;
    }

    public Focussession findFocusSessionById(String sessionId) {
        return mgr.find(Focussession.class, sessionId);
    }

    public boolean deleteFocusSession(String sessionId) {
        Focussession session = findFocusSessionById(sessionId);
        if (session != null) {
            mgr.remove(session);
            return true;
        }
        return false;
    }

    public boolean updateFocusSession(Focussession session) {
        Focussession temp = findFocusSessionById(session.getSessionid());
        if (temp != null) {
            temp.setSessiontype(session.getSessiontype());
            temp.setDuration(session.getDuration());
            temp.setPomodorominorbreak(session.getPomodorominorbreak());
            temp.setPomodoromajorbreak(session.getPomodoromajorbreak());
            temp.setSessionstatus(session.getSessionstatus());
            temp.setTreeboxesobtained(session.getTreeboxesobtained());
            temp.setIsdeleted(session.getIsdeleted());
            temp.setUserid(session.getUserid());
            return true;
        }
        return false;
    }

    public List<Focussession> findAll() {
        query = mgr.createNamedQuery("Focussession.findAll");
        return query.getResultList();
    }

    public List<Focussession> findByUserId(String userId) {
        TypedQuery<Focussession> query = mgr.createQuery(
            "SELECT f FROM Focussession f WHERE f.userid.userid = :userId", Focussession.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public String getLastSessionId() {
        String jpql = "SELECT f.sessionid FROM Focussession f ORDER BY f.sessionid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> sessionIdList = query.getResultList();

        return sessionIdList.isEmpty() ? "No session found" : sessionIdList.get(0);
    }
}
