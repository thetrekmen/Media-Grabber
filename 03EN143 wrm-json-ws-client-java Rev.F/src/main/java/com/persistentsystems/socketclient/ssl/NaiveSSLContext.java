package com.persistentsystems.socketclient.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * This class is used internally to fix issues with `wss://` in the
 * websocket implementation.
 * https://github.com/TakahikoKawasaki/nv-websocket-client/issues/154#issuecomment-390599446
 */
public class NaiveSSLContext {
    private NaiveSSLContext() {
    }

    /**
     * Get an SSLContext that implements the specified secure
     * socket protocol and naively accepts all certificates
     * without verification.
     */
    public static SSLContext getInstance(String protocol) throws NoSuchAlgorithmException {
        return init(SSLContext.getInstance(protocol));
    }

    /**
     * Set NaiveTrustManager to the given context.
     */
    private static SSLContext init(SSLContext context) {
        try {
// Set NaiveTrustManager.
            context.init(null, new TrustManager[]{new NaiveTrustManager()}, null);
        } catch (KeyManagementException e) {
            throw new RuntimeException("Failed to initialize an SSLContext.", e);
        }

        return context;
    }

    /**
     * A {@link TrustManager} which trusts all certificates naively.
     */
    private static class NaiveTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    }
}