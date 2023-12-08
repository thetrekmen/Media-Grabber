package com.persistentsystems.socketclient.listeners;

import com.neovisionaries.ws.client.WebSocket;

/**
 * This is used internally to {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket}.
 */
public class WrBlockingSocketListener extends AbstractWrSocketOnMessageListener {
    /**
     * Socket received a message.
     * Defer message classification to implementing class.
     *
     * @param xWebSocket The WebSocket.
     * @param xMessage   message
     * @throws Exception
     */
    @Override
    public void onTextMessage(WebSocket xWebSocket, String xMessage) throws Exception {

    }
}
