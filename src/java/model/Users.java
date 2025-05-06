/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @author JiaQuann
 */
@Entity
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.userid = :userid"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByUserpw", query = "SELECT u FROM Users u WHERE u.userpw = :userpw"),
    @NamedQuery(name = "Users.findByUseremail", query = "SELECT u FROM Users u WHERE u.useremail = :useremail"),
    @NamedQuery(name = "Users.findByUserbirthday", query = "SELECT u FROM Users u WHERE u.userbirthday = :userbirthday"),
    @NamedQuery(name = "Users.findByCoins", query = "SELECT u FROM Users u WHERE u.coins = :coins"),
    @NamedQuery(name = "Users.findByExp", query = "SELECT u FROM Users u WHERE u.exp = :exp"),
    @NamedQuery(name = "Users.findByLoginstreak", query = "SELECT u FROM Users u WHERE u.loginstreak = :loginstreak"),
    @NamedQuery(name = "Users.findByUserlevel", query = "SELECT u FROM Users u WHERE u.userlevel = :userlevel"),
    @NamedQuery(name = "Users.findByPity", query = "SELECT u FROM Users u WHERE u.pity = :pity"),
    @NamedQuery(name = "Users.findByLastlogindate", query = "SELECT u FROM Users u WHERE u.lastlogindate = :lastlogindate"),
    @NamedQuery(name = "Users.findByDiaryvisibility", query = "SELECT u FROM Users u WHERE u.diaryvisibility = :diaryvisibility"),
    @NamedQuery(name = "Users.findByForestvisibility", query = "SELECT u FROM Users u WHERE u.forestvisibility = :forestvisibility"),
    @NamedQuery(name = "Users.findByIsdeleted", query = "SELECT u FROM Users u WHERE u.isdeleted = :isdeleted"),
    @NamedQuery(name = "Users.findByUserimage", query = "SELECT u FROM Users u WHERE u.userimage = :userimage")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "USERID")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USERPW")
    private String userpw;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USEREMAIL")
    private String useremail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERBIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date userbirthday;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COINS")
    private int coins;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXP")
    private int exp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOGINSTREAK")
    private int loginstreak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERLEVEL")
    private int userlevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PITY")
    private int pity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LASTLOGINDATE")
    @Temporal(TemporalType.DATE)
    private Date lastlogindate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DIARYVISIBILITY")
    private Boolean diaryvisibility;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FORESTVISIBILITY")
    private Boolean forestvisibility;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Size(max = 100)
    @Column(name = "USERIMAGE")
    private String userimage;
    @JoinTable(name = "USERQUEUELIST", joinColumns = {
        @JoinColumn(name = "USERID", referencedColumnName = "USERID")}, inverseJoinColumns = {
        @JoinColumn(name = "MUSICID", referencedColumnName = "MUSICID")})
    @ManyToMany
    private Collection<Music> musicCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Land> landCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Focussession> focussessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Diaryentry> diaryentryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Appointment> appointmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Threadcomment> threadcommentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Userplaylist> userplaylistCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Response> responseCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<Userachievement> userachievementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Token> tokenCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Userinventoryitem> userinventoryitemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Reportcontent> reportcontentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<Threadvote> threadvoteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<Commentvote> commentvoteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Usertherapist> usertherapistCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<Userbadge> userbadgeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Usertasklist> usertasklistCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Thread> threadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<Userfavourite> userfavouriteCollection;

    public Users() {
    }

    public Users(String userid) {
        this.userid = userid;
    }

    public Users(String userid, String username, String userpw, String useremail, Date userbirthday, int coins, int exp, int loginstreak, int userlevel, int pity, Date lastlogindate, Boolean diaryvisibility, Boolean forestvisibility, Boolean isdeleted) {
        this.userid = userid;
        this.username = username;
        this.userpw = userpw;
        this.useremail = useremail;
        this.userbirthday = userbirthday;
        this.coins = coins;
        this.exp = exp;
        this.loginstreak = loginstreak;
        this.userlevel = userlevel;
        this.pity = pity;
        this.lastlogindate = lastlogindate;
        this.diaryvisibility = diaryvisibility;
        this.forestvisibility = forestvisibility;
        this.isdeleted = isdeleted;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public Date getUserbirthday() {
        return userbirthday;
    }

    public void setUserbirthday(Date userbirthday) {
        this.userbirthday = userbirthday;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLoginstreak() {
        return loginstreak;
    }

    public void setLoginstreak(int loginstreak) {
        this.loginstreak = loginstreak;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }

    public int getPity() {
        return pity;
    }

    public void setPity(int pity) {
        this.pity = pity;
    }

    public Date getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(Date lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public Boolean getDiaryvisibility() {
        return diaryvisibility;
    }

    public void setDiaryvisibility(Boolean diaryvisibility) {
        this.diaryvisibility = diaryvisibility;
    }

    public Boolean getForestvisibility() {
        return forestvisibility;
    }

    public void setForestvisibility(Boolean forestvisibility) {
        this.forestvisibility = forestvisibility;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    @XmlTransient
    public Collection<Music> getMusicCollection() {
        return musicCollection;
    }

    public void setMusicCollection(Collection<Music> musicCollection) {
        this.musicCollection = musicCollection;
    }

    @XmlTransient
    public Collection<Land> getLandCollection() {
        return landCollection;
    }

    public void setLandCollection(Collection<Land> landCollection) {
        this.landCollection = landCollection;
    }

    @XmlTransient
    public Collection<Focussession> getFocussessionCollection() {
        return focussessionCollection;
    }

    public void setFocussessionCollection(Collection<Focussession> focussessionCollection) {
        this.focussessionCollection = focussessionCollection;
    }

    @XmlTransient
    public Collection<Diaryentry> getDiaryentryCollection() {
        return diaryentryCollection;
    }

    public void setDiaryentryCollection(Collection<Diaryentry> diaryentryCollection) {
        this.diaryentryCollection = diaryentryCollection;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @XmlTransient
    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    @XmlTransient
    public Collection<Threadcomment> getThreadcommentCollection() {
        return threadcommentCollection;
    }

    public void setThreadcommentCollection(Collection<Threadcomment> threadcommentCollection) {
        this.threadcommentCollection = threadcommentCollection;
    }

    @XmlTransient
    public Collection<Userplaylist> getUserplaylistCollection() {
        return userplaylistCollection;
    }

    public void setUserplaylistCollection(Collection<Userplaylist> userplaylistCollection) {
        this.userplaylistCollection = userplaylistCollection;
    }

    @XmlTransient
    public Collection<Response> getResponseCollection() {
        return responseCollection;
    }

    public void setResponseCollection(Collection<Response> responseCollection) {
        this.responseCollection = responseCollection;
    }

    @XmlTransient
    public Collection<Userachievement> getUserachievementCollection() {
        return userachievementCollection;
    }

    public void setUserachievementCollection(Collection<Userachievement> userachievementCollection) {
        this.userachievementCollection = userachievementCollection;
    }

    @XmlTransient
    public Collection<Token> getTokenCollection() {
        return tokenCollection;
    }

    public void setTokenCollection(Collection<Token> tokenCollection) {
        this.tokenCollection = tokenCollection;
    }

    @XmlTransient
    public Collection<Userinventoryitem> getUserinventoryitemCollection() {
        return userinventoryitemCollection;
    }

    public void setUserinventoryitemCollection(Collection<Userinventoryitem> userinventoryitemCollection) {
        this.userinventoryitemCollection = userinventoryitemCollection;
    }

    @XmlTransient
    public Collection<Reportcontent> getReportcontentCollection() {
        return reportcontentCollection;
    }

    public void setReportcontentCollection(Collection<Reportcontent> reportcontentCollection) {
        this.reportcontentCollection = reportcontentCollection;
    }

    @XmlTransient
    public Collection<Threadvote> getThreadvoteCollection() {
        return threadvoteCollection;
    }

    public void setThreadvoteCollection(Collection<Threadvote> threadvoteCollection) {
        this.threadvoteCollection = threadvoteCollection;
    }

    @XmlTransient
    public Collection<Commentvote> getCommentvoteCollection() {
        return commentvoteCollection;
    }

    public void setCommentvoteCollection(Collection<Commentvote> commentvoteCollection) {
        this.commentvoteCollection = commentvoteCollection;
    }

    @XmlTransient
    public Collection<Usertherapist> getUsertherapistCollection() {
        return usertherapistCollection;
    }

    public void setUsertherapistCollection(Collection<Usertherapist> usertherapistCollection) {
        this.usertherapistCollection = usertherapistCollection;
    }

    @XmlTransient
    public Collection<Userbadge> getUserbadgeCollection() {
        return userbadgeCollection;
    }

    public void setUserbadgeCollection(Collection<Userbadge> userbadgeCollection) {
        this.userbadgeCollection = userbadgeCollection;
    }

    @XmlTransient
    public Collection<Usertasklist> getUsertasklistCollection() {
        return usertasklistCollection;
    }

    public void setUsertasklistCollection(Collection<Usertasklist> usertasklistCollection) {
        this.usertasklistCollection = usertasklistCollection;
    }

    @XmlTransient
    public Collection<Thread> getThreadCollection() {
        return threadCollection;
    }

    public void setThreadCollection(Collection<Thread> threadCollection) {
        this.threadCollection = threadCollection;
    }

    @XmlTransient
    public Collection<Userfavourite> getUserfavouriteCollection() {
        return userfavouriteCollection;
    }

    public void setUserfavouriteCollection(Collection<Userfavourite> userfavouriteCollection) {
        this.userfavouriteCollection = userfavouriteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Users[ userid=" + userid + " ]";
    }
    
}
