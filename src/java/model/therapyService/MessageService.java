/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.therapyService;

/**
 *
 * @author huaiern
 */


import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Message;
import model.Therapist;
import model.Users;



public class MessageService {
    @PersistenceContext EntityManager em;
    @Resource Query query;
    @Resource UserTransaction utx;
    
    public MessageService(EntityManager em){
        this.em = em;
    }
    
    public MessageService(EntityManager em, UserTransaction utx){
        this.em = em;
        this.utx = utx;
    }
    
    public List<Message> getAllMessageByUser(Users user){
       query = em.createQuery("SELECT m FROM Message m WHERE m.userid = :user", Message.class)
               .setParameter("user", user);
       
       return query.getResultList();
    }
    
    public List<Message> getAllMessageByTherapist(Therapist therapist) {
        query = em.createQuery("SELECT m FROM Message m WHERE m.therapistid = :therapist", Message.class)
                .setParameter("therapist", therapist);

        return query.getResultList();
    }
    
    public boolean addMessage(String content, String sender,Users user, Therapist therapist){
        try{

            String messageId = getNextMessageId();
            Message message = new Message(messageId, user, therapist, sender, content, new Date(), false);
            utx.begin();
            em.persist(message);
            utx.commit();
            return true;
        }catch (ConstraintViolationException e) {
        for (ConstraintViolation<?> v : e.getConstraintViolations()) {
            System.out.println("Violation: " + v.getPropertyPath() + " - " + v.getMessage());
        }
        return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    
    public String getNextMessageId() {
        String prefix = "MAA";

        // Derby SQL: get max numeric part of the ID
        String sql = "SELECT MAX(INT(SUBSTR(messageid, 4))) FROM MESSAGE";
        Integer maxNum = (Integer) em.createNativeQuery(sql).getSingleResult();
        System.out.println("MAX NUM: "  +maxNum);
        int nextNum = (maxNum == null) ? 1 : maxNum + 1;

        // Pad with 0s to 6 digits
        String nextId = String.format("%s%05d", prefix, nextNum);

        return nextId;
    }
    
    
    
    
    

}
