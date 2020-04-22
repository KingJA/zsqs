package com.kingja.zsqs.net.entiy;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2020/3/13 0013 下午 1:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StringWrap implements Serializable {
    private String keyword;

    public StringWrap() {
        this("");
    }

    public StringWrap(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return null == keyword ? "" : keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
