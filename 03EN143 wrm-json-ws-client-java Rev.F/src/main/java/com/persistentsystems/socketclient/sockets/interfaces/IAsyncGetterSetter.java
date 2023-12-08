package com.persistentsystems.socketclient.sockets.interfaces;

import com.persistentsystems.socketclient.commands.ICommand;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;

public interface IAsyncGetterSetter extends IRequireAll {
    // gets
    void get(String xVariable);

    void get(WrGetVariableList xList);

    // commands
    void command(ICommand xCommand);

    // sets
    void set(String xVariable, String xValue);

    void set(WrVariablePair xPair);

    void set(WrSetVariableList xList);

    // validate
    void validate(String xVariable, String xValue);

    void validate(WrVariablePair xPair);

    void validate(WrSetVariableList xList);

    // network get
    void networkGet(String xCmd);

    void networkGet(WrGetVariableList xList);

    // network set
    void networkSet(String xCmd, String xValue);

    void networkSet(WrVariablePair xPair);

    void networkSet(WrSetVariableList xPair);

    // network validate
    void networkValidate(String xCmd, String xValue);

    void networkValidate(WrVariablePair xPair);

    void networkValidate(WrSetVariableList xPair);

    void comand(ICommand xCommand);
}
