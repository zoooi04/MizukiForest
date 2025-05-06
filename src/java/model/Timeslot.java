/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author huaiern
 */
@Entity
@Table(name = "TIMESLOT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timeslot.findAll", query = "SELECT t FROM Timeslot t"),
    @NamedQuery(name = "Timeslot.findByTimeslotid", query = "SELECT t FROM Timeslot t WHERE t.timeslotid = :timeslotid"),
    @NamedQuery(name = "Timeslot.findByTsdate", query = "SELECT t FROM Timeslot t WHERE t.tsdate = :tsdate"),
    @NamedQuery(name = "Timeslot.findByStarttime", query = "SELECT t FROM Timeslot t WHERE t.starttime = :starttime"),
    @NamedQuery(name = "Timeslot.findByEndtime", query = "SELECT t FROM Timeslot t WHERE t.endtime = :endtime"),
    @NamedQuery(name = "Timeslot.findByStatus", query = "SELECT t FROM Timeslot t WHERE t.status = :status"),
    @NamedQuery(name = "Timeslot.findByIsdeleted", query = "SELECT t FROM Timeslot t WHERE t.isdeleted = :isdeleted")})
public class Timeslot implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private Boolean status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "TIMESLOTID")
    private String timeslotid;
    @Column(name = "TSDATE")
    @Temporal(TemporalType.DATE)
    private Date tsdate;
    @Column(name = "STARTTIME")
    @Temporal(TemporalType.TIME)
    private Date starttime;
    @Column(name = "ENDTIME")
    @Temporal(TemporalType.TIME)
    private Date endtime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeslotid")
    private List<Appointment> appointmentList;
    @JoinColumn(name = "THERAPISTID", referencedColumnName = "THERAPISTID")
    @ManyToOne(optional = false)
    private Therapist therapistid;

    public Timeslot() {
    }

    public Timeslot(String timeslotid) {
        this.timeslotid = timeslotid;
    }

    public Timeslot(String timeslotid, Boolean status, Boolean isdeleted) {
        this.timeslotid = timeslotid;
        this.status = status;
        this.isdeleted = isdeleted;
    }
    
    public Timeslot(String timeslotid, Therapist therapist, Date date, Date startTime, Date endTime, Boolean status, Boolean isdeleted) {
        this.timeslotid = timeslotid;
        this.therapistid = therapist;
        this.tsdate = date;
        this.starttime = startTime;
        this.endtime = endTime;
        this.status = status;
        this.isdeleted = isdeleted;
    }

    public String getTimeslotid() {
        return timeslotid;
    }

    public void setTimeslotid(String timeslotid) {
        this.timeslotid = timeslotid;
    }

    public Date getTsdate() {
        return tsdate;
    }

    public void setTsdate(Date tsdate) {
        this.tsdate = tsdate;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }


    @XmlTransient
    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public Therapist getTherapistid() {
        return therapistid;
    }

    public void setTherapistid(Therapist therapistid) {
        this.therapistid = therapistid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timeslotid != null ? timeslotid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timeslot)) {
            return false;
        }
        Timeslot other = (Timeslot) object;
        if ((this.timeslotid == null && other.timeslotid != null) || (this.timeslotid != null && !this.timeslotid.equals(other.timeslotid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Timeslot[ timeslotid=" + timeslotid + " ]";
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
