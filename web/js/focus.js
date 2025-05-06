document.addEventListener('DOMContentLoaded', function () {
    // Focus session button event listener
    const focusSessionBtn = document.getElementById('focusSessionBtn');
    if (focusSessionBtn) {
        focusSessionBtn.addEventListener('click', showFocusSessionModal);
    }

    // Check if there's an active session in cookies
    checkForActiveSession();

    // Link the external CSS file
    loadFocusSessionStyles();
});

function loadFocusSessionStyles() {
    if (!document.getElementById('focus-session-styles')) {
        const link = document.createElement('link');
        link.id = 'focus-session-styles';
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.href = '/MizukiForest/css/myForest.css';
        document.head.appendChild(link);
    }
}

// Global variables
let stopwatchInterval;
let timerInterval;
let pomodoroInterval;
let currentSessionType = null;
let sessionDuration = 0;
let sessionStartTime = 0;
let isMinimized = false;

let pomodoroConfig = {
    workDuration: 0,
    shortBreak: 0,
    longBreak: 0,
    cycles: 0,
    currentCycle: 0,
    isWorkPhase: true,
    isLongBreak: false,
    totalWorkTime: 0,
    totalWorkDuration: 0  // This will store the full expected work time for progress calculation
};

// Constants for tree box rewards
const TREE_BOXES = {
    COMMON: {
        id: 'TB000001',
        image: '/MizukiForest/media/images/treebox/TB000001.png',
        name: 'Common Tree Box'
    },
    RARE: {
        id: 'TB000002',
        image: '/MizukiForest/media/images/treebox/TB000002.png',
        name: 'Rare Tree Box'
    },
    EPIC: {
        id: 'TB000003',
        image: '/MizukiForest/media/images/treebox/TB000003.png',
        name: 'Epic Tree Box'
    },
    LEGENDARY: {
        id: 'TB000004',
        image: '/MizukiForest/media/images/treebox/TB000004.png',
        name: 'Legendary Tree Box'
    },
    WITHERED: {
        id: 'TR000001', // Assuming withered tree ID
        image: '/MizukiForest/media/images/forestmisc/Withered Tree.png',
        name: 'Withered Tree'
    }
};

// Session duration limits, during demo comment.
const MAX_DURATION_SECONDS = 3 * 60 * 60; // 3 hours
const TREE_BOX_INTERVAL_SECONDS = 30 * 60; // 30 minutes


//for demo, uncomment and comment above
/*
const MAX_DURATION_SECONDS = 3 * 60; // 3 minutes (instead of 3 hours)
const TREE_BOX_INTERVAL_SECONDS = 30; // 30 seconds (instead of 30 minutes)
*/

/**
 * Check if there's an active session in cookies and resume it
 */
function checkForActiveSession() {
    const sessionType = getCookie('focusSessionType');
    const sessionTimeRemaining = getCookie('focusSessionTimeRemaining');
    const sessionStart = getCookie('focusSessionStartTime');

    if (sessionType && sessionStart) {
        currentSessionType = sessionType;
        sessionStartTime = parseInt(sessionStart);

        // Recreate the session modal
        const modalContainer = createModal();
        document.body.appendChild(modalContainer);

        // Add event listeners for both help and close buttons
        const helpBtn = modalContainer.querySelector('.help-btn');
        if (helpBtn) {
            helpBtn.addEventListener('click', showHelpModal);
        }

        const closeBtn = modalContainer.querySelector('.close-btn');
        if (closeBtn) {
            closeBtn.addEventListener('click', closeModal);
        }

        // Depending on the session type, set up the correct view
        if (sessionType === 'Stopwatch') {
            setupActiveStopwatchSession(modalContainer);
        } else if (sessionType === 'Timer') {
            setupActiveTimerSession(modalContainer, parseInt(sessionTimeRemaining));
        } else if (sessionType === 'Pomodoro') {
            // TODO: Implement Pomodoro timer resumption
        }

        // Minimize the modal initially
        minimizeModal();
    }
}

/**
 * Resume an active stopwatch session
 */
function setupActiveStopwatchSession(modalContainer) {
    const elapsedTime = Math.floor((Date.now() - sessionStartTime) / 1000);
    sessionDuration = elapsedTime;

    // Create stopwatch UI
    const contentDiv = modalContainer.querySelector('.modal-content');
    contentDiv.innerHTML = createStopwatchContent();

    // Update the timer display
    updateStopwatchDisplay(elapsedTime);

    // Update progress bar
    updateProgressBar(elapsedTime);

    // Update rewards
    updateRewardsDisplay(elapsedTime);

    // Start the stopwatch
    startStopwatch();

    // Add event listeners
    const stopButton = contentDiv.querySelector('#stopFocusSession');
    if (stopButton) {
        stopButton.addEventListener('click', stopFocusSession);
    }

    // Add minimize/maximize button
    const minimizeBtn = modalContainer.querySelector('.minimize-btn');
    if (minimizeBtn) {
        minimizeBtn.addEventListener('click', toggleMinimize);
    }
}

/**
 * Resume an active timer session
 */
