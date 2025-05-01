/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "RESPONSE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Response.findAll", query = "SELECT r FROM Response r"),
    @NamedQuery(name = "Response.findByResponseid", query = "SELECT r FROM Response r WHERE r.responseid = :responseid"),
    @NamedQuery(name = "Response.findByResponsedescription", query = "SELECT r FROM Response r WHERE r.responsedescription = :responsedescription"),
    @NamedQuery(name = "Response.findByIsarchived", query = "SELECT r FROM Response r WHERE r.isarchived = :isarchived"),
    @NamedQuery(name = "Response.findByIsdeleted", query = "SELECT r FROM Response r WHERE r.isdeleted = :isdeleted")})
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "RESPONSEID")
    private String responseid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "RESPONSEDESCRIPTION")
    private String responsedescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISARCHIVED")
    private Boolean isarchived;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "QUESTIONID")
    @ManyToOne(optional = false)
    private Question questionid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Response() {
    }

    public Response(String responseid) {
        this.responseid = responseid;
    }

    public Response(String responseid, String responsedescription, Boolean isarchived, Boolean isdeleted) {
        this.responseid = responseid;
        this.responsedescription = responsedescription;
        this.isarchived = isarchived;
        this.isdeleted = isdeleted;
    }

    public String getResponseid() {
        return responseid;
    }

    public void setResponseid(String responseid) {
        this.responseid = responseid;
    }

    public String getResponsedescription() {
        return responsedescription;
    }

    public void setResponsedescription(String responsedescription) {
        this.responsedescription = responsedescription;
    }

    public Boolean getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(Boolean isarchived) {
        this.isarchived = isarchived;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Question getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Question questionid) {
        this.questionid = questionid;
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
        hash += (responseid != null ? responseid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Response)) {
            return false;
        }
        Response other = (Response) object;
        if ((this.responseid == null && other.responseid != null) || (this.responseid != null && !this.responseid.equals(other.responseid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Response[ responseid=" + responseid + " ]";
    }
    
}
