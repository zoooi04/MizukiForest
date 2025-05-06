package model;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class TreeBoxService {

    @PersistenceContext
    EntityManager em;
    @Resource
    Query query;

    public TreeBoxService(EntityManager em) {
        this.em = em;
    }

    public void createTreebox(Treebox treebox) {
        em.getTransaction().begin();
        em.persist(treebox);
        em.getTransaction().commit();
    }

    public Treebox findTreeboxById(String treeboxId) {
        return em.find(Treebox.class, treeboxId);
    }

    public List<Treebox> findAllTreeboxes() {
        TypedQuery<Treebox> query = em.createNamedQuery("Treebox.findAll", Treebox.class);
        return query.getResultList();
    }

    public void updateTreebox(Treebox treebox) {
        em.getTransaction().begin();
        em.merge(treebox);
        em.getTransaction().commit();
    }

    public void deleteTreebox(String treeboxId) {
        Treebox treebox = findTreeboxById(treeboxId);
        if (treebox != null) {
            em.getTransaction().begin();
            em.remove(treebox);
            em.getTransaction().commit();
        }
    }

    public List<Treebox> findTreeboxesByName(String name) {
        TypedQuery<Treebox> query = em.createNamedQuery("Treebox.findByTreeboxname", Treebox.class);
        query.setParameter("treeboxname", name);
        return query.getResultList();
    }

    public List<Treebox> findTreeboxesByCost(int cost) {
        TypedQuery<Treebox> query = em.createNamedQuery("Treebox.findByTreeboxcost", Treebox.class);
        query.setParameter("treeboxcost", cost);
        return query.getResultList();
    }

    public List<Treebox> findNonDeletedTreeboxes() {
        TypedQuery<Treebox> query = em.createQuery("SELECT t FROM Treebox t WHERE t.isdeleted = false", Treebox.class);
        return query.getResultList();
    }
}
