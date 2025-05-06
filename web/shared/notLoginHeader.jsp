<%-- 
    Document   : notLoginHeader
    Created on : May 4, 2025, 10:37:05 AM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://fonts.googleapis.com/css?family=Silkscreen' rel='stylesheet'>
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath()%>/media/images/mizuki.png">
</head>

<header>
    <div class="navbar">
        <div class="logo-section">
            <a href="<%= request.getContextPath()%>/index.jsp" class="logo-link">
                <img class="logo" src="<%= request.getContextPath()%>/media/images/mizuki.png" alt="Mizuki's Forest Logo">
            </a>
            <a href="<%= request.getContextPath()%>/index.jsp" class="logo-text">Mizuki's Forest</a>
        </div>

        <div class="nav-buttons">
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
            <c:if test="${pageContext.request.requestURI.contains('login')}">
                <p class="hidden-sm">New Player?</p>
                <a href="<%= request.getContextPath()%>/view/general/signup.jsp" class="btn btn-sm" style="background-color: #333; color:#fff; border: 1px solid #333;">Sign up free</a>  
            </c:if>

            <c:if test="${fn:contains(pageContext.request.requestURI, 'signup') 
                          || fn:contains(pageContext.request.requestURI, 'term') 
                          || fn:contains(pageContext.request.requestURI, 'privacy')}">
                  <p class="hidden-sm">Have an account?</p>
                  <a href="<%= request.getContextPath()%>/view/general/login.jsp" class="btn btn-sm" style="background-color: #333; color:#fff; border: 1px solid #333;">Log In</a>
            </c:if>
            <a href="https://discord.gg/rUggbkFppJ" target="_blank" class="btn btn-sm btn-accent">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 512" class="discord-icon" style="fill: white;">
                <path d="M524.5 69.8a1.5 1.5 0 0 0 -.8-.7A485.1 485.1 0 0 0 404.1 32a1.8 1.8 0 0 0 -1.9 .9 337.5 337.5 0 0 0 -14.9 30.6 447.8 447.8 0 0 0 -134.4 0 309.5 309.5 0 0 0 -15.1-30.6 1.9 1.9 0 0 0 -1.9-.9A483.7 483.7 0 0 0 116.1 69.1a1.7 1.7 0 0 0 -.8 .7C39.1 183.7 18.2 294.7 28.4 404.4a2 2 0 0 0 .8 1.4A487.7 487.7 0 0 0 176 479.9a1.9 1.9 0 0 0 2.1-.7A348.2 348.2 0 0 0 208.1 430.4a1.9 1.9 0 0 0 -1-2.6 321.2 321.2 0 0 1 -45.9-21.9 1.9 1.9 0 0 1 -.2-3.1c3.1-2.3 6.2-4.7 9.1-7.1a1.8 1.8 0 0 1 1.9-.3c96.2 43.9 200.4 43.9 295.5 0a1.8 1.8 0 0 1 1.9 .2c2.9 2.4 6 4.9 9.1 7.2a1.9 1.9 0 0 1 -.2 3.1 301.4 301.4 0 0 1 -45.9 21.8 1.9 1.9 0 0 0 -1 2.6 391.1 391.1 0 0 0 30 48.8 1.9 1.9 0 0 0 2.1 .7A486 486 0 0 0 610.7 405.7a1.9 1.9 0 0 0 .8-1.4C623.7 277.6 590.9 167.5 524.5 69.8zM222.5 337.6c-29 0-52.8-26.6-52.8-59.2S193.1 219.1 222.5 219.1c29.7 0 53.3 26.8 52.8 59.2C275.3 311 251.9 337.6 222.5 337.6zm195.4 0c-29 0-52.8-26.6-52.8-59.2S388.4 219.1 417.9 219.1c29.7 0 53.3 26.8 52.8 59.2C470.7 311 447.5 337.6 417.9 337.6z"></path>
                </svg>
                <span class="hidden-sm">COMMUNITY</span>
            </a>
        </div>
    </div>
</header>

