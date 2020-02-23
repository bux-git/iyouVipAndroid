package com.base.mvp;

import android.content.Context;

/**
 * @description：View层基类
 * @author：bux on 2020/2/21 15:53
 * @email: 471025316@qq.com
 */
public interface IBaseView {


    /**
     * 空数据
     *
     * @param tag TAG
     */
    void onEmpty(Object tag);

    /**
     * 错误数据
     *
     * @param tag      TAG
     * @param errorMsg 错误信息
     */
    void onError(Object tag, String errorMsg);

    /**
     * 上下文
     *
     * @return context
     */
    Context getContext();
}
