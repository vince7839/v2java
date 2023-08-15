package com.v2java.fs.worker.netty.master;

import com.v2java.NettyMsgType;
import com.v2java.fs.worker.netty.FilePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liaowenxing 2023/8/15
 **/
public class FilePacketEncoder extends MessageToByteEncoder<FilePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FilePacket msg, ByteBuf out) throws Exception {
        CompositeByteBuf compositeByteBuf = ctx.alloc().compositeBuffer();
        ByteBuf type = ctx.alloc().buffer().writeByte(NettyMsgType.FILE.getCode());
        FileRegion region = new DefaultFileRegion(msg.getFile(), 0, msg.getFile().length());
        compositeByteBuf.addComponent(type);
    }
}
