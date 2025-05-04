/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author johno
 */
@Entity
@Table(name = "USERTAG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usertag.findAll", query = "SELECT u FROM Usertag u"),
    @NamedQuery(name = "Usertag.findByUsertagid", query = "SELECT u FROM Usertag u WHERE u.usertagid = :usertagid"),
    @NamedQuery(name = "Usertag.findByNewtagname", query = "SELECT u FROM Usertag u WHERE u.newtagname = :newtagname"),
    @NamedQuery(name = "Usertag.findByIsdeleted", query = "SELECT u FROM Usertag u WHERE u.isdeleted = :isdeleted")})
public class Usertag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERTAGID")
    private String usertagid;
    @Size(max = 50)
    @Column(name = "NEWTAGNAME")
    private String newtagname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "TAGID", referencedColumnName = "TAGID")
    @ManyToOne(optional = false)
    private Tag tagid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;
    @OneToMany(mappedBy = "usertagid")
    private List<Diarytag> diarytagList;

    public Usertag() {
    }

    public Usertag(String usertagid) {
        this.usertagid = usertagid;
    }

    public Usertag(String usertagid, Boolean isdeleted) {
        this.usertagid = usertagid;
        this.isdeleted = isdeleted;
    }

    public String getUsertagid() {
        return usertagid;
    }

    public void setUsertagid(String usertagid) {
        this.usertagid = usertagid;
    }

    public String getNewtagname() {
        return newtagname;
    }

    public void setNewtagname(String newtagname) {
        this.newtagname = newtagname;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Tag getTagid() {
        return tagid;
    }

    public void setTagid(Tag tagid) {
        this.tagid = tagid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @XmlTransient
    public List<Diarytag> getDiarytagList() {
        return diarytagList;
    }

    public void setDiarytagList(List<Diarytag> diarytagList) {
        this.diarytagList = diarytagList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usertagid != null ? usertagid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usertag)) {
            return false;
        }
        Usertag other = (Usertag) object;
        if ((this.usertagid == null && other.usertagid != null) || (this.usertagid != null && !this.usertagid.equals(other.usertagid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Usertag[ usertagid=" + usertagid + " ]";
    }
    
}
