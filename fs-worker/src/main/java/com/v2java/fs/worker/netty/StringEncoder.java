package com.v2java.fs.worker.netty;

import com.v2java.NettyMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaowenxing 2023/8/15
 **/
@Slf4j
public class StringEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf byteBuf) throws Exception {
        byte[] bytes = msg.getBytes();
        byteBuf.writeByte(NettyMsgType.STRING.getCode());
        byteBuf.writeBytes(bytes);
    }
}
