package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/4/29 0029 上午 9:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectIdResult {

    /**
     * tv_client_id : 1
     * device_code : a7fab63cabc40064
     * project_id : bf49a831-1cf3-44c0-9739-ea0c5578f94f
     * status : 1
     * project_id_is_change : 1
     */

    private int tv_client_id;
    private String device_code;
    private String project_id;
    private String scene_address;
    private int status;
    private int project_id_is_change;

    public String getScene_address() {
        return null == scene_address ? "" : scene_address;
    }

    public void setScene_address(String scene_address) {
        this.scene_address = scene_address;
    }

    public int getTv_client_id() {
        return tv_client_id;
    }

    public void setTv_client_id(int tv_client_id) {
        this.tv_client_id = tv_client_id;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProject_id_is_change() {
        return project_id_is_change;
    }

    public void setProject_id_is_change(int project_id_is_change) {
        this.project_id_is_change = project_id_is_change;
    }
}
