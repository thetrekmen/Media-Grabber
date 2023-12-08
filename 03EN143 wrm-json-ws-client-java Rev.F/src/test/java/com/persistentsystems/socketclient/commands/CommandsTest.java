package com.persistentsystems.socketclient.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommandsTest {

    Command c;

    @Before
    public void before() {
        c = Command.GET;
    }

    @Test
    public void nonNull() {
        SuspendCommand command = new SuspendCommand();
        assertNotNull(command.get());
    }

    @Test
    public void expected() {
        SuspendCommand command = new SuspendCommand();
        assertEquals(command.get(), Command.SUSPEND);
    }

    @Test
    public void toStringTest() {
        assertEquals(c.toString(), "get");
    }

}
