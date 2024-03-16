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
            const response = await fetch(url, {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const json = await response.json();
            console.log('Успех:', JSON.stringify(json));

        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
}



usernameFormSign.addEventListener('submit', registration, true);
