package com.kingja.zsqs.ui.file;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.FileInfo;
import com.kingja.zsqs.net.entiy.ProjectDetail;
import com.kingja.zsqs.ui.project.ProjectDetailContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FilePresenter implements FileContract.Presenter {
    private UserApi mApi;
    private FileContract.View mView;

    @Inject
    public FilePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getFileInfo(String projectId, int fileType) {
        mApi.getApiService().getFileInfo( projectId,  fileType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<FileInfo>(mView) {
                    @Override
                    protected void onSuccess(FileInfo fileInfo) {
                        mView.onGetFileInfoSuccess(fileInfo);
                    }
                });
    }

    @Override
    public void attachView(@NonNull FileContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}