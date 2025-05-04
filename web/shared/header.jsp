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

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/view/trees.jsp">
                    <!--users-three phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("trees") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M128,187.85a72.44,72.44,0,0,0,8,4.62V232a8,8,0,0,1-16,0V192.47A72.44,72.44,0,0,0,128,187.85ZM198.1,62.59a76,76,0,0,0-140.2,0A71.71,71.71,0,0,0,16,127.8C15.9,166,48,199,86.14,200A72.22,72.22,0,0,0,120,192.47V156.94L76.42,135.16a8,8,0,1,1,7.16-14.32L120,139.06V88a8,8,0,0,1,16,0v27.06l36.42-18.22a8,8,0,1,1,7.16,14.32L136,132.94v59.53A72.17,72.17,0,0,0,168,200l1.82,0C208,199,240.11,166,240,127.8A71.71,71.71,0,0,0,198.1,62.59Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("trees") ? "active" : ""%>">Trees</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/ViewDateDiaryEntryServlet">
                    <!--book-bookmark phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("journal") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M208,24H72A32,32,0,0,0,40,56V224a8,8,0,0,0,8,8H192a8,8,0,0,0,0-16H56a16,16,0,0,1,16-16H208a8,8,0,0,0,8-8V32A8,8,0,0,0,208,24Zm-24,96-25.61-19.2a4,4,0,0,0-4.8,0L128,120V40h56Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("journal") ? "active" : ""%>">Journal</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="#">
                    <!--house phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" viewBox="0 0 256 256"><path d="M224,120v96a8,8,0,0,1-8,8H160a8,8,0,0,1-8-8V164a4,4,0,0,0-4-4H108a4,4,0,0,0-4,4v52a8,8,0,0,1-8,8H40a8,8,0,0,1-8-8V120a16,16,0,0,1,4.69-11.31l80-80a16,16,0,0,1,22.62,0l80,80A16,16,0,0,1,224,120Z"></path></svg>
                    <p class="small m-0">Home</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/ViewForumEntryServlet">
                    <!--users-three phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("forum") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M64.12,147.8a4,4,0,0,1-4,4.2H16a8,8,0,0,1-7.8-6.17,8.35,8.35,0,0,1,1.62-6.93A67.79,67.79,0,0,1,37,117.51a40,40,0,1,1,66.46-35.8,3.94,3.94,0,0,1-2.27,4.18A64.08,64.08,0,0,0,64,144C64,145.28,64,146.54,64.12,147.8Zm182-8.91A67.76,67.76,0,0,0,219,117.51a40,40,0,1,0-66.46-35.8,3.94,3.94,0,0,0,2.27,4.18A64.08,64.08,0,0,1,192,144c0,1.28,0,2.54-.12,3.8a4,4,0,0,0,4,4.2H240a8,8,0,0,0,7.8-6.17A8.33,8.33,0,0,0,246.17,138.89Zm-89,43.18a48,48,0,1,0-58.37,0A72.13,72.13,0,0,0,65.07,212,8,8,0,0,0,72,224H184a8,8,0,0,0,6.93-12A72.15,72.15,0,0,0,157.19,182.07Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("forum") ? "active" : ""%>">Forum</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/view/music.jsp">
                    <!--music-notes phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("music") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M212.92,17.71a7.89,7.89,0,0,0-6.86-1.46l-128,32A8,8,0,0,0,72,56V166.1A36,36,0,1,0,88,196V102.25l112-28V134.1A36,36,0,1,0,216,164V24A8,8,0,0,0,212.92,17.71Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("music") ? "active" : ""%>">Music</p>
                </a>

                <a class="text-decoration-none text-dark text-center" style="padding: 0 9px;" href="<%= request.getContextPath()%>/view/profile.jsp">
                    <!--identification-card phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" class="<%= request.getRequestURI().contains("profile") ? "svgactive" : ""%>" viewBox="0 0 256 256">
                        <path d="M112,120a16,16,0,1,1-16-16A16,16,0,0,1,112,120ZM232,56V200a16,16,0,0,1-16,16H40a16,16,0,0,1-16-16V56A16,16,0,0,1,40,40H216A16,16,0,0,1,232,56ZM135.75,166a39.76,39.76,0,0,0-17.19-23.34,32,32,0,1,0-45.12,0A39.84,39.84,0,0,0,56.25,166a8,8,0,0,0,15.5,4c2.64-10.25,13.06-18,24.25-18s21.62,7.73,24.25,18a8,8,0,1,0,15.5-4ZM200,144a8,8,0,0,0-8-8H152a8,8,0,0,0,0,16h40A8,8,0,0,0,200,144Zm0-32a8,8,0,0,0-8-8H152a8,8,0,0,0,0,16h40A8,8,0,0,0,200,112Z"></path>
                    </svg>
                    <p class="small m-0 <%= request.getRequestURI().contains("profile") ? "active" : ""%>">Profile</p>
                </a>
            </div>

            <!-- Right：More -->
            <div class="d-flex flex-row gap-1 ms-auto">
                <a class="text-decoration-none text-dark text-center bluehover" style="padding: 2px 9px 0 9px;" href="javascript:history.back()">
                    <!--list phosphor icons-->
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#888783" viewBox="0 0 256 256">
                        <path d="M221.8,175.94C216.25,166.38,208,139.33,208,104a80,80,0,1,0-160,0c0,35.34-8.26,62.38-13.81,71.94A16,16,0,0,0,48,200H88.81a40,40,0,0,0,78.38,0H208a16,16,0,0,0,13.8-24.06ZM128,216a24,24,0,0,1-22.62-16h45.24A24,24,0,0,1,128,216Z"></path>
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
                        <li><a class="dropdown-item" href="#">Profile</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/view/settings.jsp">Settings</a></li>
                        <li><a class="dropdown-item" href="#">Logout</a></li>
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
                        <a class="nav-link <%= (request.getRequestURI().contains("journal-diaries") || request.getRequestURI().contains("journal-diary")) ? "active" : ""%>" href="<%= request.getContextPath()%>/view/journals/journal-diaries.jsp">Diaries</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= request.getRequestURI().contains("journal-qotd") ? "active" : ""%>" href="<%= request.getContextPath()%>/view/journals/journal-qotd.jsp">QOTD</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</c:if>