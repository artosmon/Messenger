'use strict';

const usernameFormSign = document.querySelector('#usernameFormSign');
// const connectingElement = document.querySelector('.connecting');


let username = null;
let password = null;
// let stompClient = null;


async function authorization(event) {
    username = document.querySelector('#usernameS').value.trim();
    password = document.querySelector('#passwordS').value.trim();

    console.log("authorization");

    if (username && password) {

        // const socket = new SockJS('/ws');
        // stompClient = Stomp.over(socket);
        // stompClient.connect({}, saveUser, onError);
        const url = '/user.addUser';
        const data = {name: username, password: password};

        try {
            const response = await fetch(url, {
                method: 'POST', // или 'PUT'
                body: JSON.stringify(data), // данные могут быть 'строкой' или {объектом}!
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const json = await response.json();
            console.log('Успех:', JSON.stringify(json));
            // window.location.replace("/login");
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
    // event.preventDefault();
}


// function saveUser() {
//     console.log("saveUser");
//     stompClient.send("/app/user.addUser",
//         {},
//         JSON.stringify({name: username, password: password})
//     );
//
//     window.location.replace("/login");
// }
//
// function onError() {
//     connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//     connectingElement.style.color = 'red';
// }



usernameFormSign.addEventListener('submit', authorization, true);
