package com.persistentsystems.socketclient.commands;

/**
 * This is a basic enum containing all currently available commands to issue to the websocket.
 */
public enum Command {
    /**
     * Uused for getting a variable from a single node.
     */
    GET("get"),

    /**
     * Used to set a variable to a single node.
     */
    SET("set"),

    /**
     * Used to validate a variable to a single node.
     */
    VALIDATE("validate"),

    /**
     * Used to get a variable from all available nodes on the network.
     */
    NETWORK_GET("network_get"),

    /**
     * Used to set a variable to all available nodes on the network.
     */
    NETWORK_SET("network_set"),

    /**
     * Used to validate a variable to all availablde nodes on the network.
     */
    NETWORK_VALIDATE("network_validate"),

    /**
     * Issues a suspend command, see PDF documentation.
     */
    SUSPEND("suspend");

    private final String mString;

    Command(final String xString) {
        mString = xString;
    }

    /**
     * Overrides the to string method, this is used to get the proper text representation
     * for the websocket.
     */
    @Override
    public String toString() {
        return mString;
    }
}
