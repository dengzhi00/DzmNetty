package com.dzm.recreation.netty.task;

import com.dzm.recreation.netty.code.DzmNettyDecode;
import com.dzm.recreation.netty.code.DzmNettyEcode;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 *
 * @author 邓治民
 * date 2017/8/3 14:37
 * 业务拦截配置为止
 */

public class DzmNettyChannelInitializer extends ChannelInitializer<SocketChannel>{

    private DzmNettyHandler handler;

    public DzmNettyChannelInitializer(DzmNettyHandler handler){
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(15,10,0));
        pipeline.addLast(new DzmNettyDecode());
        pipeline.addLast(new DzmNettyEcode());
        pipeline.addLast(new DzmNettyHeart());
        pipeline.addLast(handler);
    }
}
