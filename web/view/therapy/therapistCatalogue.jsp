<%-- 
    Author     : huaiern
--%>
<%@page import="java.util.Comparator"%>
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mizukiuser.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/therapistCatalogue.css">

        <link href="https://fonts.googleapis.com/css2?family=Silkscreen:wght@400;700&display=swap" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </head>
    <body>
        <%request.setAttribute("pageTitle", "Home");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>


        <section id="sect-catalogue">
<!--            <div class="container-fluid hero-banner " ><div class="overlay"> <h2 class="text-white text-center title"> Find your therapist </h2></div></div>-->

            <div class="container-fluid hero-banner " >
                <div class="overlay px-5"> 
                    <div class="h2 text-white text-center title"> Your therapy journey starts here</div>
                </div>
            </div>

            <div class="center-layout container align-items-center">
                <div class="row filter">
                    <div class="d-flex justify-content-center another-title">🌿 Pick your therapist 🌿</div>
                </div>
                <div class="row gy-4">
                <%
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
                                        <div class="modal fade" id="confirmModal" tabindex="-1" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <form method="post" action="${pageContext.request.contextPath}/ChooseTherapistServlet">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Confirm?</h5>
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
                                        
                                        <!--<form class="w-100 d-flex justify-content-center" action="${pageContext.request.contextPath}/ChooseTherapistServlet" method="post">-->
                                                    <button class="btn-theme choose-therapist-btn" data-therapistid="<%= therapist.getTherapistid()%>" data-bs-toggle="modal" data-bs-target="#confirmModal">Choose</button>

                                            <!--<input type="hidden" name="therapistid" value="<%= therapist.getTherapistid() %>"/>-->
                                        <!--</form>-->
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


        <script>
                const confirmModal = document.getElementById('confirmModal');
                confirmModal.addEventListener('show.bs.modal', function (event) {
                    const button = event.relatedTarget;
                    const therapistId = button.getAttribute('data-therapistid');
                    document.getElementById('modal-therapistid').value = therapistId;
                });
        </script>
        <script src="../../js/mizukibase.js"></script>
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



