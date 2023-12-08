package com.persistentsystems.socketclient.sockets;

import com.neovisionaries.ws.client.WebSocketFrame;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.listeners.WrAsyncSocketListener;
import com.persistentsystems.socketclient.mocks.MockWrAsyncSocket;
import com.persistentsystems.socketclient.mocks.Mocks;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrIperfResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class WrAsyncSocketTest {
    MockWrAsyncSocket socket;
    WrAuth mAuth;
    WrIpAddress mIpaddr;

    @Before
    public void before() {
        socket = new MockWrAsyncSocket();
        mAuth = new WrAuth().setUserName("factory").setPassword("password");
        mIpaddr = new WrIpAddress().set("10.3.1.254").setPort(443);
    }

    @Test
    public void setAuth() throws Exception {
        socket.setAuth(mAuth);
        assertEquals(socket.getAuth(), mAuth);
    }

    @Test
    public void setIpAddress() throws Exception {
        socket.setIpAddress(mIpaddr);
        assertEquals(socket.getIpAddress(), mIpaddr);
    }

    @Test
    public void connectAsync() throws Exception {
        socket.connectAsync();
        Thread.sleep(250);
        assertTrue(socket.isOpen());
    }

    @Test
    public void isOpen() throws Exception {
        socket.connectAsync();
        Thread.sleep(250);
        assertTrue(socket.isOpen());
        socket.close();
        assertFalse(socket.isOpen());
    }

    @Test
    public void socketListener() {
        WrAsyncSocketListener listener = new WrAsyncSocketListener() {

            @Override
            public void onError(WrAsyncSocket xClient, Throwable xE) {

            }

            @Override
            public void onClose(WrAsyncSocket xClient) {

            }

            @Override
            public void onOpen(WrAsyncSocket xClient) {

            }

            @Override
            public void onMessage(WrAsyncSocket xClient, WrJsonResult xString) {

            }

            @Override
            public void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult) {

            }

            @Override
            public void onPong(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

            }

            @Override
            public void onPing(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

            }

            @Override
            public void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult) {

            }

            @Override
            public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {

            }
        };
        socket.setSocketListener(listener);
        assertEquals(socket.getSocketListener(), listener);
    }

    @Test
    public void get() throws IOException, InterruptedException {
        socket.connectAsync();
        final WrJsonResult[] result = {null};
        socket.setJsonResult(new WrJsonResult(Mocks.TEST_NAME_OK));
        socket.setSocketListener(new WrAsyncSocketListener() {
            @Override
            public void onError(WrAsyncSocket xClient, Throwable xE) {

            }

            @Override
            public void onClose(WrAsyncSocket xClient) {

            }

            @Override
            public void onOpen(WrAsyncSocket xClient) {

            }

            @Override
            public void onMessage(WrAsyncSocket xClient, WrJsonResult xResult) {
                result[0] = xResult;
            }

            @Override
            public void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult) {

            }

            @Override
            public void onPong(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

            }

            @Override
            public void onPing(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

            }

            @Override
            public void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult) {

            }

            @Override
            public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {

            }
        });
        socket.get("waverelay_name");
        while (result[0] == null) {
            Thread.sleep(10);
        }
        assertNotNull(result[0]);
        assertEquals(result[0].toString(), Mocks.TEST_NAME_OK);
        assertEquals(result[0].get("waverelay_name"), Mocks.EXPECTED_WAVERELAY_NAME);
    }

    @Test
    public void set() throws InterruptedException, IOException {
        socket.connectAsync();
        final WrJsonResult[] result = {null};
        socket.setJsonResult(new WrJsonResult(Mocks.TEST_NAME_OK));
        socket.setSocketListener(new WrAsyncSocketListener() {
            @Override
            public void onError(WrAsyncSocket xClient, Throwable xE) {

            }

            @Override
            public void onClose(WrAsyncSocket xClient) {

            }

            @Override
            public void onOpen(WrAsyncSocket xClient) {

            }

            @Override
            public void onMessage(WrAsyncSocket xClient, WrJsonResult xResult) {
                result[0] = xResult;
            }

            @Override
            public void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult) {

            }

            @Override
            public void onPong(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

            }

            @Override
            public void onPing(WrAsyncSocket xClient, WebSocketFrame xWebSocketFrame) {

            }

            @Override
            public void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult) {

            }

            @Override
            public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {

            }
        });
        socket.set("waverelay_name", "This is a test name");
        while (result[0] == null) {
            Thread.sleep(10);
        }
        assertNotNull(result[0]);
        assertEquals(result[0].toString(), Mocks.TEST_NAME_OK);
    }

    @Test
    public void isIperfCompleteMessage() {
        assertFalse(socket.isIperfCompleteMessage(Mocks.IPERF_NOT_FINAL_RX));
        assertFalse(socket.isIperfCompleteMessage(Mocks.IPERF_FINAL_RX));
        assertTrue(socket.isIperfCompleteMessage(Mocks.IPERF_FINAL_MESSAGE));
    }

    @Test
    public void isIperfMessage() {
        assertTrue(socket.isIperfMessage(Mocks.IPERF_NOT_FINAL_RX));
        assertTrue(socket.isIperfMessage(Mocks.IPERF_NOT_FINAL_RX));
        assertFalse(socket.isIperfMessage(Mocks.IPERF_FINAL_MESSAGE));
    }

    @Test
    public void getList() throws IOException, InterruptedException {
        WrGetVariableList list = new WrGetVariableList();
        list.add("waverelay_name");
        list.add("waverelay_ip");
        Integer[] results = {0, 0};
        socket.setJsonResult(new WrJsonResult(Mocks.IP_NAME_MESSAGE));
        socket.setSocketListener(new WrAsyncSocketListener() {
            @Override
            public void onError(WrAsyncSocket xSocket, Throwable xE) {

            }

            @Override
            public void onClose(WrAsyncSocket xSocket) {

            }

            @Override
            public void onOpen(WrAsyncSocket xSocket) {

            }

            @Override
            public void onMessage(WrAsyncSocket xSocket, WrJsonResult xResult) {
                if (xResult.has("waverelay_name")) {
                    results[0] = 1;
                }
                if (xResult.has("waverelay_ip")) {
                    results[1] = 1;
                } else {
                    fail("Unexpected result");
                }
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

            }

            @Override
            public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {

            }
        });
        socket.connectAsync();
        socket.get(list);
        while (results[0] == 0 && results[1] == 0) {
            Thread.sleep(10);
        }
        assertEquals(results[0], 1, 0);
        assertEquals(results[1], 1, 0);
    }

    @Test
    public void setMap() throws IOException, InterruptedException {
        WrSetVariableList map = new WrSetVariableList();
        map.add("waverelay_name", Mocks.EXPECTED_WAVERELAY_NAME);
        map.add("waverelay_ip", Mocks.EXPECTED_WAVERELAY_IP);
        String[] results = {null, null};
        socket.setJsonResult(new WrJsonResult(Mocks.IP_NAME_MESSAGE_VALUES));
        socket.setSocketListener(new WrAsyncSocketListener() {
            @Override
            public void onError(WrAsyncSocket xSocket, Throwable xE) {

            }

            @Override
            public void onClose(WrAsyncSocket xSocket) {

            }

            @Override
            public void onOpen(WrAsyncSocket xSocket) {

            }

            @Override
            public void onMessage(WrAsyncSocket xSocket, WrJsonResult xResult) {
                if (xResult.has("waverelay_name")) {
                    results[0] = xResult.get("waverelay_name").toString();
                }
                if (xResult.has("waverelay_ip")) {
                    results[1] = xResult.get("waverelay_ip").toString();
                } else {
                    fail("Unexpected result");
                }
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

            }

            @Override
            public void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult) {

            }
        });
        socket.connectAsync();
        socket.set(map);
        while (results[0] == null && results[1] == null) {
            Thread.sleep(10);
        }
        assertEquals(results[0], Mocks.EXPECTED_WAVERELAY_NAME);
        assertEquals(results[1], Mocks.EXPECTED_WAVERELAY_IP);
    }


    @Test
    public void isFirmwareReadyToUploadMessage() {
        assertTrue(socket.isFirmwareReadyToUploadMessage(Mocks.FIRMWARE_START));
        assertFalse(socket.isFirmwareReadyToUploadMessage("{}"));
    }

    @Test
    public void isNetworkMessage() {
        assertTrue(socket.isNetworkMessage(Mocks.NETWORK_GET_NAME_OK));
        assertFalse(socket.isNetworkMessage(Mocks.IP_NAME_MESSAGE));
    }


    @Test
    public void isFirmwareCompleteMessage() {
        assertTrue(socket.isFirmwareCompleteMessage(Mocks.FIRMWARE_FINAL));
        assertFalse(socket.isFirmwareCompleteMessage("{}"));
    }

    @Test
    public void isFirmwareErrorMessage() {
        assertTrue(socket.isFirmwareErrorMessage(Mocks.FIRMWARE_ERROR));
        assertFalse(socket.isFirmwareErrorMessage("{}"));
    }

}
