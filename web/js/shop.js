// Shop functionality
$(document).ready(function () {
    // Initialize shop modal
    createShopModal();

    // Add event listener to shop button
    $("#ShopBtn").click(function () {
        loadShopItems();
        $("#shopModal").modal("show");
    });
});

function createShopModal() {
    const shopModal =
            '<div class="modal fade" id="shopModal" tabindex="-1" role="dialog" aria-labelledby="shopModalLabel" aria-hidden="true">' +
            '<div class="modal-dialog modal-lg" role="document">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<div class="user-coins-container">' +
            '<i class="fa-solid fa-coins text-warning"></i>' +
            '<span id="userCoins">0</span>' +
            '</div>' +
            '<h5 class="modal-title" id="shopModalLabel">Shop</h5>' +
            '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span>' +
            '</button>' +
            '</div>' +
            '<div class="modal-body">' +
            '<div id="purchaseAlert" class="alert d-none mb-3" role="alert"></div>' +
            '<ul class="nav nav-tabs" id="shopTabs" role="tablist">' +
            '<li class="nav-item">' +
            '<a class="nav-link active" id="shop-items-tab" data-toggle="tab" href="#shop-items" role="tab" aria-controls="shop-items" aria-selected="true">Items</a>' +
            '</li>' +
            '<li class="nav-item">' +
            '<a class="nav-link" id="shop-treeboxes-tab" data-toggle="tab" href="#shop-treeboxes" role="tab" aria-controls="shop-treeboxes" aria-selected="false">Tree Boxes</a>' +
            '</li>' +
            '<li class="nav-item">' +
            '<a class="nav-link" id="shop-biomes-tab" data-toggle="tab" href="#shop-biomes" role="tab" aria-controls="shop-biomes" aria-selected="false">Biomes</a>' +
            '</li>' +
            '</ul>' +
            '<div class="tab-content mt-3" id="shopTabContent">' +
            '<div class="tab-pane fade show active" id="shop-items" role="tabpanel" aria-labelledby="shop-items-tab">' +
            '<div class="row" id="itemsContainer">' +
            '<div class="loading-spinner">Loading items...</div>' +
            '</div>' +
            '</div>' +
            '<div class="tab-pane fade" id="shop-treeboxes" role="tabpanel" aria-labelledby="shop-treeboxes-tab">' +
            '<div class="row" id="treeboxesContainer">' +
            '<div class="loading-spinner">Loading tree boxes...</div>' +
            '</div>' +
            '</div>' +
            '<div class="tab-pane fade" id="shop-biomes" role="tabpanel" aria-labelledby="shop-biomes-tab">' +
            '<div class="row" id="biomesContainer">' +
            '<div class="loading-spinner">Loading biomes...</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<div class="modal-footer">' +
            '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<!-- Item Info Modal -->' +
            '<div class="modal fade" id="itemInfoModal" tabindex="-1" role="dialog" aria-labelledby="itemInfoModalLabel" aria-hidden="true">' +
            '<div class="modal-dialog modal-dialog-centered" role="document">' +
            '<div class="modal-content item-info-modal-content">' +
            '<button type="button" class="close-fancy" data-dismiss="modal" aria-label="Close">' +
            '<i class="fa-solid fa-times"></i>' +
            '</button>' +
            '<div class="item-info-backdrop">' +
            '<div class="item-info-glow"></div>' +
            '</div>' +
            '<div class="item-info-body">' +
            '<div class="item-info-image-wrapper">' +
            '<div class="item-zoom-container">' +
            '<img id="itemInfoImage" src="" alt="Item Image" class="img-fluid item-zoomable">' +
            '</div>' +
            '</div>' +
            '<div class="item-info-details">' +
            '<h3 id="itemInfoName" class="item-info-title"></h3>' +
            '<div class="item-info-divider"></div>' +
            '<div id="itemInfoDescriptionContainer" class="item-info-description">' +
            '<h5>Description</h5>' +
            '<p id="itemInfoDescription"></p>' +
            '</div>' +
            '<button type="button" class="btn btn-info-close" data-dismiss="modal">' +
            'Close Preview' +
            '</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';

    // Remove existing modal if it exists
    $("#shopModal, #itemInfoModal").remove();

    // Add event listener for when modal is fully hidden (catches all close events)
    $('#shopModal').on('hidden.bs.modal', function () {
        window.location.href = '/MizukiForest/GetUserForestData?time=' + new Date().getTime();
    });

    // Append modal to body
    $('body').append(shopModal);

    // Add updated CSS for shop modal with sticky header and visible coins
    const shopStyles =
            '.modal-header {' +
            'position: sticky;' +
            'top: 0;' +
            'z-index: 1030;' +
            'background-color: white;' +
            'border-bottom: 1px solid #dee2e6;' +
            'padding-top: 15px;' +
            'padding-bottom: 15px;' +
            'display: flex;' +
            'align-items: center;' +
            'box-shadow: 0 2px 10px rgba(0,0,0,0.1);' +
            '}' +
            '.user-coins-container {' +
            'display: flex;' +
            'align-items: center;' +
            'margin-right: 20px;' +
            'font-weight: bold;' +
            'font-size: 1.1rem;' +
            'color: #000;' +
            'padding: 5px 12px;' +
            'background-color: #f8f9fa;' +
            'border-radius: 20px;' +
            'border: 1px solid #dee2e6;' +
            '}' +
            '.user-coins-container i {' +
            'margin-right: 8px;' +
            'color: #ffc107;' +
            'font-size: 1.2rem;' +
            '}' +
            '.modal-title {' +
            'margin-right: auto;' +
            'margin-left: 0;' +
            'color: #333;' +
            '}' +
            '/* Make tabs sticky too, positioned just below the header */' +
            '#shopTabs {' +
            'position: sticky;' +
            'top: 60px;' +
            'z-index: 1020;' +
            'background-color: white;' +
            'padding-top: 10px;' +
            'border-bottom: 1px solid #dee2e6;' +
            '}' +
            '/* Add some top padding to tab content to prevent overlap */' +
            '.tab-content {' +
            'padding-top: 10px;' +
            '}' +
            '.shop-item {' +
            'margin-bottom: 20px;' +
            'border: 1px solid #ddd;' +
            'border-radius: 8px;' +
            'padding: 12px;' + // Increased padding
            'position: relative;' +
            'transition: transform 0.2s;' +
            'height: 280px;' + // Increased height from 255px to 280px
            'display: flex;' +
            'flex-direction: column;' +
            'background-color: white;' +
            '}' +
            '.shop-item:hover {' +
            'transform: translateY(-5px);' +
            'box-shadow: 0 5px 15px rgba(0,0,0,0.1);' +
            '}' +
            '.shop-item-image {' +
            'position: relative;' +
            'width: 100%;' +
            'height: 140px;' + // Increased height from 120px to 140px
            'overflow: hidden;' +
            'border-radius: 4px;' +
            'background-color: #f5f5f5;' +
            'display: flex;' +
            'align-items: center;' +
            'justify-content: center;' +
            '}' +
            '.shop-item-image img {' +
            'max-width: 100%;' +
            'max-height: 100%;' +
            'object-fit: contain;' +
            '}' +
            '.info-button {' +
            'position: absolute;' +
            'top: 5px;' +
            'right: 5px;' +
            'background-color: rgba(255,255,255,0.8);' +
            'border-radius: 50%;' +
            'width: 28px;' +
            'height: 28px;' +
            'display: flex;' +
            'align-items: center;' +
            'justify-content: center;' +
            'cursor: pointer;' +
            'z-index: 2;' +
            '}' +
            '.item-name {' +
            'font-weight: bold;' +
            'margin-top: 8px;' +
            'margin-bottom: 5px;' +
            'height: 38px;' +
            'overflow: hidden;' +
            'display: -webkit-box;' +
            '-webkit-line-clamp: 2;' +
            '-webkit-box-orient: vertical;' +
            'color: #333;' +
            'font-size: 0.95rem;' +
            '}' +
            '.item-cost {' +
            'margin-bottom: 8px;' +
            'color: #555;' +
            'font-size: 0.9rem;' +
            '}' +
            '.buy-button {' +
            'width: 100%;' +
            'border: none;' +
            'padding: 6px;' +
            'border-radius: 4px;' +
            'font-weight: bold;' +
            'cursor: pointer;' +
            'display: flex;' +
            'align-items: center;' +
            'justify-content: center;' +
            'margin-top: auto;' +
            'font-size: 0.9rem;' +
            '}' +
            '.buy-button.available {' +
            'background-color: #28a745;' +
            'color: white;' +
            '}' +
            '.buy-button.affordable {' +
            'background-color: #ffc107;' +
            'color: #212529;' +
            '}' +
            '.buy-button.disabled {' +
            'background-color: #6c757d;' +
            'color: white;' +
            'cursor: not-allowed;' +
            '}' +
            '.coin-icon {' +
            'margin-right: 5px;' +
            '}' +
            '.not-available-overlay {' +
            'position: absolute;' +
            'top: 0;' +
            'left: 0;' +
            'width: 100%;' +
            'height: 100%;' +
            'background-color: rgba(0,0,0,0.6);' +
            'display: flex;' +
            'align-items: center;' +
            'justify-content: center;' +
            'z-index: 1;' +
            'border-radius: 4px;' +
            '}' +
            '.not-available-text {' +
            'background-color: rgba(220, 53, 69, 0.8);' +
            'color: white;' +
            'padding: 5px 10px;' +
            'border-radius: 4px;' +
            'font-weight: bold;' +
            'transform: rotate(-15deg);' +
            'font-size: 1.1rem;' + // Made text slightly larger
            '}' +
            '.loading-spinner {' +
            'width: 100%;' +
            'text-align: center;' +
            'padding: 20px;' +
            '}' +
            '/* Info modal styles */' +
            '.item-info-modal-content {' +
            'border: none;' +
            'border-radius: 16px;' +
            'overflow: hidden;' +
            'box-shadow: 0 10px 30px rgba(0, 0, 0, 0.25);' +
            'background: transparent;' +
            '}' +
            '.item-info-backdrop {' +
            'position: absolute;' +
            'top: 0;' +
            'left: 0;' +
            'width: 100%;' +
            'height: 100%;' +
            'background: linear-gradient(135deg, #1a2a6c, #b21f1f, #fdbb2d);' +
            'opacity: 0.85;' +
            'z-index: -1;' +
            '}' +
            '.item-info-glow {' +
            'position: absolute;' +
            'top: 50%;' +
            'left: 50%;' +
            'transform: translate(-50%, -50%);' +
            'width: 150%;' +
            'height: 150%;' +
            'background: radial-gradient(circle, rgba(255,255,255,0.2) 0%, rgba(255,255,255,0) 70%);' +
            'z-index: -1;' +
            'animation: glow-pulse 4s infinite ease-in-out;' +
            '}' +
            '@keyframes glow-pulse {' +
            '0% { opacity: 0.3; }' +
            '50% { opacity: 0.7; }' +
            '100% { opacity: 0.3; }' +
            '}' +
            '.item-info-body {' +
            'display: flex;' +
            'flex-direction: column;' +
            'padding: 20px;' +
            '}' +
            '.item-info-image-wrapper {' +
            'margin-bottom: 20px;' +
            'filter: drop-shadow(0 5px 15px rgba(0,0,0,0.2));' +
            '}' +
            '.item-zoom-container {' +
            'position: relative;' +
            'overflow: hidden;' +
            'width: 100%;' +
            'height: 250px;' +
            'display: flex;' +
            'align-items: center;' +
            'justify-content: center;' +
            'background-color: rgba(255, 255, 255, 0.1);' +
            'backdrop-filter: blur(5px);' +
            'border-radius: 12px;' +
            'border: 2px solid rgba(255, 255, 255, 0.3);' +
            '}' +
            '.item-zoomable {' +
            'max-width: 85%;' +
            'max-height: 85%;' +
            'transition: transform 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);' +
            'cursor: zoom-in;' +
            '}' +
            '.item-zoomable.zoomed {' +
            'transform: scale(1.75);' +
            'cursor: zoom-out;' +
            '}' +
            '.item-info-title {' +
            'color: white;' +
            'font-weight: 700;' +
            'text-align: center;' +
            'text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);' +
            'font-size: 1.8rem;' +
            'margin-top: 0;' +
            'margin-bottom: 15px;' +
            '}' +
            '.item-info-divider {' +
            'height: 3px;' +
            'background: linear-gradient(to right, transparent, rgba(255, 255, 255, 0.8), transparent);' +
            'margin: 15px 0;' +
            'border-radius: 3px;' +
            '}' +
            '.item-info-description {' +
            'background-color: rgba(255, 255, 255, 0.15);' +
            'border-radius: 12px;' +
            'padding: 20px;' +
            'margin-bottom: 20px;' +
            'backdrop-filter: blur(5px);' +
            '}' +
            '.item-info-description h5 {' +
            'color: white;' +
            'font-weight: 600;' +
            'margin-top: 0;' +
            'margin-bottom: 10px;' +
            'font-size: 1.2rem;' +
            'letter-spacing: 0.5px;' +
            'text-transform: uppercase;' +
            'opacity: 0.9;' +
            '}' +
            '.item-info-description p {' +
            'color: white;' +
            'opacity: 0.85;' +
            'font-size: 1.05rem;' +
            'line-height: 1.6;' +
            'margin-bottom: 0;' +
            '}' +
            '.close-fancy {' +
            'position: absolute;' +
            'top: 15px;' +
            'right: 15px;' +
            'background-color: rgba(255, 255, 255, 0.2);' +
            'border: none;' +
            'border-radius: 50%;' +
            'width: 32px;' +
            'height: 32px;' +
            'display: flex;' +
            'align-items: center;' +
            'justify-content: center;' +
            'color: white;' +
            'cursor: pointer;' +
            'z-index: 10;' +
            'transition: all 0.3s ease;' +
            'padding: 0;' +
            'opacity: 0.7;' +
            '}' +
            '.close-fancy:hover {' +
            'background-color: rgba(255, 255, 255, 0.4);' +
            'opacity: 1;' +
            'transform: rotate(90deg);' +
            '}' +
            '.btn-info-close {' +
            'background: rgba(255, 255, 255, 0.2);' +
            'border: 1px solid rgba(255, 255, 255, 0.3);' +
            'color: white;' +
            'border-radius: 30px;' +
            'padding: 10px 20px;' +
            'font-weight: 600;' +
            'width: 100%;' +
            'transition: all 0.3s ease;' +
            'letter-spacing: 0.5px;' +
            'text-transform: uppercase;' +
            'box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);' +
            '}' +
            '.btn-info-close:hover {' +
            'background: rgba(255, 255, 255, 0.3);' +
            'transform: translateY(-2px);' +
            'box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);' +
            'color: white;' +
            '}' +
            '.alert {' +
            'border-radius: 8px;' +
            'padding: 12px 20px;' +
            'margin-bottom: 20px;' +
            'animation: fadeOut 5s forwards;' +
            'opacity: 1;' +
            '}' +
            '.alert-success {' +
            'background-color: rgba(40, 167, 69, 0.15);' +
            'border: 1px solid rgba(40, 167, 69, 0.5);' +
            'color: #155724;' +
            '}' +
            '.alert-danger {' +
            'background-color: rgba(220, 53, 69, 0.15);' +
            'border: 1px solid rgba(220, 53, 69, 0.5);' +
            'color: #721c24;' +
            '}' +
            '@keyframes fadeOut {' +
            '0% { opacity: 1; }' +
            '80% { opacity: 1; }' +
            '100% { opacity: 0; }' +
            '}';

    // Remove existing style if it exists
    $("#shopStyles").remove();

    // Add ID to style element for potential future updates
    $('head').append('<style id="shopStyles">' + shopStyles + '</style>');

    // Initialize Bootstrap components
    $('#shopModal').modal({
        show: false,
        keyboard: true,
        backdrop: true
    });

    $('#itemInfoModal').modal({
        show: false,
        keyboard: true,
        backdrop: true
    });

    // Initialize Bootstrap tabs properly
    $('#shopTabs a').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    // Set up close button functionality
    $('#shopModal .close, #shopModal .btn-secondary').click(function () {
        $('#shopModal').modal('hide');
        window.location.href = '/MizukiForest/GetUserForestData?time=' + new Date().getTime();
    });

    // Set up close button functionality for info modal
    $('#itemInfoModal .close-fancy, #itemInfoModal .btn-info-close').click(function () {
        $('#itemInfoModal').modal('hide');
    });

    // Set up zoom functionality for item images
    $(document).on('click', '.item-zoomable', function () {
        $(this).toggleClass('zoomed');
    });
}

