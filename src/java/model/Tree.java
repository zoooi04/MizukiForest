/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TREE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tree.findAll", query = "SELECT t FROM Tree t"),
    @NamedQuery(name = "Tree.findByTreeid", query = "SELECT t FROM Tree t WHERE t.treeid = :treeid"),
    @NamedQuery(name = "Tree.findByTreename", query = "SELECT t FROM Tree t WHERE t.treename = :treename"),
    @NamedQuery(name = "Tree.findByTreedescription", query = "SELECT t FROM Tree t WHERE t.treedescription = :treedescription"),
    @NamedQuery(name = "Tree.findByIsarchived", query = "SELECT t FROM Tree t WHERE t.isarchived = :isarchived"),
    @NamedQuery(name = "Tree.findByTreestatus", query = "SELECT t FROM Tree t WHERE t.treestatus = :treestatus"),
    @NamedQuery(name = "Tree.findByIsdeleted", query = "SELECT t FROM Tree t WHERE t.isdeleted = :isdeleted"),
    @NamedQuery(name = "Tree.findByTreeimage", query = "SELECT t FROM Tree t WHERE t.treeimage = :treeimage")})
public class Tree implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "TREEID")
    private String treeid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TREENAME")
    private String treename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "TREEDESCRIPTION")
    private String treedescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISARCHIVED")
    private Boolean isarchived;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TREESTATUS")
    private Boolean treestatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "TREEIMAGE")
    private String treeimage;
    @OneToMany(mappedBy = "treeid")
    private Collection<Userinventoryitem> userinventoryitemCollection;
    @OneToMany(mappedBy = "treeid")
    private Collection<Landcontent> landcontentCollection;
    @JoinColumn(name = "RARITYID", referencedColumnName = "RARITYID")
    @ManyToOne(optional = false)
    private Treerarity rarityid;

    public Tree() {
    }

    public Tree(String treeid) {
        this.treeid = treeid;
    }

    public Tree(String treeid, String treename, String treedescription, Boolean isarchived, Boolean treestatus, Boolean isdeleted, String treeimage) {
        this.treeid = treeid;
        this.treename = treename;
        this.treedescription = treedescription;
        this.isarchived = isarchived;
        this.treestatus = treestatus;
        this.isdeleted = isdeleted;
        this.treeimage = treeimage;
    }

    public String getTreeid() {
        return treeid;
    }

    public void setTreeid(String treeid) {
        this.treeid = treeid;
    }

    public String getTreename() {
        return treename;
    }

    public void setTreename(String treename) {
        this.treename = treename;
    }

    public String getTreedescription() {
        return treedescription;
    }

    public void setTreedescription(String treedescription) {
        this.treedescription = treedescription;
    }

    public Boolean getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(Boolean isarchived) {
        this.isarchived = isarchived;
    }

    public Boolean getTreestatus() {
        return treestatus;
    }

    public void setTreestatus(Boolean treestatus) {
        this.treestatus = treestatus;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getTreeimage() {
        return treeimage;
    }

    public void setTreeimage(String treeimage) {
        this.treeimage = treeimage;
    }

    @XmlTransient
    public Collection<Userinventoryitem> getUserinventoryitemCollection() {
        return userinventoryitemCollection;
    }

    public void setUserinventoryitemCollection(Collection<Userinventoryitem> userinventoryitemCollection) {
        this.userinventoryitemCollection = userinventoryitemCollection;
    }

    @XmlTransient
    public Collection<Landcontent> getLandcontentCollection() {
        return landcontentCollection;
    }

    public void setLandcontentCollection(Collection<Landcontent> landcontentCollection) {
        this.landcontentCollection = landcontentCollection;
    }

    public Treerarity getRarityid() {
        return rarityid;
    }

    public void setRarityid(Treerarity rarityid) {
        this.rarityid = rarityid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (treeid != null ? treeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tree)) {
            return false;
        }
        Tree other = (Tree) object;
        if ((this.treeid == null && other.treeid != null) || (this.treeid != null && !this.treeid.equals(other.treeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tree[ treeid=" + treeid + " ]";
    }
    
}
