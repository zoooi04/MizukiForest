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
 * @author JiaQuann
 */
@Entity
@Table(name = "DIARYTAG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diarytag.findAll", query = "SELECT d FROM Diarytag d"),
    @NamedQuery(name = "Diarytag.findByDiarytagid", query = "SELECT d FROM Diarytag d WHERE d.diarytagid = :diarytagid"),
    @NamedQuery(name = "Diarytag.findByIsdeleted", query = "SELECT d FROM Diarytag d WHERE d.isdeleted = :isdeleted")})
public class Diarytag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "DIARYTAGID")
    private String diarytagid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "DIARYID", referencedColumnName = "DIARYID")
    @ManyToOne(optional = false)
    private Diaryentry diaryid;
    @JoinColumn(name = "TAGID", referencedColumnName = "TAGID")
    @ManyToOne
    private Tag tagid;
    @JoinColumn(name = "USERTAGID", referencedColumnName = "USERTAGID")
    @ManyToOne
    private Usertag usertagid;

    public Diarytag() {
    }

    public Diarytag(String diarytagid) {
        this.diarytagid = diarytagid;
    }

    public Diarytag(String diarytagid, Boolean isdeleted) {
        this.diarytagid = diarytagid;
        this.isdeleted = isdeleted;
    }

    public String getDiarytagid() {
        return diarytagid;
    }

    public void setDiarytagid(String diarytagid) {
        this.diarytagid = diarytagid;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Diaryentry getDiaryid() {
        return diaryid;
    }

    public void setDiaryid(Diaryentry diaryid) {
        this.diaryid = diaryid;
    }

    public Tag getTagid() {
        return tagid;
    }

    public void setTagid(Tag tagid) {
        this.tagid = tagid;
    }

    public Usertag getUsertagid() {
        return usertagid;
    }

    public void setUsertagid(Usertag usertagid) {
        this.usertagid = usertagid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diarytagid != null ? diarytagid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diarytag)) {
            return false;
        }
        Diarytag other = (Diarytag) object;
        if ((this.diarytagid == null && other.diarytagid != null) || (this.diarytagid != null && !this.diarytagid.equals(other.diarytagid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Diarytag[ diarytagid=" + diarytagid + " ]";
    }
    
}
