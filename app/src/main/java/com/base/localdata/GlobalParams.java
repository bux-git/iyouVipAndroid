package com.base.localdata;

import android.text.TextUtils;

import com.base.BaseApplication;


/**
 * @description：全局设置参数 APP退出后清除
 * @author：bux on 2018/5/27 0:49
 * @email: 471025316@qq.com
 */
public class GlobalParams {

    private static final String FILE_NAME = "GlobalParams";

    //登录Token
    private static final String TOKEN = "token";


    private static SPUtils sp;

    public static String sToken;


    static {
        init();

    }

    private static void init() {
        sp = SPUtils.getInstance(FILE_NAME, BaseApplication.get());
        sToken = sp.getString(TOKEN, "");
    }

    public static void setToken(String token) {
        sToken = token;
        sp.put(TOKEN, sToken);
    }


    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(sToken);
    }


    public static void clear() {
        sp.clear();
        init();
    }

}
