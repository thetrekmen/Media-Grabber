package com.persistentsystems.socketclient.containers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * This is basically a wrapper around a hashset.
 * <i>Using a hashset will eliminate duplicate variables.</i>
 */
public class WrGetVariableList implements Iterable<String>, IVariableList {
    private final Set<String> mList;

    /**
     * Public constructor.
     * Initializes the member list as empty.
     */
    public WrGetVariableList() {
        mList = new HashSet<>();
    }

    /**
     * Public constructor.
     * Add an array of variables into the list.
     *
     * @param xList an array of variables to add.
     */
    public WrGetVariableList(String[] xList) {
        mList = new HashSet<>();
        for (String each : xList) {
            mList.add(each);
        }
    }

    /**
     * Adds a single variable.
     *
     * @param xVar a single variable to add.
     * @return `this` so commands can be chained.
     */
    public WrGetVariableList add(String xVar) {
        mList.add(xVar);
        return this;
    }

    /**
     * Checks if the internal hashset is empty.
     *
     * @return true if empty, false if non-empty
     */
    @Override
    public boolean isEmpty() {
        return mList.size() == 0;
    }

    /**
     * Returns the size of the internal hashset.
     *
     * @return returns size of internal hashset.
     */
    @Override
    public int size() {
        return mList.size();
    }

    /**
     * String representation of the internal hashset.
     *
     * @return returns a comma seperated string of values.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String each : mList) {
            sb.append(each);
            sb.append(", ");
        }
        return sb.toString();
    }

    /**
     * An iterator required for use by foreach methods.
     *
     * @return the iterator from our internal hashset.
     */
    @Override
    public Iterator<String> iterator() {
        return mList.iterator();
    }
}
