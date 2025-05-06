/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author huaiern
 */
@Entity
@Table(name = "USERTHERAPIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usertherapist.findAll", query = "SELECT u FROM Usertherapist u"),
    @NamedQuery(name = "Usertherapist.findByUsertherapistid", query = "SELECT u FROM Usertherapist u WHERE u.usertherapistid = :usertherapistid"),
    @NamedQuery(name = "Usertherapist.findByDateestablished", query = "SELECT u FROM Usertherapist u WHERE u.dateestablished = :dateestablished"),
    @NamedQuery(name = "Usertherapist.findByDateend", query = "SELECT u FROM Usertherapist u WHERE u.dateend = :dateend"),
    @NamedQuery(name = "Usertherapist.findByIsdeleted", query = "SELECT u FROM Usertherapist u WHERE u.isdeleted = :isdeleted")})
public class Usertherapist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERTHERAPISTID")
    private String usertherapistid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEESTABLISHED")
    @Temporal(TemporalType.DATE)
    private Date dateestablished;
    @Column(name = "DATEEND")
    @Temporal(TemporalType.DATE)
    private Date dateend;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "THERAPISTID", referencedColumnName = "THERAPISTID")
    @ManyToOne(optional = false)
    private Therapist therapistid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Usertherapist() {
    }

    public Usertherapist(String usertherapistid) {
        this.usertherapistid = usertherapistid;
    }

    public Usertherapist(String usertherapistid, Date dateestablished, Boolean isdeleted) {
        this.usertherapistid = usertherapistid;
        this.dateestablished = dateestablished;
        this.isdeleted = isdeleted;
    }
    
    public Usertherapist(String usertherapistid, Therapist therapist, Users user, Date dateestablished, Date dateend, Boolean isdeleted) {
        this.usertherapistid = usertherapistid;
        this.therapistid = therapist;
        this.userid = user;
        this.dateestablished = dateestablished;
        this.dateend = dateend;
        this.isdeleted = isdeleted;
    }

    public String getUsertherapistid() {
        return usertherapistid;
    }

    public void setUsertherapistid(String usertherapistid) {
        this.usertherapistid = usertherapistid;
    }

    public Date getDateestablished() {
        return dateestablished;
    }

    public void setDateestablished(Date dateestablished) {
        this.dateestablished = dateestablished;
    }

    public Date getDateend() {
        return dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Therapist getTherapistid() {
        return therapistid;
    }

    public void setTherapistid(Therapist therapistid) {
        this.therapistid = therapistid;
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
        hash += (usertherapistid != null ? usertherapistid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usertherapist)) {
            return false;
        }
        Usertherapist other = (Usertherapist) object;
        if ((this.usertherapistid == null && other.usertherapistid != null) || (this.usertherapistid != null && !this.usertherapistid.equals(other.usertherapistid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Usertherapist[ usertherapistid=" + usertherapistid + " ]";
    }
    
}
