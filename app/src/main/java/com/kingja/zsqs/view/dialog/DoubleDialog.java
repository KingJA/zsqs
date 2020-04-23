package com.kingja.zsqs.view.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.view.StringTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 下午 3:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DoubleDialog extends BaseTimerDialog {
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.sstv_confirm)
    SuperShapeTextView sstvConfirm;
    private String content;
    private String confirmText;

    public static DoubleDialog newInstance(String content, String confirmText) {
        DoubleDialog fragment = new DoubleDialog();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.CONTENT, content);
        args.putString(Constants.Extra.CONFIRMTEXT, confirmText);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.sstv_confirm, R.id.ssll_dismiss})
    void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.sstv_confirm:
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                break;
            case R.id.ssll_dismiss:
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
                break;
        }
    }


    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            content = getArguments().getString(Constants.Extra.CONTENT, "提示内容");
            confirmText = getArguments().getString(Constants.Extra.CONFIRMTEXT, "确定");
        }
    }

    public void setContent(String content) {
        this.content=content;
        tvContent.setText(content);
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {
        tvContent.setText(content);
        sstvConfirm.setText(confirmText);

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_double;
    }

    @Override
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }
}
