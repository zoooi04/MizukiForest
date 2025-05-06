<%@ page import="java.util.List" %>
<%@ page import="model.Threadcategory" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <head>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum/forum_thread_list.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum/forum_new_thread.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>

    <%request.setAttribute("pageTitle", "Thread Detail");%>
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <jsp:include page="../../shared/title.jsp"/>
    <%@ include file="../../shared/header.jsp" %>

    <body>
        <div class="container">
            <div class="forum-section">
                <div class="forum-header">
                    <a href="<%= request.getContextPath()%>/view/forum/forum-thread-list.jsp?threadType=my" class="back-link">
                        <i class="fas fa-arrow-left"></i> Back to Forum
                    </a>
                    <h1 class="text-3xl font-bold text-center my-6">Create New Thread</h1>
                </div>

                <%-- Display error message if present --%>
                <% if (request.getParameter("error") != null) {%>
                <div class="error-message">
                    <p><%= request.getParameter("error")%></p>
                </div>
                <% }%>

                <div class="new-thread-container">
                    <form id="newThreadForm" action="../../AddForumThreadServlet" method="post">
                        <div class="form-group">
                            <label for="threadTitle">Title</label>
                            <input type="text" id="threadTitle" name="threadTitle" placeholder="Enter thread title" required>
                        </div>

                        <div class="form-group">
                            <label for="threadDescription">Description</label>
                            <textarea id="threadDescription" name="threadDescription" placeholder="Enter thread description" required></textarea>
                        </div>

                        <div class="form-group">
                            <label for="threadCategory">Category</label>
                            <select id="threadCategory" name="categoryId" required>
                                <option value="" disabled selected>Select a category</option>
                                <% List<Threadcategory> categories = (List<Threadcategory>) session.getAttribute("forumThreadCategoryList");
                                   if (categories != null) {
                                       for (Threadcategory category : categories) { %>
                                           <option value="<%= category.getThreadcategoryid() %>"><%= category.getThreadcategoryname() %></option>
                                       <% }
                                   } %>
                            </select>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn-create">Create Thread</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </body>
    <script src="<%= request.getContextPath()%>/js/forum/forum_new_thread.js"></script>
    <script>
        document.getElementById('newThreadForm').addEventListener('submit', function(event) {
            const titleInput = document.getElementById('threadTitle');
            if (!titleInput.value.trim()) {
                alert('Thread title is required.');
                event.preventDefault();
            }
        });
    </script>
</html>