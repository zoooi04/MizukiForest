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
@Table(name = "TAG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
    @NamedQuery(name = "Tag.findByTagid", query = "SELECT t FROM Tag t WHERE t.tagid = :tagid"),
    @NamedQuery(name = "Tag.findByTagname", query = "SELECT t FROM Tag t WHERE t.tagname = :tagname"),
    @NamedQuery(name = "Tag.findByIscustomisable", query = "SELECT t FROM Tag t WHERE t.iscustomisable = :iscustomisable"),
    @NamedQuery(name = "Tag.findByIsdeleted", query = "SELECT t FROM Tag t WHERE t.isdeleted = :isdeleted")})
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "TAGID")
    private String tagid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TAGNAME")
    private String tagname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCUSTOMISABLE")
    private Boolean iscustomisable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tagid")
    private List<Usertag> usertagList;
    @OneToMany(mappedBy = "tagid")
    private List<Diarytag> diarytagList;

    public Tag() {
    }

    public Tag(String tagid) {
        this.tagid = tagid;
    }

    public Tag(String tagid, String tagname, Boolean iscustomisable, Boolean isdeleted) {
        this.tagid = tagid;
        this.tagname = tagname;
        this.iscustomisable = iscustomisable;
        this.isdeleted = isdeleted;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Boolean getIscustomisable() {
        return iscustomisable;
    }

    public void setIscustomisable(Boolean iscustomisable) {
        this.iscustomisable = iscustomisable;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Usertag> getUsertagList() {
        return usertagList;
    }

    public void setUsertagList(List<Usertag> usertagList) {
        this.usertagList = usertagList;
    }

    @XmlTransient
    public List<Diarytag> getDiarytagList() {
        return diarytagList;
    }

    public void setDiarytagList(List<Diarytag> diarytagList) {
        this.diarytagList = diarytagList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tagid != null ? tagid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.tagid == null && other.tagid != null) || (this.tagid != null && !this.tagid.equals(other.tagid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tag[ tagid=" + tagid + " ]";
    }
    
}
