/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johno
 */
@Entity
@Table(name = "THREADVOTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Threadvote.findAll", query = "SELECT t FROM Threadvote t"),
    @NamedQuery(name = "Threadvote.findByThreadid", query = "SELECT t FROM Threadvote t WHERE t.threadvotePK.threadid = :threadid"),
    @NamedQuery(name = "Threadvote.findByUserid", query = "SELECT t FROM Threadvote t WHERE t.threadvotePK.userid = :userid"),
    @NamedQuery(name = "Threadvote.findByVotetype", query = "SELECT t FROM Threadvote t WHERE t.votetype = :votetype")})
public class Threadvote implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ThreadvotePK threadvotePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOTETYPE")
    private Boolean votetype;
    @JoinColumn(name = "THREADID", referencedColumnName = "THREADID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Thread thread;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Threadvote() {
    }

    public Threadvote(ThreadvotePK threadvotePK) {
        this.threadvotePK = threadvotePK;
    }

    public Threadvote(ThreadvotePK threadvotePK, Boolean votetype) {
        this.threadvotePK = threadvotePK;
        this.votetype = votetype;
    }

    public Threadvote(String threadid, String userid) {
        this.threadvotePK = new ThreadvotePK(threadid, userid);
    }

    public ThreadvotePK getThreadvotePK() {
        return threadvotePK;
    }

    public void setThreadvotePK(ThreadvotePK threadvotePK) {
        this.threadvotePK = threadvotePK;
    }

    public Boolean getVotetype() {
        return votetype;
    }

    public void setVotetype(Boolean votetype) {
        this.votetype = votetype;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
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
        hash += (threadvotePK != null ? threadvotePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Threadvote)) {
            return false;
        }
        Threadvote other = (Threadvote) object;
        if ((this.threadvotePK == null && other.threadvotePK != null) || (this.threadvotePK != null && !this.threadvotePK.equals(other.threadvotePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Threadvote[ threadvotePK=" + threadvotePK + " ]";
    }
    
}
