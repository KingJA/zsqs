package com.kingja.zsqs.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description:TODO
 * Create Time:2020/1/14 0014 下午 5:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AutoViewPager extends ViewPager {

    private OnViewPagerTouchListener mTouchListener = null;

    public AutoViewPager(Context context) {
        this(context, null);
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTouchListener != null) {
                    mTouchListener.onPagerTouch(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mTouchListener != null) {
                    mTouchListener.onPagerTouch(false);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnViewPagerTouchListener(OnViewPagerTouchListener listener) {
        this.mTouchListener = listener;
    }

    public interface OnViewPagerTouchListener {
        void onPagerTouch(boolean isTouch);
    }

}
