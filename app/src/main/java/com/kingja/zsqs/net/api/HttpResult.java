package com.kingja.zsqs.net.api;

/**
 * Description：TODO
 * Create Time：2016/10/620:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HttpResult<T> {

    private int status_code;
    private String message;
    private T data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
