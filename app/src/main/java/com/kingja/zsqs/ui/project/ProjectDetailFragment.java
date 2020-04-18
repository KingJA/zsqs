package com.kingja.zsqs.ui.project;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.FilePageAdapter;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.FileInfo;
import com.kingja.zsqs.net.entiy.FileItem;
import com.kingja.zsqs.net.entiy.ProjectDetail;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.view.dialog.DialogAllFileFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 下午 2:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectDetailFragment extends BaseTitleFragment implements ProjectDetailContract.View {
    @Inject
    ProjectDetailPresenter projectDetailPresenter;
    @BindView(R.id.tv_projectName)
    StringTextView tvProjectName;
    @BindView(R.id.tv_areaName)
    StringTextView tvAreaName;
    @BindView(R.id.tv_address)
    StringTextView tvAddress;
    @BindView(R.id.tv_totalBuildingCount)
    StringTextView tvTotalBuildingCount;
    @BindView(R.id.tv_houseTotalBuildingCount)
    StringTextView tvHouseTotalBuildingCount;
    @BindView(R.id.tv_entTotalBuildingCount)
    StringTextView tvEntTotalBuildingCount;
    @BindView(R.id.tv_totalBuildingArea)
    StringTextView tvTotalBuildingArea;
    @BindView(R.id.tv_houseTotalBuildingArea)
    StringTextView tvHouseTotalBuildingArea;
    @BindView(R.id.tv_entTotalBuildingArea)
    StringTextView tvEntTotalBuildingArea;
    @BindView(R.id.tv_implementor)
    StringTextView tvImplementor;
    @BindView(R.id.tv_agent)
    StringTextView tvAgent;
    @BindView(R.id.tv_mapper)
    StringTextView tvMapper;
    @BindView(R.id.tv_identifier)
    StringTextView tvIdentifier;
    @BindView(R.id.tv_evaluator)
    StringTextView tvEvaluator;
    @BindView(R.id.tv_areaRange)
    StringTextView tvAreaRange;
    @BindView(R.id.vp_files)
    ViewPager vpFiles;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.sstv_index)
    SuperShapeTextView sstvIndex;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        projectDetailPresenter.attachView(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {
        projectDetailPresenter.getDecorationDetail(SpSir.getInstance().getString(SpSir.PROJECT_ID));
    }

    @Override
    protected String getTitle() {
        return "项目概况";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_project_detail;
    }

    @Override
    public void onGetDecorationDetailSuccess(ProjectDetail projectDetail) {
        tvProjectName.setString(projectDetail.getProjectName());
        tvAreaName.setString(projectDetail.getAreaName());
        tvAddress.setString(projectDetail.getAddress());
        tvTotalBuildingCount.setString(projectDetail.getTotalBuildingCount() + "户");
        tvHouseTotalBuildingCount.setString(projectDetail.getHouseTotalBuildingCount() + "户");
        tvEntTotalBuildingCount.setString(projectDetail.getEntTotalBuildingCount() + "户");
        tvTotalBuildingArea.setString(projectDetail.getTotalBuildingArea() + "㎡");
        tvHouseTotalBuildingArea.setString(projectDetail.getHouseTotalBuildingArea() + "㎡");
        tvEntTotalBuildingArea.setString(projectDetail.getEntTotalBuildingArea() + "㎡");
        tvImplementor.setString(projectDetail.getImplementor());
        tvAgent.setString(projectDetail.getAgent());
        tvMapper.setString(projectDetail.getMapper());
        tvIdentifier.setString(projectDetail.getIdentifier());
        tvEvaluator.setString(projectDetail.getEvaluator());
        tvAreaRange.setString(projectDetail.getAreaRange());
        List<FileItem> fileList = projectDetail.getRedLineFile();
        if (fileList != null && fileList.size() > 0) {
            FilePageAdapter filePageAdapter = new FilePageAdapter(getActivity(), fileList);
            vpFiles.setAdapter(filePageAdapter);
            sstvIndex.setText(String.format("1/%d",fileList.size()));
            vpFiles.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                    sstvIndex.setText(String.format("1/%d",position+1));
                }
            });
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            sstvIndex.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
}
