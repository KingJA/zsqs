package com.kingja.zsqs.net.api;

import com.google.gson.Gson;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.constant.Status;
import com.kingja.zsqs.utils.LogUtil;
import com.kingja.zsqs.utils.ToastUtil;
import com.orhanobut.logger.Logger;


import io.reactivex.observers.DefaultObserver;

/**
 * Description：TODO
 * Create Time：2016/10/12 15:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class ResultObserver<T> extends DefaultObserver<HttpResult<T>> {
    private final String TAG = getClass().getSimpleName();
    protected BaseView baseView;

    public ResultObserver(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        showLoading();
        RxRe.getInstance().add(baseView, this);
    }

    protected void showLoading() {
        baseView.showLoading();
    }

    protected void hideLoading() {
        baseView.hideLoading();
    }


    @Override
    public void onNext(HttpResult<T> httpResult) {
        Logger.json(new Gson().toJson(httpResult));
        hideLoading();
        if (httpResult.getStatus_code() == Status.ResultCode.SUCCESS) {

            if (baseView.ifRegisterLoadSir()) {
                baseView.showSuccessCallback();
            }
            onSuccess(httpResult.getData());
        } else if (httpResult.getStatus_code() == Status.ResultCode.ERROR_LOGIN_FAIL) {
            onLoginFail();
        } else if (httpResult.getStatus_code() == Status.ResultCode.UNNORMAL) {
            onResultError(httpResult.getStatus_code(), httpResult.getMessage());
        } else {
            onResultError(httpResult.getStatus_code(), httpResult.getMessage());
        }
    }

    protected abstract void onSuccess(T t);

    protected void onResultError(int code, String message) {
        ToastUtil.showText(message);
    }

    protected void onLoginFail() {
//        LogUtil.e(TAG, "登录失效，请重新登录");
//        SpUtils.put(AppUtil.getContext(), Constants.KEY_TOKEN, "");
//        EventBus.getDefault().post(new ResetLoginStatusEvent());
//        ToastUtil.showText("登录失效，请重新登录");
//
//        for (Activity activity : BaseApplication.list()) {
//            if (!(activity instanceof IndexActivity)) {
//                activity.finish();
//        }
//        }
//        AppManager.getAppManager().finishAllActivity();
//        Intent intent = new Intent(App.getContext(), LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        App.getContext().startActivity(intent);
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        //记录错误
        LogUtil.e(TAG, "【网络错误onServerError】: " + e.toString());
        if (e instanceof ApiException) {
            if (((ApiException) e).getCode() == Status.ResultCode.ERROR_LOGIN_FAIL) {
                onLoginFail();
            } else {
                onResultError(((ApiException) e).getCode(), e.getMessage());
            }
            return;
        }
        onServerError(e);
    }

    protected void onServerError(Throwable e) {
        ToastUtil.showText("服务器开小差");
    }

    @Override
    public void onComplete() {
        LogUtil.e(TAG, "onComplete: ");
    }

    public void cancleRequest() {
        LogUtil.e(TAG, "cancleRequest: " + baseView.getClass().getSimpleName());
        cancel();
    }
}
