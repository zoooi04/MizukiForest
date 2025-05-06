// Wait for DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Get the trigger button
    const visitBtn = document.getElementById('visitBtn');
    
    // Add click event listener to the trigger button
    visitBtn.addEventListener('click', function() {
        showUserSearchModal();
    });
    
    // Create search modal HTML structure
    function showUserSearchModal() {
        // Create modal container
        const modalHTML = 
            '<div class="user-forest-overlay" id="userSearchOverlay">' +
                '<div class="user-forest-container">' +
                    '<div class="user-forest-header">' +
                        '<h2>Search Friend\'s Forest</h2>' +
                        '<span class="user-forest-close">&times;</span>' +
                    '</div>' +
                    '<div class="user-forest-body">' +
                        '<div class="user-forest-form-group">' +
                            '<label for="userProfileId">Enter Friend\'s Profile ID:</label>' +
                            '<input type="text" id="userProfileId" class="user-forest-input" placeholder="Enter Profile ID">' +
                            '<div id="userForestError" class="user-forest-error"></div>' +
                        '</div>' +
                    '</div>' +
                    '<div class="user-forest-footer">' +
                        '<button id="userForestSearchBtn" class="user-forest-btn user-forest-primary-btn">Search</button>' +
                        '<button id="userForestCancelBtn" class="user-forest-btn user-forest-secondary-btn">Cancel</button>' +
                    '</div>' +
                '</div>' +
            '</div>';
        
        // Insert modal into the DOM
        const searchModalWrapper = document.createElement('div');
        searchModalWrapper.id = 'userSearchModalWrapper';
        searchModalWrapper.innerHTML = modalHTML;
        document.body.appendChild(searchModalWrapper);
        
        // Add event listeners for modal functionality
        const overlay = document.getElementById('userSearchOverlay');
        const closeBtn = document.querySelector('.user-forest-close');
        const searchBtn = document.getElementById('userForestSearchBtn');
        const cancelBtn = document.getElementById('userForestCancelBtn');
        
        // Close modal when clicking the X
        closeBtn.addEventListener('click', function() {
            closeUserSearchModal();
        });
        
        // Close modal when clicking cancel button
        cancelBtn.addEventListener('click', function() {
            closeUserSearchModal();
        });
        
        // Close modal when clicking outside
        overlay.addEventListener('click', function(e) {
            if (e.target === overlay) {
                closeUserSearchModal();
            }
        });
        
        // Search button functionality
        searchBtn.addEventListener('click', function() {
            searchUserForest();
        });
        
        // Allow pressing Enter to search
        document.getElementById('userProfileId').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchUserForest();
            }
        });
    }
    
    // Function to close the search modal
    function closeUserSearchModal() {
        const modalWrapper = document.getElementById('userSearchModalWrapper');
        if (modalWrapper) {
            document.body.removeChild(modalWrapper);
        }
    }
    
    // Function to search for friend's forest by submitting form
    function searchUserForest() {
        const profileId = document.getElementById('userProfileId').value.trim();
        const errorElement = document.getElementById('userForestError');
        
        // Validate input
        if (profileId === '') {
            errorElement.textContent = 'Profile ID cannot be empty';
            return;
        }
        
        // Clear previous error messages
        errorElement.textContent = '';
        
        // Create a form element
        const form = document.createElement('form');
        form.method = 'GET';
        form.action = '/MizukiForest/SearchForestData';
        form.style.display = 'none';
        
        // Create an input for friendId
        const input = document.createElement('input');
        input.type = 'text';
        input.name = 'friendId';
        input.value = profileId;
        
        // Append the input to the form
        form.appendChild(input);
        
        // Append the form to the body
        document.body.appendChild(form);
        
        // Submit the form
        form.submit();
        
        // Close the modal
        closeUserSearchModal();
    }
});