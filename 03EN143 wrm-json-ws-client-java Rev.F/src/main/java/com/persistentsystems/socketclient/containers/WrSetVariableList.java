package com.persistentsystems.socketclient.containers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implements a list of settable variable pairs for use by the {@link com.persistentsystems.socketclient.sockets.WrAsyncSocket}
 * or {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket}.
 * Internally, this class is backed by a hashset so there will be no duplicate variables.
 */
public class WrSetVariableList implements Iterable<WrVariablePair>, IVariableList {
    private final Set<WrVariablePair> mList;

    /**
     * Public constructor.
     * Initializes internal hashset to empty.
     */
    public WrSetVariableList() {
        mList = new HashSet<>();
    }

    /**
     * Public constructor.
     *
     * @param xPairs adds values contained to internal hashset.
     */
    public WrSetVariableList(WrVariablePair[] xPairs) {
        mList = new HashSet<>();
        for (WrVariablePair pair : xPairs) {
            mList.add(pair);
        }
    }

    /**
     * Returns the size of the internal hashset.
     *
     * @return size of internal hashset.
     */
    @Override
    public int size() {
        return mList.size();
    }

    /**
     * Returns true/false if the internal hashset is empty.
     *
     * @return true if internal hashset is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    /**
     * Adds a given xVar/xVal to internal hashset.
     *
     * @param xVar variable to add
     * @param xVal value to add
     * @return `this` so command can be chained.
     */
    public WrSetVariableList add(String xVar, String xVal) {
        mList.add(new WrVariablePair(xVar, xVal));
        return this;
    }

    /**
     * Iterator used by foreach style methods.
     *
     * @return iterator to internal hashset.
     */
    @Override
    public Iterator<WrVariablePair> iterator() {
        return mList.iterator();
    }
}
