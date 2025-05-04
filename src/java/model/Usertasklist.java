/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johno
 */
@Entity
@Table(name = "USERTASKLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usertasklist.findAll", query = "SELECT u FROM Usertasklist u"),
    @NamedQuery(name = "Usertasklist.findByUsertasklistid", query = "SELECT u FROM Usertasklist u WHERE u.usertasklistid = :usertasklistid"),
    @NamedQuery(name = "Usertasklist.findByCustomisedtaskname", query = "SELECT u FROM Usertasklist u WHERE u.customisedtaskname = :customisedtaskname"),
    @NamedQuery(name = "Usertasklist.findByCustomisedtaskdescription", query = "SELECT u FROM Usertasklist u WHERE u.customisedtaskdescription = :customisedtaskdescription"),
    @NamedQuery(name = "Usertasklist.findByDatetimeaccepted", query = "SELECT u FROM Usertasklist u WHERE u.datetimeaccepted = :datetimeaccepted"),
    @NamedQuery(name = "Usertasklist.findByDatecompleted", query = "SELECT u FROM Usertasklist u WHERE u.datecompleted = :datecompleted"),
    @NamedQuery(name = "Usertasklist.findByIsdeleted", query = "SELECT u FROM Usertasklist u WHERE u.isdeleted = :isdeleted")})
public class Usertasklist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERTASKLISTID")
    private String usertasklistid;
    @Size(max = 100)
    @Column(name = "CUSTOMISEDTASKNAME")
    private String customisedtaskname;
    @Size(max = 300)
    @Column(name = "CUSTOMISEDTASKDESCRIPTION")
    private String customisedtaskdescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATETIMEACCEPTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeaccepted;
    @Column(name = "DATECOMPLETED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecompleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "TASKID", referencedColumnName = "TASKID")
    @ManyToOne(optional = false)
    private Task taskid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Usertasklist() {
    }

    public Usertasklist(String usertasklistid) {
        this.usertasklistid = usertasklistid;
    }

    public Usertasklist(String usertasklistid, Date datetimeaccepted, Boolean isdeleted) {
        this.usertasklistid = usertasklistid;
        this.datetimeaccepted = datetimeaccepted;
        this.isdeleted = isdeleted;
    }

    public String getUsertasklistid() {
        return usertasklistid;
    }

    public void setUsertasklistid(String usertasklistid) {
        this.usertasklistid = usertasklistid;
    }

    public String getCustomisedtaskname() {
        return customisedtaskname;
    }

    public void setCustomisedtaskname(String customisedtaskname) {
        this.customisedtaskname = customisedtaskname;
    }

    public String getCustomisedtaskdescription() {
        return customisedtaskdescription;
    }

    public void setCustomisedtaskdescription(String customisedtaskdescription) {
        this.customisedtaskdescription = customisedtaskdescription;
    }

    public Date getDatetimeaccepted() {
        return datetimeaccepted;
    }

    public void setDatetimeaccepted(Date datetimeaccepted) {
        this.datetimeaccepted = datetimeaccepted;
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

    public Task getTaskid() {
        return taskid;
    }

    public void setTaskid(Task taskid) {
        this.taskid = taskid;
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
        hash += (usertasklistid != null ? usertasklistid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usertasklist)) {
            return false;
        }
        Usertasklist other = (Usertasklist) object;
        if ((this.usertasklistid == null && other.usertasklistid != null) || (this.usertasklistid != null && !this.usertasklistid.equals(other.usertasklistid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Usertasklist[ usertasklistid=" + usertasklistid + " ]";
    }
    
}
