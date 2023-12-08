package com.persistentsystems.socketclient.containers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WrGetVariableListTest {

    WrGetVariableList mList;

    @Before
    public void setUp() throws Exception {
        mList = new WrGetVariableList();
    }

    @Test
    public void arrayInit() {
        mList = new WrGetVariableList(new String[]{"waverelay_name", "waverelay_ip"});
        assertEquals(2, mList.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(mList.isEmpty());
    }

    @Test
    public void hasVariables() {
        mList.add("waverelay_name");
        mList.add("waverelay_ip");
        assertEquals(2, mList.size());
    }

    @Test
    public void testString() {
        mList.add("waverelay_name");
        assertTrue(mList.toString().equals("waverelay_name, "));
    }

    @Test
    public void ensureUnique() {
        mList.add("waverelay_ip");
        mList.add("waverelay_ip");
        assertEquals(mList.size(), 1);
    }

    @Test
    public void iter() {
        mList.add("waverelay_name");
        mList.add("waverelay_ip");
        int i = 0;
        for (String each : mList) {
            if (each != null) i++;
        }
        assertEquals(2, i);
    }
}
