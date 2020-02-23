package com.base.baseui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.BaseApplication;
import com.base.utils.BarUtils;
import com.base.widget.globalloading.GLoadData;
import com.billy.android.loading.Gloading;
import com.hwangjr.rxbus.RxBus;
import com.jfkj.iyouvip.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    //全局加载
    protected Gloading.Holder mHolder;

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
     *
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


    /**
     * 全局空view设置,空布局等要显示得View得位置
     * make a Gloading.Holder wrap with current activity by default
     * override this method in subclass to do special initialization
     */
    protected void initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            View view = setGloadingWrapView();

            if (view.getParent() instanceof RelativeLayout
                    || view.getParent() instanceof ConstraintLayout) {
                //bind status view to activity root view by default
                mHolder = Gloading.getDefault().cover(view);
            } else {
                mHolder = Gloading.getDefault().wrap(view);
            }
            mHolder.withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
    }

    /**
     * 设置空布局等显示得位置，
     * //为某个View显示加载状态
     * //Gloading会自动创建一个FrameLayout，将view包裹起来，LoadingView也显示在其中
     * 如果父容器为 RelativeLayout (或ConstraintLayout)，用cover可以保持与同级其它控件之间的关联关系（since: v1.1.0）
     *
     * @return
     */
    protected View setGloadingWrapView() {
        return findViewById(android.R.id.content);
    }

    protected void onLoadRetry() {
        // override this method in subclass to do retry task
    }

    public void showLoading() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoading();
    }

    public void showLoadSuccess() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadSuccess();
    }

    public void showLoadFailed() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadFailed();
    }

    public void showEmpty() {
        initLoadingStatusViewIfNeed();
        mHolder.showEmpty();
    }

    public void showEmpty(int emptyMsg, int emptyImg) {
        initLoadingStatusViewIfNeed();
        mHolder.withData(new GLoadData(emptyMsg, emptyImg)).showEmpty();
    }
}
