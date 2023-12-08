package com.persistentsystems.examples.testclient2;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.IOException;

public class TestBlockingClient {

    public static void main(String[] args) throws IOException, WebSocketException {
        WrBlockingSocket c1 = new WrBlockingSocket();
        c1.setAuth(new WrAuth().setPassword("password").setUserName("factory"));
        c1.setIpAddress(new WrIpAddress().set("10.3.1.254").setPort(443));
        c1.connectBlocking();

        WrJsonResult results = null;
        try {
            results = c1.get("waverelay_ip");
            System.out.println("Got: " + results.get("waverelay_ip"));
        } catch (WebSocketTimeout xWebSocketTimeout) {
            xWebSocketTimeout.printStackTrace();
        }

        c1.close();
    }

}
