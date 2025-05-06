<%-- 
    Document   : new_player
    Created on : May 4, 2025, 12:29:32 PM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Boolean signedUp = (Boolean) session.getAttribute("signedUp");
    if (signedUp == null || !signedUp) {
        response.sendRedirect(request.getContextPath() + "/view/general/signup.jsp");
        return;
    }
    String email = (String) session.getAttribute("tempEmail");
    String password = (String) session.getAttribute("tempPassword");
    session.setAttribute("visitedWelcome", true);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mizuki's Forest</title>
</head>
<body>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Add a short delay before showing content to ensure animation is visible
            setTimeout(function() {
                document.querySelector('.registration-container').style.animationPlayState = 'running';
            }, 100);
            
            const registrationForm = document.getElementById('registration-form');
            const birthDateInput = document.getElementById('userBirthday');
            const ageDisplay = document.getElementById('age-display');
            const whyLink = document.getElementById('why-link');
            const ageExplanation = document.getElementById('age-explanation');
            const ageError = document.getElementById('age-error');
            const playerNameInput = document.getElementById('userName');
            const referralSelect = document.getElementById('referral');
            
            // Calculate minimum allowed birth date (13 years ago from today)
            const today = new Date();
            const minAge = 13;
            const minDate = new Date();
            minDate.setFullYear(today.getFullYear() - minAge);
            
            // Set max attribute on date input to prevent selecting dates that would make user under 13
            const maxDateString = minDate.toISOString().split('T')[0];
            birthDateInput.setAttribute('max', maxDateString);
            
            // Set default date to show 13 years old
            const defaultDate = new Date();
            defaultDate.setFullYear(defaultDate.getFullYear() - 13);
            const defaultDateString = defaultDate.toISOString().split('T')[0];
            birthDateInput.value = defaultDateString;
            
            // Calculate age function
            function calculateAge(birthDate) {
                const today = new Date();
                const birth = new Date(birthDate);
                let age = today.getFullYear() - birth.getFullYear();
                const monthDiff = today.getMonth() - birth.getMonth();
                
                if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
                    age--;
                }
                
                return age;
            }
            
            // Update age display on page load
            ageDisplay.textContent = calculateAge(birthDateInput.value);
            
            // Update age and validate when date changes
            birthDateInput.addEventListener('change', function() {
                const selectedDate = new Date(this.value);
                const age = calculateAge(this.value);
                ageDisplay.textContent = age;
                
                // Validate age is at least 13
                if (age < minAge) {
                    ageError.style.display = 'block';
                    this.setCustomValidity('You must be at least 13 years old to register.');
                } else {
                    ageError.style.display = 'none';
                    this.setCustomValidity('');
                }
            });
            
            // Toggle explanation when Why? is clicked
            whyLink.addEventListener('click', function(e) {
                e.preventDefault();
                ageExplanation.style.display = ageExplanation.style.display === 'block' ? 'none' : 'block';
            });
            
            // Form validation and submission
            registrationForm.addEventListener('submit', function(e) {
                let isValid = true;
                
                // Reset previous error messages
                document.querySelectorAll('.form-error').forEach(el => {
                    el.style.display = 'none';
                });
                
                // Validate age
                const age = calculateAge(birthDateInput.value);
                if (age < minAge) {
                    ageError.style.display = 'block';
                    isValid = false;
                }
                
                // Validate player name
                if (!playerNameInput.value.trim()) {
                    document.getElementById('userName-error').style.display = 'block';
                    isValid = false;
                }
                
                // Validate referral selection
                if (referralSelect.value === 'Select') {
                    document.getElementById('referral-error').style.display = 'block';
                    isValid = false;
                }
                
                // If validation fails, prevent form submission
                if (!isValid) {
                    e.preventDefault();
                } 
            });
        });
    </script>
    <div class="registration-container">
        <div class="pixel-graphics">
            <img src="<%= request.getContextPath()%>/media/images/yumemizuki.png" alt="Player Character">
        </div>
        
        <div class="registration-title">
            Time to officially register as a <span>Player!</span>
        </div>
        
        <form id="registration-form" action="${pageContext.request.contextPath}/signup" method="post">
                
            <div class="form-group">
                <div class="age-control">
                    <span class="age-label">I am</span>
                    <span class="age-value" id="age-display">13</span>
                    <span class="age-unit">years old</span>
                    <a class="why-link" id="why-link">
                        <svg class="why-icon" xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 256 256">
                            <path d="M128,24A104,104,0,1,0,232,128,104.11,104.11,0,0,0,128,24Zm0,168a12,12,0,1,1,12-12A12,12,0,0,1,128,192Zm8-48.72V144a8,8,0,0,1-16,0v-8a8,8,0,0,1,8-8c13.23,0,24-9,24-20s-10.77-20-24-20-24,9-24,20v4a8,8,0,0,1-16,0v-4c0-19.85,17.94-36,40-36s40,16.15,40,36C168,125.38,154.24,139.93,136,143.28Z"></path>
                        </svg>
                        &nbsp;Why?
                    </a>
                </div>
                <input type="date" class="date-input" id="userBirthday" name="userBirthday" required>
                <div class="explanation-box" id="age-explanation">
                    We collect your age to ensure compliance with legal 
                    requirements in your country. You must be at least 13 years old
                    to use our service. Your age is kept private and is not used for any other purpose.
                </div>
                <div class="form-error" id="age-error">You must be at least 13 years old to register.</div>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="timezone">My time zone</label>
                <select id="timezone" name="timezone" required>
                    <option value="GMT+08:00">(GMT+08:00) Kuala Lumpur</option>
                    <option value="GMT-08:00">(GMT-08:00) Pacific Time (US & Canada)</option>
                    <option value="GMT-05:00">(GMT-05:00) Eastern Time (US & Canada)</option>
                    <option value="GMT+00:00">(GMT+00:00) London</option>
                    <option value="GMT+01:00">(GMT+01:00) Paris, Berlin</option>
                    <option value="GMT+09:00">(GMT+09:00) Tokyo</option>
                </select>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="referral">I discovered Player on...</label>
                <select id="referral" name="referral" required>
                    <option value="Select">Select</option>
                    <option value="Social Media">Social Media</option>
                    <option value="Friend">Friend Recommendation</option>
                    <option value="Advertisement">Advertisement</option>
                    <option value="Search">Search Engine</option>
                    <option value="Other">Other</option>
                </select>
                <div class="form-error" id="referral-error">Please select how you discovered us</div>
            </div>
            
            <div class="form-group">
                <input type="text" id="userBirthday" name="userEmail" value="<%= email %>"  hidden>
                <input type="text" id="userPw" name="userPw" value="<%= password %>" hidden>
                <label class="form-label" for="userName">I go by...</label>
                <input type="text" id="userName" name="userName" placeholder="My Player name" required>
                <div class="form-error" id="userName-error">Please enter your player name</div>
            </div>
            
            <input type="hidden" name="form_submitted" value="1">
            <button type="submit" class="register-btn">Register</button>
        </form>
    </div>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #242424;
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        
        .registration-container {
            width: 450px;
            text-align: center;
            opacity: 0;
            animation: fadeIn 0.8s ease-in-out forwards;
            animation-play-state: paused;
        }
        
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .pixel-graphics {
            display: flex;
            justify-content: center;
            margin-bottom: 15px;
        }
        
        .pixel-graphics img {
            height: 80px;
            width: 160px;
            object-fit: contain;
        }
        
        .registration-title {
            margin-bottom: 30px;
            font-size: 18px;
            color: #ccc;
        }
        
        .registration-title span {
            color: #fff;
            font-weight: bold;
        }
        
        .form-group {
            margin-bottom: 25px;
            text-align: left;
        }
        
        .form-label {
            display: block;
            margin-bottom: 10px;
            color: #ccc;
        }
        
        .age-control {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            width: 100%;
        }
        
        .date-input {
            width: 100%;
            padding: 12px;
            border: 1px solid #444;
            background-color: #333;
            color: white;
            border-radius: 4px;
            outline: none;
            box-sizing: border-box;
        }
        
        .age-value {
            font-size: 24px;
            font-weight: bold;
            color: white;
            margin: 0 5px;
        }
        
        .age-unit {
            color: #ccc;
        }
        
        .age-label {
            white-space: nowrap;
        }
        
        .why-link {
            color: #cd853f;
            text-decoration: none;
            font-size: 14px;
            margin-left: auto;
            cursor: pointer;
            display: flex;
            align-items: center;
        }
        
        .why-link:hover {
            text-decoration: none;
        }
        
        .why-icon {
            fill: #cd853f;
            width: 18px;
            height: 18px;
            margin-left: 5px;
        }
        
        .explanation-box {
            background-color: #333;
            border-radius: 4px;
            padding: 15px;
            margin: 10px 0;
            font-size: 14px;
            color: #ccc;
            line-height: 1.5;
            display: none;
        }
        
        select, input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #444;
            background-color: #333;
            color: white;
            border-radius: 4px;
            outline: none;
            box-sizing: border-box;
        }
        
        select {
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='6'%3E%3Cpath d='M0 0l6 6 6-6z' fill='%23ccc'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 15px center;
            cursor: pointer;
        }
        
        .register-btn {
            background-color: #2e8b57;
            color: white;
            border: none;
            padding: 12px 30px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        .register-btn:hover {
            background-color: #3aa76d;
        }
        
        .form-error {
            color: #ff6b6b;
            font-size: 14px;
            margin-top: 5px;
            display: none;
        }
        
        /* Age restriction style */
        input[type="date"]:invalid {
            border: 1px solid #ff6b6b;
        }
        
        .age-requirements {
            color: #2e8b57;
            font-size: 14px;
            margin-top: 5px;
        }
    </style>
</body>
</html>