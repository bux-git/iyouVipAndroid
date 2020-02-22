package com.base.di.component;


import com.base.BaseApplication;
import com.base.di.module.AppModule;
import com.base.di.module.HttpModule;
import com.base.http.ApiService;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * @description：
 * @author：bux on 2018/4/2 16:43
 * @email: 471025316@qq.com
 */

@Component(modules = {AppModule.class, HttpModule.class})
@Singleton
public interface AppComponent {

    ApiService getApiService();

    BaseApplication getApplication();

    Gson getGson();

    OkHttpClient getOkHttpClient();


}
