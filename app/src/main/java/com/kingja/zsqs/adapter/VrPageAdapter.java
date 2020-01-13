package com.kingja.zsqs.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.loader.image.ImageLoader;
import com.kingja.zsqs.net.entiy.FileItem;
import com.kingja.zsqs.net.entiy.PlacementDetail;
import com.kingja.zsqs.utils.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/8/21 0021 下午 4:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VrPageAdapter extends PagerAdapter {
    private List<View> fileViews = new ArrayList<>();

    public VrPageAdapter(Activity context, List<PlacementDetail.HousePlanRenovationCaseBean> list) {
        for (PlacementDetail.HousePlanRenovationCaseBean item : list) {
            View itemView = View.inflate(context, R.layout.page_vr, null);
            ImageView iv_vr = itemView.findViewById(R.id.iv_vr);
            ImageView iv_img = itemView.findViewById(R.id.iv_img);
            TextView tv_styleName = itemView.findViewById(R.id.tv_styleName);
            ImageLoader.getInstance().loadImage(context,Constants.BASE_FWCQ_IMG_URL+ item.getVr_img_url(),iv_img);
            tv_styleName.setText(item.getStyle_name());
            itemView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                }
            });
            fileViews.add(itemView);
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return fileViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View bannerView = fileViews.get(position);
        ViewParent parent = bannerView.getParent();
        if (parent != null) {
            ((ViewPager) bannerView.getParent()).removeView(bannerView);
            if (((ViewPager) parent).getChildCount() < fileViews.size()) {
                container.addView(bannerView);
            }
        } else {
            container.addView(bannerView);
        }
        return bannerView;
    }
}
