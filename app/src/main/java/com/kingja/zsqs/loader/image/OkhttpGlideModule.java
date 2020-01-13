package com.kingja.zsqs.loader.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * Created by hp on 2019/4/30.
 */
@GlideModule
public class OkhttpGlideModule extends AppGlideModule{

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(SSLSocketFactory.getHttpClient()));
    }
}
