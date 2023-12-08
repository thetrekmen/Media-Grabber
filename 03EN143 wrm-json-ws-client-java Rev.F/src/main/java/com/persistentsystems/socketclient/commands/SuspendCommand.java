package com.persistentsystems.socketclient.commands;

/**
 * This class implements the suspend command.
 */
public class SuspendCommand implements ICommand {

    /**
     * Get the variable from the {@link Command} enum.
     *
     * @return return the variable we picked from the command enum.
     */
    @Override
    public Command get() {
        return Command.SUSPEND;
    }

    /**
     * Returns null / associated variable with this command.
     *
     * @return null or string variable associated with this command, most often this will null.
     */
    @Override
    public String getVariable() {
        return null;
    }
}
