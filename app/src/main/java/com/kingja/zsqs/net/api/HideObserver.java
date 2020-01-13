package com.kingja.zsqs.net.api;


import com.kingja.zsqs.base.BaseView;

/**
 * Description：TODO
 * Create Time：2016/10/12 15:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class HideObserver<T> extends ResultObserver<T> {

    public HideObserver(BaseView baseView) {
        super(baseView);
    }

    @Override
    protected void showLoading() {
    }

    @Override
    protected void hideLoading() {
    }

}
