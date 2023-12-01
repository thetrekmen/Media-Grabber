const dgram = require('dgram');
var fs = require('fs');

const LOCAL_IP_ADDRESS = '10.3.1.45';
const MULTICAST_ADDRESS = '239.26.21.52';
const START_PORT = 1024;
const END_PORT = 49151;
const TIMEOUT_MS = 50;

let client;
let messageReceived = false;

function createClient() {
  client = dgram.createSocket({ type: 'udp4', reuseAddr: true });

  client.on('error', (err) => {
    console.log(`client error:\n${err.stack}`);
    client.close();
  });

  client.on('message', (msg, rinfo) => {
    console.log(msg);
    messageReceived = true;
  });

  client.on('listening', () => {
    const address = client.address();
    console.log(`client listening ${address.address}:${address.port}`);
  });
}

function bindToNextPort(port) {
  if (port > END_PORT) {
    console.error('No suitable port found.');
    process.exit(1);
  }

  console.log(`Trying port ${port}`);

  createClient();

  client.bind(port, function () {
    client.addMembership(MULTICAST_ADDRESS, LOCAL_IP_ADDRESS);
  }).on('error', (err) => {
    if (err.code === 'EADDRINUSE') {
      bindToNextPort(port + 1);
    } else {
      console.error('Unexpected error:', err);
    }
  });

  // Set a timeout to check for received messages
  setTimeout(() => {
    if (!messageReceived) {
      client.close();
      bindToNextPort(port + 1);
    }
  }, TIMEOUT_MS);
}

bindToNextPort(START_PORT);
