package com.persistentsystems.socketclient.sockets;

import com.neovisionaries.ws.client.WebSocket;
import com.persistentsystems.socketclient.listeners.WrBlockingSocketListener;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;

/**
 * This is a package private class.
 * This should not need to be used outside of .sockets.*
 */
class WrBlockingListenerFactory {
    public static final WrBlockingSocketListener GetInstance(WrJsonNetworkResults[] xArray, boolean xDebug) {
        return new WrBlockingSocketListener() {
            @Override
            public void onTextMessage(WebSocket xWebSocket, String xString) throws Exception {
                if (xDebug) {
                    System.err.println(xString);
                }
                xArray[0] = new WrJsonNetworkResults(xString);
            }
        };
    }

    public static final WrBlockingSocketListener GetInstance(WrJsonResult[] xArray, boolean xDebug) {
        return new WrBlockingSocketListener() {
            @Override
            public void onTextMessage(WebSocket xWebSocket, String xString) throws Exception {
                if (xDebug) {
                    System.err.println(xString);
                }
                xArray[0] = new WrJsonResult(xString);
            }
        };
    }
}
