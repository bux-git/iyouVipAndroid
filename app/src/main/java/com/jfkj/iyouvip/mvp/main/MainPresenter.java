package com.jfkj.iyouvip.mvp.main;

import com.base.http.exception.ApiException;
import com.base.http.rx.subscriber.ErrorHandlerSubscriber;
import com.base.mvp.BasePresenter;
import com.jfkj.iyouvip.bean.BannerData;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * @description：
 * @author：bux on 2020/2/22 11:23
 * @email: 471025316@qq.com
 */
public class MainPresenter extends BasePresenter<MainModel, IMainView> {


    @Override
    protected <E> MainModel<E> createModule(LifecycleProvider<E> rxLife) {
        return new MainModel<E>(rxLife);
    }

    @Override
    public void start() {

    }

    public void getData(int page) {
        getModule().getData(page, new ErrorHandlerSubscriber<BannerData>() {
            @Override
            public void onNext(BannerData bannerData) {
                if (bannerData == null) {
                    mProxyView.onEmpty(null);
                } else if (page == 1) {
                    mProxyView.setNewData(bannerData.getDatas());
                } else if (bannerData.getCurPage() >=3) {// bannerData.getPageCount()
                    mProxyView.dataLoadFinish();
                } else {
                    mProxyView.addPageData(bannerData.getDatas());
                }
            }

            @Override
            public void onFail(ApiException e) {
                super.onFail(e);
                mProxyView.onEmpty(e);
            }
        });
    }
}