<style>
    :root {
        font-family: 'Silkscreen', sans-serif;
        --base-100: #faf8f2;
        --base-300: #242424;
        --accent: #5662f6;
        --text-color: #1f1f1f;
        --bg-gradient-light: radial-gradient(#f3e3cd 40%, #f7ebdc 55%, transparent 70%);
        --bg-gradient-dark: radial-gradient(#33475b 0%, transparent 70%);
    }

    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        height: 100vh;
        background-color: #242424;
        color: white;
        display: flex;
        flex-direction: column;
        antialiased: true;
    }

    .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0.5rem 1rem;
        background-color: #242424;
        height: 4rem;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        position: fixed;
        width: 100%;
        z-index: 50;
    }

    .logo-section {
        display: flex;
        align-items: center;
        margin-left: 0.25rem;
    }

    .logo {
        height: 1.5rem;
        image-rendering: pixelated;
    }

    .logo-text {
        font-size: 1.5rem;
        font-weight: normal;
        letter-spacing: -0.05em;
        margin-left: 0.125rem;
        color: white;
        text-decoration: none;
        padding: 0.25rem;
    }

    .nav-buttons {
        display: flex;
        align-items: center;
        gap: 1rem;
    }

    .btn {
        padding: 0.5rem 1rem;
        border-radius: 0.5rem;
        font-weight: bold;
        cursor: pointer;
        font-size: 0.875rem;
        transition: all 0.2s;
        text-decoration: none;
        display: inline-flex;
        align-items: center;
    }

    .btn-sm {
        padding: 0.25rem 0.75rem;
        font-size: 0.875rem;
    }

    .btn-ghost {
        background-color: transparent;
        color: white;
    }

    .btn-accent {
        background-color: var(--accent);
        color: white;
    }

    .main {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: center; /* Center vertically */
        align-items: center; /* Center horizontally */
        padding: 0 1rem;
        margin-top: 4rem; /* Account for navbar height */
        height: calc(100% - 4rem);
        position: relative;
    }

    .bg-gradient {
        height: calc(100vh - 4rem);
        width: 100%;
        border-radius: 50%;
        opacity: 0.5;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: -10;
        pointer-events: none;
        background-image: var(--bg-gradient-dark);
    }

    .card {
        background-color: var(--base-300);
        border-radius: 0.5rem;
        box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
        width: 100%;
        max-width: 20rem;
        margin: 0 auto;
    }

    .card-body {
        padding: 1.5rem;
    }

    h1 {
        font-size: 1.5rem;
        text-align: center;
        margin-bottom: 1rem;
    }

    .form-control {
        margin-bottom: 1rem;
    }

    .input {
        width: 100%;
        padding: 0.75rem;
        border-radius: 0.5rem;
        border: 1px solid #4b5563;
        background-color: #1a1a1a;
        color: white;
        outline: none;
        transition: border-color 0.2s;
    }

    .input:focus {
        border-color: var(--accent);
    }

    .label {
        display: flex;
        justify-content: space-between;
        margin-top: 0.25rem;
    }

    .label-text-alt {
        font-size: 0.75rem;
        color: #a0a0a0;
    }

    .link {
        color: #a0a0a0;
        text-decoration: none;
    }

    .link:hover {
        text-decoration: underline;
    }

    .discord-icon {
        height: 1rem;
        width: 1rem;
        margin-right: 0.5rem;
    }

    .hidden-sm {
        display: none;
    }

    @media (min-width: 640px) {
        .hidden-sm {
            display: block;
        }
    }

    /* Checkbox styling */
    .checkbox-container {
        display: flex;
        align-items: center;
        position: relative;
        padding-left: 35px;
        cursor: pointer;
        font-size: 0.875rem;
        user-select: none;
    }

    .checkbox-container input {
        position: absolute;
        opacity: 0;
        cursor: pointer;
        height: 0;
        width: 0;
    }

    .checkmark {
        position: absolute;
        top: 0;
        left: 0;
        height: 20px;
        width: 20px;
        background-color: #1a1a1a;
        border: 1px solid #4b5563;
        border-radius: 4px;
    }

    .checkbox-container:hover input ~ .checkmark {
        background-color: #2c2c2c;
    }

    .checkbox-container input:checked ~ .checkmark {
        background-color: var(--accent);
        border-color: var(--accent);
    }

    .checkmark:after {
        content: "";
        position: absolute;
        display: none;
    }

    .checkbox-container input:checked ~ .checkmark:after {
        display: block;
    }

    .checkbox-container .checkmark:after {
        left: 6px;
        top: 2px;
        width: 5px;
        height: 10px;
        border: solid white;
        border-width: 0 2px 2px 0;
        transform: rotate(45deg);
    }

    .label-text {
        margin-left: 10px;
        font-size: 0.75rem;
        color: #a0a0a0;
    }

    /* Modal Styles */
    .modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 100;
        align-items: center;
        justify-content: center;
    }

    .modal.show {
        display: flex;
    }

    .modal-content {
        background-color: #333;
        border-radius: 0.5rem;
        width: 90%;
        max-width: 400px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        animation: modalFade 0.3s ease-out;
    }

    @keyframes modalFade {
        from {
            opacity: 0;
            transform: translateY(-20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem;
        border-bottom: 1px solid #444;
    }

    .modal-title {
        font-size: 1.25rem;
        margin: 0;
    }

    .close {
        font-size: 1.5rem;
        color: #aaa;
        cursor: pointer;
        border: none;
        background: transparent;
        font-weight: bold;
    }

    .close:hover {
        color: white;
    }

    .modal-body {
        padding: 1rem;
    }

    .modal-footer {
        padding: 1rem;
        border-top: 1px solid #444;
        display: flex;
        justify-content: flex-end;
    }

    .btn-dismiss {
        background-color: #7c3aed;
        color: white;
        border: none;
        padding: 0.5rem 1rem;
        border-radius: 0.25rem;
        cursor: pointer;
    }

    .btn-dismiss:hover {
        background-color: #6d28d9;
    }
</style>