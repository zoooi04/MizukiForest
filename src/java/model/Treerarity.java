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
@Table(name = "TREERARITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Treerarity.findAll", query = "SELECT t FROM Treerarity t"),
    @NamedQuery(name = "Treerarity.findByRarityid", query = "SELECT t FROM Treerarity t WHERE t.rarityid = :rarityid"),
    @NamedQuery(name = "Treerarity.findByRarityname", query = "SELECT t FROM Treerarity t WHERE t.rarityname = :rarityname"),
    @NamedQuery(name = "Treerarity.findByRaritycolour", query = "SELECT t FROM Treerarity t WHERE t.raritycolour = :raritycolour"),
    @NamedQuery(name = "Treerarity.findByIsarchived", query = "SELECT t FROM Treerarity t WHERE t.isarchived = :isarchived"),
    @NamedQuery(name = "Treerarity.findByIsdeleted", query = "SELECT t FROM Treerarity t WHERE t.isdeleted = :isdeleted")})
public class Treerarity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "RARITYID")
    private String rarityid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RARITYNAME")
    private String rarityname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "RARITYCOLOUR")
    private String raritycolour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISARCHIVED")
    private Boolean isarchived;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treerarity")
    private List<Raritydroprate> raritydroprateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rarityid")
    private List<Tree> treeList;

    public Treerarity() {
    }

    public Treerarity(String rarityid) {
        this.rarityid = rarityid;
    }

    public Treerarity(String rarityid, String rarityname, String raritycolour, Boolean isarchived, Boolean isdeleted) {
        this.rarityid = rarityid;
        this.rarityname = rarityname;
        this.raritycolour = raritycolour;
        this.isarchived = isarchived;
        this.isdeleted = isdeleted;
    }

    public String getRarityid() {
        return rarityid;
    }

    public void setRarityid(String rarityid) {
        this.rarityid = rarityid;
    }

    public String getRarityname() {
        return rarityname;
    }

    public void setRarityname(String rarityname) {
        this.rarityname = rarityname;
    }

    public String getRaritycolour() {
        return raritycolour;
    }

    public void setRaritycolour(String raritycolour) {
        this.raritycolour = raritycolour;
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
    public List<Raritydroprate> getRaritydroprateList() {
        return raritydroprateList;
    }

    public void setRaritydroprateList(List<Raritydroprate> raritydroprateList) {
        this.raritydroprateList = raritydroprateList;
    }

    @XmlTransient
    public List<Tree> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<Tree> treeList) {
        this.treeList = treeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rarityid != null ? rarityid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Treerarity)) {
            return false;
        }
        Treerarity other = (Treerarity) object;
        if ((this.rarityid == null && other.rarityid != null) || (this.rarityid != null && !this.rarityid.equals(other.rarityid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Treerarity[ rarityid=" + rarityid + " ]";
    }
    
}
