package com.persistentsystems.socketclient.messaging;

import com.persistentsystems.socketclient.messaging.interfaces.AbstractWrMessageBuilder;
import org.json.JSONObject;

/**
 * This is an (internal) class for build an API compliant Iperf message.
 */
public class WrJsonIperfMessageBuilder extends AbstractWrMessageBuilder {

    private final String TCP_CMD = "tcp_test";
    private final JSONObject mRoot;

    /**
     * Public constructor.
     */
    public WrJsonIperfMessageBuilder() {
        super();
        mRoot = new JSONObject();
        mRoot.put("protocol_version", PROTO);
        mRoot.put("msgtype", REQ);
        mRoot.put("command", TCP_CMD);
        mRoot.put("token", WrMessageToken.getIperfToken());
    }

    @Override
    public JSONObject buildRootObject() {
        mRoot.put("password", mAuth.getPassword());
        mRoot.put("username", mAuth.getUserName());
        return mRoot;
    }

    @Override
    public String build() {
        return buildRootObject().toString();
    }

    /**
     * This determines if the iperf test performed was transmit only.
     * @return true if yes, false if no.
     */
    public boolean isTxOnly() {
        return mRoot.has("tx_only") && mRoot.getBoolean("tx_only");
    }

    /**
     * This tells the API to only perform a TX test.
     * @param xTx transmit only?
     * @return `this` so messages can be built easier.
     */
    public WrJsonIperfMessageBuilder setTxOnly(boolean xTx) {
        mRoot.put("tx_only", xTx);
        return this;
    }

    /**
     * This tells the API which remote (IP) to perform the iperf test to.
     * @param xTarget ip
     * @return `this` so messages can be built easier.
     */
    public WrJsonIperfMessageBuilder setTarget(String xTarget) {
        mRoot.put("ip", xTarget);
        return this;
    }

    /**
     * Retreives the targeted IP from the response messages.
     * @return ip
     */
    public String getTargetIp() {
        if (mRoot.has("ip")) {
            return mRoot.getString("ip");
        }
        return "";
    }

    /**
     * Retreives a bool of if a detailed iperf report was run
     * @return detailed
     */
    public boolean isDetailed() {
        return mRoot.has("detailed_report") && mRoot.getBoolean("detailed_report");
    }

    /**
     * This tells the API to run a detailed report and pull more iperf information.
     * @param xDetailed
     * @return `this` so messages can be built easier.
     */
    public WrJsonIperfMessageBuilder setDetailed(boolean xDetailed) {
        mRoot.put("detailed_report", xDetailed);
        return this;
    }

    /**
     * This tells iperf to return additional metadata about the communication.
     * See usermanual/documentation, however this generally means neighbor information
     * as well as location information.
     * This is not required to be set.
     * @param xMetaData true to pull more info
     * @return `this` so messages can be built easier.
     */
    public WrJsonIperfMessageBuilder setMetaData(boolean xMetaData) {
        mRoot.put("meta_data", xMetaData);
        return this;
    }

    /**
     * Determines if the received message has metadata
     * @return true/false
     */
    public boolean hasMetaData() {
        return mRoot.has("meta_data") && mRoot.getBoolean("meta_data");
    }

    /**
     * Gets the length of this particular iperf test
     * @return length in seconds
     */
    public int getLength() {
        if (mRoot.has("time_seconds")) {
            return mRoot.getInt("time_seconds");
        }
        return 0;
    }

    /**
     * Sets the length, in seconds, to run this iperf test.
     * Note that if {@link #setTxOnly(boolean)} is set to false (or unset),
     * the total iperf length will be double the lenght set here.
     * @param xLength length in seconds
     * @return `this` so messages can be built easier.
     */
    public WrJsonIperfMessageBuilder setLength(int xLength) {
        mRoot.put("time_seconds", xLength);
        return this;
    }

}
