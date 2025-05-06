<%-- 
    Document   : signup
    Created on : May 04, 2025
    Author     : JiaQuann
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html data-theme="mizuki_dark" class="dark">

    <%request.setAttribute("pageTitle", "Sign Up");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="../../shared/notLoginHeader.jsp"/>
    <body>

        <main class="main">
            <div class="bg-gradient"></div>

            <div class="card">
                <div class="card-body">
                    <h1>Sign Up</h1>

                    <form id="signupForm" action="${pageContext.request.contextPath}/saveSignupTemp" method="POST"  autocomplete="off">
                        <div class="form-control" hidden>
                            <input type="text" id="userName" name="userName" placeholder="Username" class="input">
                            <input type="date" id="userBirthday" name="userBirthday" placeholder="Birthday" class="input">
                        </div>

                        <div class="form-control">
                            <input type="email" id="userEmail" name="userEmail" placeholder="Email" class="input" autocomplete="off" required>
                            <c:if test="${param.error == 'EmailExists'}">
                                <div style="color: red; font-size: 0.55rem; margin-top: 0.25rem; display: flex; align-items: center;">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red" viewBox="0 0 256 256" style="margin-right: 5px;">
                                    <path d="M128,24A104,104,0,1,0,232,128,104.11,104.11,0,0,0,128,24Zm-8,56a8,8,0,0,1,16,0v56a8,8,0,0,1-16,0Zm8,104a12,12,0,1,1,12-12A12,12,0,0,1,128,184Z"></path>
                                    </svg>
                                    This email is already registered. Please use a different email.
                                </div>
                            </c:if>
                        </div>
                        <div class="form-control">
                            <input type="password" id="userPw" name="userPw" placeholder="Password (12 characters minimum)" class="input" minlength="12" autocomplete="new-password" required>
                        </div>

                        <div class="form-control">
                            <input type="password" id="confirmPw" name="confirmPw" placeholder="Confirm Password" class="input" required>
                        </div>

                        <div class="form-control">
                            <label class="checkbox-container">
                                <input type="checkbox" id="termsCheck" name="termsCheck" required>
                                <span class="checkmark"></span>
                                <span class="label-text">I agree to the <a href="<%= request.getContextPath()%>/view/general/term.jsp"class="link">Terms of Service</a> and <a href="<%= request.getContextPath()%>/view/general/privacy.jsp" class="link">Privacy Policy</a></span>
                            </label>
                        </div>

                        <div class="form-control" style="margin-top: 1.5rem;">
                            <button type="submit" class="btn" style="width: 100%; background-color: #7c3aed; color: white;">Create Account</button>
                        </div>
                    </form>

                    <script>
                        document.getElementById("userName").value = "user_2004";

                        const today = new Date();
                        today.setFullYear(today.getFullYear() - 14);
                        const formattedDate = today.toISOString().split("T")[0];
                        document.getElementById("userBirthday").value = formattedDate;
                    </script>


                </div>
            </div>
        </main>

        <!-- Error Modal -->
        <div id="errorModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title">Registration Failed</h2>
                    <button class="close">&times;</button>
                </div>
                <div class="modal-body">
                    <p id="errorModalMessage">An error occurred during registration.</p>
                </div>
                <div class="modal-footer">
                    <button class="btn-dismiss">OK</button>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const signupForm = document.getElementById('signupForm');
                const errorModal = document.getElementById('errorModal');
                const closeBtn = document.querySelector('.close');
                const dismissBtn = document.querySelector('.btn-dismiss');
                const userPw = document.getElementById('userPw');
                const confirmPw = document.getElementById('confirmPw');

                // Password validation
                signupForm.addEventListener('submit', function (event) {
                    if (userPw.value !== confirmPw.value) {
                        event.preventDefault();
                        document.getElementById('errorModalMessage').textContent = "Passwords do not match!";
                        errorModal.classList.add('show');
                        return false;
                    }

                    if (userPw.value.length < 12) {
                        event.preventDefault();
                        document.getElementById('errorModalMessage').textContent = "Password must be at least 12 characters long!";
                        errorModal.classList.add('show');
                        return false;
                    }

                    return true;
                });

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


    </body>
</html>