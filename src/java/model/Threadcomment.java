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
@Table(name = "THREADCOMMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Threadcomment.findAll", query = "SELECT t FROM Threadcomment t"),
    @NamedQuery(name = "Threadcomment.findByThreadcommentid", query = "SELECT t FROM Threadcomment t WHERE t.threadcommentid = :threadcommentid"),
    @NamedQuery(name = "Threadcomment.findByPostdatetime", query = "SELECT t FROM Threadcomment t WHERE t.postdatetime = :postdatetime"),
    @NamedQuery(name = "Threadcomment.findByContent", query = "SELECT t FROM Threadcomment t WHERE t.content = :content"),
    @NamedQuery(name = "Threadcomment.findByUpvote", query = "SELECT t FROM Threadcomment t WHERE t.upvote = :upvote"),
    @NamedQuery(name = "Threadcomment.findByDownvote", query = "SELECT t FROM Threadcomment t WHERE t.downvote = :downvote"),
    @NamedQuery(name = "Threadcomment.findByIsdeleted", query = "SELECT t FROM Threadcomment t WHERE t.isdeleted = :isdeleted")})
public class Threadcomment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "THREADCOMMENTID")
    private String threadcommentid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSTDATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postdatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "CONTENT")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPVOTE")
    private int upvote;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOWNVOTE")
    private int downvote;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "THREADID", referencedColumnName = "THREADID")
    @ManyToOne(optional = false)
    private Thread threadid;
    @OneToMany(mappedBy = "commentidreplyingto")
    private List<Threadcomment> threadcommentList;
    @JoinColumn(name = "COMMENTIDREPLYINGTO", referencedColumnName = "THREADCOMMENTID")
    @ManyToOne
    private Threadcomment commentidreplyingto;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;
    @OneToMany(mappedBy = "threadcommentid")
    private List<Reportcontent> reportcontentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "threadcomment")
    private List<Commentvote> commentvoteList;

    public Threadcomment() {
    }

    public Threadcomment(String threadcommentid) {
        this.threadcommentid = threadcommentid;
    }

    public Threadcomment(String threadcommentid, Date postdatetime, String content, int upvote, int downvote, Boolean isdeleted) {
        this.threadcommentid = threadcommentid;
        this.postdatetime = postdatetime;
        this.content = content;
        this.upvote = upvote;
        this.downvote = downvote;
        this.isdeleted = isdeleted;
    }

    public String getThreadcommentid() {
        return threadcommentid;
    }

    public void setThreadcommentid(String threadcommentid) {
        this.threadcommentid = threadcommentid;
    }

    public Date getPostdatetime() {
        return postdatetime;
    }

    public void setPostdatetime(Date postdatetime) {
        this.postdatetime = postdatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Thread getThreadid() {
        return threadid;
    }

    public void setThreadid(Thread threadid) {
        this.threadid = threadid;
    }

    @XmlTransient
    public List<Threadcomment> getThreadcommentList() {
        return threadcommentList;
    }

    public void setThreadcommentList(List<Threadcomment> threadcommentList) {
        this.threadcommentList = threadcommentList;
    }

    public Threadcomment getCommentidreplyingto() {
        return commentidreplyingto;
    }

    public void setCommentidreplyingto(Threadcomment commentidreplyingto) {
        this.commentidreplyingto = commentidreplyingto;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @XmlTransient
    public List<Reportcontent> getReportcontentList() {
        return reportcontentList;
    }

    public void setReportcontentList(List<Reportcontent> reportcontentList) {
        this.reportcontentList = reportcontentList;
    }

    @XmlTransient
    public List<Commentvote> getCommentvoteList() {
        return commentvoteList;
    }

    public void setCommentvoteList(List<Commentvote> commentvoteList) {
        this.commentvoteList = commentvoteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadcommentid != null ? threadcommentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Threadcomment)) {
            return false;
        }
        Threadcomment other = (Threadcomment) object;
        if ((this.threadcommentid == null && other.threadcommentid != null) || (this.threadcommentid != null && !this.threadcommentid.equals(other.threadcommentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Threadcomment[ threadcommentid=" + threadcommentid + " ]";
    }
    
}
