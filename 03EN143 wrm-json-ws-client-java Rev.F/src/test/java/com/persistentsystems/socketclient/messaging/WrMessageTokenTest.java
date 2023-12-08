package com.persistentsystems.socketclient.messaging;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WrMessageTokenTest {
    @Test
    public void getRandomToken() throws Exception {
        assertNotEquals(WrMessageToken.getRandomToken(), WrMessageToken.getRandomToken());
    }

    @Test
    public void getIperfToken() {
        assertEquals(WrMessageToken.getIperfToken(), "IPERF_TOKEN");
    }

    @Test
    public void getFirmwareToken() {
        assertEquals(WrMessageToken.getFirmwareToken(), "FIRMWARE_TOKEN");
    }

}