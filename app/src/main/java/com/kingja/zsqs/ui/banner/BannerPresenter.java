package com.kingja.zsqs.ui.banner;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.BannerItem;

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
public class BannerPresenter implements BannerContract.Presenter {
    private UserApi mApi;
    private BannerContract.View mView;

    @Inject
    public BannerPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getBanner(String deviceCode) {
        mApi.getApiService().getBanner( deviceCode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<List<BannerItem>>(mView) {
                    @Override
                    protected void onSuccess(List<BannerItem> bannerList) {
                        mView.onGetBannerSuccess(bannerList);
                    }
                });
    }

    @Override
    public void attachView(@NonNull BannerContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}