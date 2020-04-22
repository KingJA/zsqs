package com.kingja.zsqs.ui.main;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.RuntimeABI;
import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.event.ShowSwitchButtonEvent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.ui.home.HomeFragment;
import com.kingja.zsqs.ui.login.LoginByFaceFragment;
import com.kingja.zsqs.ui.login.LoginFragment;
import com.kingja.zsqs.utils.DateUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.view.dialog.DialogHouseSelect;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.ssll_login)
    SuperShapeLinearLayout ssllLogin;
    @BindView(R.id.ssll_quit)
    SuperShapeLinearLayout ssllQuit;
    private Timer dateTimer;
    private TimerTask timerTask;
    private FragmentManager supportFragmentManager;
    boolean libraryExists = true;
    // Demo 所需的动态库文件
    private static final String[] LIBRARIES = new String[]{
            // 人脸相关
            "libarcsoft_face_engine.so",
            "libarcsoft_face.so",
            // 图像库相关
            "libarcsoft_image_util.so",
    };

    private boolean checkSoFile(String[] libraries) {
        File dir = new File(getApplicationInfo().nativeLibraryDir);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return false;
        }
        List<String> libraryNameList = new ArrayList<>();
        for (File file : files) {
            libraryNameList.add(file.getName());
        }
        boolean exists = true;
        for (String library : libraries) {
            exists &= libraryNameList.contains(library);
        }
        return exists;
    }

    @OnClick({R.id.ssll_changeHouse, R.id.ssll_returnHome, R.id.ssll_login, R.id.ssll_quit})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_changeHouse:
                new DialogHouseSelect().show(this);
                break;
            case R.id.ssll_returnHome:
                clearStack();
                break;
            case R.id.ssll_login:
                switchFragment(new LoginFragment());
//                switchFragment(new LoginByFaceFragment());
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
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(fragments.get(fragments.size()-1)).commit();
    }

    @Override
    public void initVariable() {
        SpSir.getInstance().clearData();
        EventBus.getDefault().register(this);
        checkPermissions();
    }

    @Override
    public View getContentView() {
        return View.inflate(this, R.layout.activity_main, null);
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initView() {
        initSwtichButton();
        switchFragment(new HomeFragment());
    }

    private void initSwtichButton() {
        ssllChangeHouse.setVisibility(SpSir.getInstance().getInt(
                SpSir.HOUSE_SELECT_TYPE) == Constants.HOUSE_SELECT_TYPE.MUL ? View.VISIBLE : View.GONE);
    }

    private void switchFragment(Fragment stackFragment) {
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = this.supportFragmentManager.beginTransaction();
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
    protected void initData() {
        startTimer();
        SpSir.getInstance().putString(SpSir.PROJECT_ID, Constants.PROJECT_ID);
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
        libraryExists = checkSoFile(LIBRARIES);
        Log.e(TAG, "libraryExists: "+libraryExists );
    }
    public void activeEngine() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                RuntimeABI runtimeABI = FaceEngine.getRuntimeABI();
                Log.i(TAG, "subscribe: getRuntimeABI() " + runtimeABI);

                long start = System.currentTimeMillis();
                int activeCode = FaceEngine.activeOnline(MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                Log.i(TAG, "subscribe cost: " + (System.currentTimeMillis() - start));
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            Log.e(TAG, "active_success: " );
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            Log.e(TAG, "already_activated: " );
                        } else {
                            Log.e(TAG, "active_failed: "+activeCode );
                        }
                        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
                        int res = FaceEngine.getActiveFileInfo(MainActivity.this, activeFileInfo);
                        if (res == ErrorInfo.MOK) {
                            Log.i(TAG, activeFileInfo.toString());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    @Override
    public void addStack(Fragment stackFragment) {
        switchFragment(stackFragment);
        checkStackCount();
    }

    @Override
    public void addStackAndOutLast(Fragment stackFragment) {
        switchFragment(stackFragment);


        checkStackCount();
    }

    @Override
    public void outStack(Fragment stackFragment) {
        supportFragmentManager.popBackStack(stackFragment.getClass().getSimpleName(), 1);
        checkStackCount();
        fragments.remove(stackFragment);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(fragments.get(fragments.size() - 1)).commit();
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


    public void checkPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        Disposable disposable = rxPermission
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {

                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted&&permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
                            // 用户已经同意该权限
                            activeEngine();
                            Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });
    }


}