function setupActiveTimerSession(modalContainer, timeRemaining) {
    // Create timer UI
    const contentDiv = modalContainer.querySelector('.modal-content');
    contentDiv.innerHTML = createTimerContent();

    // Simply use the remaining time from cookie directly
    // instead of recalculating and potentially double-counting elapsed time
    sessionDuration = timeRemaining;

    // Update cookie with the current remaining time
    setCookie('focusSessionTimeRemaining', sessionDuration, 1);

    // Update timer display
    updateTimerDisplay(sessionDuration);

    // Get the initial duration from cookie or calculate it
    const initialDuration = parseInt(getCookie('focusSessionInitialDuration')) || 
                           (timeRemaining + Math.floor((Date.now() - sessionStartTime) / 1000));
    
    // Store it if not already set
    if (!getCookie('focusSessionInitialDuration')) {
        setCookie('focusSessionInitialDuration', initialDuration, 1);
    }

    // Update progress bar
    updateTimerProgressBar(sessionDuration, initialDuration);

    // Update rewards based on completed time
    updateRewardsDisplay(initialDuration - sessionDuration);

    // Start the timer
    startTimer();

    // Add event listeners
    const stopButton = contentDiv.querySelector('#stopFocusSession');
    if (stopButton) {
        stopButton.addEventListener('click', stopFocusSession);
    }

    // Add minimize/maximize button
    const minimizeBtn = modalContainer.querySelector('.minimize-btn');
    if (minimizeBtn) {
        minimizeBtn.addEventListener('click', toggleMinimize);
    }
}

/**
 * Calculate total duration of timer based on remaining time and elapsed time
 */
function calculateTotalDuration(remainingTime) {
    const elapsedTime = Math.floor((Date.now() - sessionStartTime) / 1000);
    return elapsedTime + remainingTime;
}

/**
 * Show the focus session modal
 */
function showFocusSessionModal() {
    // Check if session is already active
    if (currentSessionType) {
        // If minimized, maximize it
        if (isMinimized) {
            maximizeModal();
        }
        return;
    }

    // Create modal container
    const modalContainer = createModal();
    document.body.appendChild(modalContainer);

    // Add initial content with mode selection
    const contentDiv = modalContainer.querySelector('.modal-content');
    contentDiv.innerHTML = `
        <div class="focus-mode-selection">
            <h2>Select Focus Mode</h2>
            <div class="focus-modes">
                <button id="stopwatchMode">Stopwatch</button>
                <button id="timerMode">Timer</button>
                <button id="pomodoroMode">Pomodoro Timer</button>
            </div>
        </div>
    `;

    // Add event listeners for mode selection
    const stopwatchBtn = contentDiv.querySelector('#stopwatchMode');
    const timerBtn = contentDiv.querySelector('#timerMode');
    const pomodoroBtn = contentDiv.querySelector('#pomodoroMode');

    stopwatchBtn.addEventListener('click', () => selectFocusMode('Stopwatch', modalContainer));
    timerBtn.addEventListener('click', () => selectFocusMode('Timer', modalContainer));
    pomodoroBtn.addEventListener('click', () => selectFocusMode('Pomodoro', modalContainer));

    // Add help button event listener
    const helpBtn = modalContainer.querySelector('.help-btn');
    helpBtn.addEventListener('click', showHelpModal);

    // Add close button event listener
    const closeBtn = modalContainer.querySelector('.close-btn');
    closeBtn.addEventListener('click', closeModal);
}

/**
 * Create the modal container
 */
function createModal() {
    const modalContainer = document.createElement('div');
    modalContainer.className = 'focus-session-modal';
    modalContainer.innerHTML = `
        <div class="modal-header">
            <button class="help-btn">?</button>
            <h2>Focus Session</h2>
            <div class="modal-controls">
                <button class="minimize-btn">-</button>
                <button class="close-btn">×</button>
            </div>
        </div>
        <div class="modal-content"></div>
    `;

    return modalContainer;
}

/**
 * Show the help modal with instructions
 */
function showHelpModal() {
    const helpModal = document.createElement('div');
    helpModal.className = 'help-modal';
    helpModal.innerHTML = `
        <div class="help-modal-content">
            <button class="close-help-btn">×</button>
            <h2>Focus Session Rules</h2>
            <div class="focus-modes-help">
                <div class="focus-mode-help">
                    <h3>Stopwatch Mode</h3>
                    <p>Track your focus time with no predetermined end. For every 30 minutes of focus, you'll earn a Common Tree Box.</p>
                    <p>If you stop before 30 minutes, you'll receive a Withered Tree in your forest.</p>
                    <p>Maximum focus time: 3 hours</p>
                </div>
                <div class="focus-mode-help">
                    <h3>Timer Mode</h3>
                    <p>Set a specific focus duration between 30 minutes and 3 hours.</p>
                    <p>Rewards are the same as Stopwatch mode - one Common Tree Box per 30 minutes completed.</p>
                </div>
                <div class="focus-mode-help">
                    <h3>Pomodoro Timer</h3>
                    <p>Work in focused intervals with short breaks in between.</p>
                    <p>Customize work periods and break lengths to suit your preferences.</p>
                    <p>Earn rewards based on total focused time.</p>
                </div>
                <div class="focus-mode-help">
                    <h3>Reward Conversions</h3>
                    <p>2 Common Tree Boxes = 1 Rare Tree Box</p>
                    <p>3 Common Tree Boxes = 1 Rare + 1 Common OR 3 Common</p>
                    <p>4 Common Tree Boxes = 1 Epic OR 2 Rare OR 4 Common</p>
                    <p>6 Common Tree Boxes = 1 Legendary OR 2 Epic OR 3 Rare OR 6 Common</p>
                </div>
            </div>
        </div>
    `;

    document.body.appendChild(helpModal);

    // Close button event listener
    const closeHelpBtn = helpModal.querySelector('.close-help-btn');
    closeHelpBtn.addEventListener('click', () => {
        document.body.removeChild(helpModal);
    });
}

function closeModal() {
    // Check if a session has actually started (not just mode selected)
    const startBtn = document.querySelector('#startFocusSession');
    const isSessionActive = startBtn && startBtn.style.display === 'none';

    if (!isSessionActive) {
        // No active session, we can safely close the modal and reset currentSessionType
        const modal = document.querySelector('.focus-session-modal');
        if (modal) {
            document.body.removeChild(modal);
        }
        // Reset currentSessionType when just closing the modal without an active session
        currentSessionType = null;
    } else {
        // If session is active, just minimize
        minimizeModal();
    }
}

