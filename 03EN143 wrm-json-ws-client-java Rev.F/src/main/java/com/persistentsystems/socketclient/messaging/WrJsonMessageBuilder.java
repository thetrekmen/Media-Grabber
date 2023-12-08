package com.persistentsystems.socketclient.messaging;

import com.persistentsystems.socketclient.commands.Command;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import com.persistentsystems.socketclient.messaging.interfaces.AbstractWrMessageBuilder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an (internal) basic JSON message builder to build API compliant messages.
 */
public class WrJsonMessageBuilder extends AbstractWrMessageBuilder {

    private final List<JSONObject> mVariables;
    private Command mCommand;

    /**
     * Public constructor.
     */
    public WrJsonMessageBuilder() {
        super();
        mVariables = new ArrayList<>();
    }

    /**
     * Sets a given variable into the message to be built.
     * @param xVariable variable
     * @return `this`
     */
    public WrJsonMessageBuilder setVariable(String xVariable) {
        JSONObject j = new JSONObject();
        j.put(xVariable, new JSONObject());
        mVariables.add(j);
        return this;
    }

    /**
     * Stores a list of Get variables into the message to be built.
     * @param xList list
     * @return `this`
     */
    public WrJsonMessageBuilder setVariable(WrGetVariableList xList) {
        for (String each : xList) {
            JSONObject j = new JSONObject();
            j.put(each, new JSONObject());
            mVariables.add(j);
        }
        return this;
    }

    /**
     * Stores a list of Set variables into the message to be built.
     * @param xList list
     * @return `this`
     */
    public WrJsonMessageBuilder setVariable(WrSetVariableList xList) {
        for (WrVariablePair each : xList) {
            JSONObject j = new JSONObject();
            JSONObject v = new JSONObject();
            v.put("value", each.value);
            j.put(each.variable, v);
            mVariables.add(j);
        }
        return this;
    }

    /**
     * Stores the command to be used.
     * See {@link Command} for types.
     * @param xCommand command
     * @return `this`
     */
    public WrJsonMessageBuilder setCommand(Command xCommand) {
        mCommand = xCommand;
        return this;
    }

    /**
     * Returns the stored (or generated) token for this message.
     * @return token
     */
    public String getToken() {
        return mToken;
    }

    @Override
    protected JSONObject buildRootObject() {
        JSONObject root = new JSONObject();
        root.put("protocol_version", PROTO);
        if (mAuth != null) {
            root.put("password", mAuth.getPassword());
            root.put("username", mAuth.getUserName());
        }
        root.put("msgtype", REQ);
        if (mCommand != null) {
            root.put("command", mCommand.toString());
        }
        root.put("token", mToken);
        JSONObject vars = new JSONObject();
        if (mVariables.size() > 0) {
            for (JSONObject each : mVariables) {
                for (String key : each.keySet()) {
                    // should only run once per obj
                    vars.put(key, each.get(key));
                }
            }
            root.put("variables", vars);
        }
        return root;
    }

    @Override
    public String build() {
        JSONObject root = buildRootObject();
        return root.toString();
    }

    /**
     * Stores a Set variable into this message.
     * @param xVariable var
     * @param xValue value
     * @return `this`
     */
    public WrJsonMessageBuilder setVariable(String xVariable, String xValue) {
        JSONObject val = new JSONObject();
        val.put("value", xValue);
        JSONObject var = new JSONObject();
        var.put(xVariable, val);
        mVariables.add(var);
        return this;
    }

    /**
     * Stores a Set variable pair into this message.
     * @param xPair variable pair
     * @return `this`
     */
    public WrJsonMessageBuilder setVariable(WrVariablePair xPair) {
        JSONObject val = new JSONObject();
        val.put("value", xPair.value);
        JSONObject var = new JSONObject();
        var.put(xPair.variable, val);
        mVariables.add(var);
        return this;
    }

}
