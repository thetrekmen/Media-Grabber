package com.persistentsystems.socketclient.messaging;

import com.persistentsystems.socketclient.auth.WrAuth;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class WrJsonIperfMessageBuilderTest {
    WrJsonIperfMessageBuilder mb;

    @Before
    public void before() {
        mb = new WrJsonIperfMessageBuilder();
        WrAuth auth = new WrAuth();
        auth.setPassword("password");
        auth.setUserName("factory");
        mb.setAuth(auth);
    }

    @Test
    public void build() {

    }

    @Test
    public void setTarget() {
        mb.setTarget("10.3.1.254");
        assertEquals("10.3.1.254", mb.getTargetIp());
    }

    @Test
    public void setDetailed() {
        mb.setDetailed(true);
        assertTrue(mb.isDetailed());
    }

    @Test
    public void setMetaData() {
        mb.setMetaData(true);
        assertTrue(mb.hasMetaData());
    }

    @Test
    public void setLength() {
        mb.setLength(9999);
        assertEquals(mb.getLength(), 9999);
    }

    @Test
    public void setTxOnly() {
        mb.setTxOnly(true);
        assertTrue(mb.isTxOnly());
    }

    @Test
    public void token() {
        assertTrue(mb.build(), mb.build().contains(WrMessageToken.getIperfToken()));
    }

    @Test
    public void ctor() {
        assertNotNull(mb);
    }
}
