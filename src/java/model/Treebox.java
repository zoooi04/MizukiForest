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
@Table(name = "TREEBOX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Treebox.findAll", query = "SELECT t FROM Treebox t"),
    @NamedQuery(name = "Treebox.findByTreeboxid", query = "SELECT t FROM Treebox t WHERE t.treeboxid = :treeboxid"),
    @NamedQuery(name = "Treebox.findByTreeboxname", query = "SELECT t FROM Treebox t WHERE t.treeboxname = :treeboxname"),
    @NamedQuery(name = "Treebox.findByTreeboxcost", query = "SELECT t FROM Treebox t WHERE t.treeboxcost = :treeboxcost"),
    @NamedQuery(name = "Treebox.findByIsarchived", query = "SELECT t FROM Treebox t WHERE t.isarchived = :isarchived"),
    @NamedQuery(name = "Treebox.findByIsdeleted", query = "SELECT t FROM Treebox t WHERE t.isdeleted = :isdeleted"),
    @NamedQuery(name = "Treebox.findByTreeboximage", query = "SELECT t FROM Treebox t WHERE t.treeboximage = :treeboximage")})
public class Treebox implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "TREEBOXID")
    private String treeboxid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TREEBOXNAME")
    private String treeboxname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TREEBOXCOST")
    private int treeboxcost;
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
    @Column(name = "TREEBOXIMAGE")
    private String treeboximage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treebox")
    private Collection<Raritydroprate> raritydroprateCollection;
    @OneToMany(mappedBy = "treeboxid")
    private Collection<Achievementreward> achievementrewardCollection;
    @OneToMany(mappedBy = "treeboxid")
    private Collection<Levelreward> levelrewardCollection;
    @OneToMany(mappedBy = "treeboxid")
    private Collection<Userinventoryitem> userinventoryitemCollection;

    public Treebox() {
    }

    public Treebox(String treeboxid) {
        this.treeboxid = treeboxid;
    }

    public Treebox(String treeboxid, String treeboxname, int treeboxcost, Boolean isarchived, Boolean isdeleted, String treeboximage) {
        this.treeboxid = treeboxid;
        this.treeboxname = treeboxname;
        this.treeboxcost = treeboxcost;
        this.isarchived = isarchived;
        this.isdeleted = isdeleted;
        this.treeboximage = treeboximage;
    }

    public String getTreeboxid() {
        return treeboxid;
    }

    public void setTreeboxid(String treeboxid) {
        this.treeboxid = treeboxid;
    }

    public String getTreeboxname() {
        return treeboxname;
    }

    public void setTreeboxname(String treeboxname) {
        this.treeboxname = treeboxname;
    }

    public int getTreeboxcost() {
        return treeboxcost;
    }

    public void setTreeboxcost(int treeboxcost) {
        this.treeboxcost = treeboxcost;
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

    public String getTreeboximage() {
        return treeboximage;
    }

    public void setTreeboximage(String treeboximage) {
        this.treeboximage = treeboximage;
    }

    @XmlTransient
    public Collection<Raritydroprate> getRaritydroprateCollection() {
        return raritydroprateCollection;
    }

    public void setRaritydroprateCollection(Collection<Raritydroprate> raritydroprateCollection) {
        this.raritydroprateCollection = raritydroprateCollection;
    }

    @XmlTransient
    public Collection<Achievementreward> getAchievementrewardCollection() {
        return achievementrewardCollection;
    }

    public void setAchievementrewardCollection(Collection<Achievementreward> achievementrewardCollection) {
        this.achievementrewardCollection = achievementrewardCollection;
    }

    @XmlTransient
    public Collection<Levelreward> getLevelrewardCollection() {
        return levelrewardCollection;
    }

    public void setLevelrewardCollection(Collection<Levelreward> levelrewardCollection) {
        this.levelrewardCollection = levelrewardCollection;
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
        hash += (treeboxid != null ? treeboxid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Treebox)) {
            return false;
        }
        Treebox other = (Treebox) object;
        if ((this.treeboxid == null && other.treeboxid != null) || (this.treeboxid != null && !this.treeboxid.equals(other.treeboxid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Treebox[ treeboxid=" + treeboxid + " ]";
    }
    
}
