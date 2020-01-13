package com.kingja.zsqs.callback;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kingja.zsqs.R;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class UnLoginCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_unlogin;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
