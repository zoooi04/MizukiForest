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
 * @author JiaQuann
 */
@Entity
@Table(name = "TOKEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Token.findAll", query = "SELECT t FROM Token t"),
    @NamedQuery(name = "Token.findByTokenid", query = "SELECT t FROM Token t WHERE t.tokenid = :tokenid"),
    @NamedQuery(name = "Token.findByExpire", query = "SELECT t FROM Token t WHERE t.expire = :expire"),
    @NamedQuery(name = "Token.findByIsdeleted", query = "SELECT t FROM Token t WHERE t.isdeleted = :isdeleted")})
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TOKENID")
    private String tokenid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPIRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Token() {
    }

    public Token(String tokenid) {
        this.tokenid = tokenid;
    }

    public Token(String tokenid, Date expire, Boolean isdeleted) {
        this.tokenid = tokenid;
        this.expire = expire;
        this.isdeleted = isdeleted;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
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
        hash += (tokenid != null ? tokenid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Token)) {
            return false;
        }
        Token other = (Token) object;
        if ((this.tokenid == null && other.tokenid != null) || (this.tokenid != null && !this.tokenid.equals(other.tokenid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Token[ tokenid=" + tokenid + " ]";
    }
    
}
