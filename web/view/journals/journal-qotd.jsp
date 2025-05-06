<%-- 
    Document   : journal-qotd
    Created on : Mar 6, 2025, 4:32:58 PM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/journal-qotd.css">

    <section id="journal" class="incsec">
        <% request.setAttribute("pageTitle", "QOTD");%>
        <jsp:include page="../../shared/title.jsp" />
        <%@ include file="../../shared/header.jsp" %>

        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

        <main class="container mt-5 pt-5 qotd">
            <h2>Questions of the Day (QOTD)</h2>

            <!-- TODAY'S QUESTION -->
            <c:if test="${not empty sessionScope.unanswered}">
                <!-- TODAY'S QUESTION -->
                <c:if test="${not empty sessionScope.unanswered}">
                    <c:if test="${not empty sessionScope.unanswered}">
                        <h3 class="section-subtitle">TODAY'S QUESTION</h3>
                        <c:set var="firstUnanswered" value="${sessionScope.unanswered[0]}" />

                        <c:set var="alreadyAnswered" value="false" />
                        <c:forEach var="response" items="${sessionScope.userResponses}">
                            <c:if test="${response.questionid.questionid == firstUnanswered.questionid}">
                                <c:set var="alreadyAnswered" value="true" />
                            </c:if>
                        </c:forEach>

                        <div class="today-qotd-container">
                            <div class="today-qotd-card">
                                <div class="qotd-color-markers">
                                    <div class="color-marker orange"></div>
                                    <div class="color-marker yellow"></div>
                                    <div class="color-marker blue"></div>
                                </div>
                                <div class="qotd-content">
                                    <div class="today-qotd-question">${firstUnanswered.questiondescription}</div>
                                </div>
                                <div class="today-qotd-actions">
                                    <c:choose>
                                        <c:when test="${alreadyAnswered}">
                                            <span class="answered-message">✅ You’ve answered today’s question. Wait for tomorrow!</span>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="today-qotd-action-btn"
                                                    data-bs-toggle="modal" data-bs-target="#answerModal"
                                                    onclick="loadAnswerModal('${firstUnanswered.questiondescription}', '${firstUnanswered.questionid}')">
                                                ANSWER
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:if>

                <!-- UNLOCKED QUESTIONS -->
                <c:if test="${fn:length(sessionScope.unanswered) > 1}">
                    <h3 class="section-subtitle">UNLOCKED QUESTIONS</h3>
                    <div class="qotd-container">
                        <c:forEach var="question" begin="1" end="${fn:length(sessionScope.unanswered)-1}" varStatus="status">
                            <c:if test="${status.index <= 3}">
                                <div class="qotd-card locked">
                                    <div class="qotd-color-markers">
                                        <div class="color-marker orange"></div>
                                        <div class="color-marker yellow"></div>
                                        <div class="color-marker blue"></div>
                                    </div>
                                    <div class="qotd-content">
                                        <div class="qotd-question">${sessionScope.unanswered[status.index].questiondescription}</div>
                                        <div class="lock-overlay">
                                            <div class="lock-icon">🔒</div>
                                            <div class="unlock-text">
                                                <c:choose>
                                                    <c:when test="${status.index == 1}">UNLOCK TOMORROW</c:when>
                                                    <c:when test="${status.index == 2}">UNLOCK IN 2 DAYS</c:when>
                                                    <c:when test="${status.index == 3}">UNLOCK IN 3 DAYS</c:when>
                                                    <c:otherwise>UNLOCK IN ${status.index} DAYS</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
            </c:if>

            <!-- PREVIOUS QUESTIONS -->
            <c:if test="${not empty sessionScope.answered}">
                <h3 class="section-subtitle">PREVIOUS QUESTIONS</h3>
                <div class="qotd-container">
                    <c:forEach var="question" items="${sessionScope.answered}">
                        <c:set var="matched" value="false" />
                        <c:forEach var="response" items="${sessionScope.userResponses}">
                            <c:if test="${!matched && response.questionid.questionid == question.questionid}">
                                <div class="qotd-card previous ${response.isarchived ? 'archived' : ''}">
                                    <c:if test="${response.isarchived}">
                                        <div class="archive-indicator"></div>
                                    </c:if>
                                    <div class="qotd-color-markers">
                                        <div class="color-marker orange"></div>
                                        <div class="color-marker yellow"></div>
                                        <div class="color-marker blue"></div>
                                    </div>
                                    <div class="qotd-content">
                                        <div class="qotd-question">${question.questiondescription}</div>
                                        <c:choose>
                                            <c:when test="${response.isarchived}">
                                                <div class="answered-archived-container">
                                                    <div class="archived-badge">ARCHIVED</div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="answered-badge">ANSWERED</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="qotd-actions">
                                        <button class="qotd-action-btn" data-bs-toggle="modal" data-bs-target="#viewModal"
                                                onclick="loadViewData('${question.questiondescription}', '${response.responsedescription}')">VIEW</button>
                                        <button class="qotd-action-btn" data-bs-toggle="modal" data-bs-target="#editModal"
                                                onclick="loadQuestionData('${question.questiondescription}', '${response.responsedescription}', '${response.responseid}', '${response.isarchived}')">EDIT</button>

                                    </div>
                                </div>
                                <c:set var="matched" value="true" />
                            </c:if>


                        </c:forEach>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty sessionScope.unanswered && empty sessionScope.answered}">
                <p>No questions available at the moment.</p>
            </c:if>

            <c:if test="${fn:length(sessionScope.answered) > 3}">
                <button class="view-more-btn">VIEW MORE PREVIOUS QUESTIONS</button>
            </c:if>
        </main>
    </section>

    <!-- Answer Modal -->
    <div class="modal fade" id="answerModal" tabindex="-1" aria-labelledby="answerModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <form method="post" action="${pageContext.request.contextPath}/AnswerQOTDServlet">
                    <div class="modal-header">
                        <h5 class="modal-title" id="answerModalLabel">ANSWER QUESTION</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-4">
                            <label class="form-label">QUESTION:</label>
                            <div class="form-control bg-light" id="answerQuestionDisplay" style="font-weight: bold;"></div>
                        </div>
                        <div class="mb-4">
                            <label for="answerText" class="form-label">YOUR ANSWER:</label>
                            <textarea class="form-control answer" id="answerText" name="answerText" rows="5"
                                      placeholder="Write your answer here..." required></textarea>
                        </div>

                        <!-- Hidden field to store question ID -->
                        <input type="hidden" id="questionIdInput" name="questionId" />

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">CANCEL</button>
                        <button type="submit" class="btn btn-primary">SUBMIT ANSWER</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <!-- Edit Modal -->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <form method="post" action="${pageContext.request.contextPath}/EditAnswerQOTDServlet">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">EDIT ANSWER</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-4">
                            <label class="form-label">QUESTION:</label>
                            <div class="form-control bg-light" id="editQuestionDisplay" style="font-weight: bold;"></div>
                        </div>
                        <div class="mb-4">
                            <label for="questionAnswer" class="form-label">YOUR ANSWER:</label>
                            <textarea class="form-control answer" id="questionAnswer" name="answerText" rows="5"
                                      placeholder="Write your answer here..."></textarea>
                        </div>
                        <!-- Hidden field to store response ID -->
                        <input type="hidden" id="responseIdInput" name="responseId" />
                        <input type="hidden" id="isArchivedInput" name="isArchived" />
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger me-auto" id="archiveBtn">ARCHIVE</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">CANCEL</button>
                        <button type="submit" class="btn btn-primary">SAVE CHANGES</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <!-- View Modal -->
    <div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="viewModalLabel">VIEW QUESTION & ANSWER</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-2">
                        <span class="badge bg-success ms-2">ANSWERED</span>
                    </div>
                    <div class="mb-4">
                        <h4 class="mb-3" id="viewQuestionTitle"></h4>
                        <div class="card">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <span>YOUR ANSWER</span>
                                <small class="text-muted" id="viewAnswerDate"></small>
                            </div>
                            <div class="card-body">
                                <p class="card-text" id="viewQuestionAnswer"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div>
    </div>
