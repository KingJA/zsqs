package com.kingja.zsqs.ui.placement.detail;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.PlacementDetail;
import com.kingja.zsqs.net.entiy.PlacementItem;

import java.util.List;

import retrofit2.http.Path;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface PlacementDetailContract {
    interface View extends BaseView {
        void onGetPlacementDetailSuccess(PlacementDetail placementDetail);
    }

    interface Presenter extends BasePresenter<View> {
        void getPlacementDetail(int progressId);
    }
}
