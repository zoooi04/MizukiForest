<%@page import="java.util.Collection"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/journal-diaries.css">

    <section id="diary" class="incsec">
        <%request.setAttribute("pageTitle", "Diary Entries");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <main class="container mt-5 pt-5">
            <h1 class="text-center mb-4">Diary Entries</h1>

            <div class="diary-header d-flex justify-content-between align-items-center mb-4">
                <div class="date-filter">
                    <!-- Date Range Inputs -->
                    <input type="text" id="startDate" class="form-control" placeholder="Start Date" readonly>
                    <input type="text" id="endDate" class="form-control" placeholder="End Date" readonly>
                    <button class="btn btn-light" id="filterBtn">Apply Filter</button>
                    <button class="btn btn-light" id="resetBtn">Reset</button>
                </div>
                <div class="archive-button">
                    <button class="btn btn-light" id="viewArchivedBtn">View Archived Diaries</button>
                </div>
            </div>

            <div class="diary-grid">
                <!-- Add New Book (doesn't get filtered) -->
                <div class="diary-item add-new add-new-ignore">
                    <a href="<%= request.getContextPath()%>/AddingDiaryPageServlet" class="add-button-link">
                        <div class="add-button">
                            <span>+</span>
                        </div>
                    </a>
                </div>

                <%@ page import="java.util.List" %>
                <%@ page import="model.Diaryentry" %>
                <%
                    Collection<Diaryentry> diaryEntries = (Collection<Diaryentry>) session.getAttribute("diaryEntries");
                    if (diaryEntries != null) {
                        for (Diaryentry entry : diaryEntries) {
                            String date = new SimpleDateFormat("dd/MM/yyyy").format(entry.getDatewritten());
                            String ymddate = new SimpleDateFormat("yyyy-MM-dd").format(entry.getDatewritten());
                            boolean isArchived = entry.getIsarchived(); // Assuming this is a boolean property in Diaryentry
                %>
                <div class="diary-item" data-date="<%=ymddate%>" data-archived="<%= isArchived ? "true" : "false"%>">
                    <a href="../../viewEntriesByDate?date=<%=ymddate%>" class="diary-link">
                        <div class="book-container">
                            <img src="<%= request.getContextPath()%>/media/images/blue_book.png" class="book-image" alt="Diary">
                            <div class="book-bookmarks">
                                <div class="bookmark orange"></div>
                                <div class="bookmark yellow"></div>
                            </div>
                            <div class="book-title"><%=date%></div>
                        </div>
                    </a>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </main>
    </section>

    <!-- Include jQuery and jQuery UI for datepicker -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>

    <script>
        $(document).ready(function () {
            let isArchivedView = false;

            $(".diary-item").each(function () {
                if ($(this).hasClass('add-new-ignore')) {
                    $(this).show();
                    return;
                }

                var isArchived = $(this).data("archived") === true;

                if (isArchived) {
                    $(this).hide();
                }
            });

            // Initialize datepicker
            $("#startDate, #endDate").datepicker({
                dateFormat: "yy-mm-dd"
            });

            // Filter diary entries based on date range
            $('#filterBtn').click(function () {
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();

                // Check if start date is later than end date
                if (startDate && endDate) {
                    if (new Date(startDate) > new Date(endDate)) {
                        alert("Start date cannot be later than the end date.");
                        return; // Prevent filtering if the dates are invalid
                    }

                    $(".diary-item").each(function () {
                        var entryDate = $(this).data("date");

                        // Exclude "Add New Book" item and check if the entry date is within the range
                        if ($(this).hasClass('add-new-ignore')) {
                            $(this).show();
                        } else if (entryDate >= startDate && entryDate <= endDate) {
                            $(this).show();
                        } else {
                            $(this).hide();
                        }
                    });
                } else {
                    // Show all entries if no date range is selected
                    $(".diary-item").show();
                }
            });

            // Reset the date inputs and show all entries
            $('#resetBtn').click(function () {
                $("#startDate, #endDate").val(''); // Clear date inputs
                $(".diary-item").show(); // Show all entries
            });

            // Handle click on the View Archived button (toggle between archived/unarchived)
            $('#viewArchivedBtn').click(function () {
                if (isArchivedView) {
                    // Show unarchived diaries
                    $(".diary-item").each(function () {
                        // Skip the "Add New" item
                        if ($(this).hasClass('add-new-ignore')) {
                            $(this).show();
                            return;
                        }

                        if ($(this).data("archived") === false) {
                            $(this).show();
                        } else {
                            $(this).hide();
                        }
                    });

                    // Change button text
                    $(this).text('View Archived Diaries');
                } else {
                    // Show archived diaries
                    $(".diary-item").each(function () {
                        // Skip the "Add New" item
                        if ($(this).hasClass('add-new-ignore')) {
                            $(this).hide();
                            return;
                        }

                        if ($(this).data("archived") === true) {
                            $(this).show();
                        } else {
                            $(this).hide();
                        }
                    });

                    // Change button text
                    $(this).text('View Unarchived Diaries');
                }

                // Toggle the state
                isArchivedView = !isArchivedView;
            });
        });

    </script>

</html>
