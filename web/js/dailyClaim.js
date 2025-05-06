// dailyClaim.js - Handles the daily claim modal functionality

document.addEventListener('DOMContentLoaded', function() {
    // Check if dailyClaimBtn exists before adding event listener
    const dailyClaimBtn = document.getElementById('dailyClaimBtn');
    if (dailyClaimBtn) {
        console.log('Daily claim button found, initializing...');
        initializeDailyClaim();
    } else {
        console.log('Daily claim button not found in DOM');
    }
});

function initializeDailyClaim() {
    const dailyClaimBtn = document.getElementById('dailyClaimBtn');
    
    // Add event listener to the button
    dailyClaimBtn.addEventListener('click', function() {
        console.log('Daily claim button clicked');
        
        // Destroy any existing modal instance before creating a new one
        const existingModalElement = document.getElementById('dailyClaimModal');
        if (existingModalElement) {
            console.log('Existing modal found, destroying it first');
            const existingModalInstance = bootstrap.Modal.getInstance(existingModalElement);
            if (existingModalInstance) {
                existingModalInstance.dispose();
                console.log('Existing modal instance disposed');
            }
            // Remove the element from DOM
            existingModalElement.remove();
            console.log('Existing modal element removed from DOM');
        } else {
            console.log('No existing modal found, proceeding with creation');
        }
        
        // Now check login streak and create a fresh modal
        checkLoginStreak();
    });
}

// Flag to track if a claim is in progress or has been completed
let claimProcessed = false;

function checkLoginStreak() {
    // Reset the claim processed flag when checking login streak (opening modal)
    claimProcessed = false;
    
    console.log('Sending request to check login streak');
    // Send AJAX request to check login streak
    fetch('/MizukiForest/CheckLoginStreakServlet', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log("Login streak data received:", data);
        console.log("User current coins:", data.coins);
        createAndPopulateModal(data);
    })
    .catch(error => {
        console.error('Error checking login streak:', error);
        showNotification('Failed to load login streak data. Please try again.', 'error');
    });
}

function createAndPopulateModal(data) {
    console.log('Creating new modal with user data:', data);
    
    // Check if modal already exists in DOM
    let modalElement = document.getElementById('dailyClaimModal');
    
    // If it still exists (somehow), remove it
    if (modalElement) {
        console.log('Modal element still exists, removing it');
        modalElement.remove();
    }
    
    // Create new modal element
    modalElement = document.createElement('div');
    modalElement.id = 'dailyClaimModal';
    modalElement.className = 'modal fade';
    modalElement.setAttribute('tabindex', '-1');
    modalElement.setAttribute('aria-labelledby', 'dailyClaimModalLabel');
    modalElement.setAttribute('aria-hidden', 'true');
    
    document.body.appendChild(modalElement);
    console.log('New modal element created and added to DOM');
    
    // Create modal structure
    modalElement.innerHTML = `
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="dailyClaimModalLabel">Daily Login Rewards</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Content will be added dynamically -->
                </div>
                <div class="modal-footer">
                    <!-- Buttons will be added dynamically -->
                </div>
            </div>
        </div>
    `;
    
    // Create a new modal instance
    const dailyClaimModal = new bootstrap.Modal(modalElement);
    console.log('Bootstrap modal instance created');
    
    // Add event listener for when modal is fully hidden (catches all close events)
    $(modalElement).on('hidden.bs.modal', function () {
        console.log('Modal hidden event triggered');
        console.log('Final user coins before redirect:', document.getElementById('userCoinsAmount').textContent);
        
        // Destroy the modal instance
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        if (modalInstance) {
            console.log('Disposing modal instance on close');
            modalInstance.dispose();
        }
        
        // Remove the element from DOM
        modalElement.remove();
        console.log('Modal element removed from DOM on close');
        
        // Redirect to refresh forest data
        console.log('Redirecting to refresh forest data');
        window.location.href = '/MizukiForest/GetUserForestData?time=' + new Date().getTime();
    });
    
    // Populate the modal content
    populateModal(data);
    
    // Show the modal
    dailyClaimModal.show();
    console.log('Modal displayed to user');
}

