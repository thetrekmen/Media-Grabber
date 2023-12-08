package com.persistentsystems.socketclient.messaging;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WrJsonNetworkMessageBuilderTest {

    WrJsonNetworkMessageBuilder mTest;

    @Before
    public void setUp() throws Exception {
        mTest = new WrJsonNetworkMessageBuilder();
    }

    @Test
    public void setRequireAll() throws Exception {
        mTest.setRequireAll(true);
        // assert that the generated string has the require_all obj
        assertTrue(mTest.build().contains("\"require_all_nodes\":true"));
        mTest.setRequireAll(false);
        assertTrue(mTest.build().contains("\"require_all_nodes\":false"));
    }
}
