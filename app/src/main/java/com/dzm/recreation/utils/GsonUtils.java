package com.dzm.recreation.utils;

import com.google.gson.Gson;

/**
 * Created by 83642 on 2017/8/8.
 */

public class GsonUtils {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    public static Gson getGson(){
        return gson;
    }

}
