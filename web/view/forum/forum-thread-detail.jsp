<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

<head>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum.css">
    <style>
        /* Reddit-like styling for thread detail page */
        .thread-container {
            max-width: 800px;
            margin: 0 auto;
            background: #242424;
            color: #cecece;
        }
        
        .thread-post {
            background: #2d2d35;
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
            border: 1px solid #3d3d45;
        }
        
        .thread-header {
            display: flex;
            align-items: center;
            margin-bottom: 1rem;
        }
        
        .thread-meta {
            font-size: 0.8rem;
            color: #9a9a9a;
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
        }
        
        .thread-meta .dot {
            height: 3px;
            width: 3px;
            border-radius: 50%;
            background: #9a9a9a;
            margin: 0 0.5rem;
        }
        
        .thread-content {
            margin-bottom: 1.5rem;
        }
        
        .thread-image {
            max-width: 100%;
            border-radius: 6px;
            margin: 1rem 0;
        }
        
        .vote-controls {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-right: 1rem;
        }
        
        .vote-btn {
            background: none;
            border: none;
            color: #9a9a9a;
            cursor: pointer;
            transition: color 0.2s ease;
        }
        
        .vote-btn:hover {
            color: #cecece;
        }
        
        .vote-btn.upvote:hover {
            color: #4978a8;
        }
        
        .vote-btn.downvote:hover {
            color: #a84978;
        }
        
        .action-btn {
            background: #333333;
            color: #cecece;
            border: none;
            border-radius: 4px;
            padding: 0.5rem 1rem;
            cursor: pointer;
            transition: background 0.2s ease;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        
        .action-btn:hover {
            background: #464646;
        }
        
        .comment-section {
            margin-top: 2rem;
        }
        
        .comment-form {
            background: #2d2d35;
            border-radius: 8px;
            padding: 1rem;
            margin-bottom: 2rem;
            border: 1px solid #3d3d45;
        }
        
        .comment-form textarea {
            width: 100%;
            background: #333340;
            color: #e0e0e0;
            border: 1px solid #4a4a55;
            border-radius: 4px;
            padding: 0.75rem;
            min-height: 100px;
            resize: vertical;
        }
        
        .comment-form textarea:focus {
            border-color: #4978a8;
            outline: none;
        }
        
        .comment-form button {
            background: #4978a8;
            color: #fff;
            border: none;
            border-radius: 4px;
            padding: 0.5rem 1rem;
            margin-top: 0.5rem;
            cursor: pointer;
            transition: background 0.2s ease;
        }
        
        .comment-form button:hover {
            background: #5989b9;
        }
        
        .comment-item {
            background: #2d2d35;
            border-radius: 8px;
            padding: 1rem;
            margin-bottom: 1rem;
            display: flex;
            gap: 1rem;
            border: 1px solid #3d3d45;
        }
        
        .comment-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }
        
        .comment-content {
            flex: 1;
        }
        
        .comment-author {
            font-weight: bold;
            margin-bottom: 0.25rem;
            color: #e0e0e0;
        }
        
        .comment-text {
            color: #d5d5d5;
            margin-bottom: 0.5rem;
        }
        
        .comment-actions {
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 1rem;
            font-size: 0.8rem;
        }
        
        .comment-action {
            color: #9a9a9a;
            cursor: pointer;
            transition: color 0.2s ease;
            display: flex;
            align-items: center;
            gap: 0.25rem;
        }
        
        .comment-action:hover {
            color: #e0e0e0;
        }
        
        .comment-action.reply:hover {
            color: #4978a8;
        }
        
        /* Nested comments styling */
        .replies-container {
            margin-top: 1rem;
            margin-left: 1rem;
            border-left: 2px solid #3d3d45;
            padding-left: 1rem;
        }
        
        .replies-collapsed {
            display: none;
        }
        
        .toggle-replies {
            color: #4978a8;
            cursor: pointer;
            font-size: 0.85rem;
            margin-top: 0.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            user-select: none;
        }
        
        .toggle-replies:hover {
            text-decoration: underline;
        }
        
        .indented-comment {
            margin-top: 1rem;
        }
        
        .comment-count {
            margin-bottom: 1rem;
            font-size: 1.25rem;
            font-weight: bold;
        }
        
        .thread-actions {
            display: flex;
            justify-content: space-between;
            margin-bottom: 1rem;
        }
        
        .action-group {
            display: flex;
            gap: 0.75rem;
        }
        
        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 0.75rem;
        }
        
        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            color: #9a9a9a;
            font-size: 0.9rem;
            margin-bottom: 1rem;
            text-decoration: none;
            transition: color 0.2s ease;
        }
        
        .back-link:hover {
            color: #cecece;
        }
    </style>
