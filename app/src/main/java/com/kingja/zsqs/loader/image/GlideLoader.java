package com.kingja.zsqs.loader.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Description：TODO
 * Create Time：2017/3/9 11:08
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GlideLoader implements IImageLoader {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void loadImage(Context context, String url, int resourceId, ImageView view) {
        Glide.with(context)
                .load(getWholePhotoUrl(url))
                .apply(new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC )
                        .placeholder(resourceId == -1 ? R.drawable.ic_placeholder : resourceId)
                        .error(R.drawable.ic_img_fail)
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())

                ).into(view);
    }

    @Override
    public void loadImage(Context context, Uri uri, ImageView view) {
        Glide.with(context)
                .load(uri)
                .apply(
                        new RequestOptions()
                                .skipMemoryCache(false)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC )
                                .placeholder(R.drawable.ic_placeholder)
                                .error(R.drawable.ic_img_fail)
                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                ).into(view);
    }

    @Override
    public void loadRoundImage(Context context, String url, int resourceId, ImageView view, int connerWidth) {
        Glide.with(context).load(getWholePhotoUrl(url)).apply(new RequestOptions().centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC )
                .placeholder(resourceId == -1 ? R.drawable.ic_placeholder : resourceId)
                .error(R.drawable.ic_img_fail)
                .override(view.getMeasuredWidth(), view.getMeasuredHeight())).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(view);
    }

    @Override
    public void loadCircleImage(Context context, String url, int resourceId, ImageView view) {
        Glide.with(context).load(getWholePhotoUrl(url))
                .transition(withCrossFade())
                .apply(new RequestOptions()
//                        .centerCrop()
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC )
                                .placeholder(resourceId == -1 ? R.drawable.ic_placeholder : resourceId)
                                .error(R.drawable.ic_img_fail)
                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                )
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(view);
    }

    @Override
    public void loadCircleImage(Context context, String url, int resourceId, ImageView view, int borderWidth, int
            borderColor) {
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300)
                .setCrossFadeEnabled(true).build();
        Glide.with(context).load(getWholePhotoUrl(url))
                .apply(new RequestOptions()
//                        .centerCrop()
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC )
                                .placeholder(resourceId == -1 ? R.drawable.ic_placeholder : resourceId)
                                .error(R.drawable.ic_img_fail)
                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                )
                .apply(RequestOptions.bitmapTransform(new GlideCircleWithBorder(borderWidth, borderColor)))
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(view);
    }

    public String getWholePhotoUrl(String url) {
        if (url == null) {
            return "";
        }
        return url.startsWith("http") ? url : Constants.BASE_IMG_URL + url;
    }
}
