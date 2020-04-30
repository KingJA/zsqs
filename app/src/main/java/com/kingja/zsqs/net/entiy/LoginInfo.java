package com.kingja.zsqs.net.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/1/16 0016 下午 2:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginInfo {

    /**
     * PersonId : ad8e548c-ead3-4868-b3a1-c9ef2d147d00
     * RealName : 小王
     * Idcard : 330302196012075612
     * MobilePhone : 18257736223
     */

    private String PersonId;
    private String RealName;
    private String Idcard;
    private String MobilePhone;

    private List<HouseItem> HouseList;

    public List<HouseItem> getHouseList() {
        return HouseList;
    }

    public void setHouseList(List<HouseItem> houseList) {
        HouseList = houseList;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void setPersonId(String PersonId) {
        this.PersonId = PersonId;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getIdcard() {
        return Idcard;
    }

    public void setIdcard(String Idcard) {
        this.Idcard = Idcard;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String MobilePhone) {
        this.MobilePhone = MobilePhone;
    }
}
