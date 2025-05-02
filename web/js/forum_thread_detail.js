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