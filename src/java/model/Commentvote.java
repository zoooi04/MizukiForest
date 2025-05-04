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
@Table(name = "COMMENTVOTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commentvote.findAll", query = "SELECT c FROM Commentvote c"),
    @NamedQuery(name = "Commentvote.findByThreadcommentid", query = "SELECT c FROM Commentvote c WHERE c.commentvotePK.threadcommentid = :threadcommentid"),
    @NamedQuery(name = "Commentvote.findByUserid", query = "SELECT c FROM Commentvote c WHERE c.commentvotePK.userid = :userid"),
    @NamedQuery(name = "Commentvote.findByVotetype", query = "SELECT c FROM Commentvote c WHERE c.votetype = :votetype")})
public class Commentvote implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommentvotePK commentvotePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOTETYPE")
    private Boolean votetype;
    @JoinColumn(name = "THREADCOMMENTID", referencedColumnName = "THREADCOMMENTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Threadcomment threadcomment;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Commentvote() {
    }

    public Commentvote(CommentvotePK commentvotePK) {
        this.commentvotePK = commentvotePK;
    }

    public Commentvote(CommentvotePK commentvotePK, Boolean votetype) {
        this.commentvotePK = commentvotePK;
        this.votetype = votetype;
    }

    public Commentvote(String threadcommentid, String userid) {
        this.commentvotePK = new CommentvotePK(threadcommentid, userid);
    }

    public CommentvotePK getCommentvotePK() {
        return commentvotePK;
    }

    public void setCommentvotePK(CommentvotePK commentvotePK) {
        this.commentvotePK = commentvotePK;
    }

    public Boolean getVotetype() {
        return votetype;
    }

    public void setVotetype(Boolean votetype) {
        this.votetype = votetype;
    }

    public Threadcomment getThreadcomment() {
        return threadcomment;
    }

    public void setThreadcomment(Threadcomment threadcomment) {
        this.threadcomment = threadcomment;
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
        hash += (commentvotePK != null ? commentvotePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commentvote)) {
            return false;
        }
        Commentvote other = (Commentvote) object;
        if ((this.commentvotePK == null && other.commentvotePK != null) || (this.commentvotePK != null && !this.commentvotePK.equals(other.commentvotePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Commentvote[ commentvotePK=" + commentvotePK + " ]";
    }
    
}
