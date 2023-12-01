// Import the 'child_process' module
const { spawn } = require('child_process');


// Define the command and arguments to be executed
const command = 'gst-launch-1.0';
const args = [
  '-e',
  'udpsrc',
  'multicast-group=239.192.55.12',
  'port=60012',
  '!',
  'application/x-rtp,media=audio,clock-rate=48000,encoding-name=OPUS,payload=112',
  '!',
  'rtpopusdepay',
  '!',
  'opusdec',
  '!',
  'audioconvert',
  '!',
  'audioresample',
  '!',
  'audio/x-raw,format=S16LE,rate=42100,encoding=wav,channels=1',
  '!',
  'udpsink',
  'host=239.192.55.13',
  'port=60113',
];

// Spawn the child process
const childProcess = spawn(command, args);

// Log the output of the child process
childProcess.stdout.on('data', (data) => {
  console.log(`stdout: ${data}`);
});

// Log any errors from the child process
childProcess.stderr.on('data', (data) => {
  console.error(`stderr: ${data}`);
});

// Log when the child process exits
childProcess.on('close', (code) => {
  console.log(`child process exited with code ${code}`);
});


