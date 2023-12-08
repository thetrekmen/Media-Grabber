package com.persistentsystems.socketclient.results;

import com.persistentsystems.socketclient.mocks.Mocks;
import org.junit.Before;
import org.junit.Test;

public class WrJsonNetworkResultTest {

    private WrJsonNetworkResult mResult;

    @Before
    public void before() {
        mResult = new WrJsonNetworkResult(Mocks.NETWORK_GET_NAME_OK);
    }

    @Test
    public void getMap() {
//        Map<String, WrVariablePair> result = mResult.getNetworkValues();


    }

}
