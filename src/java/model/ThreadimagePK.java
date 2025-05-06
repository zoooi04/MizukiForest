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
public class ThreadimagePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "IMAGEID")
    private String imageid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "THREADID")
    private String threadid;

    public ThreadimagePK() {
    }

    public ThreadimagePK(String imageid, String threadid) {
        this.imageid = imageid;
        this.threadid = threadid;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getThreadid() {
        return threadid;
    }

    public void setThreadid(String threadid) {
        this.threadid = threadid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imageid != null ? imageid.hashCode() : 0);
        hash += (threadid != null ? threadid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ThreadimagePK)) {
            return false;
        }
        ThreadimagePK other = (ThreadimagePK) object;
        if ((this.imageid == null && other.imageid != null) || (this.imageid != null && !this.imageid.equals(other.imageid))) {
            return false;
        }
        if ((this.threadid == null && other.threadid != null) || (this.threadid != null && !this.threadid.equals(other.threadid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ThreadimagePK[ imageid=" + imageid + ", threadid=" + threadid + " ]";
    }
    
}
