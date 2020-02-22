package com.base.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.ImageLoader;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.StringRes;

/**
 * @description：
 * @author：bux on 2018/7/4 17:36
 * @email: 471025316@qq.com
 */
public class BaseHolder extends BaseViewHolder {

    public BaseHolder(View view) {
        super(view);
    }


    public BaseHolder setTextColorResId(int viewId, int colorResId) {
        TextView view = getView(viewId);
        view.setTextColor(itemView.getContext().getResources().getColor(colorResId));
        return this;
    }

    public BaseHolder setImg(int viewId, String url) {
        ImageView view = getView(viewId);
        ImageLoader.load(url, view);
        return this;
    }

    public BaseHolder setTextFormat(int viewId, @StringRes int resId, Object... formatArgs) {
        TextView view = getView(viewId);
       // CommonUtil.setTextByResId(view, resId, formatArgs);
        return this;
    }
}
