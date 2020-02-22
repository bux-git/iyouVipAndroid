package com.base.http;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @Description：
 * @author：Bux on 2018/1/10 11:05
 * @email: 471025316@qq.com
 */

public interface ApiService {

    /**
     * 简单表单提交 http提交参数格式 key1=value1&key2=value2
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Flowable<String> postMap(@Url String url, @FieldMap Map<String, String> params);

    /**
     * get 提交 http提交参数格式 url?key1=value1&key2=value2
     *
     * @param url
     * @param params
     * @return
     */
    @GET
    Flowable<String> getQuery(@Url String url, @QueryMap Map<String, String> params);

    /**
     * post提交一个字符串格式数据 为xml json 或者其他自定义格式等
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Flowable<String> postBody(@Url String url, @Body String body);

    /**
     * 文件 文字混传 复杂表单提交 通过 MultipartBody和@body作为参数来上传
     *
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST
    Flowable<String> uploadWithRequestBody(@Url String url, @Body MultipartBody multipartBody);

    /**
     * 单个body上传
     * @param url
     * @param body
     * @return
     */
    @POST
    Flowable<String> uploadWithRequestBody(@Url String url, @Body RequestBody body);
}
