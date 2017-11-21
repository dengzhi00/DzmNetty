package com.dzm.recreation.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.dzm.recreation.netty.DzmNettyManager;
import com.dzm.recreation.netty.DzmNettyReciveListener;
import com.dzm.recreation.netty.msg.DzmResponse;

/**
 * Created by 83642 on 2017/8/8.
 */

public abstract class BaseActivity extends FragmentActivity implements DzmNettyReciveListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DzmNettyManager.getInstacnce().addListener(this);
        setContentView(layout());
        onCreate();
    }

    protected abstract int layout();

    protected abstract void onCreate();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DzmNettyManager.getInstacnce().remove(this);
    }

    @Override
    public void recive(DzmResponse message) {

    }
}
