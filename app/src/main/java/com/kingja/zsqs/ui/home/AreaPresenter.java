package com.kingja.zsqs.ui.home;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.Area;
import com.kingja.zsqs.net.entiy.HomeConfig;

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
public class AreaPresenter implements AreaContract.Presenter {
    private UserApi mApi;
    private AreaContract.View mView;

    @Inject
    public AreaPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getAreaList(String deviceCode) {
        mApi.getApiService().getAreaList( deviceCode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<List<Area>>(mView) {
                    @Override
                    protected void onSuccess(List<Area> areaList) {
                        mView.onGetAreaList(areaList);
                    }
                });
    }

    @Override
    public void attachView(@NonNull AreaContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}