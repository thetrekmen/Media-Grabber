package com.persistentsystems.socketclient.results;

import com.persistentsystems.socketclient.mocks.Mocks;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WrJsonNetworkResultsTest {

    private WrJsonNetworkResults res = null;

    @Before
    public void before() {

    }

    @Test
    public void getResultsEmpty() {
        // setting up network result with SINGLE node message
        res = new WrJsonNetworkResults(Mocks.IP_NAME_MESSAGE);
        assertEquals(0, res.getResults().size());
    }

    @Test
    public void getResultsNotEmpty() {
        // setup with multi node, true network message
        res = new WrJsonNetworkResults(Mocks.NETWORK_GET_NAME_OK);
        assertNotNull(res.getResults());
        assertFalse(res.getResults().isEmpty());
    }

}
