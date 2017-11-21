package com.dzm.recreation.netty;

import android.os.Handler;
import android.util.SparseArray;

import com.dzm.recreation.netty.task.DzmNettyTaske;
import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.netty.msg.DzmResponse;


/**
 * Created by 83642 on 2017/8/3.
 */

public class DzmNettyManager {

    private static DzmNettyManager manager;

    private Handler handler;

    public static DzmNettyManager getInstacnce(){
        if(null == manager){
            synchronized (DzmNettyManager.class){
                if(null == manager){
                    manager = new DzmNettyManager();
                }
            }
        }
        return manager;
    }

    private DzmNettyManager(){
        handler = new Handler();
        sparseArray = new SparseArray<>();
    }

    private DzmNettyTaske taske;

    private SparseArray<DzmNettyReciveListener> sparseArray;

    public void initTaske(){
        if(null == taske){
            taske = new DzmNettyTaske();
            taske.start();
        }
    }

    public void send(DzmRequest message){
        if(null != taske){
            taske.send(message);
        }
    }

    public void addListener(DzmNettyReciveListener listener){
        sparseArray.append(listener.hashCode(),listener);
    }

    public void remove(DzmNettyReciveListener listener){
        sparseArray.remove(listener.hashCode());
    }

    public void recive(final DzmResponse message){
        handler.post(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<sparseArray.size();i++){
                    sparseArray.valueAt(i).recive(message);
                }
            }
        });
    }

}
