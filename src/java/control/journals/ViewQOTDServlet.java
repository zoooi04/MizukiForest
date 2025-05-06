package control.journals;

import model.Question;
import model.Response;
import model.Users;
import model.journalService.QuestionService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewQOTD")
public class ViewQOTDServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            if (session.getAttribute("isSearch") != null) {
                session.removeAttribute("isSearch");
            }

            // Initialize services
            QuestionService questionService = new QuestionService(mgr);
            ResponseService responseService = new ResponseService(mgr);

            // Fetch active (not deleted) questions
            List<Question> allQuestions = questionService.findAllQuestions();
            List<Question> activeQuestions = new ArrayList<>();
            for (Question question : allQuestions) {
                if (!question.getIsdeleted()) {
                    activeQuestions.add(question);
                }
            }

            Users user = (Users) session.getAttribute("user");
            // if not enter back to login
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            // Fetch user responses
            List<Response> userResponses = responseService.findResponsesByUser(user);

            // Categorize questions
            List<Question> answeredQuestions = new ArrayList<>();
            List<Question> unansweredQuestions = new ArrayList<>();

            for (Question question : activeQuestions) {
                boolean isAnswered = false;
                for (Response res : userResponses) {
                    if (res.getQuestionid().getQuestionid().equals(question.getQuestionid())) {
                        isAnswered = true;
                        break;
                    }
                }

                if (isAnswered) {
                    answeredQuestions.add(question);
                } else {
                    unansweredQuestions.add(question);
                }
            }

            // Set session attributes
            session.setAttribute("answered", answeredQuestions);
            session.setAttribute("unanswered", unansweredQuestions);
            session.setAttribute("userResponses", userResponses);

            // Redirect to view page
            response.sendRedirect(request.getContextPath() + "/view/journals/journal-qotd.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load questions or responses.");
        }
    }
}
