package com.kingja.zsqs.service.update;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.ServiceObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.UpdateResult;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CheckUpdatePresenter implements CheckUpdateContract.Presenter {
    private UserApi mApi;
    private CheckUpdateContract.View mView;

    @Inject
    public CheckUpdatePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void checkUpdate(String versionCode) {
        mApi.getApiService().checkUpdate( versionCode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ServiceObserver<UpdateResult>(mView) {
                    @Override
                    protected void onSuccess(UpdateResult updateResult) {
                        mView.onCheckUpdateSuccess(updateResult);
                    }
                });
    }

    @Override
    public void attachView(@NonNull CheckUpdateContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}