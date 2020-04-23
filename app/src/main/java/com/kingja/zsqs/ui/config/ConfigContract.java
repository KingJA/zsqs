package com.kingja.zsqs.ui.config;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.LoginInfo;
import com.kingja.zsqs.net.entiy.ProjectBaseInfo;

import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ConfigContract {
    interface View extends BaseView {
        void onGetProjectInfoSuccess(ProjectBaseInfo projectBaseInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void getProjectInfo(String projectId);
    }
}
