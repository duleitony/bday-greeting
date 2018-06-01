package com.tony.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;

/**
 * The class is used to test how HmacSha works.
 *
 * @author dulei
 *
 */
public class HmacSha {

    public static final String UTF_8 = "UTF-8";
    public static final String HAMCSHA256 = "HmacSHA256";
    public static final String HMACSHA384 = "HmacSHA384";
    public static final String HMACSHA512 = "HmacSHA512";

    /**
     * The method is used to generate HMAC with given key and algorithm
     *
     * @param data
     * @param secretKey
     * @param hashType
     * @return
     */
    private static String generateHmac(String data, String secretKey, String hashType) {
        String hash = null;
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(UTF_8), hashType);
            Mac mac = Mac.getInstance(key.getAlgorithm());
            mac.init(key);
            byte[] byteArr = mac.doFinal(data.getBytes(UTF_8));
            System.out.println("Output length " + hashType + " is  : " + byteArr.length * 8);
            hash = Hex.encodeHexString(byteArr);

        } catch (UnsupportedEncodingException e) {
            // when character encoding parameter of String#getBytes is wrong
            throw new IllegalStateException(e);
        } catch (NoSuchAlgorithmException e) {
            // when algorism parameter of SecretKeySpec#SecretKeySpec is wrong
            throw new IllegalStateException(e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
        return hash;
    }

    /**
     * Key generator is used to generate secret key based on given algor
     *
     * @param keyType
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] keyGenerator(String algorithm, int keySize)
            throws NoSuchAlgorithmException {
        KeyGenerator generatorHmacSHA = KeyGenerator.getInstance(algorithm);
        generatorHmacSHA.init(keySize);
        SecretKey keyHmacSHA256 = generatorHmacSHA.generateKey();
        return keyHmacSHA256.getEncoded();
    }

    public static void main(String[] args)
            throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        String str = "Hi there hello world";
        System.out.println("Length of str is : " + str.getBytes(UTF_8).length);
        System.out.println("");

        //HMACSHA256
        byte[] keyHmacSHA256 = keyGenerator(HAMCSHA256, 256);
        String hashStr = generateHmac(str, DatatypeConverter.printHexBinary(keyHmacSHA256), HAMCSHA256);

        System.out.println("Key Size of keyHmacSHA256 is : " + keyHmacSHA256.length * 8);
        System.out.println("Key of keyHmacSHA256 is      : " + DatatypeConverter.printHexBinary(keyHmacSHA256));
        System.out.println("Hash of keyHmacSHA256 is     : " + hashStr);
        System.out.println("");

        //HMACSHA384
        byte[] keyHmacSHA384 = keyGenerator(HMACSHA384, 256);
        String hashStrHmacSHA384 = generateHmac(str, DatatypeConverter.printHexBinary(keyHmacSHA384), HMACSHA384);

        System.out.println("Key Size of keyHmacSHA384 is : " + keyHmacSHA384.length * 8);
        System.out.println("Key of keyHmacSHA384 is      : " + DatatypeConverter.printHexBinary(keyHmacSHA384));
        System.out.println("Hash of keyHmacSHA384 is     : " + hashStrHmacSHA384);
        System.out.println("");

        //HMACSHA512
        byte[] keyHmacSHA512 = keyGenerator(HMACSHA512, 256);
        String hashStrHmacSHA512 = generateHmac(str, DatatypeConverter.printHexBinary(keyHmacSHA512), HMACSHA512);

        System.out.println("Key Size of keyHmacSHA512 is : " + keyHmacSHA512.length * 8);
        System.out.println("Key of keyHmacSHA512 is      : " + DatatypeConverter.printHexBinary(keyHmacSHA512));
        System.out.println("Hash of keyHmacSHA512 is     : " + hashStrHmacSHA512);
    }

}
