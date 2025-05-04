/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johno
 */
@Entity
@Table(name = "USERPLAYLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userplaylist.findAll", query = "SELECT u FROM Userplaylist u"),
    @NamedQuery(name = "Userplaylist.findByPlaylistid", query = "SELECT u FROM Userplaylist u WHERE u.playlistid = :playlistid"),
    @NamedQuery(name = "Userplaylist.findByPlaylistname", query = "SELECT u FROM Userplaylist u WHERE u.playlistname = :playlistname"),
    @NamedQuery(name = "Userplaylist.findByDatecreated", query = "SELECT u FROM Userplaylist u WHERE u.datecreated = :datecreated"),
    @NamedQuery(name = "Userplaylist.findByIsdeleted", query = "SELECT u FROM Userplaylist u WHERE u.isdeleted = :isdeleted")})
public class Userplaylist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "PLAYLISTID")
    private String playlistid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PLAYLISTNAME")
    private String playlistname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATECREATED")
    @Temporal(TemporalType.DATE)
    private Date datecreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userplaylist")
    private List<Playlistmusic> playlistmusicList;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Userplaylist() {
    }

    public Userplaylist(String playlistid) {
        this.playlistid = playlistid;
    }

    public Userplaylist(String playlistid, String playlistname, Date datecreated, Boolean isdeleted) {
        this.playlistid = playlistid;
        this.playlistname = playlistname;
        this.datecreated = datecreated;
        this.isdeleted = isdeleted;
    }

    public String getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(String playlistid) {
        this.playlistid = playlistid;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public void setPlaylistname(String playlistname) {
        this.playlistname = playlistname;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Playlistmusic> getPlaylistmusicList() {
        return playlistmusicList;
    }

    public void setPlaylistmusicList(List<Playlistmusic> playlistmusicList) {
        this.playlistmusicList = playlistmusicList;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playlistid != null ? playlistid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userplaylist)) {
            return false;
        }
        Userplaylist other = (Userplaylist) object;
        if ((this.playlistid == null && other.playlistid != null) || (this.playlistid != null && !this.playlistid.equals(other.playlistid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Userplaylist[ playlistid=" + playlistid + " ]";
    }
    
}
