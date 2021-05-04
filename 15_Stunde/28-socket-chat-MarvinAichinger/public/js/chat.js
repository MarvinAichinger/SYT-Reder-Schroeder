const socket = io();
addSocketEventListeners();
addMessageFormSubmitListener();

let username = new URLSearchParams(window.location.search).get('username');

/**
 * Adds submit event listener to HTML form
 */
function addMessageFormSubmitListener() {
    const messageForm = document.querySelector('#message-form');
    messageForm.addEventListener('submit', handleMessageFormSubmit);
}

/**
 * Adds event listeners to socket
 */
function addSocketEventListeners() {
    socket.on('chat message', addMessageListItem);
    socket.on('connect', handleConnectEvent);
    socket.on('logged in', handleLoggedInEvent);
    socket.on('user joined', handleUserJoinedEvent);
    socket.on('user left', handleUserLeftEvent);
}

/**
 * Handles submit event of HTML form
 * @param {Event} event 
 */
function handleMessageFormSubmit(event) {
    event.preventDefault();
    const messageForm = event.target;
    const message = messageForm.message.value;
    socket.emit('chat message', message);
    messageForm.reset();

    let object = {
        message: message,
        username: username
    }
    addMessageListItem(object);
}

/**
 * Adds message to HTML list
 * @param {string} message 
 */
function addMessageListItem(object) {
    const listItem = document.createElement('li');
    listItem.innerHTML = "<b>" + object.username + "</b>: " + object.message;
    document.querySelector('#message-list').appendChild(listItem);
    listItem.scrollIntoView();
}

/**
 * Handles Connect Event
 */
function handleConnectEvent() {
    socket.emit('login', username);
}

/**
 * 
 * @param {Array} usernames 
 */
function handleLoggedInEvent(usernames) {
    const userList = document.querySelector('#user-list');
    for (let i = 0; i < usernames.length; i++) {
        let name = document.createElement('li');
        name.innerHTML = usernames[i];
        userList.appendChild(name);
    }
}

/**
 * 
 * @param {string} username 
 */
function handleUserJoinedEvent(username) {
    const userList = document.querySelector('#user-list');
    let name = document.createElement('li');
    name.innerHTML = username;
    userList.appendChild(name);
}

/**
 * 
 * @param {string} username 
 */
function handleUserLeftEvent(username) {
    const userList = document.querySelector('#user-list');
    let items = userList.getElementsByTagName("li");
    for (let i = 0; i < items.length; i++) {
        if (items[i].innerHTML == username) {
            items[i].remove();
            i = items.length;
        }
    }
}
