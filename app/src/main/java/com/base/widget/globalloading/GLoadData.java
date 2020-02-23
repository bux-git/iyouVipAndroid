package com.base.widget.globalloading;

import com.jfkj.iyouvip.R;

/**
 * @description：全局空布局等 资源类
 * @author：bux on 2019/4/2 18:08
 * @email: 471025316@qq.com
 */
public class GLoadData {

    private int emptyMsg = R.string.empty;
    private int emptyImg = R.drawable.icon_empty;

    private int errorMsg = R.string.load_failed;
    private int errorImg = R.drawable.icon_failed;

    private int loadMsg = R.string.loading;
    private int loadAnim = R.drawable.loading;

    private int noNetWorkMsg = R.string.load_failed_no_network;
    private int noNetWorkImg = R.drawable.icon_no_wifi;

    public GLoadData() {
    }

    public GLoadData(int emptyMsg, int emptyImg) {
        this.emptyMsg = emptyMsg;
        this.emptyImg = emptyImg;
    }

    public GLoadData(int emptyMsg, int emptyImg, int errorMsg, int errorImg, int loadMsg, int loadAnim) {
        this.emptyMsg = emptyMsg;
        this.emptyImg = emptyImg;
        this.errorMsg = errorMsg;
        this.errorImg = errorImg;
        this.loadMsg = loadMsg;
        this.loadAnim = loadAnim;
    }


    public int getEmptyMsg() {
        return emptyMsg;
    }

    public void setEmptyMsg(int emptyMsg) {
        this.emptyMsg = emptyMsg;
    }

    public int getEmptyImg() {
        return emptyImg;
    }

    public void setEmptyImg(int emptyImg) {
        this.emptyImg = emptyImg;
    }

    public int getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(int errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorImg() {
        return errorImg;
    }

    public void setErrorImg(int errorImg) {
        this.errorImg = errorImg;
    }

    public int getLoadMsg() {
        return loadMsg;
    }

    public void setLoadMsg(int loadMsg) {
        this.loadMsg = loadMsg;
    }

    public int getLoadAnim() {
        return loadAnim;
    }

    public void setLoadAnim(int loadAnim) {
        this.loadAnim = loadAnim;
    }

    public int getNoNetWorkMsg() {
        return noNetWorkMsg;
    }

    public void setNoNetWorkMsg(int noNetWorkMsg) {
        this.noNetWorkMsg = noNetWorkMsg;
    }

    public int getNoNetWorkImg() {
        return noNetWorkImg;
    }

    public void setNoNetWorkImg(int noNetWorkImg) {
        this.noNetWorkImg = noNetWorkImg;
    }
}
