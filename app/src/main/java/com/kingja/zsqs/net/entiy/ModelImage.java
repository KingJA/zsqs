package com.kingja.zsqs.net.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 下午 4:51
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ModelImage {
    private String Key;
    private List<FileItem> Value;

    public String getKey() {
        return null == Key ? "" : Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public List<FileItem> getValue() {
        return Value;
    }

    public void setValue(List<FileItem> value) {
        Value = value;
    }
}
