package com.persistentsystems.socketclient.results;

import com.persistentsystems.socketclient.mocks.Mocks;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WrJsonResultTest {

    @Test
    public void get() throws Exception {

    }

    @Test
    public void getJson() throws Exception {
        WrJsonResult result = new WrJsonResult(Mocks.TEST_NAME_OK);
        JSONObject r = result.getJson();
        JSONObject test = new JSONObject(Mocks.TEST_NAME_OK);
        assertEquals(r.toString(), test.toString());
    }

    @Test
    public void testNull() {
        WrJsonResult result = new WrJsonResult(Mocks.NOT_OK);
        assertNull(result.get("waverelay_ip"));
    }

    @Test
    public void notNullResponse() {
        WrJsonResult result = new WrJsonResult(Mocks.TEST_NAME_OK);
        assertEquals(result.get("waverelay_name"), Mocks.EXPECTED_WAVERELAY_NAME);
    }

    @Test
    public void has() {
        WrJsonResult result = new WrJsonResult(Mocks.TEST_NAME_OK);
        assertTrue(result.has("waverelay_name"));
        assertFalse(result.has("waverelay_ip"));
    }

    @Test
    public void getValue() {
        WrJsonResult result = new WrJsonResult(Mocks.TEST_NAME_OK);
        assertEquals(result.getValue(), Mocks.EXPECTED_WAVERELAY_NAME);
    }

    @Test
    public void getValuesFromResult() {
        WrJsonResult result = new WrJsonResult(Mocks.TEST_NAME_IP_OK);
        assertEquals(result.get("waverelay_name"), Mocks.EXPECTED_WAVERELAY_NAME);
        assertEquals(result.get("waverelay_ip"), Mocks.EXPECTED_WAVERELAY_IP);
    }

    @Test
    public void getValuesFromMap() {
        WrJsonResult result = new WrJsonResult(Mocks.TEST_NAME_IP_OK);
        Map<String, String> res = result.getValues();
        assertEquals(res.size(), 2);
        assertEquals(res.get("waverelay_name"), Mocks.EXPECTED_WAVERELAY_NAME);
        assertEquals(res.get("waverelay_ip"), Mocks.EXPECTED_WAVERELAY_IP);
    }

    @Test
    public void hasError() {
        WrJsonResult result = new WrJsonResult(Mocks.ERROR_MESSAGE);
        assertTrue(result.hasError());
        assertNull(result.get("waverelay_name"));
    }

    @Test
    public void hasNoError() {
        WrJsonResult result = new WrJsonResult(Mocks.TEST_NAME_OK);
        assertFalse(result.hasError());
        assertNull(result.getError());
        assertEquals(result.get("waverelay_name"), Mocks.EXPECTED_WAVERELAY_NAME);
    }

    @Test
    public void getErrorString() {
        WrJsonResult result = new WrJsonResult(Mocks.ERROR_MESSAGE);
        assertTrue(result.hasError());
        assertNull(result.get("waverelay_name"));
        assertEquals(result.getError(), Mocks.ERROR_DISPLAY_STRING);
    }

    @Test
    public void ensureGetReturnsNullOnGetVar() {
        WrJsonResult result = new WrJsonResult(Mocks.IP_NAME_MESSAGE);
        assertNull(result.get("waverelay_ip"));
        assertNull(result.get("waverelay_name"));
    }
}
