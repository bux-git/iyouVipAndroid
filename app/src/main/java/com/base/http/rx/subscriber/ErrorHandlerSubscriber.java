package com.base.http.rx.subscriber;


import com.base.http.exception.ApiException;
import com.base.http.exception.RxErrorHandler;

import org.reactivestreams.Subscription;


/**
 * @description：
 * @author：bux on 2018/4/18 16:26
 * @email: 471025316@qq.com
 */
public abstract class ErrorHandlerSubscriber<T> extends DefaultSubscriber<T> {

    private static final String TAG = "ErrorHandlerSubscriber";
    private String url;
    private RxErrorHandler mRxErrorHandler;

    public void setUrl(String url) {
        this.url = url;
    }

    protected ErrorHandlerSubscriber() {
        mRxErrorHandler = new RxErrorHandler();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        super.onSubscribe(subscription);

    }

    @Override
    public void onError(Throwable t) {

        ApiException exception = mRxErrorHandler.handlerError(t);
        showErrorMsg(exception);

        onFail(exception);
        onAfter();

    }

    public void showErrorMsg(ApiException exception) {
        mRxErrorHandler.showErrorMessage(exception);
    }

    public void onFail(ApiException e) {

    }

    @Override
    public void onComplete() {
        onAfter();
    }

    public void onAfter() {

    }


}
