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
 * @author johno
 */
@Entity
@Table(name = "CALLSESSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Callsession.findAll", query = "SELECT c FROM Callsession c"),
    @NamedQuery(name = "Callsession.findByCallsessionid", query = "SELECT c FROM Callsession c WHERE c.callsessionid = :callsessionid"),
    @NamedQuery(name = "Callsession.findByDuration", query = "SELECT c FROM Callsession c WHERE c.duration = :duration"),
    @NamedQuery(name = "Callsession.findByIsdeleted", query = "SELECT c FROM Callsession c WHERE c.isdeleted = :isdeleted")})
public class Callsession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "CALLSESSIONID")
    private String callsessionid;
    @Column(name = "DURATION")
    private Integer duration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "APPOINTMENTID", referencedColumnName = "APPOINTMENTID")
    @ManyToOne(optional = false)
    private Appointment appointmentid;

    public Callsession() {
    }

    public Callsession(String callsessionid) {
        this.callsessionid = callsessionid;
    }

    public Callsession(String callsessionid, Boolean isdeleted) {
        this.callsessionid = callsessionid;
        this.isdeleted = isdeleted;
    }

    public String getCallsessionid() {
        return callsessionid;
    }

    public void setCallsessionid(String callsessionid) {
        this.callsessionid = callsessionid;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Appointment getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(Appointment appointmentid) {
        this.appointmentid = appointmentid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (callsessionid != null ? callsessionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Callsession)) {
            return false;
        }
        Callsession other = (Callsession) object;
        if ((this.callsessionid == null && other.callsessionid != null) || (this.callsessionid != null && !this.callsessionid.equals(other.callsessionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Callsession[ callsessionid=" + callsessionid + " ]";
    }
    
}
