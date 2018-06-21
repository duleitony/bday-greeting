package com.tony.iso8583;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Iso8583Util {
    public static void main(String[] args){
        byte[] bcd = str2Bcd("6225887902610332");
        System.out.println(bcd.length);
        System.out.println(Hex.encodeHex(bcd));
        System.out.println(bcd2Str(bcd));
    }

    /**
     * 将str字符串转换成bcd编码的字节数组，比如字符串"123456"转成byte[] {0x12, 0x34, 0x56}
     * @param asc
     * @return
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        len /= 2;

        byte[] bbt = new byte[len];
        byte[] abt = asc.getBytes(Charset.forName("utf-8"));

        for (int p = 0; p < len; ++p) {
            int j; //字节高4位
            int k; //字节低4位

            if ((abt[(2 * p)] >= 97) && (abt[(2 * p)] <= 122)) {
                //字符a-z
                j = abt[(2 * p)] - 97 + 10;
            }
            else if ((abt[(2 * p)] >= 65) && (abt[(2 * p)] <= 90)) {
                //字符A-Z
                j = abt[(2 * p)] - 65 + 10;
            }
            else {
                //数字0-9
                j = abt[(2 * p)] - 48;
            }

            if ((abt[(2 * p + 1)] >= 97) && (abt[(2 * p + 1)] <= 122)) {
                k = abt[(2 * p + 1)] - 97 + 10;
            }
            else if ((abt[(2 * p + 1)] >= 65) && (abt[(2 * p + 1)] <= 90)) {
                k = abt[(2 * p + 1)] - 65 + 10;
            }
            else {
                k = abt[(2 * p + 1)] - 48;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }


    /**
     * 将bcd编码的字节数组转换成str字符串，比如byte[] {0x12, 0x34, 0x56}转成字符串"123456"
     * @param bytes
     * @return
     */
    public static String bcd2Str(byte[] bytes) {
        //str字符串的长度为原来的2倍
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; ++i) {
            //得到高4位二进制转换后的十进制值
            byte left = (byte) ((bytes[i] & 0xF0) >>> 4);
            //得到低4位二进制转换后的十进制值
            byte right = (byte) (bytes[i] & 0x0F);
            //根据ASCII码表转成str表示的数字
            temp.append(String.format("%c",
                    new Object[]{Integer.valueOf(left + 48)}));
            temp.append(String.format("%c",
                    new Object[]{Integer.valueOf(right + 48)}));
        }
        return temp.toString();
    }

    /**
     * Convert a ASCII to string
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String Ascii2Str(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "utf-8");
    }

    /**
     * Convert a string to ASCII
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] str2Ascaii(String str) throws UnsupportedEncodingException {
        return str.getBytes("utf-8");
    }
}
