package com.kingja.zsqs.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadLayout;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.kingja.zsqs.R;
import com.kingja.zsqs.callback.EmptyCallback;
import com.kingja.zsqs.callback.ErrorCallback;
import com.kingja.zsqs.callback.ErrorMessageCallback;
import com.kingja.zsqs.callback.LoadingCallback;
import com.kingja.zsqs.callback.LoadingVisibleCallback;
import com.kingja.zsqs.callback.UnLoginCallback;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.api.RxRe;
import com.kingja.zsqs.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description：TODO
 * Create Time：2017/3/20 14:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseFragment extends Fragment implements BaseView, DialogInterface.OnDismissListener, BaseInit {
    protected String TAG = getClass().getSimpleName();
    private ProgressDialog mDialogProgress;
    protected Unbinder unbinder;
    protected LoadService mBaseLoadService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariable();
        initCommon();
        initComponent(App.getContext().getAppComponent());
        initView();
        initData();
        initNet();
    }


    private void initCommon() {
        mDialogProgress = new ProgressDialog(getActivity());
        mDialogProgress.setCancelable(true);
        mDialogProgress.setCanceledOnTouchOutside(false);
        mDialogProgress.setOnDismissListener(this);
        mDialogProgress.setMessage("加载中");
    }

    /*设置圆形进度条*/
    protected void setProgressShow(boolean ifShow) {
        if (ifShow) {
            mDialogProgress.show();
        } else {
            mDialogProgress.dismiss();
        }
    }

    protected void initVariable() {

    }

    protected abstract void initComponent(AppComponent appComponent);

    protected void initView() {

    }

    protected void initData() {

    }

    public void initNet() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View mRootView = inflater.inflate(getContentId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        if (ifRegisterLoadSir()) {
            mBaseLoadService = LoadSir.getDefault().register(mRootView, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    onNetReload(v);
                }
            });
            LoadLayout fragmentView = mBaseLoadService.getLoadLayout();
            fragmentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            return fragmentView;
        }
        return mRootView;

    }

    protected void onNetReload(View v) {
        initNet();
    }

    protected abstract int getContentId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxRe.getInstance().cancle(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mDialogProgress != null && mDialogProgress.isShowing()) {
            mDialogProgress.dismiss();
            mDialogProgress = null;
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void showLoading() {
        setProgressShow(true);
    }

    @Override
    public void hideLoading() {
        setProgressShow(false);
    }

    @Override
    public void showCusLoading() {

    }

    @Override
    public void hideCusLoading() {

    }

    @Override
    public boolean ifShowCusLoading() {
        return false;
    }

    @Override
    public void showLoadingCallback() {
        mBaseLoadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void showEmptyCallback() {
        mBaseLoadService.showCallback(EmptyCallback.class);
    }

    @Override
    public void showErrorCallback() {
        mBaseLoadService.showCallback(ErrorCallback.class);
    }

    @Override
    public void showSuccessCallback() {
        mBaseLoadService.showSuccess();
    }

    @Override
    public void showUnLoginCallback() {
        mBaseLoadService.showCallback(UnLoginCallback.class);
    }

    @Override
    public void showLoadingVisibleCallback() {
        mBaseLoadService.showCallback(LoadingVisibleCallback.class);
    }


    @Override
    public void showErrorMessage(int code, String message) {
        if (ifRegisterLoadSir()) {
            mBaseLoadService.setCallBack(ErrorMessageCallback.class, new Transport() {
                @Override
                public void order(Context context, View view) {
                    TextView tvErrorMsg = view.findViewById(R.id.tv_layout_errorMsg);
                    tvErrorMsg.setText(message);
                }
            });
            mBaseLoadService.showCallback(ErrorMessageCallback.class);
        } else {
            ToastUtil.showText(message);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }


}
