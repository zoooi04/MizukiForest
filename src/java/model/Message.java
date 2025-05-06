/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author huaiern
 */
@Entity
@Table(name = "MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByMessageid", query = "SELECT m FROM Message m WHERE m.messageid = :messageid"),
    @NamedQuery(name = "Message.findBySender", query = "SELECT m FROM Message m WHERE m.sender = :sender"),
    @NamedQuery(name = "Message.findByContent", query = "SELECT m FROM Message m WHERE m.content = :content"),
    @NamedQuery(name = "Message.findByTimestampsent", query = "SELECT m FROM Message m WHERE m.timestampsent = :timestampsent"),
    @NamedQuery(name = "Message.findByTimestampread", query = "SELECT m FROM Message m WHERE m.timestampread = :timestampread"),
    @NamedQuery(name = "Message.findByIsdeleted", query = "SELECT m FROM Message m WHERE m.isdeleted = :isdeleted")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "MESSAGEID")
    private String messageid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "SENDER")
    private String sender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "CONTENT")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIMESTAMPSENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampsent;
    @Column(name = "TIMESTAMPREAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampread;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "THERAPISTID", referencedColumnName = "THERAPISTID")
    @ManyToOne(optional = false)
    private Therapist therapistid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Message() {
    }

    public Message(String messageid) {
        this.messageid = messageid;
    }

    public Message(String messageid, Users user, Therapist therapist, String sender, String content, Date timestampsent, Boolean isdeleted) {
        this.messageid = messageid;
        this.userid = user;
        this.therapistid = therapist;
        this.sender = sender;
        this.content = content;
        this.timestampsent = timestampsent;
        this.isdeleted = isdeleted;
    }
    

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestampsent() {
        return timestampsent;
    }

    public void setTimestampsent(Date timestampsent) {
        this.timestampsent = timestampsent;
    }

    public Date getTimestampread() {
        return timestampread;
    }

    public void setTimestampread(Date timestampread) {
        this.timestampread = timestampread;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Therapist getTherapistid() {
        return therapistid;
    }

    public void setTherapistid(Therapist therapistid) {
        this.therapistid = therapistid;
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
        hash += (messageid != null ? messageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageid == null && other.messageid != null) || (this.messageid != null && !this.messageid.equals(other.messageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Message[ messageid=" + messageid + " ]";
    }
    
}
