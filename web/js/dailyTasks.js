// Daily Tasks JavaScript

document.addEventListener('DOMContentLoaded', function () {
    // Add click event listener to the dailyTasksBtn
    const dailyTasksBtn = document.getElementById('dailyTasksBtn');
    if (dailyTasksBtn) {
        dailyTasksBtn.addEventListener('click', openDailyTasksModal);
    }
});

// Function to open the daily tasks modal
function openDailyTasksModal() {
    // Create modal container if it doesn't exist
    let modalContainer = document.getElementById('dailyTasksModalContainer');
    if (!modalContainer) {
        modalContainer = document.createElement('div');
        modalContainer.id = 'dailyTasksModalContainer';
        document.body.appendChild(modalContainer);
    }

    // Create modal HTML
    modalContainer.innerHTML = `
    <div class="daily-tasks-modal">
        <div class="daily-tasks-modal-content">
            <div class="daily-tasks-modal-header">
                <h2>Daily Tasks</h2>
                <div class="user-stats">
                    <div class="user-stat">
                        <img src="/MizukiForest/media/images/forestmisc/XP.png" alt="XP">
                        <span id="userXP">Loading...</span>
                    </div>
                    <div class="user-stat">
                        <img src="/MizukiForest/media/images/forestmisc/coin.png" alt="Coin">
                        <span id="dailyTaskUserCoins">Loading...</span>
                    </div>
                </div>
                <span class="daily-tasks-close">&times;</span>
            </div>
            <div class="daily-tasks-modal-body">
                <div class="daily-tasks-tabs">
                        <button class="daily-tasks-tab-btn active" data-tab="default">Default Tasks</button>
                        <button class="daily-tasks-tab-btn" data-tab="customisable">Customisable Tasks</button>
                    </div>
                    
                    <div class="daily-tasks-tab-content active" id="defaultTasksTab">
                        <div class="daily-tasks-loading">Loading default tasks...</div>
                        <div class="daily-tasks-list" id="defaultTasksList"></div>
                    </div>
                    
                    <div class="daily-tasks-tab-content" id="customisableTasksTab">
                        <div class="daily-tasks-customisable-header">
                        </div>
                        <div class="daily-tasks-loading">Loading customisable tasks...</div>
                        <div class="daily-tasks-list" id="customisableTasksList"></div>
                    </div>
                </div>
            </div>
        </div>
    `;
    // Show modal
    modalContainer.classList.add('active');

    // Add event listeners
    const closeBtn = modalContainer.querySelector('.daily-tasks-close');
    closeBtn.addEventListener('click', closeDailyTasksModal);

    const tabBtns = modalContainer.querySelectorAll('.daily-tasks-tab-btn');
    tabBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            switchTab(this.getAttribute('data-tab'));
        });
    });


    const editButtons = modalContainer.querySelectorAll('.daily-task-edit');
    editButtons.forEach(btn => {
        btn.addEventListener('click', function () {
            const taskItem = this.closest('.daily-task-item');
            const taskName = taskItem.querySelector('h3').innerText;
            const taskDesc = taskItem.querySelector('p').innerText;

            // If this is the placeholder task, trigger the task creation flow
            if (taskName === "Customised Task" && taskDesc === "Customised Description") {
                // When edit is clicked on a placeholder task, reload tasks after editing
                // to display a new placeholder
                loadDailyTasks();
            }

            // Note: The actual edit functionality will be implemented later
        });
    });
    loadDailyTasks();

}

// Function to close the daily tasks modal
function closeDailyTasksModal() {
    const modalContainer = document.getElementById('dailyTasksModalContainer');
    if (modalContainer) {
        modalContainer.remove();
        // Reload forest data after closing
        window.location.href = '/MizukiForest/GetUserForestData?time=' + new Date().getTime();
    }
}

