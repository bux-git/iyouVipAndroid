package com.jfkj.iyouvip.mvp.gzip;

import com.base.mvp.IBaseView;

/**
 * @author bux on 2020/5/14 0014 11:37
 * @email 471025316@qq.com
 * @description
 */
public interface IGzipView extends IBaseView {

    void setOriginData(String originData);

    /**
     * @param compressData       压缩后转换成字符串数据
     * @param compressByteLength 压缩后byte长度
     *
     */
    void setCompressData(String compressData, int compressByteLength);


    void setUnCompressData(String s);
}