</html>

<script src="../js/mizukibase.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js">
</script>
<script>
    // Function to load question data into the answer modal
    function loadAnswerModal(questionDesc, questionId) {
        document.getElementById('answerQuestionDisplay').innerText = questionDesc;
        document.getElementById('questionIdInput').value = questionId;
        document.getElementById('answerText').value = '';
    }

    function loadQuestionData(questionDescription, answerDescription, responseId, isArchived) {
        document.getElementById("editQuestionDisplay").innerText = questionDescription;
        document.getElementById("questionAnswer").value = answerDescription;
        document.getElementById("responseIdInput").value = responseId;
        const isArchivedInput = document.getElementById("isArchivedInput");
        isArchivedInput.value = isArchived;
        document.getElementById('editModalLabel').textContent = 'EDIT ANSWER';

        const archiveBtn = document.getElementById('archiveBtn');
        updateArchiveButtonStyle(archiveBtn, isArchived);

        const newArchiveBtn = archiveBtn.cloneNode(true);
        archiveBtn.parentNode.replaceChild(newArchiveBtn, archiveBtn);
        newArchiveBtn.addEventListener('click', () => {
            const current = isArchivedInput.value === 'true';
            const newValue = !current;
            isArchivedInput.value = newValue.toString();
            updateArchiveButtonStyle(newArchiveBtn, newValue.toString());
        });
    }

    function updateArchiveButtonStyle(button, isArchived) {
        if (isArchived === 'true') {
            button.textContent = 'UNARCHIVE';
            button.classList.remove('btn-danger');
            button.classList.add('btn-warning');
        } else {
            button.textContent = 'ARCHIVE';
            button.classList.remove('btn-warning');
            button.classList.add('btn-danger');
        }
    }

    function loadViewData(question, answer, date, isArchived) {
        document.getElementById('viewQuestionTitle').textContent = question;
        document.getElementById('viewQuestionAnswer').textContent = answer;
        document.getElementById('viewAnswerDate').textContent = date || '';

        const archivedBadge = document.getElementById('archivedBadge');
        if (archivedBadge) {
            archivedBadge.style.display = isArchived ? 'inline-block' : 'none';
        }

        const editBtn = document.getElementById('goToEditBtn');
        if (editBtn) {
            editBtn.onclick = function () {
                setTimeout(function () {
                    loadQuestionData(question, answer, isArchived);
                }, 500);
            };
        }
    }

    document.addEventListener('DOMContentLoaded', function () {
        // ARCHIVE BUTTON
        const archiveBtn = document.getElementById('archiveBtn');
        if (archiveBtn) {
            archiveBtn.addEventListener('click', function () {
                if (this.textContent === 'ARCHIVE') {
                    this.textContent = 'UNARCHIVE';
                    this.classList.remove('btn-danger');
                    this.classList.add('btn-warning');
                } else {
                    this.textContent = 'ARCHIVE';
                    this.classList.remove('btn-warning');
                    this.classList.add('btn-danger');
                }
            });
        }

        // VIEW MORE / HIDE PREVIOUS
        const viewMoreBtn = document.querySelector('.view-more-btn');
        const previousQuestions = document.querySelectorAll('.qotd-card.previous');
        const limit = 3;
        function updateVisibility(showAll) {
            previousQuestions.forEach((card, index) => {
                card.style.display = (showAll || index < limit) ? 'flex' : 'none';
            });
        }
        if (viewMoreBtn) {
            updateVisibility(false);
            let expanded = false;
            viewMoreBtn.addEventListener('click', function () {
                expanded = !expanded;
                updateVisibility(expanded);
                viewMoreBtn.textContent = expanded ? 'HIDE PREVIOUS QUESTIONS' : 'VIEW MORE PREVIOUS QUESTIONS';
            });
        }

        // ✅ QOTD Answer Date Control
        const todayKey = 'qotd_answered_date';
        const today = new Date().toISOString().slice(0, 10); // YYYY-MM-DD
        const answeredDate = localStorage.getItem(todayKey);
        const hasAnsweredToday = answeredDate === today;

        const answerBtn = document.querySelector('.today-qotd-action-btn');

        if (answerBtn) {
            if (hasAnsweredToday) {
                const message = document.createElement('span');
                message.textContent = '✅ You’ve answered today’s question. Wait for tomorrow!';
                message.classList.add('text-muted', 'fw-bold');

                answerBtn.replaceWith(message);
            } else {
                const answerForm = document.querySelector('#answerModal form');
                if (answerForm) {
                    answerForm.addEventListener('submit', () => {
                        localStorage.setItem(todayKey, today);

                        const answerText = document.getElementById('answerText').value;
                        const questionId = document.getElementById('questionIdInput').value;
                        const responses = JSON.parse(localStorage.getItem('qotd_responses') || '{}');

                        responses[today] = {
                            answer: answerText,
                            questionId: questionId,
                            responseId: Date.now().toString(),
                            isArchived: false
                        };
                        localStorage.setItem('qotd_responses', JSON.stringify(responses));
                    });
                }
            }
        }

        // UNLOCK TEXT CONTROL
        const unlockTexts = document.querySelectorAll('.unlock-text');
        unlockTexts.forEach(function (unlockText, index) {
            const daysToUnlock = index + 1;
            unlockText.innerHTML = `UNLOCK IN ` + daysToUnlock + ` DAYS`;
        });
    });
