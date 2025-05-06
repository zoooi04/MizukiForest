<%-- 
    Document   : forest main
    Created on : Mar 6, 2025, 9:52:45 AM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bootstrap Navigation Bar -->

<div class="nav-mar">
    <nav class="navbar navbar-expand-lg top-header">
        <div class="container d-flex justify-content-between align-items-center navpad">
            <!-- Left：Logo -->
            <div class="align-items-center me-auto flex-grow-0">
                <a class="flex-shrink-0" style="text-decoration: none;" href="<%= request.getContextPath()%>/index.jsp">
                    <img class="h-6"
                         src="<%= request.getContextPath()%>/media/images/mizuki.png" width="35">
                </a>
                <a class="btn btn-sm btn-ghost rounded-lg logo-font" href="<%= request.getContextPath()%>/index.jsp">Mizuki's Forest</a>
            </div>

            <!-- Center：Button -->
            <div class="d-flex flex-row gap-1 mx-auto">
                <a class="text-decoration-none text-dark text-center bluehover" style="padding: 0 9px;" href="javascript:history.back()">
                    <!--arrow-bend-up-left phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" viewBox="0 0 256 256"><path d="M232,200a8,8,0,0,1-16,0,88.1,88.1,0,0,0-88-88H88v40a8,8,0,0,1-13.66,5.66l-48-48a8,8,0,0,1,0-11.32l48-48A8,8,0,0,1,88,56V96h40A104.11,104.11,0,0,1,232,200Z"></path></svg>
                    <p class="small m-0">Back</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/ViewDateDiaryEntryServlet">
                    <!--book-bookmark phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("journal") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M208,24H72A32,32,0,0,0,40,56V224a8,8,0,0,0,8,8H192a8,8,0,0,0,0-16H56a16,16,0,0,1,16-16H208a8,8,0,0,0,8-8V32A8,8,0,0,0,208,24Zm-24,96-25.61-19.2a4,4,0,0,0-4.8,0L128,120V40h56Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("journal") ? "active" : ""%>">Journal</p>
                </a>

                
                <a class="text-decoration-none text-dark text-center" id="forestNavButton" style="padding: 0 9px;" href="<%= request.getContextPath()%>/GetUserForestData">
                    <!--users-three phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("forest") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M128,187.85a72.44,72.44,0,0,0,8,4.62V232a8,8,0,0,1-16,0V192.47A72.44,72.44,0,0,0,128,187.85ZM198.1,62.59a76,76,0,0,0-140.2,0A71.71,71.71,0,0,0,16,127.8C15.9,166,48,199,86.14,200A72.22,72.22,0,0,0,120,192.47V156.94L76.42,135.16a8,8,0,1,1,7.16-14.32L120,139.06V88a8,8,0,0,1,16,0v27.06l36.42-18.22a8,8,0,1,1,7.16,14.32L136,132.94v59.53A72.17,72.17,0,0,0,168,200l1.82,0C208,199,240.11,166,240,127.8A71.71,71.71,0,0,0,198.1,62.59Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("forest") ? "active" : ""%>">Forest</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/ViewForumEntryServlet">
                    <!--users-three phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("forum") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M64.12,147.8a4,4,0,0,1-4,4.2H16a8,8,0,0,1-7.8-6.17,8.35,8.35,0,0,1,1.62-6.93A67.79,67.79,0,0,1,37,117.51a40,40,0,1,1,66.46-35.8,3.94,3.94,0,0,1-2.27,4.18A64.08,64.08,0,0,0,64,144C64,145.28,64,146.54,64.12,147.8Zm182-8.91A67.76,67.76,0,0,0,219,117.51a40,40,0,1,0-66.46-35.8,3.94,3.94,0,0,0,2.27,4.18A64.08,64.08,0,0,1,192,144c0,1.28,0,2.54-.12,3.8a4,4,0,0,0,4,4.2H240a8,8,0,0,0,7.8-6.17A8.33,8.33,0,0,0,246.17,138.89Zm-89,43.18a48,48,0,1,0-58.37,0A72.13,72.13,0,0,0,65.07,212,8,8,0,0,0,72,224H184a8,8,0,0,0,6.93-12A72.15,72.15,0,0,0,157.19,182.07Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("forum") ? "active" : ""%>">Forum</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/MusicServlet">
                    <!--music-notes phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("music") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M212.92,17.71a7.89,7.89,0,0,0-6.86-1.46l-128,32A8,8,0,0,0,72,56V166.1A36,36,0,1,0,88,196V102.25l112-28V134.1A36,36,0,1,0,216,164V24A8,8,0,0,0,212.92,17.71Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("music") ? "active" : ""%>">Music</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/LoadTherapistServlet">
                    <!--identification-card phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("therapy") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M240,160a32,32,0,1,0-39.93,31,8,8,0,0,0-.07,1,32,32,0,0,1-32,32H144a32,32,0,0,1-32-32V151.48c31.47-4,56-31.47,56-64.31V40a8,8,0,0,0-8-8H136a8,8,0,0,0,0,16h16V87.17c0,26.58-21.25,48.49-47.36,48.83A48,48,0,0,1,56,88V48H72a8,8,0,0,0,0-16H48a8,8,0,0,0-8,8V88a64,64,0,0,0,56,63.49V192a48.05,48.05,0,0,0,48,48h24a48.05,48.05,0,0,0,48-48,8,8,0,0,0-.07-1A32,32,0,0,0,240,160Zm-32,8a8,8,0,1,1,8-8A8,8,0,0,1,208,168Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("therapy") ? "active" : ""%>">Therapy</p>
                </a>
            </div>

            <!-- Right：More -->
            <div class="d-flex flex-row gap-1 ms-auto">
                <a class="text-decoration-none text-dark text-center bluehover" style="padding: 2px 9px 0 9px;" href="${pageContext.request.contextPath}/AchievementServlet">
                    <!-- trophy phosphor icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#888783" viewBox="0 0 256 256">
                        <path d="M232,56H208V48a16,16,0,0,0-16-16H64A16,16,0,0,0,48,48v8H24A16,16,0,0,0,8,72v16a48.05,48.05,0,0,0,40,47.41,88.13,88.13,0,0,0,63.5,54.23A40,40,0,0,0,104,224H88a8,8,0,0,0,0,16h80a8,8,0,0,0,0-16H152a40,40,0,0,0-7.5-34.36A88.13,88.13,0,0,0,208,135.41,48.05,48.05,0,0,0,248,88V72A16,16,0,0,0,232,56ZM32,88V72H48v32.78A32,32,0,0,1,32,88Zm192,0a32,32,0,0,1-16,28.78V72h16Z"></path>
                    </svg>
                </a>
                <div class="dropdown">
                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false" style="padding: 0 9px; display: flex; align-items: center; text-decoration: none;">
                        <!--list phosphor icons-->
                        <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="#888783" viewBox="0 0 256 256">
                            <path d="M228,128a12,12,0,0,1-12,12H40a12,12,0,0,1,0-24H216A12,12,0,0,1,228,128ZM40,76H216a12,12,0,0,0,0-24H40a12,12,0,0,0,0,24ZM216,180H40a12,12,0,0,0,0,24H216a12,12,0,0,0,0-24Z"></path>
                        </svg>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/getUserProfileDetails">Profile</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</div>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Journal Only-->
