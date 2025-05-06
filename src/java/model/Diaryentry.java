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
 * @author JiaQuann
 */
@Entity
@Table(name = "DIARYENTRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diaryentry.findAll", query = "SELECT d FROM Diaryentry d"),
    @NamedQuery(name = "Diaryentry.findByDiaryid", query = "SELECT d FROM Diaryentry d WHERE d.diaryid = :diaryid"),
    @NamedQuery(name = "Diaryentry.findByDiarytitle", query = "SELECT d FROM Diaryentry d WHERE d.diarytitle = :diarytitle"),
    @NamedQuery(name = "Diaryentry.findByDescription", query = "SELECT d FROM Diaryentry d WHERE d.description = :description"),
    @NamedQuery(name = "Diaryentry.findByMood", query = "SELECT d FROM Diaryentry d WHERE d.mood = :mood"),
    @NamedQuery(name = "Diaryentry.findByDatewritten", query = "SELECT d FROM Diaryentry d WHERE d.datewritten = :datewritten"),
    @NamedQuery(name = "Diaryentry.findByIsarchived", query = "SELECT d FROM Diaryentry d WHERE d.isarchived = :isarchived"),
    @NamedQuery(name = "Diaryentry.findByIsdeleted", query = "SELECT d FROM Diaryentry d WHERE d.isdeleted = :isdeleted")})
public class Diaryentry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "DIARYID")
    private String diaryid;
    @Size(max = 50)
    @Column(name = "DIARYTITLE")
    private String diarytitle;
    @Size(max = 400)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 50)
    @Column(name = "MOOD")
    private String mood;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEWRITTEN")
    @Temporal(TemporalType.DATE)
    private Date datewritten;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISARCHIVED")
    private Boolean isarchived;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Diaryentry() {
    }

    public Diaryentry(String diaryid) {
        this.diaryid = diaryid;
    }

    public Diaryentry(String diaryid, Date datewritten, Boolean isarchived, Boolean isdeleted) {
        this.diaryid = diaryid;
        this.datewritten = datewritten;
        this.isarchived = isarchived;
        this.isdeleted = isdeleted;
    }

    public String getDiaryid() {
        return diaryid;
    }

    public void setDiaryid(String diaryid) {
        this.diaryid = diaryid;
    }

    public String getDiarytitle() {
        return diarytitle;
    }

    public void setDiarytitle(String diarytitle) {
        this.diarytitle = diarytitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Date getDatewritten() {
        return datewritten;
    }

    public void setDatewritten(Date datewritten) {
        this.datewritten = datewritten;
    }

    public Boolean getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(Boolean isarchived) {
        this.isarchived = isarchived;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
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
        hash += (diaryid != null ? diaryid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diaryentry)) {
            return false;
        }
        Diaryentry other = (Diaryentry) object;
        if ((this.diaryid == null && other.diaryid != null) || (this.diaryid != null && !this.diaryid.equals(other.diaryid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Diaryentry[ diaryid=" + diaryid + " ]";
    }
    
}
