<%-- 
    Document   : diary
    Created on : Mar 6, 2025, 4:32:58 PM
    Author     : JiaQuann
--%>

<%@page import="model.Usertag"%>
<%@page import="java.util.List"%>
<%@page import="model.Tag"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_light">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Set today's date as default
            const urlParams = new URLSearchParams(window.location.search);
            const dateParam = urlParams.get('date');
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('datewritten').value = dateParam || today;


            // Auto-resize textarea as user types
            const textarea = document.getElementById('description');
            textarea.addEventListener('input', function () {
                this.style.height = 'auto';
                this.style.height = (this.scrollHeight) + 'px';
            });

            // Tags management
            const newTagInput = document.getElementById('newTagInput');
            const addTagBtn = document.getElementById('addTagBtn');
            const selectedTagsDisplay = document.getElementById('selectedTags');
            const entryTagsInput = document.getElementById('entryTags');
            const popularTags = document.querySelectorAll('.popular-tag');

            let tags = [];

            // Add tag function
            function addTag(tagText) {
                tagText = tagText.trim().toLowerCase();

                // Validate tag
                if (tagText === '')
                    return;
                if (tags.includes(tagText))
                    return;

                // Add to tags array
                tags.push(tagText);

                // Create tag element
                const tagElement = document.createElement('span');
                tagElement.className = 'tag';

                // Create text node for tag name
                const tagNameNode = document.createTextNode(tagText);
                tagElement.appendChild(tagNameNode);

                // Create remove button
                const removeBtn = document.createElement('span');
                removeBtn.className = 'remove-tag';
                removeBtn.innerHTML = '&times;'; // or "x"
                removeBtn.addEventListener('click', function () {
                    const index = tags.indexOf(tagText);
                    if (index > -1) {
                        tags.splice(index, 1);
                        updateHiddenInput();
                        tagElement.remove();
                    }
                });

                // Add remove button to tag
                tagElement.appendChild(removeBtn);

                // Add to display
                selectedTagsDisplay.appendChild(tagElement);

                // Update hidden input
                updateHiddenInput();

                // Clear input
                newTagInput.value = '';
                newTagInput.focus();
            }

            // Update hidden input with all tags
            function updateHiddenInput() {
                entryTagsInput.value = tags.join(',');
            }

            // Add tag button click
            addTagBtn.addEventListener('click', function () {
                addTag(newTagInput.value);
            });

            // Add tag on Enter key
            newTagInput.addEventListener('keypress', function (e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    addTag(this.value);
                }
            });

            // Popular tags click
            popularTags.forEach(tag => {
                tag.addEventListener('click', function () {
                    addTag(this.getAttribute('data-tag'));
                });
            });

            // Form submission handling (for demo purposes)
            document.getElementById('diaryForm').addEventListener('submit', function (e) {
                e.preventDefault();
                alert('Entry saved successfully with tags: ' + entryTagsInput.value);
                window.location.href = 'AddingDiaryPageServlet';
            });
        });
    </script>

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

    <section id="diary" class="incsec">
        <%request.setAttribute("pageTitle", "Diary Entries");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%--<%@ include file="../../shared/header.jsp" %>--%>

        <main class="container ">
            <div class="diary-add-container">
                <div class="row">
                    <div class="col-lg-8 mx-auto">
                        <div class="card diary-form-card">

                            <div class="card-header">
                                <h2 class="text-center">Create New Diary Entry</h2>
                            </div>

                            <div class="card-body">
                                <form id="diaryForm" action="${pageContext.request.contextPath}/AddDiaryEntryServlet" method="post">
                                    <!-- Date Selection -->
                                    <div class="form-group mb-4">
                                        <label for="datewritten" class="form-label ">Date</label>
                                        <input type="date" class="form-control" id="datewritten" name="datewritten" required>
                                    </div>

                                    <!-- Entry Title -->
                                    <div class="form-group mb-4">
                                        <label for="title" class="form-label">Title</label>
                                        <input type="text" class="form-control font" id="title" name="title" 
                                               placeholder="Give your entry a title" required>
                                    </div>

                                    <!-- Mood Selection -->
                                    <div class="form-group mb-4">
                                        <label class="form-label">Mood</label>
                                        <div class="mood-selector">
                                            <!--Excited-->
                                            <div class="mood-option">
                                                <input type="radio" name="mood" id="mood-excited" value="Excited">
                                                <label for="mood-excited" class="mood-label">
                                                    <div class="mood-icon">🤩</div>
                                                    <span>Excited</span>
                                                </label>
                                            </div>
                                            <!--Happy-->
                                            <div class="mood-option">
                                                <input type="radio" name="mood" id="mood-happy" value="Happy">
                                                <label for="mood-happy" class="mood-label">
                                                    <div class="mood-icon">😊</div>
                                                    <span>Happy</span>
                                                </label>
                                            </div>
                                            <!--Calm-->
                                            <div class="mood-option">
                                                <input type="radio" name="mood" id="mood-calm" value="Calm">
                                                <label for="mood-calm" class="mood-label">
                                                    <div class="mood-icon">😌</div>
                                                    <span>Calm</span>
                                                </label>
                                            </div>
                                            <!--Neutral-->
                                            <div class="mood-option">
                                                <input type="radio" name="mood" id="mood-neutral" value="Neutral" checked>
                                                <label for="mood-neutral" class="mood-label">
                                                    <div class="mood-icon">😐</div>
                                                    <span>Neutral</span>
                                                </label>
                                            </div>
                                            <!--Sad-->
                                            <div class="mood-option">
                                                <input type="radio" name="mood" id="mood-sad" value="Sad">
                                                <label for="mood-sad" class="mood-label">
                                                    <div class="mood-icon">😢</div>
                                                    <span>Sad</span>
                                                </label>
                                            </div>
                                            <!--Frustrated-->
                                            <div class="mood-option">
                                                <input type="radio" name="mood" id="mood-frustrated" value="Frustrated">
                                                <label for="mood-frustrated" class="mood-label">
                                                    <div class="mood-icon">😩</div>
                                                    <span>Frustrated</span>
                                                </label>
                                            </div>
                                            <!--Angry-->
                                            <div class="mood-option">
                                                <input type="radio" name="mood" id="mood-angry" value="Angry">
                                                <label for="mood-angry" class="mood-label">
                                                    <div class="mood-icon">😠</div>
                                                    <span>Angry</span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Entry Content -->
                                    <div class="form-group mb-4">
                                        <label for="description" class="form-label">Write your thoughts</label>
                                        <textarea class="form-control" id="description" name="description" 
                                                  rows="12" placeholder="Dear diary..." required></textarea>
                                    </div>

                                    <!-- Tags -->
                                    <div class="form-group mb-4">
                                        <label class="form-label">Tags</label>
                                        <div class="tags-container">
                                            <!-- Add new tag input -->
                                            <div class="tags-input-container">
                                                <input type="text" class="form-control" id="newTagInput" 
                                                       placeholder="Add a new tag">
                                                <button type="button" class="btn btn-outline-primary" id="addTagBtn">Add</button>
                                            </div>

                                            <!-- Popular tags suggestions -->
                                            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                                            <div class="popular-tags">
                                                <div class="popular-tags-title">Tags:</div>
                                                <%
                                                    List<Tag> allTags = (List<Tag>) request.getAttribute("allTags");
                                                    if (allTags != null) {
                                                        for (Tag tag : allTags) {
                                                %>
                                                <span class="popular-tag" data-tag="<%= tag.getTagname()%>"><%= tag.getTagname()%></span>
                                                <%
                                                        }
                                                    }
                                                %>
                                                <%
                                                    List<Usertag> userTags = (List<Usertag>) request.getAttribute("userTags");
                                                    if (userTags != null) {
                                                        for (Usertag usertag : userTags) {
                                                %>
                                                <span class="popular-tag" data-tag="<%= usertag.getNewtagname()%>"><%= usertag.getNewtagname()%></span>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </div>


                                            <!-- Selected tags display -->
                                            <div class="tags-display" id="selectedTags">
                                            </div>

                                            <!-- Hidden input to store all tags -->
                                            <input type="hidden" name="entryTags" id="entryTags" value="">
                                        </div>
                                    </div>

                                    <!-- Buttons -->
                                    <div class="form-buttons mt-5">
                                        <button type="button" class="btn btn-secondary" onclick="window.location.href = '<%= request.getContextPath()%>/view/journals/journal-diaries.jsp'">Cancel</button>
                                        <button type="submit" class="btn btn-primary">Save Entry</button>
                                    </div>
                                </form>

                                <br>
                                <p style="color:green;"><%= request.getAttribute("message") != null ? request.getAttribute("message") : ""%></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </section>


</html>

<script src="../js/mizukibase.js"></script>

<style>
    .container {
        max-width: 1200px;
        margin: 0 auto;
    }

    .page-header {
        background-color: #4a89dc;
        color: white;
        padding: 20px 0;
        margin-bottom: 30px;
        text-align: center;
    }

    .diary-add-container {
        padding: 20px 15px 50px;
    }

    .diary-form-card {
        border-radius: 10px;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        border: none;
        background-color: #fff;
    }

    .diary-form-card .card-header {
        background-color: #4a89dc;
        color: white;
        padding: 15px;
        border-radius: 10px 10px 0 0;
    }

    .diary-form-card .card-body {
        padding: 30px;
    }

    .form-label {
        font-weight: 600;
        color: #555;
        margin-bottom: 8px;
    }

    textarea {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .form-control {
        border-radius: 5px;
        border: 1px solid #ddd;
        padding: 12px 15px;
        transition: all 0.3s ease;
    }

    .form-control:focus {
        border-color: #4a89dc;
        box-shadow: 0 0 0 0.25rem rgba(74, 137, 220, 0.25);
    }

    textarea.form-control {
        resize: vertical;
        min-height: 200px;
    }

    /* Mood selector styling */
    .mood-selector {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
        margin-top: 10px;
    }

    .mood-option {
        position: relative;
    }

    .mood-option input[type="radio"] {
        position: absolute;
        opacity: 0;
    }

    .mood-label {
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px 15px;
        background-color: #f5f5f5;
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .mood-icon {
        font-size: 30px;
        margin-bottom: 5px;
    }

    .mood-option input[type="radio"]:checked + .mood-label {
        background-color: #4a89dc;
        color: white;
    }

    .font {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    /* Tags styling */
    .tags-container {
        margin-top: 10px;
    }

    .tags-input-container {
        display: flex;
        gap: 10px;
        margin-bottom: 15px;
    }

    #newTagInput {
        flex-grow: 1;
    }

    .tags-display {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
        margin-top: 15px;
    }

    .tag {
        display: inline-flex;
        align-items: center;
        background-color: #e9f0fd;
        color: #4a89dc;
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 14px;
        transition: all 0.2s ease;
    }

    .tag:hover {
        background-color: #d8e5fc;
    }

    .tag .remove-tag {
        margin-left: 5px;
        cursor: pointer;
        font-size: 16px;
        color: #666;
    }

    .tag .remove-tag:hover {
        color: #dc3545;
    }

    .popular-tags {
        margin-top: 15px;
    }

    .popular-tags-title {
        font-size: 14px;
        color: #666;
        margin-bottom: 10px;
    }

    .popular-tag {
        display: inline-block;
        background-color: #f0f0f0;
        color: #555;
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 14px;
        margin-right: 8px;
        margin-bottom: 8px;
        cursor: pointer;
        transition: all 0.2s ease;
    }

    .popular-tag:hover {
        background-color: #4a89dc;
        color: white;
    }

    /* Button styling */
    .form-buttons {
        display: flex;
        justify-content: space-between;
    }

    .btn {
        padding: 10px 25px;
        border-radius: 5px;
        font-weight: 600;
        transition: all 0.3s ease;
    }

    .btn-primary {
        background-color: #4a89dc;
        border: none;
    }

    .btn-primary:hover {
        background-color: #3a79cc;
        transform: translateY(-2px);
    }

    .btn-secondary {
        background-color: #f0f0f0;
        color: #555;
        border: none;
    }

    .btn-secondary:hover {
        background-color: #e0e0e0;
        transform: translateY(-2px);
    }

    /* Navigation */
    .nav-container {
        background-color: white;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        padding: 10px 0;
    }

    .nav-links {
        display: flex;
        justify-content: center;
        gap: 20px;
    }

    .nav-links a {
        color: #555;
        text-decoration: none;
        padding: 5px 10px;
        font-weight: 500;
        transition: all 0.3s ease;
    }

    .nav-links a:hover {
        color: #4a89dc;
    }

    .nav-links a.active {
        color: #4a89dc;
        border-bottom: 2px solid #4a89dc;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
        .mood-selector {
            justify-content: center;
        }

        .form-buttons {
            flex-direction: column-reverse;
            gap: 10px;
        }

        .form-buttons button {
            width: 100%;
        }

        .nav-links {
            flex-wrap: wrap;
        }
    }
</style>
