package com.tony.iso8583.solution.gooddesign;

/**
 * The Class for data element of ISO8583
 */
public class Element {
    private int elementNo;//域序号
    private String elementName;//域名称
    private int leng;//域长度(定长)
    private ElementDataType elementDataType;//域数据类型
    private ElementLenType lenType;//域长度类型
    private boolean isDynamicLen;//是否是变长
    private String data;//数据
    private int dynamicLen;//变长长度
    private int dynamicLen_2_3;//(变长长度是2/3字节)


    public Element() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Element(int elementNo, String elementName, int leng, ElementDataType elementDataType, ElementLenType lenType,
                   boolean isDynamicLen, int dynamicLen_2_3) {
        super();
        this.elementNo = elementNo;
        this.elementName = elementName;
        this.leng = leng;
        this.elementDataType = elementDataType;
        this.lenType = lenType;
        this.isDynamicLen = isDynamicLen;
        this.dynamicLen_2_3 = dynamicLen_2_3;
    }

    public int getElementNo() {
        return elementNo;
    }

    public void setElementNo(int elementNo) {
        this.elementNo = elementNo;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public int getLeng() {
        return leng;
    }

    public void setLeng(int leng) {
        this.leng = leng;
    }

    public ElementDataType getElementDataType() {
        return elementDataType;
    }

    public void setElementDataType(ElementDataType elementDataType) {
        this.elementDataType = elementDataType;
    }

    public ElementLenType getLenType() {
        return lenType;
    }

    public void setLenType(ElementLenType lenType) {
        this.lenType = lenType;
    }

    public boolean isDynamicLen() {
        return isDynamicLen;
    }

    public void setDynamicLen(boolean dynamicLen) {
        isDynamicLen = dynamicLen;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDynamicLen() {
        return dynamicLen;
    }

    public void setDynamicLen(int dynamicLen) {
        this.dynamicLen = dynamicLen;
    }

    public int getDynamicLen_2_3() {
        return dynamicLen_2_3;
    }

    public void setDynamicLen_2_3(int dynamicLen_2_3) {
        this.dynamicLen_2_3 = dynamicLen_2_3;
    }

    @Override
    public String toString() {
        return "Element [elementNo=" + elementNo + ", elementName=" + elementName + ", leng=" + leng + ", elementDataType=" + elementDataType
                + ", lenType=" + lenType + ", data=" + data + "]";
    }

}