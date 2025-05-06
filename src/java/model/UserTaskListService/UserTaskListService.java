package model.UserTaskListService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import model.Usertasklist;

public class UserTaskListService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public UserTaskListService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addUserTask(Usertasklist task) {
        mgr.persist(task);
        return true;
    }

    public Usertasklist findUserTaskById(String id) {
        return mgr.find(Usertasklist.class, id);
    }

    public boolean deleteUserTask(String id) {
        Usertasklist task = findUserTaskById(id);
        if (task != null) {
            mgr.remove(task);
            return true;
        }
        return false;
    }

    public boolean updateUserTask(Usertasklist updatedTask) {
        Usertasklist task = findUserTaskById(updatedTask.getUsertasklistid());
        if (task != null) {
            task.setCustomisedtaskname(updatedTask.getCustomisedtaskname());
            task.setCustomisedtaskdescription(updatedTask.getCustomisedtaskdescription());
            task.setDatetimeaccepted(updatedTask.getDatetimeaccepted());
            task.setDatecompleted(updatedTask.getDatecompleted());
            task.setIsdeleted(updatedTask.getIsdeleted());
            task.setTaskid(updatedTask.getTaskid());
            task.setUserid(updatedTask.getUserid());
            return true;
        }
        return false;
    }

    public List<Usertasklist> findAllTasks() {
        query = mgr.createNamedQuery("Usertasklist.findAll");
        return query.getResultList();
    }

    public List<Usertasklist> findTasksByUserId(String userId) {
        TypedQuery<Usertasklist> query = mgr.createQuery(
            "SELECT u FROM Usertasklist u WHERE u.userid.userid = :userId", Usertasklist.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<Usertasklist> findTasksByIsDeleted(boolean isDeleted) {
        TypedQuery<Usertasklist> query = mgr.createNamedQuery("Usertasklist.findByIsdeleted", Usertasklist.class);
        query.setParameter("isdeleted", isDeleted);
        return query.getResultList();
    }
}
