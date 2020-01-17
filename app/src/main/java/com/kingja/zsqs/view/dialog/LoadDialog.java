package com.kingja.zsqs.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.zsqs.R;

/**
 * Description:TODO
 * Create Time:2020/1/16 0016 下午 2:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoadDialog extends DialogFragment {
    protected FragmentActivity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyMiddleDialogStyle);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    public void show(FragmentActivity context) {
        show(context.getSupportFragmentManager(), getClass().getSimpleName());
    }
}
