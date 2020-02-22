package com.base.mvp;

/**
 * @description：
 * @author：bux on 2020/2/22 15:47
 * @email: 471025316@qq.com
 */
public interface IBaseRefreshLoadView<D> extends IBaseView{
    void setNewData(D d);
    void addPageData(D d);
    void dataLoadFinish();
}
