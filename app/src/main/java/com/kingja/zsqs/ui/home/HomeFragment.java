package com.kingja.zsqs.ui.home;

import com.kingja.zsqs.CommonAdapter;
import com.kingja.zsqs.NavItem;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.base.BaseFragment;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.ui.affirm.ResultFragment;
import com.kingja.zsqs.ui.project.ProjectDetailFragment;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.FixedGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 上午 9:07
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.fgv_nav)
    FixedGridView fgvNav;

    @OnItemClick(R.id.fgv_nav)
    void onItemClick(int position, android.widget.AdapterView<?> adapterView) {
        ToastUtil.showText(position + "");
        switch (position) {
            case 0:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new ProjectDetailFragment());
                break;
            case 5:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new ResultFragment());
                break;

        }
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {

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

    }

    @Override
    protected int getContentId() {
        return R.layout.fra_home;
    }


}
