package com.kingja.zsqs.ui.dialog.appoint;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.ResultObserver;
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
public class AppointPresenter implements AppointContract.Presenter {
    private UserApi mApi;
    private AppointContract.View mView;

    @Inject
    public AppointPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void decorateAppoint(RequestBody requestBody) {
        mApi.getApiService().decorateAppoint( requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Boolean>(mView) {
                    @Override
                    protected void onSuccess(Boolean success) {
                        mView.onDecorateAppointSuccess(success);
                    }
                });
    }

    @Override
    public void attachView(@NonNull AppointContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}