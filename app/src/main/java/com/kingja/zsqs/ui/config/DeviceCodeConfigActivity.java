package com.kingja.zsqs.ui.config;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.RuntimeABI;
import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.ProjectBaseInfo;
import com.kingja.zsqs.net.entiy.ProjectIdResult;
import com.kingja.zsqs.ui.main.MainActivity;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.GoUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.dialog.BaseTimerDialog;
import com.kingja.zsqs.view.dialog.DoubleDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:TODO
 * Create Time:2020/4/22 0022 下午 3:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DeviceCodeConfigActivity extends BaseActivity implements ProjectInfoContract.View, ProjectIdContract.View {
    @BindView(R.id.et_deviceCode)
    EditText etDeviceCode;
    @Inject
    ProjectInfoPresenter configPresenter;
    @Inject
    ProjectIdPresenter projectIdPresenter;
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
                String deviceCode = etDeviceCode.getText().toString().trim();
                if (CheckUtil.checkEmpty(deviceCode, "请输入设备编号")) {
                    projectIdPresenter.getProjectId(deviceCode);
                }

                break;

            case R.id.iv_close:
                etDeviceCode.setText("");
                ivClose.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void initVariable() {
        checkPermissions();
    }

    public void checkPermissions() {
        new RxPermissions(this)
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {

                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted && permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
                            // 用户已经同意该权限
//                            activeEngine();
                            Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });
    }

    public void activeEngine() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                try {
                    RuntimeABI runtimeABI = FaceEngine.getRuntimeABI();
                    Log.i(TAG, "subscribe: getRuntimeABI() " + runtimeABI);
                    long start = System.currentTimeMillis();
                    int activeCode = FaceEngine.activeOnline(DeviceCodeConfigActivity.this, Constants.APP_ID,
                            Constants.SDK_KEY);
                    Log.i(TAG, "subscribe cost: " + (System.currentTimeMillis() - start));
                    emitter.onNext(activeCode);
                } catch (Exception e) {
                    Log.i(TAG, "人脸识别模块初始化失败");
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            Log.e(TAG, "active_success: ");
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            Log.e(TAG, "already_activated: ");
                        } else {
                            Log.e(TAG, "active_failed: " + activeCode);
                        }
                        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
                        int res = FaceEngine.getActiveFileInfo(DeviceCodeConfigActivity.this, activeFileInfo);
                        if (res == ErrorInfo.MOK) {
                            Log.i(TAG, activeFileInfo.toString());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public View getContentView() {
        return View.inflate(this, R.layout.activity_devicecode_config, null);
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        configPresenter.attachView(this);
        projectIdPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        tvLimitCount.setText(String.format("%d/10", etDeviceCode.getText().toString().trim().length()));
    }

    @Override
    protected void initData() {
        etDeviceCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLimitCount.setText(String.format("%d/10", s.length()));
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

                GoUtil.goActivityAndFinish(DeviceCodeConfigActivity.this, MainActivity.class);

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

    @Override
    public void onGetProjectIdSuccess(ProjectIdResult projectIdResult) {
        SpSir.getInstance().setDeviceCode(projectIdResult.getDevice_code());
        SpSir.getInstance().setSceneAddress(projectIdResult.getScene_address());
        if (TextUtils.isEmpty(projectIdResult.getProject_id())) {
            ToastUtil.showText("项目ID未配置");
        } else {
            configPresenter.getProjectInfo(projectIdResult.getProject_id());
        }

    }
}
