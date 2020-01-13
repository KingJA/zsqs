package com.kingja.zsqs.ui.file;


import com.kingja.zsqs.base.BasePresenter;
import com.kingja.zsqs.base.BaseView;
import com.kingja.zsqs.net.entiy.FileInfo;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface FileContract {
    interface View extends BaseView {
        void onGetFileInfoSuccess(FileInfo fileInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void getFileInfo(String projectId, int fileType);
    }
}
