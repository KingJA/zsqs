package com.kingja.zsqs.ui.home;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.HomeConfig;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface HomeContract {
    interface View extends BaseView {
        void onGetHomeConfigSuccess(HomeConfig homeConfig);
    }

    interface Presenter extends BasePresenter<View> {
        void getHomeConfig(String deviceCode);
    }
}
