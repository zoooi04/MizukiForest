<%-- 
    Author     : huaiern
--%>
<%@page import="model.Appointment"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.Duration"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>
<%@page import="model.Timeslot"%>
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
        <link href="https://fonts.googleapis.com/css2?family=Silkscreen:wght@400;700&display=swap" rel="stylesheet">


        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mizukiuser.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/therapistModifySchedule.css">
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

        <%
            Boolean success = session.getAttribute("success") != null ? (Boolean) session.getAttribute("success") : false;
            session.removeAttribute("success");
            
            Boolean isCancel = session.getAttribute("isCancel") != null ? (Boolean) session.getAttribute("isCancel") : false;
            session.removeAttribute("isCancel");

            Boolean isDeleted = session.getAttribute("isDeleted") != null ? (Boolean) session.getAttribute("isDeleted") : false;
            session.removeAttribute("isDeleted");
            
            String error = (String) session.getAttribute("error");
            session.removeAttribute("error");
            
            Users user = (Users)session.getAttribute("user");
            
            Therapist therapist = (Therapist) session.getAttribute("therapist");

            Map<LocalDate, List<Timeslot>> weekMap = (Map<LocalDate, List<Timeslot>>) session.getAttribute("weekMap");
            Map<LocalDate, Map<LocalTime, Integer>> rowspanTracker = new HashMap<LocalDate, Map<LocalTime, Integer>>();

            Map<String,Appointment> appMap = (Map<String,Appointment>) session.getAttribute("appMap");
            
            if(success){

        %>
                <div class="modal" id="successModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content" style="background-color: #242424">
                            <div class="modal-header">
                                <h5 class="modal-title">Successful !</h5>
                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>You have successfully made a booking.</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Ok</button>
                            </div>
                        </div>
                    </div>
                </div>
        <%
            }

            if (isCancel) {

            %>
                <div class="modal" id="cancelModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content" style="background-color: #242424">
                            <div class="modal-header">
                                <h5 class="modal-title">Error!</h5>
                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>You have cancelled an appointment.</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Ok</button>
                            </div>
                        </div>
                    </div>
                </div>
        <%            
            }
            if (isDeleted) {

        %>
                <div class="modal" id="deleteModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content" style="background-color: #242424">
                            <div class="modal-header">
                                <h5 class="modal-title">Success!</h5>
                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p>You have deleted the time slot.</p>
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
                <div class="modal" id="sdModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content" style="background-color: #242424">
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
        %>


        

        <input type="hidden" id="receiverId" value="<%= therapist.getTherapistid()%>"/>

        <section id="sect-therapist-dashboard">
            <div class="container-fluid hero-banner " >
                <div class="overlay px-5"> 
                    <div class="h2 text-white text-center title"> Welcome back, <%= user.getUsername() %> !</div>
                </div>
            </div>

            <div class="center-layout container align-items-center py-5 ">
                <div class="row mx-0 gy-4 filter">
                    <a class="btn-theme col-2" href="${pageContext.request.contextPath}/view/therapy/therapistRoom.jsp">Back to Therapy</a>
                </div>
                <div class="row justify-content-between">
                    <div class="col-2">

                    </div>  
                    <div class="col-10 p-0">
                            <div class="row">
                                <div class="col"></div>
                                <div class="col my-3 d-flex justify-content-center align-items-center">
                                    <button disabled class="btn lr-btn left-btn"><i class="bi bi-caret-left-fill"></i></button>
                                    <div class="w-50 pagination-word text-center">Change week</div>
                                    <button class="btn lr-btn right-btn"><i class="bi bi-caret-right-fill"></i></button>
                                </div>
                                <div class="col"></div>
                            </div>
                            <div class="table-wrapper">
                                <!--table-->
                                <%

                                    
                                    if (weekMap.size() > 0) {
                                        int index = 0;
                                        for (Map.Entry<LocalDate, List<Timeslot>> entry : weekMap.entrySet()) {

                                            LocalDate weekStart = entry.getKey();  // Monday of the week
                                            List<Timeslot> slots = entry.getValue();

                                            LocalDate monday = weekStart;
                                            LocalDate tuesday = monday.plusDays(1);
                                            LocalDate wednesday = monday.plusDays(2);
                                            LocalDate thursday = monday.plusDays(3);
                                            LocalDate friday = monday.plusDays(4);
                                            LocalDate saturday = monday.plusDays(5);
                                            LocalDate sunday = monday.plusDays(6);

                                            // Format the dates in 'dd/MM' format
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
                                            String formattedMonday = monday.format(formatter);
                                            String formattedTuesday = tuesday.format(formatter);
                                            String formattedWednesday = wednesday.format(formatter);
                                            String formattedThursday = thursday.format(formatter);
                                            String formattedFriday = friday.format(formatter);
                                            String formattedSaturday = saturday.format(formatter);
                                            String formattedSunday = sunday.format(formatter);

                                            if (index == 0) {
                                                %>
                                                <div class="inner-table-wrapper table-responsive mx-auto justify-content-center table-active" data-index="<%= index %>">
                                                <%
                                            } else {
                                                %>
                                                <div class="inner-table-wrapper table-responsive mx-auto justify-content-center d-none" data-index="<%= index %>">
                                                <%
                                            }
                                            
                                            %>

                                        <table class="table my-table">
                                            <colgroup>
                                                <col style="width: 5%;">
                                                <col style="width: 10%;">
                                                <col style="width: 10%;">
                                                <col style="width: 10%;">
                                                <col style="width: 10%;">
                                                <col style="width: 10%;">
                                                <col style="width: 10%;">
                                                <col style="width: 10%;">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th class="text-uppercase first-col">Time</th>
                                                    <th class="text-uppercase"><div>Monday</div><div><%= formattedMonday%></div></th>
                                                    <th class="text-uppercase"><div>Tuesday</div><div><%= formattedTuesday%></div></th>
                                                    <th class="text-uppercase"><div>Wednesday</div><div><%= formattedWednesday%></div></th>
                                                    <th class="text-uppercase"><div>Thursday</div><div><%= formattedThursday%></div></th>
                                                    <th class="text-uppercase"><div>Friday</div><div><%= formattedFriday%></div></th>
                                                    <th class="text-uppercase"><div>Saturday</div><div><%= formattedSaturday%></div></th>
                                                    <th class="text-uppercase"><div>Sunday</div><div><%= formattedSunday%></div></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Loop over the time slots and populate -->
                                                <%
                                                    // Set up time slots for each hour
                                                    String[] timeSlots = {"09:00am", "10:00am", "11:00am", "12:00pm", "01:00pm", "02:00pm", "03:00pm", "04:00pm", "05:00pm", "06:00pm", "07:00pm"};
                                                    for (String time : timeSlots) {
                                                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma", Locale.ENGLISH);
                                                        LocalTime displayTime = LocalTime.parse(time.toUpperCase(), timeFormatter);
                                                        LocalTime displayTimeEnd = displayTime.plusHours(1);
                                                        
                                                        
                                                        String displayRange = timeFormatter.format(displayTime) + " - " + timeFormatter.format(displayTimeEnd);
                                                %>
                                                <tr>
                                                    <td class="align-middle first-col text-center"><%= displayRange %></td>

                                                    <%
                                                        // For each day (Monday-Sunday), check if there is a timeslot
                                                        for (LocalDate currentDay : Arrays.asList(monday, tuesday, wednesday, thursday, friday, saturday, sunday)) {
                                                            if (rowspanTracker.containsKey(currentDay)
                                                                    && rowspanTracker.get(currentDay).containsKey(displayTime)
                                                                    && rowspanTracker.get(currentDay).get(displayTime) > 0) {

                                                                int remaining = rowspanTracker.get(currentDay).get(displayTime);
                                                                rowspanTracker.get(currentDay).put(displayTime, remaining - 1); // decrement tracker
                                                                continue; // skip rendering this <td>
                                                            }
                                                            boolean slotFound = false;

                                                            // Check for slots on the current day
                                                            for (Timeslot slot : slots) {
                                                                if(!slot.getIsdeleted()){
                                                                    LocalTime starttime = convertToTime(slot.getStarttime());
                                                                    LocalTime endtime = convertToTime(slot.getEndtime());
                                                                    LocalDate slotDate = convertToDate(slot.getTsdate());

                                                                    if (slotDate.equals(currentDay)) {

                                                                        // Check if the current slot matches the current time
                                                                        if (starttime.equals(displayTime)) {
                                                                            int rowspan = (int) Duration.between(starttime, endtime).toHours();
                                                                            if(slot.getStatus()){
                                                                                out.print("<td class=\"slot available-slot\" rowspan=\"" + rowspan 
                                                                                + "\"> <div class=\"slot-status\">Available</div><button class=\"btn btn-success book-btn\" data-slotid=\"" + slot.getTimeslotid() 
                                                                                + "\"><span class=\"book-word\">book</span></button></td>");
                                                                            }else{
                                                                                if(appMap.containsKey(slot.getTimeslotid())){
                                                                                    out.print("<td class=\"slot booked-slot\" rowspan=\"" + rowspan + "\"> <div class=\"slot-status\">Booked by You</div><button class=\"btn btn-danger cancel-btn\" data-slotid=\"" + slot.getTimeslotid()
                                                                                            + "\"><span class=\"book-word\">Cancel</span></button></td>");

                                                                                }else{
                                                                                    out.print("<td class=\"slot booked-slot\" rowspan=\"" + rowspan + "\"> <div class=\"slot-status\">Booked</div></td>");

                                                                                }
                                                                            }
                                                                            
                                                                            slotFound = true;

                                                                            // Record the hours to skip
                                                                            for (int h = 1; h < rowspan; h++) {
                                                                                LocalTime futureHour = displayTime.plusHours(h);
                                                                                if (!rowspanTracker.containsKey(currentDay)) {
                                                                                    rowspanTracker.put(currentDay, new HashMap<LocalTime, Integer>());
                                                                                }
                                                                                rowspanTracker.get(currentDay).put(futureHour, rowspan - h);
                                                                            }
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            // If no slot found, leave the cell empty
                                                            if (!slotFound) {
                                                                out.print("<td></td>");
                                                            }
                                                        }
                                                    %>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                            </tbody>

                                        </table>
                                    </div>
                                            
                                    <%
                                        index++;
                                        }
                                        
                                    } 
                                    %>
                                </div>
                                <div class="row">
                                    <div class="col"></div>
                                    <div class="col my-3 d-flex justify-content-center align-items-center">
                                        <button disabled class="btn lr-btn left-btn"><i class="bi bi-caret-left-fill"></i></button>
                                        <div class="w-50 pagination-word text-center">Change Week</div>
                                        <button class="btn lr-btn right-btn"><i class="bi bi-caret-right-fill"></i></button>
                                    </div>
                                    <div class="col"></div>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
        </section>
        <script src="https://code.jquery.com/jquery-3.7.1.js"
                integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
        crossorigin="anonymous"></script>
        <script src="../../js/bookSession.js"></script>
        <script src="../../js/mizukibase.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const successModalEl = document.getElementById('successModal');
                if (successModalEl) {
                    const successModal = new bootstrap.Modal(successModalEl);
                    successModal.show();
                }
                
                const cancelModalEl = document.getElementById('cancelModal');
                if (cancelModalEl) {
                    const cancelModal = new bootstrap.Modal(cancelModalEl);
                    cancelModal.show();
                }
                
                const deleteModalEl = document.getElementById('deleteModal');
                if (deleteModalEl) {
                    const deleteModal = new bootstrap.Modal(deleteModalEl);
                    deleteModal.show();
                }
                
                const sdModalEl = document.getElementById('sdModal');
                if (sdModalEl) {
                    const sdModal = new bootstrap.Modal(sdModalEl);
                    sdModal.show();
                }
            });
        </script>
    </body>

</html>

<%!
    public LocalDate convertToDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalTime convertToTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
    }

%>



