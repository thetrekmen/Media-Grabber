package com.persistentsystems.socketclient.containers;

/**
 * This class holds a variable and value into a `std::pair`/`tuple` like object that can be
 * used by the api classes.
 */
public class WrVariablePair {
    /**
     * Variable contained in this class.
     * This is public so it can be easily referenced.
     */
    public final String variable;
    /**
     * Value contained in this class.
     * This is public so it can be easily referenced.
     */
    public final String value;

    /**
     * Public constructor.
     *
     * @param xVariable variable to set
     * @param xValue    value to set
     */
    public WrVariablePair(final String xVariable, final String xValue) {
        variable = xVariable;
        value = xValue;
    }

    /**
     * This is an overridden equals method, using the `this.variable` as the comparator.
     *
     * @param xObj object to compare to
     * @return true/false if equal
     */
    @Override
    public boolean equals(Object xObj) {
        if (xObj instanceof WrVariablePair) {
            return ((WrVariablePair) xObj).variable.equals(this.variable);
        }
        return false;
    }

    /**
     * This is overridden for use by internal api classes.
     *
     * @return hashcode based off mVariable
     */
    @Override
    public int hashCode() {
        return variable.hashCode();
    }

    /**
     * Returns a simple string representation of this object
     * of format `variable: value`.
     *
     * @return `variable: value`
     */
    @Override
    public String toString() {
        return variable + ": " + value;
    }
}
