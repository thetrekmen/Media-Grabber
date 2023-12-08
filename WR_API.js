const { exec } = require('child_process');

// Set up necessary variables
const gatewayIpAddress = "10.3.1.254";
const waveRelayAPIVariable = "date_utc";
const jarPath = '"./03EN143 wrm-json-ws-client-java Rev.F/target/SocketCli-v2.1.14-jar-with-dependencies.jar"';

// Generalized function to call Wave Relay API
function callWaveRelayAPI(ipAddress, variableName) {
    const command = `java -jar ${jarPath} -t ${ipAddress} -c ${variableName} -y get`;
    exec(command, (error, stdout, stderr) => {
        if (error) {
            console.error(`Error: ${error}`);
            return;
        }
        if (stderr) {
            console.error(`Stderr: ${stderr}`);
            return;
        }

        // Parse the output to extract the desired variable
        const regex = new RegExp(`${variableName}:(.*)`);
        const match = stdout.match(regex);
        if (match && match[1]) {
            console.log(`${variableName} for IP ${ipAddress}: ${match[1].trim()}`);
        } else {
            console.log(`${variableName} not found for IP ${ipAddress}`);
        }
    });
}

callWaveRelayAPI(gatewayIpAddress, waveRelayAPIVariable);

