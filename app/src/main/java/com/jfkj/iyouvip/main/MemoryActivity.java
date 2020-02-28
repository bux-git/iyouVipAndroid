package com.jfkj.iyouvip.main;

import com.base.baseui.BaseActivity;
import com.jfkj.iyouvip.R;

/**
 * @description：
 * @author：bux on 2020/2/28 14:50
 * @email: 471025316@qq.com
 */
public class MemoryActivity extends BaseActivity {
    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String setPageTitle() {
        return "内存泄漏";
    }

    @Override
    protected void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        }).start();
    }
}
