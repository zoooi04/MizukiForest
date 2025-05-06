<%-- 
    Document   : journal-diary-view
    Created on : Apr 27, 2025, 12:02:06 AM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Diaryentry" %>

<%
    String mode = request.getParameter("mode");
    if (mode == null) {
        mode = "view";
    }
    Diaryentry diaryEntry = (Diaryentry) request.getAttribute("diaryEntry");
    Boolean isArchived = diaryEntry.getIsarchived();
    Date diaryDate = diaryEntry.getDatewritten();
    String formattedDate = "";
    String urlDate = "";
    if (diaryDate != null) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd, MMM yyyy");
        SimpleDateFormat outputFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = outputFormat.format(diaryDate);
        urlDate = outputFormat2.format(diaryDate);
    }

%>

<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/journal-diaries_view.css">
    <section id="diary" class="incsec">
        <%request.setAttribute("pageTitle", "Diary Entries");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <main class="container mt-5 pt-5">
            <div class="content">
                <div class="card">
                    <div class="card-header">
                        <div>
                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#000000" viewBox="0 0 256 256">
                            <path d="M208,24H72A32,32,0,0,0,40,56V224a8,8,0,0,0,8,8H192a8,8,0,0,0,0-16H56a16,16,0,0,1,16-16H208a8,8,0,0,0,8-8V32A8,8,0,0,0,208,24Zm-24,96-25.61-19.2a4,4,0,0,0-4.8,0L128,120V40h56Z"></path>
                            </svg>
                            &nbsp;${diaryEntry.diarytitle}
                        </div>
                        <!-- Badge div -->
                        <div class="private-badge" onclick="document.getElementById('archiveForm').submit();" style="cursor:pointer;">
                            <%= isArchived ? "UNARCHIVED" : "ARCHIVED"%>&nbsp;
                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#ffffff" viewBox="0 0 256 256">
                            <% if (!isArchived) { %>
                            <path d="M208,32H48A16,16,0,0,0,32,48V208a16,16,0,0,0,16,16H208a16,16,0,0,0,16-16V48A16,16,0,0,0,208,32ZM90.34,114.34a8,8,0,0,1,11.32,0L120,132.69V72a8,8,0,0,1,16,0v60.69l18.34-18.35a8,8,0,0,1,11.32,11.32l-32,32a8,8,0,0,1-11.32,0l-32-32A8,8,0,0,1,90.34,114.34ZM208,208H48V168H76.69L96,187.32A15.89,15.89,0,0,0,107.31,192h41.38A15.86,15.86,0,0,0,160,187.31L179.31,168H208v40Z"></path>
                            <% } else { %>
                            <path d="M208,32H48A16,16,0,0,0,32,48V208a16,16,0,0,0,16,16H208a16,16,0,0,0,16-16V48A16,16,0,0,0,208,32ZM90.34,98.34l32-32a8,8,0,0,1,11.32,0l32,32a8,8,0,0,1-11.32,11.32L136,91.31V152a8,8,0,0,1-16,0V91.31l-18.34,18.35A8,8,0,0,1,90.34,98.34ZM208,208H48V168H76.69L96,187.31A15.86,15.86,0,0,0,107.31,192h41.38A15.86,15.86,0,0,0,160,187.31L179.31,168H208v40Z"></path>
                            <% }%>
                            </svg>

                        </div>

                        <!-- Hidden form -->
                        <form id="archiveForm" action="ToggleArchiveServlet" method="post" style="display:none;">
                            <input type="hidden" name="diaryid" value="<%= diaryEntry.getDiaryid()%>">
                        </form>
                    </div>
                    <div class="card-body">
                        <div><%= formattedDate%></div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">

                        <% if ("edit".equals(mode)) {%>
                        <form action="<%= request.getContextPath()%>/UpdateDiaryServlet" method="post">
                            <input type="hidden" name="id" value="${diaryEntry.diaryid}">
                            <div style="margin-bottom: 10px;">
                                <input type="text" class="form-control font" name="diarytitle" value="${diaryEntry.diarytitle}" required>
                            </div>
                            <div class="edit-area">
                                <div style="margin-bottom: 10px;">
                                    <textarea class="edit-textarea" name="description" required>${diaryEntry.description}</textarea>
                                </div>
                            </div>
                            <div class="action-buttons">
                                <button type="submit" class="btn btn-success">Save</button>
                                <button type="button" class="btn btn-secondary" onclick="window.location.href = 'ViewDiaryEntryDetailsServlet?diaryid=${diaryEntry.diaryid}'">Cancel</button>
                            </div>
                        </form>
                        <% } else {%>
                        <div class="edit-area">
                            <div style="display: flex; align-items: center; margin-bottom: 10px;">
                                <span class="desc">${diaryEntry.description}</span>
                            </div>
                        </div>
                        <div class="action-buttons">
                            <% if (!diaryEntry.getIsarchived()) { %>
                            <button class="btn btn-success" onclick="window.location.href = 'ViewDiaryEntryDetailsServlet?diaryid=${diaryEntry.diaryid}&mode=edit'">Edit</button>
                            <% }%>
                            <button class="btn btn-secondary" onclick="window.location.href = '<%= request.getContextPath()%>/viewEntriesByDate?date=<%= urlDate%>'">Back</button>
                        </div>
                        <% }%>

                        <% if ("edit".equals(mode)) {%>
                        <div class="delete-button">
                            <form action="${pageContext.request.contextPath}/DeleteDiaryServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this diary entry?');">
                                <input type="hidden" name="diaryid" value="<%= diaryEntry.getDiaryid()%>"/>
                                <input type="hidden" name="date" value="<%= urlDate%>"/>
                                <button type="submit" class="btn btn-warning">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#000000" viewBox="0 0 256 256">
                                    <path d="M216,48H176V40a24,24,0,0,0-24-24H104A24,24,0,0,0,80,40v8H40a8,8,0,0,0,0,16h8V208a16,16,0,0,0,16,16H192a16,16,0,0,0,16-16V64h8a8,8,0,0,0,0-16ZM112,168a8,8,0,0,1-16,0V104a8,8,0,0,1,16,0Zm48,0a8,8,0,0,1-16,0V104a8,8,0,0,1,16,0Zm0-120H96V40a8,8,0,0,1,8-8h48a8,8,0,0,1,8,8Z"></path>
                                    </svg>
                                    &nbsp;Delete this diary
                                </button>
                            </form>
                        </div>
                        <% }%>

                    </div>
                </div>
            </div>
        </main>
    </section>
</html>