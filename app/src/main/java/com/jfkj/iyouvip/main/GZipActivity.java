package com.jfkj.iyouvip.main;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.base.baseui.BaseMvpActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.jfkj.iyouvip.R;
import com.jfkj.iyouvip.mvp.gzip.GzipPresenter;
import com.jfkj.iyouvip.mvp.gzip.IGzipView;

import butterknife.BindView;

/**
 * @author bux on 2020/5/14 0014 11:35
 * @email 471025316@qq.com
 * @description
 */
public class GZipActivity extends BaseMvpActivity<GzipPresenter> implements IGzipView {

    @BindView(R.id.et_before_compress)
    TextInputEditText mEtBeforeCompress;
    @BindView(R.id.btn_compress)
    Button mBtnCompress;
    @BindView(R.id.et_after_compress)
    TextInputEditText mEtAfterCompress;
    @BindView(R.id.tv_compress_length)
    TextView mTvCompressLength;
    @BindView(R.id.btn_uncompress)
    Button mBtnUncompress;
    @BindView(R.id.et_restore)
    TextInputEditText mEtRestore;

    @Override
    protected GzipPresenter createPresenter() {
        return new GzipPresenter();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_gzip_layout;
    }

    @Override
    protected String setPageTitle() {
        return "压缩测试";
    }

    @Override
    protected void init() {
        mPresenter.getOriginData();
        mBtnCompress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.compress();
            }
        });

        mBtnUncompress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.unCompress();
            }
        });

        mEtBeforeCompress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.setOriginData(s.toString());
            }
        });
    }


    @Override
    public void setOriginData(String originData) {
        mEtBeforeCompress.setText(originData);
    }

    @Override
    public void setCompressData(String compressData, int compressByteLength) {
        mEtAfterCompress.setText(compressData);
        mTvCompressLength.setText(getString(R.string.compress_length_format,compressByteLength));
    }

    @Override
    public void setUnCompressData(String s) {
        mEtRestore.setText(s);
    }
}
