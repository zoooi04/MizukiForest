<%@page import="java.util.List"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="model.*"%>

<%
    // Initialise EntityManager
    EntityManager em = Persistence.createEntityManagerFactory("MizukiForestPU").createEntityManager();

    // Create service
    BiomeService biomeService = new BiomeService(em);
    TreeService ts = new TreeService(em);
    ItemService is = new ItemService(em);
    TreeBoxService tbs = new TreeBoxService(em);

    // Dummy land list (replace with actual retrieval logic or pass from controller)
    List<Land> landList = (List<Land>) session.getAttribute("landList");
    List<Userinventoryitem> inventoryList = (List<Userinventoryitem>) session.getAttribute("userInventoryList");
    List<Userinventoryitem> treeOrItemList = (List<Userinventoryitem>) session.getAttribute("treeOrItemList");
    List<Userinventoryitem> biomeList = (List<Userinventoryitem>) session.getAttribute("biomeInventoryList");
    List<Userinventoryitem> treeboxList = (List<Userinventoryitem>) session.getAttribute("treeboxInventoryList");
    List<Biome> biomeAllList = (List<Biome>) session.getAttribute("biomeAllList");
    List<Tree> AllTrees = (List<Tree>) session.getAttribute("allTreeList");
    List<Item> AllItems = (List<Item>) session.getAttribute("AllItems");
    List<Landcontent> AllLandContents = (List<Landcontent>) session.getAttribute("AllLandContents");
    Users user = (Users) session.getAttribute("friend");

%>