function minimizeModal() {
    const modal = document.querySelector('.focus-session-modal');
    if (modal) {
        modal.classList.add('minimized');
        const minimizeBtn = modal.querySelector('.minimize-btn');
        minimizeBtn.textContent = '□';
        isMinimized = true;
    }
}

function maximizeModal() {
    const modal = document.querySelector('.focus-session-modal');
    if (modal) {
        modal.classList.remove('minimized');
        const minimizeBtn = modal.querySelector('.minimize-btn');
        minimizeBtn.textContent = '-';
        isMinimized = false;
    }
}

function toggleMinimize() {
    if (isMinimized) {
        maximizeModal();
    } else {
        minimizeModal();
    }
}

function selectFocusMode(mode, modalContainer) {
    currentSessionType = mode;
    const contentDiv = modalContainer.querySelector('.modal-content');

    // Add minimize button event listener
    const minimizeBtn = modalContainer.querySelector('.minimize-btn');
    minimizeBtn.addEventListener('click', toggleMinimize);

    if (mode === 'Stopwatch') {
        contentDiv.innerHTML = createStopwatchContent();

        // Add event listeners for stopwatch
        const startButton = contentDiv.querySelector('#startFocusSession');
        startButton.addEventListener('click', startStopwatch);

    } else if (mode === 'Timer') {
        contentDiv.innerHTML = createTimerContent();

        // Add event listeners for timer
        setupTimerEvents(contentDiv);

    } else if (mode === 'Pomodoro') {
        contentDiv.innerHTML = createPomodoroContent();

        // Add event listeners for pomodoro
        setupPomodoroEvents(contentDiv);
    }
}

function createStopwatchContent() {
    return `
        <div class="stopwatch-container">
            <h3>Stopwatch Mode</h3>
            <div class="timer-display">00:00:00</div>
            <div class="progress-container">
                <div class="progress-bar"></div>
            </div>
            <div class="rewards-container">
                <h4>Current Reward</h4>
                <div class="reward-display">
                    <img src="${TREE_BOXES.WITHERED.image}" alt="${TREE_BOXES.WITHERED.name}" title="${TREE_BOXES.WITHERED.name}">
                    <p>${TREE_BOXES.WITHERED.name}</p>
                </div>
            </div>
            <div class="session-controls">
                <button id="startFocusSession" class="start-btn">Start Focus Session</button>
                <button id="stopFocusSession" class="stop-btn" style="display: none;">Stop Focus Session</button>
            </div>
        </div>
    `;
}

/**
 * Create timer content
 */
function createTimerContent() {
    return `
        <div class="timer-container">
            <h3>Timer Mode</h3>
            <div class="timer-selection">
                <p>Select Duration (minutes):</p>
                <div class="timer-options">
                    <button class="timer-option" data-duration="30">30</button>
                    <button class="timer-option" data-duration="60">60</button>
                    <button class="timer-option" data-duration="90">90</button>
                    <button class="timer-option" data-duration="120">120</button>
                    <button class="timer-option" data-duration="150">150</button>
                    <button class="timer-option" data-duration="180">180</button>
                </div>
            </div>
            <div class="timer-display">00:00:00</div>
            <div class="progress-container">
                <div class="progress-bar"></div>
            </div>
            <div class="rewards-container">
                <h4>Expected Reward</h4>
                <div class="reward-display">
                    <img src="${TREE_BOXES.WITHERED.image}" alt="${TREE_BOXES.WITHERED.name}" title="${TREE_BOXES.WITHERED.name}">
                    <p>${TREE_BOXES.WITHERED.name}</p>
                </div>
            </div>
            <div class="session-controls">
                <button id="startFocusSession" class="start-btn" disabled>Start Focus Session</button>
                <button id="stopFocusSession" class="stop-btn" style="display: none;">Stop Focus Session</button>
            </div>
        </div>
    `;
}

function createPomodoroContent() {
    return `
        <div class="pomodoro-container">
            <h3>Pomodoro Mode</h3>
            <div class="pomodoro-settings">
                <div class="setting">
                    <label for="workDuration">Work Duration (minutes):</label>
                    <input type="number" id="workDuration" min="25" max="45" value="25">
                </div>
                <div class="setting">
                    <label for="shortBreak">Short Break (minutes):</label>
                    <input type="number" id="shortBreak" min="3" max="10" value="5">
                </div>
                <div class="setting">
                    <label for="longBreak">Long Break (minutes):</label>
                    <input type="number" id="longBreak" min="10" max="30" value="15">
                </div>
                <div class="setting">
                    <label for="pomodoroCycles">Cycles before long break:</label>
                    <input type="number" id="pomodoroCycles" min="4" max="6" value="4">
                </div>
            </div>
            <div class="timer-display">00:00:00</div>
            <div class="pomodoro-status">Ready to start</div>
            <div class="pomodoro-progress">
                <div class="cycle-indicators"></div>
            </div>
            <div class="progress-container">
                <div class="progress-bar"></div>
            </div>
            <div class="rewards-container">
                <h4>Expected Reward</h4>
                <div class="reward-display">
                    <img src="${TREE_BOXES.WITHERED.image}" alt="${TREE_BOXES.WITHERED.name}" title="${TREE_BOXES.WITHERED.name}">
                    <p>${TREE_BOXES.WITHERED.name}</p>
                </div>
            </div>
            <div class="session-controls">
                <button id="startFocusSession" class="start-btn">Start Focus Session</button>
                <button id="stopFocusSession" class="stop-btn" style="display: none;">Stop Focus Session</button>
                <button id="skipBreak" class="skip-btn" style="display: none;">Skip Break</button>
            </div>
        </div>
    `;
}

