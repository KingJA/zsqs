package com.kingja.zsqs.service.houses;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.ServiceObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.HouseItem;

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
public class HouseListPresenter implements HouseListContract.Presenter {
    private UserApi mApi;
    private HouseListContract.View mView;

    @Inject
    public HouseListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getHouseList(String projectId, String idcard) {
        mApi.getApiService().getHouseList(projectId, idcard).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ServiceObserver<List<HouseItem>>(mView) {
                    @Override
                    protected void onSuccess(List<HouseItem> houseItemList) {
                        mView.onGetHouseListSuccess(houseItemList);
                    }
                });
    }

    @Override
    public void attachView(@NonNull HouseListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}