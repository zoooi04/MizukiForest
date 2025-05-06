
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
import model.Therapist;
import model.Timeslot;



public class TimeSlotService {
    @PersistenceContext EntityManager em;
    @Resource Query query;
    @Resource UserTransaction utx;
    
    public TimeSlotService(EntityManager em){
        this.em = em;
    }
    
    public TimeSlotService(EntityManager em, UserTransaction utx){
        this.em = em;
        this.utx = utx;
    }
    
    public List<Timeslot> getAllTimeSlotByTherapist(Therapist therapist) {
        query = em.createQuery("SELECT m FROM Timeslot m WHERE m.therapistid = :therapist", Timeslot.class)
                .setParameter("therapist", therapist);

        return query.getResultList();
    }
    
    public boolean addAvailableSlot(Therapist therapist, Date date, Date startTime, Date endTime){
        try{
            if(checkExistingTimeSlot(therapist, date, startTime, endTime)){
                return false;
            }
            
            String timeSlotId = getNextTimeSlotId();
            Timeslot timeSlot = new Timeslot(timeSlotId, therapist, date, startTime, endTime, true, false);
            utx.begin();
            em.persist(timeSlot);
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
    
    public boolean deleteTimeSlot(String timeSlotId, Therapist therapist){
        try {
            Timeslot timeSlot = getTimeSlotById(timeSlotId);
            if(timeSlot != null){
                utx.begin();
                if (!em.contains(timeSlot)) {
                    timeSlot = em.merge(timeSlot);
                }
                
                em.remove(timeSlot);
                utx.commit();
                return true;
            }
            return false;
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> v : e.getConstraintViolations()) {
                System.out.println("Violation: " + v.getPropertyPath() + " - " + v.getMessage());
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Timeslot getTimeSlotById(String timeSlotId) {
        Timeslot timeslot = em.find(Timeslot.class, timeSlotId);
        return timeslot;
    }
    
    public boolean checkExistingTimeSlot(Therapist therapist, Date date, Date startTime, Date endTime) {
        try {
            List<Timeslot> overlappingSlots = em.createQuery(
                "SELECT t FROM Timeslot t WHERE t.therapistid = :therapist AND t.tsdate = :date " +
                "AND :startTime < t.endtime AND :endTime > t.starttime", Timeslot.class)
                .setParameter("therapist", therapist)
                .setParameter("date", date)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .getResultList();

            return !overlappingSlots.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public String getNextTimeSlotId() {
        String prefix = "TSA";

        // Derby SQL: get max numeric part of the ID
        String sql = "SELECT MAX(INT(SUBSTR(timeslotid, 4))) FROM TIMESLOT";
        Integer maxNum = (Integer) em.createNativeQuery(sql).getSingleResult();

        int nextNum = (maxNum == null) ? 1 : maxNum + 1;

        // Pad with 0s to 6 digits
        String nextId = String.format("%s%05d", prefix, nextNum);

        return nextId;
    }
    
    
    
    
    

}
