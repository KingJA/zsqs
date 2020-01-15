package com.kingja.zsqs.ui.banner;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.BannerItem;
import com.kingja.zsqs.net.entiy.FileInfo;

import java.util.List;

import retrofit2.http.Query;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface BannerContract {
    interface View extends BaseView {
        void onGetBannerSuccess(List<BannerItem> bannerList);
    }

    interface Presenter extends BasePresenter<View> {
        void getBanner(String deviceCode);
    }
}
