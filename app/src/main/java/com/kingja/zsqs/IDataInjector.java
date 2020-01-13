package com.kingja.zsqs;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/11/30 0030 下午 2:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IDataInjector<T> {
    void setData(List<T> list);
    void addData(List<T> list);
    void reset();

}
