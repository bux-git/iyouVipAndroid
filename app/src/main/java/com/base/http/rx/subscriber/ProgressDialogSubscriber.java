package com.base.http.rx.subscriber;

import android.content.Context;
import android.content.DialogInterface;

import com.base.widget.dialog.RxDialogLoading;

import org.reactivestreams.Subscription;

/**
 * @description：
 * @author：bux on 2018/4/19 11:48
 * @email: 471025316@qq.com
 */
public abstract class ProgressDialogSubscriber<T> extends ErrorHandlerSubscriber<T> implements DialogInterface.OnCancelListener {

    private RxDialogLoading mProgressDialog;
    private Context mContext;
    private boolean isShow = true;

    private Subscription mSubscription;


    public ProgressDialogSubscriber(Context context, boolean isShow, boolean cancelable) {
        this.isShow = isShow;
        mContext = context;
        if (isShow) {
            if (mProgressDialog == null) {
                mProgressDialog = new RxDialogLoading(context, cancelable, this);
            }
        }

    }

    protected ProgressDialogSubscriber(Context context) {
        this(context, true, false);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        super.onSubscribe(subscription);
        mSubscription = subscription;
        if (isShow) {
            mProgressDialog.show();
        }

    }


    @Override
    public void onError(Throwable t) {
        dismissDialog();
        super.onError(t);

    }

    @Override
    public void onComplete() {
        dismissDialog();
        super.onComplete();

    }


    protected void dismissDialog() {
        if (isShow) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 无网络 加载失败 重试
     */
    public void noLoadReTry() {

    }

    /**
     * loading关闭通知
     *
     * @param dialog
     */
    @Override
    public void onCancel(DialogInterface dialog) {

    }


}
