package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/1/15 0015 上午 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeConfig {

    /**
     * title : 中国房屋拆迁网房屋征收信息公开系统
     * logo : tv_logo_image/20200107/174e3fa8b51396e3c798cd024ec97d6d87424316.png
     * server_title : 服务热线
     * server_icon : server_icon_image/20200107/0de9c4f12f5a2986bee02f265cf9ff2912627121.png
     * server_tel : 400-867-8008
     * wechat_qrcode_title : 扫码关注公众号
     * wechat_qrcode_url : wechat_qrcode_image/20200107/fb7d3e7f8f77931ce6684bbe0ae33ebbf05404ac.png
     * app_qrcode_title : 扫码下载APP
     * app_qrcode_url : app_qrcode_image/20200107/dae953e1158cc08d18d7096005d23e59a33541df.png
     */

    private String title;
    private String logo;
    private String server_title;
    private String server_icon;
    private String server_tel;
    private String wechat_qrcode_title;
    private String wechat_qrcode_url;
    private String app_qrcode_title;
    private String app_qrcode_url;
    private int id_card_enterable;

    public int getId_card_enterable() {
        return id_card_enterable;
    }

    public void setId_card_enterable(int id_card_enterable) {
        this.id_card_enterable = id_card_enterable;
    }

    public String getTitle() {
        return null == title ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return null == logo ? "" : logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getServer_title() {
        return null == server_title ? "" : server_title;
    }

    public void setServer_title(String server_title) {
        this.server_title = server_title;
    }

    public String getServer_icon() {
        return null == server_icon ? "" : server_icon;
    }

    public void setServer_icon(String server_icon) {
        this.server_icon = server_icon;
    }

    public String getServer_tel() {
        return null == server_tel ? "" : server_tel;
    }

    public void setServer_tel(String server_tel) {
        this.server_tel = server_tel;
    }

    public String getWechat_qrcode_title() {
        return null == wechat_qrcode_title ? "" : wechat_qrcode_title;
    }

    public void setWechat_qrcode_title(String wechat_qrcode_title) {
        this.wechat_qrcode_title = wechat_qrcode_title;
    }

    public String getWechat_qrcode_url() {
        return null == wechat_qrcode_url ? "" : wechat_qrcode_url;
    }

    public void setWechat_qrcode_url(String wechat_qrcode_url) {
        this.wechat_qrcode_url = wechat_qrcode_url;
    }

    public String getApp_qrcode_title() {
        return null == app_qrcode_title ? "" : app_qrcode_title;
    }

    public void setApp_qrcode_title(String app_qrcode_title) {
        this.app_qrcode_title = app_qrcode_title;
    }

    public String getApp_qrcode_url() {
        return null == app_qrcode_url ? "" : app_qrcode_url;
    }

    public void setApp_qrcode_url(String app_qrcode_url) {
        this.app_qrcode_url = app_qrcode_url;
    }
}
