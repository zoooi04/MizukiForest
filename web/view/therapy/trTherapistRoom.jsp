<%-- 
    Author     : huaiern
--%>
<%@page import="model.Usertherapist"%>
<%@page import="model.Message"%>
<%@page import="model.Users"%>
<%@page import="model.Therapist"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mizukiuser.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/therapistRoom.css">

        <link href="https://fonts.googleapis.com/css2?family=Silkscreen:wght@400;700&display=swap" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </head>
    <body>
        <%request.setAttribute("pageTitle", "Home");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>

        <%
            Therapist therapist = (Therapist) session.getAttribute("therapist");
            List<Usertherapist> userTherapistList = (List<Usertherapist>) session.getAttribute("userTherapistList");
            List<Message> messageList = (List<Message>) session.getAttribute("messageList");
            
            int count = 0;
            for(int i = 0; i < userTherapistList.size(); i++){
                if(!userTherapistList.get(i).getIsdeleted()){
                    count++;
                }
            }
        %>

        <input type="hidden" id="receiverId" value="<%= therapist.getTherapistid() %>"/>

        <section id="sect-catalogue">
            <div class="container-fluid hero-banner " >
                <div class="overlay px-5"> 
                    <div class="h2 text-white text-center title"> Welcome back, <%= therapist.getTherapistname() %> !</div>
                </div>
            </div>
            <div class="center-layout container align-items-center py-5 ">
                <div class="row mt-1 ">
                    <div class="col-3"></div>
                    <div class="col">
                        <ul>
                            <li>This chat is a safe and confidential space for open dialogue.</li>
                            <li>Please communicate respectfully, avoid inappropriate language, 
                                and remember that you are responsible for user well-being.</li>
                            <li>Misuse of this platform may result in chat privileges being restricted.</li>
                        </ul>
                    </div>
                </div>
                <div class="row justify-content-between">
                    <div class="col-3">                        
                        <div class=" pt-3">Current assigned: <%= count %></div>

                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/LoadScheduleServlet" class="book-app-btn mt-3 d-block text-white text-decoration-none text-center">My Schedule</a>
                        </div>
                            <div class="mt-3">
                                <a href="${pageContext.request.contextPath}/LoadAssignedPatients" class="book-app-btn mt-3 d-block text-white text-decoration-none text-center">My Patients</a>
                            </div>
                    </div>
                    <div class="col-2 p-0 chat-list">
                        <%
                            int index = 0;
                            for (Usertherapist ut : userTherapistList) {
                                if(!ut.getIsdeleted()){
                                    if (index > 0) {
                                %>
                                <div class="inactive-chat chat-item" data-userid="<%= ut.getUserid().getUserid()%>"><%= ut.getUserid().getUsername() %></div>
                                <%
                                    }else{
                                        %>
                                        <div class="active-chat chat-item" data-userid="<%= ut.getUserid().getUserid()%>"><%= ut.getUserid().getUsername()%></div>

                                        <%
                                    }
                                    index++;
                                }
                            }
                            

                            for (Usertherapist ut : userTherapistList) {
                                if(ut.getIsdeleted()){

                                %>
                                <div class="inactive-chat chat-item" data-userid="<%= ut.getUserid().getUserid()%>" style="color: #ba7514"><%= ut.getUserid().getUsername() %></div>
                                <%

                                }
                            }
                        %>
                    </div>
                    
                    
                    
                    <div class="col-7 p-0">
                        <%
                            index = 0;
                            for (Usertherapist ut : userTherapistList) {
                                if (!ut.getIsdeleted()) {
                                    if (index > 0) {
                        %>
                                        <div class="chat-wrapper chat-box-inactive" data-userid="<%= ut.getUserid().getUserid()%>" data-therapistid="<%= therapist.getTherapistid() %>">
                        <%
                                    } else {
                        %>
                                        <div class="chat-wrapper chat-box-active" data-userid="<%= ut.getUserid().getUserid()%>" data-therapistid="<%= therapist.getTherapistid() %>">

                        <%
                                    }
                                    index++;
                                
%>
                                        <div class="chat-bar">Chat</div>
                                        <div class="chat-box">
                                            <!--Chat begins here-->
                                            <span class="text-center d-block mx-auto text-dark start-of-convo pt-3">-- Start chatting! --</span>
                                            <%
                                                for (Message message : messageList) {
                                                    if(message.getUserid().getUserid().equals(ut.getUserid().getUserid())){
                                                        if (message.getSender().equalsIgnoreCase("user")) {
                                            %>
                                                        <div class="row m-0">
                                                            <div class="col sender-col m-2 d-flex flex-wrap">
                                                                <div class="text-dark w-100 chat-name"><%= message.getUserid().getUsername()%></div>
                                                                <div class="speech-bubble mt-1 px-3 py-1"><%= message.getContent()%></div>
                                                            </div>
                                                            <div class="col"></div>
                                                        </div>
                                                <%
                                                     } else if (message.getSender().equalsIgnoreCase("system")) {
                                                     %>
                                                        <div class="row m-0">
                                                            <div class="col sender-col m-2 d-flex flex-wrap">
                                                                <div class="text-dark w-100 chat-name">-- System Message --</div>
                                                                <div class="speech-bubble mt-1 px-3 py-1" style="background-color: #d49237"><%= message.getContent()%></div>
                                                            </div>
                                                            <div class="col"></div>
                                                        </div>

<%
                                                        }else if(message.getSender().equalsIgnoreCase("therapist")){
                                                %>
                                                        <div class="row m-0">
                                                            <div class="col"></div>
                                                            <div class="col sender-col m-2 d-flex flex-wrap justify-content-end">
                                                                <div class="text-dark w-100 chat-name text-end">You</div>
                                                                <div class="speech-bubble mt-1 px-3 py-1"><%= message.getContent()%></div>
                                                            </div>
                                                        </div>


                                            <%
                                                        }
                                                    }
                                                }
                                            %>

                                        </div>
                                        <div class="input-message px-3">
                                            <input type="hidden" class="username" value="<%= ut.getUserid().getUsername() %>">
                                            <textarea placeholder="Start Typing.."></textarea>
                                            <button type="submit" class="send-btn pt-1 px-2">Send</button>
                                        </div>
                                        </div>
<%
                                }
                            }

                            for (Usertherapist ut : userTherapistList) {
                                if (ut.getIsdeleted()) {
                        %>
                                        <div class="chat-wrapper chat-box-inactive" data-userid="<%= ut.getUserid().getUserid() %>">
                                            <div class="chat-bar">Chat</div>
                                            <div class="chat-box">
                                                <!--Chat begins here-->
                                                <span class="text-center d-block mx-auto text-dark start-of-convo pt-3">-- Start chatting! --</span>
                                                <%

                                                    for (Message message : messageList) {
                                                        if (message.getUserid().getUserid().equals(ut.getUserid().getUserid())) {
                                                            if (message.getSender().equalsIgnoreCase("user")) {
                                                                
                                                %>
                                                <div class="row m-0">
                                                    <div class="col sender-col m-2 d-flex flex-wrap">
                                                        <div class="text-dark w-100 chat-name"><%= message.getUserid().getUsername()%></div>
                                                        <div class="speech-bubble mt-1 px-3 py-1"><%= message.getContent()%></div>
                                                    </div>
                                                    <div class="col"></div>
                                                </div>
                                                        <%                                                     
                                                            } else if (message.getSender().equalsIgnoreCase("system")) {
                                                        %>
                                                        <div class="row m-0">
                                                            <div class="col sender-col m-2 d-flex flex-wrap">
                                                                <div class="text-dark w-100 chat-name">-- System Message --</div>
                                                                <div class="speech-bubble mt-1 px-3 py-1" style="background-color: #d49237"><%= message.getContent()%></div>
                                                            </div>
                                                            <div class="col"></div>
                                                        </div>

                                                        <% 
                                                            }else if(message.getSender().equalsIgnoreCase("therapist")){
                                                %>
                                                <div class="row m-0">
                                                    <div class="col"></div>
                                                    <div class="col sender-col m-2 d-flex flex-wrap justify-content-end">
                                                        <div class="text-dark w-100 chat-name text-end">You</div>
                                                        <div class="speech-bubble mt-1 px-3 py-1"><%= message.getContent()%></div>
                                                    </div>
                                                </div>


                                                <%
                                                            }
                                                        }
                                                    }
                                                %>

                                            </div>
                                            <div class="input-message px-3">
                                                <input type="hidden" class="username" value="<%= ut.getUserid().getUsername()%>">
                                                <textarea disabled placeholder="User is assigned to another therapist. You currently cannot chat with this user."></textarea>
                                            </div>
                                        </div>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
            </div>
            </div>
        </section>
        <script src="https://code.jquery.com/jquery-3.7.1.js"
        integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
        crossorigin="anonymous"></script>
        <script src="../../js/trTherapistRoom.js"></script>
        <script src="../../js/messageListener.js"></script>
        <script src="../../js/mizukibase.js"></script>
    </body>
</html>



