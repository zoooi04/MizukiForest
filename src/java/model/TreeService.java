package model;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import model.Tree;

public class TreeService {

    @PersistenceContext
    EntityManager em;
    @Resource
    Query query;
    private UserTransaction utx;

    public TreeService(EntityManager em) {
        this.em = em;
    }

    public TreeService(EntityManager em, UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    // Find all Trees
    public List<Tree> findAllTrees() {
        Query query = em.createNamedQuery("Tree.findAll");
        return query.getResultList();
    }

    // Find a single Tree by ID
    public Tree findTreeById(String treeId) {
        return em.find(Tree.class, treeId);
    }

    // Update a Tree (with transaction)
    public void updateTree(Tree tree) {
        try {
            utx.begin();
            em.merge(tree);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Create a Tree
    public void createTree(Tree tree) {
        try {
            utx.begin();
            em.persist(tree);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Delete a Tree
    public void deleteTree(String treeId) {
        try {
            utx.begin();
            Tree tree = em.find(Tree.class, treeId);
            if (tree != null) {
                em.remove(tree);
            }
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
