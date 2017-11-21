package com.dzm.recreation.netty.msg;

import com.dzm.recreation.utils.GsonUtils;

/**
 * Created by 83642 on 2017/8/8.
 */

public class DzmResponse {

    public int head;

    public String data;

    public <T> T toDto(Class<T> cls){
        return GsonUtils.getGson().fromJson(data,cls);
    }

}
