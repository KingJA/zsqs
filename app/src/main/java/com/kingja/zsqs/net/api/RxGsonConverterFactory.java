package com.kingja.zsqs.net.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Description:TODO
 * Create Time:2019/11/20 0020 下午 4:40
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RxGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private RxGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static RxGsonConverterFactory create() {
        return create(new Gson());
    }

    public static RxGsonConverterFactory create(Gson gson) {
        return new RxGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter(gson, adapter);
    }
}
