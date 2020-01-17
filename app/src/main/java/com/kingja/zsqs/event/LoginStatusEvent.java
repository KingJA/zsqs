package com.kingja.zsqs.event;

/**
 * Description:TODO
 * Create Time:2020/1/16 0016 下午 4:40
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginStatusEvent {
    private boolean hasLogined;

    public LoginStatusEvent(boolean hasLogined) {
        this.hasLogined = hasLogined;
    }

    public boolean isHasLogined() {
        return hasLogined;
    }

    public void setHasLogined(boolean hasLogined) {
        this.hasLogined = hasLogined;
    }
}
