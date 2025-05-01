<%-- 
    Document   : journal-diary-details
    Created on : Apr 27, 2025, 12:02:06 AM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>


<!DOCTYPE html>
<html lang="en" data-theme="mizuki_light">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

    <section id="diary" class="incsec">
        <%request.setAttribute("pageTitle", "Diary Entries");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <%@ page import="java.util.List" %>
        <%@ page import="model.Diaryentry" %>

        <main class="container mt-5 pt-5">
            <div class="content">
                <div class="card">
                    <div class="card-header">
                        <div><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#000000" viewBox="0 0 256 256"><path d="M208,24H72A32,32,0,0,0,40,56V224a8,8,0,0,0,8,8H192a8,8,0,0,0,0-16H56a16,16,0,0,1,16-16H208a8,8,0,0,0,8-8V32A8,8,0,0,0,208,24Zm-24,96-25.61-19.2a4,4,0,0,0-4.8,0L128,120V40h56Z"></path></svg>
                            &nbsp;${diaryEntry.diarytitle}</div>
                        <div class="private-badge">PRIVATE &nbsp;<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#ffffff" viewBox="0 0 256 256"><path d="M227.31,73.37,182.63,28.68a16,16,0,0,0-22.63,0L36.69,152A15.86,15.86,0,0,0,32,163.31V208a16,16,0,0,0,16,16H92.69A15.86,15.86,0,0,0,104,219.31L227.31,96a16,16,0,0,0,0-22.63ZM192,108.68,147.31,64l24-24L216,84.68Z"></path></svg>️</div>
                    </div>
                    <div class="card-body">
                        <%
                            Diaryentry diaryEntry = (Diaryentry) request.getAttribute("diaryEntry");
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
                        <div><%= formattedDate%></div>

                        <div class="actions">
                            <button class="action-btn"><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#888783" viewBox="0 0 256 256"><path d="M216,32H88a8,8,0,0,0-8,8V80H40a8,8,0,0,0-8,8V216a8,8,0,0,0,8,8H168a8,8,0,0,0,8-8V176h40a8,8,0,0,0,8-8V40A8,8,0,0,0,216,32Zm-8,128H176V88a8,8,0,0,0-8-8H96V48H208Z"></path></svg>
                                &nbsp;Create a copy</button>
                            <button class="action-btn primary"><svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#ffffff" viewBox="0 0 256 256"><path d="M208,32H48A16,16,0,0,0,32,48V208a16,16,0,0,0,16,16H208a16,16,0,0,0,16-16V48A16,16,0,0,0,208,32ZM184,136H136v48a8,8,0,0,1-16,0V136H72a8,8,0,0,1,0-16h48V72a8,8,0,0,1,16,0v48h48a8,8,0,0,1,0,16Z"></path></svg>
                                &nbsp;Modify mood/tags</button>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <div class="edit-area">
                            <div style="display: flex; align-items: center; margin-bottom: 10px;">
                                <span class="desc">${diaryEntry.description}</span>
                            </div>
                            <!--<textarea class="edit-textarea" placeholder="Write your notes here..."></textarea>-->
                        </div>



                        <div class="action-buttons">
                            <button class="btn btn-success">Edit</button>
                            <button class="btn btn-secondary"  onclick="window.location.href='<%= request.getContextPath()%>/viewEntriesByDate?date=<%= urlDate%>'">Back</button>
                        </div>

                    </div>
                </div>
            </div>
        </main>
    </section>



    <style>
        main {
            /*font-family: Arial, sans-serif;*/
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 20px;
            border-bottom: 1px solid #e0e0e0;
            background-color: white;
        }

        .logo {
            display: flex;
            align-items: center;
        }

        .logo img {
            width: 30px;
            height: 30px;
            margin-right: 10px;
        }

        .logo-text {
            font-family: 'Silkscreen', cursive;
            font-size: 18px;
            font-weight: bold;
            color: #333;
        }

        .nav {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .nav-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            font-size: 12px;
            color: #666;
        }

        .nav-icon {
            font-size: 18px;
            margin-bottom: 5px;
        }

        .right-icons {
            display: flex;
            gap: 15px;
        }

        .tabs {
            display: flex;
            justify-content: center;
            padding: 10px 0;
            background-color: #f0f0f0;
        }

        .tab {
            padding: 8px 30px;
            margin: 0 5px;
            border-radius: 20px;
            cursor: pointer;
            font-weight: bold;
            font-size: 14px;
        }

        .tab.active {
            background-color: #333;
            color: white;
        }

        .content {
            max-width: 800px;
            margin: 20px auto;
            padding: 0 20px;
        }

        .desc {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .card {
            background-color: white;
            border-radius: 5px;
            overflow: hidden;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .card-header {
            position: relative;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 15px;
            border-bottom: 4px solid #3c74b5;
            background-color: #f9f9f9;
        }

        .private-badge {
            position: absolute;
            right: 0;
            top: 0;
            height: 100%;
            background-color: #3c74b5;
            color: white;
            padding: 0 10px;
            border-radius: 5px 5px 0 0;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            display: flex;
            align-items: center;
        }

        .computer-icon {
            font-size: 20px;
            margin-right: 8px;
        }

        .card-title {
            display: flex;
            align-items: center;
            font-size: 18px;
            font-weight: bold;
            color: #333;
        }

        .card-body {
            padding: 15px;
        }

        .actions {
            display: flex;
            justify-content: space-between;
            margin-top: 15px;
        }

        .action-btn {
            display: flex;
            align-items: center;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            background-color: #f0f0f0;
            border: 1px solid #ddd;
        }

        .action-btn.primary {
            background-color: #3c74b5;
            color: white;
            border: none;
        }

        .color-buttons {
            display: flex;
            justify-content: center;
            margin: 20px 0;
            gap: 10px;
        }

        .color-button {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            cursor: pointer;
        }

        .edit-area {
            background-color: white;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            padding: 15px;
            margin: 20px 0;
            min-height: 150px;
        }

        .edit-textarea {
            width: 100%;
            min-height: 150px;
            border: none;
            padding: 10px;
            font-family: Arial, sans-serif;
            font-size: 14px;
            resize: vertical;
            box-sizing: border-box;
            outline: none;
        }

        .action-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 15px;
        }

        .btn {
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            border: none;
        }

        .btn-success {
            background-color: #28a745;
            color: white;
        }

        .btn-secondary {
            background-color: #f8f9fa;
            color: #333;
            border: 1px solid #ddd;
        }

        .btn-warning {
            background-color: #ffc107;
            color: #333;
        }

        .delete-button {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
    </style>