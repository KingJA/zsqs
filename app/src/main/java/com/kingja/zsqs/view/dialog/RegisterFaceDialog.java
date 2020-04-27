package com.kingja.zsqs.view.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.view.StringTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 下午 3:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RegisterFaceDialog extends BaseTimerDialog {
    @BindView(R.id.tv_countdown)
    StringTextView tvCountdown;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.sstv_confirm)
    SuperShapeTextView sstvConfirm;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.stv_idcard)
    StringTextView stvIdcard;
    Unbinder unbinder;
    private String idcard;
    private Bitmap nv21;


    public static RegisterFaceDialog newInstance(Bitmap nv21, String username) {
        RegisterFaceDialog fragment = new RegisterFaceDialog();
        Bundle args = new Bundle();
        args.putParcelable(Constants.Extra.BYTEARRAY, nv21);
        args.putString(Constants.Extra.STRING, username);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.sstv_confirm, R.id.ssll_dismiss})
    void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.sstv_confirm:
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                break;
            case R.id.ssll_dismiss:
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
                break;
        }
    }


    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            nv21 = getArguments().getParcelable(Constants.Extra.BYTEARRAY);
            idcard = getArguments().getString(Constants.Extra.STRING);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
    }

    @Override
    protected void initView() {
        stvIdcard.setString(idcard);
        ivHead.setImageBitmap(nv21);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {
    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_register_face;
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
