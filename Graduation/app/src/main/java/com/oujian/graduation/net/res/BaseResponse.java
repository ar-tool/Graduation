package com.oujian.graduation.net.res;


/**
 * 网络返回基类 支持泛型
 * Created by yi on 2016/11/28.
 */
public class BaseResponse<T> {

    private int retCode;
    private String retMsg;
    private T retBody;

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

    public T getRetBody() {
        return retBody;
    }

    public void setRetBody(T retBody) {
        this.retBody = retBody;
    }
    public boolean isOk() {
        return retCode == 0;
    }

}
