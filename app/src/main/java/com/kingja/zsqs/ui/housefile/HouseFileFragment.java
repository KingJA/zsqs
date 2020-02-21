package com.kingja.zsqs.ui.housefile;

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
public class HouseFileFragment extends BaseTitleFragment implements HouseFileContract.View {
    @Inject
    HouseFilePresenter filePresenter;
    @BindView(R.id.fgv_file)
    GridView gvFile;
    private int fileType;
    private FileAdapter fileAdapter;
    private String projectId;
    private String houseId;

    //0：补偿款发放，1：房屋结算单
    public static HouseFileFragment newInstance(String projectId, String houseId, int fileType) {
        HouseFileFragment fragment = new HouseFileFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.PROJECTID, projectId);
        args.putString(Constants.Extra.HOUSEID, houseId);
        args.putInt(Constants.Extra.FILE_TYPE, fileType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            fileType = getArguments().getInt(Constants.Extra.FILE_TYPE);
            projectId = getArguments().getString(Constants.Extra.PROJECTID, "");
            houseId = getArguments().getString(Constants.Extra.HOUSEID, "");
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
        filePresenter.getHouseFileInfo(projectId, houseId, fileType);
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
    public void onGetHouseFileInfoSuccess(FileInfo fileInfo) {
        setTitle(fileInfo.getTitle());
        setListView(fileInfo.getFileList(), fileAdapter);

    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
}

