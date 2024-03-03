'use strict';

// const usernamePage = document.querySelector('#username-page');
// const chatPage = document.querySelector('#chat-page');
// const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');

let username = null;
let stompClient = null;
let selectedUserId = null;


async function connect(event) {
    console.log("hello");
    // username = document.querySelector('#username').value.trim();
    const usernameResponse = await fetch(`/username`);
    const user = await usernameResponse.json();
    console.log(user);
    username = user.name;
    console.log("name: "+username)
    // username = 'max';
    if (username) {

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}




function onConnected() {
    stompClient.subscribe(`/topic/messages/users.${username}`, onMessageReceived);
    stompClient.subscribe(`/topic.addUser`, onMessageReceived);

    try {
        document.querySelector('#connected-user-fullname').textContent = username;
    }catch (e){}
    DisplayUsers().then();
}

async function DisplayUsers() {
    const connectedUsersResponse = await fetch('/users');
    let connectedUsers = await connectedUsersResponse.json();
    connectedUsers = connectedUsers.filter(user => user.name !== username);
    const connectedUsersList = document.getElementById('connectedUsers');
    // try {
        connectedUsersList.innerHTML = '';
    // }catch (e){}

    connectedUsers.forEach(user => {
        appendUserElement(user, connectedUsersList);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.name;

    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
    userImage.alt = user.name;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.name;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    // try {
        listItem.appendChild(userImage);
    // }catch (e){}
    // try {
        listItem.appendChild(usernameSpan);
    // }catch (e){}
    // try {
        listItem.appendChild(receivedMsgs);
        // }catch (e){}
        // try {
        listItem.addEventListener('click', userItemClick);
        // }catch (e){}
        // try {
        connectedUsersList.appendChild(listItem);
        // }catch (e){}
}

function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectedUserId = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

}

function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    console.log('displayMessage');
    messageContainer.classList.add('message');
    if (senderId === username) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${username}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    console.log('fetchAndDisplayUserChat')
    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.content);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: username,
            recipientId: selectedUserId,
            content: messageInput.value.trim(),
            timestamp: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        console.log('sendMessage');
        displayMessage(username, messageInput.value.trim());
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}


async function onMessageReceived(payload) {
    await DisplayUsers();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    console.log('console', selectedUserId,message.senderId);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        document.querySelector(`#${selectedUserId}`).classList.add('active');
    } else {
        // try {
            messageForm.classList.add('hidden');
        // }catch (e){}
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

function onLogout() {
    // stompClient.send("/app/user.disconnectUser",
    //     {},
    //     JSON.stringify({name: username})
    // );
    // window.location.;
}

// usernameForm.addEventListener('submit', connect, true);
// try {
    window.addEventListener('load', connect);

    messageForm.addEventListener('submit', sendMessage, true);
// } catch (e){}
// try {
    logout.addEventListener('click', onLogout, true);
// } catch (e){}
window.onbeforeunload = () => onLogout();


