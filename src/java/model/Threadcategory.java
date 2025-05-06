/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "THREADCATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Threadcategory.findAll", query = "SELECT t FROM Threadcategory t"),
    @NamedQuery(name = "Threadcategory.findByThreadcategoryid", query = "SELECT t FROM Threadcategory t WHERE t.threadcategoryid = :threadcategoryid"),
    @NamedQuery(name = "Threadcategory.findByThreadcategoryname", query = "SELECT t FROM Threadcategory t WHERE t.threadcategoryname = :threadcategoryname"),
    @NamedQuery(name = "Threadcategory.findByIsdeleted", query = "SELECT t FROM Threadcategory t WHERE t.isdeleted = :isdeleted")})
public class Threadcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "THREADCATEGORYID")
    private String threadcategoryid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "THREADCATEGORYNAME")
    private String threadcategoryname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "threadcategoryid")
    private Collection<Thread> threadCollection;

    public Threadcategory() {
    }

    public Threadcategory(String threadcategoryid) {
        this.threadcategoryid = threadcategoryid;
    }

    public Threadcategory(String threadcategoryid, String threadcategoryname, Boolean isdeleted) {
        this.threadcategoryid = threadcategoryid;
        this.threadcategoryname = threadcategoryname;
        this.isdeleted = isdeleted;
    }

    public String getThreadcategoryid() {
        return threadcategoryid;
    }

    public void setThreadcategoryid(String threadcategoryid) {
        this.threadcategoryid = threadcategoryid;
    }

    public String getThreadcategoryname() {
        return threadcategoryname;
    }

    public void setThreadcategoryname(String threadcategoryname) {
        this.threadcategoryname = threadcategoryname;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public Collection<Thread> getThreadCollection() {
        return threadCollection;
    }

    public void setThreadCollection(Collection<Thread> threadCollection) {
        this.threadCollection = threadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadcategoryid != null ? threadcategoryid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Threadcategory)) {
            return false;
        }
        Threadcategory other = (Threadcategory) object;
        if ((this.threadcategoryid == null && other.threadcategoryid != null) || (this.threadcategoryid != null && !this.threadcategoryid.equals(other.threadcategoryid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Threadcategory[ threadcategoryid=" + threadcategoryid + " ]";
    }
    
}
