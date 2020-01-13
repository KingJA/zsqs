package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 下午 4:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class KV {

    /**
     * Key : 牲口房
     * Value : 66.00m²
     * Type : 0
     * Sort : 0
     */

    private String Key;
    private String Value;
    private int Type;
    private int Sort;

    public String getKey() {
        return null == Key ? "" : Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return null == Value ? "" : Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }
}
