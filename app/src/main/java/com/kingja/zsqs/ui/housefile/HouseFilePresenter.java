package com.kingja.zsqs.ui.housefile;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.FileInfo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HouseFilePresenter implements HouseFileContract.Presenter {
    private UserApi mApi;
    private HouseFileContract.View mView;

    @Inject
    public HouseFilePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getHouseFileInfo(String projectId, String houseId, int fileType, int buildingType) {
        mApi.getApiService().getHouseFileInfo(projectId, houseId, fileType, buildingType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<FileInfo>(mView) {
                    @Override
                    protected void onSuccess(FileInfo fileInfo) {
                        mView.onGetHouseFileInfoSuccess(fileInfo);
                    }
                });
    }

    @Override
    public void attachView(@NonNull HouseFileContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}