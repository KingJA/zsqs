package com.kingja.zsqs.ui.placement.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.kingja.zsqs.CommonAdapter;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.PlacementItem;
import com.kingja.zsqs.view.FixedGridView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2020/1/10 0010 下午 4:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlacementListFragment extends BaseTitleFragment implements PlacementListContract.View {
    @Inject
    PlacementListPresenter placementListPresenter;
    @BindView(R.id.fgv_placement)
    GridView fgvPlacement;
    Unbinder unbinder;
    private CommonAdapter<PlacementItem> placementAdapter;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        placementListPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        placementAdapter = new CommonAdapter<PlacementItem>(getContext(), null,R.layout.item_placement) {
            @Override
            public void convert(ViewHolder helper, PlacementItem item) {
                helper.setImageByUrl(R.id.iv_placementImg, Constants.BASE_FWCQ_IMG_URL+item.getHouse_plan_url());
                helper.setText(R.id.tv_name,String.format("户型 : %s",item.getProgress_house_plan_name()));
                helper.setText(R.id.tv_area,String.format("面积 : %s㎡",item.getArea()));
            }
        };
        fgvPlacement.setAdapter(placementAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {
        placementListPresenter.getPlacementList("bf49a831-1cf3-44c0-9739-ea0c5578f94f");
    }

    @Override
    protected String getTitle() {
        return "安置套型";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_placement_list;
    }

    @Override
    public void onGetPlacementListSuccess(List<PlacementItem> placementItemList) {
        setListView(placementItemList, placementAdapter);
    }

}
