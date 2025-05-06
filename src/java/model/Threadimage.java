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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JiaQuann
 */
@Entity
@Table(name = "THREADIMAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Threadimage.findAll", query = "SELECT t FROM Threadimage t"),
    @NamedQuery(name = "Threadimage.findByImageid", query = "SELECT t FROM Threadimage t WHERE t.threadimagePK.imageid = :imageid"),
    @NamedQuery(name = "Threadimage.findByThreadid", query = "SELECT t FROM Threadimage t WHERE t.threadimagePK.threadid = :threadid"),
    @NamedQuery(name = "Threadimage.findByIsmainimage", query = "SELECT t FROM Threadimage t WHERE t.ismainimage = :ismainimage"),
    @NamedQuery(name = "Threadimage.findByIsdeleted", query = "SELECT t FROM Threadimage t WHERE t.isdeleted = :isdeleted"),
    @NamedQuery(name = "Threadimage.findByImage", query = "SELECT t FROM Threadimage t WHERE t.image = :image")})
public class Threadimage implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ThreadimagePK threadimagePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISMAINIMAGE")
    private Boolean ismainimage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Size(max = 255)
    @Column(name = "IMAGE")
    private String image;
    @JoinColumn(name = "THREADID", referencedColumnName = "THREADID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Thread thread;

    public Threadimage() {
    }

    public Threadimage(ThreadimagePK threadimagePK) {
        this.threadimagePK = threadimagePK;
    }

    public Threadimage(ThreadimagePK threadimagePK, Boolean ismainimage, Boolean isdeleted) {
        this.threadimagePK = threadimagePK;
        this.ismainimage = ismainimage;
        this.isdeleted = isdeleted;
    }

    public Threadimage(String imageid, String threadid) {
        this.threadimagePK = new ThreadimagePK(imageid, threadid);
    }

    public ThreadimagePK getThreadimagePK() {
        return threadimagePK;
    }

    public void setThreadimagePK(ThreadimagePK threadimagePK) {
        this.threadimagePK = threadimagePK;
    }

    public Boolean getIsmainimage() {
        return ismainimage;
    }

    public void setIsmainimage(Boolean ismainimage) {
        this.ismainimage = ismainimage;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadimagePK != null ? threadimagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Threadimage)) {
            return false;
        }
        Threadimage other = (Threadimage) object;
        if ((this.threadimagePK == null && other.threadimagePK != null) || (this.threadimagePK != null && !this.threadimagePK.equals(other.threadimagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Threadimage[ threadimagePK=" + threadimagePK + " ]";
    }
    
}
