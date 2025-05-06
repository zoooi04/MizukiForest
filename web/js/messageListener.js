/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const socket = new WebSocket("ws://" + location.host + "/MizukiForest/chatSocket");

socket.onmessage = function(event) {
    const message = event.data;
    console.log("New WebSocket message:", message);

    if (message.startsWith("new_message_for_user:")) {
        const payload = message.split("new_message_for_user:")[1];
        const [receiverId, username, content, senderId] = payload.split("|");
        const chatBox = document.querySelector(`.chat-wrapper[data-therapistid="${senderId}"] .chat-box`);
        
        const userId = document.getElementById("receiverId").value;

        if (receiverId === userId) {
            chatBox.insertAdjacentHTML("beforeend", `
                <div class="row m-0">
                    <div class="col sender-col m-2 d-flex flex-wrap">
                        <div class="text-dark w-100 chat-name text-start">${username}</div> 
                        <div class="speech-bubble mt-1 px-3 py-1">${content}</div> 
                    </div>
                    <div class="col"></div>
                </div>
            `);
            chatBox.scrollTop = chatBox.scrollHeight;
        }
    } else if (message.startsWith("new_message_for_therapist:")) {
        const payload = message.split("new_message_for_therapist:")[1];
        const [receiverId, username, content, senderId] = payload.split("|");
        const chatBox = document.querySelector(`.chat-wrapper[data-userid="${senderId}"] .chat-box`);

        
        const therapistId = document.getElementById("receiverId").value;
        
        if (receiverId === therapistId) {
            chatBox.insertAdjacentHTML("beforeend", `
                <div class="row m-0">
                    <div class="col sender-col m-2 d-flex flex-wrap">
                        <div class="text-dark w-100 chat-name text-start">${username}</div> 
                        <div class="speech-bubble mt-1 px-3 py-1">${content}</div> 
                    </div>
                    <div class="col"></div>
                </div>
            `);
            chatBox.scrollTop = chatBox.scrollHeight;
        }
    }else if(message.startsWith("appointment_link:")){
        
        const payload = message.split("appointment_link:")[1];
        const [therapistId, userId, content] = payload.split("|");

        const receiverId = document.getElementById("receiverId").value;

        if(receiverId === userId){
            const chatBox = document.querySelector(`.chat-wrapper[data-therapistid="${therapistId}"] .chat-box`);
            chatBox.insertAdjacentHTML("beforeend", `
                <div class="row m-0">
                    <div class="col sender-col m-2 d-flex flex-wrap">
                        <div class="text-dark w-100 chat-name text-start">-- System Message --</div> 
                        <div class="speech-bubble mt-1 px-3 py-1 text-center">${content}</div> 
                    </div>
                    <div class="col"></div>
                </div>
            `);
            console.log("is received by user");
            chatBox.scrollTop = chatBox.scrollHeight;
        }
        
        if(receiverId === therapistId){
            const chatBox = document.querySelector(`.chat-wrapper[data-userId="${userId}"] .chat-box`);
            chatBox.insertAdjacentHTML("beforeend", `
                <div class="row m-0">
                    <div class="col sender-col m-2 d-flex flex-wrap">
                        <div class="text-dark w-100 chat-name text-start">-- System Message --</div> 
                        <div class="speech-bubble mt-1 px-3 py-1 text-center">${content}</div> 
                    </div>
                    <div class="col"></div>
                </div>
            `);
            console.log("is received by therapist");
            chatBox.scrollTop = chatBox.scrollHeight;
        }

    }

};
