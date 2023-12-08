package com.persistentsystems.examples.testclientiperf;

import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.listeners.WrAsyncSocketListener;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrIperfResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrAsyncSocket;

import java.io.IOException;

public class TestClientIperf implements WrAsyncSocketListener {

    private final String SOURCE_IP;
    private final String DEST_IP;
    private final int SECONDS;

    private boolean mDone = false;

    private final WrAsyncSocket mSocket;

    private TestClientIperf(String[] xArgs) throws IOException, InterruptedException {
        SOURCE_IP = xArgs[0];
        DEST_IP = xArgs[1];
        SECONDS = Integer.parseInt(xArgs[2]);

        mSocket = new WrAsyncSocket();

        WrAuth mAuth = new WrAuth();
        mAuth.setPassword("password");
        mAuth.setUserName("factory");

        WrIpAddress mIp = new WrIpAddress();
        mIp.set(SOURCE_IP);
        mIp.setPort(443);

        mSocket.setIpAddress(mIp);
        mSocket.setAuth(mAuth);

        mSocket.setSocketListener(this);

        mSocket.connectAsync();

        while (!mDone) {
            Thread.sleep(1000);
        }
        mSocket.close();

    }

    public static void main(String[] xArgs) throws IOException, WebSocketException, InterruptedException {
        if (xArgs.length != 3) {
            System.out.println("Usage: source_ip destination_ip time_in_seconds");
            return;
        }
        new TestClientIperf(xArgs);
    }

    @Override
    public void onError(WrAsyncSocket xSocket, Throwable xE) {

    }

    @Override
    public void onClose(WrAsyncSocket xSocket) {

    }

    @Override
    public void onOpen(WrAsyncSocket xSocket) throws WebSocketTimeout {
        mSocket.iperf(DEST_IP, SECONDS, false, true, false);
    }

    @Override
    public void onMessage(WrAsyncSocket xSocket, WrJsonResult xResult) {

    }

    @Override
    public void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult) {

    }

    @Override
    public void onPong(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame) {

    }

    @Override
    public void onPing(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame) {

    }

    @Override
    public void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult) {
        StringBuilder sb = new StringBuilder();
        if (xResult.isFinal()) {
            sb.append("Final average from: ");
            sb.append(xSocket.getIpAddress().get());
            sb.append(" ");
            sb.append(xResult.getBw());
            sb.append(" [ ");
            sb.append(xResult.getSeconds());
            sb.append(" ] ");
        } else {
            sb.append("Got Iperf message from ");
            sb.append(xSocket.getIpAddress().get());
            sb.append(": ");
            if (xResult.isTx()) {
                sb.append("TX: ");
                sb.append(xResult.getBw());
            } else if (xResult.isRx()) {
                sb.append("RX: ");
                sb.append(xResult.getBw());
            }
            sb.append(" [ ");
            sb.append(xResult.getSeconds());
            sb.append(" ] ");
        }

        System.out.println(sb.toString());
    }

    @Override
    public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {
        System.out.println("IPERF COMPLETE");
        mDone = true;
    }
}
