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
 * @author JiaQuann
 */
@Entity
@Table(name = "ACHIEVEMENTCATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Achievementcategory.findAll", query = "SELECT a FROM Achievementcategory a"),
    @NamedQuery(name = "Achievementcategory.findByAchievementcategoryid", query = "SELECT a FROM Achievementcategory a WHERE a.achievementcategoryid = :achievementcategoryid"),
    @NamedQuery(name = "Achievementcategory.findByAchievementcategoryname", query = "SELECT a FROM Achievementcategory a WHERE a.achievementcategoryname = :achievementcategoryname"),
    @NamedQuery(name = "Achievementcategory.findByIsdeleted", query = "SELECT a FROM Achievementcategory a WHERE a.isdeleted = :isdeleted")})
public class Achievementcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ACHIEVEMENTCATEGORYID")
    private String achievementcategoryid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ACHIEVEMENTCATEGORYNAME")
    private String achievementcategoryname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "BADGEID", referencedColumnName = "BADGEID")
    @ManyToOne(optional = false)
    private Badge badgeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achievementcategoryid")
    private List<Achievement> achievementList;

    public Achievementcategory() {
    }

    public Achievementcategory(String achievementcategoryid) {
        this.achievementcategoryid = achievementcategoryid;
    }

    public Achievementcategory(String achievementcategoryid, String achievementcategoryname, Boolean isdeleted) {
        this.achievementcategoryid = achievementcategoryid;
        this.achievementcategoryname = achievementcategoryname;
        this.isdeleted = isdeleted;
    }

    public String getAchievementcategoryid() {
        return achievementcategoryid;
    }

    public void setAchievementcategoryid(String achievementcategoryid) {
        this.achievementcategoryid = achievementcategoryid;
    }

    public String getAchievementcategoryname() {
        return achievementcategoryname;
    }

    public void setAchievementcategoryname(String achievementcategoryname) {
        this.achievementcategoryname = achievementcategoryname;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Badge getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(Badge badgeid) {
        this.badgeid = badgeid;
    }

    @XmlTransient
    public List<Achievement> getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(List<Achievement> achievementList) {
        this.achievementList = achievementList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (achievementcategoryid != null ? achievementcategoryid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Achievementcategory)) {
            return false;
        }
        Achievementcategory other = (Achievementcategory) object;
        if ((this.achievementcategoryid == null && other.achievementcategoryid != null) || (this.achievementcategoryid != null && !this.achievementcategoryid.equals(other.achievementcategoryid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Achievementcategory[ achievementcategoryid=" + achievementcategoryid + " ]";
    }
    
}