// Function to switch between tabs
function switchTab(tabName) {
    const tabBtns = document.querySelectorAll('.daily-tasks-tab-btn');
    const tabContents = document.querySelectorAll('.daily-tasks-tab-content');

    // Update active tab button
    tabBtns.forEach(btn => {
        if (btn.getAttribute('data-tab') === tabName) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });

    // Update active tab content
    tabContents.forEach(content => {
        if (content.id === tabName + 'TasksTab') {
            content.classList.add('active');
        } else {
            content.classList.remove('active');
        }
    });
}

// Function to load daily tasks data
function loadDailyTasks() {
    // Make AJAX request to get tasks
    fetch('/MizukiForest/GetDailyTasksServlet', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'same-origin'
    })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'error') {
                    if (data.redirect) {
                        window.location.href = data.redirect;
                        return;
                    }
                    alert('Error: ' + data.message);
                    return;
                }

                // Display tasks
                displayTasks(data.defaultTasks, 'defaultTasksList');
                displayTasks(data.customisableTasks, 'customisableTasksList');

                // Debug: Check what values we're receiving
                console.log('User XP received:', data.userExp);
                console.log('User Coins received:', data.userCoins);

                // Update user stats - FIXED SECTION
                const userXPElement = document.getElementById('userXP');
                const userCoinsElement = document.getElementById('dailyTaskUserCoins');

                // Update XP
                if (userXPElement) {
                    userXPElement.textContent = String(data.userExp || 0);
                } else {
                    console.error('userXP element not found');
                }

                // Update Coins
                if (userCoinsElement) {
                    userCoinsElement.textContent = String(data.userCoins || 0);
                } else {
                    console.error('userCoins element not found');
                }

                // Hide loading indicators
                document.querySelectorAll('.daily-tasks-loading').forEach(loading => {
                    loading.style.display = 'none';
                });

                // Setup edit buttons after tasks are displayed
                setupEditButtons();
            })
            .catch(error => {
                console.error('Error loading tasks:', error);
                alert('Failed to load tasks. Please try again later.');
            });
}

// Function to display tasks
function displayTasks(tasks, containerId) {
    const container = document.getElementById(containerId);
    if (!container)
        return;

    container.innerHTML = '';

    if (tasks.length === 0) {
        container.innerHTML = '<div class="daily-tasks-empty">No tasks available</div>';
        return;
    }

    // For customisable tasks, special handling for placeholder
    if (containerId === 'customisableTasksList') {
        // Show all tasks without limit
        let displayedTasks = [...tasks];

        // Find if there's already a placeholder task
        const hasPlaceholder = displayedTasks.some(task =>
            task.taskName === "Customised Task" && task.taskDescription === "Customised Description");

        // If no placeholder exists, add one
        if (!hasPlaceholder) {
            // Find a template task to use for the placeholder
            const customTaskTemplate = tasks.find(task => task.isCustomisable);
            if (customTaskTemplate) {
                const placeholderTask = {
                    taskId: customTaskTemplate.taskId,
                    taskName: "Customised Task",
                    taskDescription: "Customised Description",
                    isCustomisable: true,
                    isCompletedToday: false
                };
                displayedTasks.push(placeholderTask);
            }
        }

        // Replace the tasks array with our modified list
        tasks = displayedTasks;
    }

    tasks.forEach(task => {
        const taskEl = document.createElement('div');
        taskEl.className = 'daily-task-item';
        taskEl.dataset.taskId = task.taskId;

        // Store userTaskListId if available
        if (task.userTaskListId) {
            taskEl.dataset.userTaskListId = task.userTaskListId;
        }

        // If task is already completed today, add completed class
        if (task.isCompletedToday) {
            taskEl.classList.add('completed');
        }

        let taskHTML = `
            <div class="daily-task-info">
                <h3>${task.taskName}</h3>
                <p>${task.taskDescription}</p>
            </div>
        `;

        // Show different rewards content based on completion status
        if (task.isCompletedToday) {
            taskHTML += `<div class="daily-task-rewards"><div class="daily-task-claimed">CLAIMED</div></div>`;
        } else {
            // Show rewards for all tasks regardless of limit
            taskHTML += `
            <div class="daily-task-rewards">
                <div class="daily-task-reward">
                    <img src="/MizukiForest/media/images/forestmisc/XP.png" alt="XP">
                    <span>100</span>
                </div>
                <div class="daily-task-reward">
                    <img src="/MizukiForest/media/images/forestmisc/coin.png" alt="Coin">
                    <span>100</span>
                </div>
            </div>
        `;
        }

        taskHTML += `<div class="daily-task-actions">
                <label class="daily-task-checkbox">
                    <input type="checkbox" onchange="completeTask('${task.taskId}', this)" ${task.isCompletedToday ? 'checked disabled' : ''}>
                    <span class="checkmark"></span>
                </label>
        `;

        // Add edit button for customisable tasks
        if (containerId === 'customisableTasksList') {
            taskHTML += `<button class="daily-task-edit"><i class="fas fa-pencil-alt"></i></button>`;
        }

        taskHTML += `</div>`;
        taskEl.innerHTML = taskHTML;
        container.appendChild(taskEl);
    });
}


