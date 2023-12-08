package com.persistentsystems.examples.testclient3;

import com.neovisionaries.ws.client.WebSocketFrame;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import com.persistentsystems.socketclient.listeners.WrAsyncSocketListener;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrIperfResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrAsyncSocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestClient3AsyncNetwork implements WrAsyncSocketListener {
    static int TEST_NO = 0;

    private final WrAsyncSocket sock;

    private TestClient3AsyncNetwork() throws IOException, InterruptedException {
        sock = new WrAsyncSocket();
        sock.setAuth(new WrAuth().setPassword("password"));

        sock.setIpAddress(new WrIpAddress().set("10.3.1.254").setPort(443));

        sock.setSocketListener(this);
        sock.connectAsync();
        Thread.sleep(10000);
        sock.close();
    }

    public static void main(String[] xArgs) throws IOException, InterruptedException {
        if (xArgs[0] == null || xArgs[0].isEmpty()) {
            System.out.println("Must provide test number");
            System.exit(-1);
        }
        TEST_NO = Integer.parseInt(xArgs[0]);
        new TestClient3AsyncNetwork();
    }

    @Override
    public void onError(WrAsyncSocket xSocket, Throwable xE) {
        System.out.println(xSocket.getIpAddress().get() + " threw " + xE.toString());
    }

    @Override
    public void onClose(WrAsyncSocket xSocket) {
        System.out.println(xSocket.getIpAddress().get() + " is closing!");
    }

    @Override
    public void onOpen(WrAsyncSocket xSocket) {
        System.out.println("Socket opened: " + xSocket.getIpAddress().get());
        if (TEST_NO == 1) {
            sock.networkGet("waverelay_name");
        } else if (TEST_NO == 2) {
            WrGetVariableList getList = new WrGetVariableList();
            getList.add("waverelay_ip");
            getList.add("waverelay_name");
            xSocket.networkGet(getList);
        } else if (TEST_NO == 3) {

        } else {
            System.out.println("Must provide TEST_NO");
        }
    }

    @Override
    public void onMessage(WrAsyncSocket xSocket, WrJsonResult xString) {
        System.out.println("OnMessage");
        StringBuilder sb = new StringBuilder();
        sb.append(xSocket.getIpAddress().get());
        sb.append(": ");
        sb.append(xString.getValues());
        System.out.println(sb.toString());
    }

    @Override
    public void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult) {
        System.out.println("Got network message!");
        StringBuilder sb = new StringBuilder();
        sb.append("Host: ");
        sb.append(xSocket.getIpAddress().get());
        sb.append(": ");
        System.out.println(sb.toString());
        sb.setLength(0);
        // can also just call this line
        // sb.append(xResult.getNetworkValues());
        Map<String, List<WrVariablePair>> map = xResult.getNetworkValues();
        for (String each : map.keySet()) {
            sb.append("From IP: ");
            sb.append(each).append(" ");
            for (WrVariablePair pair : map.get(each)) {
                sb.append(pair.variable);
                sb.append("::");
                sb.append(pair.value);
                sb.append(", ");
            }
            System.out.println(sb.toString());
            sb.setLength(0);
        }
    }

    @Override
    public void onPong(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame) {

    }

    @Override
    public void onPing(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame) {

    }

    @Override
    public void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult) {

    }

    @Override
    public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {

    }
}
