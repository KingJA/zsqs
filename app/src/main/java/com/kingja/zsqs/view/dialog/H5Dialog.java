package com.kingja.zsqs.view.dialog;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.view.StringTextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2020/1/22 0022 下午 2:41
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class H5Dialog extends BaseTimerDialog {
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;
    @BindView(R.id.stv_title)
    StringTextView stvTitle;
    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;
    private String url;
    private String title;


    @OnClick({R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }


    public static H5Dialog newInstance(String url,String title) {
        H5Dialog fragment = new H5Dialog();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.URL, url);
        args.putString(Constants.Extra.TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            url = getArguments().getString(Constants.Extra.URL);
            title = getArguments().getString(Constants.Extra.TITLE);
        }
    }


    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        stvTitle.setString(title);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_h5;
    }

    @Override
    protected float getScreenWidthRatio() {
        return 0.8f;
    }

    @Override
    protected float getScreenHeighRatio() {
        return 0.80f;
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }

    @Override
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }

}
