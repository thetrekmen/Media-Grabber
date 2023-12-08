package com.persistentsystems.socketclient.results;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class returns parsed information about received network results from issuing
 * an API call.
 */
public class WrJsonNetworkResults extends WrJsonResult implements Iterable<WrJsonNetworkResult> {

    private List<WrJsonNetworkResult> mList;

    /**
     * Public constructor.
     * Receives information from the websocket message.
     * @param xString message from websocket
     */
    public WrJsonNetworkResults(String xString) {
        super(xString);
        mList = fillList();
    }

    private List<WrJsonNetworkResult> fillList() {
        // make sure we have the IP list object we're about to iter over
        List<WrJsonNetworkResult> list = new ArrayList<>();
        if (!mRootObject.has("ip_list")) {
            return list;
        }

        JSONObject ipList = mRootObject.getJSONObject("ip_list");
        if (ipList.length() == 0) {
            return list;
        }

        for (String ip : ipList.keySet()) {
            JSONObject jso = makeBaseObject();
            jso.put("variables", ipList.getJSONObject(ip).getJSONObject("variables"));
            jso.put("ip", ip);
            list.add(new WrJsonNetworkResult(jso.toString()));
        }
        return list;
    }

    private JSONObject makeBaseObject() {
        JSONObject jso = new JSONObject();
        if (mRootObject.has("msgtype")) {
            jso.put("msg_type", mRootObject.get("msgtype"));
        }
        if (mRootObject.has("command")) {
            jso.put("command", mRootObject.get("command"));
        }
        if (mRootObject.has("unit_id")) {
            jso.put("unit_id", mRootObject.get("unit_id"));
        }
        if (mRootObject.has("token")) {
            jso.put("token", mRootObject.get("token"));
        }
        if (mRootObject.has("protocol_version")) {
            jso.put("protocol_version", mRootObject.get("protocol_version"));
        }
        if (mRootObject.has("final_status")) {
            jso.put("final_status", mRootObject.get("final_status"));
        }
        return jso;
    }

    /**
     * Returns a list of network results parsed from the JSON.
     * @return
     */
    public List<WrJsonNetworkResult> getResults() {
        return mList;
    }

    @Override
    public Iterator<WrJsonNetworkResult> iterator() {
        if (mList == null) {
            mList = fillList();
        }
        return mList.iterator();
    }
}
