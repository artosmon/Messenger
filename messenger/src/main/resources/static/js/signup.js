'use strict';

const usernameFormSign = document.querySelector('#usernameForm');


let username = null;
let password = null;


async function registration(event) {
    username = document.querySelector('#username').value.trim();
    password = document.querySelector('#password').value.trim();

    if (username && password) {
        const url = '/user.addUser';
        const data = {name: username, password: password};

        try {

            var xhr = new XMLHttpRequest();
            xhr.open("POST", url, true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify({
                name: username,
                password: password
            }));

        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
}



usernameFormSign.addEventListener('submit', registration, true);
