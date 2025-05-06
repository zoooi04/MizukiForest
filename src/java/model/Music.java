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
import javax.persistence.ManyToMany;
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
@Table(name = "MUSIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Music.findAll", query = "SELECT m FROM Music m"),
    @NamedQuery(name = "Music.findByMusicid", query = "SELECT m FROM Music m WHERE m.musicid = :musicid"),
    @NamedQuery(name = "Music.findByMusicname", query = "SELECT m FROM Music m WHERE m.musicname = :musicname"),
    @NamedQuery(name = "Music.findByAuthor", query = "SELECT m FROM Music m WHERE m.author = :author"),
    @NamedQuery(name = "Music.findByFilepath", query = "SELECT m FROM Music m WHERE m.filepath = :filepath"),
    @NamedQuery(name = "Music.findByMhour", query = "SELECT m FROM Music m WHERE m.mhour = :mhour"),
    @NamedQuery(name = "Music.findByMminute", query = "SELECT m FROM Music m WHERE m.mminute = :mminute"),
    @NamedQuery(name = "Music.findByMsecond", query = "SELECT m FROM Music m WHERE m.msecond = :msecond"),
    @NamedQuery(name = "Music.findByIsdeleted", query = "SELECT m FROM Music m WHERE m.isdeleted = :isdeleted")})
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "MUSICID")
    private String musicid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MUSICNAME")
    private String musicname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "AUTHOR")
    private String author;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "FILEPATH")
    private String filepath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MHOUR")
    private int mhour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MMINUTE")
    private int mminute;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MSECOND")
    private int msecond;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @ManyToMany(mappedBy = "musicCollection")
    private Collection<Users> usersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "music")
    private Collection<Playlistmusic> playlistmusicCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "music")
    private Collection<Userfavourite> userfavouriteCollection;

    public Music() {
    }

    public Music(String musicid) {
        this.musicid = musicid;
    }

    public Music(String musicid, String musicname, String author, String filepath, int mhour, int mminute, int msecond, Boolean isdeleted) {
        this.musicid = musicid;
        this.musicname = musicname;
        this.author = author;
        this.filepath = filepath;
        this.mhour = mhour;
        this.mminute = mminute;
        this.msecond = msecond;
        this.isdeleted = isdeleted;
    }

    public String getMusicid() {
        return musicid;
    }

    public void setMusicid(String musicid) {
        this.musicid = musicid;
    }

    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getMhour() {
        return mhour;
    }

    public void setMhour(int mhour) {
        this.mhour = mhour;
    }

    public int getMminute() {
        return mminute;
    }

    public void setMminute(int mminute) {
        this.mminute = mminute;
    }

    public int getMsecond() {
        return msecond;
    }

    public void setMsecond(int msecond) {
        this.msecond = msecond;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    public Collection<Playlistmusic> getPlaylistmusicCollection() {
        return playlistmusicCollection;
    }

    public void setPlaylistmusicCollection(Collection<Playlistmusic> playlistmusicCollection) {
        this.playlistmusicCollection = playlistmusicCollection;
    }

    @XmlTransient
    public Collection<Userfavourite> getUserfavouriteCollection() {
        return userfavouriteCollection;
    }

    public void setUserfavouriteCollection(Collection<Userfavourite> userfavouriteCollection) {
        this.userfavouriteCollection = userfavouriteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (musicid != null ? musicid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Music)) {
            return false;
        }
        Music other = (Music) object;
        if ((this.musicid == null && other.musicid != null) || (this.musicid != null && !this.musicid.equals(other.musicid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Music[ musicid=" + musicid + " ]";
    }
    
}
