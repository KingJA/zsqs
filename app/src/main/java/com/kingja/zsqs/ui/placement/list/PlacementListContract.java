package com.kingja.zsqs.ui.placement.list;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.FileInfo;
import com.kingja.zsqs.net.entiy.PlacementItem;

import java.util.List;

import retrofit2.http.Query;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface PlacementListContract {
    interface View extends BaseView {
        void onGetPlacementListSuccess(List<PlacementItem> placementItemList);
    }

    interface Presenter extends BasePresenter<View> {
        void getPlacementList(String projectId);
    }
}
