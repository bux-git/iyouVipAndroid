package com.base.http;

import android.text.TextUtils;

import com.base.BaseApplication;
import com.base.constant.Constant;
import com.base.http.rx.RxHttpResponseCompat;
import com.base.localdata.AppParams;
import com.blankj.utilcode.util.GsonUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

/**
 * @description：
 * @author：bux on 2018/6/9 9:31
 * @email: 471025316@qq.com
 */
public class BaseModel<E> {

    private static final int POST = 1;
    private static final int GET = 2;
    private static final int POST_MUL = 3;
    private static final int POST_BODY = 4;


    private static ApiService mApiService = BaseApplication.get().getAppComponent().getApiService();
    private LifecycleProvider<E> rxLife;

    protected BaseModel(LifecycleProvider<E> rxLife) {
        this.rxLife = rxLife;
    }


    /**
     * 根据提交方式获取http请求
     *
     * @param action
     * @param map
     * @param builder
     * @param method
     * @param isLife
     * @return
     */
    private Flowable<String> getBaseApi(String action, Map<String, String> map, MultipartBody.Builder builder, int method, boolean isLife) {
        return getBaseApi(action, map, builder, "", method, isLife);
    }

    private Flowable<String> getBaseApi(String action, Map<String, String> map, MultipartBody.Builder builder, String body, int method, boolean isLife) {
        if (map == null) {
            map = new HashMap<>();
        }
        String url = commonApiParams(action, map);

        Flowable<String> flowable = mApiService.postMap(url, map);
        switch (method) {
            case GET:
                flowable = mApiService.getQuery(url, map);
                break;
            case POST_MUL:
                builder.setType(MultipartBody.FORM);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
                flowable = mApiService.uploadWithRequestBody(url, builder.build());
                break;
            case POST_BODY:
                flowable = mApiService.postBody(action, body);
                break;
            default:

        }
        if (!isLife) {
            return flowable;
        }
        return flowable.compose(rxLife.<String>bindToLifecycle());
    }

    /**
     * post 请求接口 返回List集合
     *
     * @param <T>
     * @param action
     * @param map
     * @param cls
     * @return
     */
    protected <T> Flowable<List<T>> postFieldList(String action, Map<String, String> map, Class<T> cls) {
        if (map == null) {
            map = new HashMap<>();
        }
        return getBaseApi(action, map, null, POST, true)
                .compose(RxHttpResponseCompat.<List<T>>compatResult(GsonUtils.getListType(cls)));
    }

    /**
     * post 请求接口 返回对象
     *
     * @param <T>
     * @param action
     * @param map
     * @param cls
     * @return
     */
    protected <T> Flowable<T> postFieldObj(String action, Map<String, String> map, Class<T> cls) {
        if (map == null) {
            map = new HashMap<>();
        }
        return getBaseApi(action, map, null, POST, true)
                .compose(RxHttpResponseCompat.<T>compatResult(GsonUtils.getType(cls)));
    }

    /**
     * post 请求接口 返回对象
     *
     * @param <T>
     * @param action
     * @param map
     * @param cls
     * @return
     */
    protected <T> Flowable<T> postFieldNoLifeObj(String action, Map<String, String> map, Class<T> cls) {
        if (map == null) {
            map = new HashMap<>();
        }
        return getBaseApi(action, map, null, POST, false)
                .compose(RxHttpResponseCompat.<T>compatResult(GsonUtils.getType(cls)));
    }

    /**
     * 提交text/json 格式请求体
     * 返回对象
     *
     * @param action
     * @param r
     * @param cls
     * @param <T>
     * @param <R>
     * @return
     */
    protected <T, R> Flowable<T> postBodyObj(String action, R r, Class<T> cls) {

        return getBaseApi(action, null, null, GsonUtils.toJson(r), POST_BODY, true)
                .compose(RxHttpResponseCompat.<T>compatResult(GsonUtils.getType(cls)));
    }

