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
@Table(name = "BADGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Badge.findAll", query = "SELECT b FROM Badge b"),
    @NamedQuery(name = "Badge.findByBadgeid", query = "SELECT b FROM Badge b WHERE b.badgeid = :badgeid"),
    @NamedQuery(name = "Badge.findByBadgename", query = "SELECT b FROM Badge b WHERE b.badgename = :badgename"),
    @NamedQuery(name = "Badge.findByIsdeleted", query = "SELECT b FROM Badge b WHERE b.isdeleted = :isdeleted")})
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "BADGEID")
    private String badgeid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BADGENAME")
    private String badgename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Lob
    @Column(name = "BADGEIMAGE")
    private Serializable badgeimage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "badgeid")
    private List<Achievementcategory> achievementcategoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "badge")
    private List<Userbadge> userbadgeList;

    public Badge() {
    }

    public Badge(String badgeid) {
        this.badgeid = badgeid;
    }

    public Badge(String badgeid, String badgename, Boolean isdeleted) {
        this.badgeid = badgeid;
        this.badgename = badgename;
        this.isdeleted = isdeleted;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(String badgeid) {
        this.badgeid = badgeid;
    }

    public String getBadgename() {
        return badgename;
    }

    public void setBadgename(String badgename) {
        this.badgename = badgename;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Serializable getBadgeimage() {
        return badgeimage;
    }

    public void setBadgeimage(Serializable badgeimage) {
        this.badgeimage = badgeimage;
    }

    @XmlTransient
    public List<Achievementcategory> getAchievementcategoryList() {
        return achievementcategoryList;
    }

    public void setAchievementcategoryList(List<Achievementcategory> achievementcategoryList) {
        this.achievementcategoryList = achievementcategoryList;
    }

    @XmlTransient
    public List<Userbadge> getUserbadgeList() {
        return userbadgeList;
    }

    public void setUserbadgeList(List<Userbadge> userbadgeList) {
        this.userbadgeList = userbadgeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (badgeid != null ? badgeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Badge)) {
            return false;
        }
        Badge other = (Badge) object;
        if ((this.badgeid == null && other.badgeid != null) || (this.badgeid != null && !this.badgeid.equals(other.badgeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Badge[ badgeid=" + badgeid + " ]";
    }
    
}
