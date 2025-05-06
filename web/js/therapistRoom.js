document.addEventListener("DOMContentLoaded", function(){
    // Switch between chat boxes
    this.querySelectorAll(".chat-item").forEach(item => {
        item.addEventListener("click", function (e) {
            const newTherapistId = e.target.getAttribute('data-therapistid');

            // Switch chat item
            const activeChat = document.querySelector(".active-chat");
            if (activeChat) {
                console.log("hello world");
                activeChat.classList.toggle("inactive-chat");
                activeChat.classList.toggle("active-chat");
            }

            e.target.classList.toggle("inactive-chat");
            e.target.classList.toggle("active-chat");

            // Toggle chat box display
            const currentChatBox = document.querySelector(".chat-box-active");
            if (currentChatBox) {
                currentChatBox.classList.toggle("chat-box-active");
                currentChatBox.classList.toggle("chat-box-inactive");
            }

            const newChatBox = document.querySelector(`.chat-wrapper[data-therapistid="${newTherapistId}"]`);
            if (newChatBox) {
                newChatBox.classList.toggle("chat-box-active");
                newChatBox.classList.toggle("chat-box-inactive");

            }
        });
    });

    
    
    
    
    
    
    
    
    
    
    
    //Send message
    const sendBtn = this.querySelector(".send-btn");
    const textarea = this.querySelector(".input-message textarea");
    
    sendBtn.addEventListener("click", sendMessage);
    textarea.addEventListener("keypress", function (e) {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault(); // prevent newline
            sendMessage();
        }
    });
    
    function sendMessage() {
        const firstPath = location.pathname.split('/')[1];
        const message = textarea.value.trim();
        if (message === ""){
            alert("You cannot send an empty message");
            return;
        } else{
            $.ajax({
                type: "POST",
                data: {
                    message: message
                },
                url: "/" + firstPath + "/SendMessageServlet",
                success: function (response) {
                    if (response) {
                        const chatBox = document.querySelector(".chat-box");
//                        const username = document.querySelector(".username").value.trim();
                        
                        chatBox.insertAdjacentHTML("beforeend", `
                            <div class="row m-0">
                                <div class="col"></div>
                                <div class="col sender-col m-2 d-flex flex-wrap justify-content-end">
                                    <div class="text-dark w-100 chat-name text-end">You</div> 
                                    <div class="speech-bubble mt-1 px-3 py-1">${message}</div> 
                                </div>
                            </div>
                        `);
                        document.querySelector(".input-message textarea").value = "";

                        chatBox.scrollTop = chatBox.scrollHeight;

                    } else {
                        alert("Message update failed");
                    }
                },
                error: function(){
                    console.log("error");
                }    
            });
            }

    }
    
    
    // Scroll all chat boxes to bottom on load
    document.querySelectorAll(".chat-box").forEach(chatBox => {
        chatBox.scrollTop = chatBox.scrollHeight;
    });
    
});