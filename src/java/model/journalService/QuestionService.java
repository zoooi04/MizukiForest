package model.journalService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.Question;

/**
 *
 * @author JiaQuann
 */
public class QuestionService {

    @PersistenceContext
    private EntityManager mgr;

    // Constructor
    public QuestionService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add a new Question
    public void addQuestion(Question question) {
        try {
            mgr.persist(question);
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Validation error: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
            throw e;
        }
    }

    // Find a Question by ID
    public Question findQuestionById(String questionid) {
        return mgr.find(Question.class, questionid);
    }

    // Find Questions by description
    public List<Question> findQuestionsByDescription(String description) {
        TypedQuery<Question> query = mgr.createQuery(
            "SELECT q FROM Question q WHERE q.questiondescription = :description", Question.class);
        query.setParameter("description", description);
        return query.getResultList();
    }

    // Find all Questions
    public List<Question> findAllQuestions() {
        TypedQuery<Question> query = mgr.createQuery("SELECT q FROM Question q", Question.class);
        return query.getResultList();
    }

    // Update an existing Question
    public boolean updateQuestion(Question updatedQuestion) {
        Question existingQuestion = findQuestionById(updatedQuestion.getQuestionid());
        if (existingQuestion != null) {
            existingQuestion.setQuestiondescription(updatedQuestion.getQuestiondescription());
            existingQuestion.setIsarchived(updatedQuestion.getIsarchived());
            existingQuestion.setIsdeleted(updatedQuestion.getIsdeleted());
            return true;
        }
        return false;
    }

    // Delete (soft delete) a Question by ID
    public boolean deleteQuestion(String questionid) {
        Question question = findQuestionById(questionid);
        if (question != null) {
            question.setIsdeleted(true);
            return true;
        }
        return false;
    }

    // Find active (not deleted) Questions
    public List<Question> findActiveQuestions() {
        TypedQuery<Question> query = mgr.createQuery(
            "SELECT q FROM Question q WHERE q.isdeleted = false", Question.class);
        return query.getResultList();
    }

    // Find archived Questions
    public List<Question> findArchivedQuestions() {
        TypedQuery<Question> query = mgr.createQuery(
            "SELECT q FROM Question q WHERE q.isarchived = true", Question.class);
        return query.getResultList();
    }
}
