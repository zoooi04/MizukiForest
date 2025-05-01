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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Treebox.findByIsdeleted", query = "SELECT t FROM Treebox t WHERE t.isdeleted = :isdeleted")})
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
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Lob
    @Column(name = "TREEBOXIMAGE")
    private Serializable treeboximage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treebox")
    private List<Raritydroprate> raritydroprateList;
    @OneToMany(mappedBy = "treeboxid")
    private List<Achievementreward> achievementrewardList;
    @OneToMany(mappedBy = "treeboxid")
    private List<Levelreward> levelrewardList;
    @OneToMany(mappedBy = "treeboxid")
    private List<Userinventoryitem> userinventoryitemList;

    public Treebox() {
    }

    public Treebox(String treeboxid) {
        this.treeboxid = treeboxid;
    }

    public Treebox(String treeboxid, String treeboxname, int treeboxcost, Boolean isdeleted) {
        this.treeboxid = treeboxid;
        this.treeboxname = treeboxname;
        this.treeboxcost = treeboxcost;
        this.isdeleted = isdeleted;
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

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Serializable getTreeboximage() {
        return treeboximage;
    }

    public void setTreeboximage(Serializable treeboximage) {
        this.treeboximage = treeboximage;
    }

    @XmlTransient
    public List<Raritydroprate> getRaritydroprateList() {
        return raritydroprateList;
    }

    public void setRaritydroprateList(List<Raritydroprate> raritydroprateList) {
        this.raritydroprateList = raritydroprateList;
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
