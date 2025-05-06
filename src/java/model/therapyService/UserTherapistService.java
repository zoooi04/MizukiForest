/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.therapyService;

import java.sql.SQLException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import model.Therapist;
import model.Users;
import model.Usertherapist;

/**
 *
 * @author huaiern
 */
public class UserTherapistService {

    @PersistenceContext
    EntityManager em;
    @Resource
    Query query;
    @Resource
    UserTransaction utx;

    public UserTherapistService(EntityManager em) {
        this.em = em;
    }

    public UserTherapistService(EntityManager em, UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    //get current therapist
    public Usertherapist getUserTherapist(Users user) {
        try {
            Usertherapist ut = em.createQuery("SELECT ut FROM Usertherapist ut WHERE ut.userid = :user AND ut.isdeleted = :isdeleted", Usertherapist.class)
                    .setParameter("user", user)
                    .setParameter("isdeleted", false)
                    .getSingleResult();
            return ut;
        }catch(NoResultException e){
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usertherapist> getAllUserTherapistByUser(Users user) {
        try {
            List<Usertherapist> utList = em.createQuery("SELECT ut FROM Usertherapist ut WHERE ut.userid = :user", Usertherapist.class)
                    .setParameter("user", user)
                    .getResultList();
            return utList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Usertherapist> getAllUserTherapistByTherapist(Therapist therapist) {
        try {
            List<Usertherapist> utList = em.createQuery("SELECT ut FROM Usertherapist ut WHERE ut.therapistid = :therapist", Usertherapist.class)
                    .setParameter("therapist", therapist)
                    .getResultList();
            return utList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //only for first time assign
    public boolean assignTherapist(String therapistId, Users user) {
        Usertherapist ut = getUserTherapist(user);
        try {
            if (ut == null) {
                //if user is not assigned to a therapist, assign therapist
                TherapistService ts = new TherapistService(em, utx);
                Therapist therapist = ts.getTherapistById(therapistId);
                
                ut = new Usertherapist(getNextUserTherapistId(), therapist, user, new Date(), null, false);
                System.out.println(ut);
                utx.begin();
                em.persist(ut);
                utx.commit();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeTherapist(String therapistId, Users user) {
        try {
            Usertherapist ut = getUserTherapist(user);

            //check if previously have therapist, if have then delete previous one and assign new one
            if (ut != null) {
                utx.begin();
                
                Usertherapist oldUt = null;
                boolean found = false;
                //check if have history with this therapist
                List<Usertherapist> utList = getAllUserTherapistByUser(user);                
                for (Usertherapist loopUt : utList) {
                    if (loopUt.getTherapistid().getTherapistid().equals(therapistId) && loopUt.getIsdeleted()) {
                        oldUt = loopUt;
                        break;
                    }   
                }
                
                //if have then only set back isdeleted false, then set current one isdelete to true
                if(oldUt != null){
                    oldUt.setIsdeleted(false);
                    ut.setIsdeleted(true);
                    em.merge(oldUt);
                    em.merge(ut);
                } else {
                    //if is dont have history with change-to therapist
                    //current one delete
                    ut.setIsdeleted(true);
                    em.merge(ut);
                    
                    //create new one for new connection
                    TherapistService ts = new TherapistService(em, utx);
                    Therapist therapist = ts.getTherapistById(therapistId);
                    Usertherapist newUt = new Usertherapist(getNextUserTherapistId(), therapist, user, new Date(), null, false);
                    em.persist(newUt);
                }
                
                utx.commit();
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNextUserTherapistId() {
        String prefix = "UT";

        // Derby SQL: get max numeric part of the ID
        String sql = "SELECT MAX(INT(SUBSTR(usertherapistid, 3))) FROM USERTHERAPIST";
        Integer maxNum = (Integer) em.createNativeQuery(sql).getSingleResult();

        int nextNum = (maxNum == null) ? 1 : maxNum + 1;

        // Pad with 0s to 6 digits
        String nextId = String.format("%s%06d", prefix, nextNum);

        return nextId;
    }
}
