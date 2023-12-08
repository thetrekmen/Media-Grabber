package com.persistentsystems.socketclient.listeners;

import com.persistentsystems.socketclient.results.WrFirmwareResult;

/**
 * This interface <b>must</b> be implemented by any {@link com.persistentsystems.socketclient.sockets.WrAsyncSocket} wanting
 * to receive notifications about the firmware update process.
 * See {@link com.persistentsystems.socketclient.sockets.WrAsyncSocket#setFirmwareListener(WrFirmwareListener)}.
 */
public interface WrFirmwareListener {
    /**
     * The firmware transfer has finished.
     *
     * @param xResult result
     */
    void onFirmwareFinished(WrFirmwareResult xResult);

    /**
     * An error was detected during the firmware process.
     *
     * @param xResult result
     */
    void onFirmwareError(WrFirmwareResult xResult);

    /**
     * The firmware transfer is ready to begin.
     * This happens when the mpu5 is setup in the correct mode.
     *
     * @param xResult result
     */
    void onFirmwareReady(WrFirmwareResult xResult);

}
