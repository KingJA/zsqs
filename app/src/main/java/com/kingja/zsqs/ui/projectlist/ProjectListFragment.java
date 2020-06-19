package com.kingja.zsqs.ui.projectlist;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kingja.zsqs.CommonAdapter;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.callback.EmptyCallback;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.ProjectItem;
import com.kingja.zsqs.ui.home.HomeFragment;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.dialog.ListDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/6/18 0018 下午 3:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectListFragment extends BaseTitleFragment implements ProjectListContract.View {
    @Inject
    ProjectListPresenter projectListPresenter;
    @BindView(R.id.tv_projectYear)
    TextView tvProjectYear;
    @BindView(R.id.flv_project)
    ListView flvProject;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private int regionId;
    private String year = "0";
    private CommonAdapter<ProjectItem> commonAdapter;
    private ListDialog yearSelector;

    @OnClick({R.id.ssll_yearSelector, R.id.ssll_query})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_yearSelector:
                yearSelector.show();
                break;
            case R.id.ssll_query:
                initNet();
                break;
        }
    }

    public static ProjectListFragment newInstance(int regionId) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.ID, regionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            regionId = getArguments().getInt(Constants.Extra.ID);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        projectListPresenter.attachView(this);
    }

    @Override
    protected View getNewLoadSirView() {
        return llRoot;
    }

    @Override
    protected void initView() {
        commonAdapter = new CommonAdapter<ProjectItem>(getActivity(), null,
                R.layout.item_project) {
            @Override
            public void convert(ViewHolder helper, ProjectItem item) {
                helper.setText(R.id.tv_projectName, item.getProjectName());
                helper.setText(R.id.tv_address, item.getAddress());
                helper.setText(R.id.tv_totalBuilding, item.getTotalBuilding());
                helper.setOnClickListen(R.id.sstv_view, v -> {
                    SpSir.getInstance().setProjectId(item.getProjectId());
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new HomeFragment());

                });
            }
        };
        flvProject.setAdapter(commonAdapter);

    }

    @Override
    protected void initData() {
        initYearSpinner();

    }

    private void initYearSpinner() {
        List<String> yearList = new ArrayList<>();
        Calendar cd = Calendar.getInstance();
        int currentYear = cd.get(Calendar.YEAR);
        for (int i = 0; i < 20; i++) {
            yearList.add(String.valueOf(currentYear - i));
        }

        yearSelector = new ListDialog.Builder<String>(getActivity())
                .setTitle("请选择年份")
                .setList(yearList)
                .setSelectable(true)
                .setConvertor((ListDialog.IConvertor<String>) tag -> tag)
                .setOnItemClickListener((ListDialog.OnItemClickListener<String>) tag -> {
                    tvProjectYear.setText(tag);
                    year = tag;

                })
                .build();
    }

    @Override
    public void initNet() {
        projectListPresenter.GetProjectList(regionId, year);
    }

    @Override
    protected String getTitle() {
        return "项目列表";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_project_list;
    }

    @Override
    public void onGetProjectListSuccess(List<ProjectItem> projectList) {
        if (projectList != null) {
            if (projectList.size() > 0) {
                showSuccessCallback();
                commonAdapter.setData(projectList);
            } else {
                mBaseLoadService.setCallBack(EmptyCallback.class, (context, view) -> {
                    TextView tvTip = view.findViewById(R.id.tv_tip);
                    tvTip.setText("您好！暂无该年度项目信息");
                });
                showEmptyCallback();
            }
        }
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
}
