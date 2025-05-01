<%-- 
    Document   : trees
    Created on : Mar 6, 2025, 4:32:58 PM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_light">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath()%>/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/mizukiuser.css">
    </head>
    
    <section id="tree" class="">
        <%request.setAttribute("pageTitle", "Trees");%>
        <jsp:include page="../shared/title.jsp"/>
        <%@ include file="../shared/header.jsp" %>
        
        <main class="container mt-5 pt-5">
            <h1>Hello World!</h1>
        </main>
    </section>
   
</html>

<script src="../js/mizukibase.js"></script>