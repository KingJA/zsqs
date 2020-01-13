package com.kingja.zsqs.ui.dialog.offer;

import android.os.Bundle;
import android.view.View;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.view.dialog.BaseDialogFragment;

import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 下午 3:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OfferDialog extends BaseDialogFragment {

    private int progressId;

    @OnClick({R.id.sstv_confirm, R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.sstv_confirm:
                break;
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }

    public static OfferDialog newInstance(int progressId) {
        OfferDialog fragment = new OfferDialog();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.PROGRESSID, progressId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            progressId = getArguments().getInt(Constants.Extra.PROGRESSID);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_offer;
    }
}
