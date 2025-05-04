/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johno
 */
@Entity
@Table(name = "FOCUSSESSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Focussession.findAll", query = "SELECT f FROM Focussession f"),
    @NamedQuery(name = "Focussession.findBySessionid", query = "SELECT f FROM Focussession f WHERE f.sessionid = :sessionid"),
    @NamedQuery(name = "Focussession.findBySessiontype", query = "SELECT f FROM Focussession f WHERE f.sessiontype = :sessiontype"),
    @NamedQuery(name = "Focussession.findByDuration", query = "SELECT f FROM Focussession f WHERE f.duration = :duration"),
    @NamedQuery(name = "Focussession.findByPomodorominorbreak", query = "SELECT f FROM Focussession f WHERE f.pomodorominorbreak = :pomodorominorbreak"),
    @NamedQuery(name = "Focussession.findByPomodoromajorbreak", query = "SELECT f FROM Focussession f WHERE f.pomodoromajorbreak = :pomodoromajorbreak"),
    @NamedQuery(name = "Focussession.findBySessionstatus", query = "SELECT f FROM Focussession f WHERE f.sessionstatus = :sessionstatus"),
    @NamedQuery(name = "Focussession.findByTreeboxesobtained", query = "SELECT f FROM Focussession f WHERE f.treeboxesobtained = :treeboxesobtained"),
    @NamedQuery(name = "Focussession.findByIsdeleted", query = "SELECT f FROM Focussession f WHERE f.isdeleted = :isdeleted")})
public class Focussession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "SESSIONID")
    private String sessionid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SESSIONTYPE")
    private String sessiontype;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "DURATION")
    private BigDecimal duration;
    @Column(name = "POMODOROMINORBREAK")
    private Integer pomodorominorbreak;
    @Column(name = "POMODOROMAJORBREAK")
    private Integer pomodoromajorbreak;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SESSIONSTATUS")
    private String sessionstatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TREEBOXESOBTAINED")
    private int treeboxesobtained;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Focussession() {
    }

    public Focussession(String sessionid) {
        this.sessionid = sessionid;
    }

    public Focussession(String sessionid, String sessiontype, BigDecimal duration, String sessionstatus, int treeboxesobtained, Boolean isdeleted) {
        this.sessionid = sessionid;
        this.sessiontype = sessiontype;
        this.duration = duration;
        this.sessionstatus = sessionstatus;
        this.treeboxesobtained = treeboxesobtained;
        this.isdeleted = isdeleted;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getSessiontype() {
        return sessiontype;
    }

    public void setSessiontype(String sessiontype) {
        this.sessiontype = sessiontype;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public Integer getPomodorominorbreak() {
        return pomodorominorbreak;
    }

    public void setPomodorominorbreak(Integer pomodorominorbreak) {
        this.pomodorominorbreak = pomodorominorbreak;
    }

    public Integer getPomodoromajorbreak() {
        return pomodoromajorbreak;
    }

    public void setPomodoromajorbreak(Integer pomodoromajorbreak) {
        this.pomodoromajorbreak = pomodoromajorbreak;
    }

    public String getSessionstatus() {
        return sessionstatus;
    }

    public void setSessionstatus(String sessionstatus) {
        this.sessionstatus = sessionstatus;
    }

    public int getTreeboxesobtained() {
        return treeboxesobtained;
    }

    public void setTreeboxesobtained(int treeboxesobtained) {
        this.treeboxesobtained = treeboxesobtained;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionid != null ? sessionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Focussession)) {
            return false;
        }
        Focussession other = (Focussession) object;
        if ((this.sessionid == null && other.sessionid != null) || (this.sessionid != null && !this.sessionid.equals(other.sessionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Focussession[ sessionid=" + sessionid + " ]";
    }
    
}
