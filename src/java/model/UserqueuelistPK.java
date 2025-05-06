package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite Primary Key for Userqueuelist.
 */
public class UserqueuelistPK implements Serializable {

    private String userid;
    private String musicid;

    public UserqueuelistPK() {
    }

    public UserqueuelistPK(String userid, String musicid) {
        this.userid = userid;
        this.musicid = musicid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, musicid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserqueuelistPK)) return false;
        UserqueuelistPK other = (UserqueuelistPK) obj;
        return Objects.equals(userid, other.userid) && Objects.equals(musicid, other.musicid);
    }
}
