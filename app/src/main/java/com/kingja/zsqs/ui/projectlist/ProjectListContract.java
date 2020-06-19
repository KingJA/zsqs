package com.kingja.zsqs.ui.projectlist;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.ProjectDetail;
import com.kingja.zsqs.net.entiy.ProjectItem;

import java.util.List;

import retrofit2.http.Query;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ProjectListContract {
    interface View extends BaseView {
        void onGetProjectListSuccess(List<ProjectItem> projectList);
    }

    interface Presenter extends BasePresenter<View> {
        void GetProjectList( int regionId,  String year);
    }
}
