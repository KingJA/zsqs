package com.kingja.zsqs.ui.login;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.face.FaceSir;
import com.kingja.zsqs.face.widget.FaceRectView;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.LoginInfo;
import com.kingja.zsqs.service.houses.HousesListService;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.SoundPlayer;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.dialog.DoubleDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2020/4/20 0020 下午 3:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginByFaceFragment extends BaseTitleFragment implements ViewTreeObserver.OnGlobalLayoutListener,
        LoginContract.View {
    @BindView(R.id.single_camera_texture_preview)
    TextureView previewView;
    @BindView(R.id.single_camera_face_rect_view)
    FaceRectView faceRectView;
    @BindView(R.id.single_camera_recycler_view_person)
    RecyclerView recyclerShowFaceInfo;
    @BindView(R.id.ll_inputBar)
    LinearLayout llInputBar;
    @BindView(R.id.sset_id)
    SuperShapeEditText ssetId;
    private FaceSir faceSir;
    private DoubleDialog goBindDialog;
    private String idcard;
    private DoubleDialog confirmIdcardDialog;
    @Inject
    LoginPresenter loginPresenter;

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.iv_four, R.id.iv_five, R.id.iv_six, R.id.iv_seven,
            R.id.iv_eight, R.id.iv_nine, R.id.iv_zero, R.id.iv_delete, R.id.iv_empty, R.id.iv_confirm,
            R.id.iv_confirm2})
    void onclick(View v) {
        SoundPlayer.getInstance().playVoice(R.raw.btn01);
        switch (v.getId()) {

            case R.id.sstv_face_login:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new LoginByFaceFragment());
                break;
            case R.id.iv_confirm:
            case R.id.iv_confirm2:
                idcard = ssetId.getText().toString().trim();
                if (CheckUtil.checkIdCard(idcard, "身份证号码格式不正确")) {
                    register();
                }
                break;
            case R.id.iv_empty:
                ssetId.setText("");
                break;
            case R.id.iv_delete:
                idcard = ssetId.getText().toString().trim();
                int length = idcard.length();
                if (length > 0) {
                    ssetId.setText(idcard.substring(0, length - 1));
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
        faceSir = new FaceSir(getActivity());
        faceSir.initFaces();
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
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    protected void initData() {
        goBindDialog = DoubleDialog.newInstance("未匹配到人脸数据，是否进行人脸绑定", "好的");

    }

    @Override
    public void initNet() {
    }

    @Override
    protected String getTitle() {
        return "刷脸登录";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_login_face;
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return false;
    }


    @Override
    public void onDestroyView() {
        if (faceSir != null) {
            faceSir.onDestory();
        }
        super.onDestroyView();
    }

    public void register() {
        faceSir.setRegisterStatus();
    }

    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        faceSir.initEngineAndCamera(faceRectView, previewView);
    }

    @Override
    public void onLoginSuccess(LoginInfo loginInfo) {
        SpSir.getInstance().putString(SpSir.REALNAME, loginInfo.getRealName());
        SpSir.getInstance().putString(SpSir.MOBILE, loginInfo.getMobilePhone());
        SpSir.getInstance().setIdcard(loginInfo.getIdcard());
        ToastUtil.showText("登录成功");
        EventBus.getDefault().post(new LoginStatusEvent(true));
        ((IStackActivity) getActivity()).outStack(this);
        mFragmentActivity.startService(new Intent(mFragmentActivity, HousesListService.class));
    }
}
