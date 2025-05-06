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

%>

<!DOCTYPE html>
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

        </style>

    </head>

    <section id="myForest" class="" style="padding-top:50px; padding-bottom:140px">
        <%request.setAttribute("pageTitle", "Trees");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>
        
        
        <% if((String)session.getAttribute("error") != null){%>
            <jsp:include page="/view/forest/error.jsp"/>
            
        <% session.removeAttribute("error");}%>
        
        <% if((String)session.getAttribute("error2") != null){%>
            <jsp:include page="/view/forest/error2.jsp"/>
            
        <% session.removeAttribute("error2");}%>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Game Layout with buttons flanking the plot -->
            <div class="game-layout">
                <!-- Left side buttons -->
                <div class="left-buttons">
                    <button id="dailyClaimBtn" class="game-button">Daily Claim</button>
                    <button id="dailyTasksBtn" class="game-button">Daily Tasks</button>
                </div>

                <!-- Center content with plot -->
                <div class="center-content">
                    <h2 class="text-center mb-4" id="plotTitle">Plains</h2>

                    <!-- Plot Type Toggle -->
                    <div class="toggle-container">
                        <span>Show Main Plot</span>
                        <label class="toggle-switch">
                            <input type="checkbox" id="plotTypeToggle">
                            <span class="toggle-slider"></span>
                        </label>
                        <span>Show Side Plots</span>
                    </div>

                    <%-- Dynamic Plot Containers generated based on landList --%>
                    <div class="plots-wrapper">
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

                </div>
                <!-- Right side buttons -->
                <div class="right-buttons">
                    <button id="focusSessionBtn" class="game-button">Focus <i class="fa-solid fa-hourglass-start"></i></button>
                    <button id="visitBtn" class="game-button">Visit <i class="fa-solid fa-magnifying-glass"></i></button>
                </div>
            </div>
            <!-- Plot Controls -->
            <div class="plot-controls">
                <div class="control-buttons">
                    <button id="ShopBtn" class="control-button">
                        <i class="fa-solid fa-shop"></i>
                    </button>
                    <button id="backpackBtn" class="control-button">
                        <i class="fa-solid fa-tree"></i>
                    </button>
                    <button id="inventoryModalBtn" class="control-button">
                        <i class='bx bxs-backpack'></i>
                    </button>
                    <button id="removePlantBtn" style="background-color: red" class="control-button">
                        <i class="fa-solid fa-trash"></i>
                    </button>

                </div>

                <%-- Dynamic Pagination based on landList size --%>
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

                <div class="control-buttons">
                    <button id="questionBtn" class="control-button">
                        <i class="fa-solid fa-question"></i>
                    </button>
                    <button id="levelBtn" class="control-button">
                        <i class="fa-solid fa-arrow-up-right-dots"></i>
                    </button>
                    <button id="settingsBtn" class="control-button">
                        <i class="fas fa-cog"></i>
                    </button>
                    <button id="manifestBtn" style="background-color: green" class="control-button">
                        <i class="fa-solid fa-face-smile-wink"></i>
                    </button>
                </div>
            </div>
        </div>

        <!-- Inventory Section (Initially Hidden) -->
        <div class="inventory-section" id="inventorySection">
            <h5 class="text-center mb-1" style="font-size: 0.9rem; color:black">Your Planting Items - Click to Plant</h5>
            <div class="inventory-items">
                <% //if (treeOrItemList != null && !treeOrItemList.isEmpty()) {
                    for (Userinventoryitem userInventory : treeOrItemList) {
                        if (userInventory.getTreeid() != null) {
                            Tree tree = ts.findTreeById(userInventory.getTreeid().getTreeid());
                            if (tree != null) {
                                String treeImagePath = request.getContextPath() + "/" + tree.getTreeimage();
                %>
                <div class="inventory-item"
                     data-item="tree-<%= tree.getTreeid()%>" 
                     data-type="tree" 
                     style="background-image: url('<%= treeImagePath%>');">
                    <span class="item-quantity"><%= userInventory.getQuantity()%></span>
                </div>
                <%
                    }
                } else if (userInventory.getItemid() != null) {
                    Item item = is.findItemById(userInventory.getItemid().getItemid());
                    if (item != null && !"Tool".equals(item.getItemtype())) { // Skip items with type "Tool"
                        String itemImagePath = request.getContextPath() + "/" + item.getItemimage();
                %>
                <div class="inventory-item"
                     data-item="item-<%= item.getItemid()%>" 
                     data-type="item" 
                     style="background-image: url('<%= itemImagePath%>');">
                    <span class="item-quantity"><%= userInventory.getQuantity()%></span>
                </div>
                <%
                            }
                        }
                    }
                %>
            </div>
        </div>

        <!-- Settings Modal -->
        <div class="modal fade" id="settingsModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Plot Settings</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="autoRotateToggle" class="form-label d-flex justify-content-between">
                                Auto-rotate Plots
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" id="autoRotateToggle" checked>
                                </div>
                            </label>
                        </div>
                        <div class="mb-3">
                            <label for="rotateAllPlotsToggle" class="form-label d-flex justify-content-between">
                                Rotate All Main and Side Plots
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" id="rotateAllPlotsToggle">
                                </div>
                            </label>
                        </div>
                        <div class="mb-3">
                            <label for="clearPlotBtn" class="form-label">Clear Current Plot</label>
                            <button id="clearPlotBtn" class="btn btn-warning w-100">Clear</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Inventory Modal -->
        <div class="modal fade" id="inventoryModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Your Inventory</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="inventory-items-modal">
                            <!-- Empty state message -->
                            <p class="text-center text-muted" id="emptyInventoryMsg">Your inventory is empty</p>

                            <!-- This div will be populated with items when you have them -->
                            <div id="inventoryItemsContainer" class="d-flex flex-wrap gap-3 justify-content-center">
                                <!-- Items will appear here when added -->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary w-100" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Question Modal -->
        <div class="modal fade" id="questionModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Mizuki Forest Guide</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="accordion" id="forestGuideAccordion">
                            <!-- Getting Started Section -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingOne">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                        Getting Started
                                    </button>
                                </h2>
                                <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#forestGuideAccordion">
                                    <div class="accordion-body">
                                        <p>Welcome to Mizuki Forest! This is your personal virtual forest where you can plant trees, decorate your land, and grow your own unique ecosystem.</p>
                                        <ul>
                                            <li><strong>Main Plot vs Side Plots:</strong> Toggle between your main plot and side plots using the toggle switch above the forest grid.</li>
                                            <li><strong>Navigation:</strong> Use the dots or arrow buttons below the forest to navigate between different plots.</li>
                                            <li><strong>Daily Rewards:</strong> Don't forget to claim your daily rewards by clicking the "Daily Claim" button!</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <!-- Planting & Growing Section -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingTwo">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                        Planting & Growing
                                    </button>
                                </h2>
                                <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#forestGuideAccordion">
                                    <div class="accordion-body">
                                        <p>Growing your forest is easy and rewarding:</p>
                                        <ol>
                                            <li>Click the <i class="fa-solid fa-tree"></i> button to open your tree inventory.</li>
                                            <li>Drag a tree or item from your inventory onto an empty cell in your forest plot.</li>
                                            <li>You earn more trees as you complete activities and focus sessions.</li>
                                            <li>Flex your forests to the world!</li>
                                        </ol>
                                        <p class="text-info"><i class="fa-solid fa-lightbulb"></i> Tip: Complete focus sessions to earn more trees!</p>
                                    </div>
                                </div>
                            </div>

                            <!-- Inventory Management Section -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingThree">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                        Inventory Management
                                    </button>
                                </h2>
                                <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#forestGuideAccordion">
                                    <div class="accordion-body">
                                        <p>Managing your inventory is crucial for growing your forest:</p>
                                        <ul>
                                            <li>Access your full inventory by clicking the <i class='bx bxs-backpack'></i> button.</li>
                                            <li>Your inventory contains trees, decorations, and special items.</li>
                                            <li>Earn new items by completing daily tasks and focus sessions.</li>
                                            <li>You can purchase special trees and decorations from the store (available in the main menu).</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <!-- Social Features Section -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingFour">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                        Social Features
                                    </button>
                                </h2>
                                <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#forestGuideAccordion">
                                    <div class="accordion-body">
                                        <p>Connect with friends and other forest enthusiasts:</p>
                                        <ul>
                                            <li>Visit friends' forests by clicking the "Visit" button.</li>
                                            <li>Search your friends' forests by inserting their ID.</li>
                                            <li>Turn their forests' into your motivation to focus more.</li>
                                            <li>Share your forest progress to inspire others.</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <!-- Focus Sessions Section -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingFive">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                        Focus Sessions
                                    </button>
                                </h2>
                                <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive" data-bs-parent="#forestGuideAccordion">
                                    <div class="accordion-body">
                                        <p>Focus sessions help you stay productive while growing your forest:</p>
                                        <ul>
                                            <li>Click the "Focus" button to start a focus session.</li>
                                            <li>Set a timer for your desired focus duration.</li>
                                            <li>Complete the session without interruptions to earn rewards.</li>
                                            <li>You earn tree boxes during successful focus sessions.</li>
                                            <li>Chain longer focus sessions to receive bonus rewards.</li>
                                        </ul>
                                        <p class="text-warning"><i class="fa-solid fa-triangle-exclamation"></i> Note: Exiting the app during a focus session will end it without rewards.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary w-100" data-bs-dismiss="modal">I Got It!</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Bootstrap JS and Popper -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

        <script>
            const contextPath = '<%= request.getContextPath()%>';
            const treeOrItemList = [
            <% if (treeOrItemList != null) {
                    for (Userinventoryitem item : treeOrItemList) {
                        if (item.getTreeid() != null) {
                            Tree tree = ts.findTreeById(item.getTreeid().getTreeid());%>
            {
            type: 'tree',
                    id: '<%= tree.getTreeid()%>',
                    name: '<%= tree.getTreename()%>',
                    image: '<%= tree.getTreeimage()%>',
                    description: '<%= tree.getTreedescription()%>',
                    quantity: <%= item.getQuantity()%>
            },<%
            } else if (item.getItemid() != null) {
                Item itm = is.findItemById(item.getItemid().getItemid());
            %>
            {
            type: 'item',
                    id: '<%= itm.getItemid()%>',
                    name: '<%= itm.getItemname()%>',
                    image: '<%= itm.getItemimage()%>',
                    description: '<%= itm.getItemtype()%>',
                    quantity: <%= item.getQuantity()%>
            },<%
                        }
                    }
                } %>

            ];
            const treeboxList = [
            <% if (treeboxList != null) {
                    for (Userinventoryitem item : treeboxList) {
                        if (item.getTreeboxid() != null) {
                            Treebox tb = tbs.findTreeboxById(item.getTreeboxid().getTreeboxid());%>
            {

            id: '<%= tb.getTreeboxid()%>',
                    name: '<%= tb.getTreeboxname()%>',
                    image: '<%= tb.getTreeboximage()%>',
                    quantity: <%= item.getQuantity()%>
            },<%
                        }
                    }
                }%>

            ];
            const allTrees = [
            <%
                List<Tree> allTreesList = (List<Tree>) session.getAttribute("allTreeList");
                if (allTreesList != null) {
                    for (Tree tree : allTreesList) {
            %>
            {
            id: '<%= tree.getTreeid()%>',
                    name: '<%= tree.getTreename()%>',
                    description: '<%= tree.getTreedescription()%>',
                    image: '<%= tree.getTreeimage()%>',
                    rarity: '<%= tree.getRarityid() != null ? tree.getRarityid().getRarityname() : "Unknown"%>'
            },
            <%
                    }
                }
            %>
            ];
        </script>

        <script src ="${pageContext.request.contextPath}/js/clearPlot.js"></script>
        <script src="${pageContext.request.contextPath}/js/planting-script.js"></script>
        <script src ="${pageContext.request.contextPath}/js/level-modal.js"></script>
        <script src ="${pageContext.request.contextPath}/js/inventory-modal.js"></script>
        <script src ="${pageContext.request.contextPath}/js/dailyClaim.js"></script>
        <script src ="${pageContext.request.contextPath}/js/focus.js"></script>        
        <script src ="${pageContext.request.contextPath}/js/dailyTasks.js"></script>
        <script src ="${pageContext.request.contextPath}/js/viewFriend.js"></script>



        <!-- Custom JavaScript -->


        <script>
            document.addEventListener('DOMContentLoaded', function () {
            // Get all plot containers
            const plotContainers = document.querySelectorAll('.plot-container');
            const totalPlots = plotContainers.length;
            let plantingCancelButton = null;
            // State variables
            let currentPlot = 1;
            let isMainPlot = true;
            let autoRotateEnabled = false; //no auto rotate
            let rotateAllPlotsEnabled = false; // New variable for the new toggle
            let isMouseOverPlot = false;
            let isRemovalMode = false;
            let removalOverlay = null;
            let removalCancelButton = null;
            // Elements
            const plotTitle = document.getElementById('plotTitle');
            const toggleSwitch = document.getElementById('plotTypeToggle');
            const toggleContainer = document.querySelector('.toggle-container'); // Get the toggle container
            const inventorySection = document.getElementById('inventorySection');
            const backpackBtn = document.getElementById('backpackBtn');
            const settingsBtn = document.getElementById('settingsBtn');
            let plotInterval;
            // Add drag and drop event listeners to grid cells
            function setupGridCellListeners() {
            const gridCells = document.querySelectorAll('.grid-cell');
            gridCells.forEach(cell => {
            // Add event listener for drop
            cell.addEventListener('dragover', function (e) {
            e.preventDefault();
            e.dataTransfer.dropEffect = 'move';
            });
            cell.addEventListener('drop', function (e) {
            e.preventDefault();
            const dataString = e.dataTransfer.getData('text/plain');
            // Only process drops from inventory items
            if (!dataString.startsWith('cell:')) {
            // It's a new item from inventory
            const itemParts = dataString.split('|');
            const itemType = itemParts[0];
            const itemImage = itemParts[1] || '';
            plantItem(this, itemType, itemImage);
            }
            });
            });
            }



            // Call this function to setup listeners
            setupGridCellListeners();
            // Check if modal elements exist before initializing
            function safeInitModal(id) {
            const modalElement = document.getElementById(id);
            if (modalElement && typeof bootstrap !== 'undefined') {
            return new bootstrap.Modal(modalElement);
            }
            return null;
            }

            // Modal initialization - only if elements exist
            
            const settingsModal = safeInitModal('settingsModal');
            // Safe button click handler
            function addSafeClickHandler(btnId, modal) {
            const btn = document.getElementById(btnId);
            if (btn && modal) {
            btn.addEventListener('click', function () {
            modal.show();
            });
            }
            }

            // Button click handlers - only if both button and modal exist
            if (settingsBtn && settingsModal) {
            settingsBtn.addEventListener('click', function () {
            settingsModal.show();
            });
            }

            // Add click event listener to the remove button
            const removePlantBtn = document.getElementById('removePlantBtn');
            if (removePlantBtn) {
            removePlantBtn.addEventListener('click', function() {
            // Only allow removal if not already in removal mode or planting mode
            if (!isRemovalMode && !isPlantingMode) {
            startRemovalMode();
            }
            });
            }

            // Inventory toggle - only if both elements exist
            if (backpackBtn && inventorySection) {
            backpackBtn.addEventListener('click', function () {
            inventorySection.classList.toggle('visible');
            });
            // Close inventory when clicking elsewhere
            document.addEventListener('click', function (e) {
            if (!inventorySection.contains(e.target) &&
                    e.target !== backpackBtn &&
                    !backpackBtn.contains(e.target) &&
                    inventorySection.classList.contains('visible')) {
            inventorySection.classList.remove('visible');
            }
            });
            }

            // Function to update plot visibility based on settings
            function updatePlotVisibility() {
            // If rotate all plots is enabled, show all plots regardless of main/side status
            if (rotateAllPlotsEnabled) {
            // Show all plots but keep only the current one visible
            plotContainers.forEach((plot, index) => {
            plot.style.display = (index + 1 === currentPlot) ? 'block' : 'none';
            });
            // Hide the toggle container
            if (toggleContainer) {
            toggleContainer.style.display = 'none';
            }
            } else {
            // Show the toggle container
            if (toggleContainer) {
            toggleContainer.style.display = 'flex';
            }

            // Filter plots based on ismainland value AND only show the currently selected plot
            plotContainers.forEach((plot, index) => {
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            const matchesPlotType = (isMainPlot && isMainLand) || (!isMainPlot && !isMainLand);
            const isCurrentlySelected = (index + 1 === currentPlot);
            // Only show if it matches the plot type AND is the currently selected plot
            plot.style.display = (matchesPlotType && isCurrentlySelected) ? 'block' : 'none';
            });
            // Make sure the current plot is visible if it's still valid with current settings
            const currentPlotElement = document.getElementById('plot' + currentPlot);
            if (currentPlotElement) {
            const isCurrentPlotMainLand = currentPlotElement.getAttribute('data-is-main-land') === 'true';
            const isCurrentPlotVisible = (isMainPlot && isCurrentPlotMainLand) || (!isMainPlot && !isCurrentPlotMainLand);
            if (!isCurrentPlotVisible) {
            // Find first visible plot with the current settings
            let firstVisiblePlot = findFirstVisiblePlot();
            if (firstVisiblePlot > 0) {
            showPlot(firstVisiblePlot);
            }
            }
            }
            }
            }

            // Helper function to find the first visible plot based on current settings
            function findFirstVisiblePlot() {
            if (rotateAllPlotsEnabled) {
            // When rotate all is enabled, any plot can be first
            return 1;
            }

            // Otherwise, find first plot that matches main/side setting
            for (let i = 0; i < plotContainers.length; i++) {
            const plot = plotContainers[i];
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            if ((isMainPlot && isMainLand) || (!isMainPlot && !isMainLand)) {
            return i + 1;
            }
            }
            return - 1; // No visible plots found
            }

            // Plot toggle switch
            if (toggleSwitch) {
            toggleSwitch.addEventListener('change', function () {
            isMainPlot = !this.checked;
            updatePlotVisibility();
            // If current plot is now hidden, switch to first visible one
            const currentPlotElement = document.getElementById('plot' + currentPlot);
            if (currentPlotElement && currentPlotElement.style.display === 'none') {
            let firstVisiblePlot = findFirstVisiblePlot();
            if (firstVisiblePlot > 0) {
            showPlot(firstVisiblePlot);
            }
            }

            console.log(isMainPlot ? "Switched to Main Plot" : "Switched to Side Plot");
            });
            }

            // Add listener for the rotate all plots toggle
            const rotateAllPlotsToggle = document.getElementById('rotateAllPlotsToggle');
            if (rotateAllPlotsToggle) {
            rotateAllPlotsToggle.addEventListener('change', function () {
            rotateAllPlotsEnabled = this.checked;
            updatePlotVisibility();
            // Reset plot rotation with new settings
            resetRotationTimer();
            console.log(rotateAllPlotsEnabled ? "Rotating all plots" : "Rotating only filtered plots");
            });
            }

            // Plot navigation
            function showPlot(plotNumber) {
            currentPlot = plotNumber;
            // Set plot title from data attribute
            const currentPlotElement = document.getElementById('plot' + plotNumber);
            if (currentPlotElement) {
            plotTitle.textContent = currentPlotElement.getAttribute('data-land-name');
            }

            // Get fresh collection of dots (they might have been added after initial load)
            const dots = document.querySelectorAll('.dot');
            // Update active dot
            dots.forEach((dot, index) => {
            if (index + 1 === plotNumber) {
            dot.classList.add('active');
            } else {
            dot.classList.remove('active');
            }
            });
            // Update plot visibility based on current settings rather than just showing the plot
            updatePlotVisibility();
            }

            // Function to find the next appropriate plot based on current settings
            function findNextPlot() {
            let nextPlot = currentPlot;
            let found = false;
            for (let i = 0; i < totalPlots; i++) {
            nextPlot = nextPlot % totalPlots + 1; // Loop through plots
            const plot = document.getElementById('plot' + nextPlot);
            // If rotate all plots is enabled, any plot can be next
            if (rotateAllPlotsEnabled) {
            found = true;
            break;
            }
            // Otherwise, filter by main/side setting
            else if (plot) {
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            if ((isMainPlot && isMainLand) || (!isMainPlot && !isMainLand)) {
            found = true;
            break;
            }
            }
            }

            return found ? nextPlot : - 1;
            }

            // Auto-rotation of plots
            function startPlotRotation() {
            if (!autoRotateEnabled)
                    return;
            clearInterval(plotInterval);
            plotInterval = setInterval(() => {
            // Only rotate if inventory isn't open and mouse isn't over plot
            if ((!inventorySection || !inventorySection.classList.contains('visible')) && !isMouseOverPlot) {
            // Find next visible plot
            const nextPlot = findNextPlot();
            if (nextPlot > 0) {
            showPlot(nextPlot);
            }
            }
            }, 3500); // Switch every second
            }

            // Start auto-rotation
            startPlotRotation();
            // When user interacts with navigation, reset the timer
            function resetRotationTimer() {
            clearInterval(plotInterval);
            if (autoRotateEnabled) {
            startPlotRotation();
            }
            }

            // Function to safely add event listeners to elements
            function addListenerIfExists(elementId, eventType, handler) {
            const element = document.getElementById(elementId);
            if (element) {
            element.addEventListener(eventType, handler);
            }
            }

            // Navigation button handlers
            addListenerIfExists('prevPlot', 'click', function () {
            let prevPlot = currentPlot;
            let found = false;
            for (let i = 0; i < totalPlots; i++) {
            prevPlot = prevPlot - 1;
            if (prevPlot < 1)
                    prevPlot = totalPlots; // Loop to last plot

            const plot = document.getElementById('plot' + prevPlot);
            // If rotate all plots is enabled, any plot can be selected
            if (rotateAllPlotsEnabled) {
            found = true;
            break;
            }
            // Otherwise check if it matches the main/side filter
            else if (plot) {
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            if ((isMainPlot && isMainLand) || (!isMainPlot && !isMainLand)) {
            found = true;
            break;
            }
            }
            }

            if (found) {
            // If rotate all plots is not enabled, handle toggle switching
            if (!rotateAllPlotsEnabled) {
            const plot = document.getElementById('plot' + prevPlot);
            if (plot) {
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            // Update toggle if needed
            if ((isMainLand && !isMainPlot) || (!isMainLand && isMainPlot)) {
            toggleSwitch.checked = !isMainLand; // checked = side plot
            isMainPlot = isMainLand;
            console.log(isMainPlot ? "Switched to Main Plot" : "Switched to Side Plot");
            }
            }
            }

            showPlot(prevPlot);
            resetRotationTimer();
            }
            });
            addListenerIfExists('nextPlot', 'click', function () {
            let nextPlot = currentPlot;
            let found = false;
            for (let i = 0; i < totalPlots; i++) {
            nextPlot = nextPlot % totalPlots + 1; // Loop back to first plot

            const plot = document.getElementById('plot' + nextPlot);
            // If rotate all plots is enabled, any plot can be selected
            if (rotateAllPlotsEnabled) {
            found = true;
            break;
            }
            // Otherwise check if it matches the main/side filter
            else if (plot) {
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            if ((isMainPlot && isMainLand) || (!isMainPlot && !isMainLand)) {
            found = true;
            break;
            }
            }
            }

            if (found) {
            // If rotate all plots is not enabled, handle toggle switching
            if (!rotateAllPlotsEnabled) {
            const plot = document.getElementById('plot' + nextPlot);
            if (plot) {
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            // Update toggle if needed
            if ((isMainLand && !isMainPlot) || (!isMainLand && isMainPlot)) {
            toggleSwitch.checked = !isMainLand; // checked = side plot
            isMainPlot = isMainLand;
            console.log(isMainPlot ? "Switched to Main Plot" : "Switched to Side Plot");
            }
            }
            }

            showPlot(nextPlot);
            resetRotationTimer();
            }
            });
            // Add click events to dots - need to do this after they're rendered
            function setupDotListeners() {
            const dots = document.querySelectorAll('.dot');
            dots.forEach(dot => {
            dot.addEventListener('click', function () {
            const plotNumber = parseInt(this.getAttribute('data-plot'));
            const plot = document.getElementById('plot' + plotNumber);
            if (plot) {
            // If rotate all plots is not enabled, handle toggle switching
            if (!rotateAllPlotsEnabled) {
            const isMainLand = plot.getAttribute('data-is-main-land') === 'true';
            // If current toggle state doesn't match the plot type, update the toggle
            if ((isMainLand && !isMainPlot) || (!isMainLand && isMainPlot)) {
            // Update toggle switch UI
            toggleSwitch.checked = !isMainLand; // checked = side plot

            // Update the isMainPlot variable
            isMainPlot = isMainLand;
            console.log(isMainPlot ? "Switched to Main Plot" : "Switched to Side Plot");
            }
            }

            // Show the plot
            showPlot(plotNumber);
            resetRotationTimer();
            }
            });
            });
            }

            // Setup dot listeners
            setupDotListeners();
            // Settings controls - only if elements exist
            addListenerIfExists('autoRotateToggle', 'change', function () {
            autoRotateEnabled = this.checked;
            if (autoRotateEnabled) {
            startPlotRotation();
            } else {
            clearInterval(plotInterval);
            }
            });
            // Track mouse over plot area
            plotContainers.forEach(plot => {
            plot.addEventListener('mouseenter', function () {
            isMouseOverPlot = true;
            });
            plot.addEventListener('mouseleave', function () {
            isMouseOverPlot = false;
            });
            });
            // Initialize with first plot
            showPlot(1);
            // Initialize visibility based on settings
            updatePlotVisibility();
            // planting 
            const inventoryItems = document.querySelectorAll('.inventory-item');
            // Setup planting mode variables
            let isPlantingMode = false;
            let selectedItem = null;
            let originalAutoRotateState = false;
            let plantingOverlay = null;
            // Create a planting overlay element to show instructions
            function createPlantingOverlay() {
            // First create a container for the message only
            const messageContainer = document.createElement('div');
            messageContainer.className = 'planting-message';
            messageContainer.innerHTML = 'Select a grid to plant';
            messageContainer.style.position = 'absolute';
            messageContainer.style.top = '20px';
            messageContainer.style.left = '50%';
            messageContainer.style.transform = 'translateX(-50%)';
            messageContainer.style.backgroundColor = 'rgba(0, 0, 0, 0.7)';
            messageContainer.style.padding = '10px 20px';
            messageContainer.style.borderRadius = '5px';
            messageContainer.style.color = 'white';
            messageContainer.style.fontWeight = 'bold';
            messageContainer.style.zIndex = '1000';
            messageContainer.style.pointerEvents = 'none'; // Allows clicks to pass through

            return messageContainer;
            }

            // Start planting mode
            function startPlantingMode(item) {
            console.log("Starting planting mode for item:", item.getAttribute('data-type'), item.getAttribute('data-item'));
            // Store the original auto rotate state and disable rotation
            originalAutoRotateState = autoRotateEnabled;
            autoRotateEnabled = false;
            clearInterval(plotInterval);
            // Set planting mode and selected item
            isPlantingMode = true;
            selectedItem = item;
            // Add message overlay to current plot
            const currentPlotElement = document.getElementById('plot' + currentPlot);
            if (currentPlotElement) {
            plantingOverlay = createPlantingOverlay();
            currentPlotElement.appendChild(plantingOverlay);
            // Highlight available grid cells
            highlightAvailableGrids();
            // Add a cancel button as an X in the corner
            const cancelButton = document.createElement('div');
            cancelButton.className = 'cancel-planting-btn';
            cancelButton.innerHTML = '✕'; // X symbol
            cancelButton.style.position = 'absolute';
            cancelButton.style.top = '10px';
            cancelButton.style.right = '10px';
            cancelButton.style.width = '30px';
            cancelButton.style.height = '30px';
            cancelButton.style.backgroundColor = 'rgba(244, 67, 54, 0.8)';
            cancelButton.style.color = 'white';
            cancelButton.style.border = 'none';
            cancelButton.style.borderRadius = '50%'; // Makes it a circle
            cancelButton.style.display = 'flex';
            cancelButton.style.alignItems = 'center';
            cancelButton.style.justifyContent = 'center';
            cancelButton.style.cursor = 'pointer';
            cancelButton.style.fontWeight = 'bold';
            cancelButton.style.zIndex = '1001';
            cancelButton.style.boxShadow = '0 2px 5px rgba(0,0,0,0.3)';
            cancelButton.addEventListener('click', function() {
            console.log("Cancel button clicked");
            cancelPlanting();
            });
            currentPlotElement.appendChild(cancelButton);
            // Store reference to the cancel button for later removal
            plantingCancelButton = cancelButton;
            }
            }

            // Cancel planting mode
            function cancelPlanting() {
            console.log("Canceling planting mode");
            isPlantingMode = false;
            selectedItem = null;
            // Restore auto rotation if it was enabled
            autoRotateEnabled = originalAutoRotateState;
            if (autoRotateEnabled) {
            startPlotRotation();
            }

            // Remove overlay
            if (plantingOverlay && plantingOverlay.parentNode) {
            plantingOverlay.parentNode.removeChild(plantingOverlay);
            plantingOverlay = null;
            }

            // Remove cancel button
            if (plantingCancelButton && plantingCancelButton.parentNode) {
            plantingCancelButton.parentNode.removeChild(plantingCancelButton);
            plantingCancelButton = null;
            }

            // Remove cell highlighting - Make sure this happens!
            removeGridHighlights();
            }

            function highlightAvailableGrids() {
            console.log("Highlighting available grid cells");
            const currentGridId = 'grid' + currentPlot;
            const cells = document.querySelectorAll('#' + currentGridId + ' .grid-cell');
            cells.forEach(cell => {
            // Check if the cell already has content
            const hasContent = cell.getAttribute('data-has-content') === 'true';
            if (hasContent) {
            // Make occupied cells appear "blocked"
            cell.style.opacity = '0.3';
            cell.style.cursor = 'not-allowed';
            cell.classList.add('occupied');
            } else {
            // Make available cells appear clickable
            cell.style.cursor = 'pointer';
            cell.style.border = '2px dashed yellow';
            cell.classList.add('available');
            // Add click event for planting
            cell.addEventListener('click', plantItemOnGrid);
            }
            });
            }

            // Remove highlighting from grid cells
            function removeGridHighlights() {
            console.log("Removing grid highlights");
            const cells = document.querySelectorAll('.grid-cell');
            cells.forEach(cell => {
            cell.style.opacity = '';
            cell.style.cursor = '';
            cell.style.border = '';
            cell.classList.remove('available');
            cell.classList.remove('occupied');
            // Remove planting event listener
            cell.removeEventListener('click', plantItemOnGrid);
            });
            }

            // Plant item on selected grid
            function plantItemOnGrid(e) {
            if (!isPlantingMode || !selectedItem) return;
            const cell = e.currentTarget;
            const xCoord = parseInt(cell.getAttribute('data-x'));
            const yCoord = parseInt(cell.getAttribute('data-y'));
            const plotId = document.getElementById('plot' + currentPlot).getAttribute('data-land-id');
            const itemType = selectedItem.getAttribute('data-type');
            const itemId = selectedItem.getAttribute('data-item').split('-')[1];
            console.log("Planting item:", itemType, itemId, "at", xCoord, yCoord, "on plot", plotId);
            // Create form and submit
            submitPlantingForm(plotId, xCoord, yCoord, itemType, itemId);
            // Exit planting mode after planting
            cancelPlanting();
            }

            // Submit form to update database
            function submitPlantingForm(landId, xCoord, yCoord, itemType, itemId) {
            console.log("Submitting planting form with data:", {
            landId: landId,
                    xCoord: xCoord,
                    yCoord: yCoord,
                    itemType: itemType,
                    itemId: itemId
            });
            // Create a form element
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/MizukiForest/PlantItemServlet'; // Replace with your servlet path
            form.style.display = 'none';
            // Add form fields
            const fields = {
            'landId': landId,
                    'xCoord': xCoord,
                    'yCoord': yCoord,
                    'itemType': itemType,
                    'itemId': itemId
            };
            // Create input elements for each field
            for (const [key, value] of Object.entries(fields)) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = value;
            form.appendChild(input);
            }

            // Add form to document and submit
            document.body.appendChild(form);
            form.submit();
            }

            // Add click event listeners to inventory items
            inventoryItems.forEach(item => {
            item.addEventListener('click', function() {
            // Only allow planting if not already in planting mode
            if (!isPlantingMode) {
            startPlantingMode(this);
            }
            });
            });
            // Check if contextPath is defined, otherwise set default
            if (typeof contextPath === 'undefined') {
            // Fallback if not defined
            contextPath = '/MizukiForest';
            }


// Function to handle planting of items - uses data attributes from the grid cells
            function plantItem(cell, itemType, itemImage) {
            if (!cell) return;
            // Update the cell's appearance with the new item/tree
            cell.style.backgroundImage = `url('${itemImage}')`;
            cell.setAttribute('data-has-content', 'true');
            // Additional visual feedback
            const feedback = document.createElement('div');
            feedback.className = 'planting-feedback';
            feedback.textContent = 'Planted!';
            feedback.style.position = 'absolute';
            feedback.style.top = '50%';
            feedback.style.left = '50%';
            feedback.style.transform = 'translate(-50%, -50%)';
            feedback.style.backgroundColor = 'rgba(0, 255, 0, 0.7)';
            feedback.style.padding = '5px 10px';
            feedback.style.borderRadius = '5px';
            feedback.style.color = 'white';
            feedback.style.fontWeight = 'bold';
            feedback.style.zIndex = '1001';
            cell.appendChild(feedback);
            // Remove the feedback after animation
            setTimeout(() => {
            if (feedback.parentNode) {
            feedback.parentNode.removeChild(feedback);
            }
            }, 1000);
            }


            //unplant them
            // Function to start removal mode
            function startRemovalMode() {
            console.log("Starting removal mode");
            // Store the original auto rotate state and disable rotation
            originalAutoRotateState = autoRotateEnabled;
            autoRotateEnabled = false;
            clearInterval(plotInterval);
            // Set removal mode
            isRemovalMode = true;
            // Add message overlay to current plot
            const currentPlotElement = document.getElementById('plot' + currentPlot);
            if (currentPlotElement) {
            removalOverlay = createRemovalOverlay();
            currentPlotElement.appendChild(removalOverlay);
            // Highlight occupied grid cells
            highlightOccupiedGrids();
            // Add a cancel button as an X in the corner
            const cancelButton = document.createElement('div');
            cancelButton.className = 'cancel-removal-btn';
            cancelButton.innerHTML = '✕'; // X symbol
            cancelButton.style.position = 'absolute';
            cancelButton.style.top = '10px';
            cancelButton.style.right = '10px';
            cancelButton.style.width = '30px';
            cancelButton.style.height = '30px';
            cancelButton.style.backgroundColor = 'rgba(244, 67, 54, 0.8)';
            cancelButton.style.color = 'white';
            cancelButton.style.border = 'none';
            cancelButton.style.borderRadius = '50%'; // Makes it a circle
            cancelButton.style.display = 'flex';
            cancelButton.style.alignItems = 'center';
            cancelButton.style.justifyContent = 'center';
            cancelButton.style.cursor = 'pointer';
            cancelButton.style.fontWeight = 'bold';
            cancelButton.style.zIndex = '1001';
            cancelButton.style.boxShadow = '0 2px 5px rgba(0,0,0,0.3)';
            cancelButton.addEventListener('click', function() {
            console.log("Cancel button clicked");
            cancelRemoval();
            });
            currentPlotElement.appendChild(cancelButton);
            // Store reference to the cancel button for later removal
            removalCancelButton = cancelButton;
            }
            }



// Create a removal overlay element to show instructions
            function createRemovalOverlay() {
            const messageContainer = document.createElement('div');
            messageContainer.className = 'removal-message';
            messageContainer.innerHTML = 'Select the grid to remove the item/tree from';
            messageContainer.style.position = 'absolute';
            messageContainer.style.top = '20px';
            messageContainer.style.left = '50%';
            messageContainer.style.transform = 'translateX(-50%)';
            messageContainer.style.backgroundColor = 'rgba(0, 0, 0, 0.7)';
            messageContainer.style.padding = '10px 20px';
            messageContainer.style.borderRadius = '5px';
            messageContainer.style.color = 'white';
            messageContainer.style.fontWeight = 'bold';
            messageContainer.style.zIndex = '1000';
            messageContainer.style.pointerEvents = 'none'; // Allows clicks to pass through

            return messageContainer;
            }

// Highlight occupied grid cells
            // Highlight occupied grid cells
            function highlightOccupiedGrids() {
            console.log("Highlighting occupied grid cells");
            const currentGridId = 'grid' + currentPlot;
            const currentPlotElement = document.getElementById('plot' + currentPlot);
            // Get the plot's background image URL
            const plotBackgroundImage = currentPlotElement ?
                    window.getComputedStyle(currentPlotElement).backgroundImage : '';
            const cells = document.querySelectorAll('#' + currentGridId + ' .grid-cell');
            cells.forEach(cell => {
            // Check if the cell has content (by background image or data attribute)
            const hasContent = cell.getAttribute('data-has-content') === 'true' ||
                    (cell.style.backgroundImage && cell.style.backgroundImage !== '');
            // Check if cell's background matches the plot's background
            const cellBackground = cell.style.backgroundImage || '';
            const isPlotBackground = cellBackground === plotBackgroundImage;
            if (hasContent && !isPlotBackground) {
            // Make occupied cells appear selectable with more obvious red border
            cell.style.cursor = 'pointer';
            cell.style.border = '3px solid red'; // Changed from 2px dashed to 3px solid
            cell.style.boxShadow = '0 0 5px red'; // Added glow effect
            cell.classList.add('removable');
            // Add click event for removal
            cell.addEventListener('click', confirmRemoveItem);
            } else {
            // Make unoccupied cells or cells with plot background appear "blocked"
            cell.style.opacity = '0.3';
            cell.style.cursor = 'not-allowed';
            cell.classList.add('empty');
            }
            });
            }

// Confirm removal of item
            function confirmRemoveItem(e) {
            if (!isRemovalMode) return;
            const cell = e.currentTarget;
            const xCoord = parseInt(cell.getAttribute('data-x'));
            const yCoord = parseInt(cell.getAttribute('data-y'));
            const plotId = document.getElementById('plot' + currentPlot).getAttribute('data-land-id');
            // Check if this is a withered tree by examining the background image
            const backgroundImage = cell.style.backgroundImage || '';
            const isWitheredTree = backgroundImage.includes('/media/images/forestmisc/Withered Tree.png');
            if (isWitheredTree) {
            // Check for shovels via AJAX
            checkShovelInventory(plotId, xCoord, yCoord);
            } else {
            // Regular item removal flow
            if (confirm("Are you sure you want to remove this item and return it to your inventory?")) {
            console.log("Removing item at", xCoord, yCoord, "from plot", plotId);
            // Submit removal form
            submitRemovalForm(plotId, xCoord, yCoord);
            // Exit removal mode after submission
            cancelRemoval();
            }
            }
            }

            function showRemovalErrorMessage(message) {
            // Create error message container if it doesn't exist
            let errorMsg = document.getElementById('removal-error-message');
            if (!errorMsg) {
            errorMsg = document.createElement('div');
            errorMsg.id = 'removal-error-message';
            errorMsg.style.position = 'fixed';
            errorMsg.style.top = '20%';
            errorMsg.style.left = '50%';
            errorMsg.style.transform = 'translateX(-50%)';
            errorMsg.style.backgroundColor = 'rgba(255, 50, 50, 0.9)';
            errorMsg.style.color = 'white';
            errorMsg.style.padding = '15px 20px';
            errorMsg.style.borderRadius = '5px';
            errorMsg.style.boxShadow = '0 0 10px rgba(0,0,0,0.5)';
            errorMsg.style.zIndex = '2000';
            errorMsg.style.fontWeight = 'bold';
            errorMsg.style.maxWidth = '80%';
            errorMsg.style.textAlign = 'center';
            document.body.appendChild(errorMsg);
            }

            // Set the message
            errorMsg.textContent = message;
            // Show the message
            errorMsg.style.display = 'block';
            // Hide the message after 3 seconds
            setTimeout(() => {
            errorMsg.style.display = 'none';
            }, 3000);
            }

            function checkShovelInventory(landId, xCoord, yCoord) {
            // Show a loading indicator
            showLoadingOverlay();
            // Make AJAX request to check for shovels
            $.ajax({
            url: '/MizukiForest/CheckShovelInventoryServlet',
                    type: 'POST',
                    data: {
                    itemId: 'IT000021' // Shovel item ID
                    },
                    success: function(response) {
                    hideLoadingOverlay();
                    // Parse response to get shovel count
                    const data = response;
                    if (data.success) {
                    const shovelCount = data.shovelCount;
                    if (shovelCount > 0) {
                    // Confirm using a shovel
                    if (confirm("You have currently " + shovelCount + " of shovels. Use 1 to remove this withered tree?")) {
                    // Submit the form to remove withered tree and use up a shovel
                    submitWitheredTreeRemovalForm(landId, xCoord, yCoord);
                    } else {
                    // User canceled
                    console.log("Removal canceled by user");
                    }
                    } else {
                    // No shovels
                    showRemovalErrorMessage("You need a shovel to remove withered trees. Purchase at the shop below.");
                    }
                    } else {
                    // Error in processing
                    showRemovalErrorMessage("Error checking inventory: " + (data.message || "Unknown error"));
                    }
                    },
                    error: function(xhr, status, error) {
                    hideLoadingOverlay();
                    console.error("AJAX Error:", error);
                    showRemovalErrorMessage("Failed to check inventory. Please try again.");
                    },
                    datatype: 'json'
            });
            }

            function submitWitheredTreeRemovalForm(landId, xCoord, yCoord) {
            // Create a form element dynamically
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/MizukiForest/RemoveWitheredTreeServlet';
            // Add landId input
            const landIdInput = document.createElement('input');
            landIdInput.type = 'hidden';
            landIdInput.name = 'landId';
            landIdInput.value = landId;
            form.appendChild(landIdInput);
            // Add xCoord input
            const xCoordInput = document.createElement('input');
            xCoordInput.type = 'hidden';
            xCoordInput.name = 'xCoord';
            xCoordInput.value = xCoord;
            form.appendChild(xCoordInput);
            // Add yCoord input
            const yCoordInput = document.createElement('input');
            yCoordInput.type = 'hidden';
            yCoordInput.name = 'yCoord';
            yCoordInput.value = yCoord;
            form.appendChild(yCoordInput);
            // Add itemId input for shovel
            const itemIdInput = document.createElement('input');
            itemIdInput.type = 'hidden';
            itemIdInput.name = 'itemId';
            itemIdInput.value = 'IT000021'; // Shovel item ID
            form.appendChild(itemIdInput);
            // Append the form to the document body
            document.body.appendChild(form);
            // Submit the form
            form.submit();
            // Remove the form from the DOM (optional cleanup)
            document.body.removeChild(form);
            }

            function showLoadingOverlay() {
            let loadingOverlay = document.getElementById('loading-overlay');
            if (!loadingOverlay) {
            loadingOverlay = document.createElement('div');
            loadingOverlay.id = 'loading-overlay';
            loadingOverlay.style.position = 'fixed';
            loadingOverlay.style.top = '0';
            loadingOverlay.style.left = '0';
            loadingOverlay.style.width = '100%';
            loadingOverlay.style.height = '100%';
            loadingOverlay.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
            loadingOverlay.style.display = 'flex';
            loadingOverlay.style.justifyContent = 'center';
            loadingOverlay.style.alignItems = 'center';
            loadingOverlay.style.zIndex = '2001';
            const spinner = document.createElement('div');
            spinner.className = 'spinner';
            spinner.style.border = '5px solid #f3f3f3';
            spinner.style.borderTop = '5px solid #3498db';
            spinner.style.borderRadius = '50%';
            spinner.style.width = '40px';
            spinner.style.height = '40px';
            spinner.style.animation = 'spin 2s linear infinite';
            // Add keyframes for spinner animation
            const style = document.createElement('style');
            style.textContent = '@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }';
            document.head.appendChild(style);
            loadingOverlay.appendChild(spinner);
            document.body.appendChild(loadingOverlay);
            }
            loadingOverlay.style.display = 'flex';
            }


            function hideLoadingOverlay() {
            const loadingOverlay = document.getElementById('loading-overlay');
            if (loadingOverlay) {
            loadingOverlay.style.display = 'none';
            }
            }


// Submit form to update database
            function submitRemovalForm(landId, xCoord, yCoord) {
            console.log("Submitting removal form with data:", {
            landId: landId,
                    xCoord: xCoord,
                    yCoord: yCoord
            });
            // Create a form element
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/MizukiForest/RemoveItemServlet'; // Path to the removal servlet
            form.style.display = 'none';
            // Add form fields
            const fields = {
            'landId': landId,
                    'xCoord': xCoord,
                    'yCoord': yCoord
            };
            // Create input elements for each field
            for (const [key, value] of Object.entries(fields)) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = value;
            form.appendChild(input);
            }

            // Add form to document and submit
            document.body.appendChild(form);
            form.submit();
            }

