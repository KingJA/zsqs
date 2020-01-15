package com.kingja.zsqs.ui.dialog.appoint;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface AppointContract {
    interface View extends BaseView {
        void onDecorateAppointSuccess(boolean success);
    }

    interface Presenter extends BasePresenter<View> {
        void decorateAppoint(RequestBody requestBody);
    }
}
