package com.persistentsystems.examples.clang;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;

import java.io.IOException;
import java.util.Map;

public class Clang {

    private WrAuth mAuth;
    private WrIpAddress mIp;
    private WrBlockingSocket mSocket;


    private static void print(String TAG, String fmt) {
        System.out.println(TAG + ": " + fmt);
    }


    public static void main(String[] xArgs) throws IOException, WebSocketException, WebSocketTimeout, InterruptedException {
        if (xArgs.length == 0) {
            System.out.println("Usage");
            System.out.println("Args are positional. I don't have a fancy argparser");
            System.out.println("1st arg: ip (EG: 172.26.1.152)");
            return;
        }
        new Clang(xArgs);
    }

    private Clang(String[] xArgs) throws IOException, WebSocketException, WebSocketTimeout, InterruptedException {
        mIp = new WrIpAddress();
        mIp.set(xArgs[0]);
        mIp.setPort(443);

        mAuth = new WrAuth();
        mAuth.setPassword("password");
        mAuth.setUserName("factory");

        mSocket = new WrBlockingSocket();
        mSocket.setAuth(mAuth);
        mSocket.setIpAddress(mIp);

        mSocket.setRequireAll(true);

        mSocket.connectBlocking();

        boolean failed = false;

        System.out.println("================================");
        System.out.println("Please ensure your radios are on the same network (key/connectivity) before continuing");
        System.out.println("================================");
        System.out.println("Ensure your radios are listed in the managed node list.");
        System.out.println("================================");
        System.out.println("Change your security key after this test, a pre-generated key is being used");
        System.out.println("================================");
        Thread.sleep(2000);

        System.out.println("================================");
        System.out.println("Beginning clang security tests");

        // single node get
        failed = singleNodeGet();
        System.out.println("================================");

//        // single node set
//        failed |= singleNodeSet();
//        System.out.println("================================");

        // single node validate
        failed |= singleNodeValidate();
        System.out.println("================================");

        // network get
        failed |= networkGet();
        System.out.println("================================");

        // network set
        System.out.println("Performing network sets.");
        System.out.println("This may take a few minutes please wait");
        failed |= networkSet();
        System.out.println("================================");

        failed |= networkValidate();
        System.out.println("================================");

        System.out.println("Test complete");
        System.out.println("================================");

        if (failed) {
            System.err.println("================================");
            System.err.println("At least one clang test failed");
            System.err.println("================================");
        } else {
            System.out.println("================================");
            System.out.println("      Clang tests passed!");
            System.out.println("================================");
        }

        mSocket.close();
    }

    private boolean runTest(String TAG, String xVar, String xExpect, int xCheckLen) throws WebSocketTimeout {
        final String val = mSocket.get(xVar).getValue();
        if (val.equals(xExpect)) {
            print(TAG, xVar + " test passed");
            return false;
        } else {
            if (xCheckLen > 0 && val.length() >= xCheckLen) {
                // we should just be checking length / existence of _a_ value
                print(TAG, xVar + " length check passed");
                return false;
            }
            print(TAG, xVar + " test failed");
            if (xCheckLen > 0) {
                print(TAG, xVar + " expected len >= (" + xCheckLen + ") got (" + val.length() + ")");
            } else {
                print(TAG, xVar + " got: " + val);
                print(TAG, xVar + " expected: " + xExpect);
            }
            return true;
        }
    }

    private boolean singleNodeGet() throws WebSocketTimeout {
        final String TAG = "Single Node Get";
        boolean failed = false;

        // test 1, make sure we can get security mode
        failed = runTest(TAG, "security_mode", ClangConfig.SECURITY_MODE_DEFAULT, 0);
        // make sure security key is at least 192
        failed |= runTest(TAG, "security_key", "", 192);
        // make sure we see crypto as operational
        failed |= runTest(TAG, "security_operational", "Operational", 0);

        return failed;
    }

    private boolean singleNodeSet() throws WebSocketTimeout {
        final String TAG = "Single Node Set";
        print(TAG, "Please wait, this test takes up to 2 minutes.");

        WrJsonResult vals = mSocket.set(
            new WrSetVariableList(
                new WrVariablePair[]{
                    // these must be set together
                    new WrVariablePair("security_mode", ClangConfig.SECURITY_MODE_TEST),
                    new WrVariablePair("security_key", ClangConfig.SECURITY_KEY_TEST)
                }
            )
        );
        WrJsonResult mode = mSocket.get("security_operational");

        WrJsonResult valsRevert = mSocket.set(
            new WrSetVariableList(
                new WrVariablePair[] {
                    new WrVariablePair("security_mode", ClangConfig.SECURITY_MODE_DEFAULT),
                    new WrVariablePair("security_key", ClangConfig.SECURITY_KEY_DEFAULT)
                }
            )
        );
        WrJsonResult modeRevert = mSocket.get("security_operational");

        if (vals.hasError()) {
            print(TAG, "Error detected in security_mode + security_key");
            print(TAG, vals.getError());
            print(TAG, vals.toString());
            return true;
        }
        if (valsRevert.hasError()) {
            print(TAG, "Error detected in reverting security_mode + security_key");
            print(TAG, valsRevert.getError());
            print(TAG, valsRevert.toString());
            return true;
        }
        if (mode.hasError()) {
            print(TAG, "Error detected in security_operational");
            print(TAG, mode.getError());
            print(TAG, mode.toString());
            return true;
        }
        if (modeRevert.hasError()) {
            print(TAG, "error detected after revert with security_operational");
            print(TAG, modeRevert.getError());
            print(TAG, modeRevert.toString());
            return true;
        }
        Map<String, String> map = vals.getValues();
        String modeVal;
        if (!map.get("security_mode").equals(ClangConfig.SECURITY_MODE_TEST)) {
            print(TAG, "Unable to set security_mode to " + ClangConfig.SECURITY_MODE_TEST);
            print(TAG, "Test failed.");
            return true;
        }
        if (!map.get("security_key").equals(ClangConfig.SECURITY_KEY_TEST)) {
            print(TAG, "Unable to set security_key to expected value");
            print(TAG, "Test failed");
            return true;
        }
        modeVal = mode.getValue();
        if (!modeVal.equals("Operational")) {
            print(TAG, "Security not operational after set");
            print(TAG, "Test failed");
            return true;
        }

        map = valsRevert.getValues();
        if (!map.get("security_mode").equals(ClangConfig.SECURITY_MODE_DEFAULT)) {
            print(TAG, "Unable to revert security_mode to expected value");
            print(TAG, "test failed");
            return true;
        }
        if (!map.get("security_key").equals(ClangConfig.SECURITY_KEY_DEFAULT)) {
            print(TAG, "Unable to revert security_key to expected value");
            print(TAG, "test failed");
            return true;
        }
        modeVal = modeRevert.getValue();
        if (!modeVal.equals("Operational")) {
            print(TAG, "Security not operational after revert settings");
            print(TAG, "Test failed");
            return true;
        }
        print(TAG, "security_mode + security_key test passed");
        return false;
    }