// Cancel removal mode
            function cancelRemoval() {
            console.log("Canceling removal mode");
            isRemovalMode = false;
            // Restore auto rotation if it was enabled
            autoRotateEnabled = originalAutoRotateState;
            if (autoRotateEnabled) {
            startPlotRotation();
            }

            // Remove overlay
            if (removalOverlay && removalOverlay.parentNode) {
            removalOverlay.parentNode.removeChild(removalOverlay);
            removalOverlay = null;
            }

            // Remove cancel button
            if (removalCancelButton && removalCancelButton.parentNode) {
            removalCancelButton.parentNode.removeChild(removalCancelButton);
            removalCancelButton = null;
            }

            // Remove cell highlighting
            removeRemovalHighlights();
            }

// Remove highlighting from grid cells
            function removeRemovalHighlights() {
            console.log("Removing grid highlights");
            const cells = document.querySelectorAll('.grid-cell');
            cells.forEach(cell => {
            cell.style.opacity = '';
            cell.style.cursor = '';
            cell.style.border = '';
            cell.style.boxShadow = ''; // Added to remove the glow effect
            cell.classList.remove('removable');
            cell.classList.remove('empty');
            // Remove removal event listener
            cell.removeEventListener('click', confirmRemoveItem);
            });
            }

            const autoRotateToggle = document.getElementById('autoRotateToggle');
            if (autoRotateToggle) {
            autoRotateToggle.checked = false; // Ensure the toggle is unchecked by default
            }
            });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
            // Initialize all modals
            const questionBtn = document.getElementById('questionBtn');
            const questionModal = new bootstrap.Modal(document.getElementById('questionModal'));
            const inventoryModalBtn = document.getElementById('inventoryModalBtn');
            const inventoryModal = new bootstrap.Modal(document.getElementById('inventoryModal'));
            // Question button click handler
            if (questionBtn) {
            questionBtn.addEventListener('click', function () {
            questionModal.show();
            });
            }
            });
        </script>


        <script>
                    document.addEventListener('DOMContentLoaded', function () {

                    // Initialize inventory modal             
                    const inventoryModal = new bootstrap.Modal(document.getElementById('inventoryModal'));
                    const inventoryModalBtn = document.getElementById('inventoryModalBtn');
                    const audioPath = contextPath + '/media/audio/mcplateclick.mp3';
                    const clickSound = new Audio(audioPath);
                    // Update modal HTML structure to include tabs
                    const modalBody = document.querySelector('#inventoryModal .modal-body');
                    if (modalBody) {
            modalBody.innerHTML = `     <ul class="nav nav-tabs" id="inventoryTabs" role="tablist">
    <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="trees-tab" data-bs-toggle="tab" data-bs-target="#trees" type="button" role="tab">Trees</button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="treeboxes-tab" data-bs-toggle="tab" data-bs-target="#treeboxes" type="button" role="tab">Tree Boxes</button>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="items-tab" data-bs-toggle="tab" data-bs-target="#items" type="button" role="tab">Items</button>
                                </li>
                            </ul>
            <div class="tab-content py-3" id="inventoryTabContent">
                    <div class="tab-pane fade show active" id="trees" role="tabpanel">
                    <div class="inventory-items-container d-flex flex-wrap gap-3 justify-content-center">
                            <!-- Tree items will be populated here -->
                            </div>
                            </div>
                            <div class="tab-pane fade" id="treeboxes" role="tabpanel">
                            <div class="inventory-items-container d-flex flex-wrap gap-3 justify-content-center">
                        <!-- Tree boxes will be populated here -->
                        </div>
                            </div>
                            <div class="tab-pane fade" id="items" role="tabpanel">
                                <div class="inventory-items-container d-flex flex-wrap gap-3 justify-content-center">
                                    <!-- Items will be populated here -->
                                    </div>
                                    </div>
                                    </div>
                                    `;
                    }

                    // Inventory modal button click handler
                    if (inventoryModalBtn) {
                    inventoryModalBtn.addEventListener('click', function () {
// Play sound when opening inventory
                    try {
                    clickSound.currentTime = 0;
                    clickSound.play().catch(e => console.log("Audio play failed:", e));
                    } catch (e) {
                    console.log("Audio error:", e);
                    }

// Populate inventory items from session data
                    populateInventoryTabs();
// Show inventory modal
                    inventoryModal.show();
                    });
                    }

                    // Function to populate all inventory tabs
                    function populateInventoryTabs() {
                    const treesContainer = document.querySelector('#trees .inventory-items-container');
                    const treeboxesContainer = document.querySelector('#treeboxes .inventory-items-container');
                    const itemsContainer = document.querySelector('#items .inventory-items-container');
// Clear existing content
                    if (treesContainer)
                            treesContainer.innerHTML = '';
                    if (treeboxesContainer)
                            treeboxesContainer.innerHTML = '';
                    if (itemsContainer)
                            itemsContainer.innerHTML = '';
// Check if arrays exist before using them
                    const hasTrees = typeof treeOrItemList !== 'undefined' && Array.isArray(treeOrItemList);
                    const hasTreeboxes = typeof treeboxList !== 'undefined' && Array.isArray(treeboxList);
// Trees and Items from treeOrItemList
                    if (hasTrees && treesContainer && itemsContainer) {
                    treeOrItemList.forEach(invItem => {
                    if (!invItem)
                            return; // Skip null/undefined items

// Get item properties with fallbacks
                    const name = invItem.name || 'Unknown';
                    const qty = typeof invItem.quantity === 'number' ? invItem.quantity : 1;
                    const desc = invItem.description || '';
                    const image = invItem.image || '';
                    const type = invItem.type || 'item';
                    const element = createInventoryItem(image, qty, name, desc);
                    if (type === 'tree') {
                    treesContainer.appendChild(element);
                    } else if (type === 'item') {
                    itemsContainer.appendChild(element);
                    }
                    });
                    }

// Treeboxes
                    if (hasTreeboxes && treeboxesContainer) {
                    treeboxList.forEach(tbItem => {
                    if (!tbItem)
                            return; // Skip null/undefined items

// Get item properties with fallbacks
                    const name = tbItem.name || 'Unknown Box';
                    const qty = typeof tbItem.quantity === 'number' ? tbItem.quantity : 1;
                    const image = tbItem.image || '';
                    treeboxesContainer.appendChild(createInventoryItem(   image,
                qty, name+ ' Tree Box',
            'Click for Details'
            ));
    });
    }

// Handle empty containers
    if (treesContainer && treesContainer.children.length === 0)
            treesContainer.innerHTML = '<div class="empty-category">No trees in your inventory</div>';
    if (treeboxesContainer && treeboxesContainer.children.length === 0)
            treeboxesContainer.innerHTML = '<div class="empty-category">No tree boxes in your inventory</div>';
    if (itemsContainer && itemsContainer.children.length === 0)
            itemsContainer.innerHTML = '<div class="empty-category">No items in your inventory</div>';
    }

    // Helper function to create an inventory item element
    function createInventoryItem(imagePath, quantity, name, description) {
    const itemContainer = document.createElement('div');
    itemContainer.className = 'inventory-item-container';
// Item image
    const itemImg = document.createElement('div');
    itemImg.className = 'inventory-item-img';
// Handle image path correctly
    if (imagePath === false || imagePath === true || !imagePath) {
// Set a placeholder background
    itemImg.style.backgroundColor = "red";
    itemImg.textContent = '?';
    itemImg.style.display = 'flex';
    itemImg.style.justifyContent = 'center';
    itemImg.style.alignItems = 'center';
    } else {
// Set the background image with proper styling
    itemImg.style.backgroundImage = "url('/MizukiForest" + imagePath + "')";
    itemImg.style.backgroundSize = 'contain';
    itemImg.style.backgroundPosition = 'center';
    itemImg.style.backgroundRepeat = 'no-repeat';
    }

// Quantity badge
    const quantityBadge = document.createElement('div');
    quantityBadge.className = 'item-quantity';
    quantityBadge.textContent = quantity;
// Tooltip with name and description
const tooltip = document.createElement('div');
tooltip.className = 'item-tooltip';
tooltip.innerHTML = "<strong>" + name + "</strong><br>" + (description || '');
// Assemble the item
itemContainer.appendChild(itemImg);
itemContainer.appendChild(quantityBadge);
itemContainer.appendChild(tooltip);
return itemContainer;
}
            


// Add click event listeners to tabs to play sound on tab change
const tabButtons = document.querySelectorAll('#inventoryTabs .nav-link');
tabButtons.forEach(tab => {
tab.addEventListener('click', function() {
try {
clickSound.currentTime = 0;
clickSound.play().catch(e => console.log("Audio play failed:", e));
} catch (e) {
console.log("Audio error:", e);
}
});
});



});
        </script>

        <script>
                            document.addEventListener('DOMContentLoaded', function() {
                            // Create tree box detail modal
                            const treeBoxDetailModal = document.createElement('div');
                            treeBoxDetailModal.className = 'modal fade';
                            treeBoxDetailModal.id = 'treeBoxDetailModal';
                            treeBoxDetailModal.tabIndex = '-1';
                            treeBoxDetailModal.setAttribute('aria-hidden', 'true');
                            treeBoxDetailModal.innerHTML =
                                    '<div class="modal-dialog modal-dialog-centered">' +
                                    '<div class="modal-content">' +
                                    '<div class="modal-header">' +
                                    '<h5 class="modal-title">Tree Box Details</h5>' +
                                    '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                                    '</div>' +
                                    '<div class="modal-body">' +
                                    '<div class="text-center mb-3">' +
                                    '<div id="treeBoxDetailImage" style="width: 128px; height: 128px; margin: 0 auto; background-size: contain; background-position: center; background-repeat: no-repeat;"></div>' +
                                    '<h4 id="treeBoxDetailName" class="mt-2"></h4>' +
                                    '</div>' +
                                    '<div id="treeBoxDropRates" class="mb-3">' +
                                    '<h5>Drop Rates:</h5>' +
                                    '<div id="dropRateList" class="ps-3"></div>' +
                                    '</div>' +
                                    '<button id="openTreeBoxBtn" class="btn btn-primary mb-3 w-100">Open Tree Box</button>' +
                                    '<div>' +
                                    '<h5>Trees by Rarity:</h5>' +
                                    '<div class="accordion" id="treesAccordion"></div>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>';
                            document.body.appendChild(treeBoxDetailModal);
                            // Create custom styles
                            const treeBoxStyle = document.createElement('style');
                            treeBoxStyle.textContent =
                                    '.tree-box-tooltip-enhanced {' +
                                    'min-width: 150px !important;' +
                                    '}' +
                                    '.tree-item-container {' +
                                    'display: inline-block;' +
                                    'margin: 5px;' +
                                    'text-align: center;' +
                                    'position: relative;' +
                                    'cursor: pointer;' +
                                    '}' +
                                    '.tree-item-img {' +
                                    'width: 48px;' +
                                    'height: 48px;' +
                                    'border: 1px solid #ddd;' +
                                    'border-radius: 4px;' +
                                    'margin: 0 auto;' +
                                    'background-size: contain;' +
                                    'background-position: center;' +
                                    'background-repeat: no-repeat;' +
                                    'background-color: #f9f9f9;' +
                                    '}' +
                                    '.tree-item-name {' +
                                    'font-size: 10px;' +
                                    'margin-top: 3px;' +
                                    'max-width: 60px;' +
                                    'overflow: hidden;' +
                                    'text-overflow: ellipsis;' +
                                    'white-space: nowrap;' +
                                    '}' +
                                    '.tree-tooltip {' +
                                    'display: none;' +
                                    'position: absolute;' +
                                    'bottom: 100%;' +
                                    'left: 50%;' +
                                    'transform: translateX(-50%);' +
                                    'background-color: rgba(0,0,0,0.8);' +
                                    'color: white;' +
                                    'padding: 5px 10px;' +
                                    'border-radius: 4px;' +
                                    'font-size: 12px;' +
                                    'white-space: nowrap;' +
                                    'z-index: 1000;' +
                                    'margin-bottom: 5px;' +
                                    'pointer-events: none;' +
                                    '}' +
                                    '.tree-item-container:hover .tree-tooltip {' +
                                    'display: block;' +
                                    '}' +
                                    '.rarity-common { color: #aaaaaa; }' +
                                    '.rarity-rare { color: #4287f5; }' +
                                    '.rarity-epic { color: #9932CC; }' +
                                    '.rarity-legendary { color: #FFA500; }' +
                                    '.drop-rate-item {' +
                                    'display: flex;' +
                                    'justify-content: space-between;' +
                                    'margin-bottom: 5px;' +
                                    'padding: 2px 5px;' +
                                    'border-radius: 3px;' +
                                    '}' +
                                    '.drop-rate-common { background-color: rgba(170, 170, 170, 0.1); }' +
                                    '.drop-rate-rare { background-color: rgba(66, 135, 245, 0.1); }' +
                                    '.drop-rate-epic { background-color: rgba(153, 50, 204, 0.1); }' +
                                    '.drop-rate-legendary { background-color: rgba(255, 165, 0, 0.1); }';
                            document.head.appendChild(treeBoxStyle);
                            // Drop rate cache
                            const dropRateCache = {};
                            // Wait for inventory modal to be fully initialized
                            let setupAttempts = 0;
                            const maxAttempts = 10;
                            function attemptSetup() {
                            setupAttempts++;
                            // Look for inventory panes as evidence the inventory is initialized
                            const treeboxesPane = document.querySelector('#treeboxes');
                            const treeboxesContainer = treeboxesPane ? treeboxesPane.querySelector('.inventory-items-container') : null;
                            if (treeboxesContainer) {
                            setupTreeBoxEnhancements();
                            } else if (setupAttempts < maxAttempts) {
                            setTimeout(attemptSetup, 500);
                            }
                            }

                            // Start the setup process
                            setTimeout(attemptSetup, 500);
                            function setupTreeBoxEnhancements() {
                            // Add click listeners to all tree box items
                            function addTreeBoxListeners() {
                            const treeboxesContainer = document.querySelector('#treeboxes .inventory-items-container');
                            if (!treeboxesContainer) return;
                            const treeBoxItems = treeboxesContainer.querySelectorAll('.inventory-item-container');
                            treeBoxItems.forEach(item => {
                            // Check if this is a tree box by checking the tooltip
                            const tooltip = item.querySelector('.item-tooltip');
                            if (tooltip && tooltip.textContent.includes('Tree Box')) {
                            // Enhance tooltip
                            tooltip.classList.add('tree-box-tooltip-enhanced');
                            // Remove existing click listener if any
                            const newItem = item.cloneNode(true);
                            item.parentNode.replaceChild(newItem, item);
                            // Add new click listener
                            newItem.addEventListener('click', function(e) {
                            e.preventDefault();
                            e.stopPropagation();
                            const itemImg = newItem.querySelector('.inventory-item-img');
                            if (!itemImg) return;
                            const backgroundImageStyle = itemImg.style.backgroundImage;
                            const imageUrlMatch = backgroundImageStyle.match(/url\(['"]?([^'"]+)['"]?\)/);
                            const fullImageUrl = imageUrlMatch ? imageUrlMatch[1] : '';
                            // Extract path and tree box ID - matches /media/images/treebox/TB000005.png
                            const pathMatch = fullImageUrl.match(/\/MizukiForest(\/media\/images\/treebox\/[^"']+)/);
                            const imagePath = pathMatch ? pathMatch[1] : '';
                            // Extract just the TB000005 part from the filename
                            const treeBoxIdMatch = imagePath.match(/\/treebox\/([^\/\.]+)/);
                            const treeBoxId = treeBoxIdMatch ? treeBoxIdMatch[1] : '';
                            // Get box name
                            const nameElement = tooltip.querySelector('strong');
                            const treeBoxName = nameElement ? nameElement.textContent : 'Tree Box';
                            if (treeBoxId) {
                            showTreeBoxDetails(treeBoxId, treeBoxName, imagePath);
                            }
                            });
                            // Add hover listener for tooltip enhancement
                            newItem.addEventListener('mouseenter', function() {
                            const itemImg = newItem.querySelector('.inventory-item-img');
                            if (!itemImg) return;
                            const backgroundImageStyle = itemImg.style.backgroundImage;
                            const imageUrlMatch = backgroundImageStyle.match(/url\(['"]?([^'"]+)['"]?\)/);
                            const fullImageUrl = imageUrlMatch ? imageUrlMatch[1] : '';
                            // Extract path and tree box ID - matches /media/images/treebox/TB000005.png
                            const pathMatch = fullImageUrl.match(/\/MizukiForest(\/media\/images\/treebox\/[^"']+)/);
                            const imagePath = pathMatch ? pathMatch[1] : '';
                            // Extract just the TB000005 part from the filename
                            const treeBoxIdMatch = imagePath.match(/\/treebox\/([^\/\.]+)/);
                            const treeBoxId = treeBoxIdMatch ? treeBoxIdMatch[1] : '';
                            if (treeBoxId) {
                            // Check cache
                            if (dropRateCache[treeBoxId]) {
                            updateTreeBoxTooltip(tooltip, dropRateCache[treeBoxId]);
                            } else {
                            // Fetch drop rate data
                            fetchTreeBoxDropRates(treeBoxId, function(data) {
                            dropRateCache[treeBoxId] = data;
                            updateTreeBoxTooltip(tooltip, data);
                            });
                            }
                            }
                            });
                            }
                            });
                            }

                            // Listen for inventory modal opening
                            const inventoryModalBtn = document.getElementById('inventoryModalBtn');
                            if (inventoryModalBtn) {
                            inventoryModalBtn.addEventListener('click', function() {
                            // Wait a moment for the inventory to populate
                            setTimeout(addTreeBoxListeners, 300);
                            });
                            }

                            // Listen for tab changes
                            const treeboxTab = document.getElementById('treeboxes-tab');
                            if (treeboxTab) {
                            treeboxTab.addEventListener('click', function() {
                            setTimeout(addTreeBoxListeners, 300);
                            });
                            }

                            // Initial setup if inventory is already open
                            setTimeout(addTreeBoxListeners, 300);
                            }

                            // Function to update tree box tooltip with drop rate data
                            function updateTreeBoxTooltip(tooltipElement, dropRateData) {
                            if (!tooltipElement) return;
                            // Clear existing drop rate info
                            const existingDropRateInfo = tooltipElement.querySelector('.drop-rate-info');
                            if (existingDropRateInfo) {
                            existingDropRateInfo.remove();
                            }

                            // Create drop rate section
                            const dropRateInfo = document.createElement('div');
                            dropRateInfo.className = 'drop-rate-info';
                            dropRateInfo.innerHTML = '<strong>Drop Rate:</strong><br>';
                            // Add each rarity drop rate
                            if (dropRateData && dropRateData.length > 0) {
                            dropRateData.forEach(rate => {
                            const rarityClass = 'rarity-' + rate.rarityName.toLowerCase();
                            dropRateInfo.innerHTML += '<span class="' + rarityClass + '">' + rate.rarityName + rate.percentage + '%</span><br>';
                            });
                            } else {
                            dropRateInfo.innerHTML += '<span>No drop rate data available</span><br>';
                            }

                            // Add click instructions
                            dropRateInfo.innerHTML += '<br><em>(Click for details)</em>';
                            // Append to tooltip
                            tooltipElement.appendChild(dropRateInfo);
                            }

                            // Function to show tree box details in modal
                            function showTreeBoxDetails(treeBoxId, treeBoxName, imagePath) {
                            // Get modal elements
                            const detailModalElement = document.getElementById('treeBoxDetailModal');
                            if (!detailModalElement) return;
                            const detailModal = new bootstrap.Modal(detailModalElement);
                            const detailImage = document.getElementById('treeBoxDetailImage');
                            const detailName = document.getElementById('treeBoxDetailName');
                            const dropRateList = document.getElementById('dropRateList');
                            const treesAccordion = document.getElementById('treesAccordion');
                            const openBtn = document.getElementById('openTreeBoxBtn');
                            // Set tree box image and name
                            if (detailImage) detailImage.style.backgroundImage = 'url(\'/MizukiForest' + imagePath + '\')';
                            if (detailName) detailName.textContent = treeBoxName;
                            // Clear previous content
                            if (dropRateList) dropRateList.innerHTML = '';
                            if (treesAccordion) treesAccordion.innerHTML = '';
                            // Get drop rate data (from cache or fetch it)
                            if (dropRateCache[treeBoxId]) {
                            populateTreeBoxDetails(dropRateCache[treeBoxId]);
                            } else {
                            fetchTreeBoxDropRates(treeBoxId, function(data) {
                            dropRateCache[treeBoxId] = data;
                            populateTreeBoxDetails(data);
                            });
                            }

                            // Open button event handler
                            if (openBtn) {
                            openBtn.onclick = function() {
                            alert('Tree box opening feature is coming soon!');
                            };
                            }

                            // Show the modal
                            try {
                            detailModal.show();
                            } catch (error) {
                            alert('Failed to open tree box details.');
                            }
                            }

                            function populateTreeBoxDetails(dropRateData) {
                            // Populate drop rates
                            if (dropRateList) {
                            dropRateList.innerHTML = '';
                            if (dropRateData && dropRateData.length > 0) {
                            dropRateData.forEach(rate => {
                            const dropRateItem = document.createElement('div');
                            dropRateItem.className = 'drop-rate-item drop-rate-' + rate.rarityName.toLowerCase();
                            const raritySpan = document.createElement('span');
                            raritySpan.className = 'rarity-' + rate.rarityName.toLowerCase();
                            raritySpan.textContent = rate.rarityName;
                            const percentSpan = document.createElement('span');
                            percentSpan.textContent = rate.percentage + '%';
                            dropRateItem.appendChild(raritySpan);
                            dropRateItem.appendChild(percentSpan);
                            dropRateList.appendChild(dropRateItem);
                            });
                            } else {
                            dropRateList.innerHTML = '<div>No drop rate data available</div>';
                            }
                            }

                            // Group trees by rarity
                            if (treesAccordion && typeof allTrees !== 'undefined') {
                            const treesByRarity = {};
                            allTrees.forEach(tree => {
                            if (!tree) return;
                            const rarity = tree.rarity || 'Unknown';
                            if (!treesByRarity[rarity]) {
                            treesByRarity[rarity] = [];
                            }
                            treesByRarity[rarity].push(tree);
                            });
                            // Create accordion items for each rarity
                            treesAccordion.innerHTML = '';
                            // Desired order of rarities
                            const rarityOrder = ['Legendary', 'Epic', 'Rare', 'Common', 'Unknown'];
                            rarityOrder.forEach(rarity => {
                            if (treesByRarity[rarity] && treesByRarity[rarity].length > 0) {
                            const accordionItem = document.createElement('div');
                            accordionItem.className = 'accordion-item';
                            const headerId = 'heading' + rarity;
                            const collapseId = 'collapse' + rarity;
                            accordionItem.innerHTML =
                                    '<h2 class="accordion-header" id="' + headerId + '">' +
                                    '<button class="accordion-button collapsed rarity-' + rarity.toLowerCase() + '" type="button" data-bs-toggle="collapse" data-bs-target="#' + collapseId + '" aria-expanded="false" aria-controls="' + collapseId + '">' +
                                    rarity + ' Trees (' + treesByRarity[rarity].length + ')' +
                                    '</button>' +
                                    '</h2>' +
                                    '<div id="' + collapseId + '" class="accordion-collapse collapse" aria-labelledby="' + headerId + '" data-bs-parent="#treesAccordion">' +
                                    '<div class="accordion-body tree-grid d-flex flex-wrap justify-content-center">' +
                                    '<!-- Trees will be added here -->' +
                                    '</div>' +
                                    '</div>';
                            treesAccordion.appendChild(accordionItem);
                            // Add trees to this rarity section
                            const treeGrid = accordionItem.querySelector('.tree-grid');
                            if (treeGrid) {
                            treesByRarity[rarity].forEach(function(tree) {
                            const treeItem = document.createElement('div');
                            treeItem.className = 'tree-item-container';
                            const treeImg = document.createElement('div');
                            treeImg.className = 'tree-item-img';
                            treeImg.style.backgroundImage = 'url(\'/MizukiForest/media/images/tree/' + tree.name + '.png\')';
                            // Create custom tooltip
                            const tooltip = document.createElement('div');
                            tooltip.className = 'tree-tooltip';
                            tooltip.textContent = tree.name;
                            treeItem.appendChild(treeImg);
                            treeItem.appendChild(tooltip);
                            treeGrid.appendChild(treeItem);
                            });
                            }
                            }
                            });
                            } else if (treesAccordion) {
                            treesAccordion.innerHTML = '<div class="alert alert-warning">Tree data unavailable</div>';
                            }
                            }

                            // Function to fetch tree box drop rates using AJAX
                            function fetchTreeBoxDropRates(treeBoxId, callback) {
                            const url = contextPath + '/treeBoxDropRates?treeboxId=' + treeBoxId;
                            // Create AJAX request
                            const xhr = new XMLHttpRequest();
                            xhr.open('GET', url, true);
                            xhr.onload = function() {
                            if (xhr.status === 200) {
                            try {
                            const data = JSON.parse(xhr.responseText);
                            callback(data);
                            } catch (e) {
                            callback([]); // Return empty array on error
                            }
                            } else {
                            callback([]); // Return empty array on error
                            }
                            };
                            xhr.onerror = function() {
                            callback([]); // Return empty array on error
                            };
                            xhr.send();
                            }
                            });
        </script>


        <script src= "${pageContext.request.contextPath}/js/treeBoxOpener.js"></script>
        <script src= "${pageContext.request.contextPath}/js/treeBoxOpener2.js"></script>

        <script src= "${pageContext.request.contextPath}/js/shop.js"></script>


        <script src="${pageContext.request.contextPath}/js/mizukibase.js"></script>
    </section>
</html>