</head>

<jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

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
                        <button class="action-btn">
                            <i class="fas fa-comment"></i>
                            <span>Comments</span>
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
                                        <div class="comment-action reply">
                                            <i class="fas fa-reply"></i>
                                            <span>Reply</span>
                                        </div>
                                        <div class="comment-action">
                                            <i class="fas fa-exclamation-circle"></i>
                                            <span>Report</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Nested Reply 2 -->
                            <div class="comment-item indented-comment">
                                <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Avatar" class="comment-avatar">
                                <div class="comment-content">
                                    <div class="comment-author">ReplyUser2</div>
                                    <div class="comment-text">I have a slightly different perspective on this. I think...</div>
                                    <div class="comment-actions">
                                        <div class="comment-action">
                                            <i class="fas fa-thumbs-up"></i>
                                            <span>2</span>
                                        </div>
                                        <div class="comment-action">
                                            <i class="fas fa-thumbs-down"></i>
                                            <span>1</span>
                                        </div>
                                        <div class="comment-action reply">
                                            <i class="fas fa-reply"></i>
                                            <span>Reply</span>
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
                
                <!-- Comment Item 2 with Replies -->
                <div class="comment-item">
                    <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Avatar" class="comment-avatar">
                    <div class="comment-content">
                        <div class="comment-author">AnotherUser</div>
                        <div class="comment-text">Great post! I really enjoyed reading this thread. Looking forward to more content like this.</div>
                        <div class="comment-actions">
                            <div class="comment-action">
                                <i class="fas fa-thumbs-up"></i>
                                <span>5</span>
                            </div>
                            <div class="comment-action">
                                <i class="fas fa-thumbs-down"></i>
                                <span>0</span>
                            </div>
                            <div class="comment-action reply">
                                <i class="fas fa-reply"></i>
                                <span>Reply</span>
                            </div>
                            <div class="comment-action">
                                <i class="fas fa-exclamation-circle"></i>
                                <span>Report</span>
                            </div>
                        </div>
                        
                        <!-- Toggle for replies -->
                        <div class="toggle-replies" onclick="toggleReplies('comment2')">
                            <i class="fas fa-chevron-down"></i> 
                            <span>Show replies (1)</span>
                        </div>
                        
                        <!-- Replies Container -->
                        <div id="comment2-replies" class="replies-container replies-collapsed">
                            <!-- Nested Reply -->
                            <div class="comment-item indented-comment">
                                <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Avatar" class="comment-avatar">
                                <div class="comment-content">
                                    <div class="comment-author">ThreadAuthor</div>
                                    <div class="comment-text">Thank you for your positive feedback! I'm glad you enjoyed it.</div>
                                    <div class="comment-actions">
                                        <div class="comment-action">
                                            <i class="fas fa-thumbs-up"></i>
                                            <span>2</span>
                                        </div>
                                        <div class="comment-action">
                                            <i class="fas fa-thumbs-down"></i>
                                            <span>0</span>
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

<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<script src="../js/mizukibase.js"></script>
<script>
    // Function to toggle comment replies
    function toggleReplies(commentId) {
        const repliesContainer = document.getElementById(commentId + '-replies');
        const toggleButton = repliesContainer.previousElementSibling;
        const toggleIcon = toggleButton.querySelector('i');
        const toggleText = toggleButton.querySelector('span');
        
        if (repliesContainer.classList.contains('replies-collapsed')) {
            // Show replies
            repliesContainer.classList.remove('replies-collapsed');
            toggleIcon.classList.remove('fa-chevron-down');
            toggleIcon.classList.add('fa-chevron-up');
            toggleText.textContent = toggleText.textContent.replace('Show', 'Hide');
        } else {
            // Hide replies
            repliesContainer.classList.add('replies-collapsed');
            toggleIcon.classList.remove('fa-chevron-up');
            toggleIcon.classList.add('fa-chevron-down');
            toggleText.textContent = toggleText.textContent.replace('Hide', 'Show');
        }
    }
</script>
</html>