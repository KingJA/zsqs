package com.kingja.zsqs.utils;

import android.app.Activity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:TODO
 * Create Time:2020/5/11 0011 下午 2:51
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TimerSir {
    private  final String TAG ="TimerSir" ;
    private Activity activity;
    private Runnable runnable;
    private long period;
    private Timer dateTimer;
    private TimerTask timerTask;

    public TimerSir(Activity activity, Runnable runnable,long period) {
        this.activity = activity;
        this.runnable = runnable;
        this.period = period;
    }

    public void startTimer() {
        stopTimer();
        dateTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(runnable);
            }
        };
        dateTimer.schedule(timerTask, 0, period);
    }

    public void stopTimer() {
        if (dateTimer != null) {
            dateTimer.cancel();
            dateTimer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
