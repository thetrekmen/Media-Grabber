package com.persistentsystems.socketclient.containers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WrSetVariableListTest {

    private WrSetVariableList mList = null;

    @Before
    public void setUp() throws Exception {
        mList = new WrSetVariableList();
    }

    @Test
    public void arrayInit() {
        mList = new WrSetVariableList(new WrVariablePair[]{
                new WrVariablePair("waverelay_ip", "10.3.1.254"),
                new WrVariablePair("waverelay_name", "test")
        });
        assertEquals(2, mList.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(mList.isEmpty());
    }

    @Test
    public void add() {
        mList.add("ip_flow_list", "asdf");
        assertEquals(1, mList.size());
    }

    @Test
    public void ensureUnique() {
        mList.add("waverelay_name", "asdf");
        mList.add("waverelay_name", "asdf");
        assertEquals(1, mList.size());

        // hashing is done on WrVariable.*
        // not on value
        mList.add("waverelay_name", "Asdf");
        assertEquals(1, mList.size());

        mList.add("waverelay_ip", "asdf");
        assertEquals(2, mList.size());
    }

    @Test
    public void size() {
        mList.add("waverelay_name", "Test");
        mList.add("ip_flow_list", "asdf");
        assertEquals(2, mList.size());
    }

    @Test
    public void iter() {
        mList.add("waverelay_name", "test");
        mList.add("ip_flow_list", "asdf");
        int i = 0;
        for (WrVariablePair each : mList) {
            if (each != null) i++;
        }
        assertEquals(2, i);
    }

}