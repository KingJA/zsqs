package com.kingja.zsqs.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.kingja.zsqs.base.App;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.event.ShowSwitchButtonEvent;
import com.kingja.zsqs.net.entiy.HouseItem;
import com.kingja.zsqs.utils.GsonUtil;
import com.kingja.zsqs.utils.LogUtil;
import com.kingja.zsqs.utils.SpSir;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Description:TODO
 * Create Time:2018/7/10 13:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class InitializeService extends IntentService implements HouseListContract.View {

    private static final String TAG = "InitializeService";
    @Inject
    HouseListPresenter houseListPresenter;

    public InitializeService(String name) {
        super(name);
    }

    public InitializeService() {
        super("InitializeService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerBaseCompnent.builder()
                .appComponent(App.getContext().getAppComponent())
                .build()
                .inject(this);
        houseListPresenter.attachView(this);
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
        houseListPresenter.getHouseList("e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a", "330302196012075612");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "【数据初始化结束】");
    }

    @Override
    public void onGetHouseListSuccess(List<HouseItem> houseItemList) {
        if (houseItemList == null || houseItemList.size() == 0) {
            SpSir.getInstance().putInt(SpSir.HOUSE_SELECT_TYPE, Constants.HOUSE_SELECT_TYPE.NONE);
        } else if (houseItemList.size() == 1) {
            SpSir.getInstance().putInt(SpSir.HOUSE_SELECT_TYPE, Constants.HOUSE_SELECT_TYPE.ONE);
            SpSir.getInstance().putString(SpSir.HOUSE_JSON, GsonUtil.obj2Json(houseItemList));
            SpSir.getInstance().putString(SpSir.HOUSE_ID,houseItemList.get(0).getHouseId());
        } else {
            SpSir.getInstance().putInt(SpSir.HOUSE_SELECT_TYPE, Constants.HOUSE_SELECT_TYPE.MUL);
            SpSir.getInstance().putString(SpSir.HOUSE_JSON, GsonUtil.obj2Json(houseItemList));
            EventBus.getDefault().post(new ShowSwitchButtonEvent());
        }
    }
}
