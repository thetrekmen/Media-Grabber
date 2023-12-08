package com.persistentsystems.socketclient.auth;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WrAuthTest {
    @Test
    public void ipAddress() {
        WrAuth auth = new WrAuth();
        auth.setPassword("TEST_PASSWORD");
        assertEquals(auth.getPassword(), "TEST_PASSWORD");
    }

    @Test
    public void username() {
        WrAuth auth = new WrAuth();
        auth.setUserName("TEST_USERNAME");
        assertEquals(auth.getUserName(), "TEST_USERNAME");
    }

    @Test
    public void defaultUsername() {
        WrAuth auth = new WrAuth();
        assertEquals(auth.getUserName(), "factory");
    }

    @Test
    public void passwordNull() {
        WrAuth auth = new WrAuth();
        assertEquals(auth.getPassword(), "");
    }
}
