package com.kingja.zsqs.service;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.HouseItem;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface HouseListContract {
    interface View extends BaseView {
        void onGetHouseListSuccess(List<HouseItem> houseItemList);
    }

    interface Presenter extends BasePresenter<View> {
        void getHouseList(String projectId, String idcard);
    }
}
