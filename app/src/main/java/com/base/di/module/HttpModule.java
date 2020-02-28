package com.base.di.module;

import com.base.BaseApplication;
import com.base.constant.Constant;
import com.base.http.ApiService;
import com.base.http.util.SSLSocketClient;
import com.google.gson.Gson;
import com.jfkj.iyouvip.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @description：
 * @author：bux on 2018/4/2 17:56
 * @email: 471025316@qq.com
 */

@Module
public class HttpModule {
    private static final String TAG = "HttpModule";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Gson gson, BaseApplication context) {
        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (BuildConfig.DEBUG) {
            logging.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.level(HttpLoggingInterceptor.Level.BASIC);
        }
        OkHttpClient.Builder okClient = new OkHttpClient.Builder()
                //HeadInterceptor实现了Interceptor，用来往Request Header添加一些业务相关数据，如APP版本，token信息
                // .addInterceptor(new HeadInterceptor())
                //  .addInterceptor(commonParamsInterceptor)
                //.addInterceptor(new LoggingInterceptor())
                .addInterceptor(logging)
                // 连接超时时间设置
                .connectTimeout(60, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());

        return okClient.build();
    }

    @Provides
    @Singleton
    Retrofit provodeRetrofit(OkHttpClient okHttpClient) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.GLOBAL_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);
        return builder.build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        // L.e( "provideApiService: " + retrofit);
        return retrofit.create(ApiService.class);
    }


}
