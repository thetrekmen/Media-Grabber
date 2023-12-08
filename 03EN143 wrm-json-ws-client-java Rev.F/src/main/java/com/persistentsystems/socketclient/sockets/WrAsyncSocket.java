package com.persistentsystems.socketclient.sockets;

import com.neovisionaries.ws.client.*;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.commands.Command;
import com.persistentsystems.socketclient.commands.ICommand;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import com.persistentsystems.socketclient.listeners.WrAsyncSocketListener;
import com.persistentsystems.socketclient.listeners.WrFirmwareListener;
import com.persistentsystems.socketclient.messaging.*;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrFirmwareResult;
import com.persistentsystems.socketclient.results.WrIperfResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.interfaces.IAsyncGetterSetter;
import com.persistentsystems.socketclient.sockets.interfaces.ISockAuthIp;
import com.persistentsystems.socketclient.ssl.NaiveSSLContext;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class WrAsyncSocket implements WebSocketListener, ISockAuthIp<WrAsyncSocket>, IAsyncGetterSetter {
    private WrAuth mAuth = null;
    private WrIpAddress mIpAddress = null;
    private WebSocket mSocket = null;
    private WebSocketFactory mFactory = null;
    private WrAsyncSocketListener mListener = null;
    private WrFirmwareListener mFirmwareListener = null;
    private boolean mRequireAll = false;

    public WrAsyncSocket() {
        mFactory = new WebSocketFactory();
        try {
            SSLContext context = NaiveSSLContext.getInstance("TLS");
            mFactory.setSSLContext(context);
            mFactory.setVerifyHostname(false);
        } catch (NoSuchAlgorithmException xE) {
            xE.printStackTrace();
        }
    }

    @Override
    public void setRequireAll(boolean xRequireAll) {
        mRequireAll = xRequireAll;
    }

    public WrAuth getAuth() {
        return mAuth;
    }

    public WrAsyncSocket setAuth(WrAuth xAuth) {
        mAuth = xAuth;
        return this;
    }

    public WrIpAddress getIpAddress() {
        return mIpAddress;
    }

    public WrAsyncSocket setIpAddress(WrIpAddress xIpAddress) {
        mIpAddress = xIpAddress;
        return this;
    }

    public void connectAsync() throws IOException {
        mSocket = mFactory.createSocket(mIpAddress.getConnectUrl());
        mSocket.addListener(this);
        mSocket.connectAsynchronously();
    }

    public void sendPing() {
        mSocket.sendPing();
    }

    public void sendPing(byte[] xPayload) {
        mSocket.sendPing(xPayload);
    }

    public void sendPing(String xPayload) {
        mSocket.sendPing(xPayload);
    }

    public boolean isOpen() {
        if (mSocket == null) return false;
        return mSocket.isOpen();
    }

    public void close() {
        mSocket.disconnect();
    }

    public WrAsyncSocketListener getSocketListener() {
        return mListener;
    }

    public void setSocketListener(WrAsyncSocketListener xListener) {
        mListener = xListener;
    }

    public WrFirmwareListener getFirmwareListener() {
        return mFirmwareListener;
    }

    public void setFirmwareListener(WrFirmwareListener xListener) {
        mFirmwareListener = xListener;
    }

    @Override
    public void onStateChanged(WebSocket xWebSocket, WebSocketState xWebSocketState) throws Exception {
        // don't care
    }

    @Override
    public void onConnected(WebSocket xWebSocket, Map<String, List<String>> xMap) throws Exception {
        mListener.onOpen(this);
    }

    @Override
    public void onConnectError(WebSocket xWebSocket, WebSocketException xE) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void onDisconnected(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame, WebSocketFrame xWebSocketFrame1, boolean xB) throws Exception {
        mListener.onClose(this);
    }

    @Override
    public void onFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onContinuationFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onTextFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onBinaryFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onCloseFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onPingFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {
        mListener.onPing(this, xWebSocketFrame);
    }

    @Override
    public void onPongFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {
        mListener.onPong(this, xWebSocketFrame);
    }

    @Override
    public void onTextMessage(WebSocket xWebSocket, String xString) throws Exception {
        if (isIperfMessage(xString)) {
            if (mListener == null) throw new Exception("AsyncSocket listener not set (iperf messsage)");
            mListener.onIperfMessage(this, new WrIperfResult(xString));
            return;
        }
        if (isIperfCompleteMessage(xString)) {
            if (mListener == null) throw new Exception("AsyncSocket listener not set (iperf message)");
            mListener.onIperfComplete(this, new WrJsonResult(xString));
            return;
        }

        if (isFirmwareReadyToUploadMessage(xString)) {
            if (mFirmwareListener == null) throw new Exception("Firmware listener not set");
            mFirmwareListener.onFirmwareReady(new WrFirmwareResult(xString));
            return;
        }

        if (isFirmwareCompleteMessage(xString)) {
            if (mFirmwareListener == null) throw new Exception("Firmware listener not set");
            mFirmwareListener.onFirmwareFinished(new WrFirmwareResult(xString));
            return;
        }

        if (isFirmwareErrorMessage(xString)) {
            if (mFirmwareListener == null) throw new Exception("Firmware listener not set");
            mFirmwareListener.onFirmwareError(new WrFirmwareResult(xString));
            return;
        }
        if (isNetworkMessage(xString)) {
            if (mListener == null) throw new Exception("AsyncSocket listener not set");
            mListener.onNetworkMessage(this, new WrJsonNetworkResult(xString));
            return;
        }
        if (mListener == null) throw new Exception("AsyncSocket listener not set");
        mListener.onMessage(this, new WrJsonResult(xString));
    }

    protected boolean isNetworkMessage(String xMessage) {
        JSONObject obj = new JSONObject(xMessage);
        return obj.has("ip_list");
    }

    protected boolean isFirmwareReadyToUploadMessage(String xMessage) {
        JSONObject obj = new JSONObject(xMessage);
        return obj.has("token") && obj.getString("token").equals(WrMessageToken.getFirmwareToken()) &&
                obj.has("status") && obj.getString("status").equals("ready_to_receive");
    }

    protected boolean isFirmwareCompleteMessage(String xMessage) {
        JSONObject obj = new JSONObject(xMessage);
        return obj.has("token") && obj.getString("token").equals(WrMessageToken.getFirmwareToken()) &&
                obj.has("final_status") && obj.getString("final_status").equals("ok");
    }

    protected boolean isFirmwareErrorMessage(String xMessage) {
        JSONObject obj = new JSONObject(xMessage);
        return obj.has("token") && obj.getString("token").equals(WrMessageToken.getFirmwareToken()) &&
                obj.has("final_status") && (obj.getString("final_status").equals("errors") || obj.getString("final_status").equals("failed"));
    }

    protected boolean isIperfMessage(String xMessage) {
        JSONObject obj = new JSONObject(xMessage);
        return obj.has("token") && obj.getString("token").equals(WrMessageToken.getIperfToken()) &&
                obj.has("throughput_test");
    }

    protected boolean isIperfCompleteMessage(String xMessage) {
        JSONObject obj = new JSONObject(xMessage);
        return obj.has("token") && obj.getString("token").equals(WrMessageToken.getIperfToken()) &&
                obj.has("final_status") && obj.has("display") &&
                obj.getString("display").toLowerCase().equals("throughput test complete");
    }

    @Override
    public void onBinaryMessage(WebSocket xWebSocket, byte[] xBytes) throws Exception {

    }

    @Override
    public void onSendingFrame(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onFrameSent(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onFrameUnsent(WebSocket xWebSocket, WebSocketFrame xWebSocketFrame) throws Exception {

    }

    @Override
    public void onThreadCreated(WebSocket xWebSocket, ThreadType xThreadType, Thread xThread) throws Exception {

    }

    @Override
    public void onThreadStarted(WebSocket xWebSocket, ThreadType xThreadType, Thread xThread) throws Exception {

    }

    @Override
    public void onThreadStopping(WebSocket xWebSocket, ThreadType xThreadType, Thread xThread) throws Exception {

    }

    @Override
    public void onError(WebSocket xWebSocket, WebSocketException xE) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void onFrameError(WebSocket xWebSocket, WebSocketException xE, WebSocketFrame xWebSocketFrame) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void onMessageError(WebSocket xWebSocket, WebSocketException xE, List<WebSocketFrame> xList) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void onMessageDecompressionError(WebSocket xWebSocket, WebSocketException xE, byte[] xBytes) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void onTextMessageError(WebSocket xWebSocket, WebSocketException xE, byte[] xBytes) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void onSendError(WebSocket xWebSocket, WebSocketException xE, WebSocketFrame xWebSocketFrame) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void onUnexpectedError(WebSocket xWebSocket, WebSocketException xE) throws Exception {
        mListener.onError(this, xE);
    }

    @Override
    public void handleCallbackError(WebSocket xWebSocket, Throwable xThrowable) throws Exception {
        mListener.onError(this, xThrowable);
    }

    @Override
    public void onSendingHandshake(WebSocket xWebSocket, String xS, List<String[]> xList) throws Exception {

    }

    @Override
    public void get(String xVariable) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setCommand(Command.GET);
        mb.setAuth(mAuth);
        mb.setVariable(xVariable);
        mSocket.sendText(mb.build());
    }

    @Override
    public void get(WrGetVariableList xList) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setCommand(Command.GET);
        mb.setAuth(mAuth);
        mb.setVariable(xList);
        mSocket.sendText(mb.build());
    }

    @Override
    public void command(ICommand xCommand) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(xCommand.get());
        String var = xCommand.getVariable();
        if (var != null) {
            mb.setVariable(var);
        }
        mSocket.sendText(mb.build());
    }

    public void set(String xVariable, String xValue) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setVariable(xVariable, xValue);
        mb.setCommand(Command.SET);
        mSocket.sendText(mb.build());
    }

    @Override
    public void set(WrVariablePair xPair) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setVariable(xPair);
        mb.setCommand(Command.SET);
        mSocket.sendText(mb.build());
    }

    @Override
    public void set(WrSetVariableList xList) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setVariable(xList);
        mb.setCommand(Command.SET);
        mSocket.sendText(mb.build());
    }

    @Override
    public void validate(String xVariable, String xValue) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setVariable(xVariable, xValue);
        mb.setCommand(Command.VALIDATE);
        mSocket.sendText(mb.build());
    }

    @Override
    public void validate(WrVariablePair xPair) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setVariable(xPair);
        mb.setCommand(Command.VALIDATE);
        mSocket.sendText(mb.build());
    }

    @Override
    public void validate(WrSetVariableList xList) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setVariable(xList);
        mb.setCommand(Command.VALIDATE);
        mSocket.sendText(mb.build());
    }

    @Override
    public void networkGet(String xVar) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xVar);
        mb.setCommand(Command.NETWORK_GET);
        mSocket.sendText(mb.toString());
    }

    @Override
    public void networkGet(WrGetVariableList xList) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xList);
        mb.setCommand(Command.NETWORK_GET);
        mSocket.sendText(mb.toString());
    }

    @Override
    public void networkSet(String xCmd, String xValue) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xCmd, xValue);
        mb.setCommand(Command.NETWORK_SET);
        mSocket.sendText(mb.toString());
    }

    @Override
    public void networkSet(WrVariablePair xPair) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xPair);
        mb.setCommand(Command.NETWORK_SET);
        mSocket.sendText(mb.toString());
    }

    @Override
    public void networkSet(WrSetVariableList xList) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xList);
        mb.setCommand(Command.NETWORK_SET);
        mSocket.sendText(mb.toString());
    }

    @Override
    public void networkValidate(String xCmd, String xValue) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xCmd, xValue);
        mb.setCommand(Command.NETWORK_SET);
        mSocket.sendText(mb.toString());
    }

    @Override
    public void networkValidate(WrVariablePair xPair) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xPair);
        mb.setCommand(Command.NETWORK_SET);
        mSocket.sendText(mb.toString());
    }

    @Override
    public void networkValidate(WrSetVariableList xPair) {
        WrJsonNetworkMessageBuilder mb = new WrJsonNetworkMessageBuilder();
        mb.setAuth(mAuth);
        mb.setRequireAll(mRequireAll);
        mb.setVariable(xPair);
        mb.setCommand(Command.SET);
        mSocket.sendText(mb.build());
    }

    @Override
    public void comand(ICommand xCommand) {
        WrJsonMessageBuilder mb = new WrJsonMessageBuilder();
        mb.setAuth(mAuth);
        mb.setCommand(xCommand.get());
        mSocket.sendText(mb.build());
    }

    /**
     * this can be overridden by child implementations of WrAsyncSocket
     * in order to inject additional messages into the IPERF command
     */
    protected void onSendIperf(JSONObject xObject) {
    }

    public void iperf(String xTo, int xLen, boolean xTxOnly, boolean xDetailed, boolean xMetadata) {
        WrJsonIperfMessageBuilder mb = new WrJsonIperfMessageBuilder();
        mb.setAuth(mAuth);
        mb.setTarget(xTo);
        mb.setTxOnly(xTxOnly);
        mb.setLength(xLen);
        mb.setDetailed(xDetailed);
        mb.setMetaData(xMetadata);
        // add onto the JSONObject reference here
        onSendIperf(mb.buildRootObject());
        mSocket.sendText(mb.build());
    }

    public void sendFirmware(File xFile) throws IOException {
        if (xFile == null || !xFile.exists()) {
            throw new FileNotFoundException("Firmware file not found");
        }
        WrJsonFirmwareMessageBuilder mb = new WrJsonFirmwareMessageBuilder();
        mb.setAuth(mAuth);
        mb.setFileSize(xFile.length());
        mSocket.sendText(mb.build());
    }
}
