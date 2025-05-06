package model.TaskService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Task;

public class TaskService {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    Query query;

    public TaskService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addTask(Task task) {
        mgr.persist(task);
        return true;
    }

    public Task findTaskById(String id) {
        return mgr.find(Task.class, id);
    }

    public List<Task> findTaskByName(String name) {
        TypedQuery<Task> query = mgr.createQuery("SELECT t FROM Task t WHERE t.taskname = :name", Task.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public boolean deleteTask(String id) {
        Task task = findTaskById(id);
        if (task != null) {
            mgr.remove(task);
            return true;
        }
        return false;
    }

    public List<Task> findAll() {
        query = mgr.createNamedQuery("Task.findAll");
        return query.getResultList();
    }

    public boolean updateTask(Task task) {
        Task existingTask = findTaskById(task.getTaskid());
        if (existingTask != null) {
            existingTask.setTaskname(task.getTaskname());
            existingTask.setTaskdescription(task.getTaskdescription());
            existingTask.setIscustomisable(task.getIscustomisable());
            existingTask.setIsarchived(task.getIsarchived());
            existingTask.setIsdeleted(task.getIsdeleted());
            return true;
        }
        return false;
    }

    public String getLastTaskId() {
        String jpql = "SELECT t.taskid FROM Task t WHERE t.taskid LIKE 'T%' ORDER BY t.taskid DESC";
        Query query = mgr.createQuery(jpql);
        List<String> taskIdList = query.getResultList();
        return taskIdList.isEmpty() ? "No task found" : taskIdList.get(0);
    }
}
