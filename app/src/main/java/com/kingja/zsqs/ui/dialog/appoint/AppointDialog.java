package com.kingja.zsqs.ui.dialog.appoint;

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
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.view.dialog.AppointSuccessDialog;
import com.kingja.zsqs.view.dialog.BaseTimerDialog;

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
public class AppointDialog extends BaseTimerDialog implements AppointContract.View {
    @Inject
    AppointPresenter appointPresenter;
    @BindView(R.id.sset_userName)
    SuperShapeEditText ssetUserName;
    @BindView(R.id.sset_mobile)
    SuperShapeEditText ssetMobile;
    @BindView(R.id.sset_area)
    SuperShapeEditText ssetArea;
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;
    Unbinder unbinder;
    private String progressId;
    private String projectId;
    private String houseId;
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
                    appointPresenter.decorateAppoint(new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("user_name", userName)
                            .addFormDataPart("mobile", mobile)
                            .addFormDataPart("area", area)
                            .addFormDataPart("budget", String.valueOf(1))
                            .addFormDataPart("progress_house_plan_id", progressId)
                            .addFormDataPart("fwzs_project_id", projectId)
                            .addFormDataPart("fwzs_house_id", houseId)
                            .build());
                }
                break;
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }

    public static AppointDialog newInstance(String projectId, String houseId, String progressId,String area) {
        AppointDialog fragment = new AppointDialog();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.PROJECTID, projectId);
        args.putString(Constants.Extra.HOUSEID, houseId);
        args.putString(Constants.Extra.PROGRESSID, progressId);
        args.putString(Constants.Extra.AREA, area);
        fragment.setArguments(args);
        return fragment;
    }

    public static AppointDialog newInstance(String projectId, String houseId,String area) {
        return newInstance(projectId, houseId, "", area);
    }

    public static AppointDialog newInstance(String progressId,String area) {
        return newInstance("", "", progressId,area);
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            progressId = getArguments().getString(Constants.Extra.PROGRESSID, "");
            projectId = getArguments().getString(Constants.Extra.PROJECTID, "");
            houseId = getArguments().getString(Constants.Extra.HOUSEID, "");
            area = getArguments().getString(Constants.Extra.AREA, "");
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        appointPresenter.attachView(this);
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
        return R.layout.dialog_appoint;
    }

    @Override
    public void onDecorateAppointSuccess(boolean success) {
        new AppointSuccessDialog().show(mActivity);
    }




    @Override
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }

}
