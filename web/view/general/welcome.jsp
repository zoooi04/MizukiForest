<%-- 
    Document   : welcome
    Created on : May 4, 2025, 11:21:52 AM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Boolean signedUp = (Boolean) session.getAttribute("signedUp");
    if (signedUp == null || !signedUp) {
        response.sendRedirect(request.getContextPath() + "/view/general/signup.jsp");
        return;
    }

    session.setAttribute("visitedWelcome", true);
%>

<html data-theme="mizuki_dark" class="dark">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/welcome.css">
        <link rel="icon" href="favicon.ico" sizes="any">
        <title>Mizuki's Forest</title>


    </head>

    <body class="h-screen antialiased">
        <div class="main h-full flex flex-col">

            <main class="px-4 h-full">
                <div class="z-100 fixed top-0 left-0 w-full h-full overflow-y-scroll no-scrollbar">
                    <div class="pointer-events-none h-20 w-full fixed top-0 left-0 -z-10 bg-backdrop-bg-dark"></div>
                    <div class="pointer-events-none h-96 w-full fixed top-20 left-0 -z-10 bg-gradient-to-b from-backdrop-bg-dark to-transparent"></div>

                    <div class="flex justify-center w-full mt-16">
                        <div class="flex flex-col items-start gap-4 px-8 max-w-lg font-serif">
                            <div class="animate-jump-in mb-2 -ml-4 pointer-events-none">
                                <div class="animate-jumping-bounce-always-fast">
                                    <img class="h-32 w-12 flex-shrink-0 select-none pointer-events-none" 
                                         style="image-rendering: pixelated;"
                                         src="<%= request.getContextPath()%>/media/images/yumemizuki.png">
                                </div>
                            </div>

                            <p class="animate-appear-1 w-full leading-tight">Hey there! Welcome to Mizuki's Forest!</p>
                            <p class="animate-appear-2 w-full leading-tight">I'm Yumemizuki Mizuki, is a clinical psychologist, but everyone around here calls me the Mizuki Professor.</p>

                            <div class="animate-appear-3 mt-2 pointer-events-none">
                                <div class="h-32 w-32 relative overflow-hidden flex-shrink-0 select-none pointer-events-none"
                                     style="image-rendering: pixelated;">
                                    <img class="h-32 w-32" src="<%= request.getContextPath()%>/media/images/tree/Cherry Blossom.png">
                                </div>
                            </div>

                              <p class="animate-appear-5 w-full leading-tight">In Mizuki's Forest, each tree is a living testament to nature's artistry—their branches dancing with the winds, leaves painting the sky with vibrant colors.</p>
                            <p class="animate-appear-6 w-full leading-tight">This special place is where ancient roots connect to fertile soil, creating a sanctuary where tranquility blooms alongside the most magnificent cherry blossoms you'll ever witness.</p>
                            <p class="animate-appear-7 w-full leading-tight">I've been diving into the mission of uncovering every secret within the Mizuki's Forest.</p>
                            <p class="animate-appear-8 w-full leading-tight">And you are?</p>

                            <a class="new-trainer-link relative mt-8 mb-48 mx-auto self-center" href="<%= request.getContextPath()%>/view/general/new_player.jsp">
                                <div class="animate-appear-9 rounded bg-primary tracking-tighter text-primary-content px-3 py-2 text-lg w-full flex justify-center items-center gap-2">
                                    Create my profile
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256" class="fill-current h-6">
                                    <path d="M224.49,136.49l-72,72a12,12,0,0,1-17-17L187,140H40a12,12,0,0,1,0-24H187L135.51,64.48a12,12,0,0,1,17-17l72,72A12,12,0,0,1,224.49,136.49Z"></path>
                                    </svg>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>
