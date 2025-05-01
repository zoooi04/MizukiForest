<%-- 
    Document   : journal-diary-content
    Created on : Mar 17, 2025, 12:35:42 PM
    Author     : JiaQuann
--%>

<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_light">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <section id="diary" class="incsec">
        <%request.setAttribute("pageTitle", "Diary Entries");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>


        <%@ page import="java.util.List" %>
        <%@ page import="model.Diaryentry" %>

        <%
            List<Diaryentry> entriesOnDate = (List<Diaryentry>) request.getAttribute("entriesOnDate");
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

        <main class="container  mt-5 pt-5">
            <div class="diary-container">
                <div class="diary-header details-font">
                    <div class="date-display" style="font-size: 40px"><%= formattedDate%></div>
                </div>
                <ul class="entry-list">
                    <% if (entriesOnDate == null || entriesOnDate.isEmpty()) { %>
                    <li class="entry-item">
                        <div class="entry-header">
                            <div class="entry-tags">
                            </div>
                        </div>
                        <div class="entry-content">
                            <span class="entry-emoji">😞</span>
                            <div class="entry-title">No entries found on this date.</div>
                        </div>
                        <div class="entry-actions">
                        </div>
                    </li>
                    <% } else { %>
                    <% for (Diaryentry entry : entriesOnDate) {%>
                    <li class="entry-item">
                        <div class="entry-header">
                            <div class="entry-tags">
                                <span class="tag">BREAK</span>
                                <span class="tag"><%= entry.getMood()%></span>
                            </div>
                        </div>
                        <div class="entry-content">
                            <span class="entry-emoji"></span>
                            <div class="entry-title details-font"><%= entry.getDiarytitle()%></div>
                        </div>
                        <div class="entry-actions">
                            <button class="action-btn" onclick="window.location.href='<%= request.getContextPath() %>/ViewDiaryEntryDetailsServlet?diaryid=<%= entry.getDiaryid() %>'">
                                <span class="action-icon"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#000000" viewBox="0 0 256 256"><path d="M247.31,124.76c-.35-.79-8.82-19.58-27.65-38.41C194.57,61.26,162.88,48,128,48S61.43,61.26,36.34,86.35C17.51,105.18,9,124,8.69,124.76a8,8,0,0,0,0,6.5c.35.79,8.82,19.57,27.65,38.4C61.43,194.74,93.12,208,128,208s66.57-13.26,91.66-38.34c18.83-18.83,27.3-37.61,27.65-38.4A8,8,0,0,0,247.31,124.76ZM128,192c-30.78,0-57.67-11.19-79.93-33.25A133.47,133.47,0,0,1,25,128,133.33,133.33,0,0,1,48.07,97.25C70.33,75.19,97.22,64,128,64s57.67,11.19,79.93,33.25A133.46,133.46,0,0,1,231.05,128C223.84,141.46,192.43,192,128,192Zm0-112a48,48,0,1,0,48,48A48.05,48.05,0,0,0,128,80Zm0,80a32,32,0,1,1,32-32A32,32,0,0,1,128,160Z"></path></svg>
                                    ️</span> View
                            </button>
                            <button class="action-btn">
                                <span class="action-icon"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#000000" viewBox="0 0 256 256"><path d="M227.31,73.37,182.63,28.68a16,16,0,0,0-22.63,0L36.69,152A15.86,15.86,0,0,0,32,163.31V208a16,16,0,0,0,16,16H92.69A15.86,15.86,0,0,0,104,219.31L227.31,96a16,16,0,0,0,0-22.63ZM51.31,160,136,75.31,152.69,92,68,176.68ZM48,179.31,76.69,208H48Zm48,25.38L79.31,188,164,103.31,180.69,120Zm96-96L147.31,64l24-24L216,84.68Z"></path></svg>
                                    ️</span> Edit
                            </button>
                            <button class="action-btn">
                                <span class="action-icon"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#000000" viewBox="0 0 256 256"><path d="M200,32H163.74a47.92,47.92,0,0,0-71.48,0H56A16,16,0,0,0,40,48V216a16,16,0,0,0,16,16H200a16,16,0,0,0,16-16V48A16,16,0,0,0,200,32Zm-72,0a32,32,0,0,1,32,32H96A32,32,0,0,1,128,32Zm72,184H56V48H82.75A47.93,47.93,0,0,0,80,64v8a8,8,0,0,0,8,8h80a8,8,0,0,0,8-8V64a47.93,47.93,0,0,0-2.75-16H200Z"></path></svg>
                                </span> Duplicate
                            </button>
                            <button class="action-btn">
                                <span class="action-icon"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#000000" viewBox="0 0 256 256"><path d="M243.31,136,144,36.69A15.86,15.86,0,0,0,132.69,32H40a8,8,0,0,0-8,8v92.69A15.86,15.86,0,0,0,36.69,144L136,243.31a16,16,0,0,0,22.63,0l84.68-84.68a16,16,0,0,0,0-22.63Zm-96,96L48,132.69V48h84.69L232,147.31ZM96,84A12,12,0,1,1,84,72,12,12,0,0,1,96,84Z"></path></svg>
                                    ️</span> Add tags
                            </button>
                        </div>
                    </li>

                    <% } %>

                    <% }%>

                </ul>
            </div>

            <div class="add-entry-container">
                <a class="add-entry-btn" href="<%= request.getContextPath()%>/AddingDiaryPageServlet?date=<%= date%>" >
                    <span class="add-icon">+</span> Add Diary Entry
                </a>
            </div>
        </main>
    </section>
