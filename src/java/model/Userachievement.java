/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JiaQuann
 */
@Entity
@Table(name = "USERACHIEVEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userachievement.findAll", query = "SELECT u FROM Userachievement u"),
    @NamedQuery(name = "Userachievement.findByUserid", query = "SELECT u FROM Userachievement u WHERE u.userachievementPK.userid = :userid"),
    @NamedQuery(name = "Userachievement.findByAchievementid", query = "SELECT u FROM Userachievement u WHERE u.userachievementPK.achievementid = :achievementid"),
    @NamedQuery(name = "Userachievement.findByDatecompleted", query = "SELECT u FROM Userachievement u WHERE u.datecompleted = :datecompleted"),
    @NamedQuery(name = "Userachievement.findByIsdeleted", query = "SELECT u FROM Userachievement u WHERE u.isdeleted = :isdeleted")})
public class Userachievement implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserachievementPK userachievementPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATECOMPLETED")
    @Temporal(TemporalType.DATE)
    private Date datecompleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "ACHIEVEMENTID", referencedColumnName = "ACHIEVEMENTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Achievement achievement;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Userachievement() {
    }

    public Userachievement(UserachievementPK userachievementPK) {
        this.userachievementPK = userachievementPK;
    }

    public Userachievement(UserachievementPK userachievementPK, Date datecompleted, Boolean isdeleted) {
        this.userachievementPK = userachievementPK;
        this.datecompleted = datecompleted;
        this.isdeleted = isdeleted;
    }

    public Userachievement(String userid, String achievementid) {
        this.userachievementPK = new UserachievementPK(userid, achievementid);
    }

    public UserachievementPK getUserachievementPK() {
        return userachievementPK;
    }

    public void setUserachievementPK(UserachievementPK userachievementPK) {
        this.userachievementPK = userachievementPK;
    }

    public Date getDatecompleted() {
        return datecompleted;
    }

    public void setDatecompleted(Date datecompleted) {
        this.datecompleted = datecompleted;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userachievementPK != null ? userachievementPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userachievement)) {
            return false;
        }
        Userachievement other = (Userachievement) object;
        if ((this.userachievementPK == null && other.userachievementPK != null) || (this.userachievementPK != null && !this.userachievementPK.equals(other.userachievementPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Userachievement[ userachievementPK=" + userachievementPK + " ]";
    }
    
}
