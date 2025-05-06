<%-- 
    Document   : journal-diary-content
    Created on : Mar 17, 2025, 12:35:42 PM
    Author     : JiaQuann
--%>

<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Diaryentry" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/journal-diaries_content.css">
    <section id="diary" class="incsec">
        <% request.setAttribute("pageTitle", "Diary Entries"); %>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <%
            Map<Diaryentry, List<String>> entryTagMap = (Map<Diaryentry, List<String>>) request.getAttribute("entryTagMap");
            String date = request.getParameter("date");

            String formattedDate = "";
            if (date != null && !date.isEmpty()) {
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd, MMM yyyy");
                    Date parsedDate = inputFormat.parse(date);
                    formattedDate = outputFormat.format(parsedDate);
                } catch (ParseException e) {
                    formattedDate = date; // fallback if parsing fails
                }
            }
        %>

        <main class="container mt-5 pt-5">
            <div class="diary-container">
                <div class="diary-header ">
                    <div class="date-display" style="font-size: 40px"><%= formattedDate %></div>
                </div>
                <ul class="entry-list">
                    <% if (entryTagMap == null || entryTagMap.isEmpty()) { %>
                        <li class="entry-item">
                            <div class="entry-header">
                                <div class="entry-tags"></div>
                            </div>
                            <div class="entry-content">
                                <span class="entry-emoji">😞</span>
                                <div class="entry-title">No entries found on this date.</div>
                            </div>
                            <div class="entry-actions"></div>
                        </li>
                    <% } else { 
                        for (Map.Entry<Diaryentry, List<String>> mapEntry : entryTagMap.entrySet()) {
                            Diaryentry entry = mapEntry.getKey();
                            List<String> tags = mapEntry.getValue();
                    %>
                        <li class="entry-item">
                            <div class="entry-header">
                                <div class="entry-tags">
                                    <span class="tag <%= entry.getMood() %>"><%= entry.getMood() %></span>
                                    <% for (String tag : tags) { %>
                                        <span class="tag"><%= tag %></span>
                                    <% } %>
                                </div>
                            </div>
                            <div class="entry-content">
                                <span class="entry-emoji"></span>
                                <div class="entry-title"><%= entry.getDiarytitle() %></div>
                            </div>
                            <div class="entry-actions">
                                <button class="action-btn" onclick="window.location.href = '<%= request.getContextPath() %>/ViewDiaryEntryDetailsServlet?diaryid=<%= entry.getDiaryid() %>&mode=view'">
                                    <span class="action-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#000000" viewBox="0 0 256 256">
                                            <path d="M247.31,124.76c-.35-.79-8.82-19.58-27.65-38.41C194.57,61.26,162.88,48,128,48S61.43,61.26,36.34,86.35C17.51,105.18,9,124,8.69,124.76a8,8,0,0,0,0,6.5c.35.79,8.82,19.57,27.65,38.4C61.43,194.74,93.12,208,128,208s66.57-13.26,91.66-38.34c18.83-18.83,27.3-37.61,27.65-38.4A8,8,0,0,0,247.31,124.76ZM128,192c-30.78,0-57.67-11.19-79.93-33.25A133.47,133.47,0,0,1,25,128,133.33,133.33,0,0,1,48.07,97.25C70.33,75.19,97.22,64,128,64s57.67,11.19,79.93,33.25A133.46,133.46,0,0,1,231.05,128C223.84,141.46,192.43,192,128,192Zm0-112a48,48,0,1,0,48,48A48.05,48.05,0,0,0,128,80Zm0,80a32,32,0,1,1,32-32A32,32,0,0,1,128,160Z"></path>
                                        </svg>
                                    </span> View
                                </button>
                            </div>
                        </li>
                    <% 
                        } // end for
                    } // end else 
                    %>
                </ul>
            </div>

            <div class="add-entry-container">
                <a class="add-entry-btn" href="<%= request.getContextPath() %>/AddingDiaryPageServlet?date=<%= date %>" >
                    <span class="add-icon">+</span> Add Diary Entry
                </a>
            </div>
        </main>
    </section>
</html>
