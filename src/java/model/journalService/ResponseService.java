package model.journalService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Response;
import model.Users;
import model.Question;

/**
 *
 * @author JiaQuann
 */
public class ResponseService {

    @PersistenceContext
    private EntityManager mgr;

    public ResponseService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new Response
    public void addResponse(Response response) {
        try {
            mgr.persist(response);
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
            throw e;
        }
    }

    // Find a Response by ID
    public Response findResponseById(String responseId) {
        return mgr.find(Response.class, responseId);
    }

    // Find all Responses
    public List<Response> findAllResponses() {
        TypedQuery<Response> query = mgr.createQuery("SELECT r FROM Response r", Response.class);
        return query.getResultList();
    }

    // Find Responses by Question
    public List<Response> findResponsesByQuestion(Question question) {
        TypedQuery<Response> query = mgr.createQuery("SELECT r FROM Response r WHERE r.questionid = :question", Response.class);
        query.setParameter("question", question);
        return query.getResultList();
    }

    // Find Responses by User
    public List<Response> findResponsesByUser(Users user) {
        TypedQuery<Response> query = mgr.createQuery("SELECT r FROM Response r WHERE r.userid = :user", Response.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    // Delete a Response by ID
    public boolean deleteResponse(String responseId) {
        Response response = findResponseById(responseId);
        if (response != null) {
            mgr.remove(response);
            return true;
        }
        return false;
    }

    // Update a Response
    public boolean updateResponse(Response updatedResponse) {
        Response existingResponse = findResponseById(updatedResponse.getResponseid());
        if (existingResponse != null) {
            existingResponse.setResponsedescription(updatedResponse.getResponsedescription());
            existingResponse.setIsarchived(updatedResponse.getIsarchived());
            existingResponse.setIsdeleted(updatedResponse.getIsdeleted());
            existingResponse.setQuestionid(updatedResponse.getQuestionid());
            existingResponse.setUserid(updatedResponse.getUserid());
            return true;
        }
        return false;
    }

    // Find non-deleted and non-archived responses for a question
    public List<Response> findActiveResponsesByQuestion(Question question) {
        String jpql = "SELECT r FROM Response r WHERE r.questionid = :question AND r.isdeleted = false AND r.isarchived = false";
        return mgr.createQuery(jpql, Response.class)
                .setParameter("question", question)
                .getResultList();
    }
}