// Load shop items via AJAX
function loadShopItems() {
    $.ajax({
        url: contextPath + '/shop',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            // Clear containers
            $('#itemsContainer').empty();
            $('#treeboxesContainer').empty();
            $('#biomesContainer').empty();

            // Get user coins from the server response
            const userCoins = data.userCoins || 0;

            // Update the coins display
            $('#userCoins').text(userCoins);

            //Process the items
            if (data.items && Array.isArray(data.items)) {
                displayItems(data.items, userCoins, 'itemsContainer', 'item');
            }

            // Process the tree boxes
            if (data.treeBoxes && Array.isArray(data.treeBoxes)) {
                displayItems(data.treeBoxes, userCoins, 'treeboxesContainer', 'treebox');
            }

            // Process the biomes
            if (data.biomes && Array.isArray(data.biomes)) {
                displayItems(data.biomes, userCoins, 'biomesContainer', 'biome');
            }
        },
        error: function (error) {
            console.error('Error loading shop items:', error);
            $('#itemsContainer, #treeboxesContainer, #biomesContainer').html(
                    '<div class="alert alert-danger">Failed to load shop items. Please try again later.</div>'
                    );
        }
    });
}

// Display items in their respective containers
function displayItems(items, userCoins, containerId, itemType) {
    const container = $('#' + containerId);

    if (items.length === 0) {
        container.html('<div class="col-12"><p class="text-center">No items available.</p></div>');
        return;
    }

    items.forEach(function (item) {
        // Skip deleted items
        if (item.isdeleted) {
            return;
        }

        // Determine item properties based on type
        let id, name, description, image, cost, isArchived;

        switch (itemType) {
            case 'item':
                id = item.id || item.itemid;
                name = item.name || item.itemname;
                description = item.description || item.itemdescription;
                image = item.image || item.itemimage || 'assets/img/default-item.png';
                cost = item.cost || item.itemcost;
                isArchived = item.isArchived; // Fix: Use correct property name
                break;
            case 'treebox':
                id = item.id || item.treeboxid;
                name = item.name || item.treeboxname;
                description = "A treebox that contains mysterious items";
                image = item.image || item.treeboximage || 'assets/img/default-treebox.png';
                cost = item.cost || item.treeboxcost;
                isArchived = item.isArchived; // Fix: Use correct property name
                break;
            case 'biome':
                id = item.id || item.biomeid;
                name = item.name || item.biomename;
                description = item.description || item.biomedescription;
                image = item.image || item.biomeimage || 'assets/img/default-biome.png';
                cost = item.cost || item.biomecost;
                isArchived = item.isArchived; // Fix: Use correct property name
                break;
        }

        // Check if the user can afford this item
        const isAffordable = userCoins >= cost;

        // Create the HTML for this item - Change to col-lg-4 to have 3 items per row
        const itemHtml =
                '<div class="col-lg-4 col-md-6 col-sm-6">' +
                '<div class="shop-item" data-id="' + id + '" data-type="' + itemType + '" data-cost="' + cost + '">' +
                '<div class="shop-item-image">' +
                '<img src="' + contextPath + image + '" alt="' + name + '" onerror="this.src=\'' + contextPath + '/assets/img/default-item.png\'">' +
                '<div class="info-button" data-toggle="modal" data-target="#itemInfoModal" ' +
                'data-name="' + name + '" data-description="' + description + '" data-image="' + contextPath + image + '">' +
                '<i class="fa-solid fa-info"></i>' +
                '</div>' +
                (isArchived ?
                        '<div class="not-available-overlay">' +
                        '<div class="not-available-text">Not Available</div>' +
                        '</div>' : '') +
                '</div>' +
                '<div class="item-name">' + name + '</div>' +
                '<div class="item-cost">' +
                '<i class="fa-solid fa-coins text-warning"></i> ' + cost + ' coins' +
                '</div>' +
                (!isArchived && isAffordable ?
                        '<form class="purchase-form">' +
                        '<input type="hidden" name="itemId" value="' + id + '">' +
                        '<input type="hidden" name="itemType" value="' + itemType + '">' +
                        '<input type="hidden" name="itemCost" value="' + cost + '">' +
                        '<button type="submit" class="buy-button available">' +
                        '<i class="fa-solid fa-shopping-cart coin-icon"></i> Buy' +
                        '</button>' +
                        '</form>' :
                        '<button class="buy-button disabled" disabled>' +
                        '<i class="fa-solid fa-lock coin-icon"></i> ' + (isArchived ? 'Not Available' : 'Not enough coins') +
                        '</button>'
                        ) +
                '</div>' +
                '</div>';

        container.append(itemHtml);
    });

    initializeInfoButtons();
    initializePurchaseForms();
}

