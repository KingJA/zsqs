package com.kingja.zsqs.ui.config;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.ResultObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.ProjectIdResult;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectIdPresenter implements ProjectIdContract.Presenter {
    private UserApi mApi;
    private ProjectIdContract.View mView;

    @Inject
    public ProjectIdPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getProjectId(String deviceCode) {
        mApi.getApiService().getProjectId(deviceCode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<ProjectIdResult>(mView) {
                    @Override
                    protected void onSuccess(ProjectIdResult projectIdResult) {
                        mView.onGetProjectIdSuccess(projectIdResult);
                    }
                });
    }

    @Override
    public void attachView(@NonNull ProjectIdContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}