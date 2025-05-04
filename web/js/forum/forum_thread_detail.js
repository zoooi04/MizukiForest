// Function to toggle comment replies
function toggleReplies(commentId) {
    const repliesContainer = document.getElementById(commentId + '-replies');
    const toggleButton = repliesContainer.previousElementSibling;
    const toggleIcon = toggleButton.querySelector('i');
    const toggleText = toggleButton.querySelector('span');

    if (repliesContainer.classList.contains('replies-collapsed')) {
        // Show replies
        repliesContainer.classList.remove('replies-collapsed');
        toggleIcon.classList.remove('fa-chevron-down');
        toggleIcon.classList.add('fa-chevron-up');
        toggleText.textContent = toggleText.textContent.replace('Show', 'Hide');
    } else {
        // Hide replies
        repliesContainer.classList.add('replies-collapsed');
        toggleIcon.classList.remove('fa-chevron-up');
        toggleIcon.classList.add('fa-chevron-down');
        toggleText.textContent = toggleText.textContent.replace('Hide', 'Show');
    }
}

// DOM Content Loaded Event Listener
document.addEventListener('DOMContentLoaded', function() {
    // No need to add event listeners for toggle-replies
    // as they already have an onclick attribute in the HTML
    // that calls toggleReplies directly

    // Initialize Reply Buttons
    const replyButtons = document.querySelectorAll('.comment-action.reply');
    replyButtons.forEach(button => {
        button.addEventListener('click', function() {
            toggleReplyForm(this);
        });
    });

    // Initialize Edit Buttons
    const editButtons = document.querySelectorAll('.action-btn:has(i.fa-pencil-alt), .comment-action:has(i.fa-pencil-alt)');
    editButtons.forEach(button => {
        button.addEventListener('click', function() {
            openEditPopup(this);
        });
    });

    // Initialize Delete Buttons
    const deleteButtons = document.querySelectorAll('.action-btn:has(i.fa-trash), .comment-action:has(i.fa-trash)');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            openDeletePopup(this);
        });
    });

    // Initialize Report Buttons
    const reportButtons = document.querySelectorAll('.action-btn:has(i.fa-exclamation-circle), .comment-action:has(i.fa-exclamation-circle)');
    reportButtons.forEach(button => {
        button.addEventListener('click', function() {
            openReportPopup(this);
        });
    });

    // Close popup when clicking outside
    document.addEventListener('click', function(event) {
        const popups = document.querySelectorAll('.popup-container');
        popups.forEach(popup => {
            if (event.target === popup) {
                closePopup(popup);
            }
        });
    });
});

// Function to open edit popup
function openEditPopup(button) {
    // Determine if this is a thread edit or comment edit
    const isThreadEdit = button.closest('.thread-actions') !== null;
    
    // Get current content
    let title = '';
    let description = '';
    let category = 'General';
    let imageSrc = '';
    
    if (isThreadEdit) {
        const threadContainer = button.closest('.thread-container');
        title = threadContainer.querySelector('h1').textContent;
        description = threadContainer.querySelector('.thread-content p').textContent;
        const categoryText = threadContainer.querySelector('.thread-meta span:nth-child(3)').textContent;
        category = categoryText.replace('Category: ', '');
        const threadImage = threadContainer.querySelector('.thread-image');
        if (threadImage) {
            imageSrc = threadImage.src;
        }
    } else {
        // For comment edit, we only need the comment text
        description = button.closest('.comment-content').querySelector('.comment-text').textContent;
    }

    // Create popup HTML
    let popupHTML = `
        <div class="popup-container">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>${isThreadEdit ? 'Edit Thread' : 'Edit Comment'}</h2>
                    <span class="close-popup">&times;</span>
                </div>
                <div class="popup-body">
                    <div class="user-info">
                        <img src="${button.closest('.thread-container').querySelector('.user-avatar').src}" alt="User Avatar" class="user-avatar-small">
                        <span>${button.closest('.thread-container').querySelector('.font-bold, .comment-author').textContent}</span>
                    </div>
    `;

    if (isThreadEdit) {
        popupHTML += `
                    <div class="form-group">
                        <label for="edit-title">Title</label>
                        <input type="text" id="edit-title" value="${title}">
                    </div>
                    <div class="form-group">
                        <label for="edit-category">Category</label>
                        <select id="edit-category">
                            <option value="General" ${category === 'General' ? 'selected' : ''}>General</option>
                            <option value="Question" ${category === 'Question' ? 'selected' : ''}>Question</option>
                            <option value="Discussion" ${category === 'Discussion' ? 'selected' : ''}>Discussion</option>
                            <option value="Announcement" ${category === 'Announcement' ? 'selected' : ''}>Announcement</option>
                        </select>
                    </div>
        `;
    }

    popupHTML += `
                    <div class="form-group">
                        <label for="edit-description">${isThreadEdit ? 'Description' : 'Comment'}</label>
                        <textarea id="edit-description">${description}</textarea>
                    </div>
    `;

    if (isThreadEdit) {
        popupHTML += `
                    <div class="form-group">
                        <label for="edit-image">Image</label>
                        <div class="image-upload-container">
                            ${imageSrc ? `<img src="${imageSrc}" alt="Thread Image" class="preview-image">` : ''}
                            <input type="file" id="edit-image" accept="image/*">
                            <label for="edit-image" class="file-upload-btn">Choose File</label>
                        </div>
                    </div>
        `;
    }

    popupHTML += `
                </div>
                <div class="popup-footer">
                    <button class="btn-cancel">Cancel</button>
                    <button class="btn-confirm">Save Changes</button>
                </div>
            </div>
        </div>
    `;

    // Append popup to body
    document.body.insertAdjacentHTML('beforeend', popupHTML);

    // Add event listeners
    const popup = document.querySelector('.popup-container');
    
    // Close button event
    popup.querySelector('.close-popup').addEventListener('click', function() {
        closePopup(popup);
    });
    
    // Cancel button event
    popup.querySelector('.btn-cancel').addEventListener('click', function() {
        closePopup(popup);
    });
    
    // Save button event
    popup.querySelector('.btn-confirm').addEventListener('click', function() {
        // Here you would normally save the changes
        // For now, we'll just close the popup
        alert('Changes saved successfully!');
        closePopup(popup);
    });

    // Image upload preview (if it's a thread edit)
    if (isThreadEdit) {
        const imageInput = popup.querySelector('#edit-image');
        imageInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    let previewImage = popup.querySelector('.preview-image');
                    if (!previewImage) {
                        const imageContainer = popup.querySelector('.image-upload-container');
                        imageContainer.insertAdjacentHTML('afterbegin', `<img src="${e.target.result}" alt="Thread Image" class="preview-image">`);
                    } else {
                        previewImage.src = e.target.result;
                    }
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    }
}

