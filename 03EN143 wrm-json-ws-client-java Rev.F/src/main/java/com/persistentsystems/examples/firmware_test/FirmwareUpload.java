package com.persistentsystems.examples.firmware_test;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.File;
import java.io.IOException;

public class FirmwareUpload {

    public FirmwareUpload(String xFilename, String xIp) {

        File fp = new File(xFilename);

        WrBlockingSocket socket = new WrBlockingSocket();
        WrBlockingSocket.DEBUG = true;


        WrAuth auth = new WrAuth();
        WrIpAddress ip = new WrIpAddress();

        auth.setPassword("password");

        ip.set(xIp);
        ip.setPort(443);


        socket.setAuth(auth);
        socket.setIpAddress(ip);

        try {
            socket.connectBlocking();
            WrJsonResult r = socket.sendFirmware(fp);
            System.out.println(r);
        } catch (IOException | WebSocketTimeout | WebSocketException xE) {
            xE.printStackTrace();
        }
    }


    public static void main(String[] args) {

        new FirmwareUpload(args[0], args[1]);

    }

}
