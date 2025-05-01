/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JiaQuann
 */
@Entity
@Table(name = "THREAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Thread.findAll", query = "SELECT t FROM Thread t"),
    @NamedQuery(name = "Thread.findByThreadid", query = "SELECT t FROM Thread t WHERE t.threadid = :threadid"),
    @NamedQuery(name = "Thread.findByThreadtitle", query = "SELECT t FROM Thread t WHERE t.threadtitle = :threadtitle"),
    @NamedQuery(name = "Thread.findByThreaddescription", query = "SELECT t FROM Thread t WHERE t.threaddescription = :threaddescription"),
    @NamedQuery(name = "Thread.findByUpvote", query = "SELECT t FROM Thread t WHERE t.upvote = :upvote"),
    @NamedQuery(name = "Thread.findByDownvote", query = "SELECT t FROM Thread t WHERE t.downvote = :downvote"),
    @NamedQuery(name = "Thread.findBySharecount", query = "SELECT t FROM Thread t WHERE t.sharecount = :sharecount"),
    @NamedQuery(name = "Thread.findByIsdeleted", query = "SELECT t FROM Thread t WHERE t.isdeleted = :isdeleted")})
public class Thread implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "THREADID")
    private String threadid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "THREADTITLE")
    private String threadtitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "THREADDESCRIPTION")
    private String threaddescription;
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
    @Column(name = "SHARECOUNT")
    private int sharecount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    private List<Threadimage> threadimageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "threadid")
    private List<Threadcomment> threadcommentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    private List<Threadvote> threadvoteList;
    @JoinColumn(name = "THREADCATEGORYID", referencedColumnName = "THREADCATEGORYID")
    @ManyToOne(optional = false)
    private Threadcategory threadcategoryid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Thread() {
    }

    public Thread(String threadid) {
        this.threadid = threadid;
    }

    public Thread(String threadid, String threadtitle, String threaddescription, int upvote, int downvote, int sharecount, Boolean isdeleted) {
        this.threadid = threadid;
        this.threadtitle = threadtitle;
        this.threaddescription = threaddescription;
        this.upvote = upvote;
        this.downvote = downvote;
        this.sharecount = sharecount;
        this.isdeleted = isdeleted;
    }

    public String getThreadid() {
        return threadid;
    }

    public void setThreadid(String threadid) {
        this.threadid = threadid;
    }

    public String getThreadtitle() {
        return threadtitle;
    }

    public void setThreadtitle(String threadtitle) {
        this.threadtitle = threadtitle;
    }

    public String getThreaddescription() {
        return threaddescription;
    }

    public void setThreaddescription(String threaddescription) {
        this.threaddescription = threaddescription;
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

    public int getSharecount() {
        return sharecount;
    }

    public void setSharecount(int sharecount) {
        this.sharecount = sharecount;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Threadimage> getThreadimageList() {
        return threadimageList;
    }

    public void setThreadimageList(List<Threadimage> threadimageList) {
        this.threadimageList = threadimageList;
    }

    @XmlTransient
    public List<Threadcomment> getThreadcommentList() {
        return threadcommentList;
    }

    public void setThreadcommentList(List<Threadcomment> threadcommentList) {
        this.threadcommentList = threadcommentList;
    }

    @XmlTransient
    public List<Threadvote> getThreadvoteList() {
        return threadvoteList;
    }

    public void setThreadvoteList(List<Threadvote> threadvoteList) {
        this.threadvoteList = threadvoteList;
    }

    public Threadcategory getThreadcategoryid() {
        return threadcategoryid;
    }

    public void setThreadcategoryid(Threadcategory threadcategoryid) {
        this.threadcategoryid = threadcategoryid;
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
        hash += (threadid != null ? threadid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Thread)) {
            return false;
        }
        Thread other = (Thread) object;
        if ((this.threadid == null && other.threadid != null) || (this.threadid != null && !this.threadid.equals(other.threadid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Thread[ threadid=" + threadid + " ]";
    }
    
}
