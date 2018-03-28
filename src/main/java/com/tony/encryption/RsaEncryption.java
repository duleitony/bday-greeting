package com.tony.encryption;

import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * The class is used to try how to generate a RSA key pair and how to encrypt and decrypt with a key and different mode
 * 
 * @author lei.du
 *
 */
public class RsaEncryption {

    public static void main(String[] args) {
        RsaKeyPairGenerator(512);

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

    public static String RsaKeyPairGenerator(int keySize) {
        final String ENCRYPTION_ALGORITHM_RSA="RSA";

        KeyPairGenerator keyPairGen;
        String hexPrivateKey = null;
        String hexPublicKey = null;

        try {
            keyPairGen = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM_RSA);
            keyPairGen.initialize(keySize); 

            byte[] binaryPrivateKey = keyPairGen.genKeyPair().getPrivate().getEncoded();
            byte[] binaryPublicKey = keyPairGen.genKeyPair().getPublic().getEncoded();
            System.out.println("The length of binaryPrivateKey is : " + binaryPrivateKey.length + " bytes");
            System.out.println("The length of binaryPublicKey is : " + binaryPublicKey.length + " bytes");

            hexPrivateKey = Hex.encodeHexString(binaryPrivateKey);
            hexPublicKey = Hex.encodeHexString(binaryPublicKey);

            System.out.println("hexPrivateKey in HEX is : " + hexPrivateKey);
            System.out.println("hexPublicKey in HEX is : " + hexPublicKey);

            System.out.println("The length of hexPrivateKey is : " + hexPrivateKey.length());
            System.out.println("The length of hexPublicKey is : " + hexPublicKey.length());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexPublicKey;
        
    }
}
