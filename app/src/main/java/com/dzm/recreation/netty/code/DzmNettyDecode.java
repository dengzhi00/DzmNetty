package com.dzm.recreation.netty.code;

import com.dzm.recreation.netty.msg.DzmResponse;
import com.dzm.recreation.utils.DesUtils;
import com.dzm.recreation.utils.LogUtils;
import com.dzm.recreation.utils.OperatorUtils;
import com.dzm.recreation.utils.Param;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

/**
 *
 * @author 邓治民
 * date 2017/8/3 14:32
 * 解码
 */

public class DzmNettyDecode extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        if(in.readableBytes()<=0){
            return;
        }
        in.markReaderIndex();
        if(in.readByte() != Param.PROTOCOL_0x7E){
            in.discardReadBytes();
            return;
        }
        int len;
        if((len = in.bytesBefore(Param.PROTOCOL_0x7E)) == -1){
            in.resetReaderIndex();
            return;
        }
        in.resetReaderIndex();
        ByteBuf deco = Unpooled.buffer(len + 2);
        in.readBytes(deco);

        byte start = deco.readByte();
        if(start != Param.PROTOCOL_0x7E){
            return;
        }
        ByteBuf buf = Unpooled.buffer();
        while(deco.isReadable()){
            byte cout = deco.readByte();
            if(cout == Param.PROTOCOL_0x7E)
                continue;
            if(cout == Param.PROTOCOL_0x7D){
                byte b = deco.readByte();
                if(b == Param.PROTOCOL_0x02){
                    buf.writeByte(Param.PROTOCOL_0x7E);
                }else if(b == Param.PROTOCOL_0x01){
                    buf.writeByte(Param.PROTOCOL_0x7D);
                }
            }else{
                buf.writeByte(cout);
            }
        }
        buf.markReaderIndex();
        int head = buf.readInt();
        int ke1Lenth = buf.readShort();
        int key2Lehth = buf.readShort();
        int dataLenth = buf.readShort();
        buf.resetReaderIndex();
        ByteBuf bufData = Unpooled.buffer(ke1Lenth+key2Lehth+dataLenth+10);
        buf.readBytes(bufData);
        byte sign = buf.readByte();
        bufData.markReaderIndex();
        byte b = bufData.readByte();
        while (bufData.isReadable()){
            b^= bufData.readByte();
        }
        bufData.resetReaderIndex();
        if(b != sign){
            in.discardReadBytes();
            ReferenceCountUtil.release(bufData);
            ReferenceCountUtil.release(buf);
            ReferenceCountUtil.release(deco);
            LogUtils.msg("签名错误：sigh:"+sign+"\tb:"+b);
            return;
        }
        bufData.readInt();
        bufData.readShort();
        bufData.readShort();
        bufData.readShort();
        byte[] key1Bs = new byte[ke1Lenth];
        byte[] key2Bs = new byte[key2Lehth];
        byte[] dataBs = new byte[dataLenth];
        bufData.readBytes(key1Bs);
        bufData.readBytes(key2Bs);
        bufData.readBytes(dataBs);
        String key1 = DesUtils.getInstance().decrypt(key1Bs);
        String key2 = DesUtils.getInstance().decrypt(key2Bs);
        String dataDs = DesUtils.getInstance().decrypt(dataBs);
        String data = OperatorUtils.decrypt(dataDs,key1,key2);
        DzmResponse message = new DzmResponse();
        message.head = head;
        message.data = data;
        list.add(message);
        ReferenceCountUtil.release(bufData);
        ReferenceCountUtil.release(buf);
        ReferenceCountUtil.release(deco);

    }
}
