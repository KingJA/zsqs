package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/4/23 0023 上午 11:41
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class UpdateResult {

    /**
     * status : 1
     * type : 0
     * download_url : https://www.baidu.com/
     */

    private int status;
    private int type;
    private String download_url;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
