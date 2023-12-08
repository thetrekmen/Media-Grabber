package com.persistentsystems.examples.cli;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonNetworkResult;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

public class Cli {

    private static boolean DEBUG = false;
    private static boolean REQUIRE_ALL = false;
    private final String mTarget;
    private final OptionType mOptionType;
    private final String mValue;
    private final boolean mIsNetwork;
    private final WrBlockingSocket mSocket;
    private final String mCmd;

    public Cli(String xTgt, OptionType xType, String xCmd, String xValue, boolean xIsNetwork, int xTimeout) throws IOException, WebSocketException {
        mTarget = xTgt;
        mOptionType = xType;
        mValue = xValue;
        mIsNetwork = xIsNetwork;
        mCmd = xCmd;

        if (mCmd == null || mCmd.isEmpty()) {
            System.out.println("Invalid variable entered");
            System.exit(0);
        }
        if (mOptionType == OptionType.SET || mOptionType == OptionType.VALIDATE) {
            if (xValue.isEmpty()) {
                System.out.println("Please provide a value with -v when setting | validating a command");
                System.exit(0);
            }
        }
        System.out.println("Target: " + mTarget);
        System.out.println("Options: " + mOptionType);
        System.out.println("Cmd: " + mCmd);
        System.out.println("Network: " + mIsNetwork);
        System.out.println("Require all: " + REQUIRE_ALL);

        mSocket = new WrBlockingSocket();
        if (xTimeout > 0) {
            mSocket.setSocketTimeOutMS(xTimeout);
        }

        WrAuth auth = new WrAuth();
        auth.setUserName("factory");
        auth.setPassword("password");
        mSocket.setAuth(auth);

        WrIpAddress addr = new WrIpAddress();
        addr.set(mTarget);
        addr.setPort(443);
        mSocket.setIpAddress(addr);

        System.out.println("Connecting.....");
        mSocket.connectBlocking();
        System.out.println("Connected");
        if (mIsNetwork) {
            switch (mOptionType) {
                case GET:
                    networkGet();
                    break;
                case SET:
                    networkSet();
                    break;
                case VALIDATE:
                    networkValidate();
                    break;
            }
        } else {
            switch (mOptionType) {
                case GET:
                    localGet();
                    break;
                case SET:
                    localSet();
                    break;
                case VALIDATE:
                    localValidate();
                    break;
            }
        }

        mSocket.close();
        System.out.println("Socket closed. Exiting");
    }

    public static void main(String[] xArgs) throws IOException, WebSocketException {
        Options options = new Options();

        Option target = new Option("t", "target", true, "The MPU5 to connect via");
        target.setRequired(true);
        options.addOption(target);

        Option timeout = new Option("x", "timeout", true, "Set timeout for socket in milliseconds");
        options.addOption(timeout);

        Option cmd = new Option("c", "cmd", true, "A single command to send to the MPU5");
        cmd.setRequired(true);
        options.addOption(cmd);

        Option val = new Option("v", "val", true, "Value used during set | validate");
        val.setRequired(false);
        options.addOption(val);

        Option net = new Option("n", "net", false, "Use all nodes on network");
        net.setRequired(false);
        options.addOption(net);

        Option getSet = new Option("y", "type", true, "get | set | validate");
        getSet.setRequired(true);
        options.addOption(getSet);

        Option debug = new Option("d", "debug", false, "Debug flag for more output");
        options.addOption(debug);

        Option req = new Option("r", "requireAll", false, "Require all nodes (network requests only)");
        options.addOption(req);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmdLine = null;

        try {
            cmdLine = parser.parse(options, xArgs);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("SocketCli", options);
            System.exit(1);
        }
        OptionType opt = OptionType.UNUSED;
        if (cmdLine.hasOption('y')) {
            opt = parseOption(cmdLine.getOptionValue('y'));
        }

        String tgt = null;
        if (cmdLine.hasOption('t')) {
            tgt = cmdLine.getOptionValue('t');
        }

        String tCmd = null;
        if (cmdLine.hasOption('c')) {
            tCmd = cmdLine.getOptionValue('c');
        }

        String tVal = null;
        if (cmdLine.hasOption('v')) {
            tVal = cmdLine.getOptionValue('v');
        }

        if (cmdLine.hasOption('r')) {
            REQUIRE_ALL = true;
        }

        int sockTimeout = -1;
        if (cmdLine.hasOption('x')) {
            sockTimeout = Integer.parseInt(cmdLine.getOptionValue('x'));
        }

        DEBUG = cmdLine.hasOption('d');


        boolean isNetwork = false;
        if (cmdLine.hasOption('n')) {
            isNetwork = true;
        }

        new Cli(tgt, opt, tCmd, tVal, isNetwork, sockTimeout);
    }

