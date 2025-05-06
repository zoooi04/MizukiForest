<%-- 
    Document   : callsession
    Created on : 5 May 2025, 9:23:08 am
    Author     : huaiern
--%>
<%@ page import="java.net.URLEncoder" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mizukiuser.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/callSession.css">

        <link href="https://fonts.googleapis.com/css2?family=Silkscreen:wght@400;700&display=swap" rel="stylesheet">
        
        <style>

        </style>
    </head>
    <body>
        <%request.setAttribute("pageTitle", "Home");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>
        
        <%
            String role = (String) session.getAttribute("role");
        %>
        
        <section id="sect-callsession">
            <div class="center-layout container d-flex justify-content-center py-5 flex-wrap">
                <div class="row w-100 mx-0 gy-4 filter">
                    <a class="btn-theme col-2 text-decoration-none text-white d-block" href="${pageContext.request.contextPath}/view/therapy/therapistRoom.jsp">Back to Chat</a>
                </div>
                <div class="row w-100 mb-5 mt-5">
                    <div class="col-12 text-center">Please be patient while waiting for the other participant.</div>

                </div>
                <div class="row w-100 ">
                    <%
                        String room = request.getParameter("room");
                        if (room == null || room.trim().isEmpty()) {
                    %>
                    <p>Invalid or missing meeting link.</p>
                    <%
                    } else {
                        room = URLEncoder.encode(room, "UTF-8");
                    %>
                    <div class="col-12 d-flex justify-content-center">
                        <iframe  
                            class="meet"
                            src="https://meet.jit.si/<%= room%>" 
                            allow="camera; microphone; fullscreen; display-capture" >
                        </iframe>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </section>
        <script src="https://code.jquery.com/jquery-3.7.1.js"
                integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
        crossorigin="anonymous"></script>
        <script src="../../js/mizukibase.js"></script>

    </body>
</html>
