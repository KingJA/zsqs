package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/1/17 0017 上午 8:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HouseItem {

    /**
     * HouseId : e08f8072-d87b-41a1-9e73-19d52eb80406
     * CusCode : 21113
     * RealName : 小王
     * MobilePhone : 18257736223
     * Idcard : 330302196012075612
     * Address : 温州某路
     * StatusId : 5
     * StatusName : 协议生成
     * CertArea : 70
     * LegalArea : 169
     */

    private String HouseId;
    private String CusCode;
    private String RealName;
    private String MobilePhone;
    private String Idcard;
    private String Address;
    private int StatusId;
    private int BuildingType;
    private String StatusName;
    private double CertArea;
    private double LegalArea;

    public int getBuildingType() {
        return BuildingType;
    }

    public void setBuildingType(int buildingType) {
        BuildingType = buildingType;
    }

    public String getHouseId() {
        return null == HouseId ? "" : HouseId;
    }

    public void setHouseId(String houseId) {
        HouseId = houseId;
    }

    public String getCusCode() {
        return null == CusCode ? "" : CusCode;
    }

    public void setCusCode(String cusCode) {
        CusCode = cusCode;
    }

    public String getRealName() {
        return null == RealName ? "" : RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getMobilePhone() {
        return null == MobilePhone ? "" : MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getIdcard() {
        return null == Idcard ? "" : Idcard;
    }

    public void setIdcard(String idcard) {
        Idcard = idcard;
    }

    public String getAddress() {
        return null == Address ? "" : Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getStatusName() {
        return null == StatusName ? "" : StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public double getCertArea() {
        return CertArea;
    }

    public void setCertArea(double certArea) {
        CertArea = certArea;
    }

    public double getLegalArea() {
        return LegalArea;
    }

    public void setLegalArea(double legalArea) {
        LegalArea = legalArea;
    }
}
