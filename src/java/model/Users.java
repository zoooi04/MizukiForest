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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
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
    @NamedQuery(name = "Users.findByDiaryvisibility", query = "SELECT u FROM Users u WHERE u.diaryvisibility = :diaryvisibility"),
    @NamedQuery(name = "Users.findByForestvisibility", query = "SELECT u FROM Users u WHERE u.forestvisibility = :forestvisibility"),
    @NamedQuery(name = "Users.findByIsdeleted", query = "SELECT u FROM Users u WHERE u.isdeleted = :isdeleted")})
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
    @Lob
    @Column(name = "USERIMAGE")
    private Serializable userimage;
    @JoinTable(name = "USERQUEUELIST", joinColumns = {
        @JoinColumn(name = "USERID", referencedColumnName = "USERID")}, inverseJoinColumns = {
        @JoinColumn(name = "MUSICID", referencedColumnName = "MUSICID")})
    @ManyToMany
    private List<Music> musicList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Land> landList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Usertag> usertagList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Focussession> focussessionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Diaryentry> diaryentryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Message> messageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Appointment> appointmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Threadcomment> threadcommentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Userplaylist> userplaylistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Response> responseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Userachievement> userachievementList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Token> tokenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Userinventoryitem> userinventoryitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Threadvote> threadvoteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Commentvote> commentvoteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Usertherapist> usertherapistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Userbadge> userbadgeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Usertasklist> usertasklistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<Thread> threadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Userfavourite> userfavouriteList;

    public Users() {
    }

    public Users(String userid) {
        this.userid = userid;
    }

    public Users(String userid, String username, String userpw, String useremail, Date userbirthday, int coins, int exp, int loginstreak, int userlevel, Boolean diaryvisibility, Boolean forestvisibility, Boolean isdeleted) {
        this.userid = userid;
        this.username = username;
        this.userpw = userpw;
        this.useremail = useremail;
        this.userbirthday = userbirthday;
        this.coins = coins;
        this.exp = exp;
        this.loginstreak = loginstreak;
        this.userlevel = userlevel;
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

    public Serializable getUserimage() {
        return userimage;
    }

    public void setUserimage(Serializable userimage) {
        this.userimage = userimage;
    }

    @XmlTransient
    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    @XmlTransient
    public List<Land> getLandList() {
        return landList;
    }

    public void setLandList(List<Land> landList) {
        this.landList = landList;
    }

    @XmlTransient
    public List<Usertag> getUsertagList() {
        return usertagList;
    }

    public void setUsertagList(List<Usertag> usertagList) {
        this.usertagList = usertagList;
    }

    @XmlTransient
    public List<Focussession> getFocussessionList() {
        return focussessionList;
    }

    public void setFocussessionList(List<Focussession> focussessionList) {
        this.focussessionList = focussessionList;
    }

    @XmlTransient
    public List<Diaryentry> getDiaryentryList() {
        return diaryentryList;
    }

    public void setDiaryentryList(List<Diaryentry> diaryentryList) {
        this.diaryentryList = diaryentryList;
    }

    @XmlTransient
    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @XmlTransient
    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @XmlTransient
    public List<Threadcomment> getThreadcommentList() {
        return threadcommentList;
    }

    public void setThreadcommentList(List<Threadcomment> threadcommentList) {
        this.threadcommentList = threadcommentList;
    }

    @XmlTransient
    public List<Userplaylist> getUserplaylistList() {
        return userplaylistList;
    }

    public void setUserplaylistList(List<Userplaylist> userplaylistList) {
        this.userplaylistList = userplaylistList;
    }

    @XmlTransient
    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    @XmlTransient
    public List<Userachievement> getUserachievementList() {
        return userachievementList;
    }

    public void setUserachievementList(List<Userachievement> userachievementList) {
        this.userachievementList = userachievementList;
    }

    @XmlTransient
    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    @XmlTransient
    public List<Userinventoryitem> getUserinventoryitemList() {
        return userinventoryitemList;
    }

    public void setUserinventoryitemList(List<Userinventoryitem> userinventoryitemList) {
        this.userinventoryitemList = userinventoryitemList;
    }

    @XmlTransient
    public List<Threadvote> getThreadvoteList() {
        return threadvoteList;
    }

    public void setThreadvoteList(List<Threadvote> threadvoteList) {
        this.threadvoteList = threadvoteList;
    }

    @XmlTransient
    public List<Commentvote> getCommentvoteList() {
        return commentvoteList;
    }

    public void setCommentvoteList(List<Commentvote> commentvoteList) {
        this.commentvoteList = commentvoteList;
    }

    @XmlTransient
    public List<Usertherapist> getUsertherapistList() {
        return usertherapistList;
    }

    public void setUsertherapistList(List<Usertherapist> usertherapistList) {
        this.usertherapistList = usertherapistList;
    }

    @XmlTransient
    public List<Userbadge> getUserbadgeList() {
        return userbadgeList;
    }

    public void setUserbadgeList(List<Userbadge> userbadgeList) {
        this.userbadgeList = userbadgeList;
    }

    @XmlTransient
    public List<Usertasklist> getUsertasklistList() {
        return usertasklistList;
    }

    public void setUsertasklistList(List<Usertasklist> usertasklistList) {
        this.usertasklistList = usertasklistList;
    }

    @XmlTransient
    public List<Thread> getThreadList() {
        return threadList;
    }

    public void setThreadList(List<Thread> threadList) {
        this.threadList = threadList;
    }

    @XmlTransient
    public List<Userfavourite> getUserfavouriteList() {
        return userfavouriteList;
    }

    public void setUserfavouriteList(List<Userfavourite> userfavouriteList) {
        this.userfavouriteList = userfavouriteList;
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
