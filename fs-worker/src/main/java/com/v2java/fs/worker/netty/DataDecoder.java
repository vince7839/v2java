package com.v2java.fs.worker.netty;


import com.v2java.NettyMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.ByteToMessageCodec;
import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataDecoder extends ByteToMessageCodec {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf byteBuf) throws Exception {
        //  log.info("发出消息：{}",msg.toString());
        if (msg instanceof String) {
            byte[] bytes = ((String) msg).getBytes();
            byteBuf.writeLong(bytes.length);
            byteBuf.writeInt(NettyMsgType.STRING.getCode());
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(byteBuf);
        } else if (msg instanceof File) {
            long fileLength = ((File) msg).length();
            long start = 0;
            int BLOCK_SIZE = 1024 * 1024;
            while (start < fileLength) {
                long send = Math.min(fileLength - start, (long) BLOCK_SIZE);
                FileRegion region = new DefaultFileRegion((File) msg, start, send);
                start += send;
                byteBuf.writeLong(send);
                byteBuf.writeInt(NettyMsgType.FILE.getCode());
                ctx.writeAndFlush(byteBuf);
                ctx.writeAndFlush(region);
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List list) {
        int bytes = byteBuf.readableBytes();
        long length = byteBuf.readLong();
        int type = byteBuf.readInt();
        if (type == NettyMsgType.STRING.getCode()) {

        } else if (type == NettyMsgType.FILE.getCode()) {

        }
        byteBuf.nioBuffer();
    }
}
