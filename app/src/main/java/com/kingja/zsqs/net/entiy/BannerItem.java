package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/1/14 0014 下午 4:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BannerItem {

    /**
     * slide_id : 1
     * type : 1
     * img_url : tv_slide_image/20200113/c2e76102fddd6a416e8341b646a5c106460f1329.png
     * link_url : http://m.chinafwcq.com
     */

    private int slide_id;
    private int type;
    private String img_url;
    private String link_url;

    public int getSlide_id() {
        return slide_id;
    }

    public void setSlide_id(int slide_id) {
        this.slide_id = slide_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
}
