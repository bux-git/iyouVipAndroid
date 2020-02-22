package com.jfkj.iyouvip.main;

import com.base.adapter.BaseHolder;
import com.base.baseui.BaseMvpActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jfkj.iyouvip.R;
import com.jfkj.iyouvip.bean.BannerData;
import com.jfkj.iyouvip.mvp.main.IMainView;
import com.jfkj.iyouvip.mvp.main.MainPresenter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements IMainView {
    private static final String TAG = "MainActivity";
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    BaseQuickAdapter<BannerData.DatasBean, BaseHolder> mAdapter;
    private int page = 1;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String setPageTitle() {
        return null;
    }

    @Override
    protected void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new BaseQuickAdapter<BannerData.DatasBean, BaseHolder>(R.layout.main_item_layout) {

            @Override
            protected void convert(BaseHolder helper, BannerData.DatasBean item) {
                helper.setImg(R.id.iv_bg, item.getEnvelopePic())
                        .setText(R.id.title, item.getTitle());
            }
        });

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getData(++page);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getData(page = 1);
            }
        });

        mRefreshLayout.autoRefresh();


    }


    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        mAdapter.setEmptyView(R.layout.main_item_layout);
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        mRefreshLayout.finishRefresh(false);
        mRefreshLayout.finishLoadMore(false);
    }


    @Override
    public void setNewData(List<BannerData.DatasBean> banners) {
        mAdapter.setNewData(banners);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.resetNoMoreData();
    }

    @Override
    public void addPageData(List<BannerData.DatasBean> banners) {
        mAdapter.addData(banners);
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void dataLoadFinish() {
        mRefreshLayout.finishRefreshWithNoMoreData();
    }
}
