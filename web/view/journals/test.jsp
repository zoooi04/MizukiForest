<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test Page</title>
</head>
<body>

<h1>Diary Entry Information</h1>

<% 
    String diaryData = (String) request.getAttribute("diaryData");
    String tagData = (String) request.getAttribute("tagData");

    if (diaryData != null) {
        out.println("<p>" + diaryData + "</p>");
    } else {
        out.println("<p>No diary entry data found.</p>");
    }

    if (tagData != null) {
        out.println("<p>" + tagData + "</p>");
    } else {
        out.println("<p>No tag data found.</p>");
    }
%>

</body>
</html>
