package com.kingja.zsqs.ui.affirm;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.ResultInfo;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ResultContract {
    interface View extends BaseView {
        void onGetResultInfoSuccess(ResultInfo resultInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void getResultInfo(String projectId, String HouseId, int queryType, int buildingType);
    }
}
