package com.kingja.zsqs.ui.housefile;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.FileInfo;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface HouseFileContract {
    interface View extends BaseView {
        void onGetHouseFileInfoSuccess(FileInfo fileInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void getHouseFileInfo(String projectId, String houseId, int fileType);
    }
}
