<%@page import="java.util.Collection"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_light">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>

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
                    <button class="btn btn-light" id="resetBtn">Reset</button> <!-- Reset Button -->
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
                <div class="diary-item" data-date="<%=ymddate%>" data-archived="<%= isArchived ? "true" : "false" %>">
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

    <style>
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .diary-header {
            padding: 0 15px;
        }

        .btn-light {
            background-color: #f0f0f0;
            border: none;
            border-radius: 6px;
            padding: 8px 15px;
            font-size: 14px;
        }

        /* Modified to display 5 items per row */
        .diary-grid {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 20px;
            padding: 10px;
        }

        @media (max-width: 992px) {
            .diary-grid {
                grid-template-columns: repeat(3, 1fr);
            }
        }

        @media (max-width: 576px) {
            .diary-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        .diary-item {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .add-button-link {
            text-decoration: none;
        }

        /* Made add button match book size */
        .add-button {
            width: 150px;
            height: 180px;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .add-button span {
            font-size: 48px;
            color: #888;
            transition: all 0.3s ease;
        }

        .add-button:hover {
            background-color: #e0e0e0;
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }

        .add-button:hover span {
            color: #555;
            font-size: 54px;
        }

        .diary-link {
            text-decoration: none;
            color: inherit;
        }

        /* Improved book appearance */
        .book-container {
            position: relative;
            width: 100%;
            display: flex;
            justify-content: center;
            transition: all 0.3s ease;
        }

        .book-container:hover {
            transform: translateY(-5px);
            filter: brightness(1.05);
        }

        /* Increased book image size */
        .book-image {
            width: 100%;
            max-width: 150px;
            height: auto;
        }

        .book-bookmarks {
            position: absolute;
            top: 15px;
            right: 25%;
            display: flex;
            flex-direction: column;
            gap: 6px;
        }

        .bookmark {
            width: 15px;
            height: 20px;
            border-radius: 1px;
        }

        .orange {
            background-color: #ff7f00;
        }

        .yellow {
            background-color: #ffcf00;
        }

        /* Improved date display on book cover */
        .book-title {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: white;
            font-size: 16px;
            font-weight: bold;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.7);
            text-align: center;
            width: 80%;
            background-color: rgba(0, 0, 0, 0.2);
            padding: 5px;
            border-radius: 3px;
        }
    </style>

    <!-- Include jQuery and jQuery UI for datepicker -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>

    <script>
        $(document).ready(function () {
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

            // Handle click on the View Archived button
            $('#viewArchivedBtn').click(function () {
                $(".diary-item").each(function () {
                    // If this entry is archived, show it
                    if ($(this).data("archived") === "true") {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                });
            });
        });
    </script>

</html>
