package com.persistentsystems.examples.testclient4;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.File;
import java.io.IOException;

public class TestClient4BlockingFirmware {
    private final WrBlockingSocket mSocket;

    private final WrAuth mAuth;
    private final WrIpAddress mIpAddress;

    private TestClient4BlockingFirmware(String xIp, int xPort, File xFile) throws IOException, WebSocketTimeout, WebSocketException, InterruptedException {

        WrBlockingSocket.DEBUG = true;

        mSocket = new WrBlockingSocket();

        mAuth = new WrAuth();
        mAuth.setPassword("password");

        mIpAddress = new WrIpAddress();
        mIpAddress.setPort(xPort);
        mIpAddress.set(xIp);

        mSocket.setAuth(mAuth);
        mSocket.setIpAddress(mIpAddress);

        mSocket.setSocketTimeOutMS(400000); // 300 seconds

        mSocket.connectBlocking();

        WrJsonResult r = mSocket.sendFirmware(xFile);
        System.out.println(r.toString());
        Thread.sleep(400000);
        mSocket.close();
    }

    public static void main(String[] xArgs) throws IOException, WebSocketTimeout, WebSocketException, InterruptedException {

        System.out.println("Using file: " + xArgs[2]);

        File f = new File(xArgs[2]);

        new TestClient4BlockingFirmware(xArgs[0], Integer.parseInt(xArgs[1]), f);
    }

}
