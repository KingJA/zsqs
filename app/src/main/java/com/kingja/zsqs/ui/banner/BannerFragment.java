package com.kingja.zsqs.ui.banner;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.BannerAdapter;
import com.kingja.zsqs.base.BaseFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.BannerItem;
import com.kingja.zsqs.utils.AppUtil;
import com.kingja.zsqs.view.AutoViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 上午 9:07
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BannerFragment extends BaseFragment implements BannerContract.View,
        AutoViewPager.OnViewPagerTouchListener {
    @Inject
    BannerPresenter bannerPresenter;
    @BindView(R.id.vp_banner)
    AutoViewPager vpBanner;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    private List<View> points = new ArrayList<>();
    private Handler autoHandler;
    private boolean mIsTouch = false;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        bannerPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        vpBanner.setOnViewPagerTouchListener(this);
    }

    @Override
    protected void initData() {
        autoHandler = new Handler();
    }

    @Override
    public void initNet() {
        bannerPresenter.getBanner("a7fab63cabc40063");
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_banner;
    }

    @Override
    public void onGetBannerSuccess(List<BannerItem> bannerList) {
        fillBanner(bannerList);
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    private void fillBanner(List<BannerItem> bannerList) {
        if (bannerList == null || bannerList.size() == 0) {
            return;
        }
        initBanner(bannerList);
        if (bannerList.size() > 1) {
            initDots(bannerList);
            startAutoBanner(bannerList.size());
        }
    }

    private void initBanner(List<BannerItem> bannerList) {
        BannerAdapter bannerPageAdapter = new BannerAdapter(getActivity(), bannerList);
        vpBanner.setAdapter(bannerPageAdapter);
        vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (bannerList.size() < 2) {
                    return;
                }
                for (int i = 0; i < points.size(); i++) {
                    if (i == position % bannerList.size()) {
                        points.get(i).setBackgroundResource(R.mipmap.ic_dot_sel);
                    } else {
                        points.get(i).setBackgroundResource(R.mipmap.ic_dot_nor);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDots(List<BannerItem> bannerList) {
        for (int i = 0; i < bannerList.size(); i++) {
            View view = new View(getActivity());
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.ic_dot_sel);
            } else {
                view.setBackgroundResource(R.mipmap.ic_dot_nor);
            }
            points.add(view);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AppUtil.dp2px(8), AppUtil.dp2px(8));
        layoutParams.setMargins(0, 0, AppUtil.dp2px(12), 0);
        for (int i = 0; i < bannerList.size(); i++) {
            llDot.addView(points.get(i), layoutParams);
        }
    }

    public void startAutoBanner(int size) {
        if (size == 1) {
            return;
        }
        autoHandler.removeCallbacks(autoRunnable);
        autoRunnable = () -> {
            if (vpBanner!=null&&!mIsTouch) {
                int currentItem = vpBanner.getCurrentItem();
                autoHandler.removeCallbacks(autoRunnable);
                vpBanner.setCurrentItem(++currentItem);
            }
            new Handler().postDelayed(autoRunnable, Constants.TIME_MILLISECOND.BANNER);

        };
        new Handler().postDelayed(autoRunnable,  Constants.TIME_MILLISECOND.BANNER);
    }

    public void stopAutoBanner() {
        autoHandler.removeCallbacks(autoRunnable);
    }

    private Runnable autoRunnable;

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAutoBanner();
    }

    @Override
    public void onPagerTouch(boolean isTouch) {
        Log.e(TAG, "onPagerTouch: " + isTouch);
        mIsTouch = isTouch;
    }
}
