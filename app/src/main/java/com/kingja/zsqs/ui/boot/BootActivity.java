package com.kingja.zsqs.ui.boot;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.ui.config.DeviceCodeConfigActivity;
import com.kingja.zsqs.ui.main.MainActivity;
import com.kingja.zsqs.utils.GoUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.VersionUtil;
import com.kingja.zsqs.view.StringTextView;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2020/2/25 0025 上午 9:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BootActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    StringTextView tvVersion;
    private Handler goHandler;

    @Override
    public View getContentView() {
        return View.inflate(this, R.layout.activity_boot, null);
    }

    @Override
    protected void initData() {
        tvVersion.setString(String.format("V%s-%d", VersionUtil.getVerName(this), VersionUtil.getVersionCode(this)));
    }

    @Override
    public void initNet() {
        goHandler = new Handler();
        goHandler.postDelayed(DelayRunnable, Constants.TIME_MILLISECOND.BOOT_PAGE_GO);
    }

    private Runnable DelayRunnable = () -> {
        if (TextUtils.isEmpty(SpSir.getInstance().getProjectId())) {
            GoUtil.goActivityAndFinish(BootActivity.this, DeviceCodeConfigActivity.class);
        } else {
            GoUtil.goActivityAndFinish(BootActivity.this, MainActivity.class);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        goHandler.removeCallbacks(DelayRunnable);
    }
}
