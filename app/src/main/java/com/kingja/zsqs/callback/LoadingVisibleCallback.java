package com.kingja.zsqs.callback;

import com.kingja.loadsir.callback.Callback;
import com.kingja.zsqs.R;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadingVisibleCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return true;
    }
}
