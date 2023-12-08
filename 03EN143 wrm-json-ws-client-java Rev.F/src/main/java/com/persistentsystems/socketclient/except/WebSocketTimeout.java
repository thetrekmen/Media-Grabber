package com.persistentsystems.socketclient.except;

/**
 * This exception is thrown when the websocket times out.
 * <p>
 * it's most commonly seen in the {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket} class.
 * </p>
 * <p>
 * The timeout value is configurable via {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT}
 * </p>
 */
public class WebSocketTimeout extends Exception {
    // empty class
    public WebSocketTimeout(String xReason) {
        super(xReason);
    }
}
