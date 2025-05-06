package control.journals;

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
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 *
 * @author JiaQuann
 */
@WebServlet(name = "EditAnswerQOTDServlet", urlPatterns = {"/EditAnswerQOTDServlet"})
public class EditAnswerQOTDServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        // Redirect to index if user is not logged in
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String responseId = request.getParameter("responseId");
        String answerText = request.getParameter("answerText");
        String isArchivedParam = request.getParameter("isArchived");
        boolean isArchived = Boolean.parseBoolean(isArchivedParam);

        try {
            utx.begin();

            // Retrieve the existing response object
            Response existingResponse = em.find(Response.class, responseId);
            if (existingResponse == null) {
                throw new Exception("Response not found.");
            }

            // Update the answer and archive status
            existingResponse.setResponsedescription(answerText);
            existingResponse.setIsarchived(isArchived);

            // Update the response in the database
            ResponseService responseService = new ResponseService(em);
            responseService.updateResponse(existingResponse);

            utx.commit();

            // Set success message
            session.setAttribute("message", "Your answer has been updated successfully.");

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

        // Redirect to view QOTD
        response.sendRedirect(request.getContextPath() + "/viewQOTD");
    }
}
