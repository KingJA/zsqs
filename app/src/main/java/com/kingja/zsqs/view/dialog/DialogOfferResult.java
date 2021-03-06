package com.kingja.zsqs.view.dialog;

import android.os.Bundle;
import android.view.View;

import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.ui.dialog.appoint.AppointDialog;
import com.kingja.zsqs.view.StringTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/14 0014 上午 9:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DialogOfferResult extends BaseDialogFragment {

    @BindView(R.id.tv_price)
    StringTextView tvPrice;
    private String price;
    private String progressId;

    @OnClick({R.id.sstv_confirm, R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.sstv_confirm:
                dismiss();
                AppointDialog.newInstance(String.valueOf(progressId)).show(mActivity);
                break;
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }

    public static DialogOfferResult newInstance(String price, String progressId) {
        DialogOfferResult fragment = new DialogOfferResult();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.PRICE, price);
        args.putString(Constants.Extra.PROGRESSID, progressId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            price = getArguments().getString(Constants.Extra.PRICE);
            progressId = getArguments().getString(Constants.Extra.PROGRESSID);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvPrice.setText(price);
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_offer_result;
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }
}
