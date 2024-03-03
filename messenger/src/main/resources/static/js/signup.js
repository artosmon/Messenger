'use strict';

const usernameFormSign = document.querySelector('#usernameFormSign');


let username = null;
let password = null;


async function authorization(event) {
    username = document.querySelector('#usernameS').value.trim();
    password = document.querySelector('#passwordS').value.trim();

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



usernameFormSign.addEventListener('submit', authorization, true);