// Function to open delete popup
function openDeletePopup(button) {
    // Determine if this is a thread delete or comment delete
    const isThreadDelete = button.closest('.thread-actions') !== null;
    
    // Create popup HTML
    const popupHTML = `
        <div class="popup-container">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>${isThreadDelete ? 'Delete Thread' : 'Delete Comment'}</h2>
                    <span class="close-popup">&times;</span>
                </div>
                <div class="popup-body">
                    <p>Are you sure you want to delete this ${isThreadDelete ? 'thread' : 'comment'}? This action cannot be undone.</p>
                </div>
                <div class="popup-footer">
                    <button class="btn-cancel">Cancel</button>
                    <button class="btn-confirm btn-danger">Yes, Delete</button>
                </div>
            </div>
        </div>
    `;

    // Append popup to body
    document.body.insertAdjacentHTML('beforeend', popupHTML);

    // Add event listeners
    const popup = document.querySelector('.popup-container');
    
    // Close button event
    popup.querySelector('.close-popup').addEventListener('click', function() {
        closePopup(popup);
    });
    
    // Cancel button event
    popup.querySelector('.btn-cancel').addEventListener('click', function() {
        closePopup(popup);
    });
    
    // Delete button event
    popup.querySelector('.btn-confirm').addEventListener('click', function() {
        // Here you would normally delete the thread/comment
        // For now, we'll just close the popup
        alert('Deleted successfully!');
        closePopup(popup);
    });
}

// Function to open report popup
function openReportPopup(button) {
    // Determine if this is a thread report or comment report
    const isThreadReport = button.closest('.thread-actions') !== null;
    
    // Create popup HTML
    const popupHTML = `
        <div class="popup-container">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>Report ${isThreadReport ? 'Thread' : 'Comment'}</h2>
                    <span class="close-popup">&times;</span>
                </div>
                <div class="popup-body">
                    <div class="form-group">
                        <label for="report-reason">Reason for Report</label>
                        <select id="report-reason">
                            <option value="">-- Select a Reason --</option>
                            <option value="spam">Spam</option>
                            <option value="harassment">Harassment</option>
                            <option value="inappropriate">Inappropriate Content</option>
                            <option value="offensive">Offensive Language</option>
                            <option value="other">Other</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="report-details">Additional Details</label>
                        <textarea id="report-details" placeholder="Please provide more details..."></textarea>
                    </div>
                </div>
                <div class="popup-footer">
                    <button class="btn-cancel">Cancel</button>
                    <button class="btn-confirm">Submit Report</button>
                </div>
            </div>
        </div>
    `;

    // Append popup to body
    document.body.insertAdjacentHTML('beforeend', popupHTML);

    // Add event listeners
    const popup = document.querySelector('.popup-container');
    
    // Close button event
    popup.querySelector('.close-popup').addEventListener('click', function() {
        closePopup(popup);
    });
    
    // Cancel button event
    popup.querySelector('.btn-cancel').addEventListener('click', function() {
        closePopup(popup);
    });
    
    // Submit button event
    popup.querySelector('.btn-confirm').addEventListener('click', function() {
        const reason = popup.querySelector('#report-reason').value;
        if (!reason) {
            alert('Please select a reason for your report.');
            return;
        }
        
        // Here you would normally submit the report
        // For now, we'll just close the popup
        alert('Report submitted successfully!');
        closePopup(popup);
    });
}

