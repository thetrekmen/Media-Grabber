package com.persistentsystems.examples.testclient3;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.IOException;

public class TestClient3BlockingNetwork {
    static int TEST_NO = 0;

    public static void main(String[] xArgs) throws IOException, WebSocketException, WebSocketTimeout {
        if (xArgs[0] == null || xArgs[0].isEmpty()) {
            System.out.println("Must provide test number");
            System.exit(-1);
        }
        TEST_NO = Integer.parseInt(xArgs[0]);

        WrBlockingSocket c1 = new WrBlockingSocket();
        c1.setAuth(new WrAuth().setPassword("password").setUserName("factory"));
        c1.setIpAddress(new WrIpAddress().set("10.3.1.254").setPort(443));
        c1.connectBlocking();

        if (TEST_NO == 1) {
            WrGetVariableList getList = new WrGetVariableList();
            getList.add("waverelay_ip");
            getList.add("waverelay_name");
            WrJsonNetworkResults result = c1.networkGet(getList);
            System.out.println("Got: " + result.getValues());
        }

        if (TEST_NO == 2) {
            WrJsonNetworkResults r = c1.networkGet("waverelay_ip");
            System.out.println(r.getValue());
        }

        if (TEST_NO == 3) {
            WrSetVariableList setList = new WrSetVariableList();
            setList.add("waverelay_name", "Test Name");
            WrJsonResult r = c1.networkSet(setList);
            System.out.println("Set: " + r.getValues());
        }

        if (TEST_NO == 4) {
            WrJsonResult res = c1.networkSet("waverelay_name", "TEST_NAME 2");
            System.out.println("Got " + res.getValues());
        }

        c1.close();
    }
}
