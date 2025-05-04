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
@Table(name = "LEVELREWARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Levelreward.findAll", query = "SELECT l FROM Levelreward l"),
    @NamedQuery(name = "Levelreward.findByLevelrewardid", query = "SELECT l FROM Levelreward l WHERE l.levelrewardid = :levelrewardid"),
    @NamedQuery(name = "Levelreward.findByQuantity", query = "SELECT l FROM Levelreward l WHERE l.quantity = :quantity"),
    @NamedQuery(name = "Levelreward.findByIsdeleted", query = "SELECT l FROM Levelreward l WHERE l.isdeleted = :isdeleted")})
public class Levelreward implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "LEVELREWARDID")
    private String levelrewardid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    @ManyToOne
    private Item itemid;
    @JoinColumn(name = "TREEBOXID", referencedColumnName = "TREEBOXID")
    @ManyToOne
    private Treebox treeboxid;
    @JoinColumn(name = "LEVELID", referencedColumnName = "LEVELID")
    @ManyToOne(optional = false)
    private Userlevel levelid;

    public Levelreward() {
    }

    public Levelreward(String levelrewardid) {
        this.levelrewardid = levelrewardid;
    }

    public Levelreward(String levelrewardid, int quantity, Boolean isdeleted) {
        this.levelrewardid = levelrewardid;
        this.quantity = quantity;
        this.isdeleted = isdeleted;
    }

    public String getLevelrewardid() {
        return levelrewardid;
    }

    public void setLevelrewardid(String levelrewardid) {
        this.levelrewardid = levelrewardid;
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

    public Userlevel getLevelid() {
        return levelid;
    }

    public void setLevelid(Userlevel levelid) {
        this.levelid = levelid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (levelrewardid != null ? levelrewardid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Levelreward)) {
            return false;
        }
        Levelreward other = (Levelreward) object;
        if ((this.levelrewardid == null && other.levelrewardid != null) || (this.levelrewardid != null && !this.levelrewardid.equals(other.levelrewardid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Levelreward[ levelrewardid=" + levelrewardid + " ]";
    }
    
}
