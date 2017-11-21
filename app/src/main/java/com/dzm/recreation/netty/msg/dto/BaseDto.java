package com.dzm.recreation.netty.msg.dto;

import com.dzm.recreation.utils.GsonUtils;

/**
 * Created by 83642 on 2017/8/8.
 */
public class BaseDto {
    public String msg;

    public int code;

    @Override
    public String toString() {
        return GsonUtils.getGson().toJson(this);
    }
}
