package com.v2java.fs.worker.netty;


import com.v2java.NettyMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.ByteToMessageCodec;

import java.io.File;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataDecoder extends ByteToMessageCodec {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf byteBuf) throws Exception {
        //  log.info("发出消息：{}",msg.toString());
        if (msg instanceof String) {
            byte[] bytes = ((String) msg).getBytes();
            byteBuf.writeInt(NettyMsgType.STRING.getCode());
            byteBuf.writeInt(bytes.length + Integer.BYTES);
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(byteBuf);
        } else if (msg instanceof File) {
            long fileLength = ((File) msg).length();
            long start = 0;
            long BLOCK_SIZE = 1024 * 1024;
            while (start < fileLength) {
                int send = (int) Math.min(fileLength - start, BLOCK_SIZE);
                FileRegion region = new DefaultFileRegion((File) msg, start, send);
                start += send;
                byteBuf.writeInt(NettyMsgType.FILE.getCode());
                byteBuf.writeInt(send + Integer.BYTES);
                ctx.writeAndFlush(byteBuf);
                ctx.writeAndFlush(region);
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List list) {
        int type = byteBuf.readInt();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Object msg = null;
        if (type == NettyMsgType.STRING.getCode()) {
            msg = new String(bytes);
        } else if (type == NettyMsgType.FILE.getCode()) {
            msg = new FilePacket(bytes);
        }
        if (Objects.nonNull(msg)) {
            list.add(msg);
        }
    }
}
