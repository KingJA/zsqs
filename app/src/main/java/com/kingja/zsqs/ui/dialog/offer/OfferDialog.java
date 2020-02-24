package com.kingja.zsqs.ui.dialog.offer;

import android.os.Bundle;
import android.view.View;

import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.view.dialog.BaseDialogFragment;
import com.kingja.zsqs.view.dialog.DialogOfferResult;

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
public class OfferDialog extends BaseDialogFragment implements OfferContract.View {

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
    private String projectId;
    private String houseId;

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
                            .addFormDataPart("fwzs_project_id", projectId)
                            .addFormDataPart("fwzs_house_id", houseId)
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

    public static OfferDialog newInstance(int progressId) {
        OfferDialog fragment = new OfferDialog();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.PROGRESSID, progressId);
        fragment.setArguments(args);
        return fragment;
    }


    public static OfferDialog newInstance(String projectId, String houseId) {
        OfferDialog fragment = new OfferDialog();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.PROJECTID, projectId);
        args.putString(Constants.Extra.HOUSEID, houseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            progressId = getArguments().getInt(Constants.Extra.PROGRESSID);
            projectId = getArguments().getString(Constants.Extra.PROJECTID, "");
            houseId = getArguments().getString(Constants.Extra.HOUSEID, "");
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
        DialogOfferResult offerResultDialog = DialogOfferResult.newInstance(price, String.valueOf(progressId));
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