    private boolean singleNodeValidate() throws WebSocketTimeout {
        final String TAG = "Single Node Validate";
        String var = "security_key";
        String val = ClangConfig.SECURITY_KEY_DEFAULT;
        WrSetVariableList list = new WrSetVariableList();
        list.add("security_mode", ClangConfig.SECURITY_MODE_DEFAULT);
        list.add("security_key", ClangConfig.SECURITY_KEY_DEFAULT);

        WrJsonResult res = mSocket.validate(list);
        if (res.hasError()) {
            print(TAG, "Test failed");
            print(TAG, res.toString());
            print(TAG, res.getError());
            return true;
        }
        print(TAG, "validation test passed");
        return false;
    }

    private boolean networkGet() throws WebSocketTimeout {
        final String TAG = "Network Get";

        WrJsonNetworkResults res = mSocket.networkGet("security_operational");
        if (res.hasError()) {
            print(TAG, "Error detected.");
            print(TAG, res.getError());
            print(TAG, res.toString());
            return true;
        }
        for(WrJsonNetworkResult each : res.getResults()) {
            if (!each.getValue().equals("Operational")) {
                print(TAG, "Test failed");
                print(TAG, each.getIp() + " security_mode not == Operational");
                print(TAG, each.toString());
                return true;
            }
        }

        res = mSocket.networkGet("security_key");
        String tmp = null;
        for(WrJsonNetworkResult each : res.getResults()) {
            if (tmp == null) {
                tmp = each.getValue();
                continue;
            }
            if (!each.getValue().equals(tmp)) {
                print(TAG, "Test failed");
                print(TAG, "Security keys differ.");
                return true;
            }
        }

        res = mSocket.networkGet("security_mode");
        tmp = null;
        for(WrJsonNetworkResult each : res.getResults()) {
            if (tmp == null) {
                tmp = each.getValue();
                continue;
            }
            if (!each.getValue().equals(tmp)) {
                print(TAG, "Test failed.");
                print(TAG, "Security mode differs");
                return true;
            }
        }

        print(TAG, "test passed");
        return false;
    }

    private boolean networkSet() throws WebSocketTimeout {
        final String TAG = "Network Set";

        WrSetVariableList list = new WrSetVariableList();
        list.add("security_mode", ClangConfig.SECURITY_MODE_TEST);
        list.add("security_key", ClangConfig.SECURITY_KEY_TEST);

        WrJsonNetworkResults res = mSocket.networkSet(list);
        WrJsonNetworkResults opRes = mSocket.networkGet("security_operational");

        WrSetVariableList defList = new WrSetVariableList();
        defList.add("security_mode", ClangConfig.SECURITY_MODE_DEFAULT);
        defList.add("security_key", ClangConfig.SECURITY_KEY_DEFAULT);
        mSocket.networkSet(defList);

        for(WrJsonNetworkResult each : res) {
            Map<String, String> e = each.getValues();
            for(String v : e.values()) {
                if (v.equals(ClangConfig.SECURITY_KEY_TEST) || v.equals(ClangConfig.SECURITY_MODE_TEST)) {
                    continue;
                }
                print(TAG, "Test failed.");
                return true;
            }
        }
        for(WrJsonNetworkResult each : opRes) {
            if (!each.getValue().equals("Operational")) {
                print(TAG, "Security not operational");
                return true;
            }
        }

        print(TAG, "Test passed");
        return false;
    }

    private boolean networkValidate() throws WebSocketTimeout {
        final String TAG = "Network Validate";
        WrSetVariableList list = new WrSetVariableList();
        list.add("security_key", ClangConfig.SECURITY_KEY_DEFAULT);
        list.add("security_mode", ClangConfig.SECURITY_MODE_DEFAULT);

        WrJsonNetworkResults res = mSocket.networkValidate(list);
        
        for(WrJsonNetworkResult each : res) {
            Map<String, String> e = each.getValues();
            for(String v : e.values()) {
                if (v.equals(ClangConfig.SECURITY_KEY_DEFAULT) || v.equals(ClangConfig.SECURITY_MODE_DEFAULT)) {
                    continue;
                }
                print(TAG, "Test failed.");
                return true;
            }
        }

        print(TAG, "Test passed");
        return false;
    }



}
