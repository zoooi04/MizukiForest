
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Forum Only-->
<c:if test="${pageContext.request.requestURI.contains('forum')}">
    <nav class="navbar navbar-expand-lg second-header">
        <div class="container d-flex justify-content-center align-items-center navpad">
            <!-- Center：Button -->
            <div class="d-flex flex-row ">
                <ul class="nav nav-pills gap-3">
                    <li class="nav-item">
                        <a class="nav-link <%= request.getRequestURI().contains("forum-thread-list") && "all".equals(request.getParameter("threadType")) ? "active" : ""%>" href="<%= request.getContextPath()%>/ViewForumEntryServlet?threadType=all&category=all&vote=all">All Thread</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= request.getRequestURI().contains("forum-thread-list-my") || "my".equals(request.getParameter("threadType")) ? "active" : ""%>" href="<%= request.getContextPath()%>/ViewForumEntryServlet?threadType=my&category=all&vote=all">My Thread</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</c:if>

<!-- Bootstrap JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>