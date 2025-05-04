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

                <div class="new-thread-container">
                    <form id="newThreadForm" action="<%= request.getContextPath()%>/forum/create" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="threadTitle">Title</label>
                            <input type="text" id="threadTitle" name="threadTitle" required placeholder="Enter thread title">
                        </div>

                        <div class="form-group">
                            <label for="threadDescription">Description</label>
                            <textarea id="threadDescription" name="threadDescription" required placeholder="Enter thread description"></textarea>
                        </div>

                        <div class="form-group">
                            <label>Images</label>
                            <div class="image-upload-container">
                                <div class="upload-area" id="uploadArea">
                                    <i class="fas fa-cloud-upload-alt"></i>
                                    <p>Drag & drop images here or</p>
                                    <label for="imageUpload" class="file-upload-btn">Select Images</label>
                                    <input type="file" id="imageUpload" name="threadImages" accept="image/*" multiple>
                                </div>

                                <div class="image-preview-container" id="imagePreviewContainer">
                                    <!-- Preview images will be dynamically added here -->
                                </div>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="button" class="btn-cancel" onclick="window.location.href = '<%= request.getContextPath()%>/view/forum/forum-thread-list.jsp?threadType=my'">Cancel</button>
                            <button type="submit" class="btn-create">Create Thread</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </body>
    <script src="<%= request.getContextPath()%>/js/forum/forum_new_thread.js"></script>
</html>