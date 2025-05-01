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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JiaQuann
 */
@Entity
@Table(name = "USERLEVEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userlevel.findAll", query = "SELECT u FROM Userlevel u"),
    @NamedQuery(name = "Userlevel.findByLevelid", query = "SELECT u FROM Userlevel u WHERE u.levelid = :levelid"),
    @NamedQuery(name = "Userlevel.findByRequiredxp", query = "SELECT u FROM Userlevel u WHERE u.requiredxp = :requiredxp"),
    @NamedQuery(name = "Userlevel.findByIsdeleted", query = "SELECT u FROM Userlevel u WHERE u.isdeleted = :isdeleted")})
public class Userlevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEVELID")
    private Integer levelid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUIREDXP")
    private int requiredxp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelid")
    private List<Levelreward> levelrewardList;

    public Userlevel() {
    }

    public Userlevel(Integer levelid) {
        this.levelid = levelid;
    }

    public Userlevel(Integer levelid, int requiredxp, Boolean isdeleted) {
        this.levelid = levelid;
        this.requiredxp = requiredxp;
        this.isdeleted = isdeleted;
    }

    public Integer getLevelid() {
        return levelid;
    }

    public void setLevelid(Integer levelid) {
        this.levelid = levelid;
    }

    public int getRequiredxp() {
        return requiredxp;
    }

    public void setRequiredxp(int requiredxp) {
        this.requiredxp = requiredxp;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Levelreward> getLevelrewardList() {
        return levelrewardList;
    }

    public void setLevelrewardList(List<Levelreward> levelrewardList) {
        this.levelrewardList = levelrewardList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (levelid != null ? levelid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userlevel)) {
            return false;
        }
        Userlevel other = (Userlevel) object;
        if ((this.levelid == null && other.levelid != null) || (this.levelid != null && !this.levelid.equals(other.levelid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Userlevel[ levelid=" + levelid + " ]";
    }
    
}
