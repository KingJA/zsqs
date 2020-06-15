package com.kingja.zsqs.ui.login;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arcsoft.face.FaceFeature;
import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.event.ShowSwitchButtonEvent;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.HouseItem;
import com.kingja.zsqs.net.entiy.LoginInfo;
import com.kingja.zsqs.threepart.face.util.FaceSir;
import com.kingja.zsqs.threepart.face.widget.FaceRectView;
import com.kingja.zsqs.threepart.idcard.IdcardSir;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.GsonUtil;
import com.kingja.zsqs.utils.SoundPlayer;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.view.dialog.BaseTimerDialog;
import com.kingja.zsqs.view.dialog.DoubleDialog;
import com.kingja.zsqs.view.dialog.HouseSelectDialog;
import com.kingja.zsqs.view.dialog.RegisterFaceDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
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
        LoginContract.View, FaceSir.OnSearchFaceListener, FaceSir.OnRegisterFaceListener {
    @BindView(R.id.single_camera_texture_preview)
    TextureView previewView;
    @BindView(R.id.single_camera_face_rect_view)
    FaceRectView faceRectView;
    @BindView(R.id.single_camera_recycler_view_person)
    RecyclerView recyclerShowFaceInfo;
    @BindView(R.id.ll_inputBar)
    LinearLayout llInputBar;
    @BindView(R.id.sset_id)
    SuperShapeTextView ssetId;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_bandBtn)
    LinearLayout llBandBtn;
    private FaceSir faceSir;
    private DoubleDialog goBindDialog;
    private DoubleDialog confirmIdcardDialog;
    @Inject
    LoginPresenter loginPresenter;

    private boolean isBindUI;
    private IdcardSir idcardSir;

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.iv_four, R.id.iv_five, R.id.iv_six, R.id.iv_seven,
            R.id.iv_eight, R.id.iv_nine, R.id.iv_zero, R.id.iv_delete, R.id.iv_empty, R.id.iv_confirm, R.id.iv_x,
            R.id.sstv_face_bind})
    void onclick(View v) {
//        SoundPlayer.getInstance().playVoice(R.raw.btn01);
        String idcard = ssetId.getText().toString().trim();
        switch (v.getId()) {
            case R.id.sstv_face_login:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new LoginByFaceFragment());
                break;
            case R.id.iv_confirm:

                if (CheckUtil.checkIdCard(idcard, "身份证号码格式不正确")) {
                    register(idcard);
                }
                break;
            case R.id.iv_empty:
                ssetId.setText("");
                break;
            case R.id.sstv_face_bind:
                showBindUI();
                break;
            case R.id.iv_delete:
                idcard = ssetId.getText().toString().trim();
                int length = idcard.length();
                if (length > 0) {
                    ssetId.setText(idcard.substring(0, length - 1));
//                    ssetId.setSelection(ssetId.getText().length());
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
            case R.id.iv_x:
                input("x");
                break;
        }
    }

    public void input(String number) {
        String content = Objects.requireNonNull(ssetId.getText()).toString().trim();
        ssetId.setText(content + number);
//        ssetId.setSelection(ssetId.getText().length());
    }

    @Override
    protected void initVariable() {
        faceSir = new FaceSir(getActivity());
        faceSir.initFaces();

        idcardSir = new IdcardSir(getFragmentActivity());
        idcardSir.init(idcard -> {
            ssetId.setText(idcard);
        });
        idcardSir.startAuth();
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
        if (idcardSir != null) {
            idcardSir.onDestroy();
        }
        super.onDestroyView();
    }

    public void register(String username) {
        faceSir.register(username);
    }

    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        faceSir.initEngineAndCamera(faceRectView, previewView, this, this);
    }

    @Override
    public void onLoginSuccess(LoginInfo loginInfo) {
        SpSir.getInstance().putString(SpSir.REALNAME, loginInfo.getRealName());
        SpSir.getInstance().putString(SpSir.MOBILE, loginInfo.getMobilePhone());
        SpSir.getInstance().setIdcard(loginInfo.getIdcard());
        EventBus.getDefault().post(new LoginStatusEvent(true));
        ((IStackActivity) Objects.requireNonNull(getActivity())).outStack(this);
        List<HouseItem> houseItemList = loginInfo.getHouseList();
        if (houseItemList == null || houseItemList.size() == 0) {
            SpSir.getInstance().putInt(SpSir.HOUSE_SELECT_TYPE, Constants.HOUSE_SELECT_TYPE.NONE);
        } else if (houseItemList.size() == 1) {
            SpSir.getInstance().putInt(SpSir.HOUSE_SELECT_TYPE, Constants.HOUSE_SELECT_TYPE.ONE);
            SpSir.getInstance().putString(SpSir.HOUSE_JSON, GsonUtil.obj2Json(houseItemList));
            SpSir.getInstance().putString(SpSir.HOUSE_ID, houseItemList.get(0).getHouseId());
            SpSir.getInstance().putString(SpSir.ADDRESS, houseItemList.get(0).getAddress());
            SpSir.getInstance().setBuildingType(houseItemList.get(0).getBuildingType());
        } else {
            SpSir.getInstance().putInt(SpSir.HOUSE_SELECT_TYPE, Constants.HOUSE_SELECT_TYPE.MUL);
            SpSir.getInstance().putString(SpSir.HOUSE_JSON, GsonUtil.obj2Json(houseItemList));
            EventBus.getDefault().post(new ShowSwitchButtonEvent());
        }
        new HouseSelectDialog().show(getFragmentActivity());
    }

    @Override
    public void onSearchFaceSuccess(int requestId, String username) {
        if (!isBindUI && (confirmIdcardDialog == null || !confirmIdcardDialog.isShowing())) {
            SoundPlayer.getInstance().playVoice(R.raw.success);
            confirmIdcardDialog = DoubleDialog.newInstance(String.format("您绑定的身份证号码为:\n%s", username), "确定");
            confirmIdcardDialog.setOnConfirmListener(new BaseTimerDialog.OnConfirmListener() {
                @Override
                public void onConfirm() {
                    loginPresenter.login(SpSir.getInstance().getProjectId(), username,SpSir.getInstance().getSceneAddress(),SpSir.getInstance().getDeviceCode());

                }
            });
            confirmIdcardDialog.setOnCancelListener(new BaseTimerDialog.OnCancelListener() {
                @Override
                public void onCancel() {
                    faceSir.retryRecognizeDelayed(requestId);
                }
            });
            confirmIdcardDialog.show(getActivity());
        }

    }

    @Override
    public void onSearchFail(int requestId) {
        if (!isBindUI) {
            if (!goBindDialog.isShowing() && llInputBar.getVisibility() == View.GONE) {
                goBindDialog.setOnCancelListener(new BaseTimerDialog.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        faceSir.research(requestId);

                    }
                });
                goBindDialog.setOnConfirmListener(new BaseTimerDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        faceSir.stopListenSearchFail();
                        faceSir.retryRecognizeDelayed(requestId);
                        showBindUI();
                    }
                });
                goBindDialog.show(getActivity());
            }

        } else {
            faceSir.retryRecognizeDelayed(requestId);
        }


    }

    private void showBindUI() {
        isBindUI = true;
        llInputBar.setVisibility(View.VISIBLE);
        llBandBtn.setVisibility(View.GONE);
        tvTip.setText("请输入身份证号或扫描身份证自动识别，按‘确定’绑定人脸");
    }

    @Override
    public void onRegisterFaceCheck(Bitmap faceBitmap, FaceFeature faceFeature, String username) {
        RegisterFaceDialog registerFaceDialog = RegisterFaceDialog.newInstance(faceBitmap, username);
        registerFaceDialog.setOnConfirmListener(new BaseTimerDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                faceSir.doRegister(faceBitmap, faceFeature, username);
            }
        });
        registerFaceDialog.show(getFragmentActivity());

    }

    @Override
    public void onRegisterFaceSuccess(String username) {
        loginPresenter.login(SpSir.getInstance().getProjectId(), username,SpSir.getInstance().getSceneAddress(),SpSir.getInstance().getDeviceCode());
    }

    @Override
    public void onRegisterFaceFail(Throwable e) {

    }
}