// Initialize item info modal triggers
function initializeInfoButtons() {
    $('.info-button').off('click').on('click', function () {
        const name = $(this).data('name');
        const description = $(this).data('description');
        const image = $(this).data('image');

        $('#itemInfoName').text(name);
        $('#itemInfoDescription').text(description);
        $('#itemInfoImage').attr('src', image);

        // Reset zoom state
        $('#itemInfoImage').removeClass('zoomed');

        $('#itemInfoModal').modal('show');
    });
}

// Initialize purchase form submissions
function initializePurchaseForms() {
    $('.purchase-form').off('submit').on('submit', function (e) {
        e.preventDefault();

        const form = $(this);
        const itemId = form.find('input[name="itemId"]').val();
        const itemType = form.find('input[name="itemType"]').val();
        const itemCost = form.find('input[name="itemCost"]').val();

        // Disable the button to prevent multiple clicks
        const buyButton = form.find('.buy-button');
        buyButton.prop('disabled', true).html('<i class="fa-solid fa-spinner fa-spin"></i> Processing...');

        // Send AJAX request to purchase the item
        $.ajax({
            url: contextPath + '/purchaseItem',
            method: 'POST',
            data: {
                itemId: itemId,
                itemType: itemType,
                itemCost: itemCost
            },
            dataType: 'json',
            success: function (response) {
                if (response.success) {
                    // Show success message
                    showAlert('success', response.message);

                    // Update user coins display
                    $('#userCoins').text(response.userCoins);

                    // Update button states based on new coin amount
                    updateBuyButtonStates(response.userCoins);
                } else {
                    // Show error message
                    showAlert('danger', response.message || 'Purchase failed.');
                }

                // Re-enable the button
                buyButton.prop('disabled', false).html('<i class="fa-solid fa-shopping-cart coin-icon"></i> Buy');
            },
            error: function (error) {
                console.error('Error purchasing item:', error);

                // Show error message
                showAlert('danger', 'An error occurred. Please try again.');

                // Re-enable the button
                buyButton.prop('disabled', false).html('<i class="fa-solid fa-shopping-cart coin-icon"></i> Buy');
            }
        });
    });
}

