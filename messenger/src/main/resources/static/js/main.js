'use strict';


const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');

let username = null;
let stompClient = null;
let selectedUserId = null;


async function connect(event) {

    username = localStorage.getItem("usernameKey");

    if (username != null) {

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}




function onConnected() {
    stompClient.subscribe(`/topic/messages.users.${username}`, onMessageReceived);
    // stompClient.subscribe(`/topic/notifications.users.${username}`, NotificationSend);

    try {
        document.querySelector('#connected-user-fullname').textContent = username;
    }catch (e){}
    DisplayUsers().then();
}

async function DisplayUsers() {

    const connectedUsersResponse = await fetch('/users');
    const loggedUsersResponse = await fetch('/users.loggedUsers');
    const notificationsResponse = await fetch('/user.getAllNotifications');

    let notifications = await notificationsResponse.json();
    let connectedUsers = await connectedUsersResponse.json();
    let loggedUsers = await loggedUsersResponse.json();

    connectedUsers = connectedUsers.filter(user => user.name !== username);
    const connectedUsersList = document.getElementById('connectedUsers');

        connectedUsersList.innerHTML = '';

    connectedUsers.forEach(user => {
        appendUserElement(user, connectedUsersList,loggedUsers,notifications);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

function appendUserElement(user, connectedUsersList, loggedUsers,notifications) {



    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.name;

    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
    userImage.alt = user.name;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.name;


    const receivedMsgs = document.createElement('span');
    // receivedMsgs.textContent = '0';


    notifications = notifications.filter(n => n.senderId === user.name);


    if (notifications.length === 0)
        receivedMsgs.classList.add('nbr-msg', 'hidden');
    else
        receivedMsgs.classList.add('nbr-msg');


    const onlineStatus = document.createElement('span');

    if (loggedUsers.indexOf(user.name) === -1) {
        onlineStatus.classList.add('onl-st', 'hidden');
    } else {
        onlineStatus.classList.add('onl-st');
    }

    listItem.appendChild(onlineStatus);

    listItem.appendChild(userImage);

    listItem.appendChild(usernameSpan);

    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);


    connectedUsersList.appendChild(listItem);

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

    const notificationId = selectedUserId + '_' + username;
    const deletedNotification = {recipientId: username,id: notificationId};

    stompClient.send("/app/user.deleteNotification",{},JSON.stringify(deletedNotification));

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

}

function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
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
        };
        stompClient.send("/app/send.message", {}, JSON.stringify(chatMessage));
        console.log('sendMessage');

        const notification = {senderId: username, recipientId: selectedUserId};
        stompClient.send("/app/user.saveNotification", {}, JSON.stringify(notification));

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

            messageForm.classList.add('hidden');

    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {

        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

// function NotificationSend(payload) {
//
// }






    window.addEventListener('load', connect);

    messageForm.addEventListener('submit', sendMessage, true);


