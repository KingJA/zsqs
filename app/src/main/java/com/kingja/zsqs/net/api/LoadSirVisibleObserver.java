package com.kingja.zsqs.net.api;


import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.utils.ToastUtil;

/**
 * Description：TODO
 * Create Time：2016/10/12 15:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class LoadSirVisibleObserver<T> extends ResultObserver<T> {

    public LoadSirVisibleObserver(BaseView baseView) {
        super(baseView);
    }

    @Override
    protected void showLoading() {
        baseView.showLoadingVisibleCallback();
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
        ToastUtil.showText(message);
    }
}
