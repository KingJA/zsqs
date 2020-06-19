package com.kingja.zsqs.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;


import com.kingja.zsqs.R;
import com.kingja.zsqs.utils.AppUtil;

import butterknife.ButterKnife;

/**
 * Description:TODO
 * Create Time:2019/4/16 0016 上午 10:26
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseDialog extends Dialog {
    protected OnConfirmListener onConfirmListener;
    protected OnCancelListener onCancelListener;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.RoundAlertDialog);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    public void show() {
        super.show();
        if(getWindow()!=null) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width = (int) (AppUtil.getScreenWidth() * 0.8f);
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().getDecorView().setPadding(0, 0, 0, 0);
            getWindow().setAttributes(layoutParams);
        }
    }

    public interface OnConfirmListener {
        void onConfirm();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }
}