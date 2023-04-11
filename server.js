const WebSocket = require('ws');

const server = new WebSocket.Server({ port: 8080 });
console.log(server);

server.on('connection', (socket) => {
  console.log('Client connected');

  // Send a random string value to the client every second
  const intervalId = setInterval(() => {
    const value = Math.random().toString(36).substring(2);
    console.log("sending " + value);
    socket.send(value);
  }, 1000);

  // Handle client disconnection
  socket.on('close', () => {
    console.log('Client disconnected');
    clearInterval(intervalId);
  });
});
