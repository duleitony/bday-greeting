package com.tony.encryption;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;

public class AesEncryption {

    public static void main(String[] args) {
        AesKeyGenerator(128);
        AesKeyGenerator(192);
        AesKeyGenerator(256);
    }

    public static String encryptWithAes() {
        return null;
    }

    public static String dencryptWithAes() {
        return null;
    }

    public static String AesKeyGenerator(int keySize) {
        final String ENCRYPTION_ALGORITHM_AES="AES";
        KeyGenerator keyGen;
        String hexKey = null;
        try {
            keyGen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM_AES);
            keyGen.init(keySize); 

            SecretKey secretKey = keyGen.generateKey();
            byte[] binaryKey = secretKey.getEncoded();
            System.out.println("The length of binaryKey is : " + binaryKey.length + " bytes");

            hexKey = Hex.encodeHexString(binaryKey);
            System.out.println("AesKey is : " + hexKey);
            System.out.println("The length of AesKey is : " + hexKey.length());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexKey;
        
    }
}
