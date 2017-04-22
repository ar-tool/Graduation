package com.oujian.graduation.net.res;


/**
 * 网络返回基类 支持泛型
 * Created by yi on 2016/11/28.
 */
public class BaseResponse<T> {

    private int retCode;
    private String retMsg;
    private T user;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public boolean isOk() {
        return retCode == 0;
    }

}
