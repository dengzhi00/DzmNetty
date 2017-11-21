package com.dzm.recreation.netty.msg;

import com.dzm.recreation.netty.msg.dto.BaseDto;

/**
 * Created by 83642 on 2017/8/8.
 */

public class DzmRequest<T extends BaseDto> {

    public int head;

    public T data;

}
