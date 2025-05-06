// Declare global variables for currentUser and selectedThreadID
const currentUser = {
    username: document.querySelector('meta[name="current-username"]').content,
    userId: document.querySelector('meta[name="current-userid"]').content
};

const selectedThreadID = document.querySelector('meta[name="selected-threadid"]').content;

// Ensure commentIdReplyTo is declared and retrieved correctly
const commentIdReplyTo = document.querySelector('meta[name="comment-id-reply-to"]')?.content || "undefined";

$(document).ready(function() {
    // Initialize Reply Buttons
    const replyButtons = document.querySelectorAll('.comment-action.reply');
    replyButtons.forEach(button => {
        button.addEventListener('click', function () {
            toggleReplyForm(this);
        });
    });

    // Initialize Edit Buttons
    const editButtons = document.querySelectorAll('.action-btn:has(i.bi-pencil-square), .comment-action:has(i.bi-pencil-square)');
    editButtons.forEach(button => {
        button.addEventListener('click', function () {
            openEditPopup(this);
        });
    });

    // Initialize Delete Buttons
    const deleteButtons = document.querySelectorAll('.action-btn:has(i.bi-trash), .comment-action:has(i.bi-trash)');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            openDeletePopup(this);
        });
    });

    // Initialize Report Buttons
    const reportButtons = document.querySelectorAll('.action-btn:has(i.bi-exclamation-circle), .comment-action:has(i.bi-exclamation-circle)');
    reportButtons.forEach(button => {
        button.addEventListener('click', function () {
            openReportPopup(this);
        });
    });

    // Initialize all vote buttons (thread, comments, and replies)
    const allVoteButtons = document.querySelectorAll('.thread-actions .action-btn, .comment-item .action-btn');
    allVoteButtons.forEach(button => {
        button.addEventListener('click', function() {
            handleVote(this);
        });
    });
});

