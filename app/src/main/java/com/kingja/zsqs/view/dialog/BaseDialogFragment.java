package com.kingja.zsqs.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.App;
import com.kingja.zsqs.injector.component.AppComponent;

import java.util.Objects;

import butterknife.ButterKnife;

/**
 * Description:TODO
 * Create Time:2019/12/31 0031 上午 9:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected OnConfirmListener onConfirmListener;
    protected OnCancelListener onCancelListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyMiddleDialogStyle);
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

    public void show(FragmentActivity context) {
        show(context.getSupportFragmentManager(), getClass().getSimpleName());
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
        return rootView;
    }

    protected abstract int getContentId();

    @Override
    public void onStart() {
        super.onStart();
        setScreenWidth();
    }

    protected float getScreenWidthRatio() {
        return 0.72f;
    }

    private void setScreenWidth() {
        Window window = getDialog().getWindow();
        DisplayMetrics dm = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width = (int) (dm.widthPixels * getScreenWidthRatio());
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
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
}
