// Declare global variables for currentUser and selectedThreadID
const currentUser = {
    username: document.querySelector('meta[name="current-username"]').content,
    userId: document.querySelector('meta[name="current-userid"]').content
};

const selectedThreadID = document.querySelector('meta[name="selected-threadid"]').content;

// Ensure commentIdReplyTo is declared and retrieved correctly
const commentIdReplyTo = document.querySelector('meta[name="comment-id-reply-to"]')?.content || "undefined";

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
    const editButtons = document.querySelectorAll('.action-btn:has(i.bi-pencil-square), .comment-action:has(i.bi-pencil-square)');
    editButtons.forEach(button => {
        button.addEventListener('click', function() {
            openEditPopup(this);
        });
    });

    // Initialize Delete Buttons
    const deleteButtons = document.querySelectorAll('.action-btn:has(i.bi-trash), .comment-action:has(i.bi-trash)');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            openDeletePopup(this);
        });
    });

    // Initialize Report Buttons
    const reportButtons = document.querySelectorAll('.action-btn:has(i.bi-exclamation-circle), .comment-action:has(i.bi-exclamation-circle)');
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

// Modify the edit popup to use a form
function openEditPopup(button) {
    const isThreadEdit = button.closest('.thread-actions') !== null;
    let title = '', description = '', category = 'General', imageSrc = '';

    if (isThreadEdit) {
        const threadContainer = button.closest('.thread-container');
        title = threadContainer.querySelector('h1').textContent;
        description = threadContainer.querySelector('.thread-content p').textContent;
        const categoryText = threadContainer.querySelector('.thread-meta span:nth-child(3)').textContent;
        category = categoryText.replace('Category: ', '');
        const threadImage = threadContainer.querySelector('.thread-image');
        if (threadImage) imageSrc = threadImage.src;
    } else {
        description = button.closest('.comment-content').querySelector('.comment-text').textContent;
    }

    const popupHTML = `
        <form class="popup-container" action="/editServlet" method="POST">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>${isThreadEdit ? 'Edit Thread' : 'Edit Comment'}</h2>
                    <button type="button" class="close-popup">&times;</button>
                </div>
                <div class="popup-body">
                    ${isThreadEdit ? `
                        <div class="form-group">
                            <label for="edit-title">Title</label>
                            <input type="text" id="edit-title" name="title" value="${title}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-category">Category</label>
                            <select id="edit-category" name="category">
                                <option value="General" ${category === 'General' ? 'selected' : ''}>General</option>
                                <option value="Question" ${category === 'Question' ? 'selected' : ''}>Question</option>
                                <option value="Discussion" ${category === 'Discussion' ? 'selected' : ''}>Discussion</option>
                                <option value="Announcement" ${category === 'Announcement' ? 'selected' : ''}>Announcement</option>
                            </select>
                        </div>
                    ` : ''}
                    <div class="form-group">
                        <label for="edit-description">${isThreadEdit ? 'Description' : 'Comment'}</label>
                        <textarea id="edit-description" name="description" required>${description}</textarea>
                    </div>
                    ${isThreadEdit ? `
                        <div class="form-group">
                            <label for="edit-image">Image</label>
                            <input type="file" id="edit-image" name="image" accept="image/*">
                        </div>
                    ` : ''}
                </div>
                <div class="popup-footer">
                    <button type="button" class="btn-cancel">Cancel</button>
                    <button type="submit" class="btn-confirm">Save Changes</button>
                </div>
            </div>
        </form>
    `;

    document.body.insertAdjacentHTML('beforeend', popupHTML);
    const popup = document.querySelector('.popup-container');
    popup.querySelector('.close-popup').addEventListener('click', () => closePopup(popup));
    popup.querySelector('.btn-cancel').addEventListener('click', () => closePopup(popup));
}

// Modify the delete popup to use a form
function openDeletePopup(button) {
    const isThreadDelete = button.closest('.thread-actions') !== null;
    const popupHTML = `
        <form class="popup-container" action="/deleteServlet" method="POST">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>${isThreadDelete ? 'Delete Thread' : 'Delete Comment'}</h2>
                    <button type="button" class="close-popup">&times;</button>
                </div>
                <div class="popup-body">
                    <p>Are you sure you want to delete this ${isThreadDelete ? 'thread' : 'comment'}? This action cannot be undone.</p>
                </div>
                <div class="popup-footer">
                    <button type="button" class="btn-cancel">Cancel</button>
                    <button type="submit" class="btn-confirm btn-danger">Yes, Delete</button>
                </div>
            </div>
        </form>
    `;

    document.body.insertAdjacentHTML('beforeend', popupHTML);
    const popup = document.querySelector('.popup-container');
    popup.querySelector('.close-popup').addEventListener('click', () => closePopup(popup));
    popup.querySelector('.btn-cancel').addEventListener('click', () => closePopup(popup));
}

