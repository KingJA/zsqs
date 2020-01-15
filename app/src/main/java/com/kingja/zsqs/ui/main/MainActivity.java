package com.kingja.zsqs.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.ui.home.HomeFragment;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.DateUtil;
import com.kingja.zsqs.view.StringTextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 上午 9:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MainActivity extends BaseActivity implements IStackActivity {

    @BindView(R.id.tv_date)
    StringTextView tvDate;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.ssll_changeHouse)
    SuperShapeLinearLayout ssllChangeHouse;
    @BindView(R.id.ssll_returnHome)
    SuperShapeLinearLayout ssllReturnHome;
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ssll_login)
    SuperShapeLinearLayout ssllLogin;
    private Timer dateTimer;
    private TimerTask timerTask;
    private FragmentManager supportFragmentManager;

    @OnClick({R.id.ssll_changeHouse, R.id.ssll_returnHome, R.id.ssll_login})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_changeHouse:
                break;
            case R.id.ssll_returnHome:
                clearStack();
                break;
            case R.id.ssll_login:
                break;
        }
    }

    private void clearStack() {
        supportFragmentManager.executePendingTransactions();
        for (int i = 0; i < supportFragmentManager.getBackStackEntryCount(); i++) {
            supportFragmentManager.popBackStack();
        }
        ssllReturnHome.setVisibility(View.GONE);
    }

    @Override
    public void initVariable() {

    }

    @Override
    public View getContentView() {
        View rootView = View.inflate(this, R.layout.activity_main, null);
        return rootView;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.rl_content, new HomeFragment()).commit();
        checkStackCount();
    }

    private void switchFragment(Fragment stackFragment) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rl_content, stackFragment);
        fragmentTransaction.addToBackStack(stackFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    protected void initData() {
        startTimer();
    }

    private void startTimer() {
        cancelTimer();
        dateTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> tvDate.setString(DateUtil.StringData()));

            }
        };
        dateTimer.schedule(timerTask, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    private void cancelTimer() {
        if (dateTimer != null) {
            dateTimer.cancel();
            dateTimer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    public void initNet() {

    }

    @Override
    public void addStack(Fragment stackFragment) {
        switchFragment(stackFragment);
        checkStackCount();
    }

    private void checkStackCount() {
        supportFragmentManager.executePendingTransactions();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        Log.e(TAG, "backStackEntryCount: " + backStackEntryCount);
        ssllReturnHome.setVisibility(backStackEntryCount > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void outStack(Fragment stackFragment) {
        supportFragmentManager.popBackStack();

        checkStackCount();
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
}
