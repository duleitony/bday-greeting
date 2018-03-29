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
import javax.crypto.ShortBufferException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * The class is used to try how to generate a AES key and how to encrypt and decrypt with a key and different mode
 * 
 * @author lei.du
 *
 */
public class AesEncryption {
    final static String TRANSFORMATION_AESCBC = "AES/CBC/PKCS5Padding";
    final static String TRANSFORMATION_AESGCM = "AES/GCM/NoPadding";

    public static void main(String[] args)
            throws InvalidKeyException,
    NoSuchAlgorithmException, NoSuchPaddingException, 
    InvalidParameterSpecException, IllegalBlockSizeException, 
    BadPaddingException, InvalidAlgorithmParameterException, 
    UnsupportedEncodingException, ShortBufferException 
    {
        String plainText = "Hello World!";
        //By default, the key size is supported by Java8 is 128
        byte[] aes128SecretKey = AesBinaryKeyGenerator(128);
        IvParameterSpec ivParameterSpec = generateIv();
        //String cipherText = encryptWithAesCbc(plainText, aes128SecretKey, ivParameterSpec);
        //String decryptedPlainText = dencryptWithAesCbc(cipherText, aes128SecretKey, ivParameterSpec);

        GCMParameterSpec myParams = new GCMParameterSpec(128, ivParameterSpec.getIV());
        String cipherText = encryptWithAesGcm(plainText, aes128SecretKey, myParams);
        System.out.println("The cipher text of " + plainText + " is: " + cipherText);

        String decryptedPlainText = decryptWithAesGcm(cipherText, aes128SecretKey, myParams);
        System.out.println("The plain text of " + cipherText + " is: " + decryptedPlainText);
    }

    /**
     * Encrypt function with AES CBC mode
     * 
     * @param plaintext
     * @param key
     * @param ivParameterSpec2 
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidParameterSpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException
     */
    public static String encryptWithAesCbc(String plaintext, byte[] key, IvParameterSpec ivParameterSpec)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidParameterSpecException, 
            IllegalBlockSizeException, BadPaddingException, 
            InvalidAlgorithmParameterException, UnsupportedEncodingException 
    {
        byte[] cipherText;
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_AESCBC);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));

        return DatatypeConverter.printHexBinary(cipherText);
    }

    /**
     * Decrypt function with AES CBC mode
     * 
     * @param cipherText
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException 
     * @throws InvalidKeyException 
     * @throws UnsupportedEncodingException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    public static String dencryptWithAesCbc(String cipherText, byte[] key, IvParameterSpec ivParameterSpec) 
            throws NoSuchAlgorithmException, NoSuchPaddingException, 
            InvalidKeyException, InvalidAlgorithmParameterException, 
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException 
    {
        byte[] plainText;
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_AESCBC);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        plainText = cipher.doFinal(DatatypeConverter.parseHexBinary(cipherText));

        return new String(plainText);
    }

    /**
     * 
     * 
     * @param plainText
     * @param key
     * @param myParams2
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encryptWithAesGcm(String plainText, byte[] key, GCMParameterSpec myParams) 
            throws NoSuchAlgorithmException, NoSuchPaddingException, 
            InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException 
    {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_AESGCM);
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, myParams);
        byte[] binaryCipherText = new byte[cipher.getOutputSize(plainText.getBytes().length)];
        //cipher.updateAAD(plainText.getBytes());
        cipher.doFinal(plainText.getBytes(), 0, plainText.getBytes().length, binaryCipherText);

        return  DatatypeConverter.printHexBinary(binaryCipherText);
    }

    /**
     * 
     * 
     * @param cipherText
     * @param key
     * @param myParams2
     * @return
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decryptWithAesGcm(String cipherText, byte[] key, GCMParameterSpec myParams) 
            throws InvalidKeyException, InvalidAlgorithmParameterException, 
            NoSuchAlgorithmException, NoSuchPaddingException, 
            IllegalBlockSizeException, BadPaddingException 
    {
        byte[] plainText;
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_AESGCM);
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, myParams);
        //cipher.updateAAD(plainText.getBytes());
        plainText = cipher.doFinal(DatatypeConverter.parseBase64Binary(cipherText));

        return new String(plainText);
    };

    //================================================================================================================//
    /**
     * The method is used to generate IV
     * 
     * @return
     * @throws InvalidParameterSpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static IvParameterSpec generateIv() 
            throws InvalidParameterSpecException, 
            NoSuchAlgorithmException, NoSuchPaddingException 
    {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_AESCBC);
        IvParameterSpec spec = 
           cipher.getParameters().getParameterSpec(IvParameterSpec.class);
        byte[] iv = spec.getIV();
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    /**
     * AES key generator
     * 
     * @param keySize
     * @return
     */
    public static byte[] AesBinaryKeyGenerator(int keySize) 
    {
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
            System.out.println("The AesKey in HEX is : " + hexKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //in case if you return hexKey, DatatypeConverter.parseHexBinary() can be used to convert from hex to binary array
        return binaryKey;
        
    }
}
