package com.dzm.recreation.netty;

import com.dzm.recreation.netty.msg.DzmResponse;

/**
 * Created by 83642 on 2017/8/7.
 */

public interface DzmNettyReciveListener {

    void recive(DzmResponse message);

}
