package com.kingja.zsqs.ui.project;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.ProjectDetail;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ProjectDetailContract {
    interface View extends BaseView {
        void onGetDecorationDetailSuccess(ProjectDetail projectDetail);
    }

    interface Presenter extends BasePresenter<View> {
        void getDecorationDetail(String projectId);
    }
}
