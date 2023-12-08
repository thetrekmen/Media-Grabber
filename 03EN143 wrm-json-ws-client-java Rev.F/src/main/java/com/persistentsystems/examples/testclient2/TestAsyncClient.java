package com.persistentsystems.examples.testclient2;

import com.neovisionaries.ws.client.WebSocketFrame;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.listeners.WrAsyncSocketListener;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrIperfResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrAsyncSocket;

import java.io.IOException;

public class TestAsyncClient implements WrAsyncSocketListener {
    WrAsyncSocket c1;
    WrAsyncSocket c2;
    WrAuth mWrAuth;
    WrIpAddress mC1Ip;
    WrIpAddress mC2Ip;

    private TestAsyncClient() throws IOException, InterruptedException {
        mWrAuth = new WrAuth().setPassword("password").setUserName("factory");
        mC1Ip = new WrIpAddress().set("172.26.1.151").setPort(443);
        mC2Ip = new WrIpAddress().set("172.26.1.152").setPort(443);

        c1 = new WrAsyncSocket().setAuth(mWrAuth).setIpAddress(mC1Ip);
        c2 = new WrAsyncSocket().setAuth(mWrAuth).setIpAddress(mC2Ip);

        c1.setSocketListener(this);
        c2.setSocketListener(this);

        c1.connectAsync();
        c2.connectAsync();

        c1.iperf("172.26.1.152", 5, false, true, false);

//        mClient.set(WrVariable.WAVERELAY_NAME, "TEST_NAME_" + UUID.randomUUID().toString().substring(0, 2));
        Thread.sleep(50000);
        c1.get("waverelay_name");
        c1.close();
        c2.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new TestAsyncClient();
    }

    @Override
    public void onMessage(WrAsyncSocket xSocket, WrJsonResult xMessage) {
        if (xMessage.hasError()) {
            System.out.println(xSocket.getIpAddress().get() + " HAS ERROR: '" + xMessage.getError() + "'");
            return;
        }
        System.out.println(xSocket.getIpAddress().get() + " GOT " + xMessage.getValue());
    }

    @Override
    public void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult) {
        if (xResult.hasError()) {
            System.out.println(xSocket.getIpAddress().get() + " HAS ERROR '" + xResult.getError() + "'");
            return;
        }
        System.out.println(xSocket.getIpAddress().get() + " GOT " + xResult.getValues());
    }

    @Override
    public void onError(WrAsyncSocket xSocket, Throwable xThrowable) {

    }

    @Override
    public void onClose(WrAsyncSocket xSocket) {
        System.out.println(xSocket.getIpAddress().get() + " closed!");
    }

    @Override
    public void onOpen(WrAsyncSocket xSocket) {
        System.out.println("Connected: " + xSocket.getIpAddress().get());
    }

    @Override
    public void onPing(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

    }

    @Override
    public void onPong(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

    }

    @Override
    public void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(xSocket.getIpAddress().get()).append(":").append(xSocket.getIpAddress().getPort());
        sb.append(" reports: ");

        sb.append("[").append(xResult.getSeconds()).append("] ");
        sb.append(xResult.getBw()).append(" mbps");

        System.out.println(sb.toString());
    }

    @Override
    public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {
        System.out.println(xSocket.getIpAddress().get() + " " + xResult.getJson().getString("display"));
    }
}
