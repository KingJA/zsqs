package com.kingja.zsqs.base;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.injector.module.ActivityModule;
import com.kingja.zsqs.injector.module.AppModule;
import com.kingja.zsqs.net.api.RxRe;
import com.kingja.zsqs.utils.AppManager;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.dialog.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * Description：BaseToolActivity
 * Create Time：2016/10/14:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView, DialogInterface.OnDismissListener,
        BaseInit {
    protected String TAG = getClass().getSimpleName();
    private LoadDialog mDialogProgress;
    private AppCompatActivity mActivity;


    protected AppCompatActivity getActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        AppManager.getAppManager().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initCommon();
        initVariable();
        setContentView(getContentView());
        ButterKnife.bind(this);
        initComponent(App.getContext().getAppComponent());
        initView();
        initData();
        initNet();
    }

    /*初始化公共组件*/
    private void initCommon() {
        initLoading();
    }

    private void initLoading() {
        mDialogProgress = new LoadDialog();
    }

    /*设置圆形进度条*/
    protected void setProgressShow(boolean ifShow) {
        if (ifShow) {
            if (mDialogProgress == null) {
                mDialogProgress = new LoadDialog();
            }
            mDialogProgress.show(this);
        } else {
            mDialogProgress.dismiss();
        }
    }

    /*获取初始化数据*/
    public  void initVariable(){

    }

    /*获取界面Id*/
    public abstract View getContentView();

    /*依赖注入*/
    protected  void initComponent(AppComponent appComponent){

    }

    /*初始化界面和事件*/
    protected  void initView(){

    }

    protected  void initData(){

    }

    @Override
    public  void initNet(){

    }
    /*初始化网络数据*/


    /*提供全局AppComponent*/
    protected AppComponent getAppComponent() {
        return App.getContext().getAppComponent();
    }

    /*提供全局AppModule*/
    protected AppModule getAppModule() {
        return App.getContext().getAppModule();
    }

    /*提供当前ActivityModule*/
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /*清理事件队列*/
    /*销毁对话框*/
    /*销毁Activity*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxRe.getInstance().cancle(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mDialogProgress != null) {
            mDialogProgress = null;
        }
        AppManager.getAppManager().finishActivity(this);
    }

    public void showSuccessDialogAndFinish() {
        showSuccessDialogAndFinish("保存成功");
    }

    public void showSuccessDialogAndFinish(String tip) {
//        DialogUtil.createSingleDialog(this, tip, () -> finish());
    }

    public void showSuccessToastAndFinish() {
        showSuccessToastAndFinish("保存成功");
    }

    public void showSuccessToastAndFinish(String tip) {
        ToastUtil.showText(tip);
        finish();
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
    public void onDismiss(DialogInterface dialog) {
        RxRe.getInstance().cancle(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
