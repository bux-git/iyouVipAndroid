package com.base.bean;

import android.text.TextUtils;

import com.base.http.exception.ExCodeConstant;

/**
 * @description：
 * @author：bux on 2018/9/10 23:12
 * @email: 471025316@qq.com
 */
public class BaseBean<T> {


    public BaseBean() {
    }

    public BaseBean(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    /**
     * state : 1
     * msg : 成功
     * data : {"userName":"七区","Phone":"111111111"}
     */


    private int code;
    private int errorCode;
    private String message;
    private String desc;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        if (!TextUtils.isEmpty(desc)) {
            return desc;
        }
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public String getDesc() {
        return getMsg();
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public boolean isSuccess() {
        return code == ExCodeConstant.API_SUCCESS || errorCode == 0;
    }


    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", msg='" + message +
                '}';
    }
}
