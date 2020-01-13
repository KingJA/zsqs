package com.kingja.zsqs.ui.dialog.offer;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.ProjectDetail;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface OfferContract {
    interface View extends BaseView {
        void onDecorateOfferSuccess(String  price);
    }

    interface Presenter extends BasePresenter<View> {
        void decorateOffer(RequestBody requestBody);
    }
}
