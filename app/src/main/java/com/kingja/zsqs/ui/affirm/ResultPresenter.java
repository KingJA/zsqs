package com.kingja.zsqs.ui.affirm;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.ResultInfo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ResultPresenter implements ResultContract.Presenter {
    private UserApi mApi;
    private ResultContract.View mView;

    @Inject
    public ResultPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getResultInfo(String projectId,String HouseId,int queryType, int buildingType) {
        mApi.getApiService().getResultInfo( projectId, HouseId, queryType,buildingType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<ResultInfo>(mView) {
                    @Override
                    protected void onSuccess(ResultInfo resultInfo) {
                        mView.onGetResultInfoSuccess(resultInfo);
                    }
                });
    }

    @Override
    public void attachView(@NonNull ResultContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}