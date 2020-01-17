package com.kingja.zsqs.ui.login;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.LoginInfo;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface LoginContract {
    interface View extends BaseView {
        void onLoginSuccess(LoginInfo loginInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String projectId, String idcard);
    }
}
