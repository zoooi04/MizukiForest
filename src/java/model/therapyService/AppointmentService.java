/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.therapyService;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Appointment;
import model.Timeslot;
import model.Users;

/**
 *
 * @author huaiern
 */
public class AppointmentService {
        @PersistenceContext EntityManager em;
    @Resource Query query;
    @Resource UserTransaction utx;
    
    public AppointmentService(EntityManager em){
        this.em = em;
    }
    
    public AppointmentService(EntityManager em, UserTransaction utx){
        this.em = em;
        this.utx = utx;
    }
    
    public List<Appointment> getAllAppointmentByUser(Users user) {
        query = em.createQuery("SELECT m FROM Appointment m WHERE m.userid = :user", Appointment.class)
                .setParameter("user", user);

        return query.getResultList();
    }
    
    public List<Appointment> getAllAppointment() {
        Query query = em.createNativeQuery("SELECT * FROM APPOINTMENT", Appointment.class);
        return query.getResultList();
    }
    
    public boolean bookAppointment(Users user, String timeSlotId){
        try{
            TimeSlotService tss = new TimeSlotService(em,utx);
            Timeslot timeslot = tss.getTimeSlotById(timeSlotId);
            
            if(hasAppointment(user,timeslot)){
                return false;
            }
            
            Appointment app = getDeletedAppointment(user, timeslot);
            //if no appointment but rather only is deleted, reupdate it
            if (app != null) {
                app.setIsdeleted(false);
                timeslot.setStatus(false);
                
                utx.begin();
                em.merge(timeslot);
                em.merge(app);
                utx.commit();
                return true;
            }

            
            String appId = getNextAppointmentId();
            String jitsiLink = "http://localhost:8080/MizukiForest/view/therapy/callSession.jsp?room=" + appId + "_" + user.getUserid();
            Appointment appointment = new Appointment(appId, user, timeslot, jitsiLink, "Scheduled", false);
            
            
            timeslot.setStatus(false);
            utx.begin();
            em.merge(timeslot);
            em.persist(appointment);
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
    
    public boolean cancelAppointment(Users user, String timeSlotId){
        try {
            TimeSlotService tss = new TimeSlotService(em, utx);
            Timeslot timeslot = tss.getTimeSlotById(timeSlotId);

            if(timeslot != null){
                Appointment app = em.createQuery(
                        "SELECT a FROM Appointment a where a.userid = :user AND a.timeslotid = :timeslot", Appointment.class)
                        .setParameter("user", user)
                        .setParameter("timeslot", timeslot)
                        .getSingleResult();
                
                //set appointment to deleted
                app.setIsdeleted(true);
                
                // make ti available
                timeslot.setStatus(true);

                
                
                utx.begin();                
                em.merge(app);
                em.merge(timeslot);
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
    
    public boolean updateLinkSent(Appointment app){
        app.setLinksent(true);

        try {
            System.out.println("SENT APPOINTMENT LINK");
            utx.begin();
            em.merge(app);
            utx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        
    }
    
    public Appointment getAppointmentById(String appId) {
        Appointment app = em.find(Appointment.class, appId);
        return app;
    }
    
    //to check whether has appointment before even if isdeleted
    public Appointment getDeletedAppointment(Users user, Timeslot timeslot){
        try {
            Appointment app = em.createQuery(
                    "SELECT a FROM Appointment a where a.userid = :user AND a.timeslotid = :timeslot", Appointment.class)
                    .setParameter("user", user)
                    .setParameter("timeslot", timeslot)
                    .getSingleResult();

            return app;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public boolean hasAppointment(Users user, Timeslot timeSlot) {
        try {
            Appointment app = em.createQuery(
                "SELECT a FROM Appointment a where a.userid = :user AND a.timeslotid = :timeslot", Appointment.class)
                .setParameter("user", user)
                .setParameter("timeslot", timeSlot)
                .getSingleResult();

            if(app.getIsdeleted()){
                return false;
            }
            
            return true;
        }catch(NoResultException e){
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public String getNextAppointmentId() {
        String prefix = "APAA";

        // Get max numeric part from existing IDs
        String sql = "SELECT MAX(CAST(SUBSTR(appointmentid, 5) AS INT)) FROM APPOINTMENT";
        Integer maxNum = (Integer) em.createNativeQuery(sql).getSingleResult();

        int nextNum = (maxNum == null) ? 1 : maxNum + 1;

        // Pad with zeros to 4 digits
        String nextId = String.format("%s%04d", prefix, nextNum);

        return nextId;
    }

    
}
