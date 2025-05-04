<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    
    <head>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum/forum_thread_list.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum/forum_thread_detail.css">
    </head>

    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="../js/mizukibase.js"></script>
    <script src="<%= request.getContextPath()%>/js/forum/forum_thread_detail.js"></script>

    <section id="forum" class="">
        <%request.setAttribute("pageTitle", "Thread Detail");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <main class="container mt-5 pt-5">
            <div class="thread-container">
                <!-- Back to Forum Link -->
                <a href="../../ViewForumEntryServlet" class="back-link">
                    <i class="fas fa-arrow-left"></i> Back to Forum
                </a>

                <!-- Thread Post Section -->
                <div class="thread-post">
                    <div class="thread-header">
                        <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Avatar" class="user-avatar">
                        <div>
                            <div class="font-bold">Username</div>
                            <div class="thread-meta">
                                <span>Posted 2 days ago</span>
                                <span class="dot"></span>
                                <span>Category: General</span>
                            </div>
                        </div>
                    </div>

                    <h1 class="font-serif text-3xl mb-3">Thread Title</h1>

                    <div class="thread-content">
                        <p>Thread Description</p>
                        <!-- Thread image if available -->
                        <img src="<%= request.getContextPath()%>/media/images/mizuki.png" alt="Thread Image" class="thread-image">
                    </div>

                    <!-- Thread Actions -->
                    <div class="thread-actions">
                        <div class="action-group">
                            <button class="action-btn">
                                <i class="fas fa-thumbs-up"></i>
                                <span>123</span>
                            </button>
                            <button class="action-btn">
                                <i class="fas fa-thumbs-down"></i>
                                <span>45</span>
                            </button>
                        </div>

                        <div class="action-group">
                            <button class="action-btn" style="display: block">
                                <i class="fas fa-pencil-alt"></i>
                                <span>Edit</span>
                            </button>
                            <button class="action-btn" style="display: block">
                                <i class="fas fa-trash"></i>
                                <span>Delete</span>
                            </button>
                            <button class="action-btn">
                                <i class="fas fa-exclamation-circle"></i>
                                <span>Report</span>
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Comment Form -->
                <div class="comment-form">
                    <div class="flex items-center mb-3">
                        <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Avatar" class="comment-avatar mr-2">
                        <span>Comment as <strong>Username</strong></span>
                    </div>
                    <textarea placeholder="What are your thoughts?"></textarea>
                    <div class="flex justify-end">
                        <button class="btn-comment">Comment</button>
                    </div>
                </div>

                <!-- Comment Section -->
                <div class="comment-section">
                    <div class="comment-count">Comments (2)</div>

                    <!-- Comment Item 1 with Replies -->
                    <div class="comment-item">
                        <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Avatar" class="comment-avatar">
                        <div class="comment-content">
                            <div class="comment-author">Anonymous</div>
                            <div class="comment-text">This is a sample comment content. It can span multiple lines and contain various opinions.</div>
                            <div class="comment-actions">
                                <div class="comment-action">
                                    <i class="fas fa-thumbs-up"></i>
                                    <span>10</span>
                                </div>
                                <div class="comment-action">
                                    <i class="fas fa-thumbs-down"></i>
                                    <span>2</span>
                                </div>
                                <div class="comment-action reply">
                                    <i class="fas fa-reply"></i>
                                    <span>Reply</span>
                                </div>
                                <div class="comment-action" style="display: block">
                                    <i class="fas fa-pencil-alt"></i>
                                    <span>Edit</span>
                                </div>
                                <div class="comment-action" style="display: block">
                                    <i class="fas fa-trash"></i>
                                    <span>Delete</span>
                                </div>
                                <div class="comment-action">
                                    <i class="fas fa-exclamation-circle"></i>
                                    <span>Report</span>
                                </div>
                            </div>

                            <!-- Toggle for replies -->
                            <div class="toggle-replies" onclick="toggleReplies('comment1')">
                                <i class="fas fa-chevron-down"></i> 
                                <span>Show replies (2)</span>
                            </div>

                            <!-- Replies Container -->
                            <div id="comment1-replies" class="replies-container replies-collapsed">
                                <!-- Nested Reply 1 -->
                                <div class="comment-item indented-comment">
                                    <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Avatar" class="comment-avatar">
                                    <div class="comment-content">
                                        <div class="comment-author">ReplyUser1</div>
                                        <div class="comment-text">I agree with your comment! Very insightful observations.</div>
                                        <div class="comment-actions">
                                            <div class="comment-action">
                                                <i class="fas fa-thumbs-up"></i>
                                                <span>3</span>
                                            </div>
                                            <div class="comment-action">
                                                <i class="fas fa-thumbs-down"></i>
                                                <span>0</span>
                                            </div>
                                            <div class="comment-action">
                                                <i class="fas fa-pencil-alt"></i>
                                                <span>Edit</span>
                                            </div>
                                            <div class="comment-action">
                                                <i class="fas fa-trash"></i>
                                                <span>Delete</span>
                                            </div>
                                            <div class="comment-action">
                                                <i class="fas fa-exclamation-circle"></i>
                                                <span>Report</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </section>

</html>
