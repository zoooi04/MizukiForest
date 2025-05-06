<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@ page import="java.util.*, model.Userqueuelist, model.Music, model.MusicService" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MizukiForestPU");
    EntityManager em = emf.createEntityManager();

    MusicService ms = new MusicService(em); // pass 'em' manually

%>

<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Music Queue</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath()%>/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/mizukiuser.css">
    </head>

    <section id="queue" class="incsec">
        <%request.setAttribute("pageTitle", "Music Queue");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>


        <div class="container mt-5 pt-5" style='padding-bottom:170px; padding-top:50px'>
            <h2>Your Music Queue</h2>

            <%if (session.getAttribute("RemoveFromQueue") != null) {%>
            <%@include file="RemoveFromQueueSuccess.jsp"%>
            <% session.removeAttribute("RemoveFromQueue");}%>
            
            <%if (session.getAttribute("ClearQueue") != null) {%>
            <%@include file="ClearQueueSuccess.jsp"%>
            <% session.removeAttribute("ClearQueue");}%>

            <!-- Clear queue form -->
            <form action="<%= request.getContextPath()%>/UserQueueServlet" method="post">
                <button class="btn btn-danger mb-4" type="submit" name="clearQueue">Clear Queue</button>
            </form>

            <!-- Music list -->
            <ul class="list-group">
                <%
                    List<Userqueuelist> queue = (List<Userqueuelist>) session.getAttribute("queue");
                    System.out.println("size at jsp " + queue.size());
                    if (queue != null && !queue.isEmpty()) {
                        for (Userqueuelist item : queue) {
                            Music music = ms.findMusicById(item.getMusicid());

                %>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <div>
                        <strong><%= music.getMusicname()%></strong> by <%= music.getAuthor()%>
                    </div>
                    <!-- Remove music form -->
                    <form action="<%= request.getContextPath()%>/UserQueueServlet" method="post" class="d-inline">
                        <input type="hidden" name="musicId" value="<%= music.getMusicid()%>"/>
                        <button type="submit" class="btn btn-outline-danger">Remove</button>
                        <input type="hidden" name="remove" value="true"/>
                    </form>
                </li>
                <%
                    }
                } else {
                %>
                <li class="list-group-item">Your queue is empty.</li>
                    <%
                        }
                    %>
            </ul>
        </div>

        <iframe src="<%=request.getContextPath()%>/shared/FooterPlayer.jsp" id='player'
                style="width:100%; position:fixed; bottom:0; left:0; border:none; z-index:9999;">
        </iframe>

        <script>

            const musicQueue = [
            <%
                queue = (List<Userqueuelist>) session.getAttribute("queue");
                if (queue != null) {
                    for (int i = 0; i < queue.size(); i++) {
                        Music music = ms.findMusicById(queue.get(i).getMusicid());
            %>
            { src: "<%= request.getContextPath() + music.getFilepath()%>", title: "<%= music.getMusicname().replace("\"", "\\\"")%> by <%= music.getAuthor().replace("\"", "\\\"")%>" }<%= i < queue.size() - 1 ? "," : ""%>
            <% }
                }%>
                ];
                // Save queue to sessionStorage
                sessionStorage.setItem("musicQueue", JSON.stringify(musicQueue));
                sessionStorage.setItem("currentIndex", "0");

                /*
                 const iframe = document.getElementById("player");
                 if (iframe) {
                 iframe.contentWindow.location.reload();
                 }
                 */


        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        
        <script>
        // Save current state before the page unloads
        window.addEventListener("beforeunload", () => {
            sessionStorage.setItem("audioCurrentTime", audio.currentTime);
            sessionStorage.setItem("isPlaying", !audio.paused);
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
    if (!queue || queue.length === 0) return;

    // Skip deleted music (fail-safe)
    while (queue[index] && queue[index].isDeleted) {
        index = (index + 1) % queue.length;
        if (index === currentIndex) return; // Prevent infinite loop
    }

    currentIndex = index;
    const music = queue[index];
    audio.src = music.src;
    nowPlaying.innerText = "Now Playing: " + music.title;
    sessionStorage.setItem("currentIndex", currentIndex);

    audio.onloadedmetadata = () => {
        if (resetTime) audio.currentTime = 0;
        
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
document.addEventListener('DOMContentLoaded', function() {
    // Remove autoplay attribute
    const audioElement = document.getElementById('audioPlayer');
    if (audioElement) {
        audioElement.removeAttribute('autoplay');
    }
});



//        window.onload = init;
init();

    </script>
    
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const contextPath = '<%=request.getContextPath() %>';
            
    // Get references to audio player elements
    const audioPlayer = document.getElementById('audioPlayer');
    const playPauseBtn = document.getElementById('playPauseBtn');
    
    // Achievement tracking variables
    let achievementChecked = false;
    
    // Function to check if achievement should be unlocked
    function checkMusicAchievement() {
        // Only check once per session
        if (achievementChecked) return;
        
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
            // If achievement was unlocked, show modal
            if (data.success && data.achievementUnlocked) {
                showAchievementModal(data);
            }
        })
        .catch(error => {
            console.error('Error checking music achievement:', error);
        });
    }
    
    // Function to show achievement modal
    function showAchievementModal(achievementData) {
        // Create reward items HTML
        let rewardsHtml = '';

        if (achievementData.rewards && achievementData.rewards.length > 0) {
            achievementData.rewards.forEach(function(reward) {
                rewardsHtml += 
                    '<div class="col-md-4 mb-3">' +
                        '<div class="reward-item text-center">' +
                            '<div class="position-relative">' +
                                '<img src="' + contextPath+ reward.image + '" alt="' + reward.name + '" class="img-fluid rounded" style="max-height: 80px; width: auto;">' +
                                '<span class="position-absolute bottom-0 end-0 badge bg-primary rounded-pill">x' + reward.quantity + '</span>' +
                            '</div>' +
                            '<p class="mt-2 mb-0">' + reward.name + '</p>' +
                        '</div>' +
                    '</div>';
            });
        }

        // Create modal HTML
        const modalHtml = 
            '<div class="modal fade" id="achievementModal" tabindex="-1" aria-labelledby="achievementModalLabel" aria-hidden="true">' +
                '<div class="modal-dialog modal-dialog-centered">' +
                    '<div class="modal-content">' +
                        '<div class="modal-header bg-warning">' +
                            '<h5 class="modal-title" id="achievementModalLabel">' +
                                '<i class="fas fa-trophy me-2"></i>Achievement Unlocked!' +
                            '</h5>' +
                            '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                        '</div>' +
                        '<div class="modal-body">' +
                            '<div class="text-center mb-4">' +
                                '<div class="achievement-icon mb-3">' +
                                    '<i class="fas fa-music fa-3x text-primary"></i>' +
                                '</div>' +
                                '<h4>' + achievementData.achievementName + '</h4>' +
                                '<p class="text-muted">' + achievementData.achievementDescription + '</p>' +
                                '<p class="small">Unlocked: ' + achievementData.dateCompleted + '</p>' +
                            '</div>' +
                            
                            '<div class="rewards-section">' +
                                '<h5>Rewards:</h5>' +
                                '<div class="row">' +
                                    rewardsHtml +
                                '</div>' +
                                '<div class="alert alert-success mt-3">' +
                                    '<i class="fas fa-check-circle me-2"></i>Rewards automatically sent to your inventory' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        '<div class="modal-footer">' +
                            '<button type="button" class="btn btn-primary" data-bs-dismiss="modal">Close</button>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>';

        // Add modal to DOM
        document.body.insertAdjacentHTML('beforeend', modalHtml);

        // Show the modal
        const achievementModal = new bootstrap.Modal(document.getElementById('achievementModal'));
        achievementModal.show();

        // Remove modal from DOM when hidden
        document.getElementById('achievementModal').addEventListener('hidden.bs.modal', function() {
            this.remove();
        });
    }
    
    // Add event listener to play button
    playPauseBtn.addEventListener('click', function() {
        // Check if audio is playing or paused
        checkMusicAchievement();
    });
    
    // Also trigger achievement check when audio plays automatically
    audioPlayer.addEventListener('play', function() {
        playPauseBtn.innerHTML = '<i class="bi bi-pause-fill"></i> Pause';
        checkMusicAchievement();
    });
});
    </script>
    </section>
</html>

<script src="/js/mizukibase.js"></script>