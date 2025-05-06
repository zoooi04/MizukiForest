/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.therapyService;

/**
 *
 * @author huaiern
 */


import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import model.Therapist;
import model.Users;
import model.Usertherapist;



public class TherapistService {
    @PersistenceContext EntityManager em;
    @Resource Query query;
    @Resource UserTransaction utx;
    
    public TherapistService(EntityManager em){
        this.em = em;
    }
    
    public TherapistService(EntityManager em, UserTransaction utx){
        this.em = em;
        this.utx = utx;
    }
    
    public List<Therapist> getAllTherapist(){
       Query query = em.createNativeQuery("SELECT * FROM THERAPIST", Therapist.class);
       return query.getResultList();
    }
    
    public Therapist getTherapistById(String therapistId){
        Therapist therapist = em.find(Therapist.class, therapistId);
        return therapist;
    }
    
     public Therapist findTherapistByEmail(String email) {
        TypedQuery<Therapist> query = em.createQuery(
                "SELECT u FROM Therapist u WHERE u.therapistemail = :email", Therapist.class);
        query.setParameter("email", email);
        List<Therapist> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
     
    public Therapist getCurrentTherapistByUser(Users user){
        UserTherapistService uts = new UserTherapistService(em, utx);
        
        Usertherapist ut = uts.getUserTherapist(user);
        
        if(ut != null){
            Therapist therapist = ut.getTherapistid();
            return therapist;
        }
        return null;
    }
    
    public String getNextTherapistId() {
        String prefix = "TR";

        // Derby SQL: get max numeric part of the ID
        String sql = "SELECT MAX(INT(SUBSTR(therapistid, 3))) FROM THERAPIST";
        Integer maxNum = (Integer) em.createNativeQuery(sql).getSingleResult();

        int nextNum = (maxNum == null) ? 1 : maxNum + 1;

        // Pad with 0s to 6 digits
        String nextId = String.format("%s%06d", prefix, nextNum);

        return nextId;
    }
    
    
    
    
    

}
