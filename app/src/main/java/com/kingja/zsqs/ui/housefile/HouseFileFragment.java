package com.kingja.zsqs.ui.housefile;

import android.os.Bundle;
import android.widget.GridView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.FileAdapter;
import com.kingja.zsqs.base.BaseHouseFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.FileInfo;
import com.kingja.zsqs.net.entiy.FileItem;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.dialog.PhotoPriviewFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 上午 9:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HouseFileFragment extends BaseHouseFragment implements HouseFileContract.View {
    @Inject
    HouseFilePresenter filePresenter;
    @BindView(R.id.fgv_file)
    GridView gvFile;
    private int fileType;
    private FileAdapter fileAdapter;
    private List<FileItem> fileList;

    @OnItemClick(R.id.fgv_file)
    void onItemClick(android.widget.AdapterView<?> adapterView, int postiion) {
        PhotoPriviewFragment.newInstance(fileList, postiion).show(getActivity());
    }

    //0：补偿款发放，1：房屋结算单
    public static HouseFileFragment newInstance(int fileType) {
        HouseFileFragment fragment = new HouseFileFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.FILE_TYPE, fileType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        super.initVariable();
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
        filePresenter.getHouseFileInfo(SpSir.getInstance().getString(SpSir.PROJECT_ID), SpSir.getInstance().getString(SpSir.HOUSE_ID), fileType);
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
        fileList = fileInfo.getFileList();
        setTitle(fileInfo.getTitle());
        setListView(fileList, fileAdapter);

    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
}

