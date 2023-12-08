package com.persistentsystems.socketclient.messaging;

import java.util.UUID;

/**
 * This is a simple data class to hold a few static functions for use with
 * (internal) websocket messaging.
 */
public class WrMessageToken {
    /**
     * Returns a constant string used in sending / receiving iperf messages.
     * @return IPERF_TOKEN
     */
    public static String getIperfToken() {
        return "IPERF_TOKEN";
    }

    /**
     * Returns a constant string used in sending / receiving firmware messages.
     * @return FIRMWARE_TOKEN
     */
    public static String getFirmwareToken() {
        return "FIRMWARE_TOKEN";
    }

    /**
     * This is used in general messaging situations where a random string is needed.
     * @return a random string of length 13.
     */
    public static String getRandomToken() {
        final UUID tok = UUID.randomUUID();
        return tok.toString().substring(0, 13);
    }
}