// Modify the report popup to use a form
function openReportPopup(button) {
    const isThreadReport = button.closest('.thread-actions') !== null;
    const popupHTML = `
        <form class="popup-container" action="/reportServlet" method="POST">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>Report ${isThreadReport ? 'Thread' : 'Comment'}</h2>
                    <button type="button" class="close-popup">&times;</button>
                </div>
                <div class="popup-body">
                    <div class="form-group">
                        <label for="report-details">Additional Details</label>
                        <textarea id="report-details" name="details" placeholder="Please provide more details..." required></textarea>
                    </div>
                </div>
                <div class="popup-footer">
                    <button type="button" class="btn-cancel">Cancel</button>
                    <button type="submit" class="btn-confirm">Submit Report</button>
                </div>
            </div>
        </form>
    `;

    document.body.insertAdjacentHTML('beforeend', popupHTML);
    const popup = document.querySelector('.popup-container');
    popup.querySelector('.close-popup').addEventListener('click', () => closePopup(popup));
    popup.querySelector('.btn-cancel').addEventListener('click', () => closePopup(popup));
}

// Function to close popup
function closePopup(popup) {
    popup.remove();
}

// Function to toggle reply form
function toggleReplyForm(replyButton) {
    const commentContent = replyButton.closest('.comment-content');
    const existingForm = commentContent.querySelector('.reply-form');

    // Set the commentIdReplyTo value on the reply button's parent element
    replyButton.closest('.comment-content').setAttribute('data-comment-id', replyButton.dataset.commentId);

    // If there's already a reply form, remove it
    if (existingForm) {
        existingForm.remove();
        return;
    }

    // Modify the reply form to use a form element
    const replyFormHTML = `
        <form class="reply-form" action="../../AddForumThreadCommentServlet" method="POST">
            <div class="flex items-center mb-3">
                <img src="../../media/images/mizuki.png" alt="User Avatar" class="comment-avatar mr-2">
                <span>Replying as <strong>${currentUser.username}</strong></span>
            </div>
            <textarea name="commentDescription" placeholder="Write your reply..." required></textarea>
            <input type="hidden" name="userId" value="${currentUser.userId}">
            <input type="hidden" name="threadId" value="${selectedThreadID}">
            <input type="hidden" name="commentIdReplyTo" value="${commentContent.dataset.commentId}">
            <div class="reply-form-actions flex justify-end">
                <button type="button" class="btn-cancel-reply">Cancel</button>
                <button type="submit" class="btn-send-reply">Reply</button>
            </div>
        </form>
    `;

    // Find the right place to insert the form
    const toggleReplies = commentContent.querySelector('.toggle-replies');
    if (toggleReplies) {
        toggleReplies.insertAdjacentHTML('beforebegin', replyFormHTML);
    } else {
        commentContent.insertAdjacentHTML('beforeend', replyFormHTML);
    }

    // Add event listeners to the newly created buttons
    const newForm = commentContent.querySelector('.reply-form');

    // Cancel button removes the form
    newForm.querySelector('.btn-cancel-reply').addEventListener('click', function() {
        newForm.remove();
    });

    // Focus the textarea
    newForm.querySelector('textarea').focus();
}

// Updated submitReply function to send data to the server
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

    // Send the reply to the server
    fetch('/submitReply', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            replyText: replyText,
            parentId: commentContent.dataset.commentId, // Assuming each comment has a data-comment-id attribute
        }),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to submit reply');
        }
        return response.json();
    })
    .then(data => {
        // Add the new reply to the UI
        const newReplyHTML = `
            <div class="comment-item indented-comment">
                <img src="${document.querySelector('.comment-avatar').src}" alt="User Avatar" class="comment-avatar">
                <div class="comment-content">
                    <div class="comment-author">${data.username}</div>
                    <div class="comment-text">${data.replyText}</div>
                    <div class="comment-actions">
                        <div class="comment-action">
                            <i class="bi bi-hand-thumbs-up"></i>
                            <span>0</span>
                        </div>
                        <div class="comment-action">
                            <i class="bi bi-hand-thumbs-down"></i>
                            <span>0</span>
                        </div>
                        <div class="comment-action">
                            <i class="bi bi-pencil-square"></i>
                            <span>Edit</span>
                        </div>
                        <div class="comment-action">
                            <i class="bi bi-trash"></i>
                            <span>Delete</span>
                        </div>
                        <div class="comment-action">
                            <i class="bi bi-exclamation-circle"></i>
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
        newComment.querySelector('.comment-action:has(i.bi-pencil-square)').addEventListener('click', function() {
            openEditPopup(this);
        });

        // Delete button
        newComment.querySelector('.comment-action:has(i.bi-trash)').addEventListener('click', function() {
            openDeletePopup(this);
        });

        // Report button
        newComment.querySelector('.comment-action:has(i.bi-exclamation-circle)').addEventListener('click', function() {
            openReportPopup(this);
        });

        // Show success message
        alert('Reply posted successfully!');
    })
    .catch(error => {
        console.error('Error submitting reply:', error);
        alert('Failed to post reply. Please try again later.');
    });
}