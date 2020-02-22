package com.base.constant;

import com.jfkj.iyouvip.BuildConfig;

/**
 * @description：
 * @author：bux on 2018/3/22 16:12
 * @email: 471025316@qq.com
 */

public class Constant {

    private static final boolean IS_DEBUG = BuildConfig.DEBUG;//true;//

    //正式地址
    public static final String R_BASE_URL = "http://10.10.1.59:8082/";
    public static final String R_IMAGE_URL = "http://10.10.1.59:8082/";
    public static final String R_HOME_BASE_URL = "http://10.10.1.59:8080/";

    //测试地址
    public static final String TEST_BASE_URL = "https://www.wanandroid.com/";
    public static final String TEST_IMAGE_URL = "https://www.wanandroid.com/";
    public static final String TEST_HOME_BASE_URL = "https://www.wanandroid.com/";


    //当前使用地址
    public static final String BASE_URL = IS_DEBUG ? TEST_BASE_URL : R_BASE_URL;
    public static final String IMAGE_URL = IS_DEBUG ? TEST_IMAGE_URL : R_IMAGE_URL;

    public static final String GLOBAL_BASE_URL = BASE_URL;

    //获取banners数据
    public static final String ACTION_BANNER="project/list/";


}
