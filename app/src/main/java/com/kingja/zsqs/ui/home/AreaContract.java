package com.kingja.zsqs.ui.home;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.Area;
import com.kingja.zsqs.net.entiy.HomeConfig;

import java.util.List;

import retrofit2.http.Query;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface AreaContract {
    interface View extends BaseView {
        void onGetAreaList(List<Area> areaList);
    }

    interface Presenter extends BasePresenter<View> {
        void getAreaList(String deviceCode);
    }
}
