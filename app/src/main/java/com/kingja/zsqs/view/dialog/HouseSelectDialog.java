package com.kingja.zsqs.view.dialog;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingja.zsqs.CommonAdapter;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.event.OnHouseChangeEvent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.HouseItem;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.FixedListView;
import com.kingja.zsqs.view.StringTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 下午 3:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HouseSelectDialog extends BaseTimerDialog {

    @BindView(R.id.flv_house)
    FixedListView flvHouse;
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;
    private CommonAdapter<HouseItem> houseAdapter;

    @OnClick({R.id.ssll_dismiss, R.id.sstv_confirm})
    void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.ssll_dismiss:

                break;
            case R.id.sstv_confirm:
                EventBus.getDefault().post(new OnHouseChangeEvent());
                break;
        }
    }

    @OnItemClick(R.id.flv_house)
    void onItemClick(android.widget.AdapterView<?> adapterView, int postiion) {
        HouseItem item = (HouseItem) adapterView.getItemAtPosition(postiion);
        SpSir.getInstance().setHouseId(item.getHouseId());
        SpSir.getInstance().setBuildingType(item.getBuildingType());
        SpSir.getInstance().putString(SpSir.REALNAME, item.getRealName());
        SpSir.getInstance().putString(SpSir.ADDRESS, item.getAddress());
        houseAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initVariable() {
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
    }

    @Override
    protected void initView() {
        String houseListJson = SpSir.getInstance().getString(SpSir.HOUSE_JSON);
        List<HouseItem> houseItemList = new Gson().fromJson(houseListJson, new TypeToken<List<HouseItem>>() {
        }.getType());

        houseAdapter = new CommonAdapter<HouseItem>(getActivity(), houseItemList,
                R.layout.item_house) {
            @Override
            public void convert(ViewHolder helper, HouseItem item) {
                helper.setText(R.id.tv_realName, item.getRealName());
                helper.setText(R.id.tv_mobilePhone, item.getMobilePhone());
                helper.setText(R.id.tv_statusName, item.getStatusName());
                helper.setText(R.id.tv_cusCode, item.getCusCode());
                helper.setText(R.id.tv_address, item.getAddress());
                helper.setText(R.id.tv_certArea, item.getCertArea() + "㎡");
                helper.setText(R.id.tv_legalArea, item.getLegalArea() + "㎡");
                helper.setBackgroundResource(R.id.ll_itemRoot,
                        SpSir.getInstance().getHouseId().equals(item.getHouseId()) ?
                                R.drawable.shape_r0_red_2 : R.drawable.shape_r0_c_2);
            }
        };
        flvHouse.setAdapter(houseAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected float getScreenWidthRatio() {
        return 0.8f;
    }


    @Override
    protected int getContentId() {
        return R.layout.dialog_house_select;
    }


    @Override
    protected void updateTimer(int countDownTime) {
        tvCountdown.setString(String.format("[%ds]", countDownTime));
    }

    @Override
    protected boolean ifStartTimer() {
        return true;
    }
}
