<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

<head>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/forum.css">
    <style>
        .action-icons {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
        }

        .action-icons .left-icons,
        .action-icons .right-icons {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .action-icons .icon {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            cursor: pointer;
        }

        .comment-section .comment {
            display: flex;
            align-items: flex-start;
            gap: 1rem;
            margin-bottom: 1rem;
        }

        .comment-section .comment .comment-icons {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            margin-left: auto;
        }

        .comment-section .comment img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
        }

        .thread-detail img {
            display: block;
            margin: 0 auto;
        }
    </style>
</head>

<jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

<section id="forum" class="">
    <%request.setAttribute("pageTitle", "Thread Detail");%>
    <jsp:include page="../../shared/title.jsp"/>
    <%@ include file="../../shared/header.jsp" %>

    <main class="container mt-5 pt-5">
        <!-- Thread Details Section -->
        <div class="thread-detail">
            <h1 class="font-serif text-3xl text-center mb-4">Thread Title</h1>
            <p class="text-lg text-center text-secondary mb-4">Thread Description</p>
            <div class="flex justify-center mb-6">
                <img src="<%= request.getContextPath()%>/media/images/mizuki.png" alt="Thread Image" class="max-w-md rounded-lg shadow-md">
            </div>

            <!-- Action Icons -->
            <div class="action-icons">
                <div class="left-icons">
                    <div class="icon">
                        <i class="fas fa-thumbs-up"></i> <span>123</span>
                    </div>
                    <div class="icon">
                        <i class="fas fa-thumbs-down"></i> <span>45</span>
                    </div>
                </div>
                <div class="right-icons">
                    <div class="icon">
                        <i class="fas fa-exclamation-circle"></i> Report
                    </div>
                    <div class="icon" style="display: block">
                        <i class="fas fa-pencil-alt"></i> Edit
                    </div>
                    <div class="icon" style="display: block">
                        <i class="fas fa-trash"></i> Delete
                    </div>
                </div>
            </div>
        </div>

        <!-- Write Comment Section -->
        <div class="write-comment mb-6">
            <textarea class="form-control" placeholder="Write your comment here..."></textarea>
            <button class="btn btn-primary mt-2">Send</button>
        </div>

        <!-- Comment Section -->
        <div class="comment-section">
            <h2 class="font-serif text-2xl mb-4">Comments</h2>

            <!-- Example Comment -->
            <div class="comment">
                <img src="<%= request.getContextPath()%>/media/images/user-placeholder.png" alt="User Image">
                <div>
                    <p class="font-bold">Anonymous</p>
                    <p class="text-secondary">This is a sample comment content.</p>
                </div>
                <div class="comment-icons">
                    <div class="icon">
                        <i class="fas fa-thumbs-up"></i> <span>10</span>
                    </div>
                    <div class="icon">
                        <i class="fas fa-thumbs-down"></i> <span>2</span>
                    </div>
                    <div class="icon">
                        <i class="fas fa-exclamation-circle"></i>
                    </div>
                    <div class="icon" style="display: block">
                        <i class="fas fa-pencil-alt"></i>
                    </div>
                    <div class="icon" style="display: block">
                        <i class="fas fa-trash"></i>
                    </div>
                </div>
            </div>
        </div>
    </main>
</section>

<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<script src="../js/mizukibase.js"></script>
</html>
