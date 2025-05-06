<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </head>

    <div class="container mt-5" style='overflow:hidden'>
        <div id="music-player-container" style="position:fixed;  bottom:0; width:80%; background:#fff; z-index:9999;" class="card-body d-flex flex-column align-items-center">
            <h5 id="nowPlaying" class="mb-3 text-center">Now Playing: -</h5>
            <audio id="audioPlayer" autoplay controls class="w-100 mb-3">
                <source src="" type="audio/mp3">
            </audio>

            <div class="d-flex flex-wrap justify-content-center gap-2">
                <button id="prevBtn" class="btn btn-outline-secondary"><i class="bi bi-skip-backward-fill"></i> Prev</button>
                <button id="rewindBtn" class="btn btn-outline-warning"><i class="bi bi-skip-start-fill"></i> Rewind</button>
                <button id="playPauseBtn" class="btn btn-outline-primary"><i class="bi bi-play-fill"></i> Play</button>
                <!--            <button id="skipBtn" class="btn btn-outline-warning"><i class="bi bi-skip-end-fill"></i> Skip</button>-->
                <button id="nextBtn" class="btn btn-outline-secondary"><i class="bi bi-skip-forward-fill"></i> Next</button>
                <button id="repeatBtn" class="btn btn-outline-info"><i class="bi bi-repeat"></i> Repeat All</button>
                <button id="shuffleBtn" class="btn btn-outline-dark"><i class="bi bi-shuffle"></i> Shuffle OFF</button>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

    <script>
        // === SAVE MUSIC STATE BEFORE PAGE UNLOAD ===
