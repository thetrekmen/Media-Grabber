package com.persistentsystems.socketclient.sockets;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.commands.Command;
import com.persistentsystems.socketclient.commands.ICommand;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.listeners.WrBlockingSocketListener;
import com.persistentsystems.socketclient.messaging.WrJsonFirmwareMessageBuilder;
import com.persistentsystems.socketclient.messaging.WrJsonMessageBuilder;
import com.persistentsystems.socketclient.messaging.WrJsonNetworkMessageBuilder;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.interfaces.IBlockingGetterSetter;
import com.persistentsystems.socketclient.sockets.interfaces.ISockAuthIp;
import com.persistentsystems.socketclient.ssl.NaiveSSLContext;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

public class WrBlockingSocket implements IBlockingGetterSetter, ISockAuthIp<WrBlockingSocket> {
    public static int DEFAULT_SOCKET_TIMEOUT = 60000;
    public static boolean DEBUG = false;
    private final int mCheckInterval = 15;
    private final WebSocketFactory mFactory;
    private WrIpAddress mIpAddress = null;
    private WrAuth mAuth = null;
    private WebSocket mSocket;
    private int mTimeout = DEFAULT_SOCKET_TIMEOUT;
    private boolean mRequireAll = false;

    public WrBlockingSocket() {
        mFactory = new WebSocketFactory();
        try {
            SSLContext context = NaiveSSLContext.getInstance("TLS");
            mFactory.setSSLContext(context);
            mFactory.setVerifyHostname(false);
        } catch (NoSuchAlgorithmException xE) {
            xE.printStackTrace();
        }
    }

    public int getSocketTimeOutMS() {
        return mTimeout;
    }

    public void setSocketTimeOutMS(int xTimeout) {
        mTimeout = xTimeout;
    }

    @Override
    public WrAuth getAuth() {
        return mAuth;
    }

    @Override
    public WrBlockingSocket setAuth(WrAuth xAuth) {
        mAuth = xAuth;
        return this;
    }

    @Override
    public WrIpAddress getIpAddress() {
        return mIpAddress;
    }

    @Override
    public WrBlockingSocket setIpAddress(WrIpAddress xIpAddress) {
        mIpAddress = xIpAddress;
        return this;
    }

    public void connectBlocking() throws IOException, WebSocketException {
        mSocket = mFactory.createSocket(mIpAddress.getConnectUrl());
        mSocket.connect();
    }

    public void close() {
        mSocket.disconnect();
    }

    public boolean isOpen() {
        if (mSocket == null) return false;
        return mSocket.isOpen();
    }

