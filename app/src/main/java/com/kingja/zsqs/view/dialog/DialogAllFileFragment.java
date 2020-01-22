package com.kingja.zsqs.view.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.IFileAdapter;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.IFile;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.view.StringTextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2020/1/22 0022 下午 2:41
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DialogAllFileFragment extends BaseDialogFragment {
    @BindView(R.id.gv_file)
    GridView gvFile;
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;
    private List<IFile> fileList;

    @OnItemClick(R.id.gv_file)
    void onItemClick(AdapterView<?> adapterView, int postiion) {
        PhotoPriviewFragment.newInstance(fileList,postiion).show(getActivity());
    }

    @OnClick({R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }


    public static DialogAllFileFragment newInstance(List<? extends IFile> fileList) {
        DialogAllFileFragment fragment = new DialogAllFileFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.Extra.FILE_LIST, (Serializable) fileList);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            fileList = (List<IFile>) getArguments().getSerializable(Constants.Extra.FILE_LIST);
        }
    }


    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        gvFile.setAdapter(new IFileAdapter(getActivity(), fileList));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_all_file;
    }

    @Override
    protected float getScreenWidthRatio() {
        return 0.8f;
    }

    @Override
    protected float getScreenHeighRatio() {
        return 0.60f;
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }

    @Override
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }
}
