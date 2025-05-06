$(document).ready(function () {
    // Create level modal structure
    enhanceLevelModalAnimations();
    initSoundEffects();
    createLevelModal();

    // Add event listener to level button
    $("#levelBtn").click(function () {
        $("#levelModal").modal("show");
        // Slight delay before loading data to ensure modal is visible for animations
        setTimeout(function () {
            enhancedLoadUserLevelData();
        }, 300);
    });

    // Initialize tooltip system for when modal is opened
    $('#levelModal').on('shown.bs.modal', function () {
        initRewardItemTooltips();
    });
    // Destroy the modal when it's hidden
    $(document).on('hidden.bs.modal', '#levelModal', function () {
        $(this).remove();
        $("#levelModalStyles").remove();
        $("#levelEnhancedAnimations").remove();
        createLevelModal(); // Recreate the modal structure for next time
    });


});

// Create the level modal structure
function createLevelModal() {
    // Add animations for level-up effects
    const enhancedAnimations = `
        @keyframes levelPulse {
            0% { transform: scale(0.8); opacity: 0; }
            50% { transform: scale(1.1); opacity: 1; }
            100% { transform: scale(1); opacity: 1; }
        }
        
        @keyframes levelUpBurst {
            0% { 
                transform: scale(1);
                box-shadow: 0 0 0 0 rgba(46, 125, 50, 0.7);
            }
            20% { 
                transform: scale(1.2);
                box-shadow: 0 0 0 10px rgba(46, 125, 50, 0.7);
            }
            40% { 
                transform: scale(0.9);
                box-shadow: 0 0 0 20px rgba(46, 125, 50, 0);
            }
            60% { 
                transform: scale(1.1);
                box-shadow: 0 0 0 10px rgba(46, 125, 50, 0.5);
            }
            100% { 
                transform: scale(1);
                box-shadow: 0 0 0 0 rgba(46, 125, 50, 0);
            }
        }
        
        @keyframes textGlow {
            0% { text-shadow: 0 0 0 rgba(255, 255, 255, 0); }
            50% { text-shadow: 0 0 10px rgba(255, 255, 255, 0.8); }
            100% { text-shadow: 0 0 0 rgba(255, 255, 255, 0); }
        }
        
        @keyframes progressFlash {
            0% { background-color: #e9ecef; }
            25% { background-color: rgba(67, 160, 71, 0.3); }
            50% { background-color: rgba(67, 160, 71, 0.2); }
            100% { background-color: #e9ecef; }
        }
        
        .level-circle {
            animation: levelPulse 0.5s ease-out 1.5s;
            animation-fill-mode: backwards;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .level-up-burst {
            animation: levelUpBurst 1s ease-out;
        }
        
        .level-circle span {
            display: inline-block;
            transition: all 0.3s;
        }
        
        .level-up-text {
            animation: textGlow 2s ease-in-out;
        }
        
        .progress-flash {
            animation: progressFlash 1s ease-in-out;
        }
        
        .level-particle {
            pointer-events: none;
        }
        
        .level-shockwave {
            pointer-events: none;
        }
    `;

    const levelModal = `
        <div class="modal fade" id="levelModal" tabindex="-1" role="dialog" aria-labelledby="levelModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="levelModalLabel">Your Level Progress</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="level-info-container">
                            <div class="current-level">
                                <div class="level-circle">
                                    <span id="currentLevelValue">0</span>
                                </div>
                                <h4 id="currentLevelText">Current Level</h4>
                            </div>
                            
                            <div class="level-progress-container">
                                <div class="level-progress-label">
                                    <span id="currentExpValue">0</span> / <span id="nextLevelExpValue">100</span> XP
                                </div>
                                <div class="progress level-progress">
                                    <div id="expProgressBar" class="progress-bar bg-success" role="progressbar" style="width: 0%"></div>
                                </div>
                                <div class="exp-info">
                                    Complete tasks, plant trees, and write diary entries to earn more XP and level up to get rewards!
                                </div>
                            </div>
                        </div>
                        
                        <h4 class="mt-4">Level Rewards</h4>
                        <div class="level-rewards-container">
                            <div id="levelRewardsLoading" class="text-center">
                                <div class="spinner-border text-primary" role="status">
                                    <span class="sr-only">Loading...</span>
                                </div>
                            </div>
                            <div id="levelRewardsList"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" id="closeModalBtn">Got It!</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    // Remove existing modal if it exists
    $("#levelModal").remove();

    // Append modal to body
    $('body').append(levelModal);

    // Add CSS for level modal
    const levelStyles = `
        <style id="levelModalStyles">
            .level-info-container {
                display: flex;
                align-items: center;
                margin-bottom: 20px;
                background-color: #f8f9fa;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            
            .current-level {
                text-align: center;
                margin-right: 30px;
            }
            
            .level-circle {
                width: 80px;
                height: 80px;
                border-radius: 50%;
                background: linear-gradient(135deg, #43a047, #2e7d32);
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 32px;
                font-weight: bold;
                margin: 0 auto 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
                border: 4px solid white;
            }
            
            .level-progress-container {
                flex-grow: 1;
            }
            
            .level-progress {
                height: 20px;
                margin-bottom: 10px;
                background-color: #e9ecef;
                box-shadow: inset 0 1px 2px rgba(0,0,0,0.1);
            }
            
            .level-progress .progress-bar {
                background: linear-gradient(90deg, #43a047, #2e7d32);
                transition: width 1s ease;
                box-shadow: 0 1px 2px rgba(0,0,0,0.2);
            }
            
            .level-progress-label {
                display: flex;
                justify-content: flex-end;
                margin-bottom: 5px;
                font-weight: bold;
                color: #495057;
            }
            
            .total-exp {
                color: #495057;
                font-weight: bold;
                margin-top: 5px;
                font-size: 0.9em;
            }
            
            .exp-info {
                color: #6c757d;
                font-style: italic;
                margin-top: 5px;
                font-size: 0.9em;
            }
            
            .level-rewards-container {
                max-height: 300px;
                overflow-y: auto;
                border: 1px solid #dee2e6;
                border-radius: 5px;
                padding: 10px;
            }
            
            .reward-row {
                display: flex;
                align-items: center;
                padding: 12px;
                border-bottom: 1px solid #e9ecef;
                background-color: #f8f9fa;
                margin-bottom: 10px;
                border-radius: 5px;
                transition: all 0.3s ease;
            }
            
            .reward-row:hover {
                background-color: #f1f3f5;
                transform: translateY(-2px);
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            
            .level-number {
                width: 36px;
                height: 36px;
                border-radius: 50%;
                background-color: #2e7d32;
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: bold;
                margin-right: 15px;
                flex-shrink: 0;
            }
            
            .reward-items {
                display: flex;
                flex-grow: 1;
                flex-wrap: wrap;
                gap: 8px;
            }
            
            .reward-item {
                display: flex;
                align-items: center;
                background-color: white;
                padding: 4px 8px;
                border-radius: 5px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.1);
                font-size: 0.9em;
            }
            
            .reward-item img {
                width: 30px;
                height: 30px;
                object-fit: contain;
                margin-right: 8px;
                border-radius: 4px;
                background-color: #f5f5f5;
            }
            
            .reward-status {
                margin-left: auto;
                flex-shrink: 0;
                width: 70px;
                text-align: center;
            }
            
            .claimed-badge {
                background-color: #28a745;
                color: white;
                padding: 3px 8px;
                border-radius: 20px;
                font-size: 11px;
                font-weight: bold;
            }
            
            .not-claimed-badge {
                background-color: #6c757d;
                color: white;
                padding: 3px 8px;
                border-radius: 20px;
                font-size: 11px;
                font-weight: bold;
            }
            
            .coming-soon-row {
                background-color: #e2f0d9;
                color: #495057;
                font-style: italic;
                text-align: center;
                padding: 10px;
                border-radius: 5px;
                margin-top: 10px;
            }
            
            .coming-soon-text {
                font-weight: bold;
                font-size: 1.1em;
                color: #2e7d32;
            }
            
            @media (max-width: 768px) {
                .level-info-container {
                    flex-direction: column;
                }
                
                .current-level {
                    margin-right: 0;
                    margin-bottom: 20px;
                }
                
                .reward-row {
                    flex-direction: column;
                    align-items: flex-start;
                }
                
                .level-number {
                    margin-bottom: 10px;
                }
                
                .reward-status {
                    margin-left: 0;
                    margin-top: 10px;
                    width: 100%;
                    text-align: left;
                }
            }
        </style>
    `;

    // Remove existing style if it exists
    $("#levelModalStyles").remove();

    // Add style element with animations
    $('head').append(levelStyles);
    $('head').append(`<style id="levelEnhancedAnimations">${enhancedAnimations}</style>`);

    // Add jQuery easing if not present
    if ($.easing.easeOutQuad === undefined) {
        // Add easing functions
        $.extend($.easing, {
            easeOutQuad: function (x, t, b, c, d) {
                return -c * (t /= d) * (t - 2) + b;
            },
            easeOutCubic: function (x, t, b, c, d) {
                return c * ((t = t / d - 1) * t * t + 1) + b;
            },
            easeOutBack: function (x, t, b, c, d, s) {
                if (s === undefined)
                    s = 1.70158;
                return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
            }
        });
    }

    // Initialize Bootstrap components
    $('#levelModal').modal({
        show: false,
        keyboard: true,
        backdrop: true
    });

    // Add event listeners for modal closing
    $(document).on('click', '.close, #closeModalBtn', function () {
        $("#levelModal").modal('hide');
    });

    // Play sound when modal opens
    $('#levelModal').on('shown.bs.modal', function () {
        playSound('level-up');
    });
}

function initSoundEffects() {
    // Create sound objects if they don't exist
    if ($("#levelUpSound").length === 0) {
        const levelUpSound = $('<audio id="levelUpSound" preload="auto">');
        levelUpSound.append('<source src="' + contextPath + '/media/audio/minecraftxp.mp3" type="audio/mpeg">');
        $('body').append(levelUpSound);
    }
}

// Play sound function - updated to handle multiple sound types
function playSound(soundType) {
    let sound;

    if (soundType === 'level-up') {
        sound = $("#levelUpSound")[0];
    } 

    if (sound) {
        sound.currentTime = 0;
        sound.play().catch(e => console.log("Sound play prevented:", e));
    }
}

// Load user level data via AJAX
function enhancedLoadUserLevelData() {
    // Show loading spinner and reset any previous animations
    $("#levelRewardsLoading").show();
    $("#levelRewardsList").empty();
    $("#currentLevelValue").text("0");
    $("#currentExpValue").text("0");
    $("#totalExpValue").text("0");
    $("#expProgressBar").css("width", "0%");

    $.ajax({
        url: contextPath + '/UserLevelServlet',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            // Play modal sound if it's the first load
            playSound('level-up');

            // Update user level info
            updateUserLevelInfo(data);

            // Use enhanced function to populate rewards list
            enhancedPopulateRewardsList(data);

            // Initialize tooltip system
            initRewardItemTooltips();

            // Hide loading spinner
            $("#levelRewardsLoading").hide();

            // Check if user just leveled up
            if (data.justLeveledUp === true) {
                // Play the level up animation and sound
                animateLevelUp(data.previousLevel, data.userLevel, data.userExp - data.totalExpForCurrentLevel, data.nextLevelRequiredExp);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error loading level data:', error);
            $("#levelRewardsList").html(
                    '<div class="alert alert-danger">Error loading level data. Please try again later.</div>'
                    );
            $("#levelRewardsLoading").hide();
        }
    });
}

// Update user level information with enhanced animations
function updateUserLevelInfo(data) {
    const currentLevel = data.userLevel;
    const currentExp = data.userExp;
    const requiredExp = data.nextLevelRequiredExp;
    const totalExpForCurrentLevel = data.totalExpForCurrentLevel || 0;

    // XP progress is the amount over the previous level threshold
    const progressExp = currentExp - totalExpForCurrentLevel;

    // Calculate progress percentage toward next level
    const progressPercentage = Math.min(100, (progressExp / requiredExp) * 100);

    // Reset initial values for animation
    $("#currentLevelValue").text("0");
    $("#currentExpValue").text("0");
    $("#nextLevelExpValue").text(requiredExp);
    $("#expProgressBar").css("width", "0%");

    // Create an array of intermediate levels with delays
    const stepCount = currentLevel;
    const steps = [];

    // Calculate time per step - make earlier levels faster, current level more dramatic
    const totalDuration = 1500; // total animation time in ms
    let timePerEarlyLevel = 100; // ms for early levels
    let currentLevelTime = 500; // ms for the final/current level

    // Calculate remaining time for intermediate levels
    const remainingTime = totalDuration - currentLevelTime - ((currentLevel - 1) * timePerEarlyLevel);
    const intermediateTime = Math.max(remainingTime / Math.max(1, currentLevel - 2), timePerEarlyLevel);

    // Create animation steps with increasing durations
    let totalDelay = 0;
    for (let i = 1; i <= stepCount; i++) {
        let stepTime;
        if (i < currentLevel - 1) {
            // Earlier levels are quicker
            stepTime = timePerEarlyLevel;
        } else if (i === currentLevel - 1) {
            // Second-to-last level is intermediate
            stepTime = intermediateTime;
        } else {
            // Current level gets the most time
            stepTime = currentLevelTime;
        }

        steps.push({
            level: i,
            delay: totalDelay,
            duration: stepTime
        });

        totalDelay += stepTime;
    }

    // Animate through each level step
    steps.forEach(step => {
        setTimeout(() => {
            $("#currentLevelValue").text(step.level);

            // Apply visual emphasis for the final level
            if (step.level === currentLevel) {
                $(".level-circle").addClass("level-up-burst");

                // After a short delay, create the shockwave effect
                setTimeout(function () {
                    createLevelShockwave();
                }, 300);
            }
        }, step.delay);
    });

    // Animate XP counter from 0 to current progress XP
    $({expValue: 0}).animate({expValue: progressExp}, {
        duration: 1500,
        easing: 'easeOutQuad',
        step: function () {
            $("#currentExpValue").text(Math.floor(this.expValue));
        },
        complete: function () {
            $("#currentExpValue").text(progressExp);
        }
    });

    // Animate progress bar from 0% to current percentage
    $("#expProgressBar").animate({
        width: progressPercentage + "%"
    }, 1500, 'easeOutQuad');
}

// Enhanced level-up animation with more dramatic effects
function animateLevelUp(previousLevel, newLevel, newExp, requiredExp) {
    // Create burst animation particles
    createLevelUpBurst();

    // Play level up sound
    playSound('level-up');

    // First animate progress bar to 100%
    $("#expProgressBar").animate({
        width: "100%"
    }, 800, 'easeOutQuad', function () {
        // Add flash effect when progress bar is full
        $(".level-progress").addClass("progress-flash");

        // Short pause before level up
        setTimeout(function () {
            // Create burst effect around level circle
            $(".level-circle").addClass("level-up-burst");

            // Animate the level number with a bounce effect
            $({levelValue: previousLevel}).animate({levelValue: newLevel}, {
                duration: 1200,
                easing: 'easeOutBack',
                step: function () {
                    $("#currentLevelValue").text(Math.floor(this.levelValue));
                },
                complete: function () {
                    $("#currentLevelValue").text(newLevel);

                    // After level animation completes, reset progress bar
                    setTimeout(function () {
                        // Remove flash effect
                        $(".level-progress").removeClass("progress-flash");

                        // Reset progress bar to start from 0
                        $("#expProgressBar").css("width", "0%");

                        // Calculate new progress percentage
                        const progressPercentage = Math.min(100, (newExp / requiredExp) * 100);

                        // Then animate to current percentage
                        $("#expProgressBar").animate({
                            width: progressPercentage + "%"
                        }, 800, 'easeOutQuad');

                        // Animate current XP counter
                        $({expValue: 0}).animate({expValue: newExp}, {
                            duration: 800,
                            easing: 'easeOutQuad',
                            step: function () {
                                $("#currentExpValue").text(Math.floor(this.expValue));
                            },
                            complete: function () {
                                $("#currentExpValue").text(newExp);
                            }
                        });
                    }, 500);
                }
            });
        }, 800);
    });
}

// Create burst particles for level up effect
function createLevelUpBurst() {
    // Remove any existing particles
    $(".level-particle").remove();

    // Get position and dimensions of level circle
    const levelCircle = $(".level-circle");
    const circleOffset = levelCircle.offset();
    const circleWidth = levelCircle.outerWidth();
    const circleHeight = levelCircle.outerHeight();

    // Create container for particles if it doesn't exist
    if ($("#particleContainer").length === 0) {
        $("body").append('<div id="particleContainer"></div>');
    }

    // Position the particle container over the level circle
    const centerX = circleOffset.left + circleWidth / 2;
    const centerY = circleOffset.top + circleHeight / 2;

    // Create particles
    const particleCount = 30;
    const colors = ['#43a047', '#2e7d32', '#ffd54f', '#ffb300', '#ff9800', '#ffffff'];

    for (let i = 0; i < particleCount; i++) {
        const size = Math.random() * 10 + 5;
        const color = colors[Math.floor(Math.random() * colors.length)];
        const angle = Math.random() * Math.PI * 2;
        const distance = Math.random() * 60 + 30;
        const delay = Math.random() * 200;
        const duration = Math.random() * 1000 + 1000;

        const particle = $('<div class="level-particle"></div>');
        particle.css({
            position: 'absolute',
            width: size + 'px',
            height: size + 'px',
            backgroundColor: color,
            borderRadius: '50%',
            left: centerX + 'px',
            top: centerY + 'px',
            opacity: 1,
            transform: 'translate(-50%, -50%)',
            zIndex: 9999,
            boxShadow: '0 0 ' + size / 2 + 'px ' + color
        });

        $("#particleContainer").append(particle);

        // Animate particles
        setTimeout(function () {
            particle.animate({
                left: centerX + Math.cos(angle) * distance + 'px',
                top: centerY + Math.sin(angle) * distance + 'px',
                opacity: 0
            }, {
                duration: duration,
                easing: 'easeOutCubic',
                complete: function () {
                    $(this).remove();
                }
            });
        }, delay);
    }
}

// Add "shock wave" effect to level circle
function createLevelShockwave() {
    const levelCircle = $(".level-circle");
    const circleOffset = levelCircle.offset();
    const circleWidth = levelCircle.outerWidth();
    const circleHeight = levelCircle.outerHeight();

    // Create shockwave element
    const shockwave = $('<div class="level-shockwave"></div>');
    shockwave.css({
        position: 'absolute',
        width: circleWidth + 'px',
        height: circleHeight + 'px',
        left: circleOffset.left + circleWidth / 2 + 'px',
        top: circleOffset.top + circleHeight / 2 + 'px',
        border: '2px solid #43a047',
        borderRadius: '50%',
        transform: 'translate(-50%, -50%) scale(1)',
        opacity: 0.8,
        zIndex: 9998
    });

    $("body").append(shockwave);

    // Animate shockwave
    shockwave.animate({
        transform: 'translate(-50%, -50%) scale(3)',
        opacity: 0
    }, {
        duration: 800,
        easing: 'easeOutCubic',
        complete: function () {
            $(this).remove();
        }
    });
}

function enhanceLevelModalAnimations() {
    // Add animations for level-up effects
    const enhancedAnimations = `
        @keyframes levelPulse {
            0% { transform: scale(0.8); opacity: 0; }
            50% { transform: scale(1.1); opacity: 1; }
            100% { transform: scale(1); opacity: 1; }
        }
        
        @keyframes levelUpBurst {
            0% { 
                transform: scale(1);
                box-shadow: 0 0 0 0 rgba(46, 125, 50, 0.7);
            }
            20% { 
                transform: scale(1.2);
                box-shadow: 0 0 0 10px rgba(46, 125, 50, 0.7);
            }
            40% { 
                transform: scale(0.9);
                box-shadow: 0 0 0 20px rgba(46, 125, 50, 0);
            }
            60% { 
                transform: scale(1.1);
                box-shadow: 0 0 0 10px rgba(46, 125, 50, 0.5);
            }
            100% { 
                transform: scale(1);
                box-shadow: 0 0 0 0 rgba(46, 125, 50, 0);
            }
        }
        
        @keyframes textGlow {
            0% { text-shadow: 0 0 0 rgba(255, 255, 255, 0); }
            50% { text-shadow: 0 0 10px rgba(255, 255, 255, 0.8); }
            100% { text-shadow: 0 0 0 rgba(255, 255, 255, 0); }
        }
        
        @keyframes progressFlash {
            0% { background-color: #e9ecef; }
            25% { background-color: rgba(67, 160, 71, 0.3); }
            50% { background-color: rgba(67, 160, 71, 0.2); }
            100% { background-color: #e9ecef; }
        }
        
        .level-circle {
            animation: levelPulse 0.5s ease-out 1.5s;
            animation-fill-mode: backwards;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .level-up-burst {
            animation: levelUpBurst 1s ease-out;
        }
        
        .level-circle span {
            display: inline-block;
            transition: all 0.3s;
        }
        
        .level-up-text {
            animation: textGlow 2s ease-in-out;
        }
        
        .progress-flash {
            animation: progressFlash 1s ease-in-out;
        }
        
        .level-particle {
            pointer-events: none;
        }
        
        .level-shockwave {
            pointer-events: none;
        }
    `;

    // Add our enhanced animations to the head
    $('head').append(`<style id="levelEnhancedAnimations">${enhancedAnimations}</style>`);

    // Add jQuery easing if not present
    if ($.easing.easeOutQuad === undefined) {
        // Add easing functions
        $.extend($.easing, {
            easeOutQuad: function (x, t, b, c, d) {
                return -c * (t /= d) * (t - 2) + b;
            },
            easeOutCubic: function (x, t, b, c, d) {
                return c * ((t = t / d - 1) * t * t + 1) + b;
            },
            easeOutBack: function (x, t, b, c, d, s) {
                if (s === undefined)
                    s = 1.70158;
                return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
            }
        });
    }
}

// Populate rewards list
function enhancedPopulateRewardsList(data) {
    const rewardsList = $("#levelRewardsList");
    const userLevel = data.userLevel;
    const levels = data.levels;
    const rewards = data.rewards;

    // Clear existing content
    rewardsList.empty();

    // Sort levels by level ID
    levels.sort((a, b) => a.levelid - b.levelid);

    // Define a range of levels to display (1 to maximum level + 1 for "coming soon")
    const maxLevel = levels.length > 0 ? Math.max(...levels.map(l => l.levelid)) : 0;
    const totalLevelsToShow = maxLevel + 1; // Include "coming soon" level

    // Create reward rows for each level (including levels with no rewards)
    for (let levelId = 1; levelId <= totalLevelsToShow; levelId++) {
        // Find level data
        const levelData = levels.find(level => level.levelid === levelId);

        // Find rewards for this level
        const levelRewards = rewards.filter(reward => reward.levelId === levelId);

        // Create reward row
        const rewardRow = $(`
            <div class="reward-row ${levelId === totalLevelsToShow ? 'coming-soon-row' : ''}">
                <div class="level-number">${levelId}</div>
                ${levelId === totalLevelsToShow ?
                '<div class="coming-soon-text">Coming Soon!</div>' :
                '<div class="reward-items"></div>'}
                ${levelId !== totalLevelsToShow ?
                `<div class="reward-status">
                        ${userLevel >= levelId ?
                '<span class="claimed-badge">Claimed</span>' :
                '<span class="not-claimed-badge">Locked</span>'}
                    </div>` : ''}
            </div>
        `);

        // Add reward items to the row
        if (levelId !== totalLevelsToShow) {
            const rewardItemsContainer = rewardRow.find('.reward-items');

            if (levelRewards.length > 0) {
                levelRewards.forEach(reward => {
                    // Determine if it's an item or treebox
                    const itemInfo = reward.itemInfo || reward.treeboxInfo;

                    if (itemInfo) {
                        // Extract item type if available
                        let itemType = 'unknown';
                        if (reward.itemInfo && reward.itemInfo.type) {
                            itemType = reward.itemInfo.type;
                        } else if (reward.treeboxInfo) {
                            itemType = 'treebox';
                        } else if (itemInfo.name) {
                            // Try to extract type from name
                            const nameLower = itemInfo.name.toLowerCase();
                            if (nameLower.includes('treebox')) {
                                itemType = 'treebox';
                            } else if (nameLower.includes('tree')) {
                                itemType = 'tree';
                            } else if (nameLower.includes('seed')) {
                                itemType = 'seed';
                            } else {
                                itemType = 'item';
                            }
                        }

                        // Add data attributes for tooltip information
                        const rewardItem = $(`
                            <div class="reward-item" 
                                data-item-name="${itemInfo.name}"
                                data-item-type="${itemType}"
                                data-item-quantity="${reward.quantity}">
                                <img src="${contextPath}${itemInfo.image || '/images/placeholder.png'}" alt="${itemInfo.name}">
                                <span>${itemInfo.name}</span>
                                ${reward.quantity > 1 ? ` <span class="badge badge-pill badge-secondary">x${reward.quantity}</span>` : ''}
                            </div>
                        `);
                        rewardItemsContainer.append(rewardItem);
                    }
                });
            } else {
                // If no rewards for this level, add a placeholder
                rewardItemsContainer.append(`
                    <div class="reward-item">
                        <span>No rewards for this level</span>
                    </div>
                `);
            }
        }

        // Add the row to the list
        rewardsList.append(rewardRow);
    }
}

// First, add the necessary CSS for the tooltip/mini-modal
function addRewardTooltipStyles() {
    const tooltipStyles = `
        <style id="rewardTooltipStyles">
            .reward-tooltip {
                position: absolute;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
                padding: 12px;
                z-index: 10000;
                display: none;
                width: 220px;
                pointer-events: none;
                animation: tooltipFadeIn 0.2s ease-out;
                border: 2px solid #43a047;
            }
            
            @keyframes tooltipFadeIn {
                from { opacity: 0; transform: translateY(10px); }
                to { opacity: 1; transform: translateY(0); }
            }
            
            .reward-tooltip-image {
                width: 100%;
                height: 120px;
                background-color: #f8f9fa;
                border-radius: 5px;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-bottom: 10px;
                border: 1px solid #e9ecef;
                overflow: hidden;
            }
            
            .reward-tooltip-image img {
                max-width: 100%;
                max-height: 100%;
                object-fit: contain;
            }
            
            .reward-tooltip-title {
                font-weight: bold;
                font-size: 16px;
                margin-bottom: 5px;
                color: #2e7d32;
                text-align: center;
            }
            
            .reward-tooltip-description {
                font-size: 13px;
                color: #495057;
                margin-bottom: 8px;
                text-align: center;
            }
            
            .reward-tooltip-details {
                font-size: 12px;
                background-color: #f8f9fa;
                padding: 6px;
                border-radius: 4px;
                border-left: 3px solid #43a047;
            }
            
            .reward-tooltip::after {
                content: '';
                position: absolute;
                bottom: -8px;
                left: 50%;
                transform: translateX(-50%);
                width: 0;
                height: 0;
                border-left: 8px solid transparent;
                border-right: 8px solid transparent;
                border-top: 8px solid white;
            }
            
            .tooltip-top::after {
                top: -8px;
                bottom: auto;
                border-top: none;
                border-bottom: 8px solid white;
            }
        </style>
    `;

    // Remove existing style if it exists
    $("#rewardTooltipStyles").remove();

    // Add the styles to head
    $('head').append(tooltipStyles);
}

// Create and initialize the tooltip container
function initRewardTooltip() {
    // Remove existing tooltip if it exists
    $("#rewardTooltip").remove();

    // Create tooltip container
    const tooltip = $(`
        <div id="rewardTooltip" class="reward-tooltip">
            <div class="reward-tooltip-image">
                <img id="tooltipImage" src="" alt="Item preview">
            </div>
            <div class="reward-tooltip-title" id="tooltipTitle"></div>
            <div class="reward-tooltip-description" id="tooltipDescription"></div>
            <div class="reward-tooltip-details" id="tooltipDetails"></div>
        </div>
    `);

    // Append tooltip to body
    $('body').append(tooltip);
}

// Add event listeners to reward items
function addRewardHoverEvents() {
    // Add hover events to reward items
    $(document).on('mouseenter', '.reward-item', function (e) {
        // Get item data
        const $item = $(this);
        const imgSrc = $item.find('img').attr('src');
        const itemName = $item.find('span').first().text();
        const quantity = $item.find('.badge').length > 0 ?
                $item.find('.badge').text().replace('x', '') : '1';

        // Get item type from data attribute (set in enhancedPopulateRewardsList)
        const itemType = $item.data('item-type') || 'unknown';

        // Set description based on item type
        let description = '';

        if (itemType === 'treebox') {
            description = "Mystery box with various rewards inside.";
        } else if (itemName.toUpperCase() === "SHOVEL") {
            description = "A utility to remove withered trees in your forest.";
        } else if (itemType === 'tree') {
            description = "A tree to plant in your virtual garden.";
        } else if (itemType === 'seed') {
            description = "Seeds to grow plants in your garden.";
        } else if (itemType !== 'unknown') {
            // If we have a type but it's not one of the special cases above
            description = `${itemType} for your collection.`;
        } else {
            // Fallback to name-based detection if type is unknown
            if (itemName.toLowerCase().includes('tree') && !itemName.toLowerCase().includes('treebox')) {
                description = "A beautiful tree to plant in your virtual garden.";
            } else if (itemName.toLowerCase().includes('treebox')) {
                description = "Mystery box with various rewards inside.";
            } else if (itemName.toLowerCase().includes('seed')) {
                description = "Seeds to grow plants in your garden.";
            } else {
                description = "A special item for your collection.";
            }
        }

        // Set tooltip content
        $('#tooltipImage').attr('src', imgSrc);
        $('#tooltipTitle').text(itemName);
        $('#tooltipDescription').text(description);
        $('#tooltipDetails').text('Quantity: ' + quantity);

        // Position tooltip
        const $tooltip = $('#rewardTooltip');
        const itemOffset = $item.offset();
        const itemWidth = $item.outerWidth();
        const itemHeight = $item.outerHeight();
        const tooltipHeight = $tooltip.outerHeight();
        const tooltipWidth = $tooltip.outerWidth();

        // Check if there's room above the item
        const windowScrollTop = $(window).scrollTop();
        const isEnoughRoomAbove = (itemOffset.top - tooltipHeight - 10) >= windowScrollTop;

        // Position tooltip above or below the item
        let top, left;

        if (isEnoughRoomAbove) {
            // Position above
            top = itemOffset.top - tooltipHeight - 10;
            $tooltip.removeClass('tooltip-top');
        } else {
            // Position below
            top = itemOffset.top + itemHeight + 10;
            $tooltip.addClass('tooltip-top');
        }

        // Center horizontally
        left = itemOffset.left + (itemWidth / 2) - (tooltipWidth / 2);

        // Ensure tooltip stays within window bounds
        const windowWidth = $(window).width();
        if (left < 10)
            left = 10;
        if (left + tooltipWidth > windowWidth - 10)
            left = windowWidth - tooltipWidth - 10;

        // Set position and show tooltip
        $tooltip.css({
            top: top + 'px',
            left: left + 'px'
        }).fadeIn(200);
    });

    // Hide tooltip on mouse leave
    $(document).on('mouseleave', '.reward-item', function () {
        $('#rewardTooltip').fadeOut(150);
    });

    // Hide tooltip when scrolling in the rewards container
    $('.level-rewards-container').on('scroll', function () {
        $('#rewardTooltip').fadeOut(150);
    });
}

// Initialize the entire tooltip system
function initRewardItemTooltips() {
    addRewardTooltipStyles();
    initRewardTooltip();
    addRewardHoverEvents();
}