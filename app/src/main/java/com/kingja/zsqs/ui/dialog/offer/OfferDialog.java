package com.kingja.zsqs.ui.dialog.offer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.view.dialog.BaseTimerDialog;
import com.kingja.zsqs.view.dialog.OfferResultDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 下午 3:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OfferDialog extends BaseTimerDialog implements OfferContract.View {

    @BindView(R.id.sset_userName)
    SuperShapeEditText ssetUserName;
    @BindView(R.id.sset_mobile)
    SuperShapeEditText ssetMobile;
    @BindView(R.id.sset_area)
    SuperShapeEditText ssetArea;
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;
    Unbinder unbinder;
    private int progressId;

    @Inject
    OfferPresenter offerPresenter;
    private String projectId="";
    private String houseId="";
    private String area;

    @OnClick({R.id.sstv_confirm, R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.sstv_confirm:
                String userName = ssetUserName.getText().toString().trim();
                String mobile = ssetMobile.getText().toString().trim();
                String area = ssetArea.getText().toString().trim();
                if (CheckUtil.checkEmpty(userName, "请输入姓名") &&
                        CheckUtil.checkPhoneFormat(mobile) &&
                        CheckUtil.checkEmpty(area, "请输入面积")) {
                    dismiss();
                    offerPresenter.decorateOffer(new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("progress_house_plan_id", String.valueOf(progressId))
                            .addFormDataPart("fwzs_project_id", SpSir.getInstance().getProjectId())
                            .addFormDataPart("fwzs_house_id", SpSir.getInstance().getHouseId())
                            .addFormDataPart("user_name", userName)
                            .addFormDataPart("mobile", mobile)
                            .addFormDataPart("area", area)
                            .build());
                }

                break;
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }



    public static OfferDialog newInstance(int progressId, String area) {
        OfferDialog fragment = new OfferDialog();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.PROGRESSID, progressId);
        args.putString(Constants.Extra.AREA, area);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
             progressId = getArguments().getInt(Constants.Extra.PROGRESSID);
            area = getArguments().getString(Constants.Extra.AREA, "");
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        offerPresenter.attachView(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String realName = SpSir.getInstance().getRealName();
        String mobile = SpSir.getInstance().getMobile();
        ssetArea.setText(area);
        if (!TextUtils.isEmpty(realName)) {
            ssetUserName.setText(realName);
        }
        if (!TextUtils.isEmpty(mobile)) {
            ssetMobile.setText(mobile);
        }
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_offer;
    }

    @Override
    public void onDecorateOfferSuccess(String price) {
        OfferResultDialog offerResultDialog = OfferResultDialog.newInstance(price, area,String.valueOf(progressId));
        offerResultDialog.show(mActivity);
    }


    @Override
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }
}
