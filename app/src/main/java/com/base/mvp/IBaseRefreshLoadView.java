package com.base.mvp;

/**
 * @description：
 * @author：bux on 2020/2/22 15:47
 * @email: 471025316@qq.com
 */
public interface IBaseRefreshLoadView<D> extends IBaseView {
    /**
     * 刷新数据
     *
     * @param d
     */
    void onRefreshData(D d);

    /**
     * 下一页数据
     *
     * @param d
     */
    void onNextPageData(D d);

    /**
     * 数据完全加载完毕，已经没有数据了
     */
    void onDataFinish();
}
