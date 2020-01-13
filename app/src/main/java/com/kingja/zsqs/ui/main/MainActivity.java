package com.kingja.zsqs.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseActivity;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.ui.affirm.ResultFragment;
import com.kingja.zsqs.ui.file.FileFragment;
import com.kingja.zsqs.ui.placement.detail.PlacementDetailFragment;
import com.kingja.zsqs.ui.placement.list.PlacementListFragment;
import com.kingja.zsqs.ui.project.ProjectDetailFragment;
import com.kingja.zsqs.utils.ToastUtil;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 上午 9:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MainActivity extends BaseActivity implements IStackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initVariable() {

    }

    @Override
    public View getContentView() {
        View rootView = View.inflate(this, R.layout.activity_main, null);
        return rootView;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
//        switchFragment(new HomeFragment());
//        switchFragment(new ProjectDetailFragment());
//        switchFragment(FileFragment.newInstance(Constants.REQUESTCODE_FILETYPE.GONGSHIGONGGAO));
//        switchFragment(ResultFragment.newInstance(Constants.REQUESTCODE_RESULTTYPE.DIAOCHA));
//        switchFragment(new PlacementListFragment());
        switchFragment(PlacementDetailFragment.newInstance(28));
    }

    private void switchFragment(Fragment stackFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.rl_content, stackFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {

    }

    @Override
    public void addStack(Fragment stackFragment) {
        switchFragment(stackFragment);
        Log.e(TAG, "addStack: ");
        ToastUtil.showText("入栈");
    }

    @Override
    public void outStack(Fragment stackFragment) {
        ToastUtil.showText("出栈");
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
}
