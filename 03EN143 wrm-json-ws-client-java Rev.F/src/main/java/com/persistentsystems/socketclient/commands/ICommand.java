package com.persistentsystems.socketclient.commands;

/**
 * This is an interface that <b>must</b> be implemented when creating new commands.
 */
public interface ICommand {
    /**
     * Used when building the JSON object to send down the websocket.
     *
     * @return must return a member of {@link Command}
     */
    Command get();

    /**
     * This creates the variable, if required, associated with the {@link Command} we're about to issue to the websocket.
     * <p>
     * As of right now, there are no {@link Command} that require an additional variable and this should return `null`.
     * </p>
     * <p>
     * This member fn is only used by the websocket as it's building a message.
     * </p>
     *
     * @return null or string of associated variable
     */
    String getVariable();
}
