package com.kingja.zsqs.view.dialog;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.BannerAdapter;
import com.kingja.zsqs.adapter.FilePreviewAdapter;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.IFile;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.utils.AppUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2020/1/22 0022 上午 8:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PhotoPriviewFragment extends BaseDialogFragment {
    @BindView(R.id.vp_files)
    ViewPager vpFiles;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    private List<IFile> fileList;
    private int currentPosition;
    private List<View> points = new ArrayList<>();

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
        if (fileList.size() > 1) {
            initDots(fileList);
        }
    }

    private void initDots(List<IFile> fileList) {
        for (int i = 0; i < fileList.size(); i++) {
            View view = new View(getActivity());
            if (i == currentPosition) {
                view.setBackgroundResource(R.mipmap.ic_dot_sel);
            } else {
                view.setBackgroundResource(R.mipmap.ic_dot_nor);
            }
            points.add(view);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AppUtil.dp2px(16), AppUtil.dp2px(16));
        layoutParams.setMargins(0, 0, AppUtil.dp2px(16), 0);
        for (int i = 0; i < fileList.size(); i++) {
            llDot.addView(points.get(i), layoutParams);
        }
    }

    private void fillFiles(List<IFile> fileList) {
        FilePreviewAdapter filePreviewAdapter = new FilePreviewAdapter(getActivity(), fileList);
        vpFiles.setAdapter(filePreviewAdapter);
        vpFiles.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                if (fileList.size() < 2) {
                    return;
                }
                for (int i = 0; i < points.size(); i++) {
                    if (i == position ) {
                        points.get(i).setBackgroundResource(R.mipmap.ic_dot_sel);
                    } else {
                        points.get(i).setBackgroundResource(R.mipmap.ic_dot_nor);
                    }
                }
            }
        });
        vpFiles.setCurrentItem(currentPosition);
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
