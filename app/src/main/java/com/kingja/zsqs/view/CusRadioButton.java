package com.kingja.zsqs.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.kingja.zsqs.R;

/**
 * Description:TODO
 * Create Time:2019/12/31 0031 上午 11:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CusRadioButton extends AppCompatRadioButton {
    public CusRadioButton(Context context) {
        this(context, null);
    }

    public CusRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_combobox);
        drawable.setBounds(0, 0, 70, 70);
        setCompoundDrawables(null, drawable, null, null);
    }
}
