package com.tony.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;

/**
 * The class is used to try how to generate a AES key and how to encrypt and decrypt with a key and different mode
 * 
 * @author lei.du
 *
 */
public class AesEncryption {

    public static void main(String[] args) throws InvalidKeyException,
    NoSuchAlgorithmException, NoSuchPaddingException, 
    InvalidParameterSpecException, IllegalBlockSizeException, 
    BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        byte[] aes128Key = AesBinaryKeyGenerator(128);
        String plainText = "Hello World";
        String cipherText = encryptWithAesCbc(plainText, aes128Key);
        System.out.println("cipher text of " + plainText + " is: " + cipherText);

    }

    public static String encryptWithAesCbc(String plaintext, byte[] key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidParameterSpecException, 
            IllegalBlockSizeException, BadPaddingException, 
            InvalidAlgorithmParameterException, UnsupportedEncodingException {
        byte[] cipherText;
        final SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //generate IV-
        IvParameterSpec spec = 
           cipher.getParameters().getParameterSpec(IvParameterSpec.class);
        byte[] iv = spec.getIV();
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));

        return DatatypeConverter.printHexBinary(cipherText);
    }

    public static String dencryptWithAesCbc() {
        return null;
    }

    public static String encryptWithAesGcm() {
        return null;
    }

    public static String decryptWithAesGcm() {
        return null;
    }

    public static byte[] AesBinaryKeyGenerator(int keySize) {
        final String ENCRYPTION_ALGORITHM_AES="AES";
        KeyGenerator keyGen;
        String hexKey = null;
        byte[] binaryKey = null;

        try {
            keyGen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM_AES);
            keyGen.init(keySize); 

            SecretKey secretKey = keyGen.generateKey();
            binaryKey = secretKey.getEncoded();
            System.out.println("The length of binaryKey is : " + binaryKey.length + " bytes");

            hexKey = DatatypeConverter.printHexBinary(binaryKey);
            System.out.println("AesKey in HEX is : " + hexKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //in case if you return hexKey, DatatypeConverter.parseHexBinary() can be used to convert from hex to binary array
        return binaryKey;
        
    }
}
