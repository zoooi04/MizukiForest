/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johno
 */
@Entity
@Table(name = "RARITYDROPRATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Raritydroprate.findAll", query = "SELECT r FROM Raritydroprate r"),
    @NamedQuery(name = "Raritydroprate.findByTreeboxid", query = "SELECT r FROM Raritydroprate r WHERE r.raritydropratePK.treeboxid = :treeboxid"),
    @NamedQuery(name = "Raritydroprate.findByRarityid", query = "SELECT r FROM Raritydroprate r WHERE r.raritydropratePK.rarityid = :rarityid"),
    @NamedQuery(name = "Raritydroprate.findByPercentage", query = "SELECT r FROM Raritydroprate r WHERE r.percentage = :percentage"),
    @NamedQuery(name = "Raritydroprate.findByIsdeleted", query = "SELECT r FROM Raritydroprate r WHERE r.isdeleted = :isdeleted")})
public class Raritydroprate implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RaritydropratePK raritydropratePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERCENTAGE")
    private BigDecimal percentage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "TREEBOXID", referencedColumnName = "TREEBOXID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Treebox treebox;
    @JoinColumn(name = "RARITYID", referencedColumnName = "RARITYID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Treerarity treerarity;

    public Raritydroprate() {
    }

    public Raritydroprate(RaritydropratePK raritydropratePK) {
        this.raritydropratePK = raritydropratePK;
    }

    public Raritydroprate(RaritydropratePK raritydropratePK, BigDecimal percentage, Boolean isdeleted) {
        this.raritydropratePK = raritydropratePK;
        this.percentage = percentage;
        this.isdeleted = isdeleted;
    }

    public Raritydroprate(String treeboxid, String rarityid) {
        this.raritydropratePK = new RaritydropratePK(treeboxid, rarityid);
    }

    public RaritydropratePK getRaritydropratePK() {
        return raritydropratePK;
    }

    public void setRaritydropratePK(RaritydropratePK raritydropratePK) {
        this.raritydropratePK = raritydropratePK;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Treebox getTreebox() {
        return treebox;
    }

    public void setTreebox(Treebox treebox) {
        this.treebox = treebox;
    }

    public Treerarity getTreerarity() {
        return treerarity;
    }

    public void setTreerarity(Treerarity treerarity) {
        this.treerarity = treerarity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (raritydropratePK != null ? raritydropratePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Raritydroprate)) {
            return false;
        }
        Raritydroprate other = (Raritydroprate) object;
        if ((this.raritydropratePK == null && other.raritydropratePK != null) || (this.raritydropratePK != null && !this.raritydropratePK.equals(other.raritydropratePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Raritydroprate[ raritydropratePK=" + raritydropratePK + " ]";
    }
    
}
