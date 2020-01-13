package com.kingja.zsqs.net.api;


import com.kingja.zsqs.utils.EncryptUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description:TODO
 * Create Time:2018/4/17 17:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TokenHeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        long timeStamp = System.currentTimeMillis();
        Request request = chain.request()
                .newBuilder()
                .addHeader("verifyStr", String.valueOf(timeStamp))
                .addHeader("verifyToken", EncryptUtil.getMd5( timeStamp+"pM@mL8nP2N6d26Db"))
                .build();
        return chain.proceed(request);
    }
}