    /**
     * 提交text/json 格式请求体
     * 返回对象
     *
     * @param action
     * @param r
     * @param cls
     * @param <T>
     * @param <R>
     * @ret
     */
    protected <T, R> Flowable<List<T>> postBodyList(String action, R r, Class<T> cls) {

        return getBaseApi(action, null, null, GsonUtils.toJson(r), POST_BODY, true)
                .compose(RxHttpResponseCompat.<List<T>>compatResult(GsonUtils.getListType(cls)));
    }

    /**
     * get请求接口 获取列表
     *
     * @param action
     * @param map
     * @param cls
     * @param <T>
     * @return
     */
    protected <T> Flowable<List<T>> getQueryList(String action, Map<String, String> map, Class<T> cls) {
        return getBaseApi(action, map, null, GET, true)
                .compose(RxHttpResponseCompat.<List<T>>compatResult(GsonUtils.getListType(cls)));
    }

    /**
     * get请求接口 获取对象
     *
     * @param action
     * @param map
     * @param cls
     * @param <T>
     * @return
     */
    protected <T> Flowable<T> getQueryObj(String action, Map<String, String> map, Class<T> cls) {
        return getBaseApi(action, map, null, GET, true)
                .compose(RxHttpResponseCompat.<T>compatResult(GsonUtils.getType(cls)));
    }


    /**
     * 上传文件接口
     *
     * @param <T>
     * @param action
     * @param map
     * @return
     */
    protected <T> Flowable<T> uploadFileNoLife(String action, Map<String, String> map, MultipartBody.Builder builder, Class<T> cls) {
        if (map == null) {
            map = new HashMap<>();
        }

        return getBaseApi(action, map, builder, POST_MUL, false)
                .compose(RxHttpResponseCompat.<T>compatResult(GsonUtils.getType(cls)));
    }


    private static String commonApiParams(String action, Map<String, String> map) {
        String url = action;
        if (!action.contains("http")) {
            url = Constant.GLOBAL_BASE_URL + action;
        }

        commonParams(map);

        return url;
    }

    /**
     * 设置公共参数
     *
     * @param map
     * @return
     */
    private static void commonParams(Map<String, String> map) {

        //设置公共参数

        if (!TextUtils.isEmpty(AppParams.CACHE_IP)) {
            map.put("deviceIp", AppParams.CACHE_IP);
        }

    }

    /**
     * 根据action url 拼接处完整的URL和公共参数
     *
     * @param action
     * @return
     */
    public static String commonParamsUrl(String action, Map<String, String> map) {


        String url = action;
        if (!url.contains("http")) {
            url = Constant.BASE_URL + action;
        }

        String split = "";
        if (url.contains("?")) {
            split = "&";
        } else {

            split = "?";
        }

        if (map == null) {
            map = new HashMap<>();
        }

        //没有token才添加token
        if (!url.contains("token=")) {
            commonParams(map);
        }


        if (map.size() == 0) {
            return url;
        }
        return url + split + paramsToGet(map);
    }

    /**
     * 根据action 拼接处完整的URL和公共参数
     *
     * @param action
     * @return
     */
    public static String commonParamsUrl(String action) {

        return commonParamsUrl(action, null);
    }

    /**
     * 将带参数的url中的token 替换掉
     *
     * @param url
     * @return
     */
    public static String commonReplaceToken(String url) {
        if (!url.contains("?")) {
            return commonParamsUrl(url);
        }

        int indexW = url.indexOf("?");
        StringBuilder realUrl = new StringBuilder(url.substring(0, indexW));
        String paramsStr = url.substring(indexW, url.length());

        String[] params = paramsStr.split("&");

        //重新组合
        realUrl.append("?");
        String split = "";
        for (String param : params) {
            if (!param.contains("token")) {
                realUrl.append(split).append(param);
                split = "&";
            }
        }

        return realUrl.toString();
    }


    /**
     * map 参数拼接
     *
     * @param map map
     * @return 参数串
     */
    public static String paramsToGet(Map<String, String> map) {
        String split = "";
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(split).append(key).append("=").append(map.get(key));
            split = "&";
        }
        return sb.toString().trim();
    }

}
