package com.kingja.zsqs.ui.login;

import android.content.Intent;
import android.view.View;

import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.HouseItem;
import com.kingja.zsqs.net.entiy.LoginInfo;
import com.kingja.zsqs.service.HouseListContract;
import com.kingja.zsqs.service.HouseListPresenter;
import com.kingja.zsqs.service.InitializeService;
import com.kingja.zsqs.ui.placement.detail.PlacementDetailFragment;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/1/16 0016 上午 10:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginFragment extends BaseTitleFragment implements LoginContract.View {
    @BindView(R.id.sset_id)
    SuperShapeEditText ssetId;
    @Inject
    LoginPresenter loginPresenter;

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.iv_four, R.id.iv_five, R.id.iv_six, R.id.iv_seven,
            R.id.iv_eight, R.id.iv_nine, R.id.iv_zero, R.id.iv_delete, R.id.iv_empty, R.id.iv_confirm,
            R.id.sstv_face_login})
    void onClick(View v) {
        String id;
        switch (v.getId()) {
            case R.id.sstv_face_login:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStackAndOutLast(new LoginByFaceFragment(),this);
//                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new LoginByFaceFragment());
                break;
            case R.id.iv_confirm:
                id = ssetId.getText().toString().trim();
                if (CheckUtil.checkEmpty(id, "请输入或者扫描证件")) {
                    loginPresenter.login(Constants.PROJECT_ID, id);
                }
                break;
            case R.id.iv_empty:
                ssetId.setText("");
                break;
            case R.id.iv_delete:
                id = ssetId.getText().toString().trim();
                int length = id.length();
                if (length > 0) {
                    ssetId.setText(id.substring(0, length - 1));
                    ssetId.setSelection(ssetId.getText().length());
                }
                break;
            case R.id.iv_zero:
                input("0");
                break;
            case R.id.iv_one:
                input("1");
                break;
            case R.id.iv_two:
                input("2");
                break;
            case R.id.iv_three:
                input("3");
                break;
            case R.id.iv_four:
                input("4");
                break;
            case R.id.iv_five:
                input("5");
                break;
            case R.id.iv_six:
                input("6");
                break;
            case R.id.iv_seven:
                input("7");
                break;
            case R.id.iv_eight:
                input("8");
                break;
            case R.id.iv_nine:
                input("9");
                break;
        }
    }

    public void input(String number) {
        String content = ssetId.getText().toString().trim();
        ssetId.setText(content + number);
        ssetId.setSelection(ssetId.getText().length());
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
        loginPresenter.attachView(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {

    }

    @Override
    protected String getTitle() {
        return "登录";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_login;
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return false;
    }

    @Override
    public void onLoginSuccess(LoginInfo loginInfo) {
        SpSir.getInstance().putString(SpSir.REALNAME, loginInfo.getRealName());
        SpSir.getInstance().putString(SpSir.MOBILE, loginInfo.getMobilePhone());
        SpSir.getInstance().putString(SpSir.IDCARD, loginInfo.getIdcard());
        ToastUtil.showText("登录成功");
        EventBus.getDefault().post(new LoginStatusEvent(true));
        ((IStackActivity) getActivity()).outStack(this);
        mActivity.startService(new Intent(mActivity, InitializeService.class));
    }
}
