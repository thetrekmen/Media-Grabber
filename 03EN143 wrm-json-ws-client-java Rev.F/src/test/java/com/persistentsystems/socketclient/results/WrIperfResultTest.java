package com.persistentsystems.socketclient.results;

import com.persistentsystems.socketclient.mocks.Mocks;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class WrIperfResultTest {

    WrIperfResult resTx;
    WrIperfResult resRx;
    WrIperfResult resRxFinal;
    WrIperfResult resTxFinal;

    @Before
    public void before() {
        resTx = new WrIperfResult(Mocks.IPERF_NOT_FINAL_TX);
        resRx = new WrIperfResult(Mocks.IPERF_NOT_FINAL_RX);
        resRxFinal = new WrIperfResult(Mocks.IPERF_FINAL_RX);
        resTxFinal = new WrIperfResult(Mocks.IPERF_FINAL_TX);
    }

    @Test
    public void ctor() {
        assertNotNull(resTx);
        assertNotNull(resRx);
    }

    @Test(expected = JSONException.class)
    public void checkThrows() {
        WrIperfResult res = new WrIperfResult(Mocks.IPERF_INVALID_JSON);
    }

    @Test
    public void getObject() {
        JSONObject tx = resTx.getJsonObject();
        JSONObject txEq = new JSONObject(Mocks.IPERF_NOT_FINAL_TX);
        JSONObject rx = resRx.getJsonObject();
        JSONObject rxEq = new JSONObject(Mocks.IPERF_NOT_FINAL_RX);
        assertEquals(tx.toString(), txEq.toString());
        assertEquals(rx.toString(), rxEq.toString());
    }

    @Test
    public void testToString() {
        String tx = resTx.toString();
        String rx = resRx.toString();
        assertEquals(tx, Mocks.IPERF_NOT_FINAL_TX);
        assertEquals(rx, Mocks.IPERF_NOT_FINAL_RX);
    }

    @Test
    public void isRx() {
        assertTrue(resRx.isRx());
        assertFalse(resTx.isRx());
    }

    @Test
    public void isTx() {
        assertFalse(resRx.isTx());
        assertTrue(resTx.isTx());
    }

    @Test
    public void isFinal() {
        assertFalse(resRx.isFinal());
        assertFalse(resTx.isFinal());
        assertTrue(resRxFinal.isFinal());
        assertTrue(resTxFinal.isFinal());
    }

    @Test
    public void getBw() {
        assertEquals(resRx.getBw(), 42.63, 0);
        assertEquals(resTx.getBw(), 42.51, 0);
    }

    @Test
    public void getTimeSeconds() {
        assertEquals(resRx.getSeconds(), "4.0-5.0");
        assertEquals(resRxFinal.getSeconds(), "0.0-5.0");
        assertEquals(resTx.getSeconds(), "4.0-5.0");
        assertEquals(resTxFinal.getSeconds(), "0.0-5.0");
    }

}
