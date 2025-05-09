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
public class CommentvotePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "THREADCOMMENTID")
    private String threadcommentid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERID")
    private String userid;

    public CommentvotePK() {
    }

    public CommentvotePK(String threadcommentid, String userid) {
        this.threadcommentid = threadcommentid;
        this.userid = userid;
    }

    public String getThreadcommentid() {
        return threadcommentid;
    }

    public void setThreadcommentid(String threadcommentid) {
        this.threadcommentid = threadcommentid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadcommentid != null ? threadcommentid.hashCode() : 0);
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentvotePK)) {
            return false;
        }
        CommentvotePK other = (CommentvotePK) object;
        if ((this.threadcommentid == null && other.threadcommentid != null) || (this.threadcommentid != null && !this.threadcommentid.equals(other.threadcommentid))) {
            return false;
        }
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CommentvotePK[ threadcommentid=" + threadcommentid + ", userid=" + userid + " ]";
    }
    
}
