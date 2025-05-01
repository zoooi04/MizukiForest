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
@Table(name = "LAND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Land.findAll", query = "SELECT l FROM Land l"),
    @NamedQuery(name = "Land.findByLandid", query = "SELECT l FROM Land l WHERE l.landid = :landid"),
    @NamedQuery(name = "Land.findByLandname", query = "SELECT l FROM Land l WHERE l.landname = :landname"),
    @NamedQuery(name = "Land.findByIsmainland", query = "SELECT l FROM Land l WHERE l.ismainland = :ismainland"),
    @NamedQuery(name = "Land.findByIsdeleted", query = "SELECT l FROM Land l WHERE l.isdeleted = :isdeleted")})
public class Land implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "LANDID")
    private String landid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LANDNAME")
    private String landname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISMAINLAND")
    private Boolean ismainland;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "BIOMEID", referencedColumnName = "BIOMEID")
    @ManyToOne(optional = false)
    private Biome biomeid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "landid")
    private List<Landcontent> landcontentList;

    public Land() {
    }

    public Land(String landid) {
        this.landid = landid;
    }

    public Land(String landid, String landname, Boolean ismainland, Boolean isdeleted) {
        this.landid = landid;
        this.landname = landname;
        this.ismainland = ismainland;
        this.isdeleted = isdeleted;
    }

    public String getLandid() {
        return landid;
    }

    public void setLandid(String landid) {
        this.landid = landid;
    }

    public String getLandname() {
        return landname;
    }

    public void setLandname(String landname) {
        this.landname = landname;
    }

    public Boolean getIsmainland() {
        return ismainland;
    }

    public void setIsmainland(Boolean ismainland) {
        this.ismainland = ismainland;
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

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
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
        hash += (landid != null ? landid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Land)) {
            return false;
        }
        Land other = (Land) object;
        if ((this.landid == null && other.landid != null) || (this.landid != null && !this.landid.equals(other.landid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Land[ landid=" + landid + " ]";
    }
    
}
