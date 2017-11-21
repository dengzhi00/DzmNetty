package com.dzm.recreation.netty.task;

import com.dzm.recreation.netty.DzmNettyManager;
import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.netty.msg.DzmResponse;
import com.dzm.recreation.utils.LogUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author 邓治民
 * date 2017/8/3 14:30
 * 数据接收最终出口
 */

class DzmNettyHandler extends SimpleChannelInboundHandler<DzmResponse>{

    private ChannelHandlerContext context;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DzmResponse s) throws Exception {
        if(s.head == 100){
            LogUtils.msg("心跳");
        }else{
            LogUtils.msg(s.data);
            DzmNettyManager.getInstacnce().recive(s);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //netty 刚连接时调用此方法
        context = ctx;

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //连接断开
        context = null;
    }

    /**
     * 发送数据
     * @param data 数据
     * @return 成功失败
     */
    public boolean send(DzmRequest data){
        if(null != context && context.channel().isActive()){
            context.writeAndFlush(data);
            return true;
        }
        return false;
    }
}
