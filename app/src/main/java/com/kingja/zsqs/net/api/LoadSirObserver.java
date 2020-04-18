package com.kingja.zsqs.net.api;


import android.widget.Toast;

import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.utils.ToastUtil;

/**
 * Description：TODO
 * Create Time：2016/10/12 15:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class LoadSirObserver<T> extends ResultObserver<T> {

    public LoadSirObserver(BaseView baseView) {
        super(baseView);
    }

    @Override
    protected void showLoading() {
        baseView.showLoadingCallback();
    }

    @Override
    protected void hideLoading() {
        baseView.showSuccessCallback();
    }

    @Override
    protected void onServerError(Throwable e) {
        baseView.showErrorCallback();
    }

    @Override
    protected void onResultError(int code, String message) {
        baseView.showErrorMessage(code, message);
    }
}
