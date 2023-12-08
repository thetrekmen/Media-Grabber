package com.persistentsystems.socketclient.mocks;

import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.listeners.WrAsyncSocketListener;
import com.persistentsystems.socketclient.listeners.WrFirmwareListener;
import com.persistentsystems.socketclient.results.WrFirmwareResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrAsyncSocket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MockWrAsyncSocket extends WrAsyncSocket {

    private boolean mConnected = false;
    private WrJsonResult mResult;
    private WrAsyncSocketListener mListener;
    private WrFirmwareListener mFirmwareListener;

    @Override
    public void connectAsync() throws IOException {
        try {
            Thread.sleep(100);
        } catch (InterruptedException xE) {
            xE.printStackTrace();
        }
        mConnected = true;
    }

    @Override
    public boolean isOpen() {
        return mConnected;
    }

    @Override
    public void close() {
        mConnected = false;
    }

    @Override
    public WrAsyncSocketListener getSocketListener() {
        return mListener;
    }

    @Override
    public void setSocketListener(WrAsyncSocketListener xListener) {
        mListener = xListener;
    }

    @Override
    public void get(String xVariable) {
        mListener.onMessage(this, mResult);
        mResult = null;
    }

    public void setJsonResult(WrJsonResult xWrJsonResult) {
        mResult = xWrJsonResult;
    }

    @Override
    public void set(String xVariable, String xValue) {
        mListener.onMessage(this, mResult);
        mResult = null;
    }

    @Override
    public void get(WrGetVariableList xList) {
        mListener.onMessage(this, mResult);
        mResult = null;
    }

    @Override
    public void set(WrSetVariableList xMap) {
        mListener.onMessage(this, mResult);
        mResult = null;
    }

    @Override
    public void setFirmwareListener(WrFirmwareListener xListener) {
        mFirmwareListener = xListener;
    }

    public void sendFirmware(File xFile) throws FileNotFoundException {
        if (xFile == null || !xFile.exists())
            mFirmwareListener.onFirmwareReady(new WrFirmwareResult(""));
    }
}

