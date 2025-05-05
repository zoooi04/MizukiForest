<%@page import="model.Threadcategory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <%request.setAttribute("pageTitle", "Forum");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <%@ include file="../../shared/header.jsp" %>
    <jsp:include page="${request.contextPath}/view/forum/forum-subheader.jsp"/>

    <head>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum/forum_thread_list.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>

    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="../js/mizukibase.js"></script>


    <section id="forum" class="forum-section">

        <main class="container mt-5 pt-5">  
            <div class="flex flex-col justify-center my-6 forum-header">
                <h1 class="font-serif text-3xl text-center flex-shrink-0">Mizuki Forums</h1>
                <p class="text-center text-secondary mt-2">Join the conversation and share your thoughts</p>

                <!-- Filter Section with Form -->
                <form action="../../ViewForumEntryServlet" method="GET" class="filter-form flex justify-center items-center mt-4">
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
                    <button type="submit" class="btn btn-primary ml-4">Apply Filter</button>
                </form>
            </div>

            <div id="forum-list" class="mb-16 min-h-[50%]">
                <div class="grid gap-4 grid-cols-1 sm:grid-cols-2 mx-auto max-w-lg sm:max-w-4xl">
                    <%@ page import="java.util.List" %>
                    <%@ page import="model.Thread" %>
                    <%
                        String threadType = (String) session.getAttribute("threadType");
                        if ("my".equals(threadType)) {
                    %>
                    <a id="add_new_thread" href="forum-add-thread-form.jsp">
                        <div class="thread-card card w-full h-full shadow-sm sm:hover:-translate-y-1 sm:hover:shadow-md sm:transition sm:duration-200">
                            <div class="thread-content card-body p-4">
                                <div class="break-words overflow-ellipsis my-auto">
                                    <div class="flex justify-start gap-x-4 items-center">
                                        <div class="w-12 flex-shrink-0">
                                            <div class="h-12 w-12 relative overflow-hidden flex-shrink-0 select-none pointer-events-none">
                                                <i class="fa fa-plus" style="font-size:48px;color:whitesmoke" ></i>
                                            </div>
                                        </div>
                                        <div>
                                            <p class="thread-title line-clamp-2 text-lg font-bold mb-2">
                                                <u>Add Thread Now</u>
                                            </p>
                                            <p class="thread-description text-sm">
                                                <span>
                                                    Add a new thread to share with others.
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
                    %>
                    <%
                        List<model.Thread> threadList = (List<model.Thread>) session.getAttribute("forumThreadList");
                        if (threadList != null && !threadList.isEmpty()) {
                            for (Thread thread : threadList) {
                                String idtag = "thread_" + thread.getThreadid();
                                String title = thread.getThreadtitle();
                    %>
                    <a id="<%=idtag%>" href="../../ViewForumDetailServlet?thread_id=<%=thread.getThreadid()%>">
                        <div class="thread-card card w-full h-full shadow-sm sm:hover:-translate-y-1 sm:hover:shadow-md sm:transition sm:duration-200">
                            <div class="thread-content card-body p-4">
                                <div class=" break-words overflow-ellipsis my-auto">
                                    <div class="flex justify-start gap-x-4 items-center">
                                        <div class="w-12 flex-shrink-0">
                                            <div class="h-12 w-12 relative overflow-hidden flex-shrink-0 select-none pointer-events-none">
                                                <img class="h-12 w-12 thread-avatar" src="<%= request.getContextPath()%>/media/images/mizuki.png" />
                                            </div>
                                        </div>
                                        <div>
                                            <p class="thread-title line-clamp-2 text-lg font-bold mb-2">
                                                <%= thread.getThreadtitle()%>
                                            </p>
                                            <p class="thread-description text-sm">
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
                    } else {
                    %>
                    <div class="col-span-4 text-center py-10">
                        <p class="text-xl">No threads found</p>
                        <p class="text-secondary mt-2">Be the first to start a conversation!</p>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </main>
    </section>
</html>