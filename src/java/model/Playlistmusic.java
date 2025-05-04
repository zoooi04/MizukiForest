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
@Table(name = "PLAYLISTMUSIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlistmusic.findAll", query = "SELECT p FROM Playlistmusic p"),
    @NamedQuery(name = "Playlistmusic.findByPlaylistid", query = "SELECT p FROM Playlistmusic p WHERE p.playlistmusicPK.playlistid = :playlistid"),
    @NamedQuery(name = "Playlistmusic.findByMusicid", query = "SELECT p FROM Playlistmusic p WHERE p.playlistmusicPK.musicid = :musicid"),
    @NamedQuery(name = "Playlistmusic.findByIsdeleted", query = "SELECT p FROM Playlistmusic p WHERE p.isdeleted = :isdeleted")})
public class Playlistmusic implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlaylistmusicPK playlistmusicPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "MUSICID", referencedColumnName = "MUSICID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Music music;
    @JoinColumn(name = "PLAYLISTID", referencedColumnName = "PLAYLISTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Userplaylist userplaylist;

    public Playlistmusic() {
    }

    public Playlistmusic(PlaylistmusicPK playlistmusicPK) {
        this.playlistmusicPK = playlistmusicPK;
    }

    public Playlistmusic(PlaylistmusicPK playlistmusicPK, Boolean isdeleted) {
        this.playlistmusicPK = playlistmusicPK;
        this.isdeleted = isdeleted;
    }

    public Playlistmusic(String playlistid, String musicid) {
        this.playlistmusicPK = new PlaylistmusicPK(playlistid, musicid);
    }

    public PlaylistmusicPK getPlaylistmusicPK() {
        return playlistmusicPK;
    }

    public void setPlaylistmusicPK(PlaylistmusicPK playlistmusicPK) {
        this.playlistmusicPK = playlistmusicPK;
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

    public Userplaylist getUserplaylist() {
        return userplaylist;
    }

    public void setUserplaylist(Userplaylist userplaylist) {
        this.userplaylist = userplaylist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playlistmusicPK != null ? playlistmusicPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlistmusic)) {
            return false;
        }
        Playlistmusic other = (Playlistmusic) object;
        if ((this.playlistmusicPK == null && other.playlistmusicPK != null) || (this.playlistmusicPK != null && !this.playlistmusicPK.equals(other.playlistmusicPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Playlistmusic[ playlistmusicPK=" + playlistmusicPK + " ]";
    }
    
}
