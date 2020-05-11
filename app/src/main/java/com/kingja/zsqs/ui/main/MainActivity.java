package com.kingja.zsqs.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kingja.phoenixsir.AppUpdater;
import com.kingja.phoenixsir.updater.net.INetDownloadCallback;
import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.event.ShowSwitchButtonEvent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.UpdateResult;
import com.kingja.zsqs.service.update.CheckUpdateContract;
import com.kingja.zsqs.service.update.CheckUpdatePresenter;
import com.kingja.zsqs.ui.home.HomeFragment;
import com.kingja.zsqs.ui.login.LoginFragment;
import com.kingja.zsqs.utils.DateUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.TimerSir;
import com.kingja.zsqs.utils.VersionUtil;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.view.dialog.BaseTimerDialog;
import com.kingja.zsqs.view.dialog.DoubleDialog;
import com.kingja.zsqs.view.dialog.HouseSelectDialog;
import com.kingja.zsqs.view.dialog.UpdateDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 上午 9:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MainActivity extends BaseActivity implements IStackActivity, CheckUpdateContract.View {
    @Inject
    CheckUpdatePresenter checkUpdatePresenter;
    @BindView(R.id.tv_date)
    StringTextView tvDate;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.ssll_changeHouse)
    SuperShapeLinearLayout ssllChangeHouse;
    @BindView(R.id.ssll_returnHome)
    SuperShapeLinearLayout ssllReturnHome;
    @BindView(R.id.ssll_login)
    SuperShapeLinearLayout ssllLogin;
    @BindView(R.id.ssll_quit)
    SuperShapeLinearLayout ssllQuit;
    private FragmentManager supportFragmentManager;
    private UpdateDialog progressDialog;
    private TimerSir dateTimer;


    @OnClick({R.id.ssll_changeHouse, R.id.ssll_returnHome, R.id.ssll_login, R.id.ssll_quit})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_changeHouse:
                new HouseSelectDialog().show(this);
                break;
            case R.id.ssll_returnHome:
                clearStack();
                break;
            case R.id.ssll_login:
                if (fragments.get(fragments.size() - 1).getClass() == LoginFragment.class) {
                    return;
                }
                switchFragment(new LoginFragment());
                break;
            case R.id.ssll_quit:
                setLogined(false);
                SpSir.getInstance().clearData();
                initSwtichButton();
                break;
        }
    }

    private void clearStack() {
        supportFragmentManager.executePendingTransactions();
        for (int i = 0; i < supportFragmentManager.getBackStackEntryCount(); i++) {
            supportFragmentManager.popBackStack();
        }
        ssllReturnHome.setVisibility(View.GONE);
        for (int i = fragments.size() - 1; i > 0; i--) {
            fragments.remove(i);
        }
//        fragments.clear();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(fragments.get(fragments.size() - 1)).commit();
    }

    @Override
    public void initVariable() {
        SpSir.getInstance().clearData();
        EventBus.getDefault().register(this);
    }

    @Override
    public View getContentView() {
        return View.inflate(this, R.layout.activity_main, null);
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        checkUpdatePresenter.attachView(this);
    }

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initView() {
        initSwtichButton();
        dateTimer = new TimerSir(this, () -> tvDate.setString(DateUtil.StringData()),1000);
        fragments.clear();
        switchFragment(new HomeFragment());
        progressDialog = new UpdateDialog();
    }

    private void initSwtichButton() {
        ssllChangeHouse.setVisibility(SpSir.getInstance().getInt(
                SpSir.HOUSE_SELECT_TYPE) == Constants.HOUSE_SELECT_TYPE.MUL ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        dateTimer.startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dateTimer.stopTimer();
    }


    @Override
    public void initNet() {
        checkUpdatePresenter.checkUpdate(VersionUtil.getVersionCode(this) + "");
    }


    @Override
    public void addStack(Fragment stackFragment) {
        switchFragment(stackFragment);
        checkStackCount();
    }

    @Override
    public void addStackAndOutLast(Fragment addFragment, Fragment outFragment) {
        supportFragmentManager.popBackStack(outFragment.getClass().getSimpleName(), 1);
        fragments.remove(outFragment);
        switchFragment(addFragment);
        checkStackCount();
    }

    private void switchFragment(Fragment stackFragment) {
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (fragments.size() > 0) {
            fragmentTransaction.hide(fragments.get(fragments.size() - 1)).add(R.id.rl_content, stackFragment).show(stackFragment);
            fragmentTransaction.addToBackStack(stackFragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.add(R.id.rl_content, stackFragment);
        }
        fragmentTransaction.commit();
        fragments.add(stackFragment);
    }

    @Override
    public void outStack(Fragment stackFragment) {
        supportFragmentManager.popBackStack(stackFragment.getClass().getSimpleName(), 1);
        checkStackCount();
        fragments.remove(stackFragment);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(fragments.get(fragments.size() - 1)).commitAllowingStateLoss();
    }

    private void checkStackCount() {
        supportFragmentManager.executePendingTransactions();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        ssllReturnHome.setVisibility(backStackEntryCount > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addOrder(ShowSwitchButtonEvent event) {
        ssllChangeHouse.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginStatusEvent(LoginStatusEvent event) {
        setLogined(event.isHasLogined());
    }

    private void setLogined(boolean isHasLogined) {
        ssllLogin.setVisibility(isHasLogined ? View.GONE : View.VISIBLE);
        ssllQuit.setVisibility(isHasLogined ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCheckUpdateSuccess(UpdateResult updateResult) {
        if (updateResult.getStatus() == 1) {
            DoubleDialog updateDialog = DoubleDialog.newInstance("检测到新版本，是否马上更新", "是的");
            updateDialog.setOnConfirmListener(new BaseTimerDialog.OnConfirmListener() {
                @Override
                public void onConfirm() {
                    progressDialog.show(MainActivity.this);
                    AppUpdater.getInstance().getNetManager().download(updateResult.getDownload_url(),
                            new File(getCacheDir(), "update" +
                                    ".apk"), new INetDownloadCallback() {
                                @Override
                                public void onDownloadSuccess(File apkFile) {
                                    progressDialog.dismiss();
                                    VersionUtil.installApk(MainActivity.this, apkFile);
                                }

                                @Override
                                public void onProgress(int progress) {
                                    progressDialog.setProgress(progress);
                                }

                                @Override
                                public void onDownloadFailed(Throwable throwable) {
                                    Log.e(TAG, "onDownloadFailed: " + throwable.toString());
                                }
                            });
                }

            });
            updateDialog.show(this);
        }

    }
}
