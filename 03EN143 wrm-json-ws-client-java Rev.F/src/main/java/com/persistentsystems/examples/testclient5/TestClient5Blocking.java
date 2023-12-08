package com.persistentsystems.examples.testclient5;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.IOException;

public class TestClient5Blocking {
    private final WrBlockingSocket mSocket;

    private final WrAuth mAuth;
    private final WrIpAddress mIpAddress;

    private TestClient5Blocking() throws IOException, WebSocketTimeout, WebSocketException, InterruptedException {
        WrBlockingSocket.DEBUG = true;

        mSocket = new WrBlockingSocket();

        mAuth = new WrAuth();
        mAuth.setPassword("password");

        mIpAddress = new WrIpAddress();
        mIpAddress.setPort(443);
        mIpAddress.set("172.26.1.153");

        mSocket.setAuth(mAuth);
        mSocket.setIpAddress(mIpAddress);

        mSocket.setSocketTimeOutMS(300000); // 300 seconds

        mSocket.connectBlocking();
        WrGetVariableList list = new WrGetVariableList();
        list.add("waverelay_name");
        WrJsonResult r = mSocket.get(list);
        System.out.println(r.toString());
        Thread.sleep(300000);
        mSocket.close();
    }

    public static void main(String[] xArgs) throws IOException, WebSocketTimeout, WebSocketException, InterruptedException {
        new TestClient5Blocking();
    }
}
