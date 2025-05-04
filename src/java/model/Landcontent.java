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
@Table(name = "LANDCONTENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Landcontent.findAll", query = "SELECT l FROM Landcontent l"),
    @NamedQuery(name = "Landcontent.findByLandcontentid", query = "SELECT l FROM Landcontent l WHERE l.landcontentid = :landcontentid"),
    @NamedQuery(name = "Landcontent.findByXcoord", query = "SELECT l FROM Landcontent l WHERE l.xcoord = :xcoord"),
    @NamedQuery(name = "Landcontent.findByYcoord", query = "SELECT l FROM Landcontent l WHERE l.ycoord = :ycoord"),
    @NamedQuery(name = "Landcontent.findByIsdeleted", query = "SELECT l FROM Landcontent l WHERE l.isdeleted = :isdeleted")})
public class Landcontent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "LANDCONTENTID")
    private String landcontentid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "XCOORD")
    private int xcoord;
    @Basic(optional = false)
    @NotNull
    @Column(name = "YCOORD")
    private int ycoord;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    @ManyToOne
    private Item itemid;
    @JoinColumn(name = "LANDID", referencedColumnName = "LANDID")
    @ManyToOne(optional = false)
    private Land landid;
    @JoinColumn(name = "TREEID", referencedColumnName = "TREEID")
    @ManyToOne
    private Tree treeid;

    public Landcontent() {
    }

    public Landcontent(String landcontentid) {
        this.landcontentid = landcontentid;
    }

    public Landcontent(String landcontentid, int xcoord, int ycoord, Boolean isdeleted) {
        this.landcontentid = landcontentid;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.isdeleted = isdeleted;
    }

    public String getLandcontentid() {
        return landcontentid;
    }

    public void setLandcontentid(String landcontentid) {
        this.landcontentid = landcontentid;
    }

    public int getXcoord() {
        return xcoord;
    }

    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
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

    public Land getLandid() {
        return landid;
    }

    public void setLandid(Land landid) {
        this.landid = landid;
    }

    public Tree getTreeid() {
        return treeid;
    }

    public void setTreeid(Tree treeid) {
        this.treeid = treeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (landcontentid != null ? landcontentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Landcontent)) {
            return false;
        }
        Landcontent other = (Landcontent) object;
        if ((this.landcontentid == null && other.landcontentid != null) || (this.landcontentid != null && !this.landcontentid.equals(other.landcontentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Landcontent[ landcontentid=" + landcontentid + " ]";
    }
    
}
