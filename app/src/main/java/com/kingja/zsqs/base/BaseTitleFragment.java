package com.kingja.zsqs.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ILvSetData;
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

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description：TODO
 * Create Time：2017/3/20 14:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseTitleFragment extends Fragment implements BaseView, DialogInterface.OnDismissListener,
        BaseInit, IStackFragment {
    protected String TAG = getClass().getSimpleName();
    private ProgressDialog mDialogProgress;
    protected Unbinder unbinder;
    private IStackActivity stackActivity;
    private TextView tvTitle;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        setOnFragmentOperListener((IStackActivity) getActivity());
        initVariable();
        initCommon();
        initComponent(App.getContext().getAppComponent());
        initView();
        initData();
        initNet();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void goFragment() {
        Log.e(TAG, "goFragment: ");
    }

    protected abstract void initVariable();

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

    protected abstract void initComponent(AppComponent appComponent);

    protected abstract void initView();

    protected abstract void initData();

    public abstract void initNet();

    public void setOnFragmentOperListener(IStackActivity stackActivity) {
        this.stackActivity = stackActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        Log.e(TAG, "onCreateView: " );
        View mRootView = inflater.inflate(R.layout.base_title_fra, container, false);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        SuperShapeLinearLayout ssllBack = mRootView.findViewById(R.id.ssll_back);
        FrameLayout flContent = mRootView.findViewById(R.id.fl_content);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        View contentView = View.inflate(getActivity(), getContentId(), null);
        flContent.addView(contentView, params);
        ssllBack.setOnClickListener(v -> stackActivity.outStack(this));
        tvTitle.setText(getTitle());
        unbinder = ButterKnife.bind(this, contentView);
        return mRootView;

    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    protected abstract String getTitle();

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
    }

    @Override
    public void showEmptyCallback() {
    }

    @Override
    public void showErrorCallback() {
    }

    @Override
    public void showSuccessCallback() {
    }


    @Override
    public void showErrorMessage(int code, String message) {
        ToastUtil.showText(message);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }

    protected <T> void setListView(List<T> list, ILvSetData<T> adapter) {
        if (list != null && list.size() > 0) {
            showSuccessCallback();
            adapter.setData(list);
        } else {
            showEmptyCallback();
        }
    }
}