function setupTimerEvents(contentDiv) {
    const timerOptions = contentDiv.querySelectorAll('.timer-option');
    const startButton = contentDiv.querySelector('#startFocusSession');
    let selectedDuration = 0;

    timerOptions.forEach(option => {
        option.addEventListener('click', () => {
            // Remove active class from all options
            timerOptions.forEach(opt => opt.classList.remove('active'));

            // Add active class to selected option
            option.classList.add('active');

            // Get selected duration in seconds
            selectedDuration = parseInt(option.dataset.duration) * 60;

            // Update reward display based on selected duration
            updateRewardsDisplay(selectedDuration);

            // Enable start button
            startButton.disabled = false;
        });
    });

    startButton.addEventListener('click', () => {
        if (selectedDuration > 0) {
            sessionDuration = selectedDuration;
            startTimer();
        }
    });
}

function setupPomodoroEvents(contentDiv) {
    const startButton = contentDiv.querySelector('#startFocusSession');
    const workDurationInput = contentDiv.querySelector('#workDuration');
    const shortBreakInput = contentDiv.querySelector('#shortBreak');
    const longBreakInput = contentDiv.querySelector('#longBreak');
    const cyclesInput = contentDiv.querySelector('#pomodoroCycles');

    // Validate input values
    workDurationInput.addEventListener('change', () => {
        if (workDurationInput.value < 25) workDurationInput.value = 25;
        if (workDurationInput.value > 45) workDurationInput.value = 45;
        updatePomodoroRewardsDisplay();
    });

    shortBreakInput.addEventListener('change', () => {
        if (shortBreakInput.value < 3) shortBreakInput.value = 3;
        if (shortBreakInput.value > 10) shortBreakInput.value = 10;
    });

    longBreakInput.addEventListener('change', () => {
        if (longBreakInput.value < 10) longBreakInput.value = 10;
        if (longBreakInput.value > 30) longBreakInput.value = 30;
    });

    cyclesInput.addEventListener('change', () => {
        if (cyclesInput.value < 4) cyclesInput.value = 4;
        if (cyclesInput.value > 6) cyclesInput.value = 6;
        updatePomodoroRewardsDisplay();
    });

    // Initialize rewards display
    updatePomodoroRewardsDisplay();

    startButton.addEventListener('click', () => {
        const workDuration = parseInt(workDurationInput.value) * 60;
        const shortBreak = parseInt(shortBreakInput.value) * 60;
        const longBreak = parseInt(longBreakInput.value) * 60;
        const cycles = parseInt(cyclesInput.value);

        startPomodoro(workDuration, shortBreak, longBreak, cycles);
    });
}

function startStopwatch() {
    // Store start time in cookie
    sessionStartTime = Date.now();
    setCookie('focusSessionType', 'Stopwatch', 1);
    setCookie('focusSessionStartTime', sessionStartTime, 1);

    // Hide start button, show stop button
    const startButton = document.querySelector('#startFocusSession');
    const stopButton = document.querySelector('#stopFocusSession');

    if (startButton && stopButton) {
        startButton.style.display = 'none';
        stopButton.style.display = 'block';
        stopButton.addEventListener('click', stopFocusSession);
    }

    // Start the interval
    stopwatchInterval = setInterval(() => {
        sessionDuration++;

        // Update display
        updateStopwatchDisplay(sessionDuration);

        // Update progress bar
        updateProgressBar(sessionDuration);

        // Update rewards
        updateRewardsDisplay(sessionDuration);

        // Check if we've reached the max duration
        if (sessionDuration >= MAX_DURATION_SECONDS) {
            stopFocusSession();
        }
    }, 1000);
}


function updateStopwatchDisplay(seconds) {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;

    const display = document.querySelector('.timer-display');
    if (display) {
        display.textContent = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
    }
}

function updateProgressBar(seconds) {
    const progressBar = document.querySelector('.progress-bar');
    if (progressBar) {
        const percentage = Math.min((seconds / MAX_DURATION_SECONDS) * 100, 100);
        progressBar.style.width = `${percentage}%`;
    }
}