    private static OptionType parseOption(String xOpt) {
        String opt = xOpt.toLowerCase();
        switch (opt) {
            case "get":
                return OptionType.GET;
            case "set":
                return OptionType.SET;
            case "validate":
                return OptionType.VALIDATE;
            default:
                return OptionType.UNUSED;
        }
    }

    private void errorCheck(WrJsonResult xResult) {
        if (xResult == null) {
            mSocket.close();
            System.err.println("Error check got a null result");
            System.exit(-1);
        }

        if (xResult.hasError()) {
            System.out.println("Error: " + xResult.getError());
            mSocket.close();
            System.exit(-1);
        }
    }

    private void localValidate() {
        WrJsonResult r = null;
        try {
            r = mSocket.validate(mCmd, mValue);
            if (DEBUG) {
                System.out.println(r);
            }
            errorCheck(r);
            System.out.println(mCmd + ": " + r.getValue());
        } catch (WebSocketTimeout xWebSocketTimeout) {
            xWebSocketTimeout.printStackTrace();
        }
    }

    private void localSet() {
        WrJsonResult r = null;
        try {
            r = mSocket.set(mCmd, mValue);
            if (DEBUG) {
                System.out.println(r);
            }
            errorCheck(r);
            System.out.println(mCmd + ": " + r.getValue());
        } catch (WebSocketTimeout xWebSocketTimeout) {
            xWebSocketTimeout.printStackTrace();
        }
    }

    private void localGet() {
        WrJsonResult r = null;
        try {
            r = mSocket.get(mCmd);
            if (DEBUG) {
                System.out.println(r);
            }
            errorCheck(r);
            System.out.println(mCmd + ": " + r.getValue());
        } catch (WebSocketTimeout xWebSocketTimeout) {
            xWebSocketTimeout.printStackTrace();
        }
    }

    private void networkValidate() {
        WrJsonNetworkResults r = null;
        if (REQUIRE_ALL) {
            mSocket.setRequireAll(REQUIRE_ALL);
        }
        try {
            r = mSocket.networkValidate(mCmd, mValue);
        } catch (WebSocketTimeout xWebSocketTimeout) {
            xWebSocketTimeout.printStackTrace();
            System.exit(-1);
        }
        if (DEBUG) {
            System.out.println(r);
        }
        errorCheck(r);
        List<WrJsonNetworkResult> list = r.getResults();
        if (list == null) {
            System.out.println("No results!");
            return;
        }
        for (WrJsonNetworkResult each : list) {
            System.out.println(each.getIp() + ": " + mCmd + ": " + each.getValue());
        }
    }

    private void networkSet() {
        WrJsonNetworkResults r = null;
        if (REQUIRE_ALL) {
            mSocket.setRequireAll(REQUIRE_ALL);
        }
        try {
            r = mSocket.networkSet(mCmd, mValue);
        } catch (WebSocketTimeout xWebSocketTimeout) {
            xWebSocketTimeout.printStackTrace();
            System.exit(-1);
        }
        if (DEBUG) {
            System.out.println(r);
        }
        errorCheck(r);
        List<WrJsonNetworkResult> list = r.getResults();
        if (list == null) {
            System.out.println("No results!");
            return;
        }
        for (WrJsonNetworkResult each : list) {
            System.out.println(each.getIp() + ": " + mCmd + ": " + each.getValue());
        }
    }

    private void networkGet() {
        WrJsonNetworkResults r = null;
        if (REQUIRE_ALL) {
            mSocket.setRequireAll(REQUIRE_ALL);
        }
        try {
            r = mSocket.networkGet(mCmd);
        } catch (WebSocketTimeout xWebSocketTimeout) {
            xWebSocketTimeout.printStackTrace();
            System.exit(-1);
        }
        if (DEBUG) {
            System.out.println(r);
        }
        errorCheck(r);
        List<WrJsonNetworkResult> list = r.getResults();
        if (list == null) {
            System.out.println("No results!");
            return;
        }
        for (WrJsonNetworkResult each : list) {
            System.out.println(each.getIp() + ": " + mCmd + ": " + each.getValue());
        }
    }

    private enum OptionType {
        GET,
        SET,
        VALIDATE,
        UNUSED
    }
}
