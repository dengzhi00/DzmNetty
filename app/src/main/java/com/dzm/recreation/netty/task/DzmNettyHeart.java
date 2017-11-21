package com.dzm.recreation.netty.task;

import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.netty.msg.dto.HeartDao;
import com.dzm.recreation.utils.LogUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 *
 * @author 邓治民
 * date 2017/8/3 14:35
 * 心跳
 */
class DzmNettyHeart extends ChannelInboundHandlerAdapter{

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.READER_IDLE){//读闲置
                ctx.close();
                LogUtils.msg("读闲置");
            }else if(event.state() == IdleState.WRITER_IDLE){//写闲置
                DzmRequest message = new DzmRequest();
                message.head = 100;
                HeartDao heartDao = new HeartDao();
                heartDao.heart = "心跳";
                message.data = heartDao;
                ctx.writeAndFlush(message);
                LogUtils.msg("写闲置");
            }
        }
    }
}
