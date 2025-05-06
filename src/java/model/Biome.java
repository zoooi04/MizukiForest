/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "BIOME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Biome.findAll", query = "SELECT b FROM Biome b"),
    @NamedQuery(name = "Biome.findByBiomeid", query = "SELECT b FROM Biome b WHERE b.biomeid = :biomeid"),
    @NamedQuery(name = "Biome.findByBiomename", query = "SELECT b FROM Biome b WHERE b.biomename = :biomename"),
    @NamedQuery(name = "Biome.findByBiomedescription", query = "SELECT b FROM Biome b WHERE b.biomedescription = :biomedescription"),
    @NamedQuery(name = "Biome.findByBiomecost", query = "SELECT b FROM Biome b WHERE b.biomecost = :biomecost"),
    @NamedQuery(name = "Biome.findByIsdeleted", query = "SELECT b FROM Biome b WHERE b.isdeleted = :isdeleted"),
    @NamedQuery(name = "Biome.findByIsarchived", query = "SELECT b FROM Biome b WHERE b.isarchived = :isarchived"),
    @NamedQuery(name = "Biome.findByBiomeimage", query = "SELECT b FROM Biome b WHERE b.biomeimage = :biomeimage")})
public class Biome implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "BIOMEID")
    private String biomeid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BIOMENAME")
    private String biomename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "BIOMEDESCRIPTION")
    private String biomedescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BIOMECOST")
    private int biomecost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISARCHIVED")
    private Boolean isarchived;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "BIOMEIMAGE")
    private String biomeimage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "biomeid")
    private Collection<Land> landCollection;
    @OneToMany(mappedBy = "biomeid")
    private Collection<Userinventoryitem> userinventoryitemCollection;

    public Biome() {
    }

    public Biome(String biomeid) {
        this.biomeid = biomeid;
    }

    public Biome(String biomeid, String biomename, String biomedescription, int biomecost, Boolean isdeleted, Boolean isarchived, String biomeimage) {
        this.biomeid = biomeid;
        this.biomename = biomename;
        this.biomedescription = biomedescription;
        this.biomecost = biomecost;
        this.isdeleted = isdeleted;
        this.isarchived = isarchived;
        this.biomeimage = biomeimage;
    }

    public String getBiomeid() {
        return biomeid;
    }

    public void setBiomeid(String biomeid) {
        this.biomeid = biomeid;
    }

    public String getBiomename() {
        return biomename;
    }

    public void setBiomename(String biomename) {
        this.biomename = biomename;
    }

    public String getBiomedescription() {
        return biomedescription;
    }

    public void setBiomedescription(String biomedescription) {
        this.biomedescription = biomedescription;
    }

    public int getBiomecost() {
        return biomecost;
    }

    public void setBiomecost(int biomecost) {
        this.biomecost = biomecost;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Boolean getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(Boolean isarchived) {
        this.isarchived = isarchived;
    }

    public String getBiomeimage() {
        return biomeimage;
    }

    public void setBiomeimage(String biomeimage) {
        this.biomeimage = biomeimage;
    }

    @XmlTransient
    public Collection<Land> getLandCollection() {
        return landCollection;
    }

    public void setLandCollection(Collection<Land> landCollection) {
        this.landCollection = landCollection;
    }

    @XmlTransient
    public Collection<Userinventoryitem> getUserinventoryitemCollection() {
        return userinventoryitemCollection;
    }

    public void setUserinventoryitemCollection(Collection<Userinventoryitem> userinventoryitemCollection) {
        this.userinventoryitemCollection = userinventoryitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (biomeid != null ? biomeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Biome)) {
            return false;
        }
        Biome other = (Biome) object;
        if ((this.biomeid == null && other.biomeid != null) || (this.biomeid != null && !this.biomeid.equals(other.biomeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Biome[ biomeid=" + biomeid + " ]";
    }
    
}