// Function to close popup
function closePopup(popup) {
    popup.remove();
}

// Function to toggle reply form
function toggleReplyForm(replyButton) {
    const commentContent = replyButton.closest('.comment-content');
    const existingForm = commentContent.querySelector('.reply-form');
    
    // If there's already a reply form, remove it
    if (existingForm) {
        existingForm.remove();
        return;
    }
    
    // Create reply form HTML
    const replyFormHTML = `
        <div class="reply-form">
            <textarea placeholder="Write your reply..."></textarea>
            <div class="reply-form-actions">
                <button class="btn-cancel-reply">Cancel</button>
                <button class="btn-send-reply">Reply</button>
            </div>
        </div>
    `;
    
    // Find the right place to insert the form
    // If there's a toggle-replies button, insert before that
    const toggleReplies = commentContent.querySelector('.toggle-replies');
    if (toggleReplies) {
        toggleReplies.insertAdjacentHTML('beforebegin', replyFormHTML);
    } else {
        // Otherwise append to the end of comment content
        commentContent.insertAdjacentHTML('beforeend', replyFormHTML);
    }
    
    // Add event listeners to the newly created buttons
    const newForm = commentContent.querySelector('.reply-form');
    
    // Cancel button removes the form
    newForm.querySelector('.btn-cancel-reply').addEventListener('click', function() {
        newForm.remove();
    });
    
    // Send button handles the reply submission
    newForm.querySelector('.btn-send-reply').addEventListener('click', function() {
        const replyText = newForm.querySelector('textarea').value.trim();
        if (replyText) {
            submitReply(commentContent, replyText);
            newForm.remove();
        } else {
            alert('Please enter a reply.');
        }
    });
    
    // Focus the textarea
    newForm.querySelector('textarea').focus();
}

// Function to submit a reply
function submitReply(commentContent, replyText) {
    // Get the replies container or create one if it doesn't exist
    let repliesContainer = commentContent.querySelector('.replies-container');
    
    if (!repliesContainer) {
        // If there's no replies container, create one
        const commentId = 'comment' + Date.now(); // Generate a unique ID
        
        // Create toggle button first
        commentContent.insertAdjacentHTML('beforeend', `
            <div class="toggle-replies" onclick="toggleReplies('${commentId}')">
                <i class="fas fa-chevron-down"></i>
                <span>Show replies (1)</span>
            </div>
            <div id="${commentId}-replies" class="replies-container replies-collapsed">
            </div>
        `);
        
        repliesContainer = commentContent.querySelector('.replies-container');
    } else {
        // If the container exists but is collapsed, expand it
        if (repliesContainer.classList.contains('replies-collapsed')) {
            const toggleButton = commentContent.querySelector('.toggle-replies');
            toggleReplies(repliesContainer.id.replace('-replies', ''));
        }
        
        // Update the reply count
        const toggleText = commentContent.querySelector('.toggle-replies span');
        const currentCount = parseInt(toggleText.textContent.match(/\d+/)[0]);
        toggleText.textContent = toggleText.textContent.replace(
            `(${currentCount})`, 
            `(${currentCount + 1})`
        );
    }
    
    // Add the new reply
    const newReplyHTML = `
        <div class="comment-item indented-comment">
            <img src="${document.querySelector('.comment-avatar').src}" alt="User Avatar" class="comment-avatar">
            <div class="comment-content">
                <div class="comment-author">Username</div>
                <div class="comment-text">${replyText}</div>
                <div class="comment-actions">
                    <div class="comment-action">
                        <i class="fas fa-thumbs-up"></i>
                        <span>0</span>
                    </div>
                    <div class="comment-action">
                        <i class="fas fa-thumbs-down"></i>
                        <span>0</span>
                    </div>
                    <div class="comment-action">
                        <i class="fas fa-pencil-alt"></i>
                        <span>Edit</span>
                    </div>
                    <div class="comment-action">
                        <i class="fas fa-trash"></i>
                        <span>Delete</span>
                    </div>
                    <div class="comment-action">
                        <i class="fas fa-exclamation-circle"></i>
                        <span>Report</span>
                    </div>
                </div>
            </div>
        </div>
    `;
    
    repliesContainer.insertAdjacentHTML('beforeend', newReplyHTML);
    
    // Add event listeners to the new comment's buttons
    const newComment = repliesContainer.lastElementChild;
    
    // Edit button
    newComment.querySelector('.comment-action:has(i.fa-pencil-alt)').addEventListener('click', function() {
        openEditPopup(this);
    });
    
    // Delete button
    newComment.querySelector('.comment-action:has(i.fa-trash)').addEventListener('click', function() {
        openDeletePopup(this);
    });
    
    // Report button
    newComment.querySelector('.comment-action:has(i.fa-exclamation-circle)').addEventListener('click', function() {
        openReportPopup(this);
    });
    
    // Show success message
    alert('Reply posted successfully!');
}