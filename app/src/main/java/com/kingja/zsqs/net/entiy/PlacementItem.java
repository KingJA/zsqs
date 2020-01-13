package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/1/10 0010 下午 4:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlacementItem {

    /**
     * progress_house_plan_id : 28
     * progress_house_plan_name : A-1户型
     * area : 159.05
     * house_plan_url : progress_house_plan_image/20191127/b9141b02ccb94035a2e4fbeb3fefa4e442e87ab6.jpg
     */

    private int progress_house_plan_id;
    private String progress_house_plan_name;
    private String area;
    private String house_plan_url;

    public int getProgress_house_plan_id() {
        return progress_house_plan_id;
    }

    public void setProgress_house_plan_id(int progress_house_plan_id) {
        this.progress_house_plan_id = progress_house_plan_id;
    }

    public String getProgress_house_plan_name() {
        return null == progress_house_plan_name ? "" : progress_house_plan_name;
    }

    public void setProgress_house_plan_name(String progress_house_plan_name) {
        this.progress_house_plan_name = progress_house_plan_name;
    }

    public String getArea() {
        return null == area ? "" : area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHouse_plan_url() {
        return null == house_plan_url ? "" : house_plan_url;
    }

    public void setHouse_plan_url(String house_plan_url) {
        this.house_plan_url = house_plan_url;
    }
}
