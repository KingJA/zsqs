package com.kingja.zsqs.ui.dialog.appoint;

import android.view.View;

import com.kingja.zsqs.R;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.view.dialog.BaseDialogFragment;
import com.kingja.zsqs.ui.dialog.offer.OfferDialog;

import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 下午 3:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AppointDialog extends BaseDialogFragment {
    @OnClick({R.id.sstv_confirm, R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.sstv_confirm:
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                dismiss();

                OfferDialog offerDialog = new OfferDialog();
                offerDialog.show(getActivity());
                break;
            case R.id.ssll_dismiss:
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
                dismiss();
                break;
        }
    }

    @Override
    protected void initVariable() {

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
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_appoint;
    }
}