<c:if test="${pageContext.request.requestURI.contains('journal')}">
    <nav class="navbar navbar-expand-lg second-header">
        <div class="container d-flex justify-content-center align-items-center navpad">
            <!-- Center：Button -->
            <div class="d-flex flex-row ">
                <ul class="nav nav-pills gap-3">
                    <li class="nav-item">
                        <a class="nav-link <%= (request.getRequestURI().contains("journal-diaries") || request.getRequestURI().contains("journal-diary")) ? "active" : ""%>" href="<%= request.getContextPath()%>/ViewDateDiaryEntryServlet">Diaries</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= request.getRequestURI().contains("journal-mood") ? "active" : ""%>" href="<%= request.getContextPath()%>/ViewMoodChartServlet">Mood Chart</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= request.getRequestURI().contains("journal-qotd") ? "active" : ""%>" href="<%= request.getContextPath()%>/viewQOTD">QOTD</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</c:if>
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
<c:if test="${pageContext.request.requestURI.contains('music')}">
    <nav class="navbar navbar-expand-lg second-header">
        <div class="container d-flex justify-content-center align-items-center navpad">
            <!-- Center：Button -->
            <div class="d-flex flex-row ">
                <ul class="nav nav-pills gap-3">
                    <li class="nav-item">
                        <a class="nav-link <%= (request.getRequestURI().contains("MusicPopular") || request.getRequestURI().contains("journal-diary")) ? "active" : ""%>"  href="${pageContext.request.contextPath}/MusicServlet">Popular Music</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= request.getRequestURI().contains("UserQueue") ? "active" : ""%>" href="${pageContext.request.contextPath}/UserQueueServlet">Queue</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= request.getRequestURI().contains("MusicPlaylist") ? "active" : ""%>" href="${pageContext.request.contextPath}/UserPlaylistServlet">Playlist</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</c:if>
<!-- Bootstrap JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
