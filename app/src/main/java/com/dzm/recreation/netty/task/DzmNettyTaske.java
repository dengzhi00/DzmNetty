package com.dzm.recreation.netty.task;

import android.util.Log;

import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.netty.task.DzmNettyChannelInitializer;
import com.dzm.recreation.netty.task.DzmNettyHandler;
import com.dzm.recreation.utils.LogUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by 83642 on 2017/8/3.
 */

public class DzmNettyTaske extends Thread {

    private DzmNettyHandler handler;
    private NioEventLoopGroup eventLoop = null;

    @Override
    public void run() {
        while (true) {

            try {
                Bootstrap bootstrap = new Bootstrap();
                eventLoop = new NioEventLoopGroup();
                bootstrap.group(eventLoop);
                bootstrap.channel(NioSocketChannel.class);
                handler = new DzmNettyHandler();
                bootstrap.handler(new DzmNettyChannelInitializer(handler));
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);//10秒超时
                LogUtils.msg("连接中");
                ChannelFuture channelFuture = bootstrap.connect("192.168.2.3", 9991).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        LogUtils.msg("连接");
                        if (!future.isSuccess()) {
                            future.cause().printStackTrace();
                        }
                    }
                }).sync();
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != eventLoop && !eventLoop.isShutdown()) {
                    eventLoop.shutdownGracefully();
                }
            }
            int index = 5;
            while(index>0){
                Log.d("netty_taske","重连接："+index);
                index--;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送数据
     * @param message 消息
     * @return 成功 失败
     */
    public boolean send(DzmRequest message) {
        return null != handler && handler.send(message);
    }
}
