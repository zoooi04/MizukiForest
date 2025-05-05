import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EntityManagerInitializer implements ServletContextListener {

    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("MizukiForestPU");
        EntityManager em = emf.createEntityManager();
        sce.getServletContext().setAttribute("em", em);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = (EntityManager) sce.getServletContext().getAttribute("em");
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}