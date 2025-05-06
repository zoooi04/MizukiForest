<%@page import="model.Threadcategory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <%request.setAttribute("pageTitle", "Forum");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <%@ include file="../../shared/header.jsp" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reported Content - MizukiForest</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            :root {
                --bg-color: #121212;
                --bg-secondary: #242424;
                --bg-form: #2d2d35;
                --text-color: #e0e0e0;
                --text-muted: #9a9a9a;
                --primary-color: #4978a8;
                --primary-hover: #5989b9;
                --border-color: #3d3d45;
                --input-bg: #333340;
                --input-border: #4a4a55;
                --error-color: #dc3545;
                --success-color: #28a745;
                --button-secondary-bg: #444450;
                --button-secondary-hover: #55555f;
                --button-secondary-text: #e0e0e0;
            }

            body {
                background-color: var(--bg-color);
                color: var(--text-color);
                font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
                line-height: 1.6;
                padding-top: 60px;
            }

            .nav-tabs {
                border-bottom: 1px solid var(--border-color);
                margin-bottom: 2rem;
            }

            .nav-tabs .nav-link {
                color: var(--text-muted);
                border: none;
                padding: 1rem 2rem;
                font-weight: 500;
                position: relative;
            }

            .nav-tabs .nav-link:hover {
                color: var(--text-color);
                border: none;
                background: transparent;
            }

            .nav-tabs .nav-link.active {
                color: var(--primary-color);
                background: transparent;
                border: none;
            }

            .nav-tabs .nav-link.active::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                right: 0;
                height: 2px;
                background-color: var(--primary-color);
            }

            .reported-content {
                background: var(--bg-form);
                border-radius: 8px;
                padding: 1.5rem;
                margin-bottom: 1rem;
                border: 1px solid var(--border-color);
            }

            .reported-content-header {
                margin-bottom: 1rem;
            }

            .reported-content-title {
                font-size: 1.25rem;
                font-weight: 600;
                color: var(--text-color);
                margin-bottom: 0.5rem;
            }

            .reported-content-description {
                color: var(--text-muted);
                margin-bottom: 1rem;
            }

            .reported-content-meta {
                font-size: 0.875rem;
                color: var(--text-muted);
                margin-bottom: 1rem;
            }

            .reported-content-actions {
                display: flex;
                justify-content: flex-end;
                gap: 0.75rem;
            }

            .btn {
                padding: 0.5rem 1.5rem;
                border-radius: 4px;
                font-weight: 500;
                transition: all 0.2s ease;
            }

            .btn-normal {
                background-color: var(--success-color);
                color: white;
                border: none;
            }

            .btn-normal:hover {
                background-color: #2fb344;
                color: white;
            }

            .btn-delete {
                background-color: var(--error-color);
                color: white;
                border: none;
            }

            .btn-delete:hover {
                background-color: #e04757;
                color: white;
            }

            .container {
                max-width: 1000px;
                margin: 0 auto;
                padding: 0 1rem;
            }

            .user-info {
                display: flex;
                align-items: center;
                gap: 0.5rem;
                margin-bottom: 0.5rem;
            }

            .user-avatar {
                width: 32px;
                height: 32px;
                border-radius: 50%;
                background: var(--primary-color);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-weight: 600;
            }

            .username {
                font-weight: 500;
                color: var(--primary-color);
            }
        </style>
    </head>
    <body>
        <div class="container">
            <ul class="nav nav-tabs" id="reportedContentTabs">
                <li class="nav-item">
                    <a class="nav-link active" data-bs-toggle="tab" href="#threads">Reported Threads</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="tab" href="#comments">Reported Comments</a>
                </li>
            </ul>

            <div class="tab-content">
                <!-- Reported Threads Tab -->
                <div class="tab-pane fade show active" id="threads">
                    <c:forEach var="thread" items="${reportedThreads}">
                        <form action="${pageContext.request.contextPath}/HandleReportedContentServlet" method="post" class="reported-content">
                            <input type="hidden" name="contentType" value="thread">
                            <input type="hidden" name="contentId" value="${thread.threadid}">

                            <div class="reported-content-header">
                                <div class="reported-content-title">${thread.threadtitle}</div>
                                <div class="reported-content-meta">
                                    Reported by: ${thread.reportedBy} • ${thread.reportDate}
                                </div>
                            </div>
                            <div class="reported-content-description">
                                ${thread.threaddescription}
                            </div>
                            <div class="reported-content-actions">
                                <button type="submit" name="action" value="normal" class="btn btn-normal">Normal</button>
                                <button type="submit" name="action" value="delete" class="btn btn-delete">Delete</button>
                            </div>
                        </form>
                    </c:forEach>
                </div>

                <!-- Reported Comments Tab -->
                <div class="tab-pane fade" id="comments">
                    <c:forEach var="comment" items="${reportedComments}">
                        <form action="${pageContext.request.contextPath}/HandleReportedContentServlet" method="post" class="reported-content">
                            <input type="hidden" name="contentType" value="comment">
                            <input type="hidden" name="contentId" value="${comment.commentid}">

                            <div class="user-info">
                                <div class="user-avatar">
                                    ${comment.username.charAt(0)}
                                </div>
                                <div class="username">${comment.username}</div>
                            </div>
                            <div class="reported-content-description">
                                ${comment.commentdescription}
                            </div>
                            <div class="reported-content-meta">
                                Reported by: ${comment.reportedBy} • ${comment.reportDate}
                            </div>
                            <div class="reported-content-actions">
                                <button type="submit" name="action" value="normal" class="btn btn-normal">Normal</button>
                                <button type="submit" name="action" value="delete" class="btn btn-delete">Delete</button>
                            </div>
                        </form>
                    </c:forEach>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Initialize Bootstrap tabs
                const triggerTabList = [].slice.call(document.querySelectorAll('#reportedContentTabs a'));
                triggerTabList.forEach(function (triggerEl) {
                    const tabTrigger = new bootstrap.Tab(triggerEl);
                    triggerEl.addEventListener('click', function (event) {
                        event.preventDefault();
                        tabTrigger.show();
                    });
                });

                // Handle form submissions
                document.querySelectorAll('form').forEach(form => {
                    form.addEventListener('submit', function (e) {
                        const action = e.submitter.value;
                        if (action === 'delete') {
                            if (!confirm('Are you sure you want to delete this content?')) {
                                e.preventDefault();
                            }
                        }
                    });
                });
            });
        </script>
    </body>
</html>