package com.persistentsystems.socketclient.messaging;

import com.persistentsystems.socketclient.messaging.interfaces.AbstractWrMessageBuilder;
import org.json.JSONObject;

/**
 * This is an (internal) class used to build an API compliant message for firmware uploads.
 */
public class WrJsonFirmwareMessageBuilder extends AbstractWrMessageBuilder {
    private final String FW_UPDATE = "firmware_update";
    long mSize = 0;
    private boolean mDataReset = false;

    /**
     * This sets the filesize of the firmware on disk.
     * This is required.
     * @param xLen file length in bytes
     */
    public void setFileSize(long xLen) {
        mSize = xLen;
    }

    @Override
    protected JSONObject buildRootObject() {
        JSONObject rtn = new JSONObject();
        rtn.put("username", mAuth.getUserName());
        rtn.put("password", mAuth.getPassword());
        rtn.put("msgtype", REQ);
        rtn.put("protocol_version", PROTO);
        rtn.put("token", WrMessageToken.getFirmwareToken());
        rtn.put("command", FW_UPDATE);
        rtn.put("file_size", mSize);
        rtn.put("data_reset", mDataReset);
        return rtn;
    }

    /**
     * Tells the API if we should perform an Android data wipe.
     * See API spec / MPU5 user manual for more information.
     * This is not required to be set.
     * @param xBool perform wipe
     */
    public void setDataReset(boolean xBool) {
        mDataReset = xBool;
    }

    @Override
    public String build() {
        JSONObject rtn = buildRootObject();
        return rtn.toString();
    }
}
