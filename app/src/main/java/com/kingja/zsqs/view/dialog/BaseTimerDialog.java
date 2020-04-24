package com.kingja.zsqs.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.App;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.ITimer;
import com.kingja.zsqs.injector.component.AppComponent;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * Description:TODO
 * Create Time:2019/12/31 0031 上午 9:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseTimerDialog extends DialogFragment {
    private final String TAG = this.getClass().getSimpleName();
    protected OnConfirmListener onConfirmListener;
    protected OnCancelListener onCancelListener;
    protected FragmentActivity mActivity;
    private Timer mTimer;
    private FragmentManager supportFragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCusStyle();
        setCancelable(getCancelalbe());
    }

    protected void setCusStyle() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyMiddleDialogStyle);

    }

    protected boolean getCancelalbe() {
        return false;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariable();
        initComponent(App.getContext().getAppComponent());
        initView();
        initData();
        initNet();
    }


    protected abstract void initVariable();

    protected abstract void initComponent(AppComponent appComponent);

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initNet();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentId(), container, false);
        ButterKnife.bind(this, rootView);
        mActivity = getActivity();
        return rootView;
    }

    protected abstract int getContentId();


    protected float getScreenWidthRatio() {
        return 0.72f;
    }

    protected float getScreenHeighRatio() {
        return 0;
    }

    private void setScreenWidth() {
        Window window = getDialog().getWindow();
        DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width = (int) (dm.widthPixels * getScreenWidthRatio());
            layoutParams.height = getScreenHeighRatio() == 0 ? WindowManager.LayoutParams.WRAP_CONTENT :
                    (int) (dm.heightPixels * getScreenHeighRatio());
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setAttributes(layoutParams);
        }
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public interface OnConfirmListener {
        void onConfirm();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public void initTimer() {
        cancelTimer();
        countDownTime = getCountDownTimer();
        mTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Objects.requireNonNull(mActivity).runOnUiThread(() -> {
                    if (countDownTime > 0) {
                        updateTimer(countDownTime--);
                    } else {
                        if (onCancelListener != null) {
                            onCancelListener.onCancel();
                        }
                        dismiss();
                    }
                });
            }
        };
        mTimer.schedule(timerTask, 0, 1000);
    }

    protected int getCountDownTimer() {
        return Constants.TIME_MILLISECOND.DIALOG_CLOSE;
    }

    private int countDownTime;


    protected void updateTimer(int countDownTime) {

    }

    public void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
//        if (onCancelListener != null) {
//            onCancelListener.onCancel();
//        }
        isShowing = false;
        cancelTimer();
        try {
            Fragment fragment =
                    supportFragmentManager.getFragments().get(supportFragmentManager.getFragments().size() - 2);
            if (fragment instanceof ITimer) {
                ITimer timer = (ITimer) fragment;
                timer.onStartTimer();
            }
        } catch (Exception e) {
            Log.e(TAG, "onDismiss: " + e.toString());
        }
        super.onDismiss(dialog);
    }

    private boolean isShowing;

    public void show(FragmentActivity context) {
        isShowing = true;
        supportFragmentManager = context.getSupportFragmentManager();
        try {
            Fragment fragment =
                    supportFragmentManager.getFragments().get(supportFragmentManager.getFragments().size() - 1);
            if (fragment instanceof ITimer) {
                ITimer timer = (ITimer) fragment;
                timer.onStopTimer();
            }
        } catch (Exception e) {
            Log.e(TAG, "show: " + e.toString());
        }
        show(context.getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    public void onStart() {
        super.onStart();
        setScreenWidth();
        if (ifStartTimer()) {
            initTimer();
        }
    }

    protected boolean ifStartTimer() {
        return true;
    }
}
