package com.persistentsystems.socketclient.mocks;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.IOException;

public class MockWrBlockingSocket extends WrBlockingSocket {
    boolean mConnected = false;
    private String mResult;

    @Override
    public void connectBlocking() throws IOException, WebSocketException {
        mConnected = true;
    }

    @Override
    public void close() {
        mConnected = false;
    }

    @Override
    public boolean isOpen() {
        return mConnected;
    }

    public void setJsonResult(String xString) {
        mResult = xString;
    }

    @Override
    public WrJsonResult get(String xVariable) {
        if (mResult == null) {
            return null;
        }
        WrJsonResult result = new WrJsonResult(mResult);
        mResult = null;
        return result;
    }

    @Override
    public WrJsonResult get(WrGetVariableList xList) {
        if (mResult == null) {
            return null;
        }
        WrJsonResult result = new WrJsonResult(mResult);
        mResult = null;
        return result;
    }

    @Override
    public WrJsonResult set(String xVariable, String xValue) {
        if (mResult == null) {
            return null;
        }
        WrJsonResult res = new WrJsonResult(mResult);
        mResult = null;
        return res;
    }

    @Override
    public WrJsonResult validate(String xVariable, String xValue) {
        if (mResult == null) {
            return null;
        }
        WrJsonResult res = new WrJsonResult(mResult);
        mResult = null;
        return res;
    }

    @Override
    public WrJsonNetworkResults networkGet(String xCmd) throws WebSocketTimeout {
        if (mResult == null) {
            throw new WebSocketTimeout("Network get timed out");
        }
        WrJsonNetworkResults res = new WrJsonNetworkResults(mResult);
        mResult = null;
        return res;
    }

    @Override
    public WrJsonNetworkResults networkValidate(String xCmd, String xValue) throws WebSocketTimeout {
        if (mResult == null) {
            throw new WebSocketTimeout("Network validate timed out");
        }
        WrJsonNetworkResults res = new WrJsonNetworkResults(mResult);
        mResult = null;
        return res;
    }

    @Override
    public WrJsonNetworkResults networkSet(String xCmd, String xValue) throws WebSocketTimeout {
        if (mResult == null) {
            throw new WebSocketTimeout("Network set timed out");
        }
        WrJsonNetworkResults res = new WrJsonNetworkResults(mResult);
        mResult = null;
        return res;
    }
}
