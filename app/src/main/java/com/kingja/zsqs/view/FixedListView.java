package com.kingja.zsqs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Description:TODO
 * Create Time:2018/9/14 0014 下午 3:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FixedListView extends ListView {
    public FixedListView(Context context) {
        super(context);
    }

    public FixedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}