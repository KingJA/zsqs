package com.kingja.zsqs.view.dialog;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.FilePreviewAdapter;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.IFile;
import com.kingja.zsqs.injector.component.AppComponent;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2020/1/22 0022 上午 8:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PhotoPriviewFragment extends BaseDialogFragment {
    @BindView(R.id.vp_files)
    ViewPager vpFiles;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    Unbinder unbinder;
    private List<IFile> fileList;
    private int currentPosition;

    @OnClick(R.id.iv_close)
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    public static PhotoPriviewFragment newInstance(List<? extends IFile> fileList, int position) {
        PhotoPriviewFragment fragment = new PhotoPriviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.Extra.FILE_LIST, (Serializable) fileList);
        args.putInt(Constants.Extra.POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            fileList = (List<IFile>) getArguments().getSerializable(Constants.Extra.FILE_LIST);
            currentPosition = getArguments().getInt(Constants.Extra.POSITION, 0);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        initFiles(fileList);
    }

    private void initFiles(List<IFile> fileList) {
        if (fileList == null || fileList.size() == 0) {
            return;
        }
        fillFiles(fileList);
    }


    private void fillFiles(List<IFile> fileList) {
        FilePreviewAdapter filePreviewAdapter = new FilePreviewAdapter(getActivity(), fileList);
        vpFiles.setAdapter(filePreviewAdapter);
        vpFiles.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                tvIndex.setText(String.format("%d/%d",position+1,fileList.size()));
            }
        });
        vpFiles.setCurrentItem(currentPosition);
        tvIndex.setText(String.format("%d/%d",1,fileList.size()));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_photo_priview;
    }

    @Override
    protected int getCountDownTimer() {
        return Constants.TIME_MILLISECOND.PREVIEW_CLOSE;
    }

    @Override
    protected float getScreenHeighRatio() {
        return 0.5f;
    }

    @Override
    protected float getScreenWidthRatio() {
        return 1.0f;
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }

}
