package com.kingja.zsqs;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.view.FixedGridView;

import java.util.ArrayList;
import java.util.List;


public class BigActivity extends AppCompatActivity {

    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.fra_home);
        tv_info = findViewById(R.id.tv_info);

        getDeviceInfo();


        FixedGridView fixedGridView = findViewById(R.id.fgv_nav);

        List<NavItem> navItemList = new ArrayList<>();
        navItemList.add(new NavItem(R.mipmap.ic_item_survey, "项目概况12"));
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

        fixedGridView.setAdapter(new CommonAdapter<NavItem>(this, navItemList,
                R.layout.item_nav) {
            @Override
            public void convert(ViewHolder helper, NavItem item) {
                helper.setBackgroundResource(R.id.iv_img, item.getRedId());
                helper.setText(R.id.tv_title, item.getNavText());
            }
        });

        if (isScreenChange()) {
            Toast.makeText(this,"横屏",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"竖屏",Toast.LENGTH_LONG).show();
        }
    }

    private void getDeviceInfo() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
// 屏幕宽度（像素）
        int width = metric.widthPixels;
// 屏幕高度（像素）
        int height = metric.heightPixels;
// 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
// 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL + ",\nSDK版本:"
                + android.os.Build.VERSION.SDK + ",\n系统版本:"
                + android.os.Build.VERSION.RELEASE + "\n屏幕宽度（像素）: " + width + "\n屏幕高度（像素）: " + height + "\n屏幕密度:  " + density + "\n屏幕密度DPI: " + densityDpi;
        Log.e("BigActivity", "info: " + info);
    }

    public boolean isScreenChange() {
        Configuration mConfiguration = this.getResources().getConfiguration(); // 获取设置的配置信息
        int ori = mConfiguration.orientation; // 获取屏幕方向

        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            return true;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {

            // 竖屏
            return false;
        }
        return false;
    }
}
