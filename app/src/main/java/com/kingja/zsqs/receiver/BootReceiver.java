package com.kingja.zsqs.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kingja.zsqs.ui.main.MainActivity;

/**
 * Description:TODO
 * Create Time:2020/1/16 0016 上午 8:46
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}