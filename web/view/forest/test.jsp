<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Tree" %>
<%@ page import="model.TreeService" %>

<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="javax.persistence.Persistence" %>
<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MizukiForestPU");
    EntityManager mgr = emf.createEntityManager();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Trees</title>
    <style>
        .tree-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        .tree-card {
            border: 1px solid #ccc;
            padding: 10px;
            width: 250px;
            border-radius: 8px;
            box-shadow: 2px 2px 5px #aaa;
        }
        .tree-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 6px;
        }
    </style>
</head>
<body>
    <h1>All Trees</h1>

    <div class="tree-container">
        <%
            TreeService treeService = new TreeService(mgr);
            List<Tree> treeList = treeService.findAllTrees();

            for (Tree tree : treeList) {
        %>
            <div class="tree-card">
                <img src="<%= request.getContextPath() + "/" + tree.getTreeimage() %>" alt="<%= tree.getTreename() %>" />
                <h3><%= tree.getTreename() %></h3>
                <p><strong>Description:</strong> <%= tree.getTreedescription() %></p>
                <p><strong>Tree ID:</strong> <%= tree.getTreeid() %></p>
                <p><strong>Archived:</strong> <%= tree.getIsarchived() %></p>
                <p><strong>Status:</strong> <%= tree.getTreestatus() %></p>
                <p><strong>Deleted:</strong> <%= tree.getIsdeleted() %></p>
                <p><strong>Rarity:</strong> <%= tree.getRarityid().getRarityid() %></p>
            </div>
        <%
            }
        %>
    </div>
</body>
</html>
