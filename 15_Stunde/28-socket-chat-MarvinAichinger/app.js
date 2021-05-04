const express = require('express');
const socketIO = require('socket.io');

const port = 3000;

const app = express();

app.use(express.static('public', {extensions: ['html']}));  // server static files

const httpServer = app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
});

const io = socketIO(httpServer);

const usernames = [];

// add event listener for new client connection
io.on('connection', (socket) => {
    socket.on('chat message', (message) => {
        let object = {
            message: message,
            username: socket.username
        }
        socket.broadcast.emit('chat message', object);
    });

    let name;
    socket.on('login', (username) => {
        name = username;
        socket.username = username;
        usernames.push(username);
        socket.emit('logged in', usernames);
        socket.broadcast.emit('user joined', username);
    });

    socket.on('disconnect', () => {
        for (let i = 0; i < usernames.length; i++) {
            if (usernames[i] === name) {
                usernames.splice(i, 1);
                i = usernames.length;
            }
        }
        socket.broadcast.emit('user left', name);
    });
});