<html lang="en" data-theme="mizuki_dark">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">

        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath()%>/media/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/mizukiuser.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/myForest.css">
        <style>
            .page-title-container {
                text-align: center;
                margin-bottom: 20px;
                padding-top: 20px;
                position: relative;
            }

            .page-title {
                font-size: 2.2rem;
                font-weight: bold;
                color: #fff;
                text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            }

            .back-button {
                position: absolute;
                top: 100px;
                left: 20px;
                font-size: 1.2rem;
                padding: 8px 15px;
                background-color: rgba(0, 0, 0, 0.6);
                color: white;
                border: 1px solid rgba(255, 255, 255, 0.3);
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .back-button:hover {
                background-color: rgba(0, 0, 0, 0.8);
            }
        </style>

    </head>
    <%request.setAttribute("pageTitle", "Trees");%>
    <jsp:include page="/shared/title.jsp"/>
    <%@ include file="/shared/header.jsp" %>

    <section style="padding-top:50px;">
        <!-- Page Title -->
        <div class="page-title-container" >
            <% if (user != null) {%>
            <h1 class="page-title"><%= user.getUsername()%>'s (<%= user.getUserid()%>) Forest Lands</h1>
            <%  }%>
        </div>

        <button class="back-button" onclick="history.back()" >
            <i class="fas fa-arrow-left"></i> Back
        </button>

        <div class="plots-wrapper" style="padding-top:20px;">
            <%
                for (int i = 0; i < landList.size(); i++) {
                    Land land = landList.get(i);
                    Biome biome = biomeService.findById(land.getBiomeid().getBiomeid());
                    if (biome != null) {
                        String biomeImagePath = request.getContextPath() + "/" + biome.getBiomeimage();
            %>
            <div class="plot-container" 
                 id="plot<%= i + 1%>" 
                 data-biome-id="<%= land.getBiomeid()%>" 
                 data-land-name="<%= land.getLandname()%>"
                 data-is-main-land="<%= land.getIsmainland()%>"
                 data-land-id="<%= land.getLandid()%>" 
                 data-biome-image="<%= biomeImagePath%>" 
                 style="<%= (i == 0 ? "" : "display: none;")%> background-image: url('<%= biomeImagePath%>');">
                <%-- Generate grid cells with JSP instead of JavaScript --%>
                <div class="grid-container" id="grid<%= i + 1%>">
                    <%
                        // Create grid cells (8x8 = 64 cells)
                        // Note: We're creating the grid with (1,1) at bottom left, (8,8) at top right
                        for (int row = 8; row >= 1; row--) {  // Start from top (8) to bottom (1)
                            for (int col = 1; col <= 8; col++) {  // Left (1) to right (8)
                                int cellIndex = (8 - row) * 8 + (col - 1);  // Adjust for 0-based index display

                                // Default background is the biome image
                                String cellBackground = biomeImagePath;
                                String cellContent = "";
                                boolean hasContent = false;

                                // Check if this cell has content in AllLandContents
                                for (Landcontent content : AllLandContents) {
                                    // Check if this content belongs to the current land
                                    if (content.getLandid().getLandid().equals(land.getLandid())
                                            && content.getXcoord() == col
                                            && content.getYcoord() == row
                                            && !content.getIsdeleted()) {

                                        // Print to console when content is found
                                        System.out.println("Plot " + (i + 1) + " has content at grid (" + col + "," + row + ") - Cell index: " + cellIndex);

                                        hasContent = true;

                                        // If it has an item, use item image
                                        if (content.getItemid() != null) {
                                            cellBackground = request.getContextPath() + "/" + content.getItemid().getItemimage();
                                            cellContent = "Item: " + content.getItemid().getItemname();
                                            System.out.println("  Content type: Item - " + content.getItemid().getItemname());
                                        } // If it has a tree, use tree image
                                        else if (content.getTreeid() != null) {
                                            cellBackground = request.getContextPath() + "/" + content.getTreeid().getTreeimage();
                                            cellContent = "Tree: " + content.getTreeid().getTreename();
                                            System.out.println("  Content type: Tree - " + content.getTreeid().getTreename());
                                        }

                                        break; // Found content for this cell, no need to continue checking
                                    }
                                }
                    %>
                    <div class="grid-cell" 
                         data-index="<%= cellIndex%>" 
                         data-grid="grid<%= i + 1%>"
                         data-content="<%= cellContent%>"
                         data-x="<%= col%>"
                         data-y="<%= row%>"
                         <%= hasContent ? "data-has-content=\"true\"" : ""%>
                         style="background-image: url('<%= cellBackground%>');">
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>

        <div class="plot-pagination">
            <button id="prevPlot" class="btn btn-sm btn-outline-secondary me-2">&lt;</button>
            <%
                if (landList != null) {
                    for (int i = 0; i < landList.size(); i++) {
                        String activeClass = (i == 0) ? "active" : "";
            %>
            <span class="dot <%= activeClass%>" data-plot="<%= i + 1%>"></span>
            <%
                    }
                }
            %>
            <button id="nextPlot" class="btn btn-sm btn-outline-secondary ms-2">&gt;</button>
        </div>
    </section>



    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Core variables for plot management
            const plotContainers = document.querySelectorAll('.plot-container');
            const totalPlots = plotContainers.length;
            const dots = document.querySelectorAll('.dot');

            // Navigation buttons
            const prevButton = document.getElementById('prevPlot');
            const nextButton = document.getElementById('nextPlot');

            // State variables
            let currentPlot = 1;
            let isMouseOverPlot = false;
            let plotInterval;

            // Function to show a specific plot
            function showPlot(plotNumber) {
                // Ensure plot number is within valid range
                if (plotNumber < 1) {
                    plotNumber = totalPlots;
                } else if (plotNumber > totalPlots) {
                    plotNumber = 1;
                }

                currentPlot = plotNumber;

                // Update plot visibility - just show the current plot
                plotContainers.forEach((plot, index) => {
                    plot.style.display = (index + 1 === currentPlot) ? 'block' : 'none';
                });

                // Update active dot
                dots.forEach((dot, index) => {
                    if (index + 1 === plotNumber) {
                        dot.classList.add('active');
                    } else {
                        dot.classList.remove('active');
                    }
                });
            }

            // Auto-rotation of plots - always rotate unless mouse is over
            function startPlotRotation() {
                clearInterval(plotInterval);
                plotInterval = setInterval(() => {
                    // Only rotate if mouse isn't over plot
                    if (!isMouseOverPlot) {
                        showPlot(currentPlot + 1);
                    }
                }, 10000); // Switch every 10 seconds
            }

            // Set up mouse over detection for plot containers
            const plotsWrapper = document.querySelector('.plots-wrapper');
            if (plotsWrapper) {
                plotsWrapper.addEventListener('mouseenter', function () {
                    isMouseOverPlot = true;
                });

                plotsWrapper.addEventListener('mouseleave', function () {
                    isMouseOverPlot = false;
                });
            }

            // Set up pagination buttons
            if (prevButton) {
                prevButton.addEventListener('click', function () {
                    showPlot(currentPlot - 1);
                });
            }

            if (nextButton) {
                nextButton.addEventListener('click', function () {
                    showPlot(currentPlot + 1);
                });
            }

            // Set up dot navigation
            dots.forEach(dot => {
                dot.addEventListener('click', function () {
                    const plotNumber = parseInt(this.getAttribute('data-plot'));
                    showPlot(plotNumber);
                });
            });

            // Initialize the plot display
            showPlot(1);

            // Start auto-rotation - always on
            startPlotRotation();
        });
    </script>
    
    
    <%  session.removeAttribute("friend");%>