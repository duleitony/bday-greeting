package com.tony.iso8583.solution.gooddesign;

import java.util.ArrayList;
import java.util.HashMap;

public class Iso8583MessageHandler {
    /**
     * 解析ISO8583报文，一个完整的报文包括6个部分
     * 1. 报文长度：长度两个字节
     * 2. TPDU：长度为10个字节, 压缩时用BCD码表示为5个字节长度的数值。
     * 3. 报文头：总长度为12字节，压缩时用BCD码表示为6个字节长度的数值。
     * 4. 报文类型
     * 5. 位图
     * 6. 报文数据
     *
     * @param str8583 8583报文
     * @param resultMap 存放8583全部解析后的Map
     * @return 填充完成的域数组集合
     *
     * */
    public static ArrayList<Element> analyzeIso8583Message(String str8583, HashMap<String, String> resultMap){

        //报文总长
        int currentIndex = 0;
        String lenStr = str8583.substring(currentIndex, 4);
        resultMap.put("LEN", lenStr);
        currentIndex += lenStr.length();

        //tpdu
        String TPDUStr = str8583.substring(currentIndex, currentIndex+10);
        resultMap.put("TPDU", TPDUStr);
        currentIndex += TPDUStr.length();

        //msghead
        String MSGHEADStr = str8583.substring(currentIndex,currentIndex+12);
        resultMap.put("MSGHEAD", MSGHEADStr);
        currentIndex += MSGHEADStr.length();

        //报文type
        String TYPEStr = str8583.substring(currentIndex, currentIndex+4);
        resultMap.put("TYPE", TYPEStr);
        currentIndex += TYPEStr.length();

        //位图
        String BITMAPStr = str8583.substring(currentIndex,currentIndex+16);
        resultMap.put("BITMAP", BITMAPStr);
        currentIndex += BITMAPStr.length();

        //位图数组
        ArrayList<Integer> bitmaps = getBitMap(BITMAPStr);

        //报文配置
        MessageConfig config = new MessageConfig();
        //根据位图找到报文对应的域
        ArrayList<Element> elements = new ArrayList<Element>();
        for (Integer elementNo : bitmaps) {
            elements.add(config.getElementWithElementNo(elementNo));
        }
        //为每个域填充信息
        for (Element element : elements) {
            String elementDataStr = null;//用来存放该域的数据
            if( element.isDynamicLen()){//如果是变长的话
                if(element.getElementDataType()==ElementDataType.BCD && element.getLenType() == ElementLenType.BCD){
                    String dylenStr = null;
                    int useLen = 0;
                    if (element.getDynamicLen_2_3() == 2) {
                        dylenStr = str8583.substring(currentIndex,currentIndex+2);
                        currentIndex += 2;
                        int dataLen = Integer.parseInt(dylenStr);
                        if (dataLen %2 ==0) {
                            useLen = dataLen;
                        }else{
                            useLen = dataLen + 1;
                        }
                    }else if(element.getDynamicLen_2_3() == 3){
                        dylenStr = str8583.substring(currentIndex,currentIndex+4);
                        currentIndex += 4;
                        int dataLen = Integer.parseInt(dylenStr);
                        if (dataLen %2 ==0) {
                            useLen = dataLen;
                        }else{
                            useLen = dataLen + 1;
                        }
                    }

                    elementDataStr = str8583.substring(currentIndex, currentIndex+useLen);
                    currentIndex += elementDataStr.length();
                    resultMap.put(String.format("%d", element.getElementNo()), elementDataStr);
                    element.setLeng(Integer.parseInt(dylenStr));

                }else if(element.getElementDataType()==ElementDataType.ASCII && element.getLenType() == ElementLenType.BCD){
                    String dylenStr = str8583.substring(currentIndex,currentIndex+4);
                    currentIndex += 4;
                    elementDataStr = str8583.substring(currentIndex, currentIndex+Integer.parseInt(dylenStr)*2);
                    currentIndex +=elementDataStr.length();
                    resultMap.put(String.format("%d", element.getElementNo()), elementDataStr);

                    element.setLeng(Integer.parseInt(dylenStr));

                }else if(element.getElementDataType()==ElementDataType.BINARY && element.getLenType() == ElementLenType.BCD){
                    String dylenStr = str8583.substring(currentIndex,currentIndex+4);
                    currentIndex += 4;
                    elementDataStr = str8583.substring(currentIndex, currentIndex+Integer.parseInt(dylenStr)*2);
                    currentIndex +=elementDataStr.length();
                    resultMap.put(String.format("%d", element.getElementNo()), elementDataStr);
                    element.setLeng(Integer.parseInt(dylenStr));
                }
            }else{//如果是固定长度
                if(element.getElementDataType()==ElementDataType.BCD && element.getLenType() == ElementLenType.BCD){
                    elementDataStr = str8583.substring(currentIndex,currentIndex+element.getLeng());
                    resultMap.put(String.format("%d", element.getElementNo()) , elementDataStr);
                    currentIndex += elementDataStr.length() %2 ==0 ?elementDataStr.length():elementDataStr.length()+1 ;
                }else if(element.getElementDataType()==ElementDataType.ASCII && element.getLenType() == ElementLenType.BCD){
                    elementDataStr = str8583.substring(currentIndex,currentIndex+element.getLeng() * 2);
                    resultMap.put(String.format("%d", element.getElementNo()) , elementDataStr);
                    currentIndex += elementDataStr.length();
                }
            }
            //存放到该域
            element.setData(elementDataStr);
        }
        return elements;
    }

    /**
     * 组装ISO8583报文
     *
     * @param requesttMap
     * @return
     */
    public static String generateIso8583Message(HashMap<String, String> requesttMap){
        //TODO
        return null;
    }

    /**
     * 获得报文的位图数组
     *
     * */
    public static ArrayList<Integer> getBitMap(String bitMapStr){
        ArrayList<Integer> bitMaps = new ArrayList<Integer>();
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
        for(int i = 0 ; i < bString.length() ; i ++){
            char c = bString.charAt(i);
            int flag = Integer.parseInt(String.format("%c", c));
            if (flag==1) {
                bitMaps.add(i+1);
            }
        }
        System.out.println("BitMap is ：" + bitMaps);
        return bitMaps;
    }
}