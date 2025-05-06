package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing the user queue list.
 */
@Entity
@Table(name = "USERQUEUELIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userqueuelist.findByUserid", query = "SELECT u FROM Userqueuelist u WHERE u.userid = :userid"),
    @NamedQuery(name = "Userqueuelist.findByMusicid", query = "SELECT u FROM Userqueuelist u WHERE u.musicid = :musicid")
})
@IdClass(UserqueuelistPK.class)
public class Userqueuelist implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERID")
    private String userid;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "MUSICID")
    private String musicid;

    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users user;

    @JoinColumn(name = "MUSICID", referencedColumnName = "MUSICID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Music music;

    // Getters and Setters
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMusicid() {
        return musicid;
    }

    public void setMusicid(String musicid) {
        this.musicid = musicid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, musicid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Userqueuelist)) return false;
        Userqueuelist other = (Userqueuelist) obj;
        return Objects.equals(userid, other.userid) && Objects.equals(musicid, other.musicid);
    }

    @Override
    public String toString() {
        return "Userqueuelist[ userid=" + userid + ", musicid=" + musicid + " ]";
    }
}
