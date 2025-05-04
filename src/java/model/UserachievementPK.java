/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author johno
 */
@Embeddable
public class UserachievementPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERID")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ACHIEVEMENTID")
    private String achievementid;

    public UserachievementPK() {
    }

    public UserachievementPK(String userid, String achievementid) {
        this.userid = userid;
        this.achievementid = achievementid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAchievementid() {
        return achievementid;
    }

    public void setAchievementid(String achievementid) {
        this.achievementid = achievementid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        hash += (achievementid != null ? achievementid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserachievementPK)) {
            return false;
        }
        UserachievementPK other = (UserachievementPK) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        if ((this.achievementid == null && other.achievementid != null) || (this.achievementid != null && !this.achievementid.equals(other.achievementid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UserachievementPK[ userid=" + userid + ", achievementid=" + achievementid + " ]";
    }
    
}
