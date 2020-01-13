package com.kingja.zsqs.utils;

import android.widget.Toast;

import com.kingja.zsqs.base.App;


/**
 * Description：TODO
 * Create Time：2016/8/4 17:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showText(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }


}
