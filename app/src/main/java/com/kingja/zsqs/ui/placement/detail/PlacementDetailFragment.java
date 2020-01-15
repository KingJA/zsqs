package com.kingja.zsqs.ui.placement.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.VrPageAdapter;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.loader.image.ImageLoader;
import com.kingja.zsqs.net.entiy.PlacementDetail;
import com.kingja.zsqs.view.StringTextView;
import com.kingja.zsqs.ui.dialog.appoint.AppointDialog;
import com.kingja.zsqs.ui.dialog.offer.OfferDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 上午 9:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlacementDetailFragment extends BaseTitleFragment implements PlacementDetailContract.View {
    @Inject
    PlacementDetailPresenter placementDetailPresenter;
    @BindView(R.id.iv_housePlanUrl)
    ImageView ivHousePlanUrl;
    @BindView(R.id.tv_rootDes)
    StringTextView tvRootDes;
    @BindView(R.id.tv_planName)
    StringTextView tvPlanName;
    @BindView(R.id.tv_area)
    StringTextView tvArea;
    @BindView(R.id.tv_towardsName)
    StringTextView tvTowardsName;
    @BindView(R.id.vp_vr)
    ViewPager vpVr;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.sstv_index)
    SuperShapeTextView sstvIndex;
    private int projectId;
    private AppointDialog appointDialog;
    private OfferDialog offerDialog;


    @OnClick({R.id.sstv_appoint, R.id.sstv_offer})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.sstv_appoint:
                appointDialog.show(getActivity());
                break;
            case R.id.sstv_offer:
                offerDialog.show(getActivity());
                break;
        }
    }

    public static PlacementDetailFragment newInstance(int projectId) {
        PlacementDetailFragment fragment = new PlacementDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.PROJECTID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            projectId = getArguments().getInt(Constants.Extra.PROJECTID);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        placementDetailPresenter.attachView(this);
    }

    @Override
    protected void initView() {

        offerDialog = new OfferDialog();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {
        placementDetailPresenter.getPlacementDetail(projectId);
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_placement_detail;
    }

    @Override
    public void onGetPlacementDetailSuccess(PlacementDetail placementDetail) {
        appointDialog = AppointDialog.newInstance(String.valueOf(placementDetail.getProgress_house_plan_id()));

        setTitle(placementDetail.getProgress_house_plan_name());
        ImageLoader.getInstance().loadImage(getActivity(),
                Constants.BASE_FWCQ_IMG_URL + placementDetail.getHouse_plan_url(), ivHousePlanUrl);
        tvRootDes.setString(String.format("%d室%d厅%d卫", placementDetail.getRoom(), placementDetail.getHall(),
                placementDetail.getToilet()));
        tvPlanName.setString(placementDetail.getProgress_house_plan_name());
        tvArea.setString(String.format("%s㎡", placementDetail.getArea()));
        tvTowardsName.setString(placementDetail.getTowards_name());

        List<PlacementDetail.HousePlanRenovationCaseBean> caseList =
                placementDetail.getHouse_plan_renovation_case();
        if (caseList != null && caseList.size() > 0) {
            VrPageAdapter vrPageAdapter = new VrPageAdapter(getActivity(), caseList);
            vpVr.setAdapter(vrPageAdapter);
            sstvIndex.setText(String.format("1/%d", caseList.size()));
            vpVr.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    sstvIndex.setText(String.format("1/%d", position + 1));
                }
            });
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            sstvIndex.setVisibility(View.GONE);
        }
    }
}