function updateRewardsDisplay(seconds) {
    const rewardDisplay = document.querySelector('.reward-display');
    if (!rewardDisplay)
        return;

    const commonBoxesEarned = Math.floor(seconds / TREE_BOX_INTERVAL_SECONDS);
    let rewardHTML = '';

    if (commonBoxesEarned < 1) {
        // Less than 30 minutes = withered tree
        rewardHTML = `
            <img src="${TREE_BOXES.WITHERED.image}" alt="${TREE_BOXES.WITHERED.name}" title="${TREE_BOXES.WITHERED.name}">
            <p>${TREE_BOXES.WITHERED.name}</p>
        `;
    } else {
        // Show the different reward options based on the number of common boxes earned
        rewardHTML = `<div class="reward-options">`;

        // Always show common boxes option
        rewardHTML += `
            <div class="reward-option">
                <img src="${TREE_BOXES.COMMON.image}" alt="${TREE_BOXES.COMMON.name}" title="${TREE_BOXES.COMMON.name}">
                <p>${TREE_BOXES.COMMON.name} x${commonBoxesEarned}</p>
            </div>
        `;

        // Show rare box option if applicable
        if (commonBoxesEarned >= 2) {
            const rareBoxes = Math.floor(commonBoxesEarned / 2);
            const remainingCommon = commonBoxesEarned % 2;

            rewardHTML += `
                <div class="reward-option">
                    <img src="${TREE_BOXES.RARE.image}" alt="${TREE_BOXES.RARE.name}" title="${TREE_BOXES.RARE.name}">
                    <p>${TREE_BOXES.RARE.name} x${rareBoxes}${remainingCommon ? ` + ${TREE_BOXES.COMMON.name} x${remainingCommon}` : ''}</p>
                </div>
            `;
        }

        // Show epic box option if applicable
        if (commonBoxesEarned >= 4) {
            const epicBoxes = Math.floor(commonBoxesEarned / 4);
            const remainingCommon = commonBoxesEarned % 4;

            rewardHTML += `
                <div class="reward-option">
                    <img src="${TREE_BOXES.EPIC.image}" alt="${TREE_BOXES.EPIC.name}" title="${TREE_BOXES.EPIC.name}">
                    <p>${TREE_BOXES.EPIC.name} x${epicBoxes}${remainingCommon ? ` + ${calculateRemainingRewards(remainingCommon)}` : ''}</p>
                </div>
            `;
        }

        // Show legendary box option if applicable
        if (commonBoxesEarned >= 6) {
            const legendaryBoxes = Math.floor(commonBoxesEarned / 6);
            const remainingCommon = commonBoxesEarned % 6;

            rewardHTML += `
                <div class="reward-option">
                    <img src="${TREE_BOXES.LEGENDARY.image}" alt="${TREE_BOXES.LEGENDARY.name}" title="${TREE_BOXES.LEGENDARY.name}">
                    <p>${TREE_BOXES.LEGENDARY.name} x${legendaryBoxes}${remainingCommon ? ` + ${calculateRemainingRewards(remainingCommon)}` : ''}</p>
                </div>
            `;
        }

        rewardHTML += `</div>`;
    }

    rewardDisplay.innerHTML = rewardHTML;

    // Add event listeners to select a reward option
    const rewardOptions = document.querySelectorAll('.reward-option');
    rewardOptions.forEach(option => {
        option.addEventListener('click', () => {
            rewardOptions.forEach(opt => opt.classList.remove('selected'));
            option.classList.add('selected');
        });
    });

    // Select the first option by default
    if (rewardOptions.length > 0) {
        rewardOptions[0].classList.add('selected');
    }
}

function calculateRemainingRewards(commonBoxes) {
    if (commonBoxes === 0)
        return '';
    if (commonBoxes < 2)
        return `${TREE_BOXES.COMMON.name} x${commonBoxes}`;

    const rareBoxes = Math.floor(commonBoxes / 2);
    const remainingCommon = commonBoxes % 2;

    return `${TREE_BOXES.RARE.name} x${rareBoxes}${remainingCommon ? ` + ${TREE_BOXES.COMMON.name} x${remainingCommon}` : ''}`;
}

function startTimer() {
    // Store start time and initial duration in cookie if not already set
    if (!getCookie('focusSessionStartTime')) {
        sessionStartTime = Date.now();
        setCookie('focusSessionStartTime', sessionStartTime, 1);
        setCookie('focusSessionInitialDuration', sessionDuration, 1);
    } else {
        // Use existing start time if resuming
        sessionStartTime = parseInt(getCookie('focusSessionStartTime'));
    }

    // Store session type
    setCookie('focusSessionType', 'Timer', 1);

    // Update cookie with current remaining time
    setCookie('focusSessionTimeRemaining', sessionDuration, 1);

    // Hide timer selection, show timer
    const timerSelection = document.querySelector('.timer-selection');
    if (timerSelection) {
        timerSelection.style.display = 'none';
    }

    // Hide start button, show stop button
    const startButton = document.querySelector('#startFocusSession');
    const stopButton = document.querySelector('#stopFocusSession');

    if (startButton && stopButton) {
        startButton.style.display = 'none';
        stopButton.style.display = 'block';
        stopButton.addEventListener('click', stopFocusSession);
    }

    // Display initial time
    updateTimerDisplay(sessionDuration);

    // Get the initial total duration for progress bar calculation
    const initialDuration = parseInt(getCookie('focusSessionInitialDuration')) || sessionDuration;
    
    // Update progress bar
    updateTimerProgressBar(sessionDuration, initialDuration);

    // Start the interval
    timerInterval = setInterval(() => {
        sessionDuration--;

        // Update cookie with remaining time
        setCookie('focusSessionTimeRemaining', sessionDuration, 1);

        // Update display
        updateTimerDisplay(sessionDuration);

        // Update progress bar
        updateTimerProgressBar(sessionDuration, initialDuration);

        // Check if timer has reached zero
        if (sessionDuration <= 0) {
            stopFocusSession();
        }
    }, 1000);
}

function updateTimerDisplay(seconds) {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;

    const display = document.querySelector('.timer-display');
    if (display) {
        display.textContent = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
    }
}

function updateTimerProgressBar(secondsRemaining, totalSeconds) {
    const progressBar = document.querySelector('.progress-bar');
    if (progressBar) {
        const percentage = ((totalSeconds - secondsRemaining) / totalSeconds) * 100;
        progressBar.style.width = `${percentage}%`;
    }
}

