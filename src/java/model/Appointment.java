/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author huaiern
 */
@Entity
@Table(name = "APPOINTMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findByAppointmentid", query = "SELECT a FROM Appointment a WHERE a.appointmentid = :appointmentid"),
    @NamedQuery(name = "Appointment.findByAppointmentlink", query = "SELECT a FROM Appointment a WHERE a.appointmentlink = :appointmentlink"),
    @NamedQuery(name = "Appointment.findByStatus", query = "SELECT a FROM Appointment a WHERE a.status = :status"),
    @NamedQuery(name = "Appointment.findByIsdeleted", query = "SELECT a FROM Appointment a WHERE a.isdeleted = :isdeleted"),
    @NamedQuery(name = "Appointment.findByLinksent", query = "SELECT a FROM Appointment a WHERE a.linksent = :linksent")})
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "APPOINTMENTID")
    private String appointmentid;
    @Size(max = 100)
    @Column(name = "APPOINTMENTLINK")
    private String appointmentlink;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LINKSENT")
    private Boolean linksent;
    @JoinColumn(name = "TIMESLOTID", referencedColumnName = "TIMESLOTID")
    @ManyToOne(optional = false)
    private Timeslot timeslotid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Appointment() {
    }

    public Appointment(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public Appointment(String appointmentid, Boolean isdeleted, Boolean linksent) {
        this.appointmentid = appointmentid;
        this.isdeleted = isdeleted;
        this.linksent = linksent;
    }
    
    public Appointment(String appointmentid, Users user, Timeslot timeslot, String link, String status,Boolean isDeleted) {
        this.appointmentid = appointmentid;
        this.userid = user;
        this.timeslotid = timeslot;
        this.appointmentlink = link;
        this.status = status;
        this.linksent = false;
        this.isdeleted = isDeleted;

    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public String getAppointmentlink() {
        return appointmentlink;
    }

    public void setAppointmentlink(String appointmentlink) {
        this.appointmentlink = appointmentlink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Boolean getLinksent() {
        return linksent;
    }

    public void setLinksent(Boolean linksent) {
        this.linksent = linksent;
    }

    public Timeslot getTimeslotid() {
        return timeslotid;
    }

    public void setTimeslotid(Timeslot timeslotid) {
        this.timeslotid = timeslotid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentid != null ? appointmentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.appointmentid == null && other.appointmentid != null) || (this.appointmentid != null && !this.appointmentid.equals(other.appointmentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Appointment[ appointmentid=" + appointmentid + " ]";
    }
    
}
