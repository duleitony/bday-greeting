package com.tony.iso8583.solution.gooddesign;

import com.tony.iso8583.solution.gooddesign.Element;
import com.tony.iso8583.solution.gooddesign.ElementDataType;
import com.tony.iso8583.solution.gooddesign.ElementLenType;

import java.util.ArrayList;

/**
 * 8583协议是基于ISO8583报文国际标准的包格式的通讯协议，主要是用来解决金融系统之间的报文交换的，
 * 说白了就是信息数据的传递。一般使用的是64域报文，最长到128域。
 *
 */
public class MessageConfig {

    public Element element0 = new Element(0, "msgtype", 4, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element1 = new Element(1, "拓展位图", 8, ElementDataType.BINARY, ElementLenType.BCD, false,0);
    public  Element element2 = new Element(2, "主账号", 19, ElementDataType.BCD, ElementLenType.BCD, true,2);
    public  Element element3 = new Element(3, "交易处理码", 6, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element4 = new Element(4, "交易金额", 12, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element11 = new Element(11, "系统跟踪号", 6, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element12 = new Element(12, "受卡发方所在地时间", 6, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element13 = new Element(13, "受卡发方所在地日期", 4, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element14 = new Element(14, "卡有效期", 4, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element15 = new Element(15, "清算日期", 4, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element22 = new Element(22, "服务点输入方式码", 3, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element23 = new Element(23, "卡序列号", 3, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element25 = new Element(25, "服务点条件码", 2, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element26 = new Element(26, "服务点PIN获取码", 2, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element32 = new Element(32, "受理机构标识码", 11, ElementDataType.BCD, ElementLenType.BCD, true,2);
    public  Element element35 = new Element(35, "第二磁道数据", 37, ElementDataType.BCD, ElementLenType.BCD, true,2);
    public  Element element36 = new Element(36, "第三磁道数据", 104, ElementDataType.BCD, ElementLenType.BCD, true,3);
    public  Element element37 = new Element(37, "检索参考号", 12, ElementDataType.ASCII, ElementLenType.BCD, false,0);
    public  Element element38 = new Element(38, "授权标识应答码", 6, ElementDataType.ASCII, ElementLenType.BCD, false,0);
    public  Element element39 = new Element(39, "应答码", 2, ElementDataType.ASCII, ElementLenType.BCD, false,0);
    public  Element element41 = new Element(41, "受卡机终端标识码", 8, ElementDataType.ASCII, ElementLenType.BCD, false,0);
    public  Element element42 = new Element(42, "受卡方标识码", 15, ElementDataType.ASCII, ElementLenType.BCD, false,0);
    public  Element element43 = new Element(43, "商户名称", 40, ElementDataType.ASCII, ElementLenType.ASCII, false,0);
    public  Element element44 = new Element(44, "附加响应数据", 25, ElementDataType.ASCII, ElementLenType.BCD, true,2);
    public  Element element48 = new Element(48, "附加数据-私有", 512, ElementDataType.BCD, ElementLenType.BCD, true,3);
    public  Element element49 = new Element(49, "交易货币代码", 3, ElementDataType.ASCII, ElementLenType.BCD, false,0);
    public  Element element52 = new Element(52, "个人标识码数据", 16, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element53 = new Element(53, "安全控制信息", 16, ElementDataType.BCD, ElementLenType.BCD, false,0);
    public  Element element54 = new Element(54, "实际金额", 32, ElementDataType.ASCII, ElementLenType.BCD, true,3);
    public  Element element55 = new Element(55, "基于PBOC借贷记标准的IC卡数据", 255, ElementDataType.BINARY, ElementLenType.BCD, true,3);
    public  Element element57 = new Element(57, "pos版本", 42, ElementDataType.ASCII, ElementLenType.BCD, true,3);
    public  Element element58 = new Element(58, "基于PBOC电子钱包/存折IC卡标准的交易数据", 100, ElementDataType.BINARY, ElementLenType.BCD, true,3);
    public  Element element60 = new Element(60, "自定义域", 100, ElementDataType.BCD, ElementLenType.BCD, true,3);
    public  Element element61 = new Element(61, "原始信息域", 200, ElementDataType.BCD, ElementLenType.BCD, true,3);
    public  Element element62 = new Element(62, "原始信息域", 200, ElementDataType.BINARY, ElementLenType.BCD, true,3);
    public  Element element63 = new Element(63, "金融网络数据", 200, ElementDataType.ASCII, ElementLenType.BCD, true,3);
    public  Element element64 = new Element(64, "MAC", 8, ElementDataType.ASCII, ElementLenType.BCD, false,0);


    public ArrayList<Element> elements = new ArrayList<Element>(){/**
     *
     */
    private static final long serialVersionUID = 4868905912449475521L;
        {
            add(element0);
            add(element1);
            add(element2);
            add(element3);
            add(element4);
            add(element11);
            add(element12);
            add(element13);
            add(element14);
            add(element15);
            add(element22);
            add(element23);
            add(element25);
            add(element26);
            add(element32);
            add(element35);
            add(element36);
            add(element37);
            add(element38);
            add(element39);
            add(element41);
            add(element42);
            add(element43);
            add(element44);
            add(element48);
            add(element49);
            add(element52);
            add(element53);
            add(element54);
            add(element55);
            add(element57);
            add(element58);
            add(element60);
            add(element61);
            add(element62);
            add(element63);
            add(element64);
        }};


    public Element getElementWithElementNo(int no){
        Element result = null;
        for (Element element : elements) {
            if(element.getElementNo() == no){
                result = element;
                break;
            }
        }
        return result;
    }



}