function startPomodoro(workDuration, shortBreak, longBreak, cycles) {
    // Store configuration
    pomodoroConfig = {
        workDuration,
        shortBreak,
        longBreak,
        cycles,
        currentCycle: 1,
        isWorkPhase: true,
        isLongBreak: false,
        totalWorkTime: 0,
        totalWorkDuration: workDuration * cycles  // Total expected work time
    };

    // Store session data in cookies
    sessionStartTime = Date.now();
    setCookie('focusSessionType', 'Pomodoro', 1);
    setCookie('focusSessionStartTime', sessionStartTime, 1);
    setCookie('pomodoroWorkDuration', workDuration, 1);
    setCookie('pomodoroShortBreak', shortBreak, 1);
    setCookie('pomodoroLongBreak', longBreak, 1);
    setCookie('pomodoroCycles', cycles, 1);
    setCookie('pomodoroCurrentCycle', 1, 1);
    setCookie('pomodoroIsWorkPhase', true, 1);
    setCookie('pomodoroTotalWorkTime', 0, 1);

    // Hide settings, show controls
    const settingsDiv = document.querySelector('.pomodoro-settings');
    if (settingsDiv) {
        settingsDiv.style.display = 'none';
    }

    // Hide start button, show stop button
    const startButton = document.querySelector('#startFocusSession');
    const stopButton = document.querySelector('#stopFocusSession');

    if (startButton && stopButton) {
        startButton.style.display = 'none';
        stopButton.style.display = 'block';
        stopButton.addEventListener('click', stopFocusSession);
    }

    // Create cycle indicators
    createCycleIndicators(cycles);

    // Start first work session
    sessionDuration = workDuration;
    startPomodoroInterval();
}

function createCycleIndicators(cycles) {
    const indicatorsContainer = document.querySelector('.cycle-indicators');
    if (indicatorsContainer) {
        indicatorsContainer.innerHTML = '';
        
        for (let i = 1; i <= cycles; i++) {
            const indicator = document.createElement('div');
            indicator.className = 'cycle-indicator';
            indicator.dataset.cycle = i;
            indicator.innerHTML = `<span>${i}</span>`;
            
            if (i === 1) {
                indicator.classList.add('current');
            }
            
            indicatorsContainer.appendChild(indicator);
        }
    }
}

function updateCycleIndicators(currentCycle, isComplete = false) {
    const indicators = document.querySelectorAll('.cycle-indicator');
    
    indicators.forEach(indicator => {
        const cycle = parseInt(indicator.dataset.cycle);
        
        // Remove all classes first
        indicator.classList.remove('current', 'completed');
        
        // Add appropriate class
        if (cycle < currentCycle) {
            indicator.classList.add('completed');
        } else if (cycle === currentCycle) {
            indicator.classList.add(isComplete ? 'completed' : 'current');
        }
    });
}

function startPomodoroInterval() {
    // Clear any existing interval
    clearInterval(pomodoroInterval);
    
    // Update status display
    updatePomodoroStatus();
    
    // Start interval
    pomodoroInterval = setInterval(() => {
        sessionDuration--;
        
        // Update display
        updateTimerDisplay(sessionDuration);
        
        // Update progress bar
        if (pomodoroConfig.isWorkPhase) {
            const totalProgress = (pomodoroConfig.totalWorkTime + (pomodoroConfig.workDuration - sessionDuration)) / pomodoroConfig.totalWorkDuration;
            updatePomodoroProgressBar(totalProgress * 100);
        }
        
        // Check if current interval is done
        if (sessionDuration <= 0) {
            handlePomodoroIntervalComplete();
        }
        
    }, 1000);
}

function updatePomodoroStatus() {
    const statusDiv = document.querySelector('.pomodoro-status');
    if (statusDiv) {
        if (pomodoroConfig.isWorkPhase) {
            statusDiv.textContent = `Focus ${pomodoroConfig.currentCycle} of ${pomodoroConfig.cycles}`;
            statusDiv.className = 'pomodoro-status working';
            
            // Hide skip button during work
            const skipButton = document.querySelector('#skipBreak');
            if (skipButton) skipButton.style.display = 'none';
        } else {
            const breakType = pomodoroConfig.isLongBreak ? 'Long' : 'Short';
            statusDiv.textContent = `${breakType} Break`;
            statusDiv.className = 'pomodoro-status break';
            
            // Show skip button during breaks
            const skipButton = document.querySelector('#skipBreak');
            if (skipButton) {
                skipButton.style.display = 'block';
                skipButton.addEventListener('click', skipBreak);
            }
        }
    }
}

function updatePomodoroProgressBar(percentage) {
    const progressBar = document.querySelector('.progress-bar');
    if (progressBar) {
        progressBar.style.width = `${Math.min(percentage, 100)}%`;
    }
}

function handlePomodoroIntervalComplete() {
    clearInterval(pomodoroInterval);
    
    // Play sound notification
    playNotificationSound();
    
    if (pomodoroConfig.isWorkPhase) {
        // Work phase complete
        
        // Add completed time to total work time
        pomodoroConfig.totalWorkTime += pomodoroConfig.workDuration;
        setCookie('pomodoroTotalWorkTime', pomodoroConfig.totalWorkTime, 1);
        
        // Mark current cycle indicator as complete
        updateCycleIndicators(pomodoroConfig.currentCycle, true);
        
        // Check if we've completed all cycles
        if (pomodoroConfig.currentCycle >= pomodoroConfig.cycles) {
            // All cycles complete, start long break
            pomodoroConfig.isWorkPhase = false;
            pomodoroConfig.isLongBreak = true;
            sessionDuration = pomodoroConfig.longBreak;
            setCookie('pomodoroIsWorkPhase', false, 1);
            
            // Update status and start break interval
            startPomodoroInterval();
        } else {
            // Start short break
            pomodoroConfig.isWorkPhase = false;
            pomodoroConfig.isLongBreak = false;
            sessionDuration = pomodoroConfig.shortBreak;
            setCookie('pomodoroIsWorkPhase', false, 1);
            
            // Update status and start break interval
            startPomodoroInterval();
        }
    } else {
        // Break phase complete
        if (pomodoroConfig.isLongBreak) {
            // Long break complete, session is done
            stopFocusSession();
        } else {
            // Short break complete, start next work cycle
            pomodoroConfig.currentCycle++;
            setCookie('pomodoroCurrentCycle', pomodoroConfig.currentCycle, 1);
            
            pomodoroConfig.isWorkPhase = true;
            setCookie('pomodoroIsWorkPhase', true, 1);
            
            sessionDuration = pomodoroConfig.workDuration;
            
            // Update cycle indicators
            updateCycleIndicators(pomodoroConfig.currentCycle);
            
            // Start next work interval
            startPomodoroInterval();
        }
    }
}

