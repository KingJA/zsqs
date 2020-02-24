package com.kingja.zsqs.base;

import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.event.OnHouseChangeEvent;
import com.kingja.zsqs.injector.component.AppComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Description:TODO
 * Create Time:2020/2/24 0024 下午 3:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseHouseFragment extends BaseTitleFragment {
    @Override
    protected void initVariable() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected abstract void initComponent(AppComponent appComponent);

    @Override
    protected abstract void initView();

    @Override
    protected abstract void initData();

    @Override
    public abstract void initNet();

    @Override
    protected abstract String getTitle();

    @Override
    protected abstract int getContentId();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHouseChange(OnHouseChangeEvent event) {
        initNet();
    }
}
