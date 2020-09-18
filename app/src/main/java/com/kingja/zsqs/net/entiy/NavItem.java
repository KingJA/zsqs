package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2019/12/16 0016 上午 11:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class NavItem {
    private int redId;
    private String navText;

    public NavItem(int redId, String navText) {
        this.redId = redId;
        this.navText = navText;
    }

    public int getRedId() {
        return redId;
    }

    public void setRedId(int redId) {
        this.redId = redId;
    }

    public String getNavText() {
        return null == navText ? "" : navText;
    }

    public void setNavText(String navText) {
        this.navText = navText;
    }
}
