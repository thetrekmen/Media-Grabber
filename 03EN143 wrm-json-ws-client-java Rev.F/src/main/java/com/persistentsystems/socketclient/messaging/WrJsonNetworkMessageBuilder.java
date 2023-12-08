package com.persistentsystems.socketclient.messaging;

import org.json.JSONObject;

/**
 * This is an (internal) API compliant message builder to build network messages.
 */
public class WrJsonNetworkMessageBuilder extends WrJsonMessageBuilder {
    private boolean mRequireAll = false;

    /**
     * Stores the `require all` api command into this message.
     * @param xValue require all
     */
    public void setRequireAll(boolean xValue) {
        mRequireAll = xValue;
    }

    @Override
    protected JSONObject buildRootObject() {
        JSONObject r = super.buildRootObject();
        r.put("require_all_nodes", mRequireAll);
        return r;
    }
}
