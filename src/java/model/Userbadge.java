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
@Table(name = "USERBADGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userbadge.findAll", query = "SELECT u FROM Userbadge u"),
    @NamedQuery(name = "Userbadge.findByUserid", query = "SELECT u FROM Userbadge u WHERE u.userbadgePK.userid = :userid"),
    @NamedQuery(name = "Userbadge.findByBadgeid", query = "SELECT u FROM Userbadge u WHERE u.userbadgePK.badgeid = :badgeid"),
    @NamedQuery(name = "Userbadge.findByIsselected", query = "SELECT u FROM Userbadge u WHERE u.isselected = :isselected"),
    @NamedQuery(name = "Userbadge.findByIsdeleted", query = "SELECT u FROM Userbadge u WHERE u.isdeleted = :isdeleted")})
public class Userbadge implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserbadgePK userbadgePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISSELECTED")
    private Boolean isselected;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "BADGEID", referencedColumnName = "BADGEID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Badge badge;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Userbadge() {
    }

    public Userbadge(UserbadgePK userbadgePK) {
        this.userbadgePK = userbadgePK;
    }

    public Userbadge(UserbadgePK userbadgePK, Boolean isselected, Boolean isdeleted) {
        this.userbadgePK = userbadgePK;
        this.isselected = isselected;
        this.isdeleted = isdeleted;
    }

    public Userbadge(String userid, String badgeid) {
        this.userbadgePK = new UserbadgePK(userid, badgeid);
    }

    public UserbadgePK getUserbadgePK() {
        return userbadgePK;
    }

    public void setUserbadgePK(UserbadgePK userbadgePK) {
        this.userbadgePK = userbadgePK;
    }

    public Boolean getIsselected() {
        return isselected;
    }

    public void setIsselected(Boolean isselected) {
        this.isselected = isselected;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
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
        hash += (userbadgePK != null ? userbadgePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userbadge)) {
            return false;
        }
        Userbadge other = (Userbadge) object;
        if ((this.userbadgePK == null && other.userbadgePK != null) || (this.userbadgePK != null && !this.userbadgePK.equals(other.userbadgePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Userbadge[ userbadgePK=" + userbadgePK + " ]";
    }
    
}