// Retrieve categories from meta tag and populate the dropdown
function openEditPopup(button) {
    const isThreadEdit = button.closest('.thread-actions') !== null;
    let title = '', description = '', category = '', commentId = '', threadId = '';

    if (isThreadEdit) {
        const threadContainer = button.closest('.thread-container');
        title = threadContainer.querySelector('h1').textContent;
        description = threadContainer.querySelector('.thread-content p').textContent;
        category = threadContainer.querySelector('.thread-meta span:nth-child(3)').textContent.replace('Category: ', '');
        threadId = document.querySelector('meta[name="selected-threadid"]').content;
    } else {
        const commentContainer = button.closest('.comment-content');
        description = commentContainer.querySelector('.comment-text').textContent;
        commentId = commentContainer.closest('.comment-item').dataset.commentId;
        threadId = document.querySelector('meta[name="selected-threadid"]').content;
    }

    // Retrieve categories from meta tag
    const categoriesMeta = document.querySelector('meta[name="forumThreadCategoryList"]').content;
    const categories = JSON.parse(categoriesMeta);

    const categoryOptions = categories.map(cat => `
        <option value="${cat.threadcategoryid}" ${cat.threadcategoryname === category ? 'selected' : ''}>
            ${cat.threadcategoryname}
        </option>
    `).join('');

    const popupHTML = `
        <form class="popup-container" action="../../EditForumThreadCommentServlet" method="POST">
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
                            <label for="edit-description">Description</label>
                            <textarea id="edit-description" name="description" required>${description}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="edit-category">Category</label>
                            <select id="edit-category" name="category">
                                ${categoryOptions}
                            </select>
                        </div>
                    ` : `
                        <div class="form-group">
                            <label for="edit-description">Comment</label>
                            <textarea id="edit-description" name="description" required>${description}</textarea>
                        </div>
                    `}
                    <input type="hidden" name="threadId" value="${threadId}">
                    ${!isThreadEdit ? `
                        <input type="hidden" name="commentId" value="${commentId}">
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
    const threadId = isThreadDelete ? document.querySelector('meta[name="selected-threadid"]').content : null;
    const commentId = !isThreadDelete ? button.closest('.comment-item').dataset.commentId : null;

    const popupHTML = `
        <form class="popup-container" action="../../DeleteForumThreadCommentServlet" method="POST">
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
                ${isThreadDelete ? `<input type="hidden" name="threadId" value="${threadId}">` : ''}
                ${!isThreadDelete ? `<input type="hidden" name="commentId" value="${commentId}">` : ''}
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
    const threadId = document.querySelector('meta[name="selected-threadid"]').content;
    if (!threadId) {
        console.error("Thread ID is missing in the page metadata.");
        return;
    }
    const commentId = !isThreadReport ? button.closest('.comment-item').dataset.commentId : null;

    const popupHTML = `
        <form class="popup-container" action="../../AddForumReportContentServlet" method="POST">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>Report ${isThreadReport ? 'Thread' : 'Comment'}</h2>
                    <button type="button" class="close-popup">&times;</button>
                </div>
                <div class="popup-body">
                    <div class="form-group">
                        <label for="report-details">Additional Details</label>
                        <textarea id="report-details" name="reportReason" placeholder="Please provide more details..." required></textarea>
                    </div>
                </div>
                <div class="popup-footer">
                    <button type="button" class="btn-cancel">Cancel</button>
                    <button type="submit" class="btn-confirm">Submit Report</button>
                </div>
                <input type="hidden" name="userId" value="${document.querySelector('meta[name="current-userid"]').content}">
                <input type="hidden" name="threadId" value="${threadId}">
                ${commentId ? `<input type="hidden" name="threadCommentId" value="${commentId}">` : ''}
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
    newForm.querySelector('.btn-cancel-reply').addEventListener('click', function () {
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
            <div class="comment-item indented-comment" data-comment-id="${data.commentId}">
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
                newComment.querySelector('.comment-action:has(i.bi-pencil-square)').addEventListener('click', function () {
                    openEditPopup(this);
                });

                // Delete button
                newComment.querySelector('.comment-action:has(i.bi-trash)').addEventListener('click', function () {
                    openDeletePopup(this);
                });

                // Report button
                newComment.querySelector('.comment-action:has(i.bi-exclamation-circle)').addEventListener('click', function () {
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

// Function to toggle replies
function toggleReplies(commentId) {
    const repliesContainer = document.getElementById(commentId + '-replies');
    const toggleButton = repliesContainer.previousElementSibling;
    
    if (repliesContainer.classList.contains('replies-collapsed')) {
        repliesContainer.classList.remove('replies-collapsed');
        toggleButton.querySelector('i').classList.remove('fa-chevron-down');
        toggleButton.querySelector('i').classList.add('fa-chevron-up');
    } else {
        repliesContainer.classList.add('replies-collapsed');
        toggleButton.querySelector('i').classList.remove('fa-chevron-up');
        toggleButton.querySelector('i').classList.add('fa-chevron-down');
    }
}

// Add click outside popup functionality
document.addEventListener('click', function(event) {
    const popup = document.querySelector('.popup-container');
    if (popup && !event.target.closest('.popup-content') && !event.target.closest('.action-btn') && !event.target.closest('.comment-action')) {
        closePopup(popup);
    }
});

// Add event listeners for upvote and downvote buttons
function getServletUrl() {
    // Get the current URL path
    const pathSegments = window.location.pathname.split('/');
    // Find the index of the application root (MizukiForest)
    const rootIndex = pathSegments.findIndex(segment => segment === 'MizukiForest');
    
    if (rootIndex === -1) {
        console.error('Unable to determine application root path');
        return null;
    }

    // Construct the base URL
    const baseUrl = window.location.origin + pathSegments.slice(0, rootIndex + 1).join('/');
    const servletUrl = `${baseUrl}/UpdateForumVoteTypeServlet`;
    
    // Validate URL format
    try {
        new URL(servletUrl);
        return servletUrl;
    } catch (e) {
        console.error('Invalid URL constructed:', servletUrl);
        return null;
    }
}

function handleVote(button) {
    const parentContainer = button.closest('.comment-item, .thread-actions');
    const isThreadVote = button.closest('.thread-actions') !== null;
    const isCommentVote = button.closest('.comment-item:not(.indented-comment)') !== null;
    const isReplyVote = button.closest('.indented-comment') !== null;
    
    // Check if the button is already in the active state (filled)
    const isUpvote = button.classList.contains('upvote');
    
    let data = {
        voteType: isUpvote ? "upvote" : "downvote"
    };

    // Add appropriate ID based on vote type
    if (isThreadVote) {
        data.threadId = selectedThreadID;
        data.type = "thread";
    } else if (isCommentVote || isReplyVote) {
        // Get the comment ID from the closest comment-item, whether it's a reply or main comment
        const commentItem = button.closest('.comment-item');
        data.commentId = commentItem.dataset.commentId;
        data.type = "comment";
    }

    // Get and validate servlet URL
    const servletUrl = getServletUrl();
    if (!servletUrl) {
        alert('Error: Unable to determine the correct URL for vote processing');
        return;
    }

    // Make AJAX call with validated URL
    $.ajax({
        url: servletUrl,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            try {
                // Parse the response if it's a string
                const result = typeof response === 'string' ? JSON.parse(response) : response;
                
                if (!result) {
                    throw new Error('Empty response received');
                }

                // Update UI based on response
                const upvoteButton = parentContainer.querySelector('.action-btn.upvote');
                const downvoteButton = parentContainer.querySelector('.action-btn.downvote');
                
                if (!upvoteButton || !downvoteButton) {
                    throw new Error('Vote buttons not found');
                }
                
                // Update vote counts
                upvoteButton.querySelector('span').textContent = result.upvotes;
                downvoteButton.querySelector('span').textContent = result.downvotes;

                // Get the icon elements
                const upvoteIcon = upvoteButton.querySelector('i');
                const downvoteIcon = downvoteButton.querySelector('i');

                // First, remove fill from both icons
                upvoteIcon.classList.remove('bi-hand-thumbs-up-fill');
                upvoteIcon.classList.add('bi-hand-thumbs-up');
                downvoteIcon.classList.remove('bi-hand-thumbs-down-fill');
                downvoteIcon.classList.add('bi-hand-thumbs-down');

                // Then update the appropriate icon based on the user's vote
                if (result.userVoteType === "upvote") {
                    upvoteIcon.classList.remove('bi-hand-thumbs-up');
                    upvoteIcon.classList.add('bi-hand-thumbs-up-fill');
                } else if (result.userVoteType === "downvote") {
                    downvoteIcon.classList.remove('bi-hand-thumbs-down');
                    downvoteIcon.classList.add('bi-hand-thumbs-down-fill');
                }
                // If userVoteType is null or undefined, both icons remain unfilled
                
            } catch (e) {
                console.error('Error processing response:', e);
                if (response.error) {
                    alert('Error: ' + response.error);
                } else {
                    alert('Error processing vote response. Please try again.');
                }
            }
        },
        error: function(xhr, status, error) {
            console.error('Error processing vote:', error);
            console.error('Status:', status);
            console.error('Response:', xhr.responseText);
            
            try {
                const errorResponse = JSON.parse(xhr.responseText);
                if (errorResponse.error) {
                    alert('Error: ' + errorResponse.error);
                } else {
                    alert('Failed to process vote. Please try again later.');
                }
            } catch (e) {
                alert('Failed to process vote. Please try again later.');
            }
        }
    });
}