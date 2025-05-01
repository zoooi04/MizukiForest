/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JiaQuann
 */
@Entity
@Table(name = "THERAPIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Therapist.findAll", query = "SELECT t FROM Therapist t"),
    @NamedQuery(name = "Therapist.findByTherapistid", query = "SELECT t FROM Therapist t WHERE t.therapistid = :therapistid"),
    @NamedQuery(name = "Therapist.findByTherapistname", query = "SELECT t FROM Therapist t WHERE t.therapistname = :therapistname"),
    @NamedQuery(name = "Therapist.findByTherapistpw", query = "SELECT t FROM Therapist t WHERE t.therapistpw = :therapistpw"),
    @NamedQuery(name = "Therapist.findByTherapistemail", query = "SELECT t FROM Therapist t WHERE t.therapistemail = :therapistemail"),
    @NamedQuery(name = "Therapist.findByAreaofinterest", query = "SELECT t FROM Therapist t WHERE t.areaofinterest = :areaofinterest"),
    @NamedQuery(name = "Therapist.findByLanguagespoken", query = "SELECT t FROM Therapist t WHERE t.languagespoken = :languagespoken"),
    @NamedQuery(name = "Therapist.findByYearofexp", query = "SELECT t FROM Therapist t WHERE t.yearofexp = :yearofexp"),
    @NamedQuery(name = "Therapist.findByCulture", query = "SELECT t FROM Therapist t WHERE t.culture = :culture"),
    @NamedQuery(name = "Therapist.findByTherapiststatus", query = "SELECT t FROM Therapist t WHERE t.therapiststatus = :therapiststatus"),
    @NamedQuery(name = "Therapist.findByIsdeleted", query = "SELECT t FROM Therapist t WHERE t.isdeleted = :isdeleted")})
public class Therapist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "THERAPISTID")
    private String therapistid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "THERAPISTNAME")
    private String therapistname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "THERAPISTPW")
    private String therapistpw;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "THERAPISTEMAIL")
    private String therapistemail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "AREAOFINTEREST")
    private String areaofinterest;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "LANGUAGESPOKEN")
    private String languagespoken;
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAROFEXP")
    private int yearofexp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CULTURE")
    private String culture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "THERAPISTSTATUS")
    private Boolean therapiststatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Lob
    @Column(name = "THERAPISTIMAGE")
    private Serializable therapistimage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "therapistid")
    private List<Message> messageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "therapistid")
    private List<Timeslot> timeslotList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "therapistid")
    private List<Usertherapist> usertherapistList;

    public Therapist() {
    }

    public Therapist(String therapistid) {
        this.therapistid = therapistid;
    }

    public Therapist(String therapistid, String therapistname, String therapistpw, String therapistemail, String areaofinterest, String languagespoken, int yearofexp, String culture, Boolean therapiststatus, Boolean isdeleted) {
        this.therapistid = therapistid;
        this.therapistname = therapistname;
        this.therapistpw = therapistpw;
        this.therapistemail = therapistemail;
        this.areaofinterest = areaofinterest;
        this.languagespoken = languagespoken;
        this.yearofexp = yearofexp;
        this.culture = culture;
        this.therapiststatus = therapiststatus;
        this.isdeleted = isdeleted;
    }

    public String getTherapistid() {
        return therapistid;
    }

    public void setTherapistid(String therapistid) {
        this.therapistid = therapistid;
    }

    public String getTherapistname() {
        return therapistname;
    }

    public void setTherapistname(String therapistname) {
        this.therapistname = therapistname;
    }

    public String getTherapistpw() {
        return therapistpw;
    }

    public void setTherapistpw(String therapistpw) {
        this.therapistpw = therapistpw;
    }

    public String getTherapistemail() {
        return therapistemail;
    }

    public void setTherapistemail(String therapistemail) {
        this.therapistemail = therapistemail;
    }

    public String getAreaofinterest() {
        return areaofinterest;
    }

    public void setAreaofinterest(String areaofinterest) {
        this.areaofinterest = areaofinterest;
    }

    public String getLanguagespoken() {
        return languagespoken;
    }

    public void setLanguagespoken(String languagespoken) {
        this.languagespoken = languagespoken;
    }

    public int getYearofexp() {
        return yearofexp;
    }

    public void setYearofexp(int yearofexp) {
        this.yearofexp = yearofexp;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public Boolean getTherapiststatus() {
        return therapiststatus;
    }

    public void setTherapiststatus(Boolean therapiststatus) {
        this.therapiststatus = therapiststatus;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Serializable getTherapistimage() {
        return therapistimage;
    }

    public void setTherapistimage(Serializable therapistimage) {
        this.therapistimage = therapistimage;
    }

    @XmlTransient
    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @XmlTransient
    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public void setTimeslotList(List<Timeslot> timeslotList) {
        this.timeslotList = timeslotList;
    }

    @XmlTransient
    public List<Usertherapist> getUsertherapistList() {
        return usertherapistList;
    }

    public void setUsertherapistList(List<Usertherapist> usertherapistList) {
        this.usertherapistList = usertherapistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (therapistid != null ? therapistid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Therapist)) {
            return false;
        }
        Therapist other = (Therapist) object;
        if ((this.therapistid == null && other.therapistid != null) || (this.therapistid != null && !this.therapistid.equals(other.therapistid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Therapist[ therapistid=" + therapistid + " ]";
    }
    
}
