const { app, BrowserWindow, ipcMain } = require('electron');
const path = require('path');
const { spawn } = require('child_process');
const dgram = require('dgram');



// Set the paths for the bundled GStreamer binaries and plugins
const gstreamerDir = path.join(__dirname, 'gstreamer');
const gstreamerBin = path.join(gstreamerDir, 'bin');
const gstreamerLib = path.join(gstreamerDir, 'lib', 'gstreamer-1.0');

process.env.PATH = `${gstreamerBin};${process.env.PATH}`;
process.env.GST_PLUGIN_PATH = gstreamerLib;

const LOCAL_IP_ADDRESS = '172.26.44.66';
const MULTICAST_ADDRESS = '239.192.44.13';
const PORT = 60013;
const client = dgram.createSocket({ type: 'udp4', reuseAddr: true });

let fileIndex = 1;
let childProcess = null;
let noDataTimer = null;
const pauseDuration = 1000; // 1 second without data to consider it a pause

client.on('error', (err) => {
    console.log(`client error:\n${err.stack}`);
    client.close();
});

client.on('listening', () => {
    const address = client.address();
    console.log(`client listening ${address.address}:${address.port}`);
});

client.on('message', () => {
    if (noDataTimer) {
        clearTimeout(noDataTimer);
    } else {
        // Data resumed after a pause, start a new process
        startGStreamerProcess();
    }
    noDataTimer = setTimeout(killGStreamerProcess, pauseDuration);
});

client.bind(PORT, function () {
    client.addMembership(MULTICAST_ADDRESS, LOCAL_IP_ADDRESS);
});

function startGStreamerProcess() {
    if (childProcess) {
        return; // Do not start a new process if one is already running
    }

    const args = [
        '-e',
        'udpsrc',
        `multicast-group=${MULTICAST_ADDRESS}`,
        `port=${PORT}`,
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
        'audio/x-raw,format=S16LE,rate=48000',
        '!',
        'wavenc',
        '!',
        'filesink',
        `location=input${fileIndex}.wav`
    ];

    childProcess = spawn('gst-launch-1.0', args);
    fileIndex++; // Increment file index for the next file
}

function killGStreamerProcess() {
    if (childProcess) {
        console.log("Killing GStreamer process...");
        childProcess.kill('SIGKILL'); // Forcefully terminate the process
        childProcess.on('close', () => {
            console.log("GStreamer process terminated.");
            childProcess = null; // Set to null after confirming termination
            noDataTimer = null;
        });
    }
}

// startGStreamerProcess(); // Start the first GStreamer process


function createWindow() {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js')
    }
  });

  win.loadFile('index.html');
}

app.whenReady().then(() => {
    createWindow();
    startGStreamerProcess(); // Start the process when the app is ready
  });
  
  app.on('window-all-closed', () => {
    killGStreamerProcess(); // Kill the process when the app is closed
    if (process.platform !== 'darwin') {
      app.quit();
    }
  });

ipcMain.on('start-process', (event, arg) => {
    console.log("Starting process...");
    startGStreamerProcess();
  });
  
  ipcMain.on('kill-process', (event, arg) => {
    console.log("Killing process...");
    killGStreamerProcess();
});
