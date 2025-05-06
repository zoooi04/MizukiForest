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
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/journal-diaries_add.css">
    <style>
        .font {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
    </style>
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
                                        <textarea class="form-control font" id="description" name="description" 
                                                  rows="12" placeholder="Dear diary..." required></textarea>
                                    </div>

                                    <!-- Buttons -->
                                    <div class="form-buttons mt-5">
                                        <%
                                            String previousDate = request.getParameter("date");
                                            String cancelUrl = previousDate != null
                                                    ? request.getContextPath() + "/viewEntriesByDate?date=" + previousDate
                                                    : request.getContextPath() + "/ViewDateDiaryEntryServlet";
                                        %>
                                        <button type="button" class="btn btn-secondary" onclick="window.location.href = '<%= cancelUrl%>'">Cancel</button>
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

    <script src="../js/mizukibase.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
                                            document.body.setAttribute('data-theme', 'mizuki_dark');
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
                                                function addTag(tagId, tagLabel) {
                                                    tagId = tagId.trim();
                                                    if (tagId === '' || tags.includes(tagId))
                                                        return;

                                                    tags.push(tagId);

                                                    const tagElement = document.createElement('span');
                                                    tagElement.className = 'tag';
                                                    tagElement.textContent = tagLabel;

                                                    const removeBtn = document.createElement('span');
                                                    removeBtn.className = 'remove-tag';
                                                    removeBtn.innerHTML = '&times;';
                                                    removeBtn.addEventListener('click', function () {
                                                        const index = tags.indexOf(tagId);
                                                        if (index > -1) {
                                                            tags.splice(index, 1);
                                                            updateHiddenInput();
                                                            tagElement.remove();
                                                        }
                                                    });

                                                    tagElement.appendChild(removeBtn);
                                                    selectedTagsDisplay.appendChild(tagElement);
                                                    updateHiddenInput();
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
                                                        const tagId = this.getAttribute('data-tag');
                                                        const tagLabel = this.textContent;
                                                        addTag(tagId, tagLabel);
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
</html>

