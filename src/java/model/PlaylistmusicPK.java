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
public class PlaylistmusicPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "PLAYLISTID")
    private String playlistid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "MUSICID")
    private String musicid;

    public PlaylistmusicPK() {
    }

    public PlaylistmusicPK(String playlistid, String musicid) {
        this.playlistid = playlistid;
        this.musicid = musicid;
    }

    public String getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(String playlistid) {
        this.playlistid = playlistid;
    }

    public String getMusicid() {
        return musicid;
    }

    public void setMusicid(String musicid) {
        this.musicid = musicid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playlistid != null ? playlistid.hashCode() : 0);
        hash += (musicid != null ? musicid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaylistmusicPK)) {
            return false;
        }
        PlaylistmusicPK other = (PlaylistmusicPK) object;
        if ((this.playlistid == null && other.playlistid != null) || (this.playlistid != null && !this.playlistid.equals(other.playlistid))) {
            return false;
        }
        if ((this.musicid == null && other.musicid != null) || (this.musicid != null && !this.musicid.equals(other.musicid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PlaylistmusicPK[ playlistid=" + playlistid + ", musicid=" + musicid + " ]";
    }
    
}
