package com.kingja.zsqs.base;

import android.support.v4.app.Fragment;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 下午 4:29
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IStackActivity {
    void addStack(Fragment stackFragment);
    void addStackAndOutLast(Fragment stackFragment);

    void outStack(Fragment stackFragment);


}