// Function to handle edit button click
function setupEditButtons() {
    const editButtons = document.querySelectorAll('.daily-task-edit');
    editButtons.forEach(btn => {
        btn.addEventListener('click', function () {
            const taskItem = this.closest('.daily-task-item');
            const taskId = taskItem.dataset.taskId;
            const taskName = taskItem.querySelector('h3').innerText;
            const taskDesc = taskItem.querySelector('p').innerText;

            // Check if this is the placeholder task
            const isPlaceholder = taskName === "Customised Task" && taskDesc === "Customised Description";
            openEditTaskModal(taskId, taskName, taskDesc, isPlaceholder);
        });
    });
}

// Function to open the edit task modal
function openEditTaskModal(taskId, currentName, currentDesc, isPlaceholder) {
    // Create modal container if it doesn't exist
    let editModalContainer = document.getElementById('editTaskModalContainer');
    if (editModalContainer) {
        editModalContainer.remove();
    }

    editModalContainer = document.createElement('div');
    editModalContainer.id = 'editTaskModalContainer';
    editModalContainer.className = 'edit-task-modal-container';

    // Set initial values for form
    const initialName = isPlaceholder ? '' : currentName;
    const initialDesc = isPlaceholder ? '' : currentDesc;

    // Get the userTaskListId from the task item when editing
    const taskItem = document.querySelector(`.daily-task-item[data-task-id="${taskId}"]`);
    const userTaskListId = isPlaceholder ? '' : (taskItem?.dataset.userTaskListId || '');

    // Create modal HTML - add a userTaskListId hidden field for backend tracking
    editModalContainer.innerHTML = `
        <div class="edit-task-modal">
            <div class="edit-task-modal-content">
                <div class="edit-task-modal-header">
                    <h3>${isPlaceholder ? 'Create' : 'Edit'} Custom Task</h3>
                    <span class="edit-task-close">&times;</span>
                </div>
                <div class="edit-task-modal-body">
                    <div class="edit-task-form">
                        <div class="edit-task-form-group">
                            <label for="taskName">Task Name:</label>
                            <input type="text" id="taskName" value="${initialName}" placeholder="Enter task name" maxlength="100">
                            <div class="edit-task-error" id="taskNameError"></div>
                        </div>
                        <div class="edit-task-form-group">
                            <label for="taskDescription">Task Description:</label>
                            <textarea id="taskDescription" placeholder="Enter task description" maxlength="300">${initialDesc}</textarea>
                            <div class="edit-task-error" id="taskDescError"></div>
                        </div>
                        <input type="hidden" id="isPlaceholder" value="${isPlaceholder}">
                        <input type="hidden" id="userTaskListId" value="${userTaskListId}">
                        <div class="edit-task-form-actions">
                            <button class="edit-task-cancel-btn">Cancel</button>
                            <button class="edit-task-submit-btn">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.body.appendChild(editModalContainer);

    // Add event listeners
    const closeBtn = editModalContainer.querySelector('.edit-task-close');
    const cancelBtn = editModalContainer.querySelector('.edit-task-cancel-btn');
    const submitBtn = editModalContainer.querySelector('.edit-task-submit-btn');

    // Close modal handlers
    const closeModal = () => {
        editModalContainer.remove();
    };

    closeBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);

    // Submit handler
    submitBtn.addEventListener('click', function () {
        // Get form values
        const taskNameInput = document.getElementById('taskName');
        const taskDescInput = document.getElementById('taskDescription');
        const isPlaceholderInput = document.getElementById('isPlaceholder');
        const taskName = taskNameInput.value.trim();
        const taskDesc = taskDescInput.value.trim();
        const isPlaceholder = isPlaceholderInput.value === 'true';

        // Reset error messages
        document.getElementById('taskNameError').textContent = '';
        document.getElementById('taskDescError').textContent = '';

        // Validate form
        let isValid = true;

        if (!taskName) {
            document.getElementById('taskNameError').textContent = 'Task name is required';
            isValid = false;
        } else if (taskName.toUpperCase() === 'CUSTOMISED TASK' || taskName.toUpperCase() === 'CUSTOMISEDTASK') {
            document.getElementById('taskNameError').textContent = 'This task name is reserved';
            isValid = false;
        }

        if (!taskDesc) {
            document.getElementById('taskDescError').textContent = 'Task description is required';
            isValid = false;
        } else if (taskDesc.toUpperCase() === 'CUSTOMISED DESCRIPTION' || taskDesc.toUpperCase() === 'CUSTOMISEDDESCRIPTION') {
            document.getElementById('taskDescError').textContent = 'This description is reserved';
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Submit the form with isPlaceholder flag to handle properly on backend
        submitCustomTask(taskId, taskName, taskDesc, isPlaceholder, closeModal);
    });
}

// Function to submit the custom task
function submitCustomTask(taskId, taskName, taskDesc, isPlaceholder, closeCallback) {
    // Get userTaskListId from the hidden input
    const userTaskListId = document.getElementById('userTaskListId').value;

    // Make AJAX request
    fetch('/MizukiForest/CustomTaskServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'taskId=' + encodeURIComponent(taskId) +
                '&userTaskListId=' + encodeURIComponent(userTaskListId) +
                '&taskName=' + encodeURIComponent(taskName) +
                '&taskDesc=' + encodeURIComponent(taskDesc) +
                '&isPlaceholder=' + encodeURIComponent(isPlaceholder),
        credentials: 'same-origin'
    })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'error') {
                    if (data.redirect) {
                        window.location.href = data.redirect;
                        return;
                    }
                    alert('Error: ' + data.message);
                    return;
                }

                // Close the modal
                if (closeCallback) {
                    closeCallback();
                }

                // Refresh the tasks list with AJAX instead of page reload
                loadDailyTasks();
            })
            .catch(error => {
                console.error('Error submitting custom task:', error);
                alert('Failed to save task. Please try again later.');
            });
}

// Function to handle task completion
function completeTask(taskId, checkbox) {
    // Disable checkbox to prevent multiple submissions
    checkbox.disabled = true;

    // Check if this is a customisable task
    const taskItem = checkbox.closest('.daily-task-item');
    const isCustomisableTask = taskItem.closest('#customisableTasksTab') !== null;

    // Check if this is the placeholder task (don't allow completing placeholder)
    const isPlaceholder = taskItem.querySelector('h3')?.textContent === "Customised Task" &&
            taskItem.querySelector('p')?.textContent === "Customised Description";

    // Only block completion for placeholder tasks
    if (isCustomisableTask && isPlaceholder) {
        alert('Please customize this task before completing it.');
        checkbox.disabled = false;
        checkbox.checked = false;
        return;
    }

    // Make AJAX request to complete the task
    fetch('/MizukiForest/CompleteTaskServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'taskId=' + encodeURIComponent(taskId) + '&isPlaceholder=' + encodeURIComponent(isPlaceholder || false),
        credentials: 'same-origin'
    })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'error') {
                    if (data.redirect) {
                        window.location.href = data.redirect;
                        return;
                    }
                    alert('Error: ' + data.message);
                    checkbox.disabled = false;
                    checkbox.checked = false;
                    return;
                }

                // Handle case where task was already completed today
                if (data.status === 'alreadyCompleted') {
                    alert('This task was already completed today.');
                    checkbox.disabled = false;
                    checkbox.checked = true;
                    return;
                }

                // Mark task as completed in UI
                const taskItem = checkbox.closest('.daily-task-item');
                taskItem.classList.add('completed');

                // Update rewards to show claimed
                const rewardsDiv = taskItem.querySelector('.daily-task-rewards');
                rewardsDiv.innerHTML = `<div class="daily-task-claimed">CLAIMED</div>`;

                // Update user stats
                if (data.newExp !== undefined && data.newCoins !== undefined) {
                    const userXPElement = document.getElementById('userXP');
                    const userCoinsElement = document.getElementById('dailyTaskUserCoins');

                    if (userXPElement) {
                        userXPElement.textContent = String(data.newExp);
                    }

                    if (userCoinsElement) {
                        userCoinsElement.textContent = String(data.newCoins);
                    }
                }

                // Check if user leveled up
                if (data.leveledUp) {
                    // Show level up modal
                    showLevelUpModal(data.oldLevel, data.newLevel, data.rewards);
                }
            })
            .catch(error => {
                console.error('Error completing task:', error);
                alert('Failed to complete task. Please try again later.');
                checkbox.disabled = false;
                checkbox.checked = false;
            });
}

// Function to show level up modal
function showLevelUpModal(oldLevel, newLevel, rewards) {
    // Create level up modal
    const levelUpModalContainer = document.createElement('div');
    levelUpModalContainer.id = 'levelUpModalContainer';
    levelUpModalContainer.className = 'level-up-modal-container';

    let rewardsHTML = '';
    if (rewards && rewards.length > 0) {
        rewardsHTML = '<ul class="level-up-rewards">';
        rewards.forEach(reward => {
            rewardsHTML += `<li>${reward.quantity} x ${reward.name}</li>`;
        });
        rewardsHTML += '</ul>';
    }

    levelUpModalContainer.innerHTML = `
        <div class="level-up-modal">
            <div class="level-up-modal-content">
                <div class="level-up-modal-header">
                    <h2>Level Up!</h2>
                    <span class="level-up-close">&times;</span>
                </div>
                <div class="level-up-modal-body">
                    <div class="level-up-animation">
                        <div class="level-up-old">Level ${oldLevel}</div>
                        <div class="level-up-arrow">➡️</div>
                        <div class="level-up-new">Level ${newLevel}</div>
                    </div>
                    <p>Congratulations! You've reached level ${newLevel}!</p>
                    ${rewards && rewards.length > 0 ? '<p>Rewards auto-claimed to inventory:</p>' + rewardsHTML : ''}
                    <p class="level-up-note">See the levels page to learn more about your progress.</p>
                    <button class="level-up-ok-btn">OK</button>
                </div>
            </div>
        </div>
    `;

    document.body.appendChild(levelUpModalContainer);

    // Add close event listeners
    const closeBtn = levelUpModalContainer.querySelector('.level-up-close');
    const okBtn = levelUpModalContainer.querySelector('.level-up-ok-btn');

    closeBtn.addEventListener('click', function () {
        levelUpModalContainer.remove();
    });

    okBtn.addEventListener('click', function () {
        levelUpModalContainer.remove();
    });
}