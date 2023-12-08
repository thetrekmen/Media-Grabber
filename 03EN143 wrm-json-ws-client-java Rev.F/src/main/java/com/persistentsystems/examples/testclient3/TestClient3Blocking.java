package com.persistentsystems.examples.testclient3;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.IOException;

public class TestClient3Blocking {
    static int TEST_NO;

    public static void main(String[] xArgs) throws IOException, WebSocketException {
        if (xArgs[0] == null || xArgs[0].isEmpty()) {
            System.out.println("Must provide test number");
            System.exit(-1);
        }
        TEST_NO = Integer.parseInt(xArgs[0]);

        WrBlockingSocket c1 = new WrBlockingSocket();
        c1.setAuth(new WrAuth().setPassword("password").setUserName("factory"));
        c1.setIpAddress(new WrIpAddress().set("10.3.1.254").setPort(443));
        c1.connectBlocking();
        WrBlockingSocket.DEBUG = true;

        WrGetVariableList getList = new WrGetVariableList(new String[]{
                "waverelay_ip", "waverelay_name"
        });

        // alternatively
        WrGetVariableList unusedGet = new WrGetVariableList();
        unusedGet.add("waverelay_neighbors");
        // etc

        WrSetVariableList setList = new WrSetVariableList(new WrVariablePair[]{
                new WrVariablePair("waverelay_name", "TestName")
        });

        // alternatively
        // WrSetVariableList unusedList = new WrSetVariableList();
        // unusedList.add(WrVariable.CLOUD_RELAY_ENABLE, "1");
        // etc


        if (TEST_NO == 1) {
            WrJsonResult result = null;
            try {
                result = c1.get(getList);
                System.out.println("Got: " + result.getValues());
            } catch (WebSocketTimeout xWebSocketTimeout) {
                xWebSocketTimeout.printStackTrace();
            }
        }

        if (TEST_NO == 2) {
            WrJsonResult r = null;
            try {
                r = c1.get("waverelay_ip");
                System.out.println(r.getValue());
            } catch (WebSocketTimeout xWebSocketTimeout) {
                xWebSocketTimeout.printStackTrace();
            }
        }

//        if (TEST_NO == 3) {
//            WrJsonResult r = c1.set(setList);
//            System.out.println("Set: " + r.getValues());
//        }

        if (TEST_NO == 4) {
            WrJsonResult res = null;
            try {
                res = c1.set("waverelay_name", "TEST_NAME");
                System.out.println("Got " + res.getValues());
            } catch (WebSocketTimeout xWebSocketTimeout) {
                xWebSocketTimeout.printStackTrace();
            }
        }

        if (TEST_NO == 5) {
            WrSetVariableList list = new WrSetVariableList();
            list.add("waverelay_name", "Test Name");
            list.add("ptt_enable", "1");
            WrJsonResult res = null;
            try {
                res = c1.set(list);
                System.out.println("Got: " + res.toString());
            } catch (WebSocketTimeout xWebSocketTimeout) {
                xWebSocketTimeout.printStackTrace();
            }
        }

        c1.close();

    }
}
