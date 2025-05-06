<%@page import="model.Reportcontent"%>
<%@page import="java.util.List"%>
<%@page import="model.Threadcategory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <%request.setAttribute("pageTitle", "Forum");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <%@ include file="../../shared/header.jsp" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reported Content - MizukiForest</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            /* Root variables */
            :root {
                --bg: #faf8f2;
                --font: #000000;
                --hovergrey: #eceae5;
                --input-bg: #ffffff;
                --border-color: #ddd;
                --highlight-color: #ff3333;
                --reason-highlight: #ffd700;
                --reason-bg: rgba(255, 215, 0, 0.1);
            }

            [data-theme="mizuki_dark"] {
                --bg: #242424;
                --font: #cecece;
                --hovergrey: #464646;
                --input-bg: #333333;
                --border-color: #464646;
                --reason-highlight: #ffd700;
                --reason-bg: rgba(255, 215, 0, 0.15);
            }

            /* Base styles */
            body {
                font-family: 'Silkscreen', sans-serif;
                background: var(--bg);
                color: var(--font);
                padding-top: 30px;
                margin: 0;
            }

            /* Header styles */
            .sub-header {
                display: flex;
                justify-content: center;
                gap: 20px;
                padding: 15px;
                background: var(--bg);
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
                margin-bottom: 30px;
                border-bottom: 1px solid var(--border-color);
            }

            .tab {
                color: var(--font);
                padding: 10px 25px;
                border-radius: 5px;
                text-decoration: none;
                cursor: pointer;
                opacity: 0.7;
                transition: opacity 0.3s ease;
            }

            .tab.active {
                background-color: var(--hovergrey);
                opacity: 1;
            }

            /* Container and form styles */
            .content-container {
                width: 90%;
                max-width: 800px;
                margin: 0 auto;
                padding: 0;
            }

            form {
                width: 100%;
                margin-bottom: 30px;
                padding: 25px;
                border: 1px solid var(--border-color);
                border-radius: 8px;
                background: var(--bg);
            }

            form:last-child {
                margin-bottom: 0;
            }

            /* Form fields */
            .form-display {
                margin-bottom: 20px;
            }

            .form-display:last-child {
                margin-bottom: 0;
            }

            .form-display label {
                display: block;
                margin-bottom: 8px;
                color: var(--font);
                font-size: 0.9em;
            }

            .form-display input,
            .form-display textarea {
                width: 100%;
                padding: 12px;
                border: 1px solid var(--border-color);
                border-radius: 4px;
                background: var(--input-bg);
                color: var(--font);
                font-family: 'Silkscreen', sans-serif;
                resize: none;
                cursor: default;
            }

            /* Highlighted fields */
            #threadSection .form-display input[name="threadTitle"],
            #threadSection .form-display input[name="threadDescription"],
            #commentSection .form-display input[name="commentContent"] {
                color: var(--highlight-color) !important;
                font-weight: 600;
            }

            /* Enhanced reported reason highlight */
            .form-display input[name="reportReason"] {
                color: var(--reason-highlight) !important;
                background-color: var(--reason-bg);
                font-weight: 500;
                border: 2px solid var(--reason-highlight);
                box-shadow: 0 0 8px rgba(255, 215, 0, 0.25);
                min-height: 70px;
                padding: 12px 15px;
                letter-spacing: 0.5px;
            }

            /* Larger input fields for longer content */
            .form-display input[name="threadDescription"],
            .form-display input[name="commentContent"],
            .form-display input[name="reportReason"] {
                min-height: 60px;
                padding: 10px 15px;
                overflow-y: auto;
                white-space: pre-wrap;
                word-break: break-word;
            }

            /* Adjust font size for better readability in larger inputs */
            .form-display input {
                font-size: 0.9em;
                line-height: 1.4;
                overflow-wrap: break-word;
                word-wrap: break-word;
                word-break: break-all;
                hyphens: auto;
                text-overflow: ellipsis;
            }

            /* Button styles */
            .button-container {
                display: flex;
                justify-content: flex-end;
                gap: 15px;
                margin-top: 30px;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-family: 'Silkscreen', sans-serif;
                transition: transform 0.2s ease, opacity 0.2s ease;
            }

            .btn:hover {
                transform: translateY(-1px);
                opacity: 0.9;
            }

            .btn:active {
                transform: translateY(0);
            }

            .btn-normal {
                background: #4e74a0;
                color: white;
            }

            .btn-delete {
                background: #dc3545;
                color: white;
            }

            /* Section display */
            #threadSection,
            #commentSection {
                display: none;
                animation: fadeIn 0.3s ease;
            }

            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(10px); }
                to { opacity: 1; transform: translateY(0); }
            }

            input[type="hidden"] {
                display: none;
            }
        </style>
    </head>
    <body>
        <div class="sub-header">
            <span class="tab active" id="threadTab">Reported Thread</span>
            <span class="tab" id="commentTab">Reported Comment</span>
        </div>

        <div id="threadSection" class="content-container">
            <%
                List<Reportcontent> reportedThreadList = (List<Reportcontent>) session.getAttribute("reportedThreadList");
                if (reportedThreadList != null) {
                    for (Reportcontent reportedThread : reportedThreadList) {
            %>
            <form action="<%= request.getContextPath()%>/UpdateForumReportedContentServlet" method="POST">
                <input type="hidden" name="contentType" value="thread">
                <input type="hidden" name="reportedContentId" value="<%= reportedThread.getReportcontentid()%>">
                
                <div class="form-display">
                    <label>Thread Title</label>
                    <input type="text" name="threadTitle" value="<%= reportedThread.getThreadid().getThreadtitle()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Thread Description</label>
                    <input type="text" name="threadDescription" value="<%= reportedThread.getThreadid().getThreaddescription()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Reported by</label>
                    <input type="email" name="reportedBy" value="<%= reportedThread.getUserid().getUseremail()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Reported Reason</label>
                    <input type="text" name="reportReason" value="<%= reportedThread.getReportreason()%>" readonly>
                </div>
                <div class="button-container">
                    <button type="submit" name="action" value="normal" class="btn btn-normal">Normal</button>
                    <button type="submit" name="action" value="delete" class="btn btn-delete">Delete</button>
                </div>
            </form>
            <%
                    }
                }
            %>
        </div>

        <div id="commentSection" class="content-container">
            <%
                List<Reportcontent> reportedCommentList = (List<Reportcontent>) session.getAttribute("reportedCommentList");
                if (reportedCommentList != null) {
                    for (Reportcontent reportedComment : reportedCommentList) {
            %>
            <form action="<%= request.getContextPath()%>/UpdateForumReportedContentServlet" method="POST">
                <input type="hidden" name="contentType" value="comment">
                <input type="hidden" name="reportedContentId" value="<%= reportedComment.getReportcontentid()%>">

                <div class="form-display">
                    <label>Thread Title</label>
                    <input type="text" name="threadTitle" value="<%= reportedComment.getThreadid().getThreadtitle()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Thread Description</label>
                    <input type="text" name="threadDescription" value="<%= reportedComment.getThreadid().getThreaddescription()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Comment User</label>
                    <input type="email" name="commentUser" value="<%= reportedComment.getThreadcommentid().getUserid().getUseremail()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Comment Content</label>
                    <input type="text" name="commentContent" value="<%= reportedComment.getThreadcommentid().getContent()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Reported by</label>
                    <input type="email" name="reportedBy" value="<%= reportedComment.getUserid().getUseremail()%>" readonly>
                </div>
                <div class="form-display">
                    <label>Reported Reason</label>
                    <input type="text" name="reportReason" value="<%= reportedComment.getReportreason()%>" readonly>
                </div>
                <div class="button-container">
                    <button type="submit" name="action" value="normal" class="btn btn-normal">Normal</button>
                    <button type="submit" name="action" value="delete" class="btn btn-delete">Delete</button>
                </div>
            </form>
            <%
                    }
                }
            %>
        </div>

        <script>
            function showSection(section) {
                // Hide all sections
                document.getElementById('threadSection').style.display = 'none';
                document.getElementById('commentSection').style.display = 'none';

                // Remove active class from all tabs
                document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));

                // Show selected section and activate tab
                document.getElementById(section + 'Section').style.display = 'block';
                document.getElementById(section + 'Tab').classList.add('active');
            }

            // Add click listeners to tabs
            document.getElementById('threadTab').addEventListener('click', () => showSection('thread'));
            document.getElementById('commentTab').addEventListener('click', () => showSection('comment'));

            // Show thread section by default
            showSection('thread');
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>