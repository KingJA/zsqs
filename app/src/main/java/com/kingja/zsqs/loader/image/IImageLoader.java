package com.kingja.zsqs.loader.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Description：TODO
 * Create Time：2017/3/9 11:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IImageLoader {
    void loadImage(Context context, String url, int resourceId, ImageView view);

    void loadRoundImage(Context context, String url, int resourceId, ImageView view, int connerWidth);

    void loadCircleImage(Context context, String url, int resourceId, ImageView view);

    void loadImage(Context context, Uri uri, ImageView view);

    void loadCircleImage(Context context, String url, int resourceId, ImageView view, int borderWidth, int
            borderColor);
}