function skipBreak() {
    // Can only skip breaks
    if (!pomodoroConfig.isWorkPhase) {
        clearInterval(pomodoroInterval);
        
        if (pomodoroConfig.isLongBreak) {
            // Long break skipped, session is done
            stopFocusSession();
        } else {
            // Short break skipped, start next work cycle
            pomodoroConfig.currentCycle++;
            setCookie('pomodoroCurrentCycle', pomodoroConfig.currentCycle, 1);
            
            pomodoroConfig.isWorkPhase = true;
            setCookie('pomodoroIsWorkPhase', true, 1);
            
            sessionDuration = pomodoroConfig.workDuration;
            
            // Update cycle indicators
            updateCycleIndicators(pomodoroConfig.currentCycle);
            
            // Start next work interval
            startPomodoroInterval();
        }
    }
}

function playNotificationSound() {
    // Create audio element for notification
    const audio = new Audio('/MizukiForest/media/audio/notification.mp3');
    audio.play().catch(error => console.log('Audio play failed:', error));
}

function updatePomodoroRewardsDisplay() {
    const workDurationInput = document.querySelector('#workDuration');
    const cyclesInput = document.querySelector('#pomodoroCycles');

    if (!workDurationInput || !cyclesInput) return;

    const workDuration = parseInt(workDurationInput.value);
    const cycles = parseInt(cyclesInput.value);
    
    // Calculate expected total focus time in seconds
    const totalFocusSeconds = workDuration * 60 * cycles;
    
    // Update rewards display based on this time
    updateRewardsDisplay(totalFocusSeconds);
}



function stopFocusSession() {
    // Clear intervals
    clearInterval(stopwatchInterval);
    clearInterval(timerInterval);
    clearInterval(pomodoroInterval);

    // Calculate final duration
    let finalDuration;
    let totalTreeBoxes;

    if (currentSessionType === 'Stopwatch') {
        finalDuration = sessionDuration;
        totalTreeBoxes = Math.floor(finalDuration / TREE_BOX_INTERVAL_SECONDS);
    } else if (currentSessionType === 'Timer') {
        // Get the initial duration from cookie
        const initialDuration = parseInt(getCookie('focusSessionInitialDuration')) || 0;
        // Calculate how much time has been used
        finalDuration = initialDuration - sessionDuration;
        totalTreeBoxes = Math.floor(finalDuration / TREE_BOX_INTERVAL_SECONDS);
    } else if (currentSessionType === 'Pomodoro') {
        // For Pomodoro, use the total work time
        finalDuration = pomodoroConfig.totalWorkTime;
        
        // Make sure finalDuration is never 0 by ensuring at least some work time is recorded
        if (finalDuration === 0 && pomodoroConfig.isWorkPhase && sessionDuration < pomodoroConfig.workDuration) {
            // If they've done some part of the current work session, count that time too
            finalDuration = pomodoroConfig.workDuration - sessionDuration;
        }
        
        totalTreeBoxes = Math.floor(finalDuration / TREE_BOX_INTERVAL_SECONDS);
    }

    // Determine selected reward
    const selectedReward = getSelectedReward();

    // Submit session to server
    submitFocusSession(finalDuration, totalTreeBoxes, selectedReward);

    // Clear cookies
    clearFocusSessionCookies();

    // Reset variables
    currentSessionType = null;
    sessionDuration = 0;
    sessionStartTime = 0;

    // Show completion modal
    showCompletionModal(finalDuration, totalTreeBoxes, selectedReward);
}

function getSelectedReward() {
    const selectedOption = document.querySelector('.reward-option.selected');
    if (!selectedOption) {
        // Default to common tree boxes
        return {
            id: TREE_BOXES.COMMON.id,
            quantity: Math.floor(sessionDuration / TREE_BOX_INTERVAL_SECONDS)
        };
    }

    // Parse the selected option to determine the reward
    const optionText = selectedOption.querySelector('p').textContent;
    let treeBoxId, quantity;

    if (optionText.includes(TREE_BOXES.LEGENDARY.name)) {
        treeBoxId = TREE_BOXES.LEGENDARY.id;
        quantity = parseInt(optionText.match(/x(\d+)/)[1]);
    } else if (optionText.includes(TREE_BOXES.EPIC.name)) {
        treeBoxId = TREE_BOXES.EPIC.id;
        quantity = parseInt(optionText.match(/x(\d+)/)[1]);
    } else if (optionText.includes(TREE_BOXES.RARE.name)) {
        treeBoxId = TREE_BOXES.RARE.id;
        quantity = parseInt(optionText.match(/x(\d+)/)[1]);
    } else {
        treeBoxId = TREE_BOXES.COMMON.id;
        quantity = parseInt(optionText.match(/x(\d+)/)[1]);
    }

    return {
        id: treeBoxId,
        quantity: quantity
    };
}

