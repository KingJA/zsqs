package com.kingja.zsqs.net.api;



import com.kingja.zsqs.constant.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 项目名称：和ApiService相关联
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/13 15:11
 * 修改备注：
 */
public class UserApi {

    private ApiService apiService;

    public UserApi() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constants.NETWORK.CONNECTTIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.NETWORK.WRITETIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.NETWORK.READTIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new TokenHeadInterceptor())
                .addInterceptor(new SwtichUrlInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(RxGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }


}
