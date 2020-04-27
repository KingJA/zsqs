package com.kingja.zsqs.threepart.idcard;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.huashi.otg.sdk.HSInterface;
import com.huashi.otg.sdk.HsOtgService;
import com.kingja.zsqs.R;
import com.kingja.zsqs.utils.SoundPlayer;
import com.kingja.zsqs.utils.ToastUtil;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:TODO
 * Create Time:2020/4/26 0026 上午 9:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IdcardSir {
    private Activity activity;
    private IdcardConnection idcardConnection;
    HSInterface hsInterface;
    private Timer timer;
    private TimerTask timerTask;
    private String TAG = getClass().getSimpleName();
    private OnIdcardAuthListener onIdcardAuthListener;

    public IdcardSir(Activity activity) {
        this.activity = activity;
    }

    public void init(OnIdcardAuthListener onIdcardAuthListener) {
        this.onIdcardAuthListener = onIdcardAuthListener;
        /*1.连接读卡器服务*/
        idcardConnection = new IdcardConnection();
        Objects.requireNonNull(activity).bindService(new Intent(activity, HsOtgService.class), idcardConnection,
                Service.BIND_AUTO_CREATE);
        /*2.开启定时器进行卡认证*/
    }

    public void startAuth() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e(TAG, "身份证认证中: ");
                if (hsInterface != null) {
                    Objects.requireNonNull(activity).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            auth(hsInterface.Authenticate());
                        }
                    });
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    private void auth(int authCode) {
        if (authCode == 1) {
            /*认证成功，停止认证*/
            stopAuthTimer();
            Log.e(TAG, "卡认证成功开始读卡: ");
            int ret = hsInterface.ReadCard();
            if (ret == 1) {
                SoundPlayer.getInstance().playVoice(R.raw.success);
                if (onIdcardAuthListener != null) {
                    onIdcardAuthListener.onIdcardAuthSuccess(HsOtgService.ic.getIDCard());
                }
                Log.e(TAG, "IDCard: " + HsOtgService.ic.getIDCard());
            } else {
                ToastUtil.showText("读卡异常");
            }
        } else if (authCode == 2) {
            Log.e(TAG, "卡认证失败");
        } else if (authCode == 0) {
            Log.e(TAG, "未连接");
        }
    }

    public void stopAuthTimer() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    public void onDestroy() {
        stopAuthTimer();
        hsInterface.unInit();
        Objects.requireNonNull(activity).unbindService(idcardConnection);
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
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            idcardConnection = null;
            hsInterface = null;
        }
    }

    public interface OnIdcardAuthListener {
        void onIdcardAuthSuccess(String idcard);
    }

}
