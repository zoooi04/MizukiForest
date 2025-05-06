// Tree Box Manifest Script - For fun pulls without affecting inventory
document.addEventListener('DOMContentLoaded', function () {
    // console.log('DOM fully loaded - initializing Tree Box Manifest script');

    // Create the manifest modal for selecting tree boxes
    createManifestModal();

    // Create the opening modal for tree box opening animation and results
    createTreeBoxOpeningModal();

    const existingButton = document.getElementById('manifestBtn');
    if (existingButton) {
        existingButton.addEventListener('click', function () {
            openManifestModal();
        });
    }

    // Preload audio files
    // console.log('Preloading audio files for rarity sounds');
    const audioFiles = {
        'Common': '/MizukiForest/media/audio/crcommon.mp3',
        'Rare': '/MizukiForest/media/audio/crrare.mp3',
        'Epic': '/MizukiForest/media/audio/crepic.mp3',
        'Legendary': '/MizukiForest/media/audio/crlegendary.mp3'
    };

    const audioElements = {};
    Object.keys(audioFiles).forEach(function (rarity) {
        var file = audioFiles[rarity];
        audioElements[rarity] = new Audio(file);
        // console.log('Preloaded ' + file + ' for ' + rarity + ' rarity');
    });

    // Create and append the manifest modal to the DOM
    function createManifestModal() {
        // console.log('Creating manifest modal for tree box selection');
        const manifestModal = document.createElement('div');
        manifestModal.className = 'modal fade';
        manifestModal.id = 'treeBoxManifestModal';
        manifestModal.tabIndex = '-1';
        manifestModal.setAttribute('data-bs-backdrop', 'static');
        manifestModal.setAttribute('data-bs-keyboard', 'true');

        manifestModal.innerHTML =
                '<div class="modal-dialog modal-lg modal-dialog-centered">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<h5 class="modal-title">Manifest Your Tree Box Pulls!</h5>' +
                '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                '</div>' +
                '<div class="modal-body">' +
                '<p class="alert alert-info">Pull for Fun! Trees obtained here do not reflect in your inventory, just for satisfaction purposes.</p>' +
                '<div class="treebox-grid">' +
                '<!-- Tree Box Grid -->' +
                createTreeBoxCards() +
                '</div>' +
                '</div>' +
                '<div class="modal-footer">' +
                '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>' +
                '</div>' +
                '</div>' +
                '</div>';

        document.body.appendChild(manifestModal);
        // console.log('Tree box manifest modal added to DOM');

        // Add styles for the tree box grid
        const manifestStyles = document.createElement('style');
        manifestStyles.textContent =
                '.treebox-grid {' +
                'display: grid;' +
                'grid-template-columns: repeat(2, 1fr);' +
                'gap: 20px;' +
                '}' +
                '.treebox-card {' +
                'border: 1px solid #ddd;' +
                'border-radius: 8px;' +
                'padding: 15px;' +
                'text-align: center;' +
                'cursor: pointer;' +
                'transition: transform 0.3s, box-shadow 0.3s;' +
                '}' +
                '.treebox-card:hover {' +
                'transform: translateY(-5px);' +
                'box-shadow: 0 10px 20px rgba(0,0,0,0.1);' +
                '}' +
                '.treebox-image {' +
                'width: 100px;' +
                'height: 100px;' +
                'background-size: contain;' +
                'background-position: center;' +
                'background-repeat: no-repeat;' +
                'margin: 0 auto 10px;' +
                '}' +
                '.treebox-name {' +
                'font-weight: bold;' +
                'margin-bottom: 5px;' +
                '}' +
                '.treebox-common {' +
                'border-color: #aaa;' +
                '}' +
                '.treebox-rare {' +
                'border-color: #4287f5;' +
                '}' +
                '.treebox-epic {' +
                'border-color: #9932CC;' +
                '}' +
                '.treebox-legendary {' +
                'border-color: #FFA500;' +
                '}' +
                '.treebox-common .treebox-name {' +
                'color: #666;' +
                '}' +
                '.treebox-rare .treebox-name {' +
                'color: #4287f5;' +
                '}' +
                '.treebox-epic .treebox-name {' +
                'color: #9932CC;' +
                '}' +
                '.treebox-legendary .treebox-name {' +
                'color: #FFA500;' +
                '}';

        document.head.appendChild(manifestStyles);
        // console.log('Tree box manifest styles added to document head');
    }

    // Create HTML for the tree box cards
    function createTreeBoxCards() {
        const treeBoxes = [
            {id: 'TB000001', name: 'Common Tree Box', rarity: 'common'},
            {id: 'TB000002', name: 'Rare Tree Box', rarity: 'rare'},
            {id: 'TB000003', name: 'Epic Tree Box', rarity: 'epic'},
            {id: 'TB000004', name: 'Legendary Tree Box', rarity: 'legendary'}
        ];

        let cardsHTML = '';
        treeBoxes.forEach(box => {
            cardsHTML +=
                    '<div class="treebox-card treebox-' + box.rarity + '" data-treebox-id="' + box.id + '">' +
                    '<div class="treebox-image" style="background-image: url(\'/MizukiForest/media/images/treebox/' + box.id + '.png\')"></div>' +
                    '<div class="treebox-name">' + box.name + '</div>' +
                    '<button class="btn btn-sm btn-outline-primary open-treebox-btn">Open Box</button>' +
                    '</div>';
        });

        return cardsHTML;
    }

    // Create and append the tree box opening modal to the DOM
    function createTreeBoxOpeningModal() {
        // console.log('Creating tree box opening modal DOM structure');
        const openingModal = document.createElement('div');
        openingModal.className = 'modal fade';
        openingModal.id = 'treeBoxOpeningModal';
        openingModal.tabIndex = '-1';
        // Remove aria-hidden attribute initially
        openingModal.setAttribute('data-bs-backdrop', 'static');
        openingModal.setAttribute('data-bs-keyboard', 'true');

        openingModal.innerHTML =
                '<div class="modal-dialog modal-dialog-centered">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<h5 class="modal-title">Opening Tree Box</h5>' +
                '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                '</div>' +
                '<div class="modal-body">' +
                '<!-- Animation Stage -->' +
                '<div id="openingAnimationStage" class="text-center mb-4">' +
                '<div class="rarities-container d-flex justify-content-between mb-4">' +
                '<div class="rarity-item rarity-common">Common</div>' +
                '<div class="rarity-item rarity-rare">Rare</div>' +
                '<div class="rarity-item rarity-epic">Epic</div>' +
                '<div class="rarity-item rarity-legendary">Legendary</div>' +
                '</div>' +
                '<div class="animation-status"></div>' +
                '</div>' +
                '<!-- Result Stage (initially hidden) -->' +
                '<div id="openingResultStage" class="text-center" style="display: none;">' +
                '<div id="resultRarityBadge" class="mb-3 py-2 fw-bold"></div>' +
                '<div id="resultTreeImage" class="mb-3" style="width: 128px; height: 128px; margin: 0 auto; background-size: contain; background-position: center; background-repeat: no-repeat;"></div>' +
                '<h4 id="resultTreeName" class="mb-1"></h4>' +
                '<p id="resultTreeDescription" class="text-muted small"></p>' +
                '</div>' +
                '</div>' +
                '<div class="modal-footer">' +
                '<button type="button" class="btn btn-primary" data-bs-dismiss="modal">Close</button>' +
                '<button type="button" id="treeBoxManifestAddBtn" class="btn btn-success">Add to Inventory</button>' +
                '<button type="button" id="manifestAgainBtn" class="btn btn-outline-success">Manifest Again</button>' +
                '</div>' +
                '</div>' +
                '</div>';

        document.body.appendChild(openingModal);
        // console.log('Tree box opening modal added to DOM');

        // Add styles for the opening animation
        // console.log('Adding CSS styles for animation');
        const openingStyles = document.createElement('style');
        openingStyles.textContent =
                '.rarities-container {' +
                'width: 100%;' +
                'padding: 10px 0;' +
                '}' +
                '.rarity-item {' +
                'padding: 10px;' +
                'border-radius: 5px;' +
                'width: 22%;' +
                'text-align: center;' +
                'font-weight: bold;' +
                'border: 2px solid transparent;' +
                'transition: all 0.3s;' +
                'opacity: 0.7;' +
                'font-size: 0.8rem;' +
                '}' +
                '.rarity-common {' +
                'background-color: rgba(170, 170, 170, 0.2);' +
                'color: #666666;' +
                '}' +
                '.rarity-rare {' +
                'background-color: rgba(66, 135, 245, 0.2);' +
                'color: #4287f5;' +
                '}' +
                '.rarity-epic {' +
                'background-color: rgba(153, 50, 204, 0.2);' +
                'color: #9932CC;' +
                '}' +
                '.rarity-legendary {' +
                'background-color: rgba(255, 165, 0, 0.2);' +
                'color: #FFA500;' +
                '}' +
                '.rarity-item.highlight {' +
                'transform: scale(1.1);' +
                'opacity: 1;' +
                'border-color: currentColor;' +
                'box-shadow: 0 0 10px rgba(0,0,0,0.2);' +
                '}' +
                '.rarity-item.selected {' +
                'transform: scale(1.15);' +
                'opacity: 1;' +
                'border-color: currentColor;' +
                'box-shadow: 0 0 15px currentColor;' +
                '}' +
                '.animation-status {' +
                'height: 20px;' +
                'font-style: italic;' +
                'color: #666;' +
                '}' +
                '@keyframes pulse {' +
                '0% { transform: scale(1); }' +
                '50% { transform: scale(1.05); }' +
                '100% { transform: scale(1); }' +
                '}' +
                '.pulse-animation {' +
                'animation: pulse 0.5s infinite;' +
                '}' +
                '#resultRarityBadge {' +
                'border-radius: 4px;' +
                'padding: 8px;' +
                'font-size: 1.2rem;' +
                '}' +
                '.badge-common {' +
                'background-color: rgba(170, 170, 170, 0.2);' +
                'color: #666666;' +
                '}' +
                '.badge-rare {' +
                'background-color: rgba(66, 135, 245, 0.2);' +
                'color: #4287f5;' +
                '}' +
                '.badge-epic {' +
                'background-color: rgba(153, 50, 204, 0.2);' +
                'color: #9932CC;' +
                '}' +
                '.badge-legendary {' +
                'background-color: rgba(255, 165, 0, 0.2);' +
                'color: #FFA500;' +
                '}';

        document.head.appendChild(openingStyles);
        // console.log('Animation styles added to document head');

        // Add event handler for Manifest Again button
        document.getElementById('manifestAgainBtn').addEventListener('click', function () {
            // console.log('Manifest Again button clicked');
            closeOpeningModal();
            openManifestModal();
        });
    }

    // Function to open the manifest modal
    function openManifestModal() {
        // console.log('Opening the manifest modal');
        const manifestModalElement = document.getElementById('treeBoxManifestModal');

        if (typeof bootstrap !== 'undefined') {
            const manifestModal = new bootstrap.Modal(manifestModalElement);
            manifestModalElement.setAttribute('aria-hidden', 'false');
            manifestModal.show();
            // console.log('Tree box manifest modal shown via Bootstrap');
        } else {
            // console.log('Bootstrap not available, showing modal manually');
            if (manifestModalElement) {
                manifestModalElement.setAttribute('aria-hidden', 'false');
                manifestModalElement.style.display = 'block';
                manifestModalElement.classList.add('show');

                // Create and add backdrop if needed
                const backdrop = document.createElement('div');
                backdrop.className = 'modal-backdrop fade show';
                document.body.appendChild(backdrop);
                // console.log('Tree box manifest modal shown manually');
            }
        }

        // Add click handlers to tree box buttons
        const openButtons = manifestModalElement.querySelectorAll('.open-treebox-btn');
        openButtons.forEach(button => {
            // Clone and replace to remove old event listeners
            const newButton = button.cloneNode(true);
            button.parentNode.replaceChild(newButton, button);

            // Add new click handler
            newButton.addEventListener('click', function () {
                const treeBoxCard = this.closest('.treebox-card');
                const treeBoxId = treeBoxCard.getAttribute('data-treebox-id');
                // console.log('Open button clicked for tree box:', treeBoxId);

                // Close the manifest modal
                closeManifestModal();

                // Open the tree box
                openTreeBox(treeBoxId);
            });
        });
    }

    // Function to close the manifest modal
    function closeManifestModal() {
        // console.log('Closing the manifest modal');
        const modalElement = document.getElementById('treeBoxManifestModal');

        if (typeof bootstrap !== 'undefined') {
            const modal = bootstrap.Modal.getInstance(modalElement);
            if (modal) {
                modal.hide();
                // console.log('Manifest modal closed via Bootstrap');
            } else {
                closeModalManually(modalElement);
            }
        } else {
            closeModalManually(modalElement);
        }
    }

    // Function to close the opening modal
    function closeOpeningModal() {
        // console.log('Closing the opening modal');
        const modalElement = document.getElementById('treeBoxOpeningModal');

        if (typeof bootstrap !== 'undefined') {
            const modal = bootstrap.Modal.getInstance(modalElement);
            if (modal) {
                modal.hide();
                // console.log('Opening modal closed via Bootstrap');
            } else {
                closeModalManually(modalElement);
            }
        } else {
            closeModalManually(modalElement);
        }
    }

    // Function to open the tree box
    function openTreeBox(treeBoxId) {
        // console.log('openTreeBox function called with treeBoxId:', treeBoxId);

        // Show the opening modal
        // console.log('Opening the tree box opening modal');
        const openingModalElement = document.getElementById('treeBoxOpeningModal');

        if (typeof bootstrap !== 'undefined') {
            const openingModal = new bootstrap.Modal(openingModalElement);
            // Set aria-hidden to false before showing
            openingModalElement.setAttribute('aria-hidden', 'false');
            openingModal.show();
            // console.log('Tree box opening modal shown via Bootstrap');
        } else {
            // console.log('Bootstrap not available, showing modal manually');
            if (openingModalElement) {
                // Set aria-hidden to false before showing
                openingModalElement.setAttribute('aria-hidden', 'false');
                openingModalElement.style.display = 'block';
                openingModalElement.classList.add('show');

                // Create and add backdrop if needed
                const backdrop = document.createElement('div');
                backdrop.className = 'modal-backdrop fade show';
                document.body.appendChild(backdrop);

                // console.log('Tree box opening modal shown manually');
            }
        }

        // Reset the animation stage
        // console.log('Resetting animation and result stages');
        const animationStage = document.getElementById('openingAnimationStage');
        const resultStage = document.getElementById('openingResultStage');

        animationStage.style.display = "block";
        resultStage.style.display = "none";
        // console.log('Animation stage reset complete');

        // Reset any highlights
        // console.log('Removing any existing highlights');
        document.querySelectorAll('.rarity-item').forEach(item => {
            item.classList.remove('highlight', 'selected');
        });

        // Set animation status
        const animationStatus = document.querySelector('.animation-status');
        if (animationStatus) {
            animationStatus.textContent = "Processing...";
            // console.log('Animation status set to: Processing...');
        }

        // Send AJAX request to open the tree box
        const url = contextPath + '/TreeBoxOpenServlet2?treeboxId=' + treeBoxId;
        // console.log('Sending AJAX request to:', url);

        const xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);

        xhr.onload = function () {
            // console.log('AJAX response received, status:', xhr.status);
            if (xhr.status === 200) {
                try {
                    const response = JSON.parse(xhr.responseText);
                    // console.log('Response parsed:', response);

                    if (response.success) {
                        // console.log('Success response, starting rarity selection animation');
                        // Start the rarity selection animation
                        playRaritySelectionAnimation(response);
                    } else {
                        // console.log('Error in response:', response.error);
                        // Show error message
                        if (animationStatus) {
                            animationStatus.textContent = "Error: " + (response.error || "Unknown error");
                        }
                    }
                } catch (e) {
                    // console.error('Error parsing response:', e);
                    if (animationStatus) {
                        animationStatus.textContent = "Error processing response";
                    }
                }
            } else {
                // console.error('Server error status:', xhr.status);
                if (animationStatus) {
                    animationStatus.textContent = "Server error: " + xhr.status;
                }
            }
        };

        xhr.onerror = function () {
            // console.error('Network error occurred');
            if (animationStatus) {
                animationStatus.textContent = "Network error";
            }
        };

        // console.log('Sending AJAX request...');
        xhr.send();
    }

    // Play the rarity selection animation
    function playRaritySelectionAnimation(response) {
        // console.log('Starting rarity selection animation with response:', response);
        const rarityItems = document.querySelectorAll('.rarity-item');
        const animationStatus = document.querySelector('.animation-status');

        // Map of rarity names to their index positions
        const rarityNameToIndex = {
            'Common': 0,
            'Rare': 1,
            'Epic': 2,
            'Legendary': 3
        };

        const selectedRarityName = response.selectedRarity.rarityName;
        const selectedRarityIndex = rarityNameToIndex[selectedRarityName] || 0;
        // console.log('Selected rarity:', selectedRarityName, 'at index:', selectedRarityIndex);

        // Initial animation parameters
        let speed = 100;
        let cycles = 0;
        let currentIndex = 0;
        let maxCycles = 5; // Number of full cycles before slowing down
        let maxSpeed = 400; // Maximum speed before forcing selection
        let animationDuration = 8000; // Max animation duration in ms
        let startTime = Date.now();
        let forceSelectionTriggered = false;
        let selectionTimeoutId = null;

        // console.log('Animation parameters - speed:', speed, 'maxCycles:', maxCycles, 'maxSpeed:', maxSpeed, 'maxDuration:', animationDuration);

        // Clear any existing interval
        if (window.rarityAnimationInterval) {
            // console.log('Clearing existing animation interval');
            clearInterval(window.rarityAnimationInterval);
        }

        // Status update
        if (animationStatus) {
            animationStatus.textContent = "Selecting rarity...";
            // console.log('Animation status updated to: Selecting rarity...');
        }

        // Function to stop animation and show result
        const stopAnimationAndShowResult = () => {
            if (forceSelectionTriggered) {
                // console.log('Selection already triggered, ignoring duplicated call');
                return;
            }

            // console.log('Stopping animation at selected rarity:', selectedRarityName);
            forceSelectionTriggered = true;

            // Clear interval and any pending timeouts
            clearInterval(window.rarityAnimationInterval);
            if (selectionTimeoutId)
                clearTimeout(selectionTimeoutId);

            // Final highlighting of the selected rarity
            rarityItems.forEach(item => item.classList.remove('highlight', 'selected'));
            rarityItems[selectedRarityIndex].classList.add('selected');
            // console.log('Final selection highlighted:', selectedRarityName, 'at index:', selectedRarityIndex);

            // Show the result after a brief pause
            // console.log('Setting timeout to show results in 1 second');
            setTimeout(() => {
                // console.log('Timeout fired, showing opening result');
                showOpeningResult(response);
            }, 1000);
        };

        // Create a function to update current index and highlight
        const updateHighlight = () => {
            // Remove highlight from all items
            rarityItems.forEach(item => item.classList.remove('highlight'));

            // Add highlight to current item
            if (rarityItems[currentIndex]) {
                rarityItems[currentIndex].classList.add('highlight');
                // console.log('Highlighting rarity at index:', currentIndex);
            }

            // When animation is slow enough and we're at the correct index, stop
            if (speed >= 300 && currentIndex === selectedRarityIndex && !forceSelectionTriggered) {
                // console.log('Animation slow enough and at selected rarity index, stopping animation');
                stopAnimationAndShowResult();
                return true; // Indicate we've stopped
            }

            // Move to next item
            currentIndex = (currentIndex + 1) % rarityItems.length;

            // If we've completed a cycle
            if (currentIndex === 0) {
                cycles++;
                // console.log('Completed cycle', cycles, 'of animation');

                // Start slowing down after a few cycles
                if (cycles >= maxCycles) {
                    speed += 50; // Gradually slow down
                    // console.log('Slowing down animation - new speed:', speed);

                    // When slow enough, prepare to stop at the selected rarity soon
                    if (speed >= maxSpeed && !forceSelectionTriggered) {
                        // console.log('Max speed threshold reached, forcing selection soon');
                        // Force selection within the next cycle
                        const cyclesNeeded = (selectedRarityIndex - currentIndex + rarityItems.length) % rarityItems.length + 1;
                        const timeToSelection = cyclesNeeded * speed;

                        // console.log('Will force selection after', cyclesNeeded, 'more steps, in', timeToSelection, 'ms');

                        // Set a timeout to force selection
                        selectionTimeoutId = setTimeout(() => {
                            // console.log('Selection timeout reached, forcing selection now');
                            stopAnimationAndShowResult();
                        }, timeToSelection);
                    }
                }

                return false; // Indicate we should continue
            }

            return false; // Indicate we should continue
        };

        // Start the animation with a recursive setTimeout approach instead of setInterval
        const runAnimationStep = () => {
            // Safety check - force selection if animation runs too long
            const currentTime = Date.now();
            const elapsedTime = currentTime - startTime;

            if (elapsedTime > animationDuration && !forceSelectionTriggered) {
                // console.log('Animation duration exceeded, forcing selection');
                stopAnimationAndShowResult();
                return;
            }

            // Update highlight and check if we should stop
            const shouldStop = updateHighlight();
            if (shouldStop || forceSelectionTriggered) {
                // console.log('Animation step indicated we should stop');
                return;
            }

            // Schedule next step
            window.rarityAnimationInterval = setTimeout(runAnimationStep, speed);
        };

        // Start the animation
        // console.log('Starting rarity cycling animation with setTimeout approach');
        window.rarityAnimationInterval = setTimeout(runAnimationStep, speed);
    }

    // Function to play rarity sound
    function playRaritySound(rarityName) {
        // console.log('Playing sound for ' + rarityName + ' rarity');
        const soundFileName = {
            'Common': '/MizukiForest/media/audio/crcommon.mp3',
            'Rare': '/MizukiForest/media/audio/crrare.mp3',
            'Epic': '/MizukiForest/media/audio/crepic.mp3',
            'Legendary': '/MizukiForest/media/audio/crlegendary.mp3'
        }[rarityName] || '/MizukiForest/media/audio/crcommon.mp3';

        // Create a new audio element each time to ensure it plays
        const audio = new Audio(soundFileName);

        // Play the sound
        audio.play().then(function () {
            // console.log(rarityName + ' sound played successfully');
        }).catch(function (error) {
            // console.error('Error playing sound:', error);
        });

        return audio;
    }

    // Show the result of the tree box opening
    function showOpeningResult(response) {
        // console.log('Showing opening result:', response);
        const animationStage = document.getElementById('openingAnimationStage');
        const resultStage = document.getElementById('openingResultStage');
        const resultRarityBadge = document.getElementById('resultRarityBadge');
        const resultTreeImage = document.getElementById('resultTreeImage');
        const resultTreeName = document.getElementById('resultTreeName');
        const manifestAddBtn = document.getElementById('treeBoxManifestAddBtn');
        const resultTreeDescription = document.getElementById('resultTreeDescription');
        const closeBtn = document.querySelector('.modal-footer .btn-primary'); // Reference to the close button
        const manifestAgainBtn = document.getElementById('manifestAgainBtn');
        const btn = document.getElementById('addToInventoryBtn');
        // Exit early if elements aren't found
        if (!animationStage || !resultStage) {
            // console.error('Critical elements not found for showing results');
            return;
        }

        // Ensure we actually have the response data
        if (!response || !response.selectedRarity || !response.selectedTree) {
            // console.error('Missing response data for showing results:', response);
            if (animationStage)
                animationStage.innerHTML = '<div class="alert alert-danger">Error: Invalid response data</div>';
            return;
        }

        try {
            // Transition from animation to result
            // console.log('Transitioning from animation to result stage');
            animationStage.style.display = "none";
            resultStage.style.display = "block";

            // Set the rarity badge
            const rarityName = response.selectedRarity.rarityName;
            resultRarityBadge.textContent = rarityName;
            resultRarityBadge.className = 'mb-3 py-2 fw-bold badge-' + rarityName.toLowerCase();
            // console.log('Rarity badge set to:', rarityName);

            // Set the tree image
            const imagePath = '/MizukiForest' + response.selectedTree.imagePath;
            resultTreeImage.style.backgroundImage = 'url(\'' + imagePath + '\')';
            // console.log('Tree image set to:', imagePath);

            // Set tree name and description
            resultTreeName.textContent = response.selectedTree.treeName;
            resultTreeDescription.textContent = response.selectedTree.treeDescription || "A beautiful tree.";
            // console.log('Tree details set - Name:', response.selectedTree.treeName);

            // Apply a pulse animation to the image
            // console.log('Adding pulse animation to tree image');
            resultTreeImage.classList.add('pulse-animation');
            setTimeout(() => {
                // console.log('Removing pulse animation');
                resultTreeImage.classList.remove('pulse-animation');
            }, 2000);

            // Play the sound based on rarity
            // console.log('Playing sound for rarity: ' + rarityName);
            playRaritySound(rarityName);

            // Show all relevant buttons in the footer
            closeBtn.style.display = "block"; // Show the close button
            manifestAddBtn.style.display = "block"; // Show the add button
            btn.textContent = "Okay!";
            btn.style.display = "block"; // Show the manifest again button
            // console.log('All footer buttons made visible');
        } catch (error) {
            // console.error('Error while showing opening result:', error);
            if (animationStage) {
                animationStage.style.display = "block";
                animationStage.innerHTML = '<div class="alert alert-danger">Error showing result: ' + error.message + '</div>';
            }
        }

        // Set up the add to inventory button functionality
        btn.onclick = function () {
            console.log('Add to inventory button clicked');

            const bodyElement = document.body;
            // Create a temporary button to receive focus
            const tempButton = document.createElement('button');
            tempButton.style.position = 'absolute';
            tempButton.style.opacity = '0';
            bodyElement.appendChild(tempButton);
            tempButton.focus();

            // Here you would implement the logic to add the tree to the player's inventory
            // For now, we'll just show an alert
            alert('Remember, this is just a simulation!');

            // Close the modal properly
            // console.log('Closing the opening modal');
            const modalElement = document.getElementById('treeBoxOpeningModal');

            // Proper modal closing that handles accessibility
            if (typeof bootstrap !== 'undefined') {
                const openingModal = bootstrap.Modal.getInstance(modalElement);
                if (openingModal) {
                    // Ensure focus is shifted before hiding
                    openingModal.hide();
                    // console.log('Modal closed via Bootstrap');
                } else {
                    closeModalManually(modalElement);
                }
            } else {
                closeModalManually(modalElement);
            }

            // Navigate after modal is closed
            setTimeout(() => {
                // Remove the temporary button
                if (tempButton && tempButton.parentNode) {
                    tempButton.parentNode.removeChild(tempButton);
                }

                // Redirect to forest data page
                window.location.href = '/MizukiForest/GetUserForestData?time=' + new Date().getTime();
            }, 100);
        };
    }

    // Helper function to safely close a modal without Bootstrap
    function closeModalManually(modalElement) {
        if (!modalElement)
            return;

        // console.log('Bootstrap not available or modal instance not found, using alternative closing method');

        // First set aria-hidden back to true before hiding
        modalElement.setAttribute('aria-hidden', 'true');
        modalElement.style.display = 'none';
        modalElement.classList.remove('show');

        // Remove modal backdrop if present
        const backdrop = document.querySelector('.modal-backdrop');
        if (backdrop && backdrop.parentNode) {
            backdrop.parentNode.removeChild(backdrop);
        }

        // Remove any inline styles that might affect scrolling
        document.body.style.overflow = '';
        document.body.style.paddingRight = '';

        // console.log('Modal hidden manually');
    }

    // console.log('Tree Box Opener script initialization complete');
});