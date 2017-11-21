package com.dzm.recreation.netty.code;

import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.utils.DesUtils;
import com.dzm.recreation.utils.OperatorUtils;
import com.dzm.recreation.utils.Param;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

/**
 *
 * @author 邓治民
 * date 2017/8/3 14:33
 * 编码
 */

public class DzmNettyEcode extends MessageToByteEncoder<DzmRequest>{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DzmRequest message, ByteBuf byteBuf) throws Exception {
        String s = message.data.toString();
        String key1 = Param.getKey();
        String key2 = Param.getKey();
        String msg = OperatorUtils.Encrypt(s,key1,key2);
        //数据
        byte[] bData = DesUtils.getInstance().encrypt(msg);
        //key1
        byte[] bKey1 = DesUtils.getInstance().encrypt(key1);
        //key2
        byte[] bKey2 = DesUtils.getInstance().encrypt(key2);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(message.head);
        buf.writeShort(bKey1.length);
        buf.writeShort(bKey2.length);
        buf.writeShort(bData.length);
        buf.writeBytes(bKey1);
        buf.writeBytes(bKey2);
        buf.writeBytes(bData);
        buf.markReaderIndex();
        byte b = buf.readByte();
        while (buf.isReadable()) {
            b ^= buf.readByte();
        }
//        LogUtils.msg("验签："+b);
        buf.resetReaderIndex();
        buf.writeByte(b);
        //写编码
        buf.markReaderIndex();
        byteBuf.writeByte(Param.PROTOCOL_0x7E);
        while (buf.isReadable()) {
            byte cout = buf.readByte();
            if (cout == Param.PROTOCOL_0x7E) {
                byteBuf.writeByte(Param.PROTOCOL_0x7D);
                byteBuf.writeByte(Param.PROTOCOL_0x02);
            } else if (cout == Param.PROTOCOL_0x7D) {
                byteBuf.writeByte(Param.PROTOCOL_0x7D);
                byteBuf.writeByte(Param.PROTOCOL_0x01);
            } else {
                byteBuf.writeByte(cout);
            }

        }
        byteBuf.writeByte(Param.PROTOCOL_0x7E);
        buf.resetReaderIndex();
        ReferenceCountUtil.safeRelease(buf);
    }
}