function submitFocusSession(duration, treeBoxesObtained, selectedReward) {
    // Create FormData object
    const formData = new FormData();

    // Add parameters to FormData
    formData.append('sessionType', currentSessionType);
    formData.append('duration', duration);
    formData.append('treeBoxesObtained', treeBoxesObtained);
    formData.append('treeBoxId', selectedReward.id);
    formData.append('treeBoxQuantity', selectedReward.quantity);

    // Add Pomodoro-specific parameters if applicable
    if (currentSessionType === 'Pomodoro') {
        formData.append('pomodoroWorkDuration', pomodoroConfig.workDuration / 60); // Convert to minutes
        formData.append('pomodoroShortBreak', pomodoroConfig.shortBreak / 60); // Convert to minutes
        formData.append('pomodoroLongBreak', pomodoroConfig.longBreak / 60); // Convert to minutes
        formData.append('pomodoroCycles', pomodoroConfig.cycles);
        formData.append('pomodoroCompletedCycles', pomodoroConfig.currentCycle - (pomodoroConfig.isWorkPhase ? 1 : 0));
        
        // Add the specific parameters the servlet is looking for
        formData.append('pomodoroMinorBreak', pomodoroConfig.shortBreak / 60); // Convert to minutes
        formData.append('pomodoroMajorBreak', pomodoroConfig.longBreak / 60); // Convert to minutes
    }

    // Log what's being sent for debugging
    console.log('Sending focus session data:');
    for (let pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }

    // Send data to server using fetch API
    fetch('/MizukiForest/FocusSessionServlet', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            // You can add additional handling here based on the response
        })
        .catch(error => {
            console.error('Error submitting focus session:', error);
            // Display error to user
            alert('Failed to save your focus session. Please try again.');
        });
}

function showCompletionModal(duration, treeBoxesObtained, selectedReward) {
    const modalContainer = document.createElement('div');
    modalContainer.className = 'completion-modal';
    let rewardImageSrc, rewardName;
    if (duration < TREE_BOX_INTERVAL_SECONDS) {
        // Less than 30 minutes - withered tree
        rewardImageSrc = TREE_BOXES.WITHERED.image;
        rewardName = TREE_BOXES.WITHERED.name;
    } else {
        // Get the reward image and name based on selected reward
        switch (selectedReward.id) {
            case TREE_BOXES.LEGENDARY.id:
                rewardImageSrc = TREE_BOXES.LEGENDARY.image;
                rewardName = TREE_BOXES.LEGENDARY.name;
                break;
            case TREE_BOXES.EPIC.id:
                rewardImageSrc = TREE_BOXES.EPIC.image;
                rewardName = TREE_BOXES.EPIC.name;
                break;
            case TREE_BOXES.RARE.id:
                rewardImageSrc = TREE_BOXES.RARE.image;
                rewardName = TREE_BOXES.RARE.name;
                break;
            default:
                rewardImageSrc = TREE_BOXES.COMMON.image;
                rewardName = TREE_BOXES.COMMON.name;
        }
    }
    const hours = Math.floor(duration / 3600);
    const minutes = Math.floor((duration % 3600) / 60);
    const seconds = duration % 60;
    const durationText = hours + 'h ' + minutes + 'm ' + seconds + 's';
    let message;
    if (duration < TREE_BOX_INTERVAL_SECONDS) {
        message = 
            '<h3>Focus Session Terminated</h3>' +
            '<p>You focused for ' + durationText + ', which is less than 30 minutes.</p>' +
            '<p>A ' + rewardName + ' has been planted in your forest.</p>';
    } else {
        message = 
            '<h3>Focus Session Completed</h3>' +
            '<p>Great job! You focused for ' + durationText + '.</p>' +
            '<p>You earned ' + selectedReward.quantity + ' ' + rewardName + 
            (selectedReward.quantity > 1 ? 'es' : '') + '!</p>';
    }
    modalContainer.innerHTML = 
        '<div class="completion-modal-content">' +
        '<button class="close-completion-btn">×</button>' +
        message +
        '<div class="reward-image">' +
        '<img src="' + rewardImageSrc + '" alt="' + rewardName + '">' +
        '</div>' +
        '<button class="ok-btn">OK</button>' +
        '</div>';
    document.body.appendChild(modalContainer);
    // Remove focus session modal
    const focusModal = document.querySelector('.focus-session-modal');
    if (focusModal) {
        document.body.removeChild(focusModal);
    }
    // Close and OK button event listeners with redirect
    const closeButton = modalContainer.querySelector('.close-completion-btn');
    const okButton = modalContainer.querySelector('.ok-btn');
    // Define the redirect function
    const redirectToForest = function() {
        // Update the session time cookie with the current exact time right before redirect
        if (currentSessionType) {
            // Update the current time in the cookie to the exact moment before redirect
            sessionStartTime = Date.now();
            setCookie('focusSessionStartTime', sessionStartTime, 1);
            // If it's a timer, update the remaining time cookie as well
            if (currentSessionType === 'Timer') {
                setCookie('focusSessionTimeRemaining', sessionDuration, 1);
            }
        }
        // Then redirect with a timestamp to prevent caching
        window.location.href = '/MizukiForest/GetUserForestData?time=' + new Date().getTime();
    };
    // Add event listeners to both buttons
    closeButton.addEventListener('click', redirectToForest);
    okButton.addEventListener('click', redirectToForest);
}

function setCookie(name, value, days) {
    const d = new Date();
    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

function getCookie(name) {
    const cname = name + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(cname) == 0) {
            return c.substring(cname.length, c.length);
        }
    }
    return "";
}

function clearFocusSessionCookies() {
    setCookie('focusSessionType', '', 0);
    setCookie('focusSessionStartTime', '', 0);
    setCookie('focusSessionTimeRemaining', '', 0);
    setCookie('focusSessionInitialDuration', '', 0);
    
    // Clear Pomodoro-specific cookies
    setCookie('pomodoroWorkDuration', '', 0);
    setCookie('pomodoroShortBreak', '', 0);
    setCookie('pomodoroLongBreak', '', 0);
    setCookie('pomodoroCycles', '', 0);
    setCookie('pomodoroCurrentCycle', '', 0);
    setCookie('pomodoroIsWorkPhase', '', 0);
    setCookie('pomodoroTotalWorkTime', '', 0);
}