package com.android.sjq.okhttprequest;

/**
 * Created by Administrator on 2016/11/3.
 */

public class RequestException extends Exception {
    private String msg;

    public RequestException() {

    }

    public RequestException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
