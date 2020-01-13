package com.kingja.zsqs.ui.placement.detail;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.PlacementDetail;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlacementDetailPresenter implements PlacementDetailContract.Presenter {
    private UserApi mApi;
    private PlacementDetailContract.View mView;

    @Inject
    public PlacementDetailPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getPlacementDetail(int progressId) {
        mApi.getApiService().getPlacementDetail( progressId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<PlacementDetail>(mView) {
                    @Override
                    protected void onSuccess(PlacementDetail placementDetail) {
                        mView.onGetPlacementDetailSuccess(placementDetail);
                    }
                });
    }

    @Override
    public void attachView(@NonNull PlacementDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}