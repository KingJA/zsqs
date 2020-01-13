package com.kingja.zsqs.net.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 下午 4:51
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ModelTao {
    private String Key;
    private List<Tao> Value;

    public String getKey() {
        return null == Key ? "" : Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public List<Tao> getValue() {
        return Value;
    }

    public void setValue(List<Tao> value) {
        Value = value;
    }
}
