package com.kingja.zsqs.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.kingja.loadsir.core.LoadSir;
import com.kingja.zsqs.callback.EmptyCallback;
import com.kingja.zsqs.callback.ErrorCallback;
import com.kingja.zsqs.callback.ErrorMessageCallback;
import com.kingja.zsqs.callback.InvalidCallback;
import com.kingja.zsqs.callback.LoadingCallback;
import com.kingja.zsqs.callback.LoadingVisibleCallback;
import com.kingja.zsqs.callback.UnLoginCallback;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.injector.component.DaggerAppComponent;
import com.kingja.zsqs.injector.module.ApiModule;
import com.kingja.zsqs.injector.module.AppModule;
import com.kingja.zsqs.utils.SoundPlayer;
import com.kingja.zsqs.utils.SpSir;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Description:TODO
 * Create Time:2019/12/31 0031 下午 1:59
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class App5 extends Application {
    private static App5 sInstance;
    public static App5 getContext() {
        return sInstance;
    }
    private AppComponent appComponent;
    private AppModule appModule;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        SoundPlayer.getInstance().init(sInstance);
        initComponent();
        initLoadSir();
        initBugly();
        initTinker();
    }

    private void initTinker() {
        Bugly.init(this, Constants.APP_ID_BUDLY, true);
    }

    private void initBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel(SpSir.getInstance().getDeviceCode());
        CrashReport.initCrashReport(getApplicationContext(), Constants.APP_ID_BUDLY, false,strategy);
    }

    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this)).build();
        appModule = new AppModule(this);
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new ErrorMessageCallback())
                .addCallback(new UnLoginCallback())
                .addCallback(new LoadingVisibleCallback())
                .addCallback(new InvalidCallback())
                .commit();
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

    public AppModule getAppModule() {
        return appModule;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);


        // 安装tinker
        Beta.installTinker();
    }
}
