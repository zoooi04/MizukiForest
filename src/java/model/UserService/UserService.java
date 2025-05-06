package model.UserService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Userbadge;
import model.Users;

public class UserService {

    @PersistenceContext
    private EntityManager mgr;

    public UserService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addUser(Users user) {
        try {
            mgr.persist(user);
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
            throw e;
        }
    }

    public Users findUserById(String userid) {
        return mgr.find(Users.class, userid);
    }

    public String getLastUserId() {
    TypedQuery<Users> query = mgr.createQuery(
            "SELECT u FROM Users u ORDER BY u.userid DESC", Users.class);
    query.setMaxResults(1);
    List<Users> result = query.getResultList();

    if (result.isEmpty()) {
        return "U2500001";
    } else {
        return result.get(0).getUserid();
    }
}

    public List<Users> findUsersByUsername(String username) {
        TypedQuery<Users> query = mgr.createQuery(
                "SELECT u FROM Users u WHERE u.username = :username", Users.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    public Users findUserByEmail(String email) {
        TypedQuery<Users> query = mgr.createQuery(
                "SELECT u FROM Users u WHERE u.useremail = :email", Users.class);
        query.setParameter("email", email);
        List<Users> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Users> findUsersByIsDeleted(Boolean isdeleted) {
        TypedQuery<Users> query = mgr.createQuery(
                "SELECT u FROM Users u WHERE u.isdeleted = :isdeleted", Users.class);
        query.setParameter("isdeleted", isdeleted);
        return query.getResultList();
    }

    public List<Users> findAllUsers() {
        TypedQuery<Users> query = mgr.createQuery("SELECT u FROM Users u", Users.class);
        return query.getResultList();
    }

    public boolean updateUser(Users updatedUser) {
        Users existingUser = findUserById(updatedUser.getUserid());
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setUserpw(updatedUser.getUserpw());
            existingUser.setUseremail(updatedUser.getUseremail());
            existingUser.setUserbirthday(updatedUser.getUserbirthday());
            existingUser.setCoins(updatedUser.getCoins());
            existingUser.setExp(updatedUser.getExp());
            existingUser.setLoginstreak(updatedUser.getLoginstreak());
            existingUser.setUserlevel(updatedUser.getUserlevel());
            existingUser.setDiaryvisibility(updatedUser.getDiaryvisibility());
            existingUser.setForestvisibility(updatedUser.getForestvisibility());
            existingUser.setPity(updatedUser.getPity());
            existingUser.setLastlogindate(updatedUser.getLastlogindate());
            existingUser.setIsdeleted(updatedUser.getIsdeleted());
            existingUser.setUserimage(updatedUser.getUserimage());
            return true;
        }
        return false;
    }

    public boolean deleteUser(String userid) {
        Users user = findUserById(userid);
        if (user != null) {
            user.setIsdeleted(true);
            return true;
        }
        return false;
    }
    
    public List<Userbadge> getUserBadges(String userId) {
        TypedQuery<Userbadge> query = mgr.createQuery(
                "SELECT ub FROM Userbadge ub JOIN FETCH ub.badge WHERE ub.userbadgePK.userid = :userId AND ub.isdeleted = false",Userbadge.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}