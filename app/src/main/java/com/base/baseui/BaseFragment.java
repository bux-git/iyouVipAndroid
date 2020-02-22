package com.base.baseui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseApplication;
import com.hwangjr.rxbus.RxBus;
import com.trello.rxlifecycle2.components.RxFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @description：
 * @author：bux on 2020/2/22 9:48
 * @email: 471025316@qq.com
 */
public abstract class BaseFragment extends RxFragment {

    private static final String TAG = "BaseFragment";

    Unbinder mUnbinder;
    protected View mRootView;
    BaseApplication mApplication;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Log.d(TAG, "onCreateView: ");
        RxBus.get().register(this);
        mRootView = inflater.inflate(setLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mApplication = (BaseApplication) getActivity().getApplicationContext();

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    protected abstract int setLayoutId();


    protected abstract void init();

    @Override
    public void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        // Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        // Log.d(TAG, "onDestroyView: ");

        RxBus.get().unregister(this);
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        //Log.d(TAG, "onDestroy: ");
        super.onDestroy();

    }
}
