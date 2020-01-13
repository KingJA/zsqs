package com.kingja.zsqs.net.api;

/**
 * Description:TODO
 * Create Time:2019/11/20 0020 下午 4:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ApiException extends RuntimeException {
    private int status_code;

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        status_code = errorCode;
    }

    public int getCode() {
        return status_code;
    }
}