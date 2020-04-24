package com.kingja.zsqs.ui.login;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.huashi.otg.sdk.HSInterface;
import com.huashi.otg.sdk.HsOtgService;
import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.LoginInfo;
import com.kingja.zsqs.service.houses.HousesListService;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.SoundPlayer;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.dialog.DoubleDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/16 0016 上午 10:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginFragment extends BaseTitleFragment implements LoginContract.View {
    @BindView(R.id.sset_id)
    SuperShapeEditText ssetId;
    @Inject
    LoginPresenter loginPresenter;
    private Intent idcardService;
    private IdcardConnection idcardConnection;
    HSInterface hsInterface;
    private Timer timer;
    private TimerTask timerTask;

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.iv_four, R.id.iv_five, R.id.iv_six, R.id.iv_seven,
            R.id.iv_eight, R.id.iv_nine, R.id.iv_zero, R.id.iv_delete, R.id.iv_empty, R.id.iv_confirm,
            R.id.sstv_face_login})
    void onClick(View v) {
        String id;
        SoundPlayer.getInstance().playVoice(R.raw.btn01);
        switch (v.getId()) {
            case R.id.sstv_face_login:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStackAndOutLast(new LoginByFaceFragment()
                        , this);
//                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new LoginByFaceFragment());
                break;
            case R.id.iv_confirm:
                id = ssetId.getText().toString().trim();
                if (CheckUtil.checkEmpty(id, "请输入或者扫描证件")) {
                    loginPresenter.login(SpSir.getInstance().getProjectId(), id);
                }
                break;
            case R.id.iv_empty:
                ssetId.setText("");
                break;
            case R.id.iv_delete:
                id = ssetId.getText().toString().trim();
                int length = id.length();
                if (length > 0) {
                    ssetId.setText(id.substring(0, length - 1));
                    ssetId.setSelection(ssetId.getText().length());
                }
                break;
            case R.id.iv_zero:
                input("0");
                break;
            case R.id.iv_one:
                input("1");
                break;
            case R.id.iv_two:
                input("2");
                break;
            case R.id.iv_three:
                input("3");
                break;
            case R.id.iv_four:
                input("4");
                break;
            case R.id.iv_five:
                input("5");
                break;
            case R.id.iv_six:
                input("6");
                break;
            case R.id.iv_seven:
                input("7");
                break;
            case R.id.iv_eight:
                input("8");
                break;
            case R.id.iv_nine:
                input("9");
                break;
        }
    }

    public void input(String number) {
        String content = ssetId.getText().toString().trim();
        ssetId.setText(content + number);
        ssetId.setSelection(ssetId.getText().length());
    }


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        loginPresenter.attachView(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        /*1.连接读卡器服务*/
        idcardService = new Intent(getActivity(), HsOtgService.class);
        idcardConnection = new IdcardConnection();
        Objects.requireNonNull(getActivity()).bindService(idcardService, idcardConnection, Service.BIND_AUTO_CREATE);
        /*2.开启定时器进行卡认证*/
    }

    @Override
    public void onStart() {
        super.onStart();
        startAuthTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopAuthTimer();
    }

    private void startAuthTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e(TAG, "身份证认证中: ");
                if (hsInterface!= null) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            auth(hsInterface.Authenticate());
                        }
                    });
                }
            }
        };
        timer.schedule(timerTask, 1000, 2000);
    }

    private void auth(int authCode) {
        if (authCode == 1) {
            /*认证成功，停止认证*/
            stopAuthTimer();
            Log.e(TAG, "卡认证成功开始读卡: ");
            int ret = hsInterface.ReadCard();
            if (ret == 1) {
                DoubleDialog confirmIdcardDialog = DoubleDialog.newInstance(String.format("您绑定的身份证号码为:\n%s",
                        HsOtgService.ic.getIDCard()), "确定");
                confirmIdcardDialog.setOnConfirmListener(() -> loginPresenter.login(SpSir.getInstance().getProjectId(),HsOtgService.ic.getIDCard()));
                confirmIdcardDialog.setOnCancelListener(this::startAuthTimer);
                confirmIdcardDialog.show(getActivity());
                Log.e(TAG, "getIDCard: " + HsOtgService.ic.getIDCard());
            } else {
                ToastUtil.showText("读卡异常");
            }
        } else if (authCode == 2) {
            Log.e(TAG, "卡认证失败");
        } else if (authCode == 0) {
            Log.e(TAG, "未连接" );
        }
    }

    private void stopAuthTimer() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    class IdcardConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: ");
            hsInterface = (HSInterface) service;
            int i = 2;
            while (i > 0) {
                i--;
                int ret = hsInterface.init();
                if (ret == 1) {
                    i = 0;
                    Log.e(TAG, "连接成功: ");
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            idcardConnection = null;
            hsInterface = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAuthTimer();
        hsInterface.unInit();
        Objects.requireNonNull(getActivity()).unbindService(idcardConnection);
    }

    @Override
    public void initNet() {

    }

    @Override
    protected String getTitle() {
        return "登录";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_login;
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return false;
    }

    @Override
    public void onLoginSuccess(LoginInfo loginInfo) {
        SpSir.getInstance().putString(SpSir.REALNAME, loginInfo.getRealName());
        SpSir.getInstance().putString(SpSir.MOBILE, loginInfo.getMobilePhone());
        SpSir.getInstance().setIdcard(loginInfo.getIdcard());
        ToastUtil.showText("登录成功");
        EventBus.getDefault().post(new LoginStatusEvent(true));
        ((IStackActivity) Objects.requireNonNull(getActivity())).outStack(this);
        mFragmentActivity.startService(new Intent(mFragmentActivity, HousesListService.class));
    }
}
