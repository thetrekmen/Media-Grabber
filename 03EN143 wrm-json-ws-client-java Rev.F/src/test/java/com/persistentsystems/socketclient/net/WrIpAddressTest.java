package com.persistentsystems.socketclient.net;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class WrIpAddressTest {
    WrIpAddress ip;

    @Before
    public void before() {
        ip = new WrIpAddress();
        ip.set("10.3.1.254");
        ip.setPort(443);
    }

    @Test
    public void ipAddress() {
        assertEquals(ip.get(), "10.3.1.254");
    }

    @Test
    public void port() {
        assertEquals(ip.getPort(), 443);
    }

    @Test
    public void connectionUrl() {
        assertEquals(ip.getConnectUrl(), URI.create("wss://10.3.1.254:443/xxx"));
    }

}
