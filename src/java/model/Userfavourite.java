/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
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
@Table(name = "USERFAVOURITE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userfavourite.findAll", query = "SELECT u FROM Userfavourite u"),
    @NamedQuery(name = "Userfavourite.findByUserid", query = "SELECT u FROM Userfavourite u WHERE u.userfavouritePK.userid = :userid"),
    @NamedQuery(name = "Userfavourite.findByMusicid", query = "SELECT u FROM Userfavourite u WHERE u.userfavouritePK.musicid = :musicid"),
    @NamedQuery(name = "Userfavourite.findByIsdeleted", query = "SELECT u FROM Userfavourite u WHERE u.isdeleted = :isdeleted")})
public class Userfavourite implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserfavouritePK userfavouritePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "MUSICID", referencedColumnName = "MUSICID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Music music;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Userfavourite() {
    }

    public Userfavourite(UserfavouritePK userfavouritePK) {
        this.userfavouritePK = userfavouritePK;
    }

    public Userfavourite(UserfavouritePK userfavouritePK, Boolean isdeleted) {
        this.userfavouritePK = userfavouritePK;
        this.isdeleted = isdeleted;
    }

    public Userfavourite(String userid, String musicid) {
        this.userfavouritePK = new UserfavouritePK(userid, musicid);
    }

    public UserfavouritePK getUserfavouritePK() {
        return userfavouritePK;
    }

    public void setUserfavouritePK(UserfavouritePK userfavouritePK) {
        this.userfavouritePK = userfavouritePK;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userfavouritePK != null ? userfavouritePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userfavourite)) {
            return false;
        }
        Userfavourite other = (Userfavourite) object;
        if ((this.userfavouritePK == null && other.userfavouritePK != null) || (this.userfavouritePK != null && !this.userfavouritePK.equals(other.userfavouritePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Userfavourite[ userfavouritePK=" + userfavouritePK + " ]";
    }
    
}
