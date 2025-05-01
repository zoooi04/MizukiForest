<%-- 
    Document   : journal-qotd
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

    <section id="journal" class="incsec">
        <%request.setAttribute("pageTitle", "Journal");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <main class="container mt-5 pt-5">
            <%@ page import="java.util.List" %>
            <%@ page import="model.Diaryentry" %>
            <h1>My Journal Entries</h1>

            <c:if test="${not empty diaryEntries}">
                <ul>
                    <c:forEach var="diaryEntry" items="${diaryEntries}">
                        <li>
                            <strong>Title:</strong> ${diaryEntry.diarytitle}<br/>
                            <strong>Mood:</strong> ${diaryEntry.mood}<br/>
                            <strong>Date Written:</strong> ${diaryEntry.datewritten}<br/>
                            <strong>Description:</strong> ${diaryEntry.description}<br/>
                            <br/>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- If no diary entries exist, display a message -->
            <c:if test="${empty diaryEntries}">
                <p>No diary entries found.</p>
            </c:if>
                
                <p>${debugMessage}</p>
        </main>
    </section>

</html>

<script src="../js/mizukibase.js"></script>