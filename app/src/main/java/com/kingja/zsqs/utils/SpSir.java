package com.kingja.zsqs.utils;

import android.content.SharedPreferences;

import com.kingja.zsqs.base.App;
import com.kingja.zsqs.constant.Constants;

import static android.content.Context.MODE_PRIVATE;


/**
 * Description：TODO
 * Create Time：2016/8/15 13:51
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SpSir {
    public static final String PROJECT_ID = "PROJECT_ID";
    public static final String IDCARD = "IDCARD";
    public static final String MOBILE = "MOBILE";
    public static final String HOUSE_JSON = "HOUSE_JSON";
    public static final String REALNAME = "REALNAME";
    public static final String HOUSE_ID = "HOUSE_ID";
    public static final String DEVICE_CODE = "DEVICE_CODE";
    public static final String ADDRESS = "ADDRESS";
    public static final String BUILDINGTYPE = "BUILDINGTYPE";
    public static final String HOUSE_SELECT_TYPE = "HOUSE_SELECT_TYPE";// 0 无房产,1 1个房产,2 多个房产
    private static SpSir mSpSir;
    private SharedPreferences mSp;

    private SpSir() {
        mSp = App.getContext().getSharedPreferences(Constants.APPLICATION_NAME, MODE_PRIVATE);
    }

    public static SpSir getInstance() {
        if (mSpSir == null) {
            synchronized (SpSir.class) {
                if (mSpSir == null) {
                    mSpSir = new SpSir();
                }
            }
        }
        return mSpSir;
    }

    public void putString(String key, String value) {
        if (value != null) {
            mSp.edit().putString(key, value).apply();
        }
    }

    public String getString(String key, String defaultValue) {
        return mSp.getString(key, defaultValue);
    }

    public void putInt(String key, int value) {
        mSp.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return mSp.getInt(key, defaultValue);
    }

    public int getInt(String key) {
        return mSp.getInt(key, -1);
    }

    public String getString(String key) {
        return mSp.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        mSp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSp.getBoolean(key, defaultValue);
    }


    public String getProjectId() {
        return getString(PROJECT_ID);
    }

    public void setProjectId(String projectId) {
        putString(PROJECT_ID, projectId);
    }

    public String getDeviceCode() {
        return getString(DEVICE_CODE);
    }

    public void setDeviceCode(String deviceCode) {
        putString(DEVICE_CODE, deviceCode);
    }

    public String getIdcard() {
        return getString(IDCARD);
    }

    public void setIdcard(String idcard) {
        putString(IDCARD, idcard);
    }
    public int getBuildingtype() {
        return getInt(BUILDINGTYPE);
    }

    public void setBuildingType(int buildingType) {
        putInt(BUILDINGTYPE, buildingType);
    }
    public String getHouseId() {
        return getString(HOUSE_ID);
    }

    public void setHouseId(String houseId) {
        putString(HOUSE_ID, houseId);
    }

    public void clearData() {
        SpSir.getInstance().putString(SpSir.REALNAME, "");
        SpSir.getInstance().putString(SpSir.MOBILE, "");
        SpSir.getInstance().putString(SpSir.IDCARD, "");

        SpSir.getInstance().putString(SpSir.HOUSE_JSON, "");
        SpSir.getInstance().putString(SpSir.HOUSE_ID, "");
        SpSir.getInstance().putInt(SpSir.HOUSE_SELECT_TYPE, -1);
    }
}
