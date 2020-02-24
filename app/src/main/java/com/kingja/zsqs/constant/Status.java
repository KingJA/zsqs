package com.kingja.zsqs.constant;

/**
 * Description:TODO
 * Create Time:2018/8/16 0016 下午 4:13
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Status {
    public interface ResultCode {
        //成功
        int SUCCESS = 200;
        //服务器错误
        int ERROR_SERVER = 10000;
        //登录失效
        int ERROR_LOGIN_FAIL = 401;
        //不正常数据
        int UNNORMAL = 2;
    }

    public interface VideoType {
        //0:网页链接,
        int VideoH5 = 0;
        //1:MP4文件
        int VideoNative = 1;
    }
}
