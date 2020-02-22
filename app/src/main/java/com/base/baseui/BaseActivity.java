package com.base.baseui;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.base.BaseApplication;
import com.base.utils.BarUtils;
import com.hwangjr.rxbus.RxBus;
import com.jfkj.iyouvip.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @description：
 * @author：bux on 2020/2/21 18:09
 * @email: 471025316@qq.com
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    private static final String TAG = "BaseActivity";
    Unbinder mUnbinder;
    BaseApplication mApplication;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
        mContext = this;
        setContentView(setLayoutId());
        mUnbinder = ButterKnife.bind(this);

        mApplication = (BaseApplication) getApplication();
        //mApi = mApplication.getAppComponent().getApiService();

        init();
        initBarStatus();

    }

    @Override
    protected void onStart() {
        setTitle(setPageTitle());
        super.onStart();

    }

    /**
     * 设置布局ID
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 设置标题 不返回默认为空
     * @return
     */
    protected abstract String setPageTitle();


    /**
     * 初始化操作
     */
    protected abstract void init();

    @Override
    protected void onResume() {
        super.onResume();
        // MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {

        RxBus.get().unregister(this);
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }


    /**
     * 沉浸式 true 沉浸式
     *
     * @return
     */
    protected boolean isNavBarImmersive() {
        return false;
    }


    protected boolean isAddMarginTop() {
        return true;
    }


    /**
     * 设置状态栏
     */
    protected void initBarStatus() {

        if (!isNavBarImmersive()) {
            //设置bar颜色
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(BaseApplication.get(), R.color.colorPrimary), 0);
            //设置bar浅色模式
            BarUtils.setStatusBarLightMode(this, true);
            //顶部增加状态栏高度MarginTop
            if (isAddMarginTop()) {
                BarUtils.addMarginTopEqualStatusBarHeight(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
            }
        } else {
            BarUtils.setStatusBarAlpha(this, 0);
            //BarUtils.setNavBarImmersive(this);
        }
    }
}
