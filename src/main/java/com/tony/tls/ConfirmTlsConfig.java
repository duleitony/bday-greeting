package com.tony.tls;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ConfirmTlsConfig {
    //-Djavax.net.debug=ssl
    public static void getSystemInfo() {
        System.out.println("================Jave Information ===============");
        System.out.println(System.getProperty("java.home"));
        System.out.println(System.getProperty("java.home") + "\\jre\\lib\\security\\cacerts");
        System.out.println(System.getProperty("java.vendor"));
        System.out.println(System.getProperty("java.version"));
        System.out.println("===============================================");
    }

    public static void getCertificateinCacerts(String alias, String pathOfKeyStore)
            throws KeyStoreException,
            NoSuchAlgorithmException,
            CertificateException,
            IOException {
        char[] password = "changeit".toCharArray();

        FileInputStream fIn = new FileInputStream(pathOfKeyStore);
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(fIn, password);
        Certificate cert = keystore.getCertificate(alias);
        System.out.println("===============================================");
        if(cert != null) {
            System.out.println(cert);
        } else {
            System.out.println("There is no certificate with alias " + alias);
        }
        System.out.println("===============================================");
    }

    public static void getSSLInfo(Boolean isUseDefault, String protocol)
            throws NoSuchAlgorithmException, IOException, KeyManagementException {
        SSLContext context = null;
        SSLSocketFactory factory = null;
        SSLSocket socket = null;

        if(isUseDefault) {
            context = SSLContext.getDefault();
        } else {
            context = SSLContext.getInstance(protocol);
            context.init(null,null,null);
        }

        factory = (SSLSocketFactory)context.getSocketFactory();
        socket = (SSLSocket)factory.createSocket();

        System.out.println("===============================================");
        //Supported protocols
        String[] protocols = socket.getSupportedProtocols();
        System.out.println("Supported Protocols: " + protocols.length);
        for(int i = 0; i < protocols.length; i++)
        {
            System.out.println(" " + protocols[i]);
        }

        //Enabled protocols
        protocols = socket.getEnabledProtocols();
        System.out.println("Enabled Protocols: " + protocols.length);
        for(int i = 0; i < protocols.length; i++)
        {
            System.out.println(" " + protocols[i]);
        }

        //Enabled cipher suites
        String[] ciphers = socket.getSupportedCipherSuites();
        System.out.println("Enabled Ciphers: " + ciphers.length);
        for(int i = 0; i < ciphers.length; i++)
        {
            System.out.println(" " + ciphers[i]);
        }
        System.out.println("===============================================");
    }

    public static void main(String[] args)
            throws Exception {
        getSystemInfo();

        //getCertificateinCacerts("rpayca", "C:\\cacerts");

        getSSLInfo(true, "TLSv1.2");
    }

}
