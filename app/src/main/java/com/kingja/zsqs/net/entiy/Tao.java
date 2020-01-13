package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 下午 5:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Tao {


    /**
     * Id : 1090
     * HouseId : e08f8072-d87b-41a1-9e73-19d52eb80406
     * PatternId : 39
     * Quantity : 1
     * PatternName : 80户型
     * Area : 80.0
     * Remark :
     */

    private int Id;
    private String HouseId;
    private int PatternId;
    private int Quantity;
    private String PatternName;
    private double Area;
    private String Remark;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getHouseId() {
        return null == HouseId ? "" : HouseId;
    }

    public void setHouseId(String houseId) {
        HouseId = houseId;
    }

    public int getPatternId() {
        return PatternId;
    }

    public void setPatternId(int patternId) {
        PatternId = patternId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getPatternName() {
        return null == PatternName ? "" : PatternName;
    }

    public void setPatternName(String patternName) {
        PatternName = patternName;
    }

    public double getArea() {
        return Area;
    }

    public void setArea(double area) {
        Area = area;
    }

    public String getRemark() {
        return null == Remark ? "" : Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
