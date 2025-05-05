package control.forum;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import model.Thread;
import model.Users;
import model.Threadcategory;
import model.forumService.ThreadService;

@MultipartConfig
public class AddForumThreadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManagerFactory emf = null;
        EntityManager mgr = null;

        try {
            // Initialize JPA manually
            emf = Persistence.createEntityManagerFactory("ThreadPU"); // Replace with your actual persistence-unit name
            mgr = emf.createEntityManager();

            String threadTitle = getStringFromPart(request.getPart("threadTitle"));
            String threadDescription = getStringFromPart(request.getPart("threadDescription"));
            String categoryId = getStringFromPart(request.getPart("categoryId"));
            String userId = "U2500001"; // Replace with session user later

            System.out.println("threadTitle = " + threadTitle);
            System.out.println("threadDescription = " + threadDescription);
            System.out.println("categoryId = " + categoryId);
            System.out.println("userId = " + userId);

            if (threadTitle == null || threadTitle.trim().isEmpty())
                throw new IllegalArgumentException("Thread title is required.");
            if (threadDescription == null || threadDescription.trim().isEmpty())
                throw new IllegalArgumentException("Thread description is required.");
            if (categoryId == null || categoryId.trim().isEmpty())
                throw new IllegalArgumentException("Category ID is required.");
            if (userId == null || userId.trim().isEmpty())
                throw new IllegalArgumentException("User ID is required.");

            Users user = mgr.find(Users.class, userId);
            if (user == null) throw new IllegalArgumentException("User not found: " + userId);

            Threadcategory category = mgr.find(Threadcategory.class, categoryId);
            if (category == null) throw new IllegalArgumentException("Category not found: " + categoryId);

            ThreadService threadService = new ThreadService(mgr);

            Thread newThread = new Thread();
            newThread.setThreadid(threadService.generateNextThreadId());
            newThread.setThreadtitle(threadTitle);
            newThread.setThreaddescription(threadDescription);
            newThread.setUserid(user);
            newThread.setThreadcategoryid(category);
            newThread.setUpvote(0);
            newThread.setDownvote(0);
            newThread.setSharecount(0);
            newThread.setIsdeleted(false);

            System.out.println("Thread object before save:");
            System.out.println("ID: " + newThread.getThreadid());
            System.out.println("Title: " + newThread.getThreadtitle());
            System.out.println("Desc: " + newThread.getThreaddescription());
            System.out.println("User: " + newThread.getUserid());
            System.out.println("Category: " + newThread.getThreadcategoryid());

            // Begin transaction and save
            mgr.getTransaction().begin();
            threadService.addThread(newThread);
            mgr.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/view/forum/forum-thread-list.jsp?threadType=my");

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Unable to create thread: " + e.getClass().getName() +
                    (e.getMessage() != null ? " - " + e.getMessage() : "");

            response.sendRedirect(request.getContextPath() + "/view/forum/forum-add-thread-form.jsp"
                + "?error=" + URLEncoder.encode(errorMessage, "UTF-8")
                + "&title=" + URLEncoder.encode(getSafeParam(request, "threadTitle"), "UTF-8")
                + "&description=" + URLEncoder.encode(getSafeParam(request, "threadDescription"), "UTF-8")
                + "&categoryId=" + URLEncoder.encode(getSafeParam(request, "categoryId"), "UTF-8"));
        } finally {
            if (mgr != null && mgr.isOpen()) mgr.close();
            if (emf != null && emf.isOpen()) emf.close();
        }
    }

    private String getStringFromPart(Part part) throws IOException {
        if (part == null) return null;
        InputStream inputStream = part.getInputStream();
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, bytesRead));
        }

        return sb.toString().trim();
    }

    private String getSafeParam(HttpServletRequest request, String name) {
        try {
            Part part = request.getPart(name);
            return part != null ? getStringFromPart(part) : "";
        } catch (Exception e) {
            return "";
        }
    }
}
