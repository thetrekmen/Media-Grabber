package com.persistentsystems.socketclient.containers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WrVariablePairTest {

    WrVariablePair mPair;

    @Before
    public void before() {
        mPair = new WrVariablePair("waverelay_ip", "10.3.1.254");
    }

    @Test
    public void ctor() {
        assertEquals("waverelay_ip", mPair.variable);
        assertEquals("10.3.1.254", mPair.value);
    }

    @Test
    public void testEq() {
        assertFalse(mPair.equals(new String("This is a test")));
    }

    @Test
    public void testToString() {
        assertEquals("waverelay_ip: 10.3.1.254", mPair.toString());
    }

}
