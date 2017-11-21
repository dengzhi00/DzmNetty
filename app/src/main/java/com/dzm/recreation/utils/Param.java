package com.dzm.recreation.utils;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

/**
 * Created by xiang on 16/8/30.
 */
public final class Param {

    public static final byte PROTOCOL_0x7E = 0x7e;
    public static final byte PROTOCOL_0x7D = 0x7d;
    public static final byte PROTOCOL_0x01 = 0x01;
    public static final byte PROTOCOL_0x02 = 0x02;
    public static final Charset GBK = Charset.forName("GBK");


    public static String getKey(){
        String u1 = UUID.randomUUID().toString();
        String u2 = UUID.randomUUID().toString();
        String key = "";
        Random random = new Random();
        for(int i = 0;i<8;i++){
            key+=String.valueOf(u1.charAt(random.nextInt(32)));
        }
        for(int i = 0;i<8;i++){
            key+=String.valueOf(u2.charAt(random.nextInt(32)));
        }
        return key;
    }
}
