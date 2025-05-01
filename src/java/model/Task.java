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
@Table(name = "TASK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByTaskid", query = "SELECT t FROM Task t WHERE t.taskid = :taskid"),
    @NamedQuery(name = "Task.findByTaskname", query = "SELECT t FROM Task t WHERE t.taskname = :taskname"),
    @NamedQuery(name = "Task.findByTaskdescription", query = "SELECT t FROM Task t WHERE t.taskdescription = :taskdescription"),
    @NamedQuery(name = "Task.findByIscustomisable", query = "SELECT t FROM Task t WHERE t.iscustomisable = :iscustomisable"),
    @NamedQuery(name = "Task.findByIsarchived", query = "SELECT t FROM Task t WHERE t.isarchived = :isarchived"),
    @NamedQuery(name = "Task.findByIsdeleted", query = "SELECT t FROM Task t WHERE t.isdeleted = :isdeleted")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "TASKID")
    private String taskid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TASKNAME")
    private String taskname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "TASKDESCRIPTION")
    private String taskdescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCUSTOMISABLE")
    private Boolean iscustomisable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISARCHIVED")
    private Boolean isarchived;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskid")
    private List<Usertasklist> usertasklistList;

    public Task() {
    }

    public Task(String taskid) {
        this.taskid = taskid;
    }

    public Task(String taskid, String taskname, String taskdescription, Boolean iscustomisable, Boolean isarchived, Boolean isdeleted) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.taskdescription = taskdescription;
        this.iscustomisable = iscustomisable;
        this.isarchived = isarchived;
        this.isdeleted = isdeleted;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTaskdescription() {
        return taskdescription;
    }

    public void setTaskdescription(String taskdescription) {
        this.taskdescription = taskdescription;
    }

    public Boolean getIscustomisable() {
        return iscustomisable;
    }

    public void setIscustomisable(Boolean iscustomisable) {
        this.iscustomisable = iscustomisable;
    }

    public Boolean getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(Boolean isarchived) {
        this.isarchived = isarchived;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Usertasklist> getUsertasklistList() {
        return usertasklistList;
    }

    public void setUsertasklistList(List<Usertasklist> usertasklistList) {
        this.usertasklistList = usertasklistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskid != null ? taskid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskid == null && other.taskid != null) || (this.taskid != null && !this.taskid.equals(other.taskid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Task[ taskid=" + taskid + " ]";
    }
    
}
