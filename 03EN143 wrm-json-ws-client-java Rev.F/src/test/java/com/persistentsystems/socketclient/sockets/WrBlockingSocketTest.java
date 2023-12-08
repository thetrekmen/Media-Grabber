package com.persistentsystems.socketclient.sockets;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.mocks.MockWrBlockingSocket;
import com.persistentsystems.socketclient.mocks.Mocks;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.*;

public class WrBlockingSocketTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    MockWrBlockingSocket client;

    @Before
    public void before() {
        client = new MockWrBlockingSocket();
        client.setAuth(new WrAuth().setUserName("factory").setPassword("password"));
        client.setIpAddress(new WrIpAddress().setPort(443).set("10.3.1.254"));
    }

    @Test
    public void testSetup() {
        assertNotEquals(client, null);
    }

    @Test
    public void clientIp() {
        WrIpAddress ip = client.getIpAddress();
        assertEquals(ip.get(), "10.3.1.254");
        assertEquals(ip.getPort(), 443);
    }

    @Test
    public void clientUsername() {
        WrAuth auth = client.getAuth();
        assertEquals(auth.getUserName(), "factory");

    }

    @Test
    public void clientPassword() {
        WrAuth auth = client.getAuth();
        assertEquals(auth.getPassword(), "password");
    }

    @Test()
    public void connectBlocking() throws IOException, WebSocketException {
        client.connectBlocking();
        assertEquals(client.isOpen(), true);
    }

    @Test
    public void close() throws IOException, WebSocketException {
        client.connectBlocking();
        assertEquals(client.isOpen(), true);
        client.close();
        assertEquals(client.isOpen(), false);
    }

    @Test
    public void getVariable() throws IOException, WebSocketException {
        client.connectBlocking();
        assertTrue(client.isOpen());
        client.setJsonResult(Mocks.TEST_NAME_OK);
        WrJsonResult result = client.get("waverelay_name");
        assertNotNull(result);
        assertEquals(result.get("waverelay_name"), Mocks.EXPECTED_WAVERELAY_NAME);
    }

    @Test
    public void setVariable() throws IOException, WebSocketException {
        client.connectBlocking();
        assertTrue(client.isOpen());
        client.setJsonResult(Mocks.TEST_NAME_OK);
        WrJsonResult result = client.set("waverelay_name", Mocks.EXPECTED_WAVERELAY_NAME);
        assertNotNull(result);
        assertEquals(result.get("waverelay_name"), Mocks.EXPECTED_WAVERELAY_NAME);
    }

    @Test
    public void getVariableList() throws IOException, WebSocketException {
        client.connectBlocking();
        assertTrue(client.isOpen());
        client.setJsonResult(Mocks.TEST_NAME_OK);

        WrGetVariableList list = new WrGetVariableList(new String[]{
                "waverelay_name"
        });

        WrJsonResult result = client.get(list);
        assertNotNull(result);
        assertEquals(result.getValue(), Mocks.EXPECTED_WAVERELAY_NAME);
    }

}
