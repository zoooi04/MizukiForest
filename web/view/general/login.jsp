<%-- 
    Document   : login
    Created on : Mar 6, 2025, 10:41:03 AM
    Author     : JiaQuann
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html data-theme="mizuki_dark" class="dark">

    <%request.setAttribute("pageTitle", "Log In");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="../../shared/notLoginHeader.jsp"/>
    <body>


        <main class="main">
            <div class="bg-gradient"></div>

            <div class="card">
                <div class="card-body">
                    <h1>Log In</h1>

                    <form id="loginForm" action="${pageContext.request.contextPath}/login" method="POST">
                        <div class="form-control">
                            <input type="email" id="userEmail" name="userEmail" placeholder="Email" class="input" required>
                        </div>

                        <div class="form-control">
                            <input type="password" id="userPw" name="userPw" placeholder="Password" class="input" required>
                        </div>

                        <div class="form-control" style="margin-top: 1.5rem;">
                            <button type="submit" class="btn" style="width: 100%; background-color: #7c3aed; color: white;">Log In</button>
                        </div>
                    </form>

                </div>
            </div>
        </main>

        <!-- Error Modal -->
        <div id="errorModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title">Login Failed</h2>
                    <button class="close">&times;</button>
                </div>
                <div class="modal-body">
                    <p id="errorModalMessage">The email or password is incorrect.</p>
                </div>
                <div class="modal-footer">
                    <button class="btn-dismiss">OK</button>
                </div>
            </div>
        </div>

        <script>
            <c:if test="${not empty sessionScope.errorMessage}">
            document.addEventListener('DOMContentLoaded', function () {
                const loginForm = document.getElementById('loginForm');
                const errorModal = document.getElementById('errorModal');
                const closeBtn = document.querySelector('.close');
                const dismissBtn = document.querySelector('.btn-dismiss');

                function showModal() {
                    errorModal.classList.add('show');
                }
                function hideModal() {
                    errorModal.classList.remove('show');
                }

                closeBtn.addEventListener('click', hideModal);
                dismissBtn.addEventListener('click', hideModal);
                window.addEventListener('click', function (event) {
                    if (event.target === errorModal) {
                        hideModal();
                    }
                });
            });
            </c:if>
        </script>


        <c:if test="${not empty sessionScope.errorMessage}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const modal = document.getElementById("errorModal");
                    const messageContainer = document.getElementById("errorModalMessage");
                    messageContainer.textContent = "${sessionScope.errorMessage}";
                    modal.classList.add("show");
                    const closeBtn = document.querySelector("#errorModal .close");
                    const overlay = document.querySelector("#errorModal .modal-overlay");
                    if (closeBtn) {
                        closeBtn.addEventListener("click", function () {
                            modal.classList.remove("show");
                        });
                    }

                    if (overlay) {
                        overlay.addEventListener("click", function () {
                            modal.classList.remove("show");
                        });
                    }
                });
            </script>
        </c:if>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                clearAllFocusSessionCookies();
            });

            /**
             * Completely clears all focus session cookies
             * This function can be used on any page to ensure all session cookies are removed
             */
            function clearAllFocusSessionCookies() {
                // List of all focus session related cookies
                const cookiesToClear = [
                    // General focus session cookies
                    'focusSessionType',
                    'focusSessionStartTime',
                    'focusSessionTimeRemaining',
                    'focusSessionInitialDuration',
                    'focusSessionElapsedTime',
                    'focusSessionPomodoroState', // In case you add this in the future

                    // Pomodoro-specific cookies
                    'pomodoroWorkDuration',
                    'pomodoroShortBreak',
                    'pomodoroLongBreak',
                    'pomodoroCycles',
                    'pomodoroCurrentCycle',
                    'pomodoroIsWorkPhase',
                    'pomodoroTotalWorkTime',
                    'pomodoroIsLongBreak'
                ];

                // Clear each cookie using multiple methods to ensure they're removed
                cookiesToClear.forEach(cookieName => {
                    // Method 1: Set expiry in the past with setCookie function
                    setCookie(cookieName, '', -1);
                    // Method 2: Standard cookie deletion approach
                    document.cookie = `${cookieName}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
                    // Method 3: Also try with different paths in case cookies were set with different paths
                    document.cookie = `${cookieName}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/MizukiForest/;`;
                    document.cookie = `${cookieName}=; expires=Thu, 01 Jan 1970 00:00:00 UTC;`;
                });

                console.log('All focus session cookies have been cleared');
            }

            /**
             * Helper function to set cookies (include this if not already in your code)
             */
            function setCookie(name, value, days) {
                const d = new Date();
                d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
                const expires = "expires=" + d.toUTCString();
                document.cookie = name + "=" + value + ";" + expires + ";path=/";
            }
        </script>




    </body>
</html>