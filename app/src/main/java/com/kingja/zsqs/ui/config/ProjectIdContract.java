package com.kingja.zsqs.ui.config;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.ProjectIdResult;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ProjectIdContract {
    interface View extends BaseView {
        void onGetProjectIdSuccess(ProjectIdResult projectIdResult);
    }

    interface Presenter extends BasePresenter<View> {
        void getProjectId(String deviceCode);
    }
}
