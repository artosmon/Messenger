'use strict';

const usernameForm = document.querySelector('#usernameForm');

let username = null;
    async function startSession() {

        username = document.querySelector('#username').value.trim();

        if(username) {
            localStorage.setItem("usernameKey", username);
        }
}



    usernameForm.addEventListener('submit', startSession, true);
