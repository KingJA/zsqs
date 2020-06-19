package com.kingja.zsqs.ui.home;

import android.text.TextUtils;
import android.util.Log;

import com.kingja.zsqs.CommonAdapter;
import com.kingja.zsqs.NavItem;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.base.BaseFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.Area;
import com.kingja.zsqs.net.entiy.HomeConfig;
import com.kingja.zsqs.ui.affirm.ResultFragment;
import com.kingja.zsqs.ui.file.FileFragment;
import com.kingja.zsqs.ui.housefile.HouseFileFragment;
import com.kingja.zsqs.ui.login.LoginFragment;
import com.kingja.zsqs.ui.placement.list.PlacementListFragment;
import com.kingja.zsqs.ui.project.ProjectDetailFragment;
import com.kingja.zsqs.ui.projectlist.ProjectListFragment;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.FixedGridView;
import com.kingja.zsqs.view.dialog.DoubleDialog;
import com.kingja.zsqs.view.dialog.HouseSelectDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 上午 9:07
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AreaFragment extends BaseFragment implements HomeContract.View, AreaContract.View {
    @BindView(R.id.fgv_nav)
    FixedGridView fgvNav;

    @Inject
    HomePresenter homePresenter;
    @Inject
    AreaPresenter areaPresenter;

    @OnItemClick(R.id.fgv_nav)
    void onItemClick(int position, android.widget.AdapterView<?> adapterView) {
        Area item = (Area) adapterView.getItemAtPosition(position);
        ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(ProjectListFragment.newInstance(item.getRegion_id()));
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        homePresenter.attachView(this);
        areaPresenter.attachView(this);
    }

    @Override
    protected void initView() {


    }


    @Override
    public void initNet() {
        homePresenter.getHomeConfig(SpSir.getInstance().getDeviceCode());
        areaPresenter.getAreaList(SpSir.getInstance().getDeviceCode());
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_area;
    }


    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void onGetHomeConfigSuccess(HomeConfig homeConfig) {
        int idCardEnterable = homeConfig.getId_card_enterable();
        SpSir.getInstance().setProjectId(homeConfig.getProject_id());
        SpSir.getInstance().setIdcardInputable(idCardEnterable==1);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void onGetAreaList(List<Area> areaList) {
        fgvNav.setAdapter(new CommonAdapter<Area>(getActivity(), areaList,
                R.layout.item_area) {
            @Override
            public void convert(ViewHolder helper, Area item) {
                helper.setImageByUrl(R.id.iv, Constants.BASE_FWCQ_IMG_URL+item.getIcon_url());
            }
        });
    }
}
