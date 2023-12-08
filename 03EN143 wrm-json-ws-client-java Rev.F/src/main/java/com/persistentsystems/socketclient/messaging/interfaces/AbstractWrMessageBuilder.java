package com.persistentsystems.socketclient.messaging.interfaces;

import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.messaging.WrMessageToken;
import org.json.JSONObject;

/**
 * This is an (internal) class used to give all message builders a common interface
 */
public abstract class AbstractWrMessageBuilder {
    public final String PROTO = "1.4.0";
    public final String REQ = "req";
    public final String mToken;
    public WrAuth mAuth;

    public AbstractWrMessageBuilder() {
        mToken = WrMessageToken.getRandomToken();
    }

    public void setAuth(WrAuth xAuth) {
        mAuth = xAuth;
    }

    abstract protected JSONObject buildRootObject();

    abstract public String build();

    @Override
    public String toString() {
        return build();
    }
}
