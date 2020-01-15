package com.kingja.zsqs.ui.project;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.ProjectDetail;
import com.kingja.zsqs.view.StringTextView;

import javax.inject.Inject;

import butterknife.BindView;

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
        projectDetailPresenter.getDecorationDetail("e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a");
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
      tvTotalBuildingCount.setString(projectDetail.getTotalBuildingCount()+"户");
      tvHouseTotalBuildingCount.setString(projectDetail.getHouseTotalBuildingCount()+"户");
      tvEntTotalBuildingCount.setString(projectDetail.getEntTotalBuildingCount()+"户");
      tvTotalBuildingArea.setString(projectDetail.getTotalBuildingArea()+"㎡");
      tvHouseTotalBuildingArea.setString(projectDetail.getHouseTotalBuildingArea()+"㎡");
      tvEntTotalBuildingArea.setString(projectDetail.getEntTotalBuildingArea()+"㎡");
      tvImplementor.setString(projectDetail.getImplementor());
      tvAgent.setString(projectDetail.getAgent());
      tvMapper.setString(projectDetail.getMapper());
      tvIdentifier.setString(projectDetail.getIdentifier());
      tvEvaluator.setString(projectDetail.getEvaluator());
      tvAreaRange.setString(projectDetail.getAreaRange());
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

}
