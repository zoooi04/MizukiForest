<%@page import="model.Threadcategory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

    <head>

        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum.css">
        
        <style>
            /* Enhanced styling for forum list */
            .forum-section {
                padding: 2rem 0;
            }

            .forum-header {
                margin-bottom: 2rem;
            }

            .filter-form {
                background: #2d2d35;
                padding: 1rem;
                border-radius: 8px;
                margin-bottom: 2rem;
                border: 1px solid #3d3d45;
            }

            .filter-form select {
                background: #3a3a3a;
                color: #cecece;
                border: 1px solid #4a4a4a;
                border-radius: 4px;
                padding: 0.5rem;
            }

            .filter-form button {
                background: #4978a8;
                color: #fff;
                border: none;
                border-radius: 4px;
                padding: 0.5rem 1rem;
                cursor: pointer;
                transition: background 0.3s ease;
            }

            .filter-form button:hover {
                background: #5989b9;
            }

            .thread-card {
                background: #333333;
                border-radius: 8px;
                overflow: hidden;
                transition: transform 0.2s ease, box-shadow 0.2s ease;
            }

            .thread-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            }

            .thread-content {
                padding: 1rem;
            }

            .thread-title {
                color: #cecece;
                font-weight: bold;
                margin-bottom: 0.5rem;
            }

            .thread-description {
                color: #9a9a9a;
                font-size: 0.875rem;
            }

            .thread-avatar {
                border-radius: 50%;
                border: 2px solid #4978a8;
            }

            .create-thread-btn {
                position: fixed;
                bottom: 2rem;
                right: 2rem;
                background: #4978a8;
                color: white;
                width: 60px;
                height: 60px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 1.5rem;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
                cursor: pointer;
                z-index: 100;
                transition: background 0.3s ease, transform 0.2s ease;
            }

            .create-thread-btn:hover {
                background: #5989b9;
                transform: scale(1.05);
            }
        </style>
    </head>

    <section id="forum" class="forum-section">
        <%request.setAttribute("pageTitle", "Forum");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

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
                        List<model.Thread> threadList = (List<model.Thread>) session.getAttribute("forumThreadList");
                        if (threadList != null && !threadList.isEmpty()) {
                            for (Thread thread : threadList) {
                                String idtag = "thread_" + thread.getThreadid();
                                String title = thread.getThreadtitle();
                    %>
                    <a id="<%=idtag%>" href="../../ViewForumDetailServlet?thread_id=<%=thread.getThreadid()%>">
                        <div class="thread-card card w-full h-full shadow-sm sm:hover:-translate-y-1 sm:hover:shadow-md sm:transition sm:duration-200">
                            <div class="thread-content card-body p-4">
                                <div class="font-serif break-words overflow-ellipsis my-auto">
                                    <div class="flex justify-start gap-x-4 items-center">
                                        <div class="w-12 flex-shrink-0">
                                            <div class="h-12 w-12 relative overflow-hidden flex-shrink-0 select-none pointer-events-none">
                                                <img class="h-12 w-12 thread-avatar" src="<%= request.getContextPath()%>/media/images/mizuki.png" />
                                            </div>
                                        </div>
                                        <div>
                                            <p class="thread-title line-clamp-2 text-lg font-sans font-bold mb-2">
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
                    <div class="col-span-2 text-center py-10">
                        <p class="text-xl">No threads found</p>
                        <p class="text-secondary mt-2">Be the first to start a conversation!</p>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>

            <!-- Create Thread Button -->
            <a href="../../CreateForumThreadServlet" class="create-thread-btn">
                <i class="fas fa-plus"></i>
            </a>
        </main>
    </section>

    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="../js/mizukibase.js"></script>
</html>