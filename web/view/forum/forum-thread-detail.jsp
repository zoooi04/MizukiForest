<%@ page import="java.util.List, java.util.Map" %>

<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <head>
        <!-- Existing meta tags -->
        <meta name="current-username" content="<%= ((model.Users) session.getAttribute("currentUser")).getUsername()%>">
        <meta name="current-userid" content="<%= ((model.Users) session.getAttribute("currentUser")).getUserid()%>">
        <meta name="selected-threadid" content="<%= session.getAttribute("selectedThreadID")%>">
        <meta name="comment-id-reply-to" content="<%= request.getParameter("commentIdReplyTo") != null ? request.getParameter("commentIdReplyTo") : ""%>">
        <meta name="forumThreadCategoryList" content='[
              <%
                  List<model.Threadcategory> categories = (List<model.Threadcategory>) session.getAttribute("forumThreadCategoryList");
                  if (categories != null) {
                      for (int i = 0; i < categories.size(); i++) {
                          model.Threadcategory category = categories.get(i);
              %>{"threadcategoryid": "<%= category.getThreadcategoryid()%>", "threadcategoryname": "<%= category.getThreadcategoryname()%>"}<%= (i < categories.size() - 1) ? "," : ""%>
              <%
                      }
                  }
              %>
              ]'>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum/forum_thread_list.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum/forum_thread_detail.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>

    <%request.setAttribute("pageTitle", "Thread Detail");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <%@ include file="../../shared/header.jsp" %>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="../js/mizukibase.js"></script>
    <script src="<%= request.getContextPath()%>/js/forum/forum_thread_detail.js"></script>

    <body>
        <main class="container mt-5 pt-5">
            <div class="thread-container">
                <!-- Back to Forum Link -->
                <a href="../../ViewForumEntryServlet" class="back-link">
                    <i class="bi bi-reply"></i> Back to Forum
                </a>

                <%-- Display success message if present --%>
                <% if (session.getAttribute("reportSuccessMessage") != null) { %>
                <div class="alert alert-success">
                    <%= session.getAttribute("reportSuccessMessage") %>
                </div>
                <%
                    session.removeAttribute("reportSuccessMessage");
                } %>

                <%-- Display error message if present --%>
                <% if (request.getAttribute("errorMessage") != null) {%>
                <div class="error-message">
                    <p><%= request.getAttribute("errorMessage")%></p>
                </div>
                <% } %>

                <!-- Thread Post Section -->
                <div class="thread-post">
                    <div class="thread-header">
                        <% if (session.getAttribute("selectedThread") != null) {%>
                        <img src="<%= request.getContextPath()%>/media/images/mizuki.png" alt="User Avatar" class="user-avatar">
                        <div>
                            <div class="font-bold"><%= ((model.Users) ((model.Thread) session.getAttribute("selectedThread")).getUserid()).getUsername()%></div>
                            <div class="thread-meta">
                                <span>Posted: Not Available</span>
                                <span class="dot"></span>
                                <% if (session.getAttribute("selectedThreadCategory") != null) {%>
                                <span>Category: <%= ((model.Threadcategory) session.getAttribute("selectedThreadCategory")).getThreadcategoryname()%></span>
                                <% } else { %>
                                <span>Category: Not Available</span>
                                <% } %>
                            </div>
                        </div>
                        <% } else { %>
                        <p>Thread details are not available.</p>
                        <% } %>
                    </div>

                    <% if (session.getAttribute("selectedThread") != null) {%>
                    <h1 class="text-3xl mb-3"><%= ((model.Thread) session.getAttribute("selectedThread")).getThreadtitle()%></h1>

                    <div class="thread-content">
                        <p><%= ((model.Thread) session.getAttribute("selectedThread")).getThreaddescription()%></p>
                        <% List<model.Threadimage> images = (List<model.Threadimage>) session.getAttribute("selectedThreadImages"); %>
                        <% if (images != null && !images.isEmpty()) { %>
                        <% for (model.Threadimage image : images) { %>
                        <% if (image.getImage() != null && !image.getImage().isEmpty()) {%>
                        <img src="<%= request.getContextPath() + "/media/images/" + image.getImage()%>" alt="Thread Image" class="thread-image">
                        <% } %>
                        <% } %>
                        <% }%>
                    </div>

                    <div class="thread-actions">
                        <div class="action-group">
                            <button class="action-btn upvote" data-vote-type="true">
                                <i class="<%= (session.getAttribute("threadVoteType") != null && (Boolean) session.getAttribute("threadVoteType")) ? "bi-hand-thumbs-up-fill" : "bi-hand-thumbs-up"%>"></i>
                                <span><%= ((model.Thread) session.getAttribute("selectedThread")).getUpvote()%></span>
                            </button>
                            <button class="action-btn downvote" data-vote-type="false">
                                <i class="bi <%= (session.getAttribute("threadVoteType") != null && !(Boolean) session.getAttribute("threadVoteType")) ? "bi-hand-thumbs-down-fill" : "bi-hand-thumbs-down"%>"></i>
                                <span><%= ((model.Thread) session.getAttribute("selectedThread")).getDownvote()%></span>
                            </button>
                        </div>

                        <% if (((model.Users) session.getAttribute("currentUser")).getUserid().equals(((model.Thread) session.getAttribute("selectedThread")).getUserid().getUserid())) { %>
                        <div class="action-group">
                            <button class="action-btn" style="display: block">
                                <i class="bi bi-pencil-square"></i>
                                <span>Edit</span>
                            </button>
                            <button class="action-btn" style="display: block">
                                <i class="bi bi-trash"></i>
                                <span>Delete</span>
                            </button>
                        </div>
                        <% } %>

                        <div class="action-group">
                            <button class="action-btn">
                                <i class="bi bi-exclamation-circle"></i>
                                <span>Report</span>
                            </button>
                        </div>
                    </div>
                    <% }%>
                </div>

                <!-- Comment Form -->
                <div class="comment-form">
                    <form action="<%= request.getContextPath()%>/AddForumThreadCommentServlet" method="post">
                        <div class="flex items-center mb-3">
                            <img src="<%= request.getContextPath()%>/media/images/mizuki.png" alt="User Avatar" class="comment-avatar mr-2">
                            <span>Comment as <strong><%= (String) ((model.Users) session.getAttribute("currentUser")).getUsername()%></strong></span>
                        </div>
                        <textarea name="commentDescription" placeholder="What are your thoughts?" required></textarea>
                        <input type="hidden" name="userId" value="<%= ((model.Users) session.getAttribute("currentUser")).getUserid()%>">
                        <input type="hidden" name="threadId" value="<%= session.getAttribute("selectedThreadID")%>">
                        <input type="hidden" name="commentIdReplyTo" value="">
                        <div class="flex justify-end">
                            <button type="submit" class="btn-comment">Comment</button>
                        </div>
                    </form>
                </div>

                <!-- Comment Section -->
                <div class="comment-section">
                    <% List<model.Threadcomment> comments = (List<model.Threadcomment>) session.getAttribute("selectedThreadCommentList"); %>
                    <% Map<String, List<model.Threadcomment>> repliesMap = (Map<String, List<model.Threadcomment>>) session.getAttribute("repliesMap"); %>
                    <% Map<String, Boolean> commentVotes = (Map<String, Boolean>) session.getAttribute("commentVotes"); %>
                    <% if (comments != null && !comments.isEmpty()) { %>
                    <% for (model.Threadcomment comment : comments) {%>
                    <div class="comment-item" data-comment-id="<%= comment.getThreadcommentid()%>">
                        <img src="<%= request.getContextPath()%>/media/images/mizuki.png" alt="User Avatar" class="comment-avatar">
                        <div class="comment-content">
                            <div class="comment-author">
                                <%= ((String) ((model.Users) session.getAttribute("currentUser")).getUserid()).equals(comment.getUserid().getUserid())
                                        ? comment.getUserid().getUsername()
                                        : "Anonymous User - " + comment.getUserid().getUserid().replaceAll("^[A-Za-z]+", "")%>
                            </div>
                            <div class="comment-text"><%= comment.getContent()%></div>
                            <div class="comment-actions">
                                <button class="action-btn upvote" data-vote-type="true">
                                    <i class="bi <%= commentVotes != null && commentVotes.get(comment.getThreadcommentid()) != null && commentVotes.get(comment.getThreadcommentid()) ? "bi-hand-thumbs-up-fill" : "bi-hand-thumbs-up"%>"></i>
                                    <span><%= comment.getUpvote()%></span>
                                </button>
                                <button class="action-btn downvote" data-vote-type="false">
                                    <i class="bi <%= commentVotes != null && commentVotes.get(comment.getThreadcommentid()) != null && !commentVotes.get(comment.getThreadcommentid()) ? "bi-hand-thumbs-down-fill" : "bi-hand-thumbs-down"%>"></i>
                                    <span><%= comment.getDownvote()%></span>
                                </button>
                                <div class="comment-action reply" data-comment-id="<%= comment.getThreadcommentid()%>">
                                    <i class="bi bi-reply"></i>
                                    <span>Reply</span>
                                </div>
                                <% if (((model.Users) session.getAttribute("currentUser")).getUserid().equals(comment.getUserid().getUserid())) { %>
                                <div class="comment-action edit">
                                    <i class="bi bi-pencil-square"></i>
                                    <span>Edit</span>
                                </div>
                                <div class="comment-action delete">
                                    <i class="bi bi-trash"></i>
                                    <span>Delete</span>
                                </div>
                                <% } %>
                                <div class="comment-action report">
                                    <i class="bi bi-exclamation-circle"></i>
                                    <span>Report</span>
                                </div>
                            </div>

                            <!-- Toggle for replies -->
                            <%
                                if (repliesMap.get(comment.getThreadcommentid()) != null && !repliesMap.get(comment.getThreadcommentid()).isEmpty()) {
                            %>
                            <div class="toggle-replies" onclick="toggleReplies('<%= comment.getThreadcommentid()%>')">
                                <span>Show replies (<%= repliesMap.get(comment.getThreadcommentid()) != null ? repliesMap.get(comment.getThreadcommentid()).size() : 0%>)</span>
                            </div>
                            <%
                                }
                            %>

                            <!-- Replies Container -->
                            <div id="<%= comment.getThreadcommentid()%>-replies" class="replies-container replies-collapsed">
                                <% if (repliesMap != null) { %>
                                <% List<model.Threadcomment> replies = repliesMap.get(comment.getThreadcommentid()); %>
                                <% Map<String, Map<String, Boolean>> replyVotes = (Map<String, Map<String, Boolean>>) session.getAttribute("replyVotes"); %>
                                <% if (replies != null && !replies.isEmpty()) { %>
                                <% for (model.Threadcomment reply : replies) {%>
                                <div class="comment-item indented-comment" >
                                    <img src="<%= request.getContextPath()%>/media/images/mizuki.png" alt="User Avatar" class="comment-avatar">
                                    <div class="comment-content">
                                        <div class="comment-author">
                                            <%= ((String) ((model.Users) session.getAttribute("currentUser")).getUserid()).equals(reply.getUserid().getUserid())
                                                    ? reply.getUserid().getUsername()
                                                    : "Anonymous User - " + reply.getUserid().getUserid().replaceAll("^[A-Za-z]+", "")%>
                                        </div>
                                        <div class="comment-text"><%= reply.getContent()%></div>
                                        <div class="comment-actions">
                                            <button class="action-btn upvote" data-vote-type="true">
                                                <i class="bi <%= replyVotes != null && replyVotes.get(comment.getThreadcommentid()) != null && replyVotes.get(comment.getThreadcommentid()).get(reply.getThreadcommentid()) != null && replyVotes.get(comment.getThreadcommentid()).get(reply.getThreadcommentid()) ? "bi-hand-thumbs-up-fill" : "bi-hand-thumbs-up"%>"></i>
                                                <span><%= reply.getUpvote()%></span>
                                            </button>
                                            <button class="action-btn downvote" data-vote-type="false">
                                                <i class="bi <%= replyVotes != null && replyVotes.get(comment.getThreadcommentid()) != null && replyVotes.get(comment.getThreadcommentid()).get(reply.getThreadcommentid()) != null && !replyVotes.get(comment.getThreadcommentid()).get(reply.getThreadcommentid()) ? "bi-hand-thumbs-down-fill" : "bi-hand-thumbs-down"%>"></i>
                                                <span><%= reply.getDownvote()%></span>
                                            </button>
                                            <% if (((model.Users) session.getAttribute("currentUser")).getUserid().equals(reply.getUserid().getUserid())) { %>
                                            <div class="comment-action edit">
                                                <i class="bi bi-pencil-square"></i>
                                                <span>Edit</span>
                                            </div>
                                            <div class="comment-action delete">
                                                <i class="bi bi-trash"></i>
                                                <span>Delete</span>
                                            </div>
                                            <% } %>
                                            <div class="comment-action report">
                                                <i class="bi bi-exclamation-circle"></i>
                                                <span>Report</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% } %>
                                <% } %>
                                <% } %>
                            </div>
                        </div>
                    </div>
                    <% } %>
                    <% } else { %>
                    <p>No comments available.</p>
                    <% }%>
                </div>
            </div>
        </main>
    </body>
</html>