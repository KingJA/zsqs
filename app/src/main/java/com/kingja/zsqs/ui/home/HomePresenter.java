package com.kingja.zsqs.ui.home;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.HomeConfig;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomePresenter implements HomeContract.Presenter {
    private UserApi mApi;
    private HomeContract.View mView;

    @Inject
    public HomePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getHomeConfig(String deviceCode) {
        mApi.getApiService().getHomeConfig(deviceCode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<HomeConfig>(mView) {
                    @Override
                    protected void onSuccess(HomeConfig homeConfig) {
                        mView.onGetHomeConfigSuccess(homeConfig);
                    }
                });
    }

    @Override
    public void attachView(@NonNull HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}