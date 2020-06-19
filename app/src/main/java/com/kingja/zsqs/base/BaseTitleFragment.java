package com.kingja.zsqs.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ILvSetData;
import com.kingja.zsqs.callback.EmptyCallback;
import com.kingja.zsqs.callback.ErrorCallback;
import com.kingja.zsqs.callback.ErrorMessageCallback;
import com.kingja.zsqs.callback.LoadingCallback;
import com.kingja.zsqs.callback.LoadingVisibleCallback;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.ITimer;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.api.RxRe;
import com.kingja.zsqs.utils.TimerSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.view.dialog.LoadDialog;

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
        BaseInit, IStackFragment, ITimer {
    protected String TAG = getClass().getSimpleName();
    private LoadDialog mDialogProgress;
    protected Unbinder unbinder;
    private IStackActivity stackActivity;
    private TextView tvTitle;
    private StringTextView tvCountdown;
    protected LoadService mBaseLoadService;
    private int countDownTime;
    protected FragmentActivity mFragmentActivity;
    private TimerSir countDownTimer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ifRegisterLoadSir()) {
            mBaseLoadService = LoadSir.getDefault().register(getNewLoadSirView() == null ? view.findViewById(R.id.fl_content) :
                            getNewLoadSirView(),
                    (Callback.OnReloadListener) this::onNetReload);
        }
        setOnFragmentOperListener((IStackActivity) getActivity());
        initVariable();
        initCommon();
        initComponent(App.getContext().getAppComponent());
        initView();
        initData();
        initNet();
    }

    public FragmentActivity getFragmentActivity() {
        return mFragmentActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mFragmentActivity = getActivity();
        View mRootView = inflater.inflate(R.layout.base_title_fra, container, false);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvTitle.setText(getTitle());
        tvCountdown = mRootView.findViewById(R.id.tv_countdown);
        SuperShapeLinearLayout ssllBack = mRootView.findViewById(R.id.ssll_back);
        ssllBack.setOnClickListener(v -> backStack());
        FrameLayout flContent = mRootView.findViewById(R.id.fl_content);
        View contentView = inflater.inflate(getContentId(), container, false);
        flContent.addView(contentView);
        unbinder = ButterKnife.bind(this, contentView);
        return mRootView;

    }
    protected View getNewLoadSirView() {
        return null;
    }
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }


    protected int getCountDownTimer() {
        return Constants.TIME_MILLISECOND.FRAGMENT_CLOSE;
    }

    @Override
    public void goFragment() {
        Log.e(TAG, "goFragment: ");
    }

    protected abstract void initVariable();

    private void initCommon() {
        mDialogProgress = new LoadDialog();
        countDownTime = getCountDownTimer();
        countDownTimer = new TimerSir(getFragmentActivity(), new Runnable() {
            @Override
            public void run() {
                    if (countDownTime > 0) {
//                        Log.e(TAG, "countDownTime: "+countDownTime );
                        updateTimer(countDownTime--);
                    } else {
                        backStack();
                    }
            }
        }, 1000);
    }

    /*设置圆形进度条*/
    protected void setProgressShow(boolean ifShow) {
        if (ifShow) {
            if (mDialogProgress == null) {
                mDialogProgress = new LoadDialog();
            }
            mDialogProgress.show(getActivity());
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


    private void backStack() {
        stackActivity.outStack(this);
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
        if (mDialogProgress != null) {
            mDialogProgress = null;
        }
        super.onDestroyView();
        RxRe.getInstance().cancle(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
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

    protected <T> void setListView(List<T> list, ILvSetData<T> adapter) {
        if (list != null && list.size() > 0) {
            showSuccessCallback();
            adapter.setData(list);
        } else {
            showEmptyCallback();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        countDownTimer.startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
        countDownTimer.stopTimer();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: "+hidden);
        if (hidden) {
            onStopTimer();
        } else {
            onStartTimer();
        }
    }

    @Override
    public void onStartTimer() {
        countDownTimer.startTimer();
    }

    @Override
    public void onStopTimer() {
        countDownTimer.stopTimer();
    }
}
