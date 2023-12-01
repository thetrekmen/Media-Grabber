const dgram = require('dgram');
var fs = require('fs');


const LOCAL_IP_ADDRESS = '10.3.1.45'
// const MULTICAST_ADDRESS = '239.26.21.51'
// const PORT = 9722
const MULTICAST_ADDRESS = '239.192.60.13'
const PORT = 60013

const client = dgram.createSocket({ type: 'udp4', reuseAddr: true });

client.on('error', (err) => {
    console.log(`client error:\n${err.stack}`);
    client.close();
});

//writing to file
// client.on('message', (msg, rinfo) => {
//     console.log(msg);
//     var stream = fs.createWriteStream("udp-stream.txt", { 'flags': 'a' });
//     stream.once('open', function (fd) {
//         stream.write(msg);
//     });
// });

//printing to console
// client.on('message', (msg, rinfo) => {
//     console.log(msg);
// });

client.on('listening', () => {
    const address = client.address();
    console.log(`client listening ${address.address}:${address.port}`);
});

client.bind(PORT, function () {
    client.addMembership(MULTICAST_ADDRESS, LOCAL_IP_ADDRESS);
});





