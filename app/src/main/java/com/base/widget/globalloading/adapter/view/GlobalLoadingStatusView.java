package com.base.widget.globalloading.adapter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.widget.globalloading.GLoadData;
import com.blankj.utilcode.util.NetworkUtils;
import com.jfkj.iyouvip.R;

import static com.billy.android.loading.Gloading.STATUS_EMPTY_DATA;
import static com.billy.android.loading.Gloading.STATUS_LOADING;
import static com.billy.android.loading.Gloading.STATUS_LOAD_FAILED;
import static com.billy.android.loading.Gloading.STATUS_LOAD_SUCCESS;

/**
 * simple loading status view for global usage
 *
 * @author billy.qi
 * @since 19/3/19 23:12
 */
@SuppressLint("ViewConstructor")
public class GlobalLoadingStatusView extends LinearLayout implements View.OnClickListener {


    private final TextView mTextView;
    private final Runnable mRetryTask;
    private final ImageView mImageView;
    private GLoadData mGloadData;

    public GlobalLoadingStatusView(Context context, Runnable retryTask) {
        super(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.view_global_loading_status, this, true);
        mImageView = findViewById(R.id.image);
        mTextView = findViewById(R.id.text);
        this.mRetryTask = retryTask;
        setBackgroundColor(0xFFF0F0F0);

        mGloadData = new GLoadData();
    }

    public void setMsgViewVisibility(boolean visible) {
        mTextView.setVisibility(visible ? VISIBLE : GONE);
    }


    public void setStatus(int status) {
        boolean show = true;
        OnClickListener onClickListener = null;
        int image = mGloadData.getLoadAnim();
        int str = R.string.str_none;
        switch (status) {
            case STATUS_LOAD_SUCCESS:
                show = false;
                break;
            case STATUS_LOADING:
                str = mGloadData.getLoadMsg();
                break;
            case STATUS_LOAD_FAILED:
                str = mGloadData.getErrorMsg();
                image = mGloadData.getErrorMsg();
                Boolean networkConn = NetworkUtils.isConnected();
                if (!networkConn) {
                    str = mGloadData.getNoNetWorkMsg();
                    image = mGloadData.getNoNetWorkImg();
                }
                onClickListener = this;
                break;
            case STATUS_EMPTY_DATA:
                str = mGloadData.getEmptyMsg();
                image = mGloadData.getEmptyImg();
                break;
            default:
                break;
        }
        mImageView.setImageResource(image);
        setOnClickListener(onClickListener);
        mTextView.setText(str);
        setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mRetryTask != null) {

            mRetryTask.run();
        }
    }

    public void setDisPlayData(GLoadData gd) {
        this.mGloadData = gd;
    }
}
