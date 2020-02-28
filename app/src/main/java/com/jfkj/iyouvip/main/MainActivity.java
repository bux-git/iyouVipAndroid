package com.jfkj.iyouvip.main;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements IMainView {
    private static final String TAG = "MainActivity";
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    BaseQuickAdapter<BannerData.DatasBean, BaseHolder> mAdapter;
    @BindView(R.id.btn_load)
    Button mBtnLoad;
    @BindView(R.id.btn_empty)
    Button mBtnEmpty;
    @BindView(R.id.btn_failed)
    Button mBtnFailed;


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
                getNextPageData();
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
            }
        });
        mRefreshLayout.autoRefresh();

    }

    private void getValue(HashMap<String, String> hashMap) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            /*if(entry.getValue() instanceof Map<String,String>){

            }else{

            }*/
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }

    private void getNextPageData() {
        mPresenter.getData(++page);
    }

    private void refreshData() {
        mPresenter.getData(page = 1);
    }

    @OnClick({R.id.btn_load, R.id.btn_empty, R.id.btn_failed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                showLoading();
                refreshData();
                break;
            case R.id.btn_empty:
                showEmpty();
                break;
            case R.id.btn_failed:
                showLoadFailed();
                startActivity(new Intent(this, MemoryActivity.class));
                break;
            default:
        }
    }


    @Override
    protected View setGloadingWrapView() {
        return mRefreshLayout;
    }


    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        if (page == 1) {
            showLoadFailed();
        } else {
            mRefreshLayout.finishRefresh(false);
            mRefreshLayout.finishLoadMore(false);
        }
    }


    @Override
    public void onRefreshData(List<BannerData.DatasBean> datasBeans) {
        mAdapter.setNewData(datasBeans);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.resetNoMoreData();
        showLoadSuccess();
    }

    @Override
    public void onNextPageData(List<BannerData.DatasBean> datasBeans) {
        mAdapter.addData(datasBeans);
        mRefreshLayout.finishLoadMore();
        showLoadSuccess();
    }

    @Override
    public void onDataFinish() {
        mRefreshLayout.finishRefreshWithNoMoreData();
    }


}
