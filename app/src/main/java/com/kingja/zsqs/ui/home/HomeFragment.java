package com.kingja.zsqs.ui.home;

import android.support.v4.app.FragmentActivity;
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
import com.kingja.zsqs.net.entiy.HomeConfig;
import com.kingja.zsqs.ui.affirm.ResultFragment;
import com.kingja.zsqs.ui.file.FileFragment;
import com.kingja.zsqs.ui.housefile.HouseFileFragment;
import com.kingja.zsqs.ui.login.LoginFragment;
import com.kingja.zsqs.ui.placement.list.PlacementListFragment;
import com.kingja.zsqs.ui.project.ProjectDetailFragment;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.FixedGridView;
import com.kingja.zsqs.view.dialog.DialogDouble;
import com.kingja.zsqs.view.dialog.DialogHouseSelect;
import com.kingja.zsqs.view.dialog.PhotoPriviewFragment;

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
public class HomeFragment extends BaseFragment implements HomeContract.View {
    @BindView(R.id.fgv_nav)
    FixedGridView fgvNav;

    @Inject
    HomePresenter homePresenter;

    @OnItemClick(R.id.fgv_nav)
    void onItemClick(int position, android.widget.AdapterView<?> adapterView) {

        switch (position) {
            case Constants.RouterCode.XIANGMUGAIKUANG:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new ProjectDetailFragment());
                break;
            case Constants.RouterCode.ZHENGSHOUJUEDING:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(FileFragment.newInstance(Constants.CODE_FILETYPE.ZHENGSHOUJUEDING));
                break;
            case Constants.RouterCode.BUCHANGFANGAN:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(FileFragment.newInstance(Constants.CODE_FILETYPE.BUCHANGFANGAN));
                break;
            case Constants.RouterCode.GONGSHIGONGGAO:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(FileFragment.newInstance(Constants.CODE_FILETYPE.GONGSHIGONGGAO));
                break;
            case Constants.RouterCode.DIAOCHAJIEGUO:
                if (checkEnterable()) {
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(ResultFragment.newInstance(Constants.CODE_RESULTTYPE.DIAOCHA));
                }
                break;
            case Constants.RouterCode.RENDINGJIEGUO:
                if (checkEnterable()) {
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(ResultFragment.newInstance(Constants.CODE_RESULTTYPE.RENDING));
                }
                break;
            case Constants.RouterCode.PINGGUJIEGUO:
                if (checkEnterable()) {
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(ResultFragment.newInstance(Constants.CODE_RESULTTYPE.PINGGU));
                }
                break;
            case Constants.RouterCode.FANGWUXIEYI:
                if (checkEnterable()) {
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(ResultFragment.newInstance(Constants.CODE_RESULTTYPE.XIEYI));
                }
                break;
            case Constants.RouterCode.BUCHAGNKUANFAFANG:
                if (checkEnterable()) {
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(HouseFileFragment.newInstance(Constants.CODE_HOUSEFILETYPE.BUCHANGKUANFAFANG));
                }
                break;
            case Constants.RouterCode.FANGWUJIESUANDAN:
                if (checkEnterable()) {
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(HouseFileFragment.newInstance(Constants.CODE_HOUSEFILETYPE.FANGWUJIESUANDAN));
                }
                break;
            case Constants.RouterCode.ANZHIHUXING:
                if (checkEnterable()) {
                    ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new PlacementListFragment());
                }
                break;
        }
    }

    public boolean checkEnterable() {
        String idcard = SpSir.getInstance().getString(SpSir.IDCARD);
        if (TextUtils.isEmpty(idcard)) {
            //去登陆
            DialogDouble loginDialog = DialogDouble.newInstance("您还未登录，是否马上登录", "去登陆");
            loginDialog.setOnConfirmListener(() -> {
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new LoginFragment());
            });
            loginDialog.show(getActivity());
            return false;
        }
        int houseSelectType = SpSir.getInstance().getInt(SpSir.HOUSE_SELECT_TYPE);
        if (houseSelectType == Constants.HOUSE_SELECT_TYPE.NONE) {
            ToastUtil.showText("用户无关联房产");
            return false;
        }
        String houseId = SpSir.getInstance().getString(SpSir.HOUSE_ID);
        if (houseSelectType == Constants.HOUSE_SELECT_TYPE.MUL && TextUtils.isEmpty(houseId)) {
            new DialogHouseSelect().show(getActivity());
            return false;
        }
        return true;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        homePresenter.attachView(this);
    }

    @Override
    protected void initView() {

        List<NavItem> navItemList = new ArrayList<>();
        navItemList.add(new NavItem(R.mipmap.ic_item_survey, "项目概况"));
        navItemList.add(new NavItem(R.mipmap.ic_a_decision, "征收决定"));
        navItemList.add(new NavItem(R.mipmap.ic_compensation_plan, "补偿方案"));
        navItemList.add(new NavItem(R.mipmap.ic_public_announcement, "公示公告"));
        navItemList.add(new NavItem(R.mipmap.ic_survey_result, "调查结果"));
        navItemList.add(new NavItem(R.mipmap.ic_as_the_results, "认定结果"));
        navItemList.add(new NavItem(R.mipmap.ic_assessment_result, "评估结果"));
        navItemList.add(new NavItem(R.mipmap.ic_housing_agreement, "房屋协议"));
        navItemList.add(new NavItem(R.mipmap.ic_vompensation_payments, "补偿款发放"));
        navItemList.add(new NavItem(R.mipmap.ic_housing_statement, "房屋结算单"));
        navItemList.add(new NavItem(R.mipmap.ic_place_for_family, "安置户型"));

        fgvNav.setAdapter(new CommonAdapter<NavItem>(getActivity(), navItemList,
                R.layout.item_nav) {
            @Override
            public void convert(ViewHolder helper, NavItem item) {
                helper.setBackgroundResource(R.id.iv_img, item.getRedId());
                helper.setText(R.id.tv_title, item.getNavText());
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {
        homePresenter.getHomeConfig("1");
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_home;
    }


    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void onGetHomeConfigSuccess(HomeConfig homeConfig) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: " + hidden);
    }
}
