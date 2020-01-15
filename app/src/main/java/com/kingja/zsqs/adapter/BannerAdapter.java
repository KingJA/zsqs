package com.kingja.zsqs.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.loader.image.ImageLoader;
import com.kingja.zsqs.net.entiy.BannerItem;
import com.kingja.zsqs.utils.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/8/21 0021 下午 4:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BannerAdapter extends PagerAdapter {
    private List<View> imageViewList = new ArrayList<>();

    public BannerAdapter(Activity context, List<BannerItem> bannerImageList) {
        for (BannerItem bannerItem : bannerImageList) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().loadImage(context, Constants.BASE_FWCQ_IMG_URL + bannerItem.getImg_url(),
                    imageView);
            imageView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                }
            });
            imageViewList.add(imageView);

        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View bannerView = imageViewList.get(position%imageViewList.size());
        ViewParent parent = bannerView.getParent();
        if (parent != null) {
            ((ViewPager) bannerView.getParent()).removeView(bannerView);
            if (((ViewPager) parent).getChildCount() < imageViewList.size()) {
                container.addView(bannerView);
            }
        } else {
            container.addView(bannerView);
        }
        return bannerView;
    }

//    @Override
//    public void finishUpdate(@NonNull ViewGroup container) {
//        super.finishUpdate(container);
//        int position = viewPager.getCurrentItem();
//
//        if (position == 0) {
//            position = simpleDraweeViewList.size() - 2;
//            viewPager.setCurrentItem(position, false);
//        } else if (position == simpleDraweeViewList.size() - 1) {
//            position = 1;
//            viewPager.setCurrentItem(position, false);
//        }
//    }
}
