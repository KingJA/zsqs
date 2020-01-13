package com.kingja.zsqs.ui.dialog.offer;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OfferPresenter implements OfferContract.Presenter {
    private UserApi mApi;
    private OfferContract.View mView;

    @Inject
    public OfferPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void decorateOffer(RequestBody requestBody) {
        mApi.getApiService().decorateOffer( requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<String>(mView) {
                    @Override
                    protected void onSuccess(String price) {
                        mView.onDecorateOfferSuccess(price);
                    }
                });
    }

    @Override
    public void attachView(@NonNull OfferContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}