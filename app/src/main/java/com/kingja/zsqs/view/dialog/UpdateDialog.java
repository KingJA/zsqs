package com.kingja.zsqs.view.dialog;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingja.zsqs.R;
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
public class UpdateDialog extends BaseTimerDialog {


    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;

    @OnClick({R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }


    @Override
    protected void initVariable() {
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

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_progress;
    }

    @Override
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }

    public void setProgress(int progress) {
        pb.setProgress(progress);
        tvProgress.setText(String.format("%d/100",progress));
    }
}
