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
 * @author JiaQuann
 */
@Entity
@Table(name = "ACHIEVEMENTREWARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Achievementreward.findAll", query = "SELECT a FROM Achievementreward a"),
    @NamedQuery(name = "Achievementreward.findByAchievementrewardid", query = "SELECT a FROM Achievementreward a WHERE a.achievementrewardid = :achievementrewardid"),
    @NamedQuery(name = "Achievementreward.findByQuantity", query = "SELECT a FROM Achievementreward a WHERE a.quantity = :quantity"),
    @NamedQuery(name = "Achievementreward.findByIsdeleted", query = "SELECT a FROM Achievementreward a WHERE a.isdeleted = :isdeleted")})
public class Achievementreward implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ACHIEVEMENTREWARDID")
    private String achievementrewardid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "ACHIEVEMENTID", referencedColumnName = "ACHIEVEMENTID")
    @ManyToOne(optional = false)
    private Achievement achievementid;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    @ManyToOne
    private Item itemid;
    @JoinColumn(name = "TREEBOXID", referencedColumnName = "TREEBOXID")
    @ManyToOne
    private Treebox treeboxid;

    public Achievementreward() {
    }

    public Achievementreward(String achievementrewardid) {
        this.achievementrewardid = achievementrewardid;
    }

    public Achievementreward(String achievementrewardid, int quantity, Boolean isdeleted) {
        this.achievementrewardid = achievementrewardid;
        this.quantity = quantity;
        this.isdeleted = isdeleted;
    }

    public String getAchievementrewardid() {
        return achievementrewardid;
    }

    public void setAchievementrewardid(String achievementrewardid) {
        this.achievementrewardid = achievementrewardid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Achievement getAchievementid() {
        return achievementid;
    }

    public void setAchievementid(Achievement achievementid) {
        this.achievementid = achievementid;
    }

    public Item getItemid() {
        return itemid;
    }

    public void setItemid(Item itemid) {
        this.itemid = itemid;
    }

    public Treebox getTreeboxid() {
        return treeboxid;
    }

    public void setTreeboxid(Treebox treeboxid) {
        this.treeboxid = treeboxid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (achievementrewardid != null ? achievementrewardid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Achievementreward)) {
            return false;
        }
        Achievementreward other = (Achievementreward) object;
        if ((this.achievementrewardid == null && other.achievementrewardid != null) || (this.achievementrewardid != null && !this.achievementrewardid.equals(other.achievementrewardid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Achievementreward[ achievementrewardid=" + achievementrewardid + " ]";
    }
    
}
