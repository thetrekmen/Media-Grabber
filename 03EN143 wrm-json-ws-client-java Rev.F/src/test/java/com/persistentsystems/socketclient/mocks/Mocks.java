package com.persistentsystems.socketclient.mocks;

import com.persistentsystems.socketclient.messaging.WrMessageToken;

public class Mocks {
    public static final String TEST_NAME_OK = "{\"protocol_version\":\"1.4.0\",\"password\":\"password\",\"variables\":{\"waverelay_name\":{\"value\":\"testName\"}},\"msgtype\":\"req\",\"command\":\"get\",\"username\":\"factory\",\"token\":\"acbf1f716bd17b8cd942746c5e6342be\"}";
    public static final String TEST_NAME_IP_OK = "{\"protocol_version\":\"1.4.0\",\"password\":\"password\",\"variables\":{\"waverelay_name\":{\"value\":\"testName\"},\"waverelay_ip\":{\"value\":\"10.3.1.254\"}},\"msgtype\":\"req\",\"command\":\"get\",\"username\":\"factory\",\"token\":\"acbf1f716bd17b8cd942746c5e6342be\"}";
    public static final String NOT_OK = "{\"protocol_version\":\"1.4.0\",\"password\":\"password\",\"msgtype\":\"req\",\"command\":\"get\",\"username\":\"factory\",\"token\":\"acbf1f716bd17b8cd942746c5e6342be\"}";
    public static final String EXPECTED_WAVERELAY_NAME = "testName";
    public static final String EXPECTED_WAVERELAY_IP = "10.3.1.254";
    public static final String ERROR_MESSAGE = "{\"protocol_version\":\"1.4.0\",\"error\":{\"display\":\"command not found\"},\"msgtype\":\"rep\",\"unit_id\":{\"management_ip\":\"172.26.1.151\",\"name\":\"testName\",\"wr_serial\":\"18585\"},\"token\":\"765a978d-19d9\"}";
    public static final String ERROR_DISPLAY_STRING = "command not found";
    // NOTE JSON responses are alphabetical
    public static final String IPERF_NOT_FINAL_RX = "{\"protocol_version\":\"1.4.0\",\"throughput_test\":{\"remote_addr\":\"172.26.1.152:46802\",\"finalAvg\":false,\"local_addr\":\"172.26.1.151:5005\",\"interval\":\"4.0-5.0\",\"time\":\"Thu Jan 01 00:31:14 1970\",\"bytesSent\":5388847,\"bw_rx\":42.63},\"msgtype\":\"rep\",\"unit_id\":{\"management_ip\":\"172.26.1.151\",\"name\":\"TEST_NAME_0a\",\"wr_serial\":\"18585\"},\"token\":\"" + WrMessageToken.getIperfToken() + "\"}";
    public static final String IPERF_FINAL_RX = "{\"protocol_version\":\"1.4.0\",\"throughput_test\":{\"remote_addr\":\"172.26.1.152:46802\",\"finalAvg\":true,\"local_addr\":\"172.26.1.151:5005\",\"interval\":\"0.0-5.0\",\"time\":\"Thu Jan 01 00:31:14 1970\",\"bytesSent\":5388847,\"bw_rx\":42.64},\"msgtype\":\"rep\",\"unit_id\":{\"management_ip\":\"172.26.1.151\",\"name\":\"TEST_NAME_0a\",\"wr_serial\":\"18585\"},\"token\":\"ea2cb6d712ee90991599c233bcc6fb03\"}";
    public static final String IPERF_NOT_FINAL_TX = "{\"protocol_version\":\"1.4.0\",\"throughput_test\":{\"remote_addr\":\"172.26.1.152:46802\",\"bw_tx\":42.51,\"finalAvg\":false,\"local_addr\":\"172.26.1.151:5005\",\"interval\":\"4.0-5.0\",\"time\":\"Thu Jan 01 00:31:14 1970\",\"bytesSent\":5388847},\"msgtype\":\"rep\",\"unit_id\":{\"management_ip\":\"172.26.1.151\",\"name\":\"TEST_NAME_0a\",\"wr_serial\":\"18585\"},\"token\":\"ea2cb6d712ee90991599c233bcc6fb03\"}";
    public static final String IPERF_FINAL_TX = "{\"protocol_version\":\"1.4.0\",\"throughput_test\":{\"remote_addr\":\"172.26.1.152:46802\",\"bw_tx\":42.51,\"finalAvg\":true,\"local_addr\":\"172.26.1.151:5005\",\"interval\":\"0.0-5.0\",\"time\":\"Thu Jan 01 00:31:14 1970\",\"bytesSent\":5388847},\"msgtype\":\"rep\",\"unit_id\":{\"management_ip\":\"172.26.1.151\",\"name\":\"TEST_NAME_0a\",\"wr_serial\":\"18585\"},\"token\":\"ea2cb6d712ee90991599c233bcc6fb03\"}";
    public static final String IPERF_INVALID_JSON = "{\"protocol_version\":\"1.4.0\",\"msgtype\":\"rep\",\"unit_id\":{\"management_ip\":\"172.26.1.151\",\"name\":\"TEST_NAME_0a\",\"wr_serial\":\"18585\"},\"token\":\"ea2cb6d712ee90991599c233bcc6fb03\"}";
    public static final String IPERF_FINAL_MESSAGE = "{\n" +
            "\t\"msgtype\": \"rep\",\n" +
            "\t\"unit_id\": {\n" +
            "\t\t\"wr_serial\": \"18585\",\n" +
            "\t\t\"name\": \"TEST_NAME_0a\",\n" +
            "\t\t\"management_ip\": \"172.26.1.151\"\n" +
            "\t},\n" +
            "\t\"token\": \"" + WrMessageToken.getIperfToken() + "\",\n" +
            "\t\"protocol_version\": \"1.4.0\",\n" +
            "\t\"final_status\": \"ok\",\n" +
            "\t\"display\": \"Throughput test complete\"\n" +
            "}";
    public static final String IP_NAME_MESSAGE = "{\"protocol_version\":\"1.4.0\",\"password\":\"password\",\"variables\":{\"waverelay_name\":{},\"waverelay_ip\":{}},\"msgtype\":\"req\",\"command\":\"get\",\"username\":\"factory\",\"token\":\"acbf1f716bd17b8cd942746c5e6342be\"}";
    public static final String IP_NAME_MESSAGE_VALUES = "{\"protocol_version\":\"1.4.0\",\"password\":\"password\",\"variables\":{\"waverelay_name\":{\"value\":\"testName\"},\"waverelay_ip\":{\"value\":\"10.3.1.254\"}},\"msgtype\":\"req\",\"command\":\"get\",\"username\":\"factory\",\"token\":\"acbf1f716bd17b8cd942746c5e6342be\"}";
    public static final String VALIDATE_NAME = "TEST_NAME_OK";
    public static final String VALIDATE_NAME_OK = "{\"msgtype\":\"rep\",\"unit_id\":{\"wr_serial\":\"21230\",\"name\":\"test\",\"management_ip\":\"172.26.1.152\"},\"token\":\"24b583dfa0fce439a25be46c50edb5f1\",\"protocol_version\":\"1.4.0\",\"command\":\"validate\",\"variables\":{\"waverelay_name\":{\"value\":\"" + VALIDATE_NAME + "\"}}}";
    public static final String NETWORK_GET_NAME_OK = "{\"msgtype\":\"rep\",\"command\":\"network_get\",\"unit_id\":{\"wr_serial\":\"32772\",\"name\":\"heller test\",\"management_ip\":\"172.26.1.151\"},\"token\":\"7b9a3ced-f218\",\"protocol_version\":\"1.4.0\",\"final_status\":\"ok\",\"ip_list\":{\"172.26.1.152\":{\"variables\":{\"waverelay_name\":{\"value\":\"" + EXPECTED_WAVERELAY_NAME + "\"}}}}}";
    public static final String NETWORK_SET_NAME_OK = "";
    public static final String NETWORK_VALIDATE_NAME_OK = "";
    public static final String FIRMWARE_START = "{\"msgtype\":\"rep\",\"unit_id\":{\"wr_serial\":\"21230\",\"name\":\"TEST_NAME\",\"management_ip\":\"172.26.1.152\"},\"token\":\"FIRMWARE_TOKEN\",\"protocol_version\":\"1.4.0\",\"status\":\"ready_to_receive\"}";
    public static final String FIRMWARE_ERROR = "{\"msgtype\":\"rep\",\"command\":\"network_firmware_update\",\"unit_id\":{\"wr_serial\":\"21230\",\"name\":\"TEST_NAME\",\"management_ip\":\"172.26.1.152\"},\"token\":\"FIRMWARE_TOKEN\",\"protocol_version\":\"1.4.0\",\"final_status\":\"failed\",\"ip_list\":{\"172.26.1.151\":{\"error\":{\"display\":\"connection closed by remote node\"}}}}";
    public static final String FIRMWARE_FINAL = "{\"msgtype\":\"rep\",\"command\":\"network_firmware_update\",\"unit_id\":{\"wr_serial\":\"16652\",\"name\":\"Desktop\",\"management_ip\":\"172.26.1.1\"},\"token\":\"" + WrMessageToken.getFirmwareToken() + "\",\"protocol_version\":\"1.4.0\",\"final_status\":\"ok\",\"ip_list\":{\"172.26.1.5\":{},\"172.26.1.28\":{},\"172.26.1.1\":{}}}";
    private static final String VALIDATE_IP = "1234.1234";
    public static final String VALIDATE_IP_NOT_OK = "{\"msgtype\":\"rep\",\"unit_id\":{\"wr_serial\":\"21230\",\"name\":\"test\",\"management_ip\":\"172.26.1.152\"},\"token\":\"c5a469ae353906b51caa8e31a7a6eb65\",\"protocol_version\":\"1.4.0\",\"command\":\"set\",\"variables\":{\"waverelay_ip\":{\"error\":{\"display\":\"Invalid value\",\"value\":\"" + VALIDATE_IP + "\"}}},\"error\":{\"display\":\"Error validating variables\"}}";
}
