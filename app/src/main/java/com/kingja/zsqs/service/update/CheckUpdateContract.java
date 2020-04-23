package com.kingja.zsqs.service.update;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.HouseItem;
import com.kingja.zsqs.net.entiy.UpdateResult;

import java.util.List;

import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface CheckUpdateContract {
    interface View extends BaseView {
        void onCheckUpdateSuccess(UpdateResult updateResult);
    }

    interface Presenter extends BasePresenter<View> {
        void checkUpdate(String versionCode);
    }
}
