package com.persistentsystems.examples.testsuspend;

import com.neovisionaries.ws.client.WebSocketException;
import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.commands.SuspendCommand;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.net.WrIpAddress;
import com.persistentsystems.socketclient.results.WrJsonResult;
import com.persistentsystems.socketclient.sockets.WrBlockingSocket;
import org.apache.commons.cli.*;

import java.io.IOException;

public class TestClientSuspend {
    public TestClientSuspend(String xIp) throws IOException, WebSocketException, WebSocketTimeout {
        WrBlockingSocket sock = new WrBlockingSocket();
        WrBlockingSocket.DEBUG = true;
        WrIpAddress ip = new WrIpAddress();
        WrAuth auth = new WrAuth();

        ip.set(xIp);
        ip.setPort(443);

        auth.setPassword("password");
        auth.setUserName("factory");

        sock.setAuth(auth);
        sock.setIpAddress(ip);

        sock.connectBlocking();
        System.out.println("Connected");

        WrJsonResult r = sock.command(new SuspendCommand());

        System.out.println(String.format("Got response: %s", r.toString()));

        sock.close();

    }

    public static void main(String[] xArgs) throws IOException, WebSocketException, WebSocketTimeout {
        Options options = new Options();

        Option target = new Option("ip", "ip", true, "The MPU5 to connect via");
        target.setRequired(true);
        options.addOption(target);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmdLine = null;

        try {
            cmdLine = parser.parse(options, xArgs);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("TestSuspend", options);
            System.exit(1);
        }

        new TestClientSuspend(cmdLine.getOptionValue("ip"));
    }

}
