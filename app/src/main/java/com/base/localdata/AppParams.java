package com.base.localdata;

import android.text.TextUtils;

import com.base.BaseApplication;

/**
 * @description：APP级别卸载才删除
 * @author：bux on 2018/6/25 9:57
 * @email: 471025316@qq.com
 */
public class AppParams {


    private static final String FILE_NAME = "AppParams";


    private static final String DEVICE_CACHE_IP = "CACHE_IP";

    //设备IP
    public static String CACHE_IP = "";

    private static SPUtils sp;


   static {
        sp = SPUtils.getInstance(FILE_NAME, BaseApplication.get());

    }


    public static String getCacheIp() {
        if (TextUtils.isEmpty(CACHE_IP)) {
            CACHE_IP = sp.getString(DEVICE_CACHE_IP, "");
        }
        return CACHE_IP;
    }

    public static void setCacheIp(String ip) {
        CACHE_IP = ip;
        sp.put(DEVICE_CACHE_IP, ip);

    }

}
