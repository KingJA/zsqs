package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/6/18 0018 下午 2:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Area {

    /**
     * region_id : 330303
     * name : 龙湾区
     * background_color : #000000
     * font_color : #000000
     * icon_url : tv_region_icon/20200618/cf004cde4a11daf7a0900350881a9bcd8d571bc1.png
     */

    private int region_id;
    private String name;
    private String icon_url;

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
