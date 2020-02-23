package com.base.baseui;

import android.content.Context;
import android.os.Bundle;

import com.base.mvp.BasePresenter;
import com.base.mvp.IBaseView;

import androidx.annotation.Nullable;

/**
 * @description：MVP基类
 * @author：bux on 2020/2/22 11:03
 * @email: 471025316@qq.com
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {


    protected P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //创建present
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this, this);
        }
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }

    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();

    //***************************************IBaseView方法实现*************************************


    @Override
    public void onEmpty(Object tag) {
        showEmpty();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        showLoadFailed();
    }

    @Override
    public Context getContext() {
        return mContext;
    }


}
