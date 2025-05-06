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
 * @author JiaQuann
 */
@Embeddable
public class UserbadgePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERID")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "BADGEID")
    private String badgeid;

    public UserbadgePK() {
    }

    public UserbadgePK(String userid, String badgeid) {
        this.userid = userid;
        this.badgeid = badgeid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(String badgeid) {
        this.badgeid = badgeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        hash += (badgeid != null ? badgeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserbadgePK)) {
            return false;
        }
        UserbadgePK other = (UserbadgePK) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        if ((this.badgeid == null && other.badgeid != null) || (this.badgeid != null && !this.badgeid.equals(other.badgeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UserbadgePK[ userid=" + userid + ", badgeid=" + badgeid + " ]";
    }
    
}
