package com.jfkj.iyouvip.mvp.gzip;

import com.base.http.BaseModel;
import com.base.mvp.BasePresenter;
import com.base.utils.GzipUtil;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.UnsupportedEncodingException;

/**
 * @author bux on 2020/5/14 0014 11:36
 * @email: 471025316@qq.com
 * @description:
 */
public class GzipPresenter extends BasePresenter<BaseModel, IGzipView> {

    public static String ORIGIN_DATA = "中文哈哈哈{\"value6\":\"testvalue\",\"name5\":\"testname\",\"name3\":\"testname\",\"value2\":\"testvalue\",\"value1\":\"testvalue\",\"value4\":\"testvalue\",\"name\":\"testname\",\"name2\":\"testname\",\"value\":\"testvalue\",\"name1\":\"testname\"}";

    public byte[] compressData;

    @Override
    protected <E> BaseModel createModule(LifecycleProvider<E> rxLife) {
        return null;
    }

    @Override
    public void start() {

    }


    public void getOriginData() {
        mProxyView.setOriginData(ORIGIN_DATA);
    }

    public void compress() {

        String result = GzipUtil.compress(ORIGIN_DATA);
        try {
            compressData = result.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mProxyView.setCompressData(new String(compressData), compressData.length);
    }

    public void unCompress() {
        byte[] result = GzipUtil.uncompress(compressData);
        mProxyView.setUnCompressData(new String(result));
    }

    public void setOriginData(String s) {
        ORIGIN_DATA = s;
    }
}
