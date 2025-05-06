<%-- 
    Author     : huaiern
--%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collections"%>
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/therapistCatalogue.css">

        <link href="https://fonts.googleapis.com/css2?family=Silkscreen:wght@400;700&display=swap" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <style>
            .modal-content{
                background-color: #242424;

            }

            .modal-content *{
                border:none;
            }
        </style>
    
    </head>
    <body>
        <%request.setAttribute("pageTitle", "Home");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>


        <section id="sect-catalogue">
            <!--<div class="container-fluid hero-banner " ><div class="overlay"> <h2 class="text-white text-center title"> Change therapist </h2></div></div>-->

            <div class="container-fluid hero-banner " >
                <div class="overlay px-5"> 
                    <div class="h2 text-white text-center title"> Change new therapist</div>
                </div>
            </div>
            
            <div class="center-layout container align-items-center">
                <div class="row mx-0 gy-4 filter">
                    <a class="btn-theme col-2" href="${pageContext.request.contextPath}/view/therapy/therapistRoom.jsp">Back to Chat</a>
                </div>
                <div class="row gy-4">
                <%
                    Boolean success = session.getAttribute("success") != null ? (Boolean) session.getAttribute("success") : false;
                    session.removeAttribute("success");
                    
                    Boolean hasApp = session.getAttribute("hasApp") != null ? (Boolean) session.getAttribute("hasApp") : false;
                    session.removeAttribute("hasApp");
                    
                    String error = (String) session.getAttribute("error");
                    session.removeAttribute("error");
                    
                    if(success){
                        %>
                        <div class="modal" id="successModal" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Successful !</h5>
                                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>You have changed your therapist.</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Ok</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                    }

                    if (hasApp) {
                        %>
                        <div class="modal" id="appModal" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Unable to change!</h5>
                                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>You still have ongoing appointment.</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Ok</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                    }

                    if (error != null) {
                        %>
                        <div class="modal" id="errModal" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Error!</h5>
                                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p><%= error %></p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Ok</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                            }


                    
                    List<Therapist> therapistList = (List<Therapist>) session.getAttribute("therapistList");
                    Collections.sort(therapistList, new Comparator<Therapist>() {
                            @Override
                            public int compare(Therapist t1, Therapist t2) {
                                return Integer.compare(t2.getYearofexp(), t1.getYearofexp()); // descending
                            }
                    });

                    if (therapistList.size() > 0) {
                        for (Therapist therapist : therapistList) {
                            // process image
                            byte[] productImageBytes = therapist.getTherapistimage();
                            String productImageBase64 = null;

                            if (productImageBytes != null && productImageBytes.length > 0) {
                                String imageFormat = getImageFormat(productImageBytes);
                                productImageBase64 = "data:image/" + imageFormat + ";base64," + java.util.Base64.getEncoder().encodeToString(productImageBytes);
                            }else {
                                productImageBase64 = request.getContextPath() + "/media/images/counselor1.jpg"; //if no image found
                            }

                            //process area of interest
                            String[] aoi = therapist.getAreaofinterest().split("\\s*,\\s*");
                %>
                    <div class="col-4">
                        <div class="card">
                            <img src="<%= productImageBase64%>" class="card-img-top" alt="...">
                            <div class="card-body p-0">
                                <div class="card-text">
                                    <div class="h6 text-center p-2 therapist-name"><%= therapist.getTherapistname()%></div>
                                    <div class="card-body">
                                    <ul class="main-bullet p-0">
                                        <li class="lh-lg">Experience: <%= therapist.getYearofexp()%> years
                                        </li>
                                        <li>Area of Interest:
                                            <ul>
                                                <%
                                                    for (String expertise : aoi) {
                                                %>                                  
                                                        <li><%= expertise %></li>
                                                <%
                                                    }
                                                %>
                                            </ul>
                                        </li>
                                        <li>Culture: <%= therapist.getCulture() %></li>
                                        <li>Language: <%= therapist.getLanguagespoken()%></li>
                                    </ul>
                                    </div>
                                    <div class="btn-section">
                                        <div class="w-100 d-flex justify-content-center">
                                            <% 
                                                Therapist currentTherapist = (Therapist) session.getAttribute("therapist");
                                                //if is current therapist, dont let change
                                                if(therapist.getTherapistid().equals(currentTherapist.getTherapistid())){
                                                %>
                                                    <button class="btn-theme choose-therapist-btn" disabled data-therapistid="<%= therapist.getTherapistid()%>" data-bs-toggle="modal" data-bs-target="#confirmModal">Your current therapist</button>
                                                <%
                                                }else{
                                                %>
                                                    <button class="btn-theme choose-therapist-btn" data-therapistid="<%= therapist.getTherapistid()%>" data-bs-toggle="modal" data-bs-target="#confirmModal">Change</button>
                                                <%
                                                }
                                            %>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
        </section>
        <div class="modal fade" id="confirmModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <form method="post" action="${pageContext.request.contextPath}/ChangeTherapistServlet">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Confirm Therapist Change</h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to change your therapist?
                        </div>
                        <div class="modal-footer">
                            <input type="hidden" name="therapistid" id="modal-therapistid">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Confirm</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script src="../../js/mizukibase.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const successModalEl = document.getElementById('successModal');
                if (successModalEl) {
                    const successModal = new bootstrap.Modal(successModalEl);
                    successModal.show();
                }
                
                const appModalEl = document.getElementById('appModal');
                if (appModalEl) {
                    const appModal = new bootstrap.Modal(appModalEl);
                    appModal.show();
                }
                
                const errModalEl = document.getElementById('errModal');
                if (errModalEl) {
                    const errModal = new bootstrap.Modal(errModalEl);
                    errModal.show();
                }


                const confirmModal = document.getElementById('confirmModal');
                confirmModal.addEventListener('show.bs.modal', function (event) {
                    const button = event.relatedTarget;
                    const therapistId = button.getAttribute('data-therapistid');
                    document.getElementById('modal-therapistid').value = therapistId;
                });
                

            });
        </script>
    </body>
</html>

<%!
    public static String getImageFormat(byte[] imageData) {
        if (imageData.length < 2) {
            return "jpeg"; // Default to JPEG if no format can be determined
        }
        if (imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50) {
            return "png"; // PNG magic number: 0x8950 (hex)
        } else if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
            return "jpeg"; // JPEG magic number: 0xFFD8 (hex)
        } else {
            return "jpeg"; // Default to JPEG if format is unknown
        }
    }
%>



