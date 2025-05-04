/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
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
@Table(name = "REPORTCONTENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reportcontent.findAll", query = "SELECT r FROM Reportcontent r"),
    @NamedQuery(name = "Reportcontent.findByReportcontentid", query = "SELECT r FROM Reportcontent r WHERE r.reportcontentid = :reportcontentid"),
    @NamedQuery(name = "Reportcontent.findByReportreason", query = "SELECT r FROM Reportcontent r WHERE r.reportreason = :reportreason"),
    @NamedQuery(name = "Reportcontent.findByIssolved", query = "SELECT r FROM Reportcontent r WHERE r.issolved = :issolved")})
public class Reportcontent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "REPORTCONTENTID")
    private String reportcontentid;
    @Size(max = 255)
    @Column(name = "REPORTREASON")
    private String reportreason;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISSOLVED")
    private Boolean issolved;
    @JoinColumn(name = "THREADID", referencedColumnName = "THREADID")
    @ManyToOne
    private Thread threadid;
    @JoinColumn(name = "THREADCOMMENTID", referencedColumnName = "THREADCOMMENTID")
    @ManyToOne
    private Threadcomment threadcommentid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Reportcontent() {
    }

    public Reportcontent(String reportcontentid) {
        this.reportcontentid = reportcontentid;
    }

    public Reportcontent(String reportcontentid, Boolean issolved) {
        this.reportcontentid = reportcontentid;
        this.issolved = issolved;
    }

    public String getReportcontentid() {
        return reportcontentid;
    }

    public void setReportcontentid(String reportcontentid) {
        this.reportcontentid = reportcontentid;
    }

    public String getReportreason() {
        return reportreason;
    }

    public void setReportreason(String reportreason) {
        this.reportreason = reportreason;
    }

    public Boolean getIssolved() {
        return issolved;
    }

    public void setIssolved(Boolean issolved) {
        this.issolved = issolved;
    }

    public Thread getThreadid() {
        return threadid;
    }

    public void setThreadid(Thread threadid) {
        this.threadid = threadid;
    }

    public Threadcomment getThreadcommentid() {
        return threadcommentid;
    }

    public void setThreadcommentid(Threadcomment threadcommentid) {
        this.threadcommentid = threadcommentid;
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
        hash += (reportcontentid != null ? reportcontentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reportcontent)) {
            return false;
        }
        Reportcontent other = (Reportcontent) object;
        if ((this.reportcontentid == null && other.reportcontentid != null) || (this.reportcontentid != null && !this.reportcontentid.equals(other.reportcontentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Reportcontent[ reportcontentid=" + reportcontentid + " ]";
    }
    
}
