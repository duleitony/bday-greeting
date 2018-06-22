package com.tony.iso8583;

import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Iso8583Util {
    public static void main(String[] args){
        byte[] bcd = str2Bcd("6225887902610332");
        System.out.println(bcd.length);
        System.out.println(Hex.encodeHex(bcd));
        System.out.println(bcd2Str(bcd));
    }

    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * BASE64 编码
     *
     * @param buff
     * @return
     */
    public static String encodeBufferBase64(byte[] buff) {
        return buff == null ? null : encoder.encode(buff);
    }

    /**
     * BASE64解码
     *
     * @param s
     * @return
     */
    public static byte[] decodeBufferBase64(String s) {
        try {
            return s == null ? null : decoder.decodeBuffer(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BASE64 字节数组编码
     *
     * @param s
     * @return String
     */
    public static String encodeBase64(byte[] s) {
        if (s == null)
            return null;
        String res = new BASE64Encoder().encode(s);
        res = res.replace("\n", "");
        res = res.replace("\r", "");
        return res;
    }

    /**
     * BASE64解码
     *
     * @param buff
     * @return
     */
    public static byte[] decodeBase64(byte[] buff) {
        if (buff == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(new String(buff));

            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将一个byte位图转成字符串
     *
     * @param b
     * @return
     */
    public static String getStrFromBitMap(byte[] b) {
        String strsum = "";
        for (int i = 0; i < b.length; i++) {
            strsum += getEigthBitsStringFromByte(b[i]);
        }
        return strsum;
    }

    public static String getEigthBitsStringFromByte(int b) {
        b |= 256; // mark the 9th digit as 1 to make sure the string
        String str = Integer.toBinaryString(b);
        int len = str.length();
        return str.substring(len - 8, len);
    }

    public static byte getByteFromEigthBitsString(String str) {
        byte b;
        if (str.substring(0, 1).equals("1")) {
            str = "0" + str.substring(1);
            b = Byte.valueOf(str, 2);
            b |= 128;
        } else {
            b = Byte.valueOf(str, 2);
        }
        return b;
    }

    /**
     * 将一个16字节数组转成128二进制数组
     *
     * @param b
     * @return
     */
    public static boolean[] getBinaryFromByte(byte[] b) {
        boolean[] binary = new boolean[b.length * 8 + 1];
        String strsum = "";
        for (int i = 0; i < b.length; i++) {
            strsum += getEigthBitsStringFromByte(b[i]);
        }
        for (int i = 0; i < strsum.length(); i++) {
            if (strsum.substring(i, i + 1).equalsIgnoreCase("1")) {
                binary[i + 1] = true;
            } else {
                binary[i + 1] = false;
            }
        }
        return binary;
    }

    /**
     * 将一个128二进制数组转成16字节数组
     *
     * @param binary
     * @return
     */
    public static byte[] getByteFromBinary(boolean[] binary) {

        int num = (binary.length - 1) / 8;
        if ((binary.length - 1) % 8 != 0) {
            num = num + 1;
        }
        byte[] b = new byte[num];
        String s = "";
        for (int i = 1; i < binary.length; i++) {
            if (binary[i]) {
                s += "1";
            } else {
                s += "0";
            }
        }
        String tmpstr;
        int j = 0;
        for (int i = 0; i < s.length(); i = i + 8) {
            tmpstr = s.substring(i, i + 8);
            b[j] = getByteFromEigthBitsString(tmpstr);
            j = j + 1;
        }
        return b;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    /**
     * 十六进制字符串转换成bytes
     *
     * @param src
     * @return
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    /**
     * 将String转成BCD码
     *
     * @param s
     * @return
     */
    public static byte[] StrToBCDBytes(String s) {

        if (s.length() % 2 != 0) {
            s = "0" + s;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i += 2) {
            int high = cs[i] - 48;
            int low = cs[i + 1] - 48;
            baos.write(high << 4 | low);
        }
        return baos.toByteArray();
    }

    /**
     * 将BCD码转成int
     *
     * @param b
     * @return
     */
    public static int bcdToint(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            int h = ((b[i] & 0xff) >> 4) + 48;
            sb.append((char) h);
            int l = (b[i] & 0x0f) + 48;
            sb.append((char) l);
        }
        return Integer.parseInt(sb.toString());
    }

    // 将byte数组bRefArr转为一个整数,字节数组的低位是整型的低字节位
    public static int byteToInt(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;

        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * i);
        }
        return iOutcome;
    }

    public static String bitmapHex2Binary(String bitmap){
        String bitMapStr =  bitmap;
        if (bitMapStr == null || bitMapStr.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < bitMapStr.length(); i++)
        {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(bitMapStr
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static String getBitMapStr(String str8583){
        return str8583.substring(30, 46);
    }

    public static String byte2hex(byte[] b) // 二进制转字符串
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1){
                hs = hs + "0" + stmp;
            }
            else{
                hs = hs + stmp;
            }
        }
        return hs;
    }

    public static byte[] hex2byte(String str) { // 字符串转二进制
        if (str == null){
            return null;
        }
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1){
            return null;
        }
        byte[] b = new byte[len / 2];
        try {for (int i = 0; i < str.length(); i += 2) {
            b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
        }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]).append(",");
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();
    }

    public static String byteToASCString(byte[] bArray){
        String strASC="";
        String strTemp="";
        for(int i=0;i<bArray.length;i++){
            strTemp=String.format("%c",bArray[i]);
            strASC=strASC+strTemp;
        }
        return strASC;
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
