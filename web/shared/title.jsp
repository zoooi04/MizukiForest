<%-- 
    Document   : title
    Created on : Mar 6, 2025, 10:51:24?AM
    Author     : JiaQuann
--%>

<% 
    String pageTitle = (String) request.getAttribute("pageTitle");
    if (pageTitle == null) {
        pageTitle = "Mizuki Forest";
    }
%>
<title>Mizuki's Forest | <%= pageTitle %></title>