function updateBuyButtonStates(userCoins) {
    $('.shop-item').each(function () {
        const itemCost = parseInt($(this).data('cost'));
        const itemType = $(this).data('type');
        const itemId = $(this).data('id');

        // Check if the item is archived by looking for the overlay
        const isArchived = $(this).find('.not-available-overlay').length > 0;

        const purchaseForm = $(this).find('.purchase-form');
        const buyButton = $(this).find('.buy-button');

        if (isArchived) {
            // Item is archived, keep it disabled
            return;
        }

        if (userCoins >= itemCost) {
            // User can afford this item
            if (buyButton.hasClass('disabled')) {
                // Replace disabled button with purchase form
                const newPurchaseForm =
                        '<form class="purchase-form">' +
                        '<input type="hidden" name="itemId" value="' + itemId + '">' +
                        '<input type="hidden" name="itemType" value="' + itemType + '">' +
                        '<input type="hidden" name="itemCost" value="' + itemCost + '">' +
                        '<button type="submit" class="buy-button available">' +
                        '<i class="fa-solid fa-shopping-cart coin-icon"></i> Buy' +
                        '</button>' +
                        '</form>';

                buyButton.replaceWith(newPurchaseForm);
            }
        } else {
            // User cannot afford this item
            if (purchaseForm.length) {
                // Replace purchase form with disabled button
                const newDisabledButton =
                        '<button class="buy-button disabled" disabled>' +
                        '<i class="fa-solid fa-lock coin-icon"></i> Not enough coins' +
                        '</button>';

                purchaseForm.replaceWith(newDisabledButton);
            }
        }
    });

    // Re-initialize purchase forms for any new buttons
    initializePurchaseForms();
}