    // GETTERS
    @Override
    public WrJsonResult get(String xVariable) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShotListener = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShotListener);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.GET);
        mb.setVariable(xVariable);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShotListener);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }

        return result[0];
    }

    @Override
    public WrJsonResult get(WrGetVariableList xList) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.GET);
        mb.setVariable(xList);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    // SETTERS
    @Override
    public WrJsonResult set(String xVariable, String xValue) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShotListener = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShotListener);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.SET);
        mb.setVariable(xVariable, xValue);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShotListener);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonResult set(WrVariablePair xPair) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.SET);
        mb.setVariable(xPair);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonResult set(WrSetVariableList xList) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.SET);
        mb.setVariable(xList);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonResult command(ICommand xCommand) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(xCommand.get());
        String var = xCommand.getVariable();
        if (var != null) {
            mb.setVariable(var);
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    // VALIDATE
    @Override
    public WrJsonResult validate(String xVariable, String xValue) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShotListener = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShotListener);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.VALIDATE);
        mb.setVariable(xVariable, xValue);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShotListener);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonResult validate(WrVariablePair xPair) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.VALIDATE);
        mb.setVariable(xPair);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonResult validate(WrSetVariableList xList) throws WebSocketTimeout {
        final WrJsonResult[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(Command.VALIDATE);
        mb.setVariable(xList);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    // NETWORK GETTERS
    @Override
    public WrJsonNetworkResults networkGet(String xCmd) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_GET);
        mb.setVariable(xCmd);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network get timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonNetworkResults networkGet(WrGetVariableList xList) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_GET);
        mb.setVariable(xList);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network set timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    // NETWORK SET
    @Override
    public WrJsonNetworkResults networkSet(String xCmd, String xValue) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_SET);
        mb.setVariable(xCmd, xValue);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network set timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonNetworkResults networkSet(WrVariablePair xPair) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_SET);
        mb.setVariable(xPair.variable, xPair.value);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network set timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonNetworkResults networkSet(WrSetVariableList xList) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_SET);
        mb.setVariable(xList);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network set timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    // NETWORK VALIDATE
    @Override
    public WrJsonNetworkResults networkValidate(String xCmd, String xValue) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_VALIDATE);
        mb.setVariable(xCmd, xValue);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network get timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public WrJsonNetworkResults networkValidate(WrVariablePair xPair) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_VALIDATE);
        mb.setVariable(xPair.variable, xPair.value);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network get timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        return result[0];
    }

    @Override
    public WrJsonNetworkResults networkValidate(WrSetVariableList xList) throws WebSocketTimeout {
        final WrJsonNetworkResults[] result = {null};
        WrBlockingSocketListener oneShot = WrBlockingListenerFactory.GetInstance(result, DEBUG);
        mSocket.addListener(oneShot);
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setCommand(Command.NETWORK_VALIDATE);
        mb.setVariable(xList);
        if (DEBUG) {
            System.err.println("SENDING: " + mb.build());
        }
        mSocket.sendText(mb.build());
        int count = 0;
        while (result[0] == null && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
                if (count > mTimeout) {
                    throw new WebSocketTimeout("Network get timed out");
                }
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        mSocket.removeListener(oneShot);
        if (count > mTimeout) {
            throw new WebSocketTimeout("");
        }
        return result[0];
    }

    @Override
    public void setRequireAll(boolean xRequireAll) {
        mRequireAll = xRequireAll;
    }

    protected boolean isFirmwareReadyToReceive(String xMessage) {
        return xMessage.toLowerCase().contains("ready_to_receive");
    }

    protected boolean isFirmwareReceiveComplete(String xMessage) {
        return xMessage.toLowerCase().contains("receive_complete");
    }

    public WrJsonResult sendFirmware(File xFile) throws IOException, WebSocketTimeout {
        if (xFile == null || !xFile.exists()) {
            throw new FileNotFoundException("Firmware file not found");
        }

        // 0 = ready to receive
        // 1 = receive complete / kicked off ok -- unit restarts after this message
        final int READY = 0;
        final int KICKOFF = 1;
        final WrJsonResult[] jsonResults = {null, null};
        final FwStatus[] result = {FwStatus.INIT, FwStatus.INIT};

        WrBlockingSocketListener oneShot = new WrBlockingSocketListener() {
            @Override
            public void onTextMessage(WebSocket xWebSocket, String xString) throws Exception {
                WrJsonResult res = new WrJsonResult(xString);
                if (DEBUG) {
                    System.err.println("Firmware got: " + xString);
                }
                if (res.hasError()) {
                    result[READY] = FwStatus.ERROR;
                    jsonResults[READY] = res;
                    result[KICKOFF] = FwStatus.ERROR;
                    jsonResults[KICKOFF] = res;
                } else if (isFirmwareReadyToReceive(xString)) {
                    // cool! send binary file
                    result[READY] = FwStatus.OK;
                    jsonResults[READY] = res;
                } else if (isFirmwareReceiveComplete(xString)) {
                    result[KICKOFF] = FwStatus.OK;
                    jsonResults[KICKOFF] = res;
                }
            }
        };

        mSocket.addListener(oneShot);

        WrJsonFirmwareMessageBuilder mb = new WrJsonFirmwareMessageBuilder();
        mb.setAuth(mAuth);
        mb.setFileSize(xFile.length());
        mSocket.sendText(mb.build());

        int count = 0;
        while (result[READY] == FwStatus.INIT && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        // we got the first ready message
        if (result[READY] != FwStatus.ERROR) {
            try {
                mSocket.sendBinary(Files.readAllBytes(xFile.toPath()));
            } catch (IOException xE) {
                xE.printStackTrace();
            }
        } else {
            mSocket.removeListener(oneShot);
            if (result[READY] == FwStatus.INIT) {
                // we never changed initial state
                throw new WebSocketTimeout("Websocket timed out before getting first firmware messsage");
            }
            return jsonResults[READY];
        }
        // reset timeout
        count = 0;
        while (result[KICKOFF] == FwStatus.INIT && count < mTimeout) {
            try {
                Thread.sleep(mCheckInterval);
                count += mCheckInterval;
            } catch (InterruptedException xE) {
                xE.printStackTrace();
            }
        }
        // we got the fw upload complete message or an error
        mSocket.removeListener(oneShot);
        if (result[KICKOFF] == FwStatus.INIT) {
            // we never changed initial state
            throw new WebSocketTimeout("Websocket timed out before firmware kickoff messsage");
        }
        return jsonResults[KICKOFF];
    }

    private enum FwStatus {
        INIT,
        OK,
        ERROR
    }
}
