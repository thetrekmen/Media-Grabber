package com.persistentsystems.socketclient.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SuspendCommandTest {
    SuspendCommand cmd;

    @Before
    public void before() {
        cmd = new SuspendCommand();
    }

    @Test
    public void testGet() {
        assertEquals(cmd.get(), Command.SUSPEND);
    }

    @Test
    public void assertNull() {
        String t = cmd.getVariable();
        assertTrue(t == null);
    }

    @Test
    public void testString() {
        assertEquals(cmd.get().toString(), "suspend");
    }

}
