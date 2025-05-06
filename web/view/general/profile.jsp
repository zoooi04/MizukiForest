<%-- 
    Document   : settings
    Created on : Mar 6, 2025, 4:32:58 PM
    Author     : JiaQuann
--%>

<%@page import="java.util.Collection"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="userDetails" scope="request" class="model.Users" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/profile.css">

    <section id="diary" class="incsec">
        <%request.setAttribute("pageTitle", "Profile");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <main class="container mt-5 pt-5">
            <div class="profile-container">
                <h1 class="profile-pixel-title">Edit Profile</h1>
                <div class="profile-color-bar"></div>

                <form id="profileForm" action="${pageContext.request.contextPath}/UpdateUserProfileDetails" method="POST" enctype="multipart/form-data" autocomplete="off">
                    <div class="profile-settings-section">
                        <h2 class="profile-settings-title">My Badges</h2>

                        <%-- This would be populated from your database in a real implementation --%>
                        <jsp:useBean id="userBadges" scope="request" class="java.util.ArrayList" />

                        <div class="profile-badge-container">
                            <%-- First Badge Slot --%>
                            <c:set var="selectedCount" value="0" scope="page" />

                            <c:forEach var="userBadge" items="${userBadges}">
                                <c:if test="${userBadge.isselected and selectedCount < 3}">
                                    <div class="profile-badge-item">
                                        <div class="profile-badge-slot">
                                            <img src="${pageContext.request.contextPath}${userBadge.badge.badgeimage}" alt="${userBadge.badge.badgename}">
                                        </div>
                                        <div class="profile-badge-info">
                                            ${userBadge.badge.badgename}
                                        </div>
                                    </div>
                                    <c:set var="selectedCount" value="${selectedCount + 1}" scope="page" />
                                </c:if>
                            </c:forEach>

                            <c:forEach begin="${selectedCount}" end="2">
                                <div class="profile-badge-item">
                                    <div class="profile-badge-slot">
                                        <div class="profile-badge-empty">
                                            <span>No Badge</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>





                        </div>
                    </div>

                    <!-- Profile Information Section -->
                    <div class="profile-settings-section">

                        <h2 class="profile-settings-title">Profile Information</h2>

                        <div class="profile-image-section">
                            <div class="profile-image-container">
                                <c:choose>
                                    <c:when test="${empty userDetails.userimage}">
                                        <img src="<%= request.getContextPath()%>/media/images/user-null.jpg" alt="Profile Image" id="imagePreview">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<%= request.getContextPath()%>/media/uploads/${userDetails.userimage}" alt="Profile Image" id="imagePreview">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="profile-image-buttons">
                                <input type="file" id="profileImageInput" name="imageFile" accept=".jpg,.jpeg,.png,.gif" style="display: none;">
                                <button type="button" class="profile-btn" onclick="document.getElementById('profileImageInput').click()">Change Image</button>
                            </div>
                        </div>

                        <div class="profile-form-group">
                            <label for="username">Username</label>
                            <input class="font" type="text" id="username" name="username" maxlength="100" required value="${userDetails.username}" 　autocomplete="oldPw">
                        </div>

                        <div class="profile-form-group">
                            <label for="email">Email Address</label>
                            <input class="font" type="email" id="email" name="useremail" maxlength="100" required value="${userDetails.useremail}" readonly>
                        </div>

                        <div class="profile-form-group">
                            <label for="userbirthday">Birthday</label>
                            <input type="date" id="userbirthday" name="userbirthday" required max=""
                                   value="<fmt:formatDate value='${userDetails.userbirthday}' pattern='yyyy-MM-dd'/>">
                        </div>
                    </div>

                    <!-- Account Stats Section -->
                    <div class="profile-settings-section">
                        <h2 class="profile-settings-title">Account Stats</h2>
                        <div class="profile-stats-grid">
                            <div class="profile-stat-card">
                                <div>Coins</div>
                                <div class="profile-stat-value" id="coinValue">${userDetails.coins}</div>
                            </div>
                            <div class="profile-stat-card">
                                <div>Level</div>
                                <div class="profile-stat-value" id="levelValue">${userDetails.userlevel}</div>
                            </div>
                            <div class="profile-stat-card">
                                <div>EXP</div>
                                <div class="profile-stat-value" id="expValue">${userDetails.exp}</div>
                            </div>
                            <div class="profile-stat-card">
                                <div>User ID</div>
                                <div class="profile-stat-value" style="font-size:19px; margin-top: 17px;" id="userIdValue">${userDetails.userid}</div>
                            </div>
                            <div class="profile-stat-card">
                                <div>Login Streak</div>
                                <div class="profile-stat-value" id="loginStreakValue">${userDetails.loginstreak}</div>
                            </div>
                            <div class="profile-stat-card">
                                <div>Pity</div>
                                <div class="profile-stat-value" id="loginPityValue">${userDetails.pity}</div>
                            </div>
                        </div>
                    </div>

                    <!-- Privacy Settings Section -->
                    <div class="profile-settings-section">
                        <h2 class="profile-settings-title">Privacy Settings</h2>

                        <div class="profile-visibility-option">
                            <div>
                                <label>Diary Visibility</label>
                                <div class="profile-info-text">Allow others to view your diary entries</div>
                            </div>
                            <label class="profile-toggle-switch">
                                <input type="checkbox" id="diaryvisibility" name="diaryvisibility"
                                       ${userDetails.diaryvisibility ? "checked" : ""}>
                                <span class="profile-slider"></span>
                            </label>
                        </div>

                        <div class="profile-visibility-option">
                            <div>
                                <label>Forest Visibility</label>
                                <div class="profile-info-text">Allow others to view your forest</div>
                            </div>
                            <label class="profile-toggle-switch">
                                <input type="checkbox" id="forestvisibility" name="forestvisibility"
                                       ${userDetails.forestvisibility ? "checked" : ""}>
                                <span class="profile-slider"></span>
                            </label>
                        </div>
                    </div>

                    <!-- Password Section -->
                    <div class="profile-settings-section">
                        <h2 class="profile-settings-title">Change Password</h2>

                        <div class="profile-form-group">
                            <label for="currentPassword">Current Password</label>
                            <input type="hidden" id="hiddenOldPassword" value="${userDetails.userpw}">
                            <input class="font" type="password" id="currentPassword" name="currentPassword"　autocomplete="oldPw" >
                        </div>

                        <div class="profile-form-group">
                            <label for="newPassword">New Password</label>
                            <input class="font" type="password" id="newPassword" name="newPassword" maxlength="20">
                        </div>

                        <div class="profile-form-group">
                            <label for="confirmPassword">Confirm New Password</label>
                            <input class="font" type="password" id="confirmPassword" name="confirmPassword" maxlength="20">
                        </div>
                    </div>

                    <button type="submit" class="profile-btn-save">Save Changes</button>
                </form>
            </div>
        </main>
    </section>
    <script>
        // Preview uploaded image
        document.getElementById('profileImageInput').addEventListener('change', function (event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById('imagePreview').src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        });

        // Set max date to today minus 13 years
        window.addEventListener('DOMContentLoaded', () => {
            const birthdayInput = document.getElementById('userbirthday');
            const today = new Date();
            const minAgeDate = new Date(today.getFullYear() - 13, today.getMonth(), today.getDate());
            birthdayInput.max = minAgeDate.toISOString().split('T')[0];
        });

        document.getElementById('profileForm').addEventListener('submit', function (e) {
            e.preventDefault();

            const oldPassword = document.getElementById('hiddenOldPassword').value.trim();
            const currentPassword = document.getElementById('currentPassword').value.trim();
            const newPassword = document.getElementById('newPassword').value.trim();
            const confirmPassword = document.getElementById('confirmPassword').value.trim();

            // Only validate password fields if user is trying to change password
            if (currentPassword !== "") {
                if (currentPassword !== oldPassword) {
                    alert('Current password is incorrect.');
                    return;
                }

                if (newPassword !== confirmPassword) {
                    alert('New passwords do not match!');
                    return;
                }

                if (newPassword.length < 12) {
                    alert('New password must be at least 12 characters long.');
                    return;
                }

                if (newPassword === oldPassword) {
                    alert('New password cannot be the same as the old password!');
                    return;
                }
            }

            this.submit();
        });
    </script>
</html>
