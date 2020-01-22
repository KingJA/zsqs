package com.kingja.zsqs.i;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2020/1/22 0022 下午 2:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IFile extends Serializable {

    public String getImgUrl();

    public String getOpenUrl();

    public String getFileName();

    public int getType();
}
