package com.persistentsystems.socketclient.results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class contains parsed information from the json message returned on
 * various websocket calls.
 */
public class WrJsonResult {

    protected JSONObject mRootObject;
    protected JSONObject mVars;
    private boolean mHasError = false;
    private String mErrorMessage = null;

    /**
     * Public constructor.
     * @param xString message from websocket
     */
    public WrJsonResult(String xString) {
        mHasError = false;
        mErrorMessage = null;
        mRootObject = new JSONObject(xString);
        if (mRootObject.has("variables")) {
            mVars = mRootObject.getJSONObject("variables");
        }
        if (mRootObject.has("error")) {
            mHasError = true;
            JSONObject err = mRootObject.getJSONObject("error");
            if (err != null) {
                mErrorMessage = err.getString("display");
            }
            mVars = null; // return null in get functions
        }
        if (mRootObject.has("final_status")) {
            if (!mRootObject.getString("final_status").equalsIgnoreCase("ok")) {
                mHasError = true;
            }
        }
    }

    /**
     * Determines if the received message contains an error.
     * Note this is not a parsing error, this is an error from the websocket api.
     * @return error
     */
    public boolean hasError() {
        return mHasError;
    }

    /**
     * Gets the error message, if any, received.
     * @return error message or null
     */
    public String getError() {
        return mErrorMessage;
    }

    /**
     * Gets a castable object from the received json message.
     * @param xVariable variable
     * @return Object or null if not found.
     */
    public Object get(String xVariable) {
        if (mVars != null && mVars.has(xVariable)) {
            JSONObject varObject = mVars.getJSONObject(xVariable);
            if (varObject != null && varObject.has("value")) {
                return varObject.get("value");
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Returns the parsed {@link JSONObject} from the received message.
     * This will contain all information received.
     * @return json object
     */
    public JSONObject getJson() {
        return mRootObject;
    }

    @Override
    public String toString() {
        return mRootObject.toString();
    }

    /**
     * Determine if a variable is contained within the response.
     * @param xVariable variable
     * @return bool
     */
    public boolean has(String xVariable) {
        return mVars != null && mVars.has(xVariable);
    }

    /**
     * Returns the variable value.
     * <b>
     * NOTE: if multiple values were issued in this command,
     * use {@link #getValues()}. Otherwise the first found value will be returned.
     * </b>
     * @return value or null if none.
     */
    public String getValue() {
        if (mVars != null) {
            for (Iterator<String> it = mVars.keys(); it.hasNext(); ) {
                String key = it.next();
                JSONObject var = mVars.getJSONObject(key);
                Object val = var.get("value");
                if (val instanceof String) {
                    return (String) val;
                } else if (val instanceof JSONObject) {
                    return val.toString();
                } else if (val instanceof JSONArray) {
                    return ((JSONArray)val).get(0).toString();
                }
            }
        }
        return null;
    }

    /**
     * Returns a Map of String, String
     * where the key is the variable name, and the value is
     * a string representation of the value.
     * @return list
     */
    public Map<String, String> getValues() {
        Map<String, String> map = new HashMap<>();
        if (mVars == null) return null;
        for (String each : mVars.keySet()) {
            JSONObject kv = mVars.getJSONObject(each);
            map.put(each, kv.getString("value"));
        }
        return map;
    }

}
