package com.kingja.zsqs.service.update;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.kingja.zsqs.base.App;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.net.entiy.UpdateResult;
import com.kingja.zsqs.utils.VersionUtil;

import javax.inject.Inject;

/**
 * Description:TODO
 * Create Time:2018/7/10 13:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CheckUpdateService extends IntentService implements CheckUpdateContract.View {

    private static final String TAG = "CheckUpdateService";
    @Inject
    CheckUpdatePresenter checkUpdatePresenter;

    public CheckUpdateService(String name) {
        super(name);
    }

    public CheckUpdateService() {
        super("CheckUpdateService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerBaseCompnent.builder()
                .appComponent(App.getContext().getAppComponent())
                .build()
                .inject(this);
        checkUpdatePresenter.attachView(this);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        checkUpdatePresenter.checkUpdate(VersionUtil.getVersionCode(this)+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCheckUpdateSuccess(UpdateResult updateResult) {
//        DoubleDialog.newInstance("检测到新版本，请马上更新","确定").show(this);

    }
}
