package com.kingja.zsqs.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/11/16 0016 下午 6:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GsonUtil {
    public static <T> T json2obj(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

//    List<Person> ps = gson.fromJson(str, new TypeToken<List<Person>>(){}.getType());

    public static <T> List<T> json2List(String json) {
        return new Gson().fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }
}
