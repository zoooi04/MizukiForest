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
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mizukiuser.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/assignedPatients.css">

        <link href="https://fonts.googleapis.com/css2?family=Silkscreen:wght@400;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <%request.setAttribute("pageTitle", "Home");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>

        <%
            System.out.println("hello world");
            Therapist therapist = (Therapist) session.getAttribute("therapist");
            List<Usertherapist> utList = (List<Usertherapist>) session.getAttribute("utList");        
            
        %>

        <section id="sect-catalogue">
            <input type="hidden" id="therapistid" value="<%= therapist.getTherapistid() %>">
            <div class="container-fluid hero-banner " >
                <div class="overlay px-5"> 
                    <div class="h2 text-white text-center title"> Every story matters — here are theirs.</div>
                </div>
            </div>

            <div class="center-layout container align-items-center py-5 ">
                <div class="row mx-0 gy-4 filter">
                    <a class="btn-theme col-2" href="${pageContext.request.contextPath}/view/therapy/trTherapistRoom.jsp">Back to Therapy</a>
                </div>
                <div class="row mt-1 ">
                    <div class="mb-5 h5">  Guiding every mind, one step at a time </div>
                </div>
                <div class="row gx-2 gy-4">
                        <%
                        for(Usertherapist ut: utList){
                            %>
                            <div class="col-3">
                                <div class="card" style="width: 18rem; height: 100%;" data-name="<%= ut.getUserid().getUsername().toLowerCase() %>">
                                    <div class="card-body d-flex flex-column" style="height: 100%;">
                                        <div>
                                            <h5 class="card-title"><%= ut.getUserid().getUsername()%></h5>
                                            <h6 class="card-subtitle mb-2">Level <%= ut.getUserid().getUserlevel()%></h6>
                                        </div>
                                        <form>
                                            <%
                                            if(ut.getUserid().getDiaryvisibility()){
                                           %>
                                                <button class="btn btn-success align-self-start">View Journal</button>
                                            <%
                                            }else{
    %>
                                                <button class="btn btn-success align-self-start" disabled>View Journal</button>
    <%
                                            }
                                            %>
                                        </form>
                                    </div>
                                </div>
                            </div>

                                
                            
                            
                            <%
                        }
                        %>
                </div>
            </div>
        </section>
        <script src="https://code.jquery.com/jquery-3.7.1.js"
        integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
        crossorigin="anonymous"></script>
        <script src="../../js/therapistRoom.js"></script>
        <script src="../../js/messageListener.js"></script>
        <script src="../../js/mizukibase.js"></script>
    </body>
</html>



