package com.kingja.zsqs.net.api;


import android.util.Log;

import com.kingja.zsqs.base.BaseView;

/**
 * Description：TODO
 * Create Time：2016/10/12 15:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class ServiceObserver<T> extends ResultObserver<T> {

    private static final String TAG = "ServiceObserver";

    public ServiceObserver(BaseView baseView) {
        super(baseView);
    }

    @Override
    protected void showLoading() {
    }

    @Override
    protected void hideLoading() {
    }

    @Override
    protected void onServerError(Throwable e) {
        Log.e(TAG, "onServerError: "+e.toString() );
    }

    @Override
    protected void onResultError(int code, String message) {
        Log.e(TAG, "onServerError: "+message );
    }
}
