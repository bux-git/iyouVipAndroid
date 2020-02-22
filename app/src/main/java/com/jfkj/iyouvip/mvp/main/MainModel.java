package com.jfkj.iyouvip.mvp.main;

import com.base.constant.Constant;
import com.base.http.BaseModel;
import com.base.http.rx.subscriber.ErrorHandlerSubscriber;
import com.jfkj.iyouvip.bean.BannerData;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * @description：
 * @author：bux on 2020/2/22 11:23
 * @email: 471025316@qq.com
 */
public class MainModel<E> extends BaseModel<E> {


    protected MainModel(LifecycleProvider<E> rxLife) {
        super(rxLife);
    }

    public void getData(int page, ErrorHandlerSubscriber<BannerData> observer) {
        getQueryObj(Constant.ACTION_BANNER + page + "/json", null, BannerData.class)
                .subscribe(observer);
    }


}
