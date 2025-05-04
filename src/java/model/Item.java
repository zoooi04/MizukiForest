/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author johno
 */
@Entity
@Table(name = "ITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findByItemid", query = "SELECT i FROM Item i WHERE i.itemid = :itemid"),
    @NamedQuery(name = "Item.findByItemname", query = "SELECT i FROM Item i WHERE i.itemname = :itemname"),
    @NamedQuery(name = "Item.findByItemtype", query = "SELECT i FROM Item i WHERE i.itemtype = :itemtype"),
    @NamedQuery(name = "Item.findByItemstatus", query = "SELECT i FROM Item i WHERE i.itemstatus = :itemstatus"),
    @NamedQuery(name = "Item.findByItemcost", query = "SELECT i FROM Item i WHERE i.itemcost = :itemcost"),
    @NamedQuery(name = "Item.findByIsarchived", query = "SELECT i FROM Item i WHERE i.isarchived = :isarchived"),
    @NamedQuery(name = "Item.findByIsdeleted", query = "SELECT i FROM Item i WHERE i.isdeleted = :isdeleted"),
    @NamedQuery(name = "Item.findByItemimage", query = "SELECT i FROM Item i WHERE i.itemimage = :itemimage")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ITEMID")
    private String itemid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ITEMNAME")
    private String itemname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ITEMTYPE")
    private String itemtype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ITEMSTATUS")
    private Boolean itemstatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ITEMCOST")
    private int itemcost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISARCHIVED")
    private Boolean isarchived;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ITEMIMAGE")
    private String itemimage;
    @OneToMany(mappedBy = "itemid")
    private List<Achievementreward> achievementrewardList;
    @OneToMany(mappedBy = "itemid")
    private List<Levelreward> levelrewardList;
    @OneToMany(mappedBy = "itemid")
    private List<Userinventoryitem> userinventoryitemList;
    @OneToMany(mappedBy = "itemid")
    private List<Landcontent> landcontentList;

    public Item() {
    }

    public Item(String itemid) {
        this.itemid = itemid;
    }

    public Item(String itemid, String itemname, String itemtype, Boolean itemstatus, int itemcost, Boolean isarchived, Boolean isdeleted, String itemimage) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemtype = itemtype;
        this.itemstatus = itemstatus;
        this.itemcost = itemcost;
        this.isarchived = isarchived;
        this.isdeleted = isdeleted;
        this.itemimage = itemimage;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public Boolean getItemstatus() {
        return itemstatus;
    }

    public void setItemstatus(Boolean itemstatus) {
        this.itemstatus = itemstatus;
    }

    public int getItemcost() {
        return itemcost;
    }

    public void setItemcost(int itemcost) {
        this.itemcost = itemcost;
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

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    @XmlTransient
    public List<Achievementreward> getAchievementrewardList() {
        return achievementrewardList;
    }

    public void setAchievementrewardList(List<Achievementreward> achievementrewardList) {
        this.achievementrewardList = achievementrewardList;
    }

    @XmlTransient
    public List<Levelreward> getLevelrewardList() {
        return levelrewardList;
    }

    public void setLevelrewardList(List<Levelreward> levelrewardList) {
        this.levelrewardList = levelrewardList;
    }

    @XmlTransient
    public List<Userinventoryitem> getUserinventoryitemList() {
        return userinventoryitemList;
    }

    public void setUserinventoryitemList(List<Userinventoryitem> userinventoryitemList) {
        this.userinventoryitemList = userinventoryitemList;
    }

    @XmlTransient
    public List<Landcontent> getLandcontentList() {
        return landcontentList;
    }

    public void setLandcontentList(List<Landcontent> landcontentList) {
        this.landcontentList = landcontentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemid != null ? itemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.itemid == null && other.itemid != null) || (this.itemid != null && !this.itemid.equals(other.itemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Item[ itemid=" + itemid + " ]";
    }
    
}
