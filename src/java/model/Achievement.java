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
@Table(name = "ACHIEVEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Achievement.findAll", query = "SELECT a FROM Achievement a"),
    @NamedQuery(name = "Achievement.findByAchievementid", query = "SELECT a FROM Achievement a WHERE a.achievementid = :achievementid"),
    @NamedQuery(name = "Achievement.findByAchievementname", query = "SELECT a FROM Achievement a WHERE a.achievementname = :achievementname"),
    @NamedQuery(name = "Achievement.findByAchievementdescription", query = "SELECT a FROM Achievement a WHERE a.achievementdescription = :achievementdescription"),
    @NamedQuery(name = "Achievement.findByHidden", query = "SELECT a FROM Achievement a WHERE a.hidden = :hidden"),
    @NamedQuery(name = "Achievement.findByIsdeleted", query = "SELECT a FROM Achievement a WHERE a.isdeleted = :isdeleted")})
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ACHIEVEMENTID")
    private String achievementid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ACHIEVEMENTNAME")
    private String achievementname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ACHIEVEMENTDESCRIPTION")
    private String achievementdescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIDDEN")
    private Boolean hidden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achievementid")
    private List<Achievementreward> achievementrewardList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achievement")
    private List<Userachievement> userachievementList;
    @JoinColumn(name = "ACHIEVEMENTCATEGORYID", referencedColumnName = "ACHIEVEMENTCATEGORYID")
    @ManyToOne(optional = false)
    private Achievementcategory achievementcategoryid;

    public Achievement() {
    }

    public Achievement(String achievementid) {
        this.achievementid = achievementid;
    }

    public Achievement(String achievementid, String achievementname, String achievementdescription, Boolean hidden, Boolean isdeleted) {
        this.achievementid = achievementid;
        this.achievementname = achievementname;
        this.achievementdescription = achievementdescription;
        this.hidden = hidden;
        this.isdeleted = isdeleted;
    }

    public String getAchievementid() {
        return achievementid;
    }

    public void setAchievementid(String achievementid) {
        this.achievementid = achievementid;
    }

    public String getAchievementname() {
        return achievementname;
    }

    public void setAchievementname(String achievementname) {
        this.achievementname = achievementname;
    }

    public String getAchievementdescription() {
        return achievementdescription;
    }

    public void setAchievementdescription(String achievementdescription) {
        this.achievementdescription = achievementdescription;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Achievementreward> getAchievementrewardList() {
        return achievementrewardList;
    }

    public void setAchievementrewardList(List<Achievementreward> achievementrewardList) {
        this.achievementrewardList = achievementrewardList;
    }

    @XmlTransient
    public List<Userachievement> getUserachievementList() {
        return userachievementList;
    }

    public void setUserachievementList(List<Userachievement> userachievementList) {
        this.userachievementList = userachievementList;
    }

    public Achievementcategory getAchievementcategoryid() {
        return achievementcategoryid;
    }

    public void setAchievementcategoryid(Achievementcategory achievementcategoryid) {
        this.achievementcategoryid = achievementcategoryid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (achievementid != null ? achievementid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Achievement)) {
            return false;
        }
        Achievement other = (Achievement) object;
        if ((this.achievementid == null && other.achievementid != null) || (this.achievementid != null && !this.achievementid.equals(other.achievementid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Achievement[ achievementid=" + achievementid + " ]";
    }
    
}
