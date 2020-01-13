package com.kingja.zsqs.ui.project;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.ProjectDetail;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectDetailPresenter implements ProjectDetailContract.Presenter {
    private UserApi mApi;
    private ProjectDetailContract.View mView;

    @Inject
    public ProjectDetailPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getDecorationDetail(String projectId) {
        mApi.getApiService().getDecorationDetail( projectId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<ProjectDetail>(mView) {
                    @Override
                    protected void onSuccess(ProjectDetail projectDetail) {
                        mView.onGetDecorationDetailSuccess(projectDetail);
                    }
                });
    }

    @Override
    public void attachView(@NonNull ProjectDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}