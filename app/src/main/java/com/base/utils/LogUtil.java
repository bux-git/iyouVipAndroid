package com.base.utils;

import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;

import androidx.annotation.Nullable;

/**
 * Log日志工具，封装logger
 */
public class LogUtil {

    private static final String LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "logger";

    /**
     * 初始化log工具，在app入口处调用
     */
    public static void init() {


        FormatStrategy logFormatStrategy = PrettyFormatStrategy.newBuilder()

                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(1)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag(AppUtils.getAppName())   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();


        Logger.addLogAdapter(new AndroidLogAdapter(logFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }

    public static void d(String message) {
        Logger.d(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }


    public static void w(String message, Throwable e) {
        String info = e != null ? e.toString() : "null";
        Logger.w(message + "：" + info);
    }

    public static void e(String message, Throwable e) {
        Logger.e(e, message);
    }

    public static void e(String message) {
        Logger.e(message);
    }


    public static void json(String json) {
        Logger.json(json);
    }
}