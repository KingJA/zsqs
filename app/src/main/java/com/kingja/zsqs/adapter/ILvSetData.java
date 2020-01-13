package com.kingja.zsqs.adapter;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/11/30 0030 下午 2:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ILvSetData<T> {
    void setData(List<T> list);

    void setData(List<T> list, boolean editable);
}
