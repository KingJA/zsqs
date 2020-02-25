package com.kingja.zsqs.ui.boot;

import android.view.View;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.injector.component.AppComponent;

/**
 * Description:TODO
 * Create Time:2020/2/25 0025 上午 9:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BootActivity extends BaseActivity {
    @Override
    public void initVariable() {

    }

    @Override
    public View getContentView() {
        return View.inflate(this, R.layout.activity_boot, null);
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
    public void initNet() {

    }
}
