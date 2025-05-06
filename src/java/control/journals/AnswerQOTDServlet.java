package control.journals;

import model.Question;
import model.Response;
import model.Users;
import model.journalService.ResponseService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
/**
 *
 * @author JiaQuann
 */
@WebServlet(name = "AnswerQOTDServlet", urlPatterns = {"/AnswerQOTDServlet"})
public class AnswerQOTDServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    private String generateNextResponseId() {
        try {
            String lastId = em.createQuery(
                    "SELECT r.responseid FROM Response r ORDER BY r.responseid DESC", String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(1));
            return String.format("R%07d", number + 1);
        } catch (Exception e) {
            return "R0000001";
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("isSearch") != null) {
            session.removeAttribute("isSearch");
        }
        
        Users user = (Users) session.getAttribute("user");
        // If user is not logged in, redirect to homepage
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String responseId = generateNextResponseId();
        String questionId = request.getParameter("questionId");
        String answerText = request.getParameter("answerText");

        try {
            utx.begin();

            ResponseService responseService = new ResponseService(em);
            Response responseEntity = new Response();

            responseEntity.setResponseid(responseId);
            responseEntity.setResponsedescription(answerText);
            responseEntity.setIsarchived(false);
            responseEntity.setIsdeleted(false);

            // Retrieve Question object by ID
            Question question = em.find(Question.class, questionId);
            responseEntity.setQuestionid(question);

            // Set the user who submitted the response
            responseEntity.setUserid(user);

            // Save response to database
            responseService.addResponse(responseEntity);

            utx.commit();

            session.setAttribute("message", "Your response has been submitted successfully.");

        } catch (Exception e) {
            try {
                if (utx != null) {
                    utx.rollback();
                }
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            session.setAttribute("errorMessage", e.getMessage());
            session.setAttribute("errorName", e.getClass().getName());
            e.printStackTrace();
        }

        // Redirect back to QOTD view page
        response.sendRedirect(request.getContextPath() + "/viewQOTD");
    }
}