// This needs to be one of the first scripts that runs
document.addEventListener('DOMContentLoaded', function() {
    const audio = document.getElementById("audioPlayer");
    
    // Restore audio state from session storage
    function restoreAudioState() {
        // Get saved values
        const savedTime = parseFloat(sessionStorage.getItem("audioCurrentTime") || "0");
        const wasPlaying = sessionStorage.getItem("isPlaying") === "true";
        const savedSrc = sessionStorage.getItem("audioSrc");
        const nowPlayingText = sessionStorage.getItem("nowPlayingText");
        
        // Only restore if we have a saved source
        if (savedSrc) {
            // Set the source and position
            if (audio.src !== savedSrc) {
                audio.src = savedSrc;
            }
            
            // Update now playing text if available
            const nowPlayingEl = document.getElementById("nowPlaying");
            if (nowPlayingEl && nowPlayingText) {
                nowPlayingEl.innerText = nowPlayingText;
            }
            
            // Handle play state restoration
            audio.onloadedmetadata = function() {
                // Set the current time
                audio.currentTime = savedTime;
                
                // If it was playing, try to resume playback
                if (wasPlaying) {
                    const playPromise = audio.play();
                    
                    if (playPromise !== undefined) {
                        playPromise.then(() => {
                            // Playback resumed successfully
                            const playBtn = document.getElementById("playPauseBtn");
                            if (playBtn) {
                                playBtn.innerHTML = '<i class="bi bi-pause-fill"></i> Pause';
                            }
                        }).catch(err => {
                            console.log("Auto-resume prevented:", err);
                            // User interaction needed for playback
                        });
                    }
                }
            };
        }
    }
    
    // Call restore function when page loads
    restoreAudioState();
    
    // Save current state before the page unloads
    window.addEventListener("beforeunload", () => {
        if (audio) {
            sessionStorage.setItem("audioCurrentTime", audio.currentTime);
            sessionStorage.setItem("isPlaying", !audio.paused);
            sessionStorage.setItem("audioSrc", audio.src);
            
            const nowPlayingEl = document.getElementById("nowPlaying");
            if (nowPlayingEl) {
                sessionStorage.setItem("nowPlayingText", nowPlayingEl.innerText);
            }
        }
    });
});
    </script>

    <script>
        let originalQueue = JSON.parse(sessionStorage.getItem("originalQueue")) || [];
        let shuffledQueue = [...originalQueue];
        let queue = shuffledQueue;
        let currentIndex = parseInt(sessionStorage.getItem("currentIndex") || "0");
        let repeatMode = sessionStorage.getItem("repeatMode") || "repeatAll"; // 'repeatAll', 'repeatOne', 'noRepeat'
        let shuffleMode = sessionStorage.getItem("shuffleMode") === "true"; // true or false

        const audio = document.getElementById("audioPlayer");
        const nowPlaying = document.getElementById("nowPlaying");
        const playBtn = document.getElementById("playPauseBtn");
        const repeatBtn = document.getElementById("repeatBtn");
        const shuffleBtn = document.getElementById("shuffleBtn");

        const repeatIcons = {
            repeatAll: `<i class="bi bi-repeat"></i> Repeat All`,
            repeatOne: `<i class="bi bi-repeat-1"></i> Repeat One`,
            noRepeat: `<i class="bi bi-arrow-repeat"></i> No Repeat`
        };

        const shuffleIcons = {
            true: `<i class="bi bi-shuffle"></i> Shuffle ON`,
            false: `<i class="bi bi-shuffle"></i> Shuffle OFF`
        };

        function updateRepeatBtn() {
            repeatBtn.innerHTML = repeatIcons[repeatMode];
        }

        function updateShuffleBtn() {
            shuffleBtn.innerHTML = shuffleIcons[shuffleMode];
        }



        function shuffleArray(array) {
            // Filter out deleted music (assuming `isDeleted` property)
            let filtered = array.filter(music => !music.isDeleted);

            let shuffled = [...filtered];
            for (let i = shuffled.length - 1; i > 0; i--) {
                const j = Math.floor(Math.random() * (i + 1));
                [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
            }
            return shuffled;
        }


        function setQueueFromOriginal() {
            queue = shuffleMode ? shuffleArray(originalQueue) : [...originalQueue];
            sessionStorage.setItem("musicQueue", JSON.stringify(queue));
        }


        function loadMusic(index, resetTime = true, autoPlay = false) {
            if (!queue || queue.length === 0)
                return;

            // Skip deleted music (fail-safe)
            while (queue[index] && queue[index].isDeleted) {
                index = (index + 1) % queue.length;
                if (index === currentIndex)
                    return; // Prevent infinite loop
            }

            currentIndex = index;
            const music = queue[index];
            audio.src = music.src;
            nowPlaying.innerText = "Now Playing: " + music.title;
            sessionStorage.setItem("currentIndex", currentIndex);

            audio.onloadedmetadata = () => {
                if (resetTime)
                    audio.currentTime = 0;

                // Only try to play if autoPlay flag is true (user has interacted)
                if (autoPlay) {
                    // Try to play with error handling
                    const playPromise = audio.play();

                    // Handle the play promise
                    if (playPromise !== undefined) {
                        playPromise.then(() => {
                            // Playback started successfully
                            playBtn.innerHTML = `<i class="bi bi-pause-fill"></i> Pause`;
                        }).catch(err => {
                            // Auto-play was prevented
                            console.log("Playback prevented:", err);
                            playBtn.innerHTML = `<i class="bi bi-play-fill"></i> Play`;
                        });
                    }
                } else {
                    // Just update the button state without trying to play
                    playBtn.innerHTML = `<i class="bi bi-play-fill"></i> Play`;
                }
            };
        }


        //andre
//        originalQueue = JSON.parse(sessionStorage.getItem("musicQueue") || "[]");
        originalQueue = JSON.parse(sessionStorage.getItem("musicQueue") || "[]").filter(m => !m.isDeleted);

        shuffledQueue = shuffleMode ? shuffleArray(originalQueue) : [...originalQueue];
        queue = shuffledQueue;
        currentIndex = parseInt(sessionStorage.getItem("currentIndex") || "0");

        if (queue.length > 0) {
            loadMusic(currentIndex);
        }
        updateRepeatBtn();
        updateShuffleBtn();


// Update the ended event handler
        audio.addEventListener("ended", () => {
            if (repeatMode === "repeatOne") {
                loadMusic(currentIndex, true, true); // Can autoplay since audio was already playing
            } else if (repeatMode === "repeatAll") {
                currentIndex = (currentIndex + 1) % queue.length;
                loadMusic(currentIndex, true, true); // Can autoplay since audio was already playing
            } else if (repeatMode === "noRepeat") {
                if (currentIndex < queue.length - 1) {
                    currentIndex++;
                    loadMusic(currentIndex, true, true); // Can autoplay since audio was already playing
                } else {
                    nowPlaying.innerText = "Now Playing: -";
                    playBtn.innerHTML = `<i class="bi bi-play-fill"></i> Play`;
                }
            }
        });


// Updated play/pause button handler
        playBtn.onclick = () => {
            if (audio.paused) {
                // Handle play with promise for better error handling
                const playPromise = audio.play();

                if (playPromise !== undefined) {
                    playPromise.then(() => {
                        // Playback started successfully
                        playBtn.innerHTML = `<i class="bi bi-pause-fill"></i> Pause`;
                    }).catch(err => {
                        console.error("Error playing audio:", err);
                        // Keep the play button state if play failed
                        playBtn.innerHTML = `<i class="bi bi-play-fill"></i> Play`;
                    });
                }
            } else {
                audio.pause();
                playBtn.innerHTML = `<i class="bi bi-play-fill"></i> Play`;
            }
        };



// Updated control button handlers
        document.getElementById("nextBtn").onclick = () => {
            currentIndex = (currentIndex + 1) % queue.length;
            // We can autoplay after button click (user interaction)
            loadMusic(currentIndex, true, true);
        };

        document.getElementById("prevBtn").onclick = () => {
            currentIndex = (currentIndex - 1 + queue.length) % queue.length;
            // We can autoplay after button click (user interaction)
            loadMusic(currentIndex, true, true);
        };

        document.getElementById("rewindBtn").onclick = () => {
            if (audio.currentTime > 2) {
                audio.currentTime = 0;
            } else {
                currentIndex = (currentIndex - 1 + queue.length) % queue.length;
                // We can autoplay after button click (user interaction)
                loadMusic(currentIndex, true, true);
            }
        };


        repeatBtn.onclick = () => {
            if (repeatMode === "repeatAll") {
                repeatMode = "repeatOne";
            } else if (repeatMode === "repeatOne") {
                repeatMode = "noRepeat";
            } else {
                repeatMode = "repeatAll";
            }
            sessionStorage.setItem("repeatMode", repeatMode);
            updateRepeatBtn();
        };



// Updated shuffle button handler
        shuffleBtn.onclick = () => {
            shuffleMode = !shuffleMode;
            sessionStorage.setItem("shuffleMode", shuffleMode);

            if (shuffleMode) {
                shuffledQueue = shuffleArray(originalQueue);
                sessionStorage.setItem("shuffledQueue", JSON.stringify(shuffledQueue));
                queue = shuffledQueue;
            } else {
                queue = [...originalQueue];
                sessionStorage.removeItem("shuffledQueue");
            }

            currentIndex = 0;
            sessionStorage.setItem("currentIndex", currentIndex);
            // We can autoplay after button click (user interaction)
            loadMusic(currentIndex, true, true);
            updateShuffleBtn();
        };


        function init() {
            originalQueue = JSON.parse(sessionStorage.getItem("musicQueue") || "[]").filter(m => !m.isDeleted);
            shuffleMode = sessionStorage.getItem("shuffleMode") === "true";
            updateShuffleBtn();

            shuffledQueue = shuffleMode ? JSON.parse(sessionStorage.getItem("shuffledQueue") || "[]") : [...originalQueue];
            queue = shuffledQueue;
            updateRepeatBtn();

            currentIndex = parseInt(sessionStorage.getItem("currentIndex") || "0");

            if (queue.length > 0) {
                // Load music but don't try to autoplay on initial page load
                loadMusic(currentIndex, true, false);
            }
        }

// Make sure we're not setting autoplay attribute
        document.addEventListener('DOMContentLoaded', function () {
            // Remove autoplay attribute
            const audioElement = document.getElementById('audioPlayer');
            if (audioElement) {
                audioElement.removeAttribute('autoplay');
            }
        });



//        window.onload = init;
        init();

    </script>

    
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const contextPath = '<%=request.getContextPath()%>';

            // Get references to audio player elements
            const audioPlayer = document.getElementById('audioPlayer');
            const playPauseBtn = document.getElementById('playPauseBtn');

            // Achievement tracking variables
            let achievementChecked = false;

            // Function to check if achievement should be unlocked
            function checkMusicAchievement() {
                // Only check once per session
                if (achievementChecked)
                    return;

                achievementChecked = true;

                // Send AJAX request to check/unlock achievement
                fetch('/MizukiForest/MusicAchievementServlet', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                })
                        .then(response => response.json())
                        .then(data => {
                            // If achievement was unlocked, show modal in parent window
                            if (data.success && data.achievementUnlocked) {
                                showAchievementModal(data);
                            }
                        })
                        .catch(error => {
                            console.error('Error checking music achievement:', error);
                        });
            }

            // Function to show achievement modal in parent window
            function showAchievementModal(achievementData) {
                // Create reward items HTML
                let rewardsHtml = '';

                if (achievementData.rewards && achievementData.rewards.length > 0) {
                    achievementData.rewards.forEach(function (reward) {
                        rewardsHtml +=
                                '<div class="col-6">' +
                                '<div class="reward-item text-center">' +
                                '<div class="position-relative">' +
                                '<img src="' + contextPath + reward.image + '" alt="' + reward.name + '" class="img-fluid rounded" style="max-height: 40px; width: auto;">' +
                                '<span class="position-absolute bottom-0 end-0 badge bg-primary rounded-pill" style="font-size: 0.7rem;">x' + reward.quantity + '</span>' +
                                '</div>' +
                                '<p class="mt-1 mb-0 small">' + reward.name + '</p>' +
                                '</div>' +
                                '</div>';
                    });
                }

                // Create modal HTML
                const modalHtml =
                        '<div class="modal fade" id="achievementModal" tabindex="-1" aria-labelledby="achievementModalLabel" aria-hidden="true">' +
                        '<div class="modal-dialog modal-dialog-centered modal-sm">' +
                        '<div class="modal-content">' +
                        '<div class="modal-header bg-warning py-2">' +
                        '<h5 class="modal-title" style="font-size:15px; color:white;" id="achievementModalLabel">' +
                        '<i class="fas fa-trophy me-2"></i>Achievement Unlocked!' +
                        '</h5>' +
                        '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                        '</div>' +
                        '<div class="modal-body px-3 py-2" style="max-height: 60vh; overflow-y: auto;">' +
                        '<div class="text-center mb-2">' +
                        '<div class="achievement-icon mb-2">' +
                        '<i class="fas fa-music fa-2x text-primary"></i>' +
                        '</div>' +
                        '<h5>' + achievementData.achievementName + '</h5>' +
                        '<p class="text-muted small mb-1">' + achievementData.achievementDescription + '</p>' +
                        '<p class="small text-secondary">Unlocked: ' + achievementData.dateCompleted + '</p>' +
                        '</div>' +
                        '<div class="rewards-section">' +
                        '<h6 class="mb-2">Rewards:</h6>' +
                        '<div class="row g-2">' +
                        rewardsHtml +
                        '</div>' +
                        '<div class="alert alert-success mt-2 py-2 px-2 small">' +
                        '<i class="fas fa-check-circle me-1"></i>Rewards automatically sent to your inventory' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="modal-footer py-1">' +
                        '<button type="button" class="btn btn-primary btn-sm" data-bs-dismiss="modal">Close</button>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';

                // Access parent window and add modal to its DOM
                if (window.parent && window.parent.document) {
                    // Create a temporary div to hold our modal
                    const tempDiv = window.parent.document.createElement('div');
                    tempDiv.innerHTML = modalHtml;

                    // Append the modal to parent document body
                    window.parent.document.body.appendChild(tempDiv.firstChild);

                    // Initialize and show the modal in parent window
                    const achievementModal = new window.parent.bootstrap.Modal(window.parent.document.getElementById('achievementModal'));
                    achievementModal.show();

                    // Remove modal from parent DOM when hidden
                    window.parent.document.getElementById('achievementModal').addEventListener('hidden.bs.modal', function () {
                        this.remove();
                    });
                } else {
                    console.error('Cannot access parent window. Same-origin policy might be preventing access.');
                }
            }

            // Add event listener to play button
            playPauseBtn.addEventListener('click', function () {
                // Check if audio is playing or paused
                checkMusicAchievement();
            });

            // Also trigger achievement check when audio plays automatically
            audioPlayer.addEventListener('play', function () {
                playPauseBtn.innerHTML = '<i class="bi bi-pause-fill"></i> Pause';
                checkMusicAchievement();
            });
        });
    </script>

</html>