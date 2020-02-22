package com.base.http.rx;


import android.text.TextUtils;

import com.base.BaseApplication;
import com.base.bean.BaseBean;
import com.base.http.exception.ApiException;
import com.blankj.utilcode.util.GsonUtils;

import org.reactivestreams.Publisher;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: bux on 2018/4/18 11:00
 * @email: 471025316@qq.com
 * @description:统一封装结果预处理
 */
public class RxHttpResponseCompat {


    /**
     * 标准 通用型 统一接口返回 处理
     *
     * @param type 接口Data数据对象 Type类型  接口返回为 基本数据类型 或 Class 或 List<T>
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<String, T> compatResult(final Type type) {

        return new FlowableTransformer<String, T>() {
            @Override
            public Publisher<T> apply(Flowable<String> upstream) {
                return upstream.flatMap(new Function<String, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(String result) throws Exception {
                        BaseBean<T> t = BaseApplication.get().getAppComponent().getGson().fromJson(result, GsonUtils.getType(BaseBean.class, type));
                        //成功
                        if (t.isSuccess()) {
                            T t1 = t.getData();
                            if (t1 == null) {

                                Class<T> cls;

                                if (type instanceof ParameterizedType) {
                                    cls = ((Class) ((ParameterizedType) type).getRawType());
                                } else {
                                    cls = (Class<T>) type;
                                }

                                if (cls.getSimpleName().equals(List.class.getSimpleName())) {
                                    t1 = (T) new ArrayList<>();
                                } else {
                                    t1 = cls.newInstance();
                                }
                            }
                            return Flowable.just(t1);
                        } else {//失败
                            String msg = TextUtils.isEmpty(t.getMsg()) ? t.getMsg() : t.getMsg();
                            return Flowable.error(new ApiException(t.getCode(), msg, result));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}






