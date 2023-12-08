package com.persistentsystems.socketclient.net;

import java.net.URI;

/**
 * This class is a holder for an IP/port combination used to determine which WaveRelay
 * to connect to.
 * <p>
 * Note: if you are developing an application to run on the MPU5, you may also
 * set `localhost` as the ip address. (Still set 443 as the port.)
 * </p>
 */
public class WrIpAddress {
    private final String mWss = "wss://";
    private final String mAfterIp = "/xxx";
    private String mIpAddress;
    private int mPort;

    /**
     * Public constructor.
     */
    public WrIpAddress() {
        mIpAddress = null;
        mPort = -1;
    }

    /**
     * Returns the fully built conneciton URL for the Wr(Blocking/Async)Socket
     * @return built string
     */
    public URI getConnectUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(mWss);
        sb.append(mIpAddress).append(":").append(mPort).append(mAfterIp);
        return URI.create(sb.toString());
    }

    /**
     * Returns the port we are connecting on.
     * @return previously set port
     */
    public int getPort() {
        return mPort;
    }

    /**
     * Sets the port to connect to (typically 443).
     * @param xPort port
     * @return `this`
     */
    public WrIpAddress setPort(int xPort) {
        mPort = xPort;
        return this;
    }

    /**
     * Returns a string representation of the IP.
     * @return ip address
     */
    public String get() {
        return mIpAddress;
    }

    /**
     * Sets the IP address that we will attempt to connect to.
     * <p>
     * NOTE: This is the IP address as your host machine sees the radio.
     * </p>
     * <p>
     * Note: If you are developing an application to run on the MPU5, you may also set
     * `localhost` as the ip address.
     * </p>
     * @param xIpAddress
     * @return `this`
     */
    public WrIpAddress set(String xIpAddress) {
        mIpAddress = xIpAddress;
        return this;
    }

    @Override
    public String toString() {
        return getConnectUrl().toString();
    }
}
