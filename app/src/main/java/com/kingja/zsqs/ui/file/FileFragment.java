package com.kingja.zsqs.ui.file;

import android.os.Bundle;
import android.widget.GridView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.FileAdapter;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.FileInfo;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 上午 9:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FileFragment extends BaseTitleFragment implements FileContract.View {
    @Inject
    FilePresenter filePresenter;
    @BindView(R.id.fgv_file)
    GridView gvFile;
    private int fileType;
    private FileAdapter fileAdapter;

    //11:补偿方案,39：房屋平面图，40：立项文件，41：项目公示文件，42：政策文件(征收决定),44:实施方案
    public static FileFragment newInstance(int fileType) {
        FileFragment fragment = new FileFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.FILE_TYPE, fileType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            fileType = getArguments().getInt(Constants.Extra.FILE_TYPE);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        filePresenter.attachView(this);
    }

    @Override
    protected void initView() {
        fileAdapter = new FileAdapter(getActivity(), null);
        gvFile.setAdapter(fileAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {
        filePresenter.getFileInfo(Constants.PROJECT_ID, fileType);
    }

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_file;
    }

    @Override
    public void onGetFileInfoSuccess(FileInfo fileInfo) {
        setTitle(fileInfo.getTitle());
        setListView(fileInfo.getProjectFileList(), fileAdapter);

    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
}

