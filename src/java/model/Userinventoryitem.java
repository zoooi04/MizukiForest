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
@Table(name = "USERINVENTORYITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userinventoryitem.findAll", query = "SELECT u FROM Userinventoryitem u"),
    @NamedQuery(name = "Userinventoryitem.findByInventoryitemid", query = "SELECT u FROM Userinventoryitem u WHERE u.inventoryitemid = :inventoryitemid"),
    @NamedQuery(name = "Userinventoryitem.findByQuantity", query = "SELECT u FROM Userinventoryitem u WHERE u.quantity = :quantity"),
    @NamedQuery(name = "Userinventoryitem.findByIsdeleted", query = "SELECT u FROM Userinventoryitem u WHERE u.isdeleted = :isdeleted")})
public class Userinventoryitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "INVENTORYITEMID")
    private String inventoryitemid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "BIOMEID", referencedColumnName = "BIOMEID")
    @ManyToOne
    private Biome biomeid;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    @ManyToOne
    private Item itemid;
    @JoinColumn(name = "TREEID", referencedColumnName = "TREEID")
    @ManyToOne
    private Tree treeid;
    @JoinColumn(name = "TREEBOXID", referencedColumnName = "TREEBOXID")
    @ManyToOne
    private Treebox treeboxid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Userinventoryitem() {
    }

    public Userinventoryitem(String inventoryitemid) {
        this.inventoryitemid = inventoryitemid;
    }

    public Userinventoryitem(String inventoryitemid, int quantity, Boolean isdeleted) {
        this.inventoryitemid = inventoryitemid;
        this.quantity = quantity;
        this.isdeleted = isdeleted;
    }

    public String getInventoryitemid() {
        return inventoryitemid;
    }

    public void setInventoryitemid(String inventoryitemid) {
        this.inventoryitemid = inventoryitemid;
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

    public Biome getBiomeid() {
        return biomeid;
    }

    public void setBiomeid(Biome biomeid) {
        this.biomeid = biomeid;
    }

    public Item getItemid() {
        return itemid;
    }

    public void setItemid(Item itemid) {
        this.itemid = itemid;
    }

    public Tree getTreeid() {
        return treeid;
    }

    public void setTreeid(Tree treeid) {
        this.treeid = treeid;
    }

    public Treebox getTreeboxid() {
        return treeboxid;
    }

    public void setTreeboxid(Treebox treeboxid) {
        this.treeboxid = treeboxid;
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
        hash += (inventoryitemid != null ? inventoryitemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userinventoryitem)) {
            return false;
        }
        Userinventoryitem other = (Userinventoryitem) object;
        if ((this.inventoryitemid == null && other.inventoryitemid != null) || (this.inventoryitemid != null && !this.inventoryitemid.equals(other.inventoryitemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Userinventoryitem[ inventoryitemid=" + inventoryitemid + " ]";
    }
    
}
