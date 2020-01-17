package com.kingja.zsqs.ui.login;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.ResultObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.LoginInfo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginPresenter implements LoginContract.Presenter {
    private UserApi mApi;
    private LoginContract.View mView;

    @Inject
    public LoginPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void login(String projectId, String idcard) {
        mApi.getApiService().login( projectId,  idcard).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<LoginInfo>(mView) {
                    @Override
                    protected void onSuccess(LoginInfo loginInfo) {
                        mView.onLoginSuccess(loginInfo);
                    }
                });
    }

    @Override
    public void attachView(@NonNull LoginContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}