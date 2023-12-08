package com.persistentsystems.socketclient.listeners;

import com.neovisionaries.ws.client.WebSocketFrame;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.results.WrIperfResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrAsyncSocket;

/**
 * This interface <b>MUST</b> be implemented by any class using an {@link WrAsyncSocket}.
 * Set the listener by {@link WrAsyncSocket#setSocketListener(WrAsyncSocketListener)}.
 * Alternatively, an anonymous implementation can be used in {@link WrAsyncSocket#setSocketListener(WrAsyncSocketListener)}.
 */
public interface WrAsyncSocketListener {

    /**
     * Called when the websocket detects an error has occured.
     *
     * @param xSocket websocket.
     * @param xCause  exception being thrown by xSocket.
     */
    void onError(WrAsyncSocket xSocket, Throwable xCause);

    /**
     * Called when the websocket has closed.
     *
     * @param xSocket socket that has closed.
     */
    void onClose(WrAsyncSocket xSocket);

    /**
     * Called when the websocket has opened.
     * This is how you'll know the websocket has opened properly. And where you'll be able to issue
     * your initial get/set/validates without having to worry about a timer or sleep of some sort.
     *
     * @param xSocket websocket that has opened
     * @throws WebSocketTimeout thrown if the conneciton times out.
     */
    void onOpen(WrAsyncSocket xSocket) throws WebSocketTimeout;

    /**
     * Called when the websocket has recevied a message from a single node.
     *
     * @param xSocket websocket
     * @param xResult message
     */
    void onMessage(WrAsyncSocket xSocket, WrJsonResult xResult);

    /**
     * Called when the websocket has received a network message from several nodes.
     *
     * @param xSocket websocket
     * @param xResult message
     */
    void onNetworkMessage(WrAsyncSocket xSocket, WrJsonNetworkResult xResult);

    /**
     * Called when a `pong` message has been received.
     * See Websockets RFC for details.
     *
     * @param xSocket         websocket
     * @param xWebSocketFrame frame with the pong
     */
    void onPong(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame);

    /**
     * Called when a `ping` message has been received.
     * See Websockets RFC for details.
     *
     * @param xSocket         websocket
     * @param xWebSocketFrame frame with the ping
     */
    void onPing(WrAsyncSocket xSocket, WebSocketFrame xWebSocketFrame);

    /**
     * Called when an iperf message has been received.
     * Note that this is not the final message. </br>
     * This is just representing a time slice (that can be viewed in {@link WrIperfResult#getSeconds()}.
     *
     * @param xSocket websocket
     * @param xResult message
     */
    void onIperfMessage(WrAsyncSocket xSocket, WrIperfResult xResult);

    /**
     * Called when an iperf operation is completed.
     * See {@link WrIperfResult} for more available information.
     *
     * @param xSocket websocket
     * @param xResult message
     */
    void onIperfComplete(WrAsyncSocket xSocket, WrJsonResult xResult);
}
