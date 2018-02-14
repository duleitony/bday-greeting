package com.tony.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * The class is used to try how to generate a AES key and how to encrypt and decrypt with a key and different mode
 * 
 * @author lei.du
 *
 */
public class AesEncryption {

    public static void main(String[] args) {
        AesKeyGenerator(128);
        AesKeyGenerator(192);
        AesKeyGenerator(256);

        String plantext = "Hello World";
    }

    public static String encryptWithAesCbc(String plaintext, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidParameterSpecException {
        //SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        final SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        IvParameterSpec spec = 
           cipher.getParameters().getParameterSpec(IvParameterSpec.class);
        byte[] iv = spec.getIV();
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        return null;
    }

    public static String decryptWithAesGcm() {
        return null;
    }

    public static String encryptWithAesGcm() {
        return null;
    }

    public static String dencryptWithAesCbc() {
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