// Show alert message in shop modal
function showAlert(type, message) {
    // Map alert types to color schemes
    const colorSchemes = {
        'success': { bg: '#28a745', text: '#fff', icon: '✓' },
        'danger': { bg: '#dc3545', text: '#fff', icon: '✕' },
        'warning': { bg: '#ffc107', text: '#212529', icon: '⚠' },
        'info': { bg: '#17a2b8', text: '#fff', icon: 'ℹ' },
        'primary': { bg: '#007bff', text: '#fff', icon: '!' }
    };
    
    // Use default if type not found
    const colors = colorSchemes[type] || colorSchemes.primary;
    
    // Remove any existing alert popups
    $('.custom-popup-alert').remove();
    
    // Create overlay background
    const overlay = $('<div>', {
        class: 'alert-overlay',
        css: {
            'position': 'fixed',
            'top': '0',
            'left': '0',
            'width': '100%',
            'height': '100%',
            'background-color': 'rgba(0, 0, 0, 0.5)',
            'z-index': '9998',
            'display': 'none'
        }
    });
    
    // Create a new popup element
    const alertDiv = $('<div>', {
        class: `custom-popup-alert`,
        html: `
            <div class="popup-header">
                <span class="close-btn">×</span>
            </div>
            <div class="alert-message">${message}</div>
        `
    });
    
    // Add styles to the popup
    alertDiv.css({
        'position': 'fixed',
        'top': '50%',
        'left': '50%',
        'transform': 'translate(-50%, -50%)',
        'z-index': '9999',
        'background-color': colors.bg,
        'color': colors.text,
        'padding': '20px',
        'box-shadow': '0 5px 15px rgba(0,0,0,0.3)',
        'border-radius': '8px',
        'min-width': '320px',
        'max-width': '80%',
        'display': 'none',
        'font-family': 'Arial, sans-serif',
        'font-size': '16px',
        'text-align': 'center'
    });
    
    // Style the header
    alertDiv.find('.popup-header').css({
        'display': 'flex',
        'justify-content': 'flex-end',
        'align-items': 'center',
        'margin-bottom': '15px'
    });
    
    // Add styles to the close button
    alertDiv.find('.close-btn').css({
        'cursor': 'pointer',
        'font-size': '28px',
        'font-weight': 'bold',
        'line-height': '20px',
        'background': 'transparent',
        'border': 'none',
        'padding': '0 5px',
        'margin-left': '15px',
        'opacity': '0.7',
        'transition': 'opacity 0.2s'
    });
    
    // Hover effect for close button
    alertDiv.find('.close-btn').hover(
        function() { $(this).css('opacity', '1'); },
        function() { $(this).css('opacity', '0.7'); }
    );
    
    // Style the message
    alertDiv.find('.alert-message').css({
        'margin': '10px 0',
        'line-height': '1.5'
    });
    
    // Append to body
    $('body').append(overlay).append(alertDiv);
    
    // Show with animation
    overlay.fadeIn(200);
    alertDiv.fadeIn(300);
    
    // Add click event to close button
    alertDiv.find('.close-btn').on('click', function() {
        overlay.fadeOut(200);
        alertDiv.fadeOut(300, function() {
            overlay.remove();
            alertDiv.remove();
        });
    });
    
    // Click outside to close
    overlay.on('click', function() {
        overlay.fadeOut(200);
        alertDiv.fadeOut(300, function() {
            overlay.remove();
            alertDiv.remove();
        });
    });
    
    // Prevent closing when clicking on the alert itself
    alertDiv.on('click', function(e) {
        e.stopPropagation();
    });
    
    // Auto close after 5 seconds
    setTimeout(function() {
        overlay.fadeOut(200);
        alertDiv.fadeOut(300, function() {
            overlay.remove();
            alertDiv.remove();
        });
    }, 5000);
}