</html>

<style>

    :root {
        --primary-color: #6a5acd;
        --accent-color: #ffd700;
        --text-color: #333333;
        --light-bg: #f8f9fa;
        --white: #ffffff;
        --gray: #aaaaaa;
        --light-gray: #eeeeee;
        --shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    .details-font {
        box-sizing: border-box;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 15px;
    }



    /* Diary Content */
    .diary-container {
        background-color: var(--white);
        border-radius: 10px;
        box-shadow: var(--shadow);
        padding: 20px;
        margin-bottom: 30px;
    }

    .diary-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        padding-bottom: 15px;
        border-bottom: 1px solid var(--light-gray);
    }

    .date-display {
        font-size: 1.5rem;
        font-weight: bold;
    }

    .edit-btn {
        background-color: var(--primary-color);
        color: var(--white);
        border: none;
        padding: 8px 15px;
        border-radius: 5px;
        cursor: pointer;
        display: flex;
        align-items: center;
        transition: background-color 0.3s;
    }

    .edit-btn:hover {
        background-color: #5549a9;
    }

    .edit-icon {
        margin-left: 5px;
    }

    /* Entry Items */
    .entry-list {
        list-style: none;
    }

    .entry-item {
        background-color: var(--white);
        border-left: 5px solid var(--accent-color);
        padding: 15px;
        margin-bottom: 15px;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
    }

    .entry-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
    }

    .entry-time {
        font-size: 0.9rem;
        color: var(--gray);
    }

    .entry-tags {
        display: flex;
        gap: 5px;
    }

    .tag {
        background-color: #f0e6ff;
        color: var(--primary-color);
        padding: 2px 8px;
        border-radius: 10px;
        font-size: 0.8rem;
    }

    .entry-content {
        display: flex;
        align-items: center;
        margin-bottom: 15px;
    }

    .entry-emoji {
        font-size: 1.5rem;
        margin-right: 10px;
    }

    .entry-title {
        font-weight: 500;
        font-size: 1.1rem;
    }

    .entry-actions {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    .action-btn {
        background: none;
        border: none;
        display: flex;
        align-items: center;
        color: var(--gray);
        font-size: 0.9rem;
        cursor: pointer;
        transition: color 0.3s;
    }

    .action-btn:hover {
        color: var(--primary-color);
    }

    .action-icon {
        margin-right: 5px;
    }

    /* Add Entry Button */
    .add-entry-container {
        display: flex;
        justify-content: center;
        margin: 20px 0;
    }

    .add-entry-btn {
        background-color: var(--white);
        color: var(--primary-color);
        border: 2px solid var(--primary-color);
        border-radius: 20px;
        padding: 10px 20px;
        font-weight: 500;
        display: flex;
        align-items: center;
        cursor: pointer;
        transition: all 0.3s;
        text-decoration: none;
    }

    .add-entry-btn:hover {
        background-color: var(--primary-color);
        color: var(--white);
        text-decoration: none;
    }

    .add-icon {
        margin-right: 8px;
        font-size: 1.2rem;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .nav-text {
            display: none;
        }

        .nav-item {
            margin: 0 10px;
        }

        .date-display {
            font-size: 1.3rem;
        }
    }

</style>