function populateModal(data) {
    console.log('Populating modal with data:', data);
    const modalBody = document.querySelector('#dailyClaimModal .modal-body');
    const modalHeader = document.querySelector('#dailyClaimModal .modal-header');
    
    // Clear existing content
    modalBody.innerHTML = '';
    
    // Add total streak display to header
    const streakCounter = document.createElement('div');
    streakCounter.className = 'ms-auto streak-counter';
    
    // Apply a data attribute for special streak styling
    const streakClass = getStreakClass(data.loginStreak);
    
    streakCounter.innerHTML = `
        <div class="d-flex align-items-center">
            <span class="streak-flame ${data.loginStreak >= 5 ? 'streak-flame-high' : ''}">🔥</span>
            <span class="streak-number ${streakClass}" data-streak="${data.loginStreak}">${data.loginStreak}</span>
            <span class="streak-text">day${data.loginStreak !== 1 ? 's' : ''} streak</span>
        </div>
    `;
    modalHeader.appendChild(streakCounter);
    
    // Add user's coins display
    const coinsDisplay = document.createElement('div');
    coinsDisplay.className = 'user-coins text-end mb-3';
    coinsDisplay.innerHTML = `
        <div class="d-flex align-items-center justify-content-end">
            <span>Your coins: </span>
            <img src="/MizukiForest/media/images/forestmisc/coin.png" alt="Coins" class="coin-icon mx-1">
            <span id="userCoinsAmount">${data.coins}</span>
        </div>
    `;
    modalBody.appendChild(coinsDisplay);
    console.log('Initial user coins display set:', data.coins);
    
    // Create day columns container
    const daysContainer = document.createElement('div');
    daysContainer.className = 'days-container d-flex justify-content-between';
    modalBody.appendChild(daysContainer);
    
    // Calculate which day we're on within the 7-day cycle
    const currentDayInCycle = data.loginStreak % 7 || 7; // If remainder is 0, we're on day 7
    console.log('Current day in 7-day cycle:', currentDayInCycle);
    
    // Create 7 day columns
    for (let i = 1; i <= 7; i++) {
        const dayColumn = document.createElement('div');
        const coinAmount = i * 100;
        let status;
        
        if (i < currentDayInCycle) {
            status = 'claimed'; // Past days (already claimed)
        } else if (i === currentDayInCycle) {
            status = data.canClaim ? 'claimable' : 'claimed'; // Current day (claimable if not already claimed today)
        } else {
            status = 'future'; // Future days
        }
        
        dayColumn.className = `day-column ${status}`;
        if (status === 'claimable') {
            dayColumn.classList.add('breathing');
        }
        
        // Calculate the date for this column
        const columnDate = new Date();
        columnDate.setDate(columnDate.getDate() - (currentDayInCycle - i));
        const formattedDate = columnDate.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
        
        dayColumn.innerHTML = `
            <div class="day-number">Day ${i}</div>
            <div class="day-date">${formattedDate}</div>
            <div class="coin-container">
                <img src="/MizukiForest/media/images/forestmisc/coin.png" alt="Coins" class="coin-icon">
                <div class="coin-amount">${coinAmount}</div>
                ${status === 'claimed' ? '<div class="claimed-overlay">CLAIMED</div>' : ''}
            </div>
        `;
        
        // Add click event for claimable day
        if (status === 'claimable') {
            dayColumn.addEventListener('click', function() {
                console.log('Claimable day column clicked');
                if (!claimProcessed) {
                    claimReward(data.userId, coinAmount);
                } else {
                    console.log('Claim already processed, ignoring click');
                }
            });
        }
        
        daysContainer.appendChild(dayColumn);
    }
    
    // Add claim button only if rewards can be claimed
    const modalFooter = document.querySelector('#dailyClaimModal .modal-footer');
    modalFooter.innerHTML = '';
    
    const closeButton = document.createElement('button');
    closeButton.type = 'button';
    closeButton.className = 'btn btn-secondary';
    closeButton.setAttribute('data-bs-dismiss', 'modal');
    closeButton.textContent = 'Close';
    closeButton.addEventListener('click', function() {
        console.log('Close button clicked');
    });
    modalFooter.appendChild(closeButton);
    
    if (data.canClaim) {
        const claimButton = document.createElement('button');
        claimButton.type = 'button';
        claimButton.id = 'claimRewardButton';
        claimButton.className = 'btn btn-success claim-btn';
        claimButton.textContent = `Claim ${currentDayInCycle * 100} Coins!`;
        claimButton.addEventListener('click', function() {
            console.log('Claim button clicked');
            if (!claimProcessed) {
                claimReward(data.userId, currentDayInCycle * 100);
            } else {
                console.log('Claim already processed, ignoring click');
            }
        });
        modalFooter.appendChild(claimButton);
        console.log('Claim button added with amount:', currentDayInCycle * 100);
    } else {
        console.log('No claim button added as canClaim is false');
    }
}

