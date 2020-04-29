package com.kingja.zsqs.ui.config;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.ResultObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.ProjectBaseInfo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectInfoPresenter implements ProjectInfoContract.Presenter {
    private UserApi mApi;
    private ProjectInfoContract.View mView;

    @Inject
    public ProjectInfoPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getProjectInfo(String projectId) {
        mApi.getApiService().getProjectInfo( projectId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<ProjectBaseInfo>(mView) {
                    @Override
                    protected void onSuccess(ProjectBaseInfo projectBaseInfo) {
                        mView.onGetProjectInfoSuccess(projectBaseInfo);
                    }
                });
    }

    @Override
    public void attachView(@NonNull ProjectInfoContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}