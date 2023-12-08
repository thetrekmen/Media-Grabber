package com.persistentsystems.socketclient.sockets.interfaces;

import com.persistentsystems.socketclient.commands.ICommand;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import com.persistentsystems.socketclient.except.WebSocketTimeout;
import com.persistentsystems.socketclient.results.WrJsonNetworkResults;
import com.persistentsystems.socketclient.results.WrJsonResult;

/**
 * This interface is applied to the internal {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket}.
 * <p>
 * The intent of this interface is to support composition over inheritance when building our own internal
 * socket classes. There's no forseeable reason for a customer to need to extend this class
 */
public interface IBlockingGetterSetter extends IRequireAll {

    /**
     * gets a value from the api
     *
     * @param xVariable string representation of the variable
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult get(String xVariable) throws WebSocketTimeout;

    /**
     * gets a list of values from the api
     *
     * @param xList a list of string values supported by the api
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult get(WrGetVariableList xList) throws WebSocketTimeout;

    /**
     * sets a single variable with value of xValue to the api
     *
     * @param xVariable variable name to set
     * @param xValue    variable value to set
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult set(String xVariable, String xValue) throws WebSocketTimeout;

    /**
     * sets a single WrVariablePair into the api
     *
     * @param xPair a single WrVariablePair (which contains the variable / value) to be set to the api
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult set(WrVariablePair xPair) throws WebSocketTimeout;

    /**
     * sets a list of {@link WrVariablePair} to the api
     *
     * @param xList a list of WrVariablePairs, which is called a WrSetVariableList
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult set(WrSetVariableList xList) throws WebSocketTimeout;

    /**
     * sends a command to the api.
     * Right now, there is only one command available {@link com.persistentsystems.socketclient.commands.SuspendCommand}.
     * Others may be added in the future.
     *
     * @param xCommand A class implementing the {@link ICommand} interface
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult command(ICommand xCommand) throws WebSocketTimeout;

    /**
     * Validates (IE confirms that the variable/value pair are acceptable to this radio) a given
     * variable with value.
     *
     * @param xVariable variable name ot validate
     * @param xValue    value to validate
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult validate(String xVariable, String xValue) throws WebSocketTimeout;

    /**
     * validates the given WrVariablePair is acceptable to this radio
     *
     * @param xPair a WrVariablePair containing the variable/value to validate
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult validate(WrVariablePair xPair) throws WebSocketTimeout;

    /**
     * Validates a list of WrVariablePair, ensuring they are acceptable to the API
     *
     * @param xList a WrSetVariableList of pairs to validate
     * @return returns a WrJsonResult when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonResult validate(WrSetVariableList xList) throws WebSocketTimeout;

    /**
     * Issues a network get to the api
     * A network get means that all available radios (in the `Managed Node List`)
     * will return the requested variable value.
     *
     * @param xCmd the variable to get from all nodes
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkGet(String xCmd) throws WebSocketTimeout;

    /**
     * issues a network get to the api
     *
     * @param xList a list of variables to get from each radio
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkGet(WrGetVariableList xList) throws WebSocketTimeout;

    /**
     * issues a network set to nodes on the network via the API
     *
     * @param xVariable variable to set on all (reachable) nodes on the network
     * @param xValue    value to set into the variable
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkSet(String xVariable, String xValue) throws WebSocketTimeout;

    /**
     * issues a network set to available nodes on the network
     *
     * @param xPair the variable/value pair to set to nodes on the network
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkSet(WrVariablePair xPair) throws WebSocketTimeout;

    /**
     * issues a network set to available nodes on the network
     *
     * @param xList a list of WrVariablePairs
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkSet(WrSetVariableList xList) throws WebSocketTimeout;

    /**
     * issues a network validate (ensures the radio can accept the given variable/value) to available
     * nodes on the network
     *
     * @param xVariable the variable to validate
     * @param xValue    the value to validate
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkValidate(String xVariable, String xValue) throws WebSocketTimeout;

    /**
     * issues a network validate (ensures the radio can accept the given variable/value) to available
     * nodes on the network
     *
     * @param xPair the variable / value pair to attempt to validate
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkValidate(WrVariablePair xPair) throws WebSocketTimeout;

    /**
     * issues a network validate (ensures the radio can accept the given variable/value) to available
     * nodes on the network
     *
     * @param xList the list of WrVariablePair to attempt to validate
     * @return returns a WrJsonNetworkResults when the call is completed
     * @throws WebSocketTimeout throws if the websocket timeout
     *                          {@link com.persistentsystems.socketclient.sockets.WrBlockingSocket#DEFAULT_SOCKET_TIMEOUT} is hit.
     */
    WrJsonNetworkResults networkValidate(WrSetVariableList xList) throws WebSocketTimeout;

}