function getStreakClass(streak) {
    if (streak >= 100) return 'streak-hundred';
    if (streak >= 30) return 'streak-thirty';
    if (streak >= 7) return 'streak-seven';
    return 'streak-default';
}

function disableClaimElements() {
    console.log('Disabling claim UI elements');
    // Disable the claim button
    const claimButton = document.getElementById('claimRewardButton');
    if (claimButton) {
        claimButton.disabled = true;
        claimButton.classList.remove('btn-success');
        claimButton.classList.add('btn-secondary');
        console.log('Claim button disabled');
    }
    
    // Remove click events from claimable day columns
    const claimableColumns = document.querySelectorAll('.day-column.claimable');
    claimableColumns.forEach(column => {
        column.style.pointerEvents = 'none';
        column.classList.remove('breathing');
        console.log('Claimable column disabled');
    });
}

function claimReward(userId, amount) {
    // Prevent multiple claims by checking flag
    if (claimProcessed) {
        console.log('Claim already processed, ignoring request');
        return;
    }
    
    // Set flag to true immediately to prevent multiple clicks
    claimProcessed = true;
    console.log('Processing claim for user', userId, 'with amount', amount);
    
    // Log current coins before claim
    const currentCoins = document.getElementById('userCoinsAmount').textContent;
    console.log('Coins before claim:', currentCoins);
    
    // Disable UI elements during processing
    disableClaimElements();
    
    // Immediately update UI to show claimed status
    // Change claimable day to claimed status
    const claimableDay = document.querySelector('.day-column.claimable');
    if (claimableDay) {
        claimableDay.classList.remove('claimable', 'breathing');
        claimableDay.classList.add('claimed');
        
        const claimedOverlay = document.createElement('div');
        claimedOverlay.className = 'claimed-overlay';
        claimedOverlay.textContent = 'CLAIMED';
        claimableDay.querySelector('.coin-container').appendChild(claimedOverlay);
        console.log('UI updated to show claimed status');
    }
    
    // Immediately remove claim button
    const claimButton = document.querySelector('#dailyClaimModal .btn-success');
    if (claimButton) {
        claimButton.remove();
        console.log('Claim button removed');
    }
    
    // Send AJAX request to claim reward
    console.log('Sending claim request to server');
    fetch('/MizukiForest/ClaimLoginRewardServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: userId,
            amount: amount
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Claim response received:', data);
        
        if (data.success) {
            // Update the coins display in the modal
            document.getElementById('userCoinsAmount').textContent = data.updatedCoins;
            console.log('Coins updated in UI after claim:', data.updatedCoins);
            
            // Show success notification
            showNotification('Rewards claimed successfully!', 'success');
            
            // Properly hide the modal after a delay
            console.log('Scheduling modal close after successful claim');
            setTimeout(function() {
                const dailyClaimModal = document.getElementById('dailyClaimModal');
                if (dailyClaimModal) {
                    const modalInstance = bootstrap.Modal.getInstance(dailyClaimModal);
                    if (modalInstance) {
                        console.log('Closing modal after successful claim');
                        modalInstance.hide(); // This will trigger the hidden.bs.modal event
                    } else {
                        console.log('Modal instance not found for auto-close');
                    }
                } else {
                    console.log('Modal element not found for auto-close');
                }
            }, 2000);
        } else {
            // If server reports failure, don't reset UI (keep it as claimed)
            // Just show the error notification
            console.warn('Server reported claim failure but UI will remain as claimed');
            showNotification('Failed to claim rewards, but your claim will be processed.', 'warning');
        }
    })
    .catch(error => {
        // If error occurs, reset claim flag to allow retrying
        claimProcessed = false;
        console.error('Error claiming reward:', error);
        showNotification('An error occurred while claiming rewards.', 'error');
    });
}

// Helper function to show notifications
function showNotification(message, type) {
    console.log('Showing notification:', message, 'type:', type);
    // Check if notification container exists
    let notificationContainer = document.querySelector('.notification-container');
    if (!notificationContainer) {
        // Create container if it doesn't exist
        notificationContainer = document.createElement('div');
        notificationContainer.className = 'notification-container';
        document.body.appendChild(notificationContainer);
        console.log('Created notification container');
    }
    
    // Create notification
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    // Add to container
    notificationContainer.appendChild(notification);
    
    // Show notification
    setTimeout(() => {
        notification.classList.add('show');
    }, 10);
    
    // Remove notification after delay
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => {
            notification.remove();
            console.log('Notification removed');
        }, 500);
    }, 3000);
}