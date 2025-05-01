<%@page import="model.Threadcategory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <head>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum.css">
    </head>

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

    <section id="forum" class="">
        <%request.setAttribute("pageTitle", "My Threads");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <main class="container mt-5 pt-5">  
            <div class="flex flex-col justify-center my-6">

                <h1 class="font-serif text-3xl text-center flex-shrink-0">My Threads</h1>

                <!-- Filter Section with Form -->
                <form action="../../ViewForumEntryServlet" method="GET" class="flex justify-center items-center mt-4">
                    <div class="flex space-x-4">

                        <!-- Category Filter Dropdown -->
                        <select name="category" id="categoryFilter" class="form-select">
                            <option value="all" <%= "all".equals(session.getAttribute("selectedCategory")) ? "selected" : ""%>>Filter by: All</option>
                            <%
                                List<Threadcategory> threadCateogryList = (List<Threadcategory>) session.getAttribute("forumThreadCategoryList");
                                if (threadCateogryList != null) {
                                    for (Threadcategory threadCategory : threadCateogryList) {
                                        String threadCateogryId = threadCategory.getThreadcategoryid();
                                        String threadCateogryName = threadCategory.getThreadcategoryname();
                            %>
                            <option value=<%=threadCateogryId%> <%= threadCateogryId.equals(session.getAttribute("selectedCategory")) ? "selected" : ""%>><%=threadCateogryName%></option>
                            <%
                                    }
                                }
                            %>
                        </select>

                        <!-- Vote Filter Dropdown -->
                        <select name="vote" id="voteFilter" class="form-select">
                            <option value="upvote" <%= "upvote".equals(session.getAttribute("selectedVote")) ? "selected" : ""%>>Vote: Upvote</option>
                            <option value="downvote" <%= "downvote".equals(session.getAttribute("selectedVote")) ? "selected" : ""%>>Vote: Downvote</option>
                        </select>
                    </div>

                    <!-- Filter Button -->
                    <button type="submit" class="btn btn-primary ml-4">Filter</button>
                </form>
            </div>

            <div id="forum-list" class="mb-16 min-h-[50%]">
                <div class="grid gap-4 grid-cols-1 sm:grid-cols-2 mx-auto max-w-lg sm:max-w-4xl">
                    <%@ page import="java.util.List" %>
                    <%@ page import="model.Thread" %>
                    <%
                        List<model.Thread> threadList = (List<model.Thread>) session.getAttribute("forumThreadList");
                        if (threadList != null) {
                            for (Thread thread : threadList) {
                                String idtag = "thread_" + thread.getThreadid();
                                String title = thread.getThreadtitle();
                    %>
                    <a id="<%=idtag%>"
                       href="../../ViewForumDetailServlet?thread_id=<%=thread.getThreadid()%>">
                        <div
                            class="card w-full h-full bg-retro-base-150 dark:bg-base-200 text-base-content shadow-sm sm:hover:-translate-y-1 sm:hover:shadow-md sm:transition sm:duration-200">
                            <div class="card-body p-4">
                                <div class="font-serif break-words overflow-ellipsis my-auto">
                                    <div class="flex justify-start gap-x-4 items-center">
                                        <div class="w-12 flex-shrink-0">
                                            <div class="h-12 w-12 relative overflow-hidden flex-shrink-0 select-none pointer-events-none "
                                                 style="image-rendering: pixelated;">
                                                <img class="h-12 w-12"
                                                     src="<%= request.getContextPath()%>/media/images/mizuki.png" />
                                            </div>
                                        </div>
                                        <div>
                                            <p class="line-clamp-2 text-lg font-sans font-bold mb-2">
                                                <%= thread.getThreadtitle()%>
                                            </p>
                                            <p class="text-sm text-secondary">
                                                <span>
                                                    <%= thread.getThreaddescription()%>
                                                </span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                    <%
                            }
                        }
                    %>
                </div>
            </div>

        </main>
    </section>

    <script src="../js/mizukibase.js"></script>

</html>