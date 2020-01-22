package com.kingja.zsqs.view.dialog;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingja.zsqs.CommonAdapter;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.HouseItem;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.FixedListView;
import com.kingja.zsqs.view.StringTextView;

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
public class DialogHouseSelect extends BaseDialogFragment {

    @BindView(R.id.flv_house)
    FixedListView flvHouse;
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;

    @OnClick({R.id.ssll_dismiss})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssll_dismiss:
                dismiss();
                break;
        }
    }

    @OnItemClick(R.id.flv_house)
    void onItemClick(android.widget.AdapterView<?> adapterView, int postiion) {
        HouseItem item = (HouseItem) adapterView.getItemAtPosition(postiion);
        SpSir.getInstance().putString(SpSir.HOUSE_ID, item.getHouseId());
        dismiss();
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

        flvHouse.setAdapter(new CommonAdapter<HouseItem>(getActivity(), houseItemList, R.layout.item_house) {
            @Override
            public void convert(ViewHolder helper, HouseItem item) {
                helper.setText(R.id.tv_realName, item.getRealName());
                helper.setText(R.id.tv_mobilePhone, item.getMobilePhone());
                helper.setText(R.id.tv_statusName, item.getStatusName());
                helper.setText(R.id.tv_cusCode, item.getCusCode());
                helper.setText(R.id.tv_address, item.getAddress());
                helper.setText(R.id.tv_certArea, item.getCertArea() + "㎡");
                helper.setText(R.id.tv_legalArea, item.getLegalArea() + "㎡");
                helper.setBackgroundResource(R.id.ll_itemRoot, SpSir.getInstance().getString(SpSir.HOUSE_ID,"").equals(item.getHouseId())?
                        R.drawable.shape_r0_red_2:R.drawable.shape_r0_c_2);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

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
