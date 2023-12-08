package com.persistentsystems.socketclient.auth;

/**
 * This class holds the username / password for authenticating into the websocket.
 */
public class WrAuth {
    private String mPassword = "";
    private String mUserName = "factory";

    /**
     * Empty public constructor.
     */
    public WrAuth() {

    }

    /**
     * Gets the currently set username.
     *
     * @return current username
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * Sets the username for the socket.
     * <p>
     * <b>Note: Currently `factory` is the only supported username.</b>
     * </p>
     *
     * @param xUserName username to set
     * @return returns `this` so commands can be chained
     */
    public WrAuth setUserName(String xUserName) {
        mUserName = xUserName;
        return this;
    }

    /**
     * Gets the currently set password.
     *
     * @return current password
     */
    // TODO: convert the local storage into char[] so we pass ptr instead of copies
    public String getPassword() {
        return mPassword;
    }

    /**
     * Sets the password for the socket.
     * <p>
     * <b>Note: the password used to login to the WMI is the same password used for websocket authentication.</b>
     * </p>
     * <p>
     * <i>This may be changed in the future.</i>
     * </p>
     * A set to this password does not change the password for the WMI/websocket, it is merely authentication.
     *
     * @param xPassword password to set
     * @return returns `this` so commands can be chained
     */
    public WrAuth setPassword(String xPassword) {
        mPassword = xPassword;
        return this;
    }

}
