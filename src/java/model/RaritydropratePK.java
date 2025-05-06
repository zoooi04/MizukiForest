/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author JiaQuann
 */
@Embeddable
public class RaritydropratePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "TREEBOXID")
    private String treeboxid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "RARITYID")
    private String rarityid;

    public RaritydropratePK() {
    }

    public RaritydropratePK(String treeboxid, String rarityid) {
        this.treeboxid = treeboxid;
        this.rarityid = rarityid;
    }

    public String getTreeboxid() {
        return treeboxid;
    }

    public void setTreeboxid(String treeboxid) {
        this.treeboxid = treeboxid;
    }

    public String getRarityid() {
        return rarityid;
    }

    public void setRarityid(String rarityid) {
        this.rarityid = rarityid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (treeboxid != null ? treeboxid.hashCode() : 0);
        hash += (rarityid != null ? rarityid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RaritydropratePK)) {
            return false;
        }
        RaritydropratePK other = (RaritydropratePK) object;
        if ((this.treeboxid == null && other.treeboxid != null) || (this.treeboxid != null && !this.treeboxid.equals(other.treeboxid))) {
            return false;
        }
        if ((this.rarityid == null && other.rarityid != null) || (this.rarityid != null && !this.rarityid.equals(other.rarityid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RaritydropratePK[ treeboxid=" + treeboxid + ", rarityid=" + rarityid + " ]";
    }
    
}