</script>

<style>
    .answer {
        font-family: Arial, sans-serif;
    }

    /* Modal styling for light theme */
    [data-theme="mizuki_light"] .modal-content {
        background-color: #FFFFFF;
        color: #333333;
        border: 1px solid #E0E0E0;
    }

    [data-theme="mizuki_light"] .modal-header {
        border-bottom: 1px solid #EEEEEE;
    }

    [data-theme="mizuki_light"] .modal-footer {
        border-top: 1px solid #EEEEEE;
    }


    [data-theme="mizuki_light"] .card {
        background-color: var(--card-bg);
        border: 1px solid var(--card-border);
    }

    [data-theme="mizuki_light"] .card-header {
        background-color: var(--input-bg);
        border-bottom: 1px solid var(--card-border);
    }

    /* Modal styling for dark theme */
    [data-theme="mizuki_dark"] .modal-content {
        background-color: #333333;
        color: #E9E9E9;
        border: 1px solid #404040;
    }
    [data-theme="mizuki_dark"] ::placeholder {
        color: #9e9b9b;
    }

    [data-theme="mizuki_dark"] .modal-header {
        border-bottom: 1px solid #484848;
    }

    [data-theme="mizuki_dark"] .modal-footer {
        border-top: 1px solid #484848;
    }

    [data-theme="mizuki_dark"] .card {
        background-color: #333333;
        border: 1px solid #404040;
    }

    [data-theme="mizuki_dark"] .card-header {
        background-color: #2A2A2A;
        border-bottom: 1px solid #404040;
        color: #E9E9E9;
    }

    [data-theme="mizuki_dark"] .card-text{
        color: #E9E9E9;
    }

</style>