package com.persistentsystems.socketclient.sockets.interfaces;

import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.net.WrIpAddress;

/**
 * Using interfaces to force a consistent API between blocking / async classes
 */
public interface ISockAuthIp<T> {
    WrAuth getAuth();

    T setAuth(WrAuth xAuth);

    WrIpAddress getIpAddress();

    T setIpAddress(WrIpAddress xIpAddress);
}
