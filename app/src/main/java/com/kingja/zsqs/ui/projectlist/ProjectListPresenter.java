package com.kingja.zsqs.ui.projectlist;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.ProjectItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectListPresenter implements ProjectListContract.Presenter {
    private UserApi mApi;
    private ProjectListContract.View mView;

    @Inject
    public ProjectListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void GetProjectList(int regionId, String year) {
        mApi.getApiService().GetProjectList(regionId, year).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<List<ProjectItem>>(mView) {
                    @Override
                    protected void onSuccess(List<ProjectItem> projectList) {
                        mView.onGetProjectListSuccess(projectList);
                    }
                });
    }

    @Override
    public void attachView(@NonNull ProjectListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}