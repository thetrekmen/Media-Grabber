package com.persistentsystems.socketclient.sockets;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.mocks.MockWrBlockingSocket;
import com.persistentsystems.socketclient.mocks.Mocks;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class WrBlockingSocketNetworkTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private MockWrBlockingSocket client = null;

    @Before
    public void before() {
        client = new MockWrBlockingSocket();
        client.setAuth(new WrAuth().setUserName("factory").setPassword("password"));
        client.setIpAddress(new WrIpAddress().setPort(443).set("10.3.1.254"));
    }

    @Test
    public void networkGetValid() throws IOException, WebSocketException, WebSocketTimeout {
        client.connectBlocking();
        assertTrue(client.isOpen());
        client.setJsonResult(Mocks.NETWORK_GET_NAME_OK);
        WrJsonNetworkResults result = client.networkGet("waverelay_name");
        assertNotNull(result);
        List<WrJsonNetworkResult> list = result.getResults();
        for (WrJsonNetworkResult each : list) {
            assertNotNull(each);
            assertEquals(each.getValue(), Mocks.EXPECTED_WAVERELAY_NAME);
        }
    }

    @Test
    public void networkSetValid() throws IOException, WebSocketException, WebSocketTimeout {
        client.connectBlocking();
        assertTrue(client.isOpen());
        client.setJsonResult(Mocks.NETWORK_GET_NAME_OK);
        WrJsonNetworkResults result = client.networkSet("waverelay_name", Mocks.EXPECTED_WAVERELAY_NAME);
        assertNotNull(result);
        List<WrJsonNetworkResult> list = result.getResults();
        for (WrJsonNetworkResult each : list) {
            assertNotNull(each);
            assertEquals(each.getValue(), Mocks.EXPECTED_WAVERELAY_NAME);
        }
    }

    @Test
    public void networkValidateValid() throws IOException, WebSocketException, WebSocketTimeout {
        client.connectBlocking();
        assertTrue(client.isOpen());
        client.setJsonResult(Mocks.NETWORK_GET_NAME_OK);
        WrJsonNetworkResults result = client.networkValidate("waverelay_name", Mocks.EXPECTED_WAVERELAY_NAME);
        assertNotNull(result);
        List<WrJsonNetworkResult> list = result.getResults();
        for (WrJsonNetworkResult each : list) {
            assertNotNull(each);
            assertEquals(each.getValue(), Mocks.EXPECTED_WAVERELAY_NAME);
        }
    }

    @Test(expected = WebSocketTimeout.class)
    public void checkNetGetTimeout() throws IOException, WebSocketException, WebSocketTimeout {
        client.setSocketTimeOutMS(10); // time isnt checked, just make sure we throw
        client.connectBlocking();
        Assert.assertTrue(client.isOpen());
        client.setJsonResult(null);
        WrJsonResult result = client.networkGet("waverelay_ip");
        // block and throw
    }

    @Test(expected = WebSocketTimeout.class)
    public void checkNetSetTimeout() throws IOException, WebSocketException, WebSocketTimeout {
        client.setSocketTimeOutMS(10); // time isnt checked, just make sure we throw
        client.connectBlocking();
        Assert.assertTrue(client.isOpen());
        client.setJsonResult(null);
        WrJsonResult result = client.networkSet("waverelay_ip", Mocks.EXPECTED_WAVERELAY_NAME);
        // block and throw
    }

    @Test(expected = WebSocketTimeout.class)
    public void checkNetValidateTimeout() throws IOException, WebSocketException, WebSocketTimeout {
        client.setSocketTimeOutMS(10); // time isnt checked, just make sure we throw
        client.connectBlocking();
        Assert.assertTrue(client.isOpen());
        client.setJsonResult(null);
        WrJsonResult result = client.networkValidate("waverelay_ip", Mocks.EXPECTED_WAVERELAY_NAME);
        // block and throw
    }
}
