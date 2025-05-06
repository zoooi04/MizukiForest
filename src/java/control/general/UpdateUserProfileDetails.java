package control.general;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

import model.Users;
import model.UserService.UserService;

@WebServlet(name = "UpdateUserProfileDetails", urlPatterns = {"/UpdateUserProfileDetails"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024, // 5MB
        maxRequestSize = 10 * 1024 * 1024)  // 10MB
public class UpdateUserProfileDetails extends HttpServlet {

    @PersistenceContext
    EntityManager mgr;

    @Resource
    UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService(mgr);
        Users user = (Users) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            String username = request.getParameter("username");
            String birthdayStr = request.getParameter("userbirthday");
            String diaryVisibility = request.getParameter("diaryvisibility");
            String forestVisibility = request.getParameter("forestvisibility");
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // Parse birthday
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sdf.parse(birthdayStr);

            Part filePart = request.getPart("imageFile"); // input name="imageFile"

            // Start transaction
            utx.begin();
            Users u = mgr.find(Users.class, user.getUserid());

            String removeImage = request.getParameter("removeImage");

// 如果用户请求移除图片，则设为空
            if ("true".equals(removeImage)) {
                u.setUserimage("");
            } else if (filePart != null && filePart.getSize() > 0) {
                // 只在用户上传了图片时处理上传
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                if (!originalFileName.isEmpty()) {
                    String fileExtension = "";
                    int dotIndex = originalFileName.lastIndexOf('.');
                    if (dotIndex >= 0) {
                        fileExtension = originalFileName.substring(dotIndex);
                    }

                    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

                    String uploadDir = getServletContext().getRealPath("/media/uploads/");
                    File uploadDirFile = new File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                    }

                    String fullPath = Paths.get(uploadDir, uniqueFileName).toString();
                    try (InputStream input = filePart.getInputStream()) {
                        File file = new File(fullPath);
                        try (OutputStream output = new FileOutputStream(file)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = input.read(buffer)) != -1) {
                                output.write(buffer, 0, bytesRead);
                            }
                        }
                    }

                    u.setUserimage(uniqueFileName);
                }
            }

            u.setUsername(username);
            u.setUserbirthday(birthday);
            u.setDiaryvisibility(diaryVisibility != null);
            u.setForestvisibility(forestVisibility != null);

            boolean isChangingPassword
                    = currentPassword != null && !currentPassword.isEmpty()
                    && newPassword != null && !newPassword.isEmpty()
                    && confirmPassword != null && !confirmPassword.isEmpty();

            if (isChangingPassword) {
                if (!user.getUserpw().equals(currentPassword)) {
                    throw new Exception("Current password is incorrect.");
                }
                if (!newPassword.equals(confirmPassword)) {
                    throw new Exception("New passwords do not match.");
                }
                u.setUserpw(newPassword);
            }

            mgr.merge(u);
            utx.commit();

            // Update session
            request.getSession().setAttribute("user", u);

            response.sendRedirect(request.getContextPath() + "/getUserProfileDetails");

        } catch (Exception ex) {
            ex.printStackTrace();
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/getUserProfileDetails");
        }
    }
}
