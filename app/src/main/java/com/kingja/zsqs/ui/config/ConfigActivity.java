package com.kingja.zsqs.ui.config;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.ProjectBaseInfo;
import com.kingja.zsqs.ui.main.MainActivity;
import com.kingja.zsqs.utils.GoUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.dialog.BaseTimerDialog;
import com.kingja.zsqs.view.dialog.DoubleDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/4/22 0022 下午 3:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ConfigActivity extends BaseActivity implements ConfigContract.View {
    @BindView(R.id.et_projectId)
    EditText etProjectId;
    @Inject
    ConfigPresenter configPresenter;
    @BindView(R.id.tv_limitCount)
    TextView tvLimitCount;
    @BindView(R.id.sstv_confirm)
    SuperShapeTextView sstvConfirm;
    @BindView(R.id.iv_close)
    ImageView ivClose;


    @OnClick({R.id.sstv_confirm, R.id.iv_close})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.sstv_confirm:
                String projectId = etProjectId.getText().toString().trim();
                if (TextUtils.isEmpty(projectId)) {
                    ToastUtil.showText("请输入项目Id");
                    return;
                }
                configPresenter.getProjectInfo(projectId);
                break;

            case R.id.iv_close:
                etProjectId.setText("");
                ivClose.setVisibility(View.GONE);

                break;
        }
    }


    @Override
    public void initVariable() {

    }

    @Override
    public View getContentView() {
        return View.inflate(this, R.layout.activity_config, null);
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        configPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        tvLimitCount.setText(String.format("%d/36", etProjectId.getText().toString().trim().length()));
    }

    @Override
    protected void initData() {
        etProjectId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLimitCount.setText(String.format("%d/36", s.length()));
            }
        });
    }

    @Override
    public void initNet() {

    }

    @Override
    public void onGetProjectInfoSuccess(ProjectBaseInfo projectBaseInfo) {
        DoubleDialog confirmDialog = DoubleDialog.newInstance(String.format("您要配置项目是\n%s\n%s",
                projectBaseInfo.getProjectName(), projectBaseInfo.getAddress()), "确定");
        confirmDialog.setOnConfirmListener(new BaseTimerDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                SpSir.getInstance().setProjectId(projectBaseInfo.getProjectId());
                GoUtil.goActivityAndFinish(ConfigActivity.this, MainActivity.class);

            }
        });
        confirmDialog.show(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
