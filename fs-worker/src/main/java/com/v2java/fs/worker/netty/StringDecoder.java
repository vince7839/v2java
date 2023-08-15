package com.v2java.fs.worker.netty;


import com.v2java.NettyMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class StringDecoder extends ByteToMessageCodec<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf byteBuf) throws Exception {
        //  log.info("发出消息：{}",msg.toString());
        byte[] bytes = msg.getBytes();
        //8字节消息长度 + 1字节消息类型 + 8字节watermark
       // byteBuf.writeLong(Long.BYTES + Byte.BYTES + bytes.length);
        byteBuf.writeByte(NettyMsgType.STRING.getCode());
        byteBuf.writeBytes(bytes);
       // ctx.writeAndFlush(byteBuf);
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
        //long length = byteBuf.readLong();
        int type = byteBuf.readByte();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String msg = new String(bytes);
        list.add(msg);
    }
}
