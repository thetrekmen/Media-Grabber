package com.persistentsystems.examples.testclient3;

import com.neovisionaries.ws.client.WebSocketFrame;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.listeners.WrAsyncSocketListener;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrIperfResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrAsyncSocket;

import java.io.IOException;

public class TestClient3Async implements WrAsyncSocketListener {
    private static int TEST_NO;

    private final WrGetVariableList list1;
    private final WrSetVariableList setList;
    private final WrAsyncSocket sock1;

    private TestClient3Async() throws IOException, InterruptedException {
        list1 = new WrGetVariableList();
        list1.add("waverelay_name");
        list1.add("waverelay_ip");

        setList = new WrSetVariableList();
        setList.add("waverelay_name", "Heller Test Name");

        WrAuth auth = new WrAuth();
        auth.setPassword("password");
        WrIpAddress ip = new WrIpAddress();
        ip.set("10.3.1.254");
        ip.setPort(443); // required

        sock1 = new WrAsyncSocket();

        sock1.setSocketListener(this);

        sock1.setAuth(auth);
        sock1.setIpAddress(ip);

        sock1.connectAsync();

        // if we don't wait
        // program will just exit
        if (TEST_NO >= 3) {
            Thread.sleep(60000);
        } else {
            Thread.sleep(5000);
        }
        sock1.close();
    }

    public static void main(String[] xArgs) throws IOException, InterruptedException {
        if (xArgs[0] == null || xArgs[0].isEmpty()) {
            System.out.println("Test number must be specified");
            System.exit(-1);
        }
        TEST_NO = Integer.parseInt(xArgs[0]);

        System.out.println("TEST_NO " + TEST_NO);

        new TestClient3Async();
    }

    @Override
    public void onMessage(WrAsyncSocket xSocket, WrJsonResult xResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(xSocket.getIpAddress().get());
        sb.append(": ");
        sb.append(xResult.getValues());
        System.out.println(sb.toString());
    }

    @Override
    public void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(xSocket.getIpAddress().get());
        sb.append(": ");
        sb.append(xResult.getValues());
        System.out.println(sb.toString());
    }

    @Override
    public void onError(WrAsyncSocket xSocket, Throwable xThrowable) {
        xThrowable.printStackTrace();
        System.exit(-1);
    }

    @Override
    public void onClose(WrAsyncSocket xSocket) {
        // ignored
        System.out.println("Closed");
    }

    @Override
    public void onOpen(WrAsyncSocket xSocket) {
        System.out.println("Opened " + xSocket.getIpAddress().toString());
        if (TEST_NO == 1) {
            sock1.get("waverelay_name");
        }

        if (TEST_NO == 2) {
            sock1.get(list1);
        }

        if (TEST_NO == 3) {
            sock1.set("waverelay_name", "Heller Test Name");
        }

        if (TEST_NO == 4) {
            sock1.set(setList);
        }
        if (TEST_NO == 5) {
            sock1.sendPing();
            sock1.sendPing("This is a test");
        }
    }

    @Override
    public void onPing(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame) {
        // ignored
        System.out.println("Got ping from " + xSocket.getIpAddress().get() + " " + xWebSocketFrame.getPayloadText());
    }

    @Override
    public void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult) {
        // ignored
    }

    @Override
    public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {
        // ignored
    }

    @Override
    public void onPong(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame) {
        // ignored
        System.out.println("Got pong from " + xSocket.getIpAddress().get() + " " + xWebSocketFrame.getPayloadText());
    }
}
