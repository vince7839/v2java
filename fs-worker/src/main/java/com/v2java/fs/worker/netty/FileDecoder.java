package com.v2java.fs.worker.netty;


import com.v2java.NettyMsgType;
import com.v2java.fs.worker.FileManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.ByteToMessageCodec;
import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileDecoder extends ByteToMessageCodec<FilePacket> {

    @Autowired
    FileManager fileManager;

    @Override
    protected void encode(ChannelHandlerContext ctx, FilePacket msg, ByteBuf byteBuf) {
        File file = fileManager.getFileByWatermark(msg.getWatermark());
        FileRegion region = new DefaultFileRegion(file, 0, file.length());
        //8字节消息长度 + 1字节消息类型 + 8字节watermark
        byteBuf.writeLong(Long.BYTES + Byte.BYTES + Long.BYTES + file.length());
        byteBuf.writeInt(NettyMsgType.FILE.getCode());
        byteBuf.writeLong(msg.getWatermark());
        ctx.writeAndFlush(byteBuf);
        ctx.writeAndFlush(region);
    }

    /**
     * 消息长度4字节、消息类型4字节、
     *
     * @param ctx
     * @param byteBuf
     * @param list
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List list) {
        long length = byteBuf.readLong();
        byte type = byteBuf.readByte();
        long watermark = byteBuf.readLong();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        list.add(new FilePacket(watermark,bytes));
    }
}
