package com.kingja.zsqs.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Description:TODO
 * Create Time:2020/1/8 0008 下午 3:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StringTextView extends AppCompatTextView {
    public StringTextView(Context context) {
        this(context, null);
    }

    public StringTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setString(int value) {
        setText(String.valueOf(value));
    }

    public void setString(double value) {
        setText(String.valueOf(value));
    }

    public void setString(String value) {
        setText(String.valueOf(value));
    }
}
