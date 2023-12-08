package com.persistentsystems.socketclient.messaging;

import com.persistentsystems.socketclient.auth.WrAuth;
import com.persistentsystems.socketclient.commands.Command;
import com.persistentsystems.socketclient.containers.WrGetVariableList;
import com.persistentsystems.socketclient.containers.WrSetVariableList;
import com.persistentsystems.socketclient.containers.WrVariablePair;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WrJsonMessageBuilderTest {

    WrJsonMessageBuilder mb;
    WrAuth auth;

    @Before
    public void before() {
        auth = new WrAuth().setPassword("password").setUserName("factory");
        mb = new WrJsonMessageBuilder();
    }

    @Test
    public void buildAuth() {
        mb.setAuth(auth);
        String built = mb.build();
        JSONObject root = new JSONObject(built);
        for (String each : root.keySet()) {
            if (each.equals("protocol_version")) {
                assertEquals(root.get(each), "1.4.0");
            } else if (each.equals("username")) {
                assertEquals(root.get(each), auth.getUserName());
            } else if (each.equals("password")) {
                assertEquals(root.get(each), auth.getPassword());
            } else if (each.equals("msgtype")) {
                assertEquals(root.get(each), "req");
            } else if (each.equals("token")) {
                assertNotEquals(root.get(each), "");
            } else {
                fail("Found extra key");
            }
        }
    }

    @Test
    public void fullSetMessage() {
        mb.setAuth(auth);
        mb.setCommand(Command.SET);
        mb.setVariable("waverelay_name", "MessageBuilderTest");
        String built = mb.build();
        JSONObject root = new JSONObject(built);
        for (String each : root.keySet()) {
            if (each.equals("protocol_version")) {
                assertEquals(root.get(each), "1.4.0");
            } else if (each.equals("username")) {
                assertEquals(root.get(each), auth.getUserName());
            } else if (each.equals("password")) {
                assertEquals(root.get(each), auth.getPassword());
            } else if (each.equals("msgtype")) {
                assertEquals(root.get(each), "req");
            } else if (each.equals("command")) {
                assertEquals(root.get(each), "set");
            } else if (each.equals("token")) {
                assertNotEquals(root.get("token"), "");
            } else if (each.equals("variables")) {
                JSONObject vars = root.getJSONObject("variables");
                for (String v : vars.keySet()) {
                    if (v.equals("waverelay_name")) {
                        JSONObject var = vars.getJSONObject("waverelay_name");
                        assertEquals(var.getString("value"), "MessageBuilderTest");
                    } else {
                        fail();
                    }
                }
            } else {
                fail();
            }
        }
    }

    @Test
    public void fullGetMessage() {
        mb.setAuth(auth);
        mb.setCommand(Command.GET);
        mb.setVariable("waverelay_ip");
        String built = mb.build();
        JSONObject root = new JSONObject(built);
        for (String each : root.keySet()) {
            if (each.equals("protocol_version")) {
                assertEquals(root.get(each), "1.4.0");
            } else if (each.equals("username")) {
                assertEquals(root.get(each), auth.getUserName());
            } else if (each.equals("password")) {
                assertEquals(root.get(each), auth.getPassword());
            } else if (each.equals("msgtype")) {
                assertEquals(root.get(each), "req");
            } else if (each.equals("command")) {
                assertEquals(root.get(each), "get");
            } else if (each.equals("token")) {
                assertNotEquals(root.get("token"), "");
            } else if (each.equals("variables")) {
                JSONObject vars = root.getJSONObject("variables");
                for (String v : vars.keySet()) {
                    if (v.equals("waverelay_ip")) {
                        assertEquals(vars.getJSONObject("waverelay_ip").toString(), "{}");
                    } else {
                        fail();
                    }
                }
            } else {
                fail();
            }
        }
    }

    @Test
    public void testToString() {
        mb.setAuth(auth);
        mb.setCommand(Command.GET);
        mb.setVariable("waverelay_ip");
        assertEquals(mb.toString(), mb.build());
    }

    @Test
    public void canUseListType() {
        mb.setAuth(auth);
        mb.setCommand(Command.GET);
        WrGetVariableList list = new WrGetVariableList();
        list.add("waverelay_ip");
        mb.setVariable(list);
        JSONObject root = new JSONObject(mb.build());
        for (String each : root.keySet()) {
            if (each.equals("variables")) {
                JSONObject vars = root.getJSONObject("variables");
                for (String v : vars.keySet()) {
                    if (v.equals("waverelay_ip")) {
                        // should be empty object
                        JSONObject t = new JSONObject();
                        t.put("waverelay_ip", new JSONObject());
                        assertEquals(root.get(each).toString(), t.toString());
                    } else {
                        fail();
                    }
                }
            }
        }
    }

    @Test
    public void tokenUniquePerInstance() {
        mb.setAuth(auth);
        mb.setCommand(Command.GET);
        mb.setVariable("waverelay_ip");
        String built = mb.build();
        String tok = new JSONObject(built).getString("token");
        assertEquals(mb.getToken(), tok);
    }

    @Test
    public void setList() {
        mb.setAuth(auth);
        mb.setCommand(Command.SET);
        WrSetVariableList list = new WrSetVariableList();
        list.add("waverelay_name", "TEST_NAME");
        list.add("waverelay_ip", "1.1.2.2");
        mb.setVariable(list);
        JSONObject root = new JSONObject(mb.build());
        JSONObject vars = root.getJSONObject("variables");
        for (String v : vars.keySet()) {
            if (v.equals("waverelay_name")) {
                assertEquals(vars.getJSONObject("waverelay_name").getString("value"), "TEST_NAME");
            } else if (v.equals("waverelay_ip")) {
                assertEquals(vars.getJSONObject("waverelay_ip").getString("value"), "1.1.2.2");
            } else {
                fail();
            }
        }
    }

    @Test
    public void setPair() {
        mb.setAuth(auth);
        mb.setCommand(Command.SET);
        mb.setVariable(new WrVariablePair("waverelay_ip", "10.3.1.254"));
        JSONObject root = new JSONObject(mb.build());
        for (String each : root.keySet()) {
            if (each.equals("protocol_version")) {
                assertEquals(root.get(each), "1.4.0");
            } else if (each.equals("username")) {
                assertEquals(root.get(each), auth.getUserName());
            } else if (each.equals("password")) {
                assertEquals(root.get(each), auth.getPassword());
            } else if (each.equals("msgtype")) {
                assertEquals(root.get(each), "req");
            } else if (each.equals("command")) {
                assertEquals("set", root.get(each));
            } else if (each.equals("token")) {
                assertNotEquals(root.get("token"), "");
            } else if (each.equals("variables")) {
                JSONObject vars = root.getJSONObject("variables");
                for (String v : vars.keySet()) {
                    if (v.equals("waverelay_ip")) {
                        JSONObject var = vars.getJSONObject("waverelay_ip");
                        assertTrue(var.toString().equals(new JSONObject("{\"value\":\"10.3.1.254\"}").toString()));
                    } else {
                        fail();
                    }
                }
            } else {
                fail();
            }
        }
    }

    @Test
    public void setArray() {
        mb.setAuth(auth);
        mb.setCommand(Command.GET);
        mb.setVariable("waverelay_ip");
        mb.setVariable("waverelay_name");
        JSONObject root = new JSONObject(mb.build());
        for (String each : root.keySet()) {
            if (each.equals("protocol_version")) {
                assertEquals(root.get(each), "1.4.0");
            } else if (each.equals("username")) {
                assertEquals(root.get(each), auth.getUserName());
            } else if (each.equals("password")) {
                assertEquals(root.get(each), auth.getPassword());
            } else if (each.equals("msgtype")) {
                assertEquals(root.get(each), "req");
            } else if (each.equals("command")) {
                assertEquals(root.get(each), "get");
            } else if (each.equals("token")) {
                assertNotEquals(root.get("token"), "");
            } else if (each.equals("variables")) {
                JSONObject vars = root.getJSONObject("variables");
                for (String v : vars.keySet()) {
                    if (v.equals("waverelay_name")) {
                        JSONObject var = vars.getJSONObject("waverelay_name");
                        assertEquals(var.toString(), new JSONObject().toString());
                    } else if (v.equals("waverelay_ip")) {
                        JSONObject var = vars.getJSONObject("waverelay_ip");
                        assertEquals(var.toString(), new JSONObject().toString());
                    } else {
                        fail();
                    }
                }
            } else {
                fail();
            }
        }
    }
